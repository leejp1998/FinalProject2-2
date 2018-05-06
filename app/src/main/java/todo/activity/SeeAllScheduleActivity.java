package todo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.xinliao.finalproject.R;

import java.util.ArrayList;
import java.util.List;

import todo.adapter.RouteAdapter;
import todo.routeinfor.AllInfor;
import todo.routeinfor.Infor;
import todo.tool.FileTool;
import todo.view.ScheduleView;

/**
 * Created by User on 2018-05-04.
 */

public class SeeAllScheduleActivity extends AppCompatActivity{

    private static String yearKey  = "year";
    private static String monthKey = "month";
    private static String daykey   = "day";

    private ListView listView;
    private ScheduleView scheduleView;
    ArrayList<Infor> schedule_list;

    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_schedule_layout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        year = bundle.getInt(yearKey);
        month = bundle.getInt(monthKey);
        day = bundle.getInt(daykey);

        //String dayinforKey

        AllInfor infors = FileTool.getAllInfor(getApplicationContext());
        //infors.addDayRouteList(dayinforKey, dayInfor);
        schedule_list = FileTool.getAllInfor(this, year, month, day);
        schedule_list = FileTool.sortByDate(schedule_list);
        RouteAdapter adapter = new RouteAdapter(this, schedule_list);
        listView = (ListView) findViewById(R.id.all_schedule_list_view);
        scheduleView = (ScheduleView)findViewById(R.id.schedule_view);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
