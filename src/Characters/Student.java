package Characters;
import Data.SchoolDepartment;
import Data.ToiletHours;
import Exceptions.PaperTowelRunOut;
import Exceptions.SoapRunOutException;
import Extra.TimeManager;
import GUI.MainWindow;
import Interfaces.StudentListener;
import Resources.PaperTowel;
import Resources.Soap;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class describes a student.
 * @author Luca Di Bello
 */
public final class Student extends Thread implements MouseListener{
    
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
     * Describes if the student is in the bathroom.
     */
    private boolean isInBathroom;
    
    /**
     * Describes when the student have to go to the bathroom.
     */
    private Time[] whenBathroom;
    
    //Statistics attributes
    /**
     * Describes how many times the student had been in bathroom.
     */
    private int timesInBathroom = 0;
    
    /**
     * Describes how many times the student had beem in bathroom without washing 
     * his hands.
     */
    private int timesNoWash = 0;
    
    /**
     * Describes how many times the students had been in bathroom and washed his hands
     * but without drying them.
     */
    private int timesNoDry = 0;
    
    //Range
    /**
     * Minumum times a student can go to the bathroom.
     */
    public final int MIN_TIMES_PER_DAY = 0;
    
    /**
     * Maximum times a student can go to the bathroom.
     */
    public final int MAX_TIMES_PER_DAY = 20;
    
    //Painting attributes
    /**
     * Radius of the student (used in painting)
     */
    public static final int STUDENT_RADIUS = 50;
    
    /**
     * Position of the student in the screen.
     */
    public Point paintPosition = new Point(-1,-1);
    
    
    //TODO: FINIRE LISTENER
    private List<StudentListener> listeners = new ArrayList<>();
    // </editor-fold>
    
    /**
     * Parameterized constructor. 
     * @param department Student department.
     * @param schoolYear Studend school year.
     * @param timesPerDay How many times during the day the student have to go to the bathroom. 
     * @param name Name of the student.
     */
    public Student(String name,SchoolDepartment department, int schoolYear, int timesPerDay) {
        setName(name);
        setDepartment(department);
        setSchoolYear(schoolYear);
        setTimesPerDay(timesPerDay);
        whenBathroom = generateBathroomTime();        
        this.start();
    }
    
    /**
     * This method sets the "isInBathroom" flag to "true" and updates the GUI.
     */
    public void goToBathroom(){
        if(!isInBathroom){
            //Set flag
            isInBathroom = true;

            //Update on GUI
            GUI.MainWindow.gManager.updatePaintStudent(this);
        }
    }
    
    /**
     * This method sets the "isInBathroom" flag to "false" and updates the GUI.
     */
    public void exitBathroom(){
        if(isInBathroom){
            //Reset flag
            isInBathroom = false; 

            //Update on GUI
            GUI.MainWindow.gManager.updatePaintStudent(this);
        }
    }
    
    @Override
    public void run(){
        while(true){
            try{
                if(isInBathroom){
                    Time currentCalendarTime = TimeManager.getTimeObj();
                    GUI.MainWindow.gManager.log(this + " entered at " + currentCalendarTime);
                    //Max 10 Minutes - Min 30 Seconds
                    int effectiveSecond = TimeManager.calculateEffectiveSecond(MainWindow.simulation.getTimeMultiplier());
                    long millMax = 60 * 10 * 1000;
                    long millMin = 30 * 1000;
                    
                    //Random sleep time -> Time until the student finish with the bathroom
                    long millSleepTime = ThreadLocalRandom.current().nextLong(millMin, millMax);

                    Time postCalendarTime = new Time(currentCalendarTime.getTime() + millSleepTime);
                   
                    //Calculate right sleep time using the "effectiveSecond" attribute
                    long sleepSecond = Math.round(TimeManager.getDifferenceSeconds(currentCalendarTime, postCalendarTime) / (double)effectiveSecond); 

                    Thread.sleep(sleepSecond);
                    
                    GUI.MainWindow.gManager.log(this + " left bathroom at " + postCalendarTime);
                    exitBathroom();
                }
                else{
                    //Little pause -> Less CPU work
                    Thread.sleep(100);
                }
            }
            catch(InterruptedException ex){
                System.out.println(this + " interrupted");
            }
        }
    }
    
    /**
     * This method it's used for letting the student washing his hands.
     * @param soap Container of soap.
     * @throws SoapRunOutException Throw this exception when the soap is ran out.
     */
    public void washHands(Soap soap) throws SoapRunOutException{
        soap.useSoap();
    }
    
    /**
     * This method it's used for letting the student drying his hands.
     * @param ptw Container of paper.
     * @throws PaperTowelRunOut Throw this exception when the paper is ran out.
     */
    public void dryHands(PaperTowel ptw) throws PaperTowelRunOut{
        ptw.grabPaper();
    }
    
