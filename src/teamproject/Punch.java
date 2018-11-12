package teamproject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.Timestamp;

public class Punch {
    
    //instance fields
    private String note;
    private String badgeid;
    private int terminalid;
    private int punchtypeid;
    private long originaltimestamp;
    private long adjustedtimestamp;
    private int id = 0;
    public static final int CLOCK_OUT = 0;
    public static final int CLOCK_IN = 1;
    public static final int TIME_OUT = 2;
    //constructor
    
    public Punch(Badge badge, int terminalid, int punchtypeid) {
        this.badgeid = badge.getId();
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
        
        this.originaltimestamp = (new GregorianCalendar()).getTimeInMillis();
        
        
        
    }
    
    
    public Punch(Badge badge, int terminalid, int punchtypeid, long originalts) {
        this.badgeid = badge.getId();
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
        
        this.originaltimestamp = originalts;
        
    }
    
    
    public String getBadgeid() {return badgeid;}

    //methods
    public void setBadgeid(String badgeid) 
    {
        this.badgeid = badgeid;
    }

    public int getTerminalid() 
    {
        return terminalid;
    }
    
   
    public int getPunchtypeid() {return punchtypeid;}
    public long getOriginaltimestamp() {return originaltimestamp;}
    public long getAdjustedtimestamp() {return adjustedtimestamp;}
    public int getID() {return id;}
   
    public void setTerminalid(int t) {
        this.terminalid = t;
    }
    public void setPunchtypeid(int p) {
        this.punchtypeid = p;
    }
    public void setOriginaltimestamp(Long ts) {
        this.originaltimestamp = ts;
    }
    public void setAdjustedtimestamp(Long a) {
        this.adjustedtimestamp = a;
    }
    public void setID(int i) {
        this.id = i;
    }
    
