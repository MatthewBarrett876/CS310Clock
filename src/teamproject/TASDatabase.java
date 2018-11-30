package teamproject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class TASDatabase {
    
    
    String query, value;
    
    PreparedStatement pstSelect = null, pstUpdate = null;
    ResultSet resultset = null;
    ResultSetMetaData metadata = null;
    Connection conn = null;
    Statement stmt;
    ResultSet punch = null;
    ResultSet badge = null;
    ResultSet shift = null;
    
    int resultCount, columnCount, updateCount = 0;
    
    boolean hasresults;
    
    public TASDatabase()
    {
    
            /* Identify the Server */
            
            String server = ("jdbc:mysql://localhost/tas");
            String username = "tasuser";
            String password = "JSUcs310";
            
            /* Load the MySQL JDBC Driver */
            try
            {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            
            /* Open Connection */

                conn = DriverManager.getConnection(server, username, password);


                /* Create Statement */
                stmt = conn.createStatement( );

            }
            
            
            catch (Exception e) 
            {
                System.err.println("Unable to connect to the database");
                System.err.println(e.toString());
            }
            
            
    }
        
    
    
    
    
   public Punch getPunch(int n)
   {
      Punch p = null;
      
     try
       {
        query = "SELECT *, UNIX_TIMESTAMP(originaltimestamp)*1000 AS ts FROM punch WHERE id = '" + n + "'";
            pstSelect = conn.prepareStatement(query);
            
            hasresults = pstSelect.execute();     
           
                while ( hasresults || pstSelect.getUpdateCount() != -1 ) {

                    if ( hasresults ) {
                        
                        /* Get ResultSet Metadata */
                        
                        resultset = pstSelect.getResultSet();
                        metadata = resultset.getMetaData();
                       
                       
                        /* Get Data; Print as Table Rows */
                        
                        while(resultset.next()) {
                          

                            while(true)
                            {
                                String badge = resultset.getString("badgeid");
                                int id = resultset.getInt("id");
                                int terminalId = resultset.getInt("terminalid");
                                int punchTypeId = resultset.getInt("punchtypeid");
                                long originalTS = resultset.getLong("ts");
                                p = new Punch(getBadge(badge), terminalId, punchTypeId, originalTS, id);
                                
                               
                                return p;
                                
                            }
                            
                        }
                    }
                    
                    hasresults = pstSelect.getMoreResults();
                }
                
               
       }
       
        catch (Exception e) 
        {
            //System.err.println("Unable to connect to the database");
            System.err.println(e.toString());
            
        }
                 
     
        return p;
   }
    
    
   public Badge getBadge(String n) 
   {
       Badge b = null;
       
       try
       {
        query = "SELECT * FROM badge WHERE id = '" + n + "'";
        
            pstSelect = conn.prepareStatement(query);
            
            hasresults = pstSelect.execute();     
           
                while ( hasresults || pstSelect.getUpdateCount() != -1 ) {

                    if ( hasresults ) {
                        
                        /* Get ResultSet Metadata */
                        
                        resultset = pstSelect.getResultSet();
                        metadata = resultset.getMetaData();
                       
                       
                        /* Get Data; Print as Table Rows */
                        
                        while(resultset.next()) {
                          

                            while(true)
                            {
                                String badgeId = resultset.getString("id");
                                String description = resultset.getString("description");
                                
                                b = new Badge(badgeId, description);
                                
                                return b;
                                
                            }
                            
                        }
                    }
                    
                    hasresults = pstSelect.getMoreResults();
                }
                
               
       }
       
        catch (Exception e) 
        {
            //System.err.println("Unable to connect to the database");
            System.err.println(e.toString());
            
        }
                 
       return b;
   }
   
   public Shift getShift(int n) 
   {
       Shift s = null;
       
      
       try
       {
           
            query = ("SELECT *, UNIX_TIMESTAMP(start)*1000 AS ts1, UNIX_TIMESTAMP(stop)*1000 AS ts2, "
                    + "UNIX_TIMESTAMP(lunchstart)*1000 AS ts3, UNIX_TIMESTAMP(lunchstop)*1000 AS ts4 FROM shift WHERE id = '" + n +"'");
            pstSelect = conn.prepareStatement(query);
            
            hasresults = pstSelect.execute();     
           
                while ( hasresults || pstSelect.getUpdateCount() != -1 ) {

                    if ( hasresults ) {
                        
                        /* Get ResultSet Metadata */
                        
                        resultset = pstSelect.getResultSet();
                        metadata = resultset.getMetaData();
                       
                       
                        /* Get Data; Print as Table Rows */
                        
                        while(resultset.next()) {
                          

                            
                            
                               
                                String description = resultset.getString("description");
                                
                                
                                long start = resultset.getLong("ts1");
                                
                                
                                long stop = resultset.getLong("ts2");
                                
                                
                                
                                long lunchstart = resultset.getLong("ts3");
                                
                                long lunchstop = resultset.getLong("ts4");

                                int interval = resultset.getInt("interval");
                                int gracePeriod = resultset.getInt("graceperiod");
                                int dock = resultset.getInt("dock");
                                int lunchDeduct = resultset.getInt("lunchdeduct");
                                
                               
                                s = new Shift(description, start, stop, lunchstart, 
                                        lunchstop, interval, gracePeriod, dock, 
                                        lunchDeduct);
                                
                                return s;
                                
                            
                            
                        }
                    }
                    
                    hasresults = pstSelect.getMoreResults();
                }
                
               
       }
       
        catch (Exception e) 
        {
            //System.err.println("Unable to connect to the database");
            System.err.println(e.toString());
            
        }
                 
       return s;
            
   }
   
   public Shift getShift(Badge b) 
   {
       int shiftId = 0;
       
       Shift s = null;
       
       String badgeId = b.getId();
      
       try
       {
           
            query = ("SELECT * FROM employee e WHERE badgeid = '" + badgeId + "'");
            pstSelect = conn.prepareStatement(query);
            
            hasresults = pstSelect.execute();     
           
                while ( hasresults || pstSelect.getUpdateCount() != -1 ) {

                    if ( hasresults ) {
                        
                        /* Get ResultSet Metadata */
                        
                        resultset = pstSelect.getResultSet();
                        metadata = resultset.getMetaData();
                       
                       
                        /* Get Data; Print as Table Rows */
                        
                        while(resultset.next()) {
                          

                            while(true)
                            {
                               
                               shiftId = resultset.getInt(7);
                               
                               s = getShift(shiftId);
                               
                               return s;
                               
                            }
                            
                        }
                    }
                    
                    hasresults = pstSelect.getMoreResults();
                }
                
               
       }
       
        catch (Exception e) 
        {
            //System.err.println("Unable to connect to the database");
            System.err.println(e.toString());
        }
             
           return s;
   }


   
public int insertPunch(Punch p)
{
    String badgeid = p.getBadgeid();
    int terminalid = p.getTerminalid();
    int punchtypeid = p.getPunchtypeid();
    int id = 0;
    
    try
    {
       
        query = "INSERT INTO punch(badgeid, terminalid, punchtypeid) VALUES ('" + badgeid + "', " + terminalid + ", " + punchtypeid + ")";
        pstUpdate = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    
                    // Execute Update Query

                    updateCount = pstUpdate.executeUpdate();

                    // Get New Key; Print To Console

                    if (updateCount > 0) {

                        resultset = pstUpdate.getGeneratedKeys();

                        if (resultset.next()) {
                            
                            id = resultset.getInt(1);
                            
                            p.setID(id);
                            
                            return id;

                        }

                    }

    }
    
    catch (Exception e) 
    {
            //System.err.println("Unable to connect to the database");
            System.err.println(e.toString());
    }
    
    return id;
    
}


public ArrayList<Punch> getDailyPunchList(Badge b, long ts) 
{
    //set one to midnight and one to 
    ArrayList<Punch> p1 = new ArrayList<>(); 
    
    GregorianCalendar check = new GregorianCalendar();
    check.setTimeInMillis(ts);
    
    
    
    
    try
       {
            query = ("SELECT *, UNIX_TIMESTAMP(originaltimestamp)*1000 AS ts1 FROM punch p WHERE badgeid = ?");
            pstSelect = conn.prepareStatement(query);
            pstSelect.setString(1, b.getId());
            
            hasresults = pstSelect.execute();     
           
                while ( hasresults || pstSelect.getUpdateCount() != -1 ) {

                    if ( hasresults ) {
                        
                        /* Get ResultSet Metadata */
                        
                        resultset = pstSelect.getResultSet();
                        metadata = resultset.getMetaData();
                       
                       
                        /* Get Data; Print as Table Rows */
                        
                        while(resultset.next()) {
                          
                               
                               String badge = resultset.getString("badgeid");
                               
                               int id = resultset.getInt("id");
                               int terminalId = resultset.getInt("terminalid");
                               int punchTypeId = resultset.getInt("punchtypeid");
                               long originalTS = resultset.getLong("ts1");
                                
                               Punch p = new Punch(b, terminalId, punchTypeId, originalTS, id);
                               p.setOriginaltimestamp(originalTS);
                               
                               GregorianCalendar gc = new GregorianCalendar();
                               gc.setTimeInMillis(originalTS);
                               gc.setTimeInMillis(ts);
    
                               SimpleDateFormat days = new SimpleDateFormat("MM/dd/yyyy");
                               
                              // SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
                               
                               
                              
                               
                               String original = days.format(originalTS);
                               String givenTs = days.format(ts);
                               
                               if(original.equals(givenTs))
                               {
                                    p1.add(p);
                                    
                               }
                               
                               
                                   
                              
                               
                        
                    }
                    
                    hasresults = pstSelect.getMoreResults();
                }
                
               
       }
       }
       
        catch (Exception e) 
        {
            //System.err.println("Unable to connect to the database");
            System.err.println(e.toString());
        }
            
    return p1;
}


}








   