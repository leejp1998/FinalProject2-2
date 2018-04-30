package todo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import todo.tool.FileTool;
import todo.tool.TimeTool;
import todo.adapter.RouteAdapter;
import todo.routeinfor.AllInfor;
import todo.routeinfor.DayInfor;
import todo.routeinfor.Infor;
import com.example.xinliao.finalproject.R;

/**
 * Created by Xin Liao on 4/29/2018.
 */

public class ScheduleView extends LinearLayout
        implements SelecteView.OnSetChangeListener
        ,MonthDataView.OnDaySelectListener
        ,RouteShowDeleteView.DelectListener
        ,View.OnClickListener{

    private View view;
    private SelecteView selecteView;
    private MonthDataView monthDataView;
    private WeekDataView weekDataView;
    private RouteShowDeleteView routeShowDeleteView;
    private ImageView addImage;

    private Context context;

    private AddRouteListener addRouteListener;

    private List<Infor> refreshList;
    private RouteAdapter adapter;

    private AllInfor infors = null;//所有的行程

    private int todayYear,todayMonth,todayDay;//今天的时间
    private int selectDay = -1;               //选择的时间
    private int currentYear,currentMonth,currentDay; //当前的时间
    private static int Year_Tag  = 110000;
    private static int Month_Tag = 001100;
    private static int Day_Tag   = 000011;
    private static String CanCleRoute    = "取消添加";
    private static String AddRoute       = "添加行程";
    private static String ListRoute      = "您的行程如下：";
    private static final String Filename = "myschdule.route";


    public ScheduleView(Context context) {
        super(context);
        init(context);
    }

    public ScheduleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScheduleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        this.context =context;

        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.schedule_layout,this);
        selecteView = (SelecteView)view.findViewById(R.id.select_view);
        monthDataView = (MonthDataView)view.findViewById(R.id.month_data_view);
        routeShowDeleteView = (RouteShowDeleteView)view.findViewById(R.id.route_show_delect);
        addImage = (ImageView)view.findViewById(R.id.add_route_image);

        selecteView.setDate(new Date(System.currentTimeMillis()));
        selecteView.setOnSetChangeListener(this);
        monthDataView.setTime(selecteView.getYear(),selecteView.getMonth(),selecteView.getMonth());
        System.out.println("--->out"+selecteView.getYear()+" "+selecteView.getMonth()+" "+selecteView.getDay());
        monthDataView.setOnDaySelectListener(this);
        addImage.setOnClickListener(this);


        //初始化展示行程的List
        initTodayTime();
        initCurrentTime();
        DayInfor dayInfor = FileTool.getDayInfor(context,currentYear,currentMonth,currentDay);
        refreshList = new ArrayList<Infor>();
        if(dayInfor != null){
            List<Infor> list = dayInfor.getInforList();
            refreshList.clear();
            refreshList.addAll(list);
        }
        adapter = new RouteAdapter(context,refreshList);
        routeShowDeleteView.setAdapter(adapter);
        routeShowDeleteView.setDelectListener(this);

    }

    private void initTodayTime(){
        TimeTool timeTool = new TimeTool();
        Date date  = new Date(System.currentTimeMillis());
        todayYear  = timeTool.getYear(date);
        todayMonth = timeTool.getMonth(date);
        todayDay   = timeTool.getDay(date);
    }

    private void initCurrentTime(){
        currentYear = selecteView.getYear();
        currentMonth = selecteView.getMonth();
        currentDay = selecteView.getDay();
    }

    //日期栏改变的时候调用
    @Override
    public void onChange(int year, int month, int day) {
        initCurrentTime();
        System.out.println("--->"+year+month+day);

        if(monthDataView.getTag()== null){
            refreshList.clear();
            adapter.notifyDataSetChanged();
        }else {
            mySelect(year,month,day);
        }

        monthDataView.setTime(year,month,day);
        System.out.println("--->"+monthDataView.mGridView.getChildCount());
        monthDataView.setSelect(year,month,day);
    }

    //点击日期触发
    @Override
    public void onDaySelect( int day ,View view) {

        mySelect(currentYear,currentMonth,day);
        monthDataView.setSelect(currentYear,currentMonth,day);
        selectDay =currentDay= day;
        selecteView.setDay(day);

    }

    private void setSelect(int year,int month,int day){

        mySelect(year,month,day);
        //monthDataView.setAllNotSelect();
        if(monthDataView.getTag() != null){
            ((DayDataView)monthDataView.getTag()).setSelect(false);
        }
        DayDataView dayDataView = (DayDataView)view;
        dayDataView.setSelect(true);
        monthDataView.setTag(dayDataView);
        selectDay =currentDay= day;
    }

    //设置 红点+行程list 更新
    public void mySelect(int year,int month,int day){
        monthDataView.refresh();  //为了提示的小红点刷新
        List<Infor> list = FileTool.getDayInforList(context,year,month,day);
        refreshList.clear();
        refreshList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    //AlertDialog的Title
    private String getTitleText(int day){
        initCurrentTime();
        String text = currentMonth+"月"+day+"日"+"\r\n"+ListRoute;
        return text;
    }

    //保存文件
    private void saveRouteData(int year,int month,int day
            ,int hour,int minute,String data) throws Exception {

        String inforKey = FileTool.getInforKey(hour,minute);
        String dayinforKey = FileTool.getDayInforKey(year,month,day);

        infors = FileTool.getAllInfor(context);
        if(infors == null){infors = new AllInfor();}

        DayInfor dayInfor = infors.getDayRouteList(dayinforKey);
        if(dayInfor == null){
            dayInfor = new DayInfor();
        }
        Infor infor = dayInfor.getInfor(inforKey);
        if(infor == null){
            infor = new Infor();   //创建一个Info存储行程
        }
        infor.setYear(year); infor.setMonth(month); infor.setDay(day);
        infor.setHour(hour);infor.setMinute(minute);
        infor.setData(data);

        dayInfor.addInfor(inforKey,infor);
        infors.addDayRouteList(dayinforKey,dayInfor);

        FileTool.writeAllInfor(context,infors);

        mySelect(year,month,day);
    }

    //删除按键处理
    @Override
    public void delect(int selectItem) {

        Infor infor = refreshList.get(selectItem);
        int year = infor.getYear();
        int month = infor.getMonth();
        int day = infor.getDay();
        int hour = infor.getHour();
        int minute = infor.getMinute();

        FileTool.removeInfor(context,year,month,day,hour,minute);
        mySelect(year,month,day);

    }

    //点击事件处理
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_route_image:
                if(addRouteListener != null){
                    addRouteListener.addRoute((View)monthDataView.getTag());
                }
                break;
        }
    }

    public void setAddRouteListener(AddRouteListener addRouteListener){
        this.addRouteListener = addRouteListener;
    }
    public interface AddRouteListener{
        void addRoute(View view);
    }




    public int getCurrentYear(){
        return currentYear;
    }
    public int getCurrentMonth(){
        return currentMonth;
    }
    public int getCurrentDay(){
        return currentDay;
    }
    public int getSelectDay(){
        return selectDay;
    }
}
