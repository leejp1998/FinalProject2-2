package com.example.xinliao.finalproject;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import todo.tool.FileTool;
import todo.activity.AddToDoActivity;
import todo.routeinfor.AllInfor;
import todo.routeinfor.DayInfor;
import todo.routeinfor.Infor;
import todo.view.ScheduleView;


public class MainActivity extends AppCompatActivity {

    private static String yearKey  = "year";
    private static String monthKey = "month";
    private static String daykey   = "day";
    private static String hourkey  = "hour";
    private static String minuteKey = "minute";
    private static String dataKey  = "data";
    private static String optionKey = "option";
    private static String assignmentKey = "assignment_option";


    private ScheduleView scheduleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduleView = (ScheduleView)findViewById(R.id.schedule_view);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scheduleView.setAddRouteListener(new ScheduleView.AddRouteListener() {
            @Override
            public void addRoute(View view){
                if(view == null){
                    Toast.makeText(getApplicationContext(),"Select a Day:",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), AddToDoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(yearKey,scheduleView.getCurrentYear());
                bundle.putInt(monthKey,scheduleView.getCurrentMonth());
                bundle.putInt(daykey,scheduleView.getSelectDay());
                intent.putExtras(bundle);
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("--->"+requestCode+" "+resultCode);
        if(requestCode == 100 && resultCode == 0){
            System.out.println("--->+"+requestCode+" "+resultCode+data);
            Bundle bundle = data.getExtras();
            int year = bundle.getInt(yearKey);
            int month = bundle.getInt(monthKey);
            int day = bundle.getInt(daykey);
            int hour = bundle.getInt(hourkey);
            int minute = bundle.getInt(minuteKey);
            String mydata = bundle.getString(dataKey);
            boolean option = bundle.getBoolean(optionKey);
            boolean assignment_option = bundle.getBoolean(assignmentKey);
            System.out.println("--->my"+year+" "+month+" "+day+" "+hour+" "+minute+" "+mydata);
            try {
                saveRouteData(year,month,day,hour,minute,mydata,option, assignment_option);
                scheduleView.mySelect(year,month,day);
                System.out.println("--->++"+requestCode+" "+resultCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //保存文件
    private void saveRouteData(int year,int month,int day
            ,int hour,int minute,String data, boolean option, boolean assignment_option) throws Exception {

        String inforKey = FileTool.getInforKey(hour,minute);
        String dayinforKey = FileTool.getDayInforKey(year,month,day);

        AllInfor infors = FileTool.getAllInfor(getApplicationContext());
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
        infor.setData(data); infor.setOption(option); infor.setAssignmentOption(assignment_option);

        System.out.println("--->info.setData(data);"+infor.getData());

        dayInfor.addInfor(inforKey,infor);
        infors.addDayRouteList(dayinforKey,dayInfor);

        FileTool.writeAllInfor(getApplicationContext(),infors);

    }
}