    /**
     * This method it's used every new day. It generates some others hours where 
     * the student will go to the bathroom.
     */
    public void renew(){
        setTimesPerDay(ThreadLocalRandom.current().nextInt(MIN_TIMES_PER_DAY, MAX_TIMES_PER_DAY));
        generateWhenBathroom();
    }
    
    /**
     * Method used for painting the student in the GUI
     * @param g Graphics object
     */
    public void paint(Graphics g){
        g.setColor((isInBathroom ? Color.GREEN : Color.RED));
        
        int x = paintPosition.x * (STUDENT_RADIUS*2);
        int y = paintPosition.y * (STUDENT_RADIUS*2);
        //Draw student on screen
        g.fillOval(
                x,
                y,
                STUDENT_RADIUS*2,
                STUDENT_RADIUS*2
        );
        
        //WRITE NAME IN THE CENTER OF THE CIRCLE
        int fontSize = STUDENT_RADIUS*2 / getName().length();
        Font f = new Font("MyFont", 1, fontSize);
        FontMetrics fm = g.getFontMetrics(f);
        int fontWidth = fm.stringWidth(getName());
        int fontHeight = fm.getAscent() -  fm.getDescent();
        g.setColor(Color.WHITE);
        g.setFont(f);
        g.drawString(getName(), x + (((STUDENT_RADIUS*2) - fontWidth) / 2), y + (STUDENT_RADIUS*2) - (((STUDENT_RADIUS*2) - fontHeight) / 2));
    }
    
    /**
     * This method is used for generating some random day hours.
     * @param times Time objects to generate 
     * @return Array of Time objects
     */
    private Time[] generateBathroomTime() {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        
        Time[] timeHours = new Time[timesPerDay];
        for(int i = 0; i < timeHours.length;i++){
            //Generate random time
            timeHours[i] = new Time(
                    random.nextInt(ToiletHours.TOILET_START.getHours(), ToiletHours.TOILET_END.getHours()),
                    random.nextInt(ToiletHours.TOILET_START.getMinutes(), ToiletHours.TOILET_END.getMinutes()),
                    random.nextInt(60)
            );
        }

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
            //System.out.println(toiletTime.getTime() + " - " + nowMillis);
            if(toiletTime.getTime() == nowMillis){
                return true;
            }
        }
        return false;
    }
    
    /**
     * This method it's used to adding a StudentListener to the list of listeners.
     * @param listener StudentListener to add from the list.
     */
    public void addStudentListener(StudentListener listener){
        this.listeners.add(listener);
    }
    
    /**
     * This method it's used to removing a StudentListener to the list of listeners.
     * @param listener StudentListener to remove from the list.
     */
    public void removeStudentListener(StudentListener listener){
        this.listeners.remove(listener);
    }
    
    /**
     * This method it's used for checking if a point it's contained in the 
     * area of the student during the visual simulation
     * @param point Point to check.
     * @return True if the point it's contained otherwise false.
     */
    public boolean isContained(Point point){
        int x = paintPosition.x * (STUDENT_RADIUS*2) + STUDENT_RADIUS;
        int y = paintPosition.y * (STUDENT_RADIUS*2) + STUDENT_RADIUS;
        
        Point buttonCenter = new Point(x , y );
        
        //Todo: Fix location error -> listener problem
        point.setLocation(point.x,point.y-30);

        return buttonCenter.distance(point) <= STUDENT_RADIUS;
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        if(isContained(me.getPoint())){
            for(StudentListener listener : listeners){
                listener.studentSelected(this);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //Not implemented
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //Not implemented
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //Not implemented
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //Not implemented
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    //whenBathroom Getter 
    public Time[] getWhenBathroom() {
        return whenBathroom;
    }
    
    //whenBathroom Setter
    public void generateWhenBathroom() {
        this.whenBathroom = generateBathroomTime();
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
        if(timesPerDay >= MIN_TIMES_PER_DAY && timesPerDay <= MAX_TIMES_PER_DAY){
            this.timesPerDay = timesPerDay;
        }
        else{
            System.err.println("Too much times in bathroom.. I set 0");
            this.timesPerDay = 0; 
        }
    }
    
    //timesInBathroom getter + add
    public void increaseTimesInBathroom(){
        timesInBathroom++;
    }
    public int getTimesInBathroom() {
        return timesInBathroom;
    }
    
    //statNoWash getter + add
    public void increaseTimesNoWash(){
        timesNoWash++;
    }
    
    public int getTimesNoWash() {
        return timesNoWash;
    }
    
    //statNoDry getter + add
    public void increaseTimesNoDry(){
        timesNoDry++;
    }
    public int getTimesNoDry() {
        return timesNoDry;
    }
    
    //Setter for painPosition parameter
    public void setPaintPosition(Point position){
        this.paintPosition = position;
    }
    // </editor-fold>

    @Override
    public String toString() {
        return "["+getName() + " - " + this.department+"]";
    }
}
