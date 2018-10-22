package teamproject;

import java.sql.*;

public class TeamProject {

    public static void main(String[] args) {
        
        TASDatabase db = new TASDatabase();
        Punch p1 = new Punch(db.getBadge("021890C0"), 101, 1);
        
        int terminalid = p1.getTerminalid();
        int eventtypeid = p1.getPunchtypeid();
		
        /* Insert Punch Into Database */
        
        int punchid = db.insertPunch(p1);
		
        System.out.println(punchid);
        
        System.out.println();
        
        
        
        
    }
}
    