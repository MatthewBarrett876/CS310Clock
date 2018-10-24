package teamproject;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
public class Punch {
    
    //instance fields
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
    
    public String printOriginalTimestamp() {
        String p = null;
        if (getPunchtypeid() == CLOCK_OUT) {
            p = "#" + badgeid + " " + "CLOCKED OUT: " + getTime(originaltimestamp).toUpperCase();
        }
        if (getPunchtypeid() == CLOCK_IN) {
            p = "#" + badgeid + " " + "CLOCKED IN: " + getTime(originaltimestamp).toUpperCase();
        }
        if (getPunchtypeid() == TIME_OUT) {
            p = "#" + badgeid + " " + "TIMED OUT: " + getTime(originaltimestamp).toUpperCase();
        }
        return p;
         
                
    }
    
    
    public String getTime(long s)
{
    GregorianCalendar gc = new GregorianCalendar();
    gc.setTimeInMillis(s);
    
    SimpleDateFormat sdf = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
    java.util.Date d = gc.getTime();
    String time = sdf.format(d);
    
    return time;
}
    
    
}
