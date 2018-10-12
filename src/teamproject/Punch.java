package teamproject;
import java.sql.*;
public class Punch {
    //instance fields
    private Badge badge;
    private int terminalid;
    private int punchtypeid;
    private Timestamp originaltimestamp;
    private Timestamp adjustedtimestamp = null;
    private int id = 0;
    public static final int CLOCK_OUT = 0;
    public static final int CLOCK_IN = 1;
    public static final int TIME_OUT = 2;
    //constructor
    public Punch(Badge badge, int terminalid, int punchtypeid) {
        this.badge = badge;
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
    }
    //methods
    public Badge getBadge() {return badge;}
    public int getTerminalid() {return terminalid;}
    public int getPunchtypeid() {return punchtypeid;}
    public Timestamp getOriginaltimestamp() {return originaltimestamp;}
    public Timestamp getAdjustedtimestamp() {return adjustedtimestamp;}
    public int getID() {return id;}
    public void setBadge(Badge b) {
        this.badge = b;
    }
    public void setTerminalid(int t) {
        this.terminalid = t;
    }
    public void setPunchtypeid(int p) {
        this.punchtypeid = p;
    }
    public void setOriginaltimestamp(Timestamp ts) {
        this.originaltimestamp = ts;
    }
    public void setAdjustedtimestamp(Timestamp a) {
        this.adjustedtimestamp = a;
    }
    public void setID(int i) {
        this.id = i;
    }
    
    public String printOriginalTimestamp() {
        String p = null;
        if (getPunchtypeid() == CLOCK_OUT) {
            p = "#" + badge.getId() + " " + "CLOCKED OUT: " + getAdjustedtimestamp();
        }
        if (getPunchtypeid() == CLOCK_IN) {
            p = "#" + badge.getId() + " " + "CLOCKED IN: " + getAdjustedtimestamp();
        }
        if (getPunchtypeid() == TIME_OUT) {
            p = "#" + badge.getId() + " " + "TIMED OUT: " + getAdjustedtimestamp();
        }
        return p;
         
                
    }
    
    
    
}
