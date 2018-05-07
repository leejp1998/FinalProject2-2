package todo.view;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.xinliao.finalproject.R;

import todo.activity.AddToDoActivity;
import todo.activity.EditToDoActivity;

/**
 * Created by Xin Liao on 4/29/2018.
 */

public class RouteShowDeleteView extends ListView implements View.OnTouchListener{

    private GestureDetector detector;                                    //手势
    private GestureDetector.SimpleOnGestureListener gestureListener;     //手势监听
    private View buttonView;                                             //删除按钮的View
    private ViewGroup selectItemGroup;                                   //选择的item
    private int selectItem;                                              //选择的Item
    private DelectListener delectListener = null;
    private EditRouteListener editRouteListener = null;

    private static String yearKey  = "year";
    private static String monthKey = "month";
    private static String daykey   = "day";
    private static String hourkey  = "hour";
    private static String minuteKey = "minute";
    private static String dataKey  = "data";
    private static String optionKey = "option";
    private static String assignmentKey = "assignment_option";

    private boolean isShowButton =false;
    public RouteShowDeleteView(Context context) {
        super(context);
        init(context);
    }

    public RouteShowDeleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RouteShowDeleteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context){

        setOnTouchListener(this);
        gestureListener = new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onDown(MotionEvent e) {
                if(!isShowButton){
                    selectItem = pointToPosition((int)e.getX(),(int)e.getY());//得到选择listView中的item
                }
                return false;
            }

            //Fling to the left = delete
            //Fling to the right = edit
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if(!isShowButton && velocityX < Math.abs(velocityY)
                        && Math.abs(velocityX) >getWidth()/3 ){
                    buttonView = LayoutInflater.from(context).inflate(R.layout.button_layout,null);
                    buttonView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectItemGroup.removeView(buttonView);
                            buttonView = null;
                            isShowButton = false;
                            if(delectListener != null){
                                delectListener.delect(selectItem);
                            }
                        }
                    });
                    selectItemGroup = (ViewGroup) getChildAt(selectItem-getFirstVisiblePosition());
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params.addRule(RelativeLayout.CENTER_VERTICAL);
                    selectItemGroup.addView(buttonView,params);
                    isShowButton = true;
                }
                //EDIT FUNCTION NEEDS TO BE MODIFIED HERE
                else if (!isShowButton && velocityX > Math.abs(velocityY)
                        && Math.abs(velocityX) > getWidth() / 3){
                    buttonView = LayoutInflater.from(context).inflate(R.layout.edit_button_layout, null);
                    buttonView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, EditToDoActivity.class);
                            Bundle bundle = new Bundle();


                            int year = 2018;
                            int month = 5;
                            int day = 7;
                            int hour = 12;
                            int minute = 23;
                            String data = "test";
                            boolean option = false;
                            boolean assignment = false;

                            bundle.putInt(yearKey, year);
                            bundle.putInt(monthKey, month);
                            bundle.putInt(daykey, day);
                            bundle.putInt(hourkey, hour);
                            bundle.putInt(minuteKey, minute);
                            bundle.putString(dataKey, data);
                            bundle.putBoolean(optionKey, option);
                            bundle.putBoolean(assignmentKey, assignment);
                            intent.putExtras(bundle);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });

                    selectItemGroup = (ViewGroup) getChildAt(selectItem-getFirstVisiblePosition());
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    params.addRule(RelativeLayout.CENTER_VERTICAL);
                    selectItemGroup.addView(buttonView,params);
                    isShowButton = true;
                }
                return false;
            }

//            @Override
//            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                if(!isShowButton && Math.abs(velocityX) > Math.abs(velocityY)
//                        && Math.abs(velocityX) >getWidth()/3 ){
//                    buttonView = LayoutInflater.from(context).inflate(R.layout.button_layout,null);
//                    buttonView.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            selectItemGroup.removeView(buttonView);
//                            buttonView = null;
//                            isShowButton = false;
//                            if(delectListener != null){
//                                delectListener.delect(selectItem);
//                            }
//                        }
//                    });
//                    //getChileAt获得的是屏幕上显示的Item序号
//                    selectItemGroup = (ViewGroup) getChildAt(selectItem-getFirstVisiblePosition());
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                    params.addRule(RelativeLayout.CENTER_VERTICAL);
//                    selectItemGroup.addView(buttonView,params);
//                    isShowButton = true;
//                }
//                return false;
//            }
        };
        detector = new GestureDetector(context,gestureListener);//设置手势监听
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(isShowButton){
            selectItemGroup.removeView(buttonView);
            isShowButton = false;
            buttonView = null;
            return false;
        }else
            return detector.onTouchEvent(event);
    }

    public interface DelectListener{
        void delect(int selectItem);
    }

    public void setDelectListener(DelectListener delectListener){
        this.delectListener = delectListener;
    }

    public interface EditRouteListener{
        void editRoute(int selectItem);
    }

    public void setEditRouteListener(EditRouteListener editRouteListener){
        this.editRouteListener = editRouteListener;
    }
}