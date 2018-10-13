package teamproject;

import java.sql.*;


public class TASDatabase {
    
    int check = 1;
    
    Connection conn = null;
    Statement stmt;
    ResultSet punch = null;
    ResultSet badge = null;
    ResultSet shift = null;
    
    public TASDatabase() throws ClassNotFoundException, SQLException 
    {
    
            /* Identify the Server */
            
            String server = ("jdbc:mysql://localhost/tas");
            String username = "tasuser";
            String password = "JSUcs310";
            
            /* Load the MySQL JDBC Driver */
            try
            {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            }
            
            catch (Exception e) 
            {
            System.err.println("Unable to connect to the database");
            }
            /* Open Connection */

            conn = DriverManager.getConnection(server, username, password);
            
            
            /* Create Statement */
            stmt = conn.createStatement( );
          
           
            /* Close Database Connection */
            
            conn.close();
            
        }
        
    
    
    
    
   public Punch getPunch(int n) throws SQLException
   {
      
        punch = stmt.executeQuery("SELECT * FROM punch p WHERE id = '" + n + "'");
           
        int terminalid = punch.getInt("terminalid");
        int punchTypeId = punch.getInt("punchtypeid");
        
        String badgeId = punch.getString("badgeid");
        
        Badge badgeResult = getBadge(badgeId);
        
        Punch punchResult = new Punch(badgeResult, terminalid, punchTypeId);
           
       return punchResult;
       
   }
    
    
   public Badge getBadge(String n) throws SQLException
   {
       badge = stmt.executeQuery("SELECT * FROM badge b WHERE id ='" + n + "'");
       
       String description = badge.getString("description");
       
       Badge badgeResult = new Badge(n, description);
       
       return badgeResult;
   }
   
   
   public Shift getShift(int n) throws SQLException
   {
       shift = stmt.executeQuery("SELECT * FROM shift s WHERE id ='" + n + "'");
       
       String description = shift.getString("description");
       
       Timestamp start = shift.getTimestamp("start");
       Timestamp stop = shift.getTimestamp("stop");
       Timestamp lunchstart = shift.getTimestamp("lunchstart");
       
       int interval = shift.getInt("interval");
       int gracePeriod = shift.getInt("graceperiod");
       int dock = shift.getInt("dock");
       int lunchDeduct = shift.getInt("lunchdeduct");
       
       Shift shiftResult = new Shift();
       
       return shiftResult;
       
   }
   
   public Shift getShift(Badge b) throws SQLException
   {
       shift = stmt.executeQuery("SELECT * FROM shift s WHERE id ='" + b + "'");
       
       Timestamp originalTimeStamp = shift.getTimestamp("originaltimestamp");
       
       Shift shiftResult = new Shift();
       
       return shiftResult;
       
   }
}
    
    


   