package teamproject;

import java.util.ArrayList;
import java.util.Calendar;
import org.json.simple.*;
import java.util.HashMap;


public class TASLogic {
    
   public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift)
   {
       
        
        int getStartMinutes = 0;
        int getStopMinutes = 0;
        int getStartHour = 0;
        
        
        
            
        int total = 0;
            
        for (Punch punch : dailypunchlist) 
        {
            punch.adjust(shift);
           
            long time = punch.getAdjustedtimestamp();
            Calendar gc = Calendar.getInstance();
       
            gc.setTimeInMillis(time);

            // Set the original time stamp
          
            int originalHour = gc.get(Calendar.HOUR_OF_DAY);
            int originalMinute = gc.get(Calendar.MINUTE);
            int nameOfDay = gc.get(Calendar.DAY_OF_WEEK);
       
           
            gc.setTimeInMillis(shift.getLunchStart());
            int lunchStartHour = gc.get(Calendar.HOUR_OF_DAY);

            gc.setTimeInMillis(shift.getLunchStop());
            int lunchStopHour = gc.get(Calendar.HOUR_OF_DAY);

            // Weekdays
            
            if(nameOfDay != Calendar.SATURDAY && nameOfDay != Calendar.SUNDAY)
            {
                boolean checkLunch = false;
                boolean checkTimeOut = false;
                
                if(shift.getDescription().equals("Shift 1"))
                {
                    if(punch.getPunchtypeid() == 2)
                    {
                        checkTimeOut = true;
                    }
                    
                    if(punch.getPunchtypeid() == 1 && originalHour != lunchStartHour)
                    {
                        getStartMinutes = (60 - originalMinute);
                        getStartHour = originalHour;

                    }
                    
                    else if(punch.getPunchtypeid() == 0 && originalHour == lunchStopHour)
                    {
                        checkLunch = true;
                        
                    }

                    else if(punch.getPunchtypeid() == 0 && (originalHour != lunchStopHour))
                    {
                        getStopMinutes = (60 - originalMinute);

                        if(getStartMinutes == 60 && getStopMinutes != 60)
                        {
                            total = (originalHour - getStartHour) * 60 + getStopMinutes;
                        }
                        
                        
                        else if(getStartMinutes != 60 && getStopMinutes != 60)
                        {
                            total = (originalHour - getStartHour) * 60 - getStartMinutes 
                                        + getStopMinutes;
                            
                        }
                        
                        else if(getStartMinutes != 60 && getStopMinutes == 60)
                        {
                            total = (originalHour - getStartHour) * 60 - getStartMinutes;
                            
                        }
                        
                        if(total > 360 && checkLunch == false && checkTimeOut == false)
                        {
                            total = total - 30;
                            
                        }
                    }
                }
                
                else if(shift.getDescription().equals("Shift 2"))
                {
                   
                    if(punch.getPunchtypeid() == 1 && (originalHour != lunchStartHour))
                    {
                        getStartMinutes = (60 - originalMinute);
                        getStartHour = originalHour;
                        
                    }
                    
                    if(punch.getPunchtypeid() == 0 && originalHour == lunchStopHour)
                    {
                        checkLunch = true;
                        
                    }

                    else if(punch.getPunchtypeid() == 0 && originalHour != lunchStopHour)
                    {
                       getStopMinutes = (60 - originalMinute);
                       
                       if(getStartMinutes == 60 && getStopMinutes != 60)
                        {
                            total = (originalHour - getStartHour) * 60 + getStopMinutes;
                        }
                        
                        
                        else if(getStartMinutes != 60 && getStopMinutes != 60)
                        {
                            total = (originalHour - getStartHour) * 60 - getStartMinutes 
                                        + getStopMinutes;
                            
                        }
                        
                        else if(getStartMinutes != 60 && getStopMinutes == 60)
                        {
                            total = (originalHour - getStartHour) * 60 - getStartMinutes;
                            
                        }
                       
                        if(punch.getPunchtypeid() == 2)
                        {
                            checkTimeOut = true;
                        }
                    
                    }
                     
                   
                    if(total > 360 && checkLunch == false && checkTimeOut == false)
                    {
                        total = total - 30;
                            
                    }
                    
                }
                
            }
            
            // Weekends
            
            else if(nameOfDay == Calendar.SATURDAY || nameOfDay == Calendar.SUNDAY)
            {
                if(punch.getPunchtypeid() == 1)
                {
                    getStartHour = originalHour;

                }

                if(punch.getPunchtypeid() == 0)
                {
                    getStopMinutes = (60 - originalMinute);
                    
                    if(getStartMinutes == 60 && getStopMinutes != 60)
                        {
                            total = (originalHour - getStartHour) * 60 + getStopMinutes;
                        }
                        
                        
                        else if(getStartMinutes != 60 && getStopMinutes != 60)
                        {
                            total = (originalHour - getStartHour) * 60 - getStartMinutes 
                                        + getStopMinutes;
                            
                        }
                        
                        else if(getStartMinutes != 60 && getStopMinutes == 60)
                        {
                            total = (originalHour - getStartHour) * 60 - getStartMinutes;
                            
                        }
                    
                }
                
                
            }
            
        }
        
        return total;
       
   }
   
   public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist) {
       String json = "";
       ArrayList<HashMap<String, String>> jsonData = new ArrayList();
       for (Punch punch : dailypunchlist) {
           HashMap<String, String> punchData = new HashMap<>();
           punchData.put("id", String.valueOf(punch.getID()));
           punchData.put("badgeid", String.valueOf(punch.getBadgeid()));
           punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
           punchData.put("punchtypeid", String.valueOf(punch.getPunchtypeid()));
           punchData.put("punchdata", String.valueOf(punch.getNote()));
           punchData.put("originaltimestamp", String.valueOf(punch.getOriginaltimestamp()));
           punchData.put("adjustedtimestamp", String.valueOf(punch.getAdjustedtimestamp()));
           jsonData.add(punchData);
       
       }
       json = JSONValue.toJSONString(jsonData);
       return json;
       
   }
    
}
