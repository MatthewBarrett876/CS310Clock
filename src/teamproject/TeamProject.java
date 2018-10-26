package teamproject;

import java.sql.*;
import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TeamProject {

    public static void main(String[] args) {
        
        TASDatabase db = new TASDatabase();
  
        GregorianCalendar ts = new GregorianCalendar();
        
        ts.set(Calendar.DAY_OF_MONTH, 12);
        ts.set(Calendar.YEAR, 2018);
        ts.set(Calendar.MONTH, 8);
        ts.set(Calendar.HOUR, 0);
        ts.set(Calendar.MINUTE, 0);
        ts.set(Calendar.SECOND, 0);
        
        Badge b = db.getBadge("87176FD7");
        
        /* Retrieve Punch List #1 (created by "getDailyPunchList()") */
        
        ArrayList<Punch> p1 = db.getDailyPunchList(b, ts.getTimeInMillis());
        
         for (Punch p : p1) 
         {
             System.out.println(p.printOriginalTimestamp());
         }
        
        

    }
}

    