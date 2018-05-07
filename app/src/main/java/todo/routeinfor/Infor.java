package todo.routeinfor;

import java.io.Serializable;



public class Infor implements Serializable {

    int year= -1 ;int month=-1; int day=-1;
    int hour= -1; int minute=-1;
    String data = ""; boolean option = false; boolean assignmentOption = false;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data += data;
    }

    public void setDataNew(String data){this.data = data;}

    public boolean getOption(){return option;}

    public void setOption(boolean option){this.option = option;}

    public void setAssignmentOption(boolean option){this.assignmentOption = option;}

    public boolean getAssignmentOption(){return assignmentOption;}

    public int getKey(){
        int result;

        int minute1, hour1, day1, month1, year1;
        minute1 = minute;
        hour1 = hour * 60;
        day1 = day * 24 * 60;
        month1 = month * 31 * 24 * 60;
        year1 = (year - 2017) * 12 * 31 * 24 * 60;
        result = minute1 + hour1 + day1 + month1 + year1;

        return result;
    }

    @Override
    public String toString() {
        return "Infor{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", data='" + data + '\'' +
                '}';
    }
}
