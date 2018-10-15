package teamproject;

import java.time.LocalTime;

public class Shift {
    private long start = 0;
    private long stop = 0;
    
public void LocalTime (int hour, int minute, int second){
    LocalTime shift1start = LocalTime.of(07,00,00);
    LocalTime shift1stop = LocalTime.of(15,30,00);
    LocalTime shift2start = LocalTime.of(12,00,00);
    LocalTime shift2stop = LocalTime.of(20,30,00);
    LocalTime shift1L = LocalTime.of(12,00,00);
    LocalTime shift1LS = LocalTime.of(12,30,00);
    LocalTime shift2L = LocalTime.of(16,30,00);    
    LocalTime shift2LS = LocalTime.of(17,00,00);
    LocalTime shift1EL = LocalTime.of(11,30,00);
    LocalTime shift1ELS = LocalTime.of(12,00,00);
    
}
public void Start(){    this.start = System.currentTimeMillis();}
public void Stop(){    this.stop = System.currentTimeMillis();}
public long getStart(){    return this.start; }
public long getStop(){    return this.stop;}
public void Shift1(){}
public void Shift2(){}
public void Interval(){}
public void GradePeriod(){}
public void Dock(){
}

}
