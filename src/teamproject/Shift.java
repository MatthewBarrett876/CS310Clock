package teamproject;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import java.time.LocalTime;
import java.util.Calendar;
 public class Shift {
    
    
    String description;
    Long start;
    Long stop;
    Long lunchStart;
    Long lunchStop;

    int interval;
    int gracePeriod;
    int dock;
    int lunchDeduct;
    
    
    public Shift(String description, Long start, Long stop, Long lunchStart, Long lunchStop, 
            int interval, int gracePeriod, int dock, int lunchDeduct)
    {
        this.description = description;
        this.start = start;
        this.stop = stop;
        this.lunchStart = lunchStart;
        this.lunchStop = lunchStop;
        this.interval = interval;
        this.gracePeriod = gracePeriod;
        this.dock = dock;
        this.lunchDeduct = lunchDeduct;
        
      
    }

   
public String getTime(long s)
{
    GregorianCalendar gc = new GregorianCalendar();
    gc.setTimeInMillis(s);
    
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    Date d = gc.getTime();
    String time = sdf.format(d);
    
    return time;
}


public Long getStart(){return start;}
public Long getStop(){return stop;}
public void Shift1(){}
public void Shift2(){}
public void Interval(){}
public void GradePeriod(){}
public void Dock(){
}
 

@Override
    public String toString() {
        return (description + ": " + getTime(start) + " - " + getTime(stop) + " (510 minutes); Lunch: " 
                + getTime(lunchStart) + " - " + getTime(lunchStop) + " (30 minutes)");
    }
    
 }