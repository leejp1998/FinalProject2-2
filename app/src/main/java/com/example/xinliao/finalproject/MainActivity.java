package com.example.xinliao.finalproject;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;

import todo.Receiver.AlarmReceiver;
import todo.activity.SeeAllScheduleActivity;
import todo.tool.FileTool;
import todo.activity.AddToDoActivity;
import todo.routeinfor.AllInfor;
import todo.routeinfor.DayInfor;
import todo.routeinfor.Infor;
import todo.tool.TimeTool;
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

    private ImageButton seeAllScheduleButton;


    private ScheduleView scheduleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduleView = (ScheduleView)findViewById(R.id.schedule_view);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        seeAllScheduleButton = (ImageButton) findViewById(R.id.seeAllScheduleButton);
        seeAllScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SeeAllScheduleActivity.class);
                Bundle bundle = new Bundle();
                TimeTool timeTool = new TimeTool();
                Date date = new Date(System.currentTimeMillis());
                bundle.putInt(yearKey, timeTool.getYear(date));
                bundle.putInt(monthKey,timeTool.getMonth(date));
                bundle.putInt(daykey,timeTool.getDay(date));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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

        //Alarm that will give notification at a set time
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)){
            Intent alrmIntent = new Intent(this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alrmIntent, 0);

            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            //This sets the time that alarm goes off. I set it to 8AM
            calendar.set(Calendar.HOUR_OF_DAY, 18);
            calendar.set(Calendar.MINUTE, 39);
            calendar.set(Calendar.SECOND, 1);

            //This sets the repetition to a day (everyday alarm)
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.apply();
        }

        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 3, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000*60*60*24, pendingIntent);
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
        infor.setOption(option); infor.setAssignmentOption(assignment_option);

        if(!infor.getData().equals("")){
            infor.setData(", " + data);
        } else {
            infor.setData(data);
        }

        System.out.println("--->info.setData(data);"+infor.getData());

        dayInfor.addInfor(inforKey,infor);
        infors.addDayRouteList(dayinforKey,dayInfor);

        FileTool.writeAllInfor(getApplicationContext(),infors);

    }
}
