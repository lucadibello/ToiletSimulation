package Characters;
import Data.SchoolDepartment;
import Extra.TimeManager;
import Resources.PaperTowel;
import Resources.Soap;
import java.awt.Graphics;
import java.sql.Time;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class describes a SAMT student.
 * @author Luca Di Bello
 */
public final class Student extends Thread{
    
    // <editor-fold defaultstate="collapsed" desc="Attributes">
    /**
     * Describes the school department where the student is studing;
     */
    private SchoolDepartment department;

    /**
     * Describes the student school year.
     */
    private int schoolYear;
    
    /**
     * Describes how many times the students go to the bathroom during the day.
     */
    private int timesPerDay;
    
    /**
     * Describes if the student is in the bathroom
     */
    private boolean isInBathroom;
    
    /**
     * Describes when the student have to go to the bathroom
     */
    private Time[] whenBathroom;
    // </editor-fold>
    
    /**
     * Parameterized constructor. 
     * @param department Student department.
     * @param schoolYear Studend school year.
     * @param timesPerDay How many times during the day the student have to go to the bathroom. 
     */
    public Student(SchoolDepartment department, int schoolYear, int timesPerDay) {
        setDepartment(department);
        setSchoolYear(schoolYear);
        setTimesPerDay(timesPerDay);
        whenBathroom = generateBathroomTime(timesPerDay);
    }
    
    public void goToBathroom(Soap soap, PaperTowel paper){
        if(!isInBathroom){
            //Set flag
            isInBathroom = true;
            this.start();
        }
    }
    
    public void exitBathroom(){
        if(isInBathroom){
            //Reset flag
            isInBathroom = false;    
        }
    }
    
    @Override
    public void run(){
        System.out.println("Student is in bathroom");
        //Random sleep time -> Time until the student finish with the bathroom
        //Massimo 30 minuti - Minimo 30 secondi
        
        long millMax = 60 * 10 * 1000;
        long millMin = 30 * 1000;
        long millSleepTime = ThreadLocalRandom.current().nextLong(millMin, millMax);

        Time currentCalendarTime = TimeManager.getTime();
        Time postCalendarTime = new Time(currentCalendarTime.getTime() + millSleepTime);
        /*
        try{
            currentThread().sleep(millSleepTime);
        }
        catch(InterruptedException ex){
        }

        System.out.println(this + " exited bathroom");
        */
        System.out.println(currentCalendarTime + " - until: " + postCalendarTime);
        exitBathroom();
    }

    /**
     * Method used for painting the student in the GUI
     * @param g Graphics object
     */
    public void paint(Graphics g){
        g.fillOval(100, 100, 200, 200);
    }
    
    /**
     * This method is used for generating some random day hours.
     * @param times Time objects to generate 
     * @return Array of Time objects
     */
    public Time[] generateBathroomTime(int times) {
        final Random random = new Random();
        final int millisInDay = 24*60*60*1000;
        
        Time[] timeHours = new Time[times];
        for(int i = 0; i < timeHours.length;i++){
            timeHours[i] = new Time((long)random.nextInt(millisInDay));
        }
        timeHours[0] = new Time(0);
        return timeHours;
    }
    
    /**
     * This method check if the current time is equal to the passed one.
     * @param currentTime Time to compare with the current one.
     * @return True if the two times are equal, otherwise False
     */
    public boolean checkTime(Time currentTime){
        long nowMillis = currentTime.getTime();
        
        for(Time toiletTime : whenBathroom){
            if(toiletTime.getTime() == nowMillis){
                return true;
            }
        }
        return false;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    //whenBathroom Getter 
    public Time[] getWhenBathroom() {
        return whenBathroom;
    }
    
    //isInBathroom Getter & Setter
    public boolean isInBathroom() {
        return isInBathroom;
    }

    public void setIsInBathroom(boolean isInBathroom) {
        this.isInBathroom = isInBathroom;
    }
    
    //Department Getter & Setter
    public SchoolDepartment getDepartment() {
        return department;
    }

    public void setDepartment(SchoolDepartment department) {
        if(department != null){
            this.department = department;
        }
        else{
            this.department = SchoolDepartment.Visitator;
        }
    }

    //School year Getter & Setter
    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        if(schoolYear >= 1 && schoolYear <= 4){
            this.schoolYear = schoolYear;
        }
        else{
            this.schoolYear = 1;
        }
    }
    
    //Incontinence Getter & Setter
    public int getTimesPerDay() {
        return timesPerDay;
    }

    public void setTimesPerDay(int timesPerDay) {
        if(timesPerDay >= 0 && timesPerDay <= 20){
            this.timesPerDay = timesPerDay;
        }
        else{
            this.timesPerDay = 0; 
        }
    }

    // </editor-fold>
}
