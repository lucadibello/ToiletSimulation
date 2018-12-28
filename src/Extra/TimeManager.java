package Extra;

import Characters.Student;
import java.sql.Time;
import java.util.Calendar;
import Simulation.Simulation;

/**
 * This class is used for controlling time.
 * @author Luca Di Bello
 */
public class TimeManager extends Thread{
    private int timeMultiplier = 1;

    private static Calendar calendario = Calendar.getInstance();

    private Student[] students = null;
    
    public TimeManager(int timeMultiplier) {
        this(timeMultiplier, null);
    }
    
    public TimeManager(int timeMultiplier, Student[] students) {
        this.timeMultiplier = timeMultiplier;
        this.students = students;
        this.start();
    }

    @Override
    public void run(){
        System.out.println("[Info] Started TimeManager");
        calendario.set(2018, Calendar.JANUARY, 1, 0, 0, 0);
        int oneMultSec = calculateEffectiveSecond();
                
        long startTime = System.currentTimeMillis();
        while(true){
            if(System.currentTimeMillis() >= startTime + oneMultSec){
                calendario.set(Calendar.SECOND, calendario.get(Calendar.SECOND) + 1);
                
                //CHECK FOR STUDENTS
                if(students != null && students.length > 0){
                    Time currTime = getTime();

                    for(Student student : students){
                        if(student.checkTime(currTime)){
                            student.goToBathroom(Simulation.soapContainer,Simulation.paperContainer);
                            System.err.println(student + " is in bathroom");
                        }
                    }
                }
                else{
                    System.out.println("[Time-check error] TimeManager - no users to check");
                }
                
                startTime = System.currentTimeMillis();
            }
        }
    }
    
    public int calculateEffectiveSecond(){
        return Math.floorDiv(1000, timeMultiplier);
    }
    
    public static Time getTime(){
        return new Time(calendario.get(Calendar.HOUR_OF_DAY),calendario.get(Calendar.MINUTE),calendario.get(Calendar.SECOND));
    }
        
    public String getCurrentDate(String divider){
        return Integer.toString(calendario.get(Calendar.DAY_OF_MONTH)) + divider +
                Integer.toString(calendario.get(Calendar.MONTH)+ 1) + divider + 
                Integer.toString(calendario.get(Calendar.YEAR));
    }
    
    public String getTime(String divider){
        return Integer.toString(calendario.get(Calendar.HOUR_OF_DAY)) + divider +
                Integer.toString(calendario.get(Calendar.MINUTE)) + divider +
                Integer.toString(calendario.get(Calendar.SECOND));
    }
}