     public void adjust(Shift s)
    {
       Calendar gc = Calendar.getInstance();
       
       int interval = s.getInterval();
       int gracePeriod = s.getGracePeriod();
       
       
       setOriginaltimestamp(originaltimestamp);
       
       gc.setTimeInMillis(originaltimestamp);
       
       // Set the original time stamp
       int originalMonth = gc.get(Calendar.MONTH);
       originalMonth ++;
       
       int originalDay = gc.get(Calendar.DAY_OF_MONTH);
       int originalYear = gc.get(Calendar.YEAR);
       int nameOfDay = gc.get(Calendar.DAY_OF_WEEK);
       
       int originalHour = gc.get(Calendar.HOUR_OF_DAY);
       int originalMinute = gc.get(Calendar.MINUTE);
       int originalSecond = gc.get(Calendar.SECOND);
       
       
       // Get the start times
       gc.setTimeInMillis(s.getStart());
       int startTimeHour = gc.get(Calendar.HOUR_OF_DAY);
       int startTimeMinute = gc.get(Calendar.MINUTE);
 
    
       gc.setTimeInMillis(s.getLunchStart());
       int lunchStartHour = gc.get(Calendar.HOUR_OF_DAY);
    
       gc.setTimeInMillis(s.getLunchStop());
       int lunchStopMinute = gc.get(Calendar.MINUTE);
        
       gc.setTimeInMillis(s.getStop()); 
       int stopTimeHour = gc.get(Calendar.HOUR_OF_DAY);
       int stopTimeMinute = gc.get(Calendar.MINUTE);
        
       
       // Sets the parameters for the interval checks
       
        int intervalAmount = (60 / interval);
        
        int intervalCheck = (interval / 2);
        
        
        
        // Shift Start
        
        if(nameOfDay == Calendar.SATURDAY || nameOfDay == Calendar.SUNDAY
                    && originalHour != lunchStartHour)
        {
                
                if(punchtypeid == CLOCK_IN)
                {
                    
                    for(int i = 1; i < intervalAmount; i++)
                    {
                        if(originalMinute == (60 - (interval * i)))
                        {
                            setAdjustedtimestamp(originaltimestamp);

                            note = "(None)";
                            break;

                        }
                        
                        // From 60 to 53
                        else if(originalMinute >= (60 - intervalCheck))
                        {
                            originalMinute = 0;
                            originalSecond = 0;
                            originalHour = originalHour + 1;
                            
                            note = "(Interval Round)";
                            
                            break;
                        }
                        
                        // From 52 to 45
                        else if(originalMinute <= (60 - interval * i) + intervalCheck 
                                    && originalMinute >= (60 - interval * i) - intervalCheck)
                        {
                            originalMinute = (60 - interval * i);
                            originalSecond = 0;
                            
                            note = "(Interval Round)";
                            
                            break;
                        }

                        
                    }
                    
                    String t = (originalYear + "-" + originalMonth + "-" 
                                        + originalDay + " " + originalHour  + ":" 
                                        + originalMinute  + ":" + originalSecond);

                    Timestamp updatedTimestamp = Timestamp.valueOf(t);

                    long tsTime1 = updatedTimestamp.getTime();

                    setAdjustedtimestamp(tsTime1);
                    gc.setTimeInMillis(adjustedtimestamp);


                }
                
                else if(punchtypeid == CLOCK_OUT)
                {
                    for(int i = 1; i < intervalAmount + 1; i++)
                        {   
                            if(i == 1 && originalMinute >= (60 - interval * i))
                                {
                                    originalMinute = 0;
                                    originalSecond = 0;
                                    originalHour = originalHour + 1;

                                    note = "(Interval Round)";

                                    break;
                                }

                            else if(originalMinute <= (60 - (interval * i) + intervalCheck) 
                                            && originalMinute >= (60 - (interval * i - intervalCheck)))
                                {
                                    originalMinute = (60 - interval);
                                    originalSecond = 0;

                                    note = "(Interval Round)";

                                    break;

                                }

                            else if(60 - (interval * i) != 0 && originalMinute != (60 - (interval * i))
                                        && originalMinute <= (60 - (interval * i) + intervalCheck)
                                        && originalMinute >= (60 - (interval * i) - intervalCheck))
                            {
                                originalMinute = (60 - (interval * i));
                                originalSecond = 0;

                                note = "(Interval Round)";
                                break;

                            }

                            else if(originalMinute > 60 - (interval * i)
                                        && originalMinute < 60 - (interval * i) + intervalCheck)
                            {
                                originalMinute = (60 - (interval * i));
                                originalSecond = 0;

                                note = "(Interval Round)";
                                break;
                                
                            }
                                
                                
                            else if(60 - (interval * i) < 0)
                            {
                                originalMinute = 0;
                                originalSecond = 0;

                                note = "(Interval Round)";
                                break;
                            }
                            
                            else if(originalMinute == (60 - (interval * i)))
                            {
                                setAdjustedtimestamp(originaltimestamp);
                                
                                note = "(None)";
                                break;
                            }
                                    
                            
                        }

                        String t = (originalYear + "-" + originalMonth + "-" 
                                        + originalDay + " " + originalHour  + ":" 
                                        + originalMinute  + ":" + originalSecond);

                        Timestamp updatedTimestamp = Timestamp.valueOf(t);

                        long tsTime1 = updatedTimestamp.getTime();

                        setAdjustedtimestamp(tsTime1);
                        gc.setTimeInMillis(adjustedtimestamp);
                }
                
                
        }
            
        else if(nameOfDay != Calendar.SATURDAY && nameOfDay != Calendar.SUNDAY
                    && originalHour != lunchStartHour)
    {
        
        if(punchtypeid == CLOCK_IN)
        {
                if(startTimeMinute == 0)
                {

                    // Check an interval 1 hour before the startTime
                    if(originalHour == (startTimeHour - 1))
                    {
                        for(int i = 1; i < intervalAmount + 1; i++)
                        {    
                            if(i == 1) 
                            {
                                // Within the interval before the shift starts
                                if (originalMinute >= (60 - interval))
                                {
                                    originalHour = (originalHour + 1);
                                    originalMinute = 0;
                                    originalSecond = 0;

                                    note = "(Shift Start)";
                                    
                                    break;

                                }

                            }

                            else
                            {
                                // Past the interval before the shift starts 

                                if(originalMinute <= (60 - (interval * i) - 1)
                                        && (originalMinute >= (60 - (interval * i)) - intervalCheck))

                                {

                                    originalMinute = (60 - (interval * i));
                                    originalSecond = 0;

                                    note = "(Interval Round)";

                                    break;

                                }

                                else if((60 - (interval * i) != 0)
                                        && originalMinute <= (60 - (interval * i) + intervalCheck) 
                                            && originalMinute >= (60 - (interval * i) - intervalCheck))
                                {

                                    originalMinute = (60 - (interval * i) + interval);
                                    originalSecond = 0;

                                    note = "(Interval Round)";

                                    break;
                                }

                                else if(60 - (interval * i) < 0)
                                {
                                    originalMinute = 0;
                                    originalSecond = 0;

                                    note = "(Interval Round)";

                                    break;

                                }
                                
                                else if(originalMinute == (60 - (interval * i)))
                                {
                                    setAdjustedtimestamp(originaltimestamp);

                                    note = "(None)";

                                }

                            }

                        }

                            String t = (originalYear + "-" + originalMonth + "-" 
                                        + originalDay + " " + originalHour  + ":" 
                                        + originalMinute  + ":" + originalSecond);

                            Timestamp updatedTimestamp = Timestamp.valueOf(t);

                            long tsTime1 = updatedTimestamp.getTime();

                            setAdjustedtimestamp(tsTime1);
                            gc.setTimeInMillis(adjustedtimestamp);

                    }


                    // Check for inteveral less than the hour before the shift 

                    else if(originalHour < startTimeHour - 1 || originalHour > startTimeHour)
                    {
                         for(int i = 1; i < intervalAmount + 1; i++)
                        {   
                            if(i == 1 && originalMinute >= (60 - intervalCheck))
                                {
                                    originalMinute = 0;
                                    originalSecond = 0;
                                    originalHour = originalHour + 1;

                                    note = "(Interval Round)";

                                    break;
                                }

                            else if((i == 1) && originalMinute <= (60 - (interval + intervalCheck)) 
                                            && originalMinute >= (60 - (interval - intervalCheck)))
                                {
                                    originalMinute = (60 - interval);
                                    originalSecond = 0;

                                    note = "(Interval Round)";

                                    break;

                                }

                            else if(60 - (interval * i) != 0
                                        && originalMinute <= (60 - (interval * i) + intervalCheck)
                                        && originalMinute >= (60 - (interval * i) - (intervalCheck - 1)))
                            {
                                originalMinute = (60 - (interval * i));
                                originalSecond = 0;

                                note = "(Interval Round)";

                            }

                            else if(60 - (interval * i) < 0)
                            {
                                originalMinute = 0;
                                originalSecond = 0;

                                note = "(Interval Round)";

                            }
                            
                            else if(originalMinute == (60 - (interval * i)))
                            {
                                originalSecond = 0;
                                setAdjustedtimestamp(originaltimestamp);
                                
                                note = "(None)";
                                
                            }
                                    
                            

                        }

                        String t = (originalYear + "-" + originalMonth + "-" 
                                        + originalDay + " " + originalHour  + ":" 
                                        + originalMinute  + ":" + originalSecond);

                        Timestamp updatedTimestamp = Timestamp.valueOf(t);

                        long tsTime1 = updatedTimestamp.getTime();

                        setAdjustedtimestamp(tsTime1);
                        gc.setTimeInMillis(adjustedtimestamp);


                    }


                    // Check within the first hour of the shift start
                    else if(originalHour == startTimeHour)
                    {
                        // Grace Period
                        if(originalMinute <= gracePeriod)
                        {
                            originalMinute = startTimeMinute;
                            originalSecond = 0;
                            
                            note = "(Shift Start)";
                            
                        }
                        
                        else if(originalMinute <= interval && originalMinute > gracePeriod)
                        {
                            
                            originalMinute = interval;
                            originalSecond = 0;
                            note = "(Shift Dock)";
                            
                        }
                        
                        // Check for either even interval or for an interval round
                        for(int i = 1; i < intervalAmount + 1; i++)
                        {
                            if(i == 1 && originalMinute > interval && originalMinute <= (interval + intervalCheck))
                            {
                                originalMinute = interval;
                                originalSecond = 0;
                                
                                note = "(Interval Round)";
                                break;
                            }
                            
                            else if(i != 1 && originalMinute >= (interval * i) - intervalCheck
                                    && originalMinute <= (interval * i) + intervalCheck)
                            {
                                originalMinute = (interval * i);
                                originalSecond = 0;
                                
                                note = "(Interval Round)";
                                break;
                            }
                            
                            else if(originalMinute >= (60 - interval) && (interval * i) < 60)
                            {
                                originalHour = originalHour++;
                                originalMinute = 0;
                                originalSecond = 0;
                                
                                note = "(Interval Round)";
                                break;
                            }
                            
                            else if(i != 1 && (interval * i < 60) && originalMinute == (interval * i))
                            {
                                setAdjustedtimestamp(originaltimestamp);
                                
                                originalSecond = 0;
                                
                                note = "(None)";
                                
                            }
                        }
                        
                            String t = (originalYear + "-" + originalMonth + "-" 
                                            + originalDay + " " + originalHour  + ":" 
                                            + originalMinute  + ":" + originalSecond);

                            Timestamp updatedTimestamp = Timestamp.valueOf(t);

                            long tsTime1 = updatedTimestamp.getTime();

                            setAdjustedtimestamp(tsTime1);
                            gc.setTimeInMillis(adjustedtimestamp);
                        
                    }

                }
            
        }
        
        
        // Shift Stop
        else if(punchtypeid == CLOCK_OUT)
        {
            
            if(stopTimeMinute != 0)
            {
                
                   if(originalHour == stopTimeHour)
                   {
                        for(int i = 1; i < intervalAmount + 1; i++)
                        {
                            if(originalMinute == stopTimeMinute + (interval * i))
                            {
                                setAdjustedtimestamp(originaltimestamp);

                                originalSecond = 0;
                                    note = "(None)";
                                    
                                    break;
                            }

                        }
                
                        // Grace Period
                        if(originalMinute >= stopTimeMinute - gracePeriod 
                                && originalMinute < stopTimeMinute)
                        {
                            originalMinute = stopTimeMinute;
                            originalSecond = 0;

                            note = "(Shift Stop)";
                            
                        }
                        
                        else if(originalMinute >= stopTimeMinute
                                && originalMinute <= stopTimeMinute + interval)
                        {
                            originalMinute = stopTimeMinute;
                            originalSecond = 0;

                            note = "(Shift Stop)";
                            
                        }
                        
                        // Dock
                        else if(originalMinute <= (stopTimeMinute - (gracePeriod - 1) )
                                && originalMinute >= (stopTimeMinute - interval))
                            {
                                originalMinute = (stopTimeMinute - interval);
                                originalSecond = 0;

                                note = "(Shift Dock)";

                            }

              
                
                        else if(originalMinute < (stopTimeMinute - interval) 
                                    && originalMinute >= (stopTimeMinute - interval) - intervalCheck)
                        {
                            originalMinute = stopTimeMinute - interval;
                            
                             originalSecond = 0;
                            
                            note = "(Interval Round)";
                            
                        }
                        
                        else if(originalMinute < (stopTimeMinute - interval) - intervalCheck)
                        {
                         
                            originalMinute = 0;
                            originalSecond = 0;
                            note = "(Interval Round)";
                        }
                        
                        
                        
                                    
                        String t = (originalYear + "-" + originalMonth + "-" 
                                        + originalDay + " " + originalHour  + ":" 
                                        + originalMinute  + ":" + originalSecond);

                        Timestamp updatedTimestamp = Timestamp.valueOf(t);

                        long tsTime1 = updatedTimestamp.getTime();

                        setAdjustedtimestamp(tsTime1);
                        gc.setTimeInMillis(adjustedtimestamp);

                        
                
                }
                
                
                else if(originalHour < stopTimeHour)
                {
                    for(int i = 1; i < intervalAmount + 1; i++)
                        {   
                            if(i == 1 && originalMinute >= (60 - interval * i))
                                {
                                    originalMinute = 0;
                                    originalSecond = 0;
                                    originalHour = originalHour + 1;

                                    note = "(Interval Round)";

                                    break;
                                }

                            else if(originalMinute <= (60 - (interval * i) + intervalCheck) 
                                            && originalMinute >= (60 - (interval * i - intervalCheck)))
                                {
                                    originalMinute = (60 - interval);
                                    originalSecond = 0;

                                    note = "(Interval Round)";

                                    break;

                                }

                            else if(60 - (interval * i) != 0 && originalMinute != (60 - (interval * i))
                                        && originalMinute <= (60 - (interval * i) + intervalCheck)
                                        && originalMinute >= (60 - (interval * i) - (intervalCheck - 1)))
                            {
                                originalMinute = (60 - (interval * i));
                                originalSecond = 0;

                                note = "(Interval Round)";
                                break;

                            }

                            else if(60 - (interval * i) < 0)
                            {
                                originalMinute = 0;
                                originalSecond = 0;

                                note = "(Interval Round)";
                                break;
                            }
                            
                            else if(originalMinute == (60 - (interval * i)))
                            {
                                setAdjustedtimestamp(originaltimestamp);
                                originalSecond = 0;
                                note = "(None)";
                                break;
                            }
                                    
                            
                        }

                        String t = (originalYear + "-" + originalMonth + "-" 
                                        + originalDay + " " + originalHour  + ":" 
                                        + originalMinute  + ":" + originalSecond);

                        Timestamp updatedTimestamp = Timestamp.valueOf(t);

                        long tsTime1 = updatedTimestamp.getTime();

                        setAdjustedtimestamp(tsTime1);
                        gc.setTimeInMillis(adjustedtimestamp);


                }
                
                
                
                 else if(originalHour > startTimeHour)
                    {
                         for(int i = 1; i < intervalAmount + 1; i++)
                        {   
                            if(i == 1 && originalMinute >= (60 - intervalCheck))
                                {
                                    originalMinute = 0;
                                    originalSecond = 0;
                                    originalHour = originalHour + 1;

                                    note = "(Interval Round)";

                                    break;
                                }

                            else if((i == 1) && originalMinute <= (60 - (interval + intervalCheck)) 
                                            && (originalMinute != (60 - interval)) 
                                            && originalMinute >= (60 - interval) - intervalCheck)
                                {
                                    originalMinute = (60 - interval);
                                    originalSecond = 0;

                                    note = "(Interval Round)";

                                    break;

                                }

                            else if(60 - (interval * i) != 0
                                        && originalMinute < stopTimeMinute - 5
                                        && originalMinute <= (60 - (interval * i) + intervalCheck)
                                        && originalMinute >= (60 - (interval * i) - intervalCheck - 1))
                            {
                                originalMinute = (60 - (interval * i));
                                originalSecond = 0;

                                note = "(Interval Round)"; 
                                break;

                            }

                            else if(60 - (interval * i) < 0)
                            {
                                originalMinute = 0;
                                originalSecond = 0;

                                note = "(Interval Round)";
                                break;
                            }
                            
                            else if(originalMinute == (60 - (interval * i)))
                            {
                                setAdjustedtimestamp(originaltimestamp);
                                originalSecond = 0;
                                note = "(None)";
                                break;
                            }
                                    
                        }

                        String t = (originalYear + "-" + originalMonth + "-" 
                                        + originalDay + " " + originalHour  + ":" 
                                        + originalMinute  + ":" + originalSecond);

                        Timestamp updatedTimestamp = Timestamp.valueOf(t);

                        long tsTime1 = updatedTimestamp.getTime();

                        setAdjustedtimestamp(tsTime1);
                        gc.setTimeInMillis(adjustedtimestamp);


                    }


            }
                
                
        }
            
    }
        
        
        // Check for lunch times
        else if(nameOfDay != Calendar.SATURDAY && nameOfDay != Calendar.SUNDAY
                    && originalHour == lunchStartHour)
        {
            
            if(punchtypeid == CLOCK_OUT && originalMinute <= (interval - 1))
            {
                originalMinute = 0;
                originalSecond = 0;
                originalHour = lunchStartHour;
                
                note = "(Lunch Start)";
                
            }
            
            else if(punchtypeid == CLOCK_IN 
                        && originalMinute >= (stopTimeMinute - interval))
            {
                originalMinute = lunchStopMinute;
                originalSecond = 0;
                
                note = "(Lunch Stop)";
                
            }
            
            String t = (originalYear + "-" + originalMonth + "-" 
                            + originalDay + " " + originalHour  + ":" 
                            + originalMinute  + ":" + originalSecond);

                        Timestamp updatedTimestamp = Timestamp.valueOf(t);

                        long tsTime1 = updatedTimestamp.getTime();

                        setAdjustedtimestamp(tsTime1);
                        gc.setTimeInMillis(adjustedtimestamp);


        }
    
    
    }
    
    
    
