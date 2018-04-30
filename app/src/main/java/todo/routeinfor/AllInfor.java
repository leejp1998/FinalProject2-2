package todo.routeinfor;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Xin Liao on 4/29/2018.
 */

public class AllInfor implements Serializable {
    HashMap<String ,DayInfor> map = new HashMap<String ,DayInfor>();
    //timea是时间 xxxx年xx月xx日
    public void addDayRouteList(String time, DayInfor dayInfor){
        map.put(time,dayInfor);
    }

    public DayInfor getDayRouteList(String time){
        return map.get(time);
    }
}
