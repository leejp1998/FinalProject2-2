package todo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xinliao.finalproject.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import todo.routeinfor.Infor;


public class RouteAdapter2 extends BaseAdapter {

    private Context context;
    private List<Infor> inforList;
    public RouteAdapter2(Context context, List<Infor> inforList){
        this.context = context;
        this.inforList = inforList;
    }
    @Override
    public int getCount() {
        return inforList.size();
    }

    @Override
    public Object getItem(int position) {
        return inforList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        HolderView holderView;
        if(convertView == null || convertView.getTag() == null){
            view = View.inflate(context,R.layout.route_show_item_layout,null);
            holderView = new HolderView(view);
            view.setTag(holderView);
        }else {
            view = convertView;
            holderView = (HolderView)view.getTag();
        }

        Infor infor = inforList.get(position);

        String time = getTime(infor);
        String data = getData(infor);
        int countdown = getCountDown(infor);
//        boolean option = getOption(infor);
        holderView.timeTextView.setText(time);
        holderView.routeTextView.setText(data);
        //Modify this to be the countdown
        holderView.textPromptView.setText("D-" + countdown);

        return view;
    }

//    private boolean getOption(Infor infor){
//        boolean option = false;
//        if(infor != null){
//            option = infor.getOption();
//        }
//        return option;
//    }
    private String getTime(Infor infor){

        String time = "";
        if(infor != null){
            if(infor.getYear() >=0 ){
                time += infor.getYear()+"/";
            }
            if(infor.getMonth() >=0){
                time += infor.getMonth()+"/";
            }
            if(infor.getDay() >= 0){
                time += infor.getDay()+" ";
            }
            if(infor .getHour()>=0){
                time += infor.getHour()+":";
            }
            if(infor.getMinute() >0){
                time += infor.getMinute();
            }
        }
        return time;
    }

    private int getCountDown(Infor infor){

        int year=0, month=0, day=0;
        int countdown = 0;
        if(infor != null){
            year = infor.getYear();
            month = infor.getMonth();
            day = infor.getDay();
        }

        Date scheduleDate = new Date (year, month, day);
        Date currentDate = new Date(System.currentTimeMillis());
        if(!currentDate.after(scheduleDate)){
            long diff = scheduleDate.getTime() - currentDate.getTime();
            long days = diff / (24*60*60*1000);
            countdown = (int) days -693990;
        }

        return countdown;
    }

    private String getData(Infor infor){
        if(infor != null){
            return infor.getData();
        }
        return "";
    }

    class HolderView{
        TextView timeTextView;
        TextView routeTextView;
        TextView textPromptView;
        public HolderView(View view){
            timeTextView = (TextView) view.findViewById(R.id.time_text);
            routeTextView =(TextView)view.findViewById(R.id.data_text);
            textPromptView = (TextView)view.findViewById(R.id.text_prompt);
        }
    }


}
