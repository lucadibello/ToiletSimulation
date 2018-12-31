package Extra;

import Characters.Student;
import Data.ToiletHours;
import Exceptions.PaperTowelRunOut;
import Exceptions.SoapRunOutException;
import GUI.MainWindow;
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
        GUI.MainWindow.gManager.log("[Info] Started TimeManager");
        calendario.set(2018, Calendar.JANUARY, 1, 0, 0, 0);
        int oneMultSec = calculateEffectiveSecond(timeMultiplier);
                
        long startTime = System.currentTimeMillis();
        
        GUI.MainWindow.gManager.updateStudentHours(students);
        while(true){
            if(System.currentTimeMillis() >= startTime + oneMultSec){
                int preDay = calendario.get(Calendar.DAY_OF_YEAR);
                calendario.set(Calendar.SECOND, calendario.get(Calendar.SECOND) + 1);
                Time calendarTimeObj = getTimeObj();
                //Check if new day
                int postDay = calendario.get(Calendar.DAY_OF_YEAR);
                
                //If new day -> New bathroom time for each student
                if(preDay != postDay){
                    for(Student student : students){
                        student.generateWhenBathroom();
                        //This method will update the table
                        GUI.MainWindow.gManager.updateStudentHours(students);
                    }
                }
                
                //Check toilet hours
                if(!GUI.MainWindow.gManager.getBathroomStatus()){
                    //if it's closed
                    if(ToiletHours.TOILET_START.getTime() - calendarTimeObj.getTime() < 0 && !(calendarTimeObj.getTime() > ToiletHours.TOILET_END.getTime())){
                        GUI.MainWindow.gManager.openBathroom();
                    }
                }
                else{
                    //if it's open
                    if(ToiletHours.TOILET_END.getTime() - calendarTimeObj.getTime() < 0){
                        GUI.MainWindow.gManager.closeBathroom();
                    }
                }
                
                //Check the students
                if(students == null || students.length <= 0){
                    GUI.MainWindow.gManager.log("[Time-check error] TimeManager - no users to check");
                }
                else{
                    for(Student student : students){
                        if(student.checkTime(calendarTimeObj)){
                            GUI.MainWindow.gManager.log(" ");
                            GUI.MainWindow.gManager.log(student + " is in bathroom");
                            student.goToBathroom();
                            
                            try{
                                student.washHands(Simulation.soapContainer);
                                student.dryHands(Simulation.paperContainer);
                                System.out.println("Soap: " + Simulation.soapContainer.getFillStatus());
                            }
                            catch(PaperTowelRunOut ex){
                                Simulation.soapContainer.refill();
                            }
                            catch(SoapRunOutException ex){
                                Simulation.paperContainer.refill();
                            }
                        }
                    }
                }
                
                startTime = System.currentTimeMillis();
            }
        }
    }
    
    public static int calculateEffectiveSecond(int timeMultiplier){
        return Math.floorDiv(1000, timeMultiplier);
    }
    
    public static double getDifferenceSeconds(Time first,Time second){
        return (second.getTime() - first.getTime()) / 1000;
    }
    
    public static Time getTimeObj(){
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
