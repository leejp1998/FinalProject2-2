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

    public boolean getOption(){return option;}

    public void setOption(boolean option){this.option = option;}

    public void setAssignmentOption(boolean option){this.assignmentOption = option;}

    public boolean getAssignmentOption(){return assignmentOption;}

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