    public String printOriginalTimestamp() {
        String p = null;
        if (getPunchtypeid() == CLOCK_OUT) {
            p = "#" + badgeid + " " + "CLOCKED OUT: " + getDateTime(originaltimestamp).toUpperCase();
        }
        if (getPunchtypeid() == CLOCK_IN) {
            p = "#" + badgeid + " " + "CLOCKED IN: " + getDateTime(originaltimestamp).toUpperCase();
        }
        if (getPunchtypeid() == TIME_OUT) {
            p = "#" + badgeid + " " + "TIMED OUT: " + getDateTime(originaltimestamp).toUpperCase();
        }
        return p;
         
                
    }
    
    
   public String printAdjustedTimestamp() {
        String p = null;
        if (getPunchtypeid() == CLOCK_OUT) {
            
            p = "#" + badgeid + " " + "CLOCKED OUT: " + getDateTime(adjustedtimestamp).toUpperCase() + " " + note;
        }
        if (getPunchtypeid() == CLOCK_IN) {
            p = "#" + badgeid + " " + "CLOCKED IN: " + getDateTime(adjustedtimestamp).toUpperCase() + " " + note;
        }
        if (getPunchtypeid() == TIME_OUT) {
            p = "#" + badgeid + " " + "TIMED OUT: " + getDateTime(adjustedtimestamp).toUpperCase() + " " + note;
        }
        return p;
         
                
    } 
    
    public String getDateTime(long s)
{
    GregorianCalendar gc = new GregorianCalendar();
    gc.setTimeInMillis(s);
    
    SimpleDateFormat sdf = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
    java.util.Date d = gc.getTime();
    String time = sdf.format(d);
    
    return time;
}
    
    
    
}
