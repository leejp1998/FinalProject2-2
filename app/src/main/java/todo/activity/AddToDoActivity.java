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
 * Created by Xin Liao on 4/29/2018.
 */

public class AddToDoActivity extends AppCompatActivity
        implements View.OnClickListener {

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
    private String data;
    private static String yearKey  = "year";
    private static String monthKey = "month";
    private static String daykey   = "day";
    private static String hourkey  = "hour";
    private static String minuteKey = "minute";
    private static String dataKey  = "data";
    private static String optionKey = "option";


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

        editText.setText(year+"/"+month+"/"+day);

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
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
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
        bundle.putString(dataKey,data);
        if(testButton.isSelected()){
            bundle.putBoolean(optionKey, true);
        } else{
            bundle.putBoolean(optionKey, false);
        }
        intent.putExtras(bundle);
        this.setResult(0,intent);
        this.finish();
    }
}
