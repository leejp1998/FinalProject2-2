package todo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.example.xinliao.finalproject.R;

/**
 * Created by User on 2018-05-06.
 */

public class EditToDoActivity extends AppCompatActivity
        implements View.OnClickListener{

    private TimePicker timePicker;
    private EditText editText;
    private Button noButton;
    private Button yesButton;
    private RadioButton testButton;
    private RadioButton assignmentButton;
    private RadioButton othersButton;
    private boolean yesChecked;
    private boolean noChecked;

    private int year ;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String data;
    private boolean option;
    private boolean assignment;
    private static String yearKey  = "year";
    private static String monthKey = "month";
    private static String daykey   = "day";
    private static String hourkey  = "hour";
    private static String minuteKey = "minute";
    private static String dataKey  = "data";
    private static String optionKey = "option";
    private static String assignmentKey = "assignment_option";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_todo);
        bindView();
        initData();
    }

    private void bindView(){
        timePicker = (TimePicker)findViewById(R.id.time_pick);
        editText = (EditText)findViewById(R.id.route_data_text);
        noButton = (Button)findViewById(R.id.no_button);
        yesButton = (Button)findViewById(R.id.yes_button);
        testButton = (RadioButton)findViewById(R.id.option_test);
        assignmentButton = (RadioButton)findViewById(R.id.option_assignment);
        othersButton = (RadioButton)findViewById(R.id.option_others);
        timePicker.setIs24HourView(true);
        noButton.setOnClickListener(this);
        yesButton.setOnClickListener(this);
    }

    private void initData(){

        Bundle bundle = getIntent().getExtras();
        year  = bundle.getInt(yearKey);
        month = bundle.getInt(monthKey);
        day   = bundle.getInt(daykey);
        hour  = bundle.getInt(hourkey);
        minute= bundle.getInt(minuteKey);
        data  = bundle.getString(dataKey);
        option= bundle.getBoolean(optionKey);
        assignment= bundle.getBoolean(assignmentKey);

        timePicker.setHour(hour);
        timePicker.setMinute(minute);

        if(option){
            testButton.setChecked(true);
        } else if(assignment){
            assignmentButton.setChecked(true);
        } else{
            othersButton.setChecked(true);
        }

        editText.setText(data);

        setResult(10,null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.no_button:
                finish();
                break;
            case R.id.yes_button:
                sureClick();
                break;
        }
    }

    private void sureClick(){
        int currentYear = year;
        int currentMonth = month;
        int currentDay = day;
        String data = editText.getText().toString();


        Intent intent =new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(yearKey,currentYear);
        bundle.putInt(monthKey,currentMonth);
        bundle.putInt(daykey,currentDay);
        bundle.putInt(hourkey,hour);
        bundle.putInt(minuteKey,minute);
        if(data.equals("")){
            bundle.putString(dataKey, "Empty Schedule");
        } else {
            bundle.putString(dataKey, data);
        }
        if(testButton.isChecked()){
            bundle.putBoolean(optionKey, true);
        } else{
            bundle.putBoolean(optionKey, false);
        }
        if(assignmentButton.isChecked()){
            bundle.putBoolean(assignmentKey, true);
        } else{
            bundle.putBoolean(assignmentKey, false);
        }
        intent.putExtras(bundle);
        this.setResult(1,intent);
        this.finish();
    }
}
