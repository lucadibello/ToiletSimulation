package Extra;

import Characters.Student;
import Data.ToiletHours;
import Exceptions.PaperTowelRunOut;
import Exceptions.SoapRunOutException;
import GUI.MainWindow;
import java.sql.Time;
import java.util.Calendar;
import Simulation.Simulation;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is used for controlling time.
 * @author Luca Di Bello
 */
public class TimeManager extends Thread{
    /**
     * Describes how many times the time speed have to be increased.
     */
    private int timeMultiplier;

    /**
     * Describes the object that will manage the time.
     */
    private static Calendar calendario = Calendar.getInstance();

    /**
     * Describes the students to check. 
     */
    private Student[] students = null;
    
    /**
     * Constructor with 1 parameter.
     * @param timeMultiplier How many times the time speed have to be increased.
     */
    public TimeManager(int timeMultiplier) {
        this(timeMultiplier, null);
    }
    
    /**
     * Constructor with 2 parameters.
     * @param timeMultiplier How many times the time speed have to be increased.
     * @param students Students to check during the execution.
     */
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
                        //Generate new "times per day"
                        student.renew();
                        
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
                            GUI.MainWindow.gManager.log(student + " is in bathroom");
                            
                            student.goToBathroom();
                            
                            student.increaseTimesInBathroom();
                            MainWindow.simulation.increaseTotalTimesInBathroom();
                            
                            //10% students don't wash their hands
                            final int NO_WASH_PROBABILITY = 10;
                            
                            int probability = ThreadLocalRandom.current().nextInt(1,100 + 1);
                            
                            if(probability > NO_WASH_PROBABILITY){
                                try{
                                    student.washHands(Simulation.soapContainer);

                                    //30% students don't dry their hands
                                    final int NO_DRY_PROBABILITY = 30;

                                    probability = ThreadLocalRandom.current().nextInt(1,100+1);
                                    if(probability > NO_DRY_PROBABILITY){
                                        student.dryHands(Simulation.paperContainer);
                                    }
                                    else{
                                        student.increaseTimesNoDry();
                                        MainWindow.simulation.increaseTotalTimesNoDry();
                                    }
                                }
                                catch(PaperTowelRunOut ex){
                                    //Refill the paper
                                    Simulation.paperContainer.refill();
                                }
                                catch(SoapRunOutException ex){
                                    //Refill the soap
                                    Simulation.soapContainer.refill();
                                }
                            }
                            else{
                                //Student doesn't wash his hands
                                student.increaseTimesNoWash();
                                MainWindow.simulation.increaseTotalTimesNoWash();
                            }
                            
                            //Update GUI data
                            GUI.MainWindow.gManager.updateStats();
                        }
                    }
                }
                
                startTime = System.currentTimeMillis();
            }
        }
    }
    
    /**
     * This method calculate the total of milliseconds that compose a second with the
     * passed time multiplier.
     * @param timeMultiplier How many times the time speed have to be increased. max value is 1000.
     * @return 
     */
    public static int calculateEffectiveSecond(int timeMultiplier){
        
        if(timeMultiplier > 1000){
            timeMultiplier = 1000;
        }
        
        return Math.floorDiv(1000, timeMultiplier);
    }
    
    /**
     * THis method calculate the difference in seconds between two times.
     * @param first First Time object.
     * @param second Second Time object.
     * @return Difference in seconds between the passed Time objects.
     */
    public static double getDifferenceSeconds(Time first,Time second){
        return (second.getTime() - first.getTime()) / 1000;
    }
    
    /**
     * This method is used for converting the current calendar in a Time object that
     * describes the current day time (hour, minutes, seconds).
     * @return Time object that contains the current day time.
     */
    public static Time getTimeObj(){
        return new Time(calendario.get(Calendar.HOUR_OF_DAY),calendario.get(Calendar.MINUTE),calendario.get(Calendar.SECOND));
    }
    
    /**
     * This method convert the current calendar date to a string.
     * @param divider Character that will divide the data.
     * @return A string with this pattern: dd<divider>mm<divider>YYYY.
     */
    public String getCurrentDate(String divider){
        return Integer.toString(calendario.get(Calendar.DAY_OF_MONTH)) + divider +
                Integer.toString(calendario.get(Calendar.MONTH)+ 1) + divider + 
                Integer.toString(calendario.get(Calendar.YEAR));
    }
    
    /**
     * This method convert the current calendar day time to a string.
     * @param divider Character that will divide the data.
     * @return A string with this patter: hh<divider>mm<divider>ss.
     */
    public String getTime(String divider){
        return Integer.toString(calendario.get(Calendar.HOUR_OF_DAY)) + divider +
                Integer.toString(calendario.get(Calendar.MINUTE)) + divider +
                Integer.toString(calendario.get(Calendar.SECOND));
    }
}
