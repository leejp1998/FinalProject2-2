package todo.tool;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import todo.routeinfor.AllInfor;
import todo.routeinfor.DayInfor;
import todo.routeinfor.Infor;



public class FileTool  {

    private static final String Filename = "myschdule.route";

    //读取整个行程
    public static AllInfor getAllInfor(Context context){
        AllInfor allInfor = null;
        try {
            allInfor =readAllInfor(context);
        } catch (Exception e) {
            allInfor = new AllInfor();
            writeAllInfor(context,allInfor);
            allInfor = readAllInfor(context);
        }

        return allInfor;
    }
    //写入所有行程
    public static void writeAllInfor(Context context,AllInfor infors){
        try {
            FileOutputStream fos = context.openFileOutput(Filename,context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(infors);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获得所有行程
    public static AllInfor readAllInfor(Context context){

        AllInfor allInfor = null;
        try {
            FileInputStream fis = context.openFileInput(Filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            allInfor = (AllInfor)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allInfor;
    }

    //获得天的行程
    public static DayInfor getDayInfor(Context context,int year, int month , int day){

        DayInfor dayInfor =null;
        String key = getDayInforKey(year,month,day);
        AllInfor infors = getAllInfor(context);
        if(infors != null){
            dayInfor = infors.getDayRouteList(key);
        }
        return dayInfor;
    }

    public static List<Infor> getDayInforList(Context context, int year, int month , int day){
        List<Infor > list = new ArrayList<Infor>();
        if(getDayInfor(context,year,month,day) != null){
            list = getDayInfor(context,year,month,day).getInforList();
        }
        return list;
    }

    public static ArrayList<Infor> getAllInfor(Context context, int year, int month, int day){
        ArrayList<DayInfor> dayInfor = new ArrayList<DayInfor> ();
        ArrayList<Infor> list = new ArrayList<Infor>();
        String key = getDayInforKey(year,month,day);
        AllInfor infors = getAllInfor(context);
        if(infors!=null){
            dayInfor = infors.getAllDayRouteList(key);
        }
        for (int i=0; i<dayInfor.size(); i++){
            for (int j=0; j< dayInfor.get(i).getDayInforToInfor().size(); j++){
                list.add(dayInfor.get(i).getDayInforToInfor().get(j));
            }
        }
        return list;

    }

    public static ArrayList<Infor> sortByDate(ArrayList<Infor> arrayList){
        ArrayList <Infor> result = new ArrayList<Infor>();
        final int SIZE = arrayList.size();

        Infor [] mylist = new Infor [SIZE];
        for(int i=0; i<SIZE; i++){
            mylist[i] = arrayList.get(i);
        }

        if(SIZE != 0){
            for(int i=0; i < mylist.length -1; i++){
                int index = i;
                for(int j=i+1; j < mylist.length; j++){
                    if(mylist[j].getKey() < mylist[index].getKey()){
                        index = j;
                    }
                }
                Infor holder = mylist[index];
                mylist[index] = mylist[i];
                mylist[i] = holder;
            }
        }
        if(SIZE!=0) {
            for (int i = 0; i < SIZE; i++) {
                result.add(mylist[i]);
            }
        }
        return result;
    }

    //判断当前是否有行程
    public static boolean isHasRoute(Context context,int year,int month,int day){
        DayInfor dayInfor   = getDayInfor(context,year,month,day);
        if(dayInfor != null && dayInfor.getInforList().size()>0){
            return true;
        }
        return  false;
    }

//    public static boolean isTestRoute(Context context, int year, int month, int day){
//        DayInfor dayInfor = getDayInfor(context, year, month, day);
//        if(dayInfor.getInfor())
//    }

    public static void removeInfor(Context context,int year
            ,int month,int day,int hour,int minute){
        String dayInforKey = getDayInforKey(year,month,day);
        String inforKey = getInforKey(hour,minute);
        AllInfor allInfor = getAllInfor(context);
        DayInfor dayInfor = allInfor.getDayRouteList(dayInforKey);
        dayInfor.remove(inforKey);

        allInfor.addDayRouteList(dayInforKey,dayInfor);
        writeAllInfor(context,allInfor);
    }


    public static String getDayInforKey(int year, int month, int day){
        int key = (int)(year *1300+month*100+day*1);
        return key+"";
    }

    public static String getInforKey(int hour , int minute){
        int key = (int)((hour*1+minute*0.01)*100);
        String timekey = key+"";
        return  timekey;
    }


}
