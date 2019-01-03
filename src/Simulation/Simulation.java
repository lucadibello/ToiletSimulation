package Simulation;

import Characters.Student;
import Extra.TimeManager;
import GUI.GuiManager;
import Resources.PaperTowel;
import Resources.Soap;

/**
 * This class is used for manage the all process (Gui - Student - Bathroom).
 * @author Luca Di Bello
 */
public class Simulation extends Thread{
    
    /**
     * Describes the soap container.
     */
    public static Soap soapContainer = new Soap();
    
    /**
     * Describes the paper towel container.
     */
    public static PaperTowel paperContainer = new PaperTowel();
    
    /**
     * Describes the object that manage the time.
     */
    private final TimeManager tManager;
    
    /**
     * Describes the object that manage the gui.
     */
    public final GUI.GuiManager gManager;
    
    /**
     * Describes the students that are in the simulation.
     */
    private final Student[] students;
    
    /**
     * Describes the time multiplier
     */
    private static int timeMultiplier;

    private static int timesInBathroom = 0;
    
    private static int statNoWash = 0;

    private static int statNoDry = 0;
    
    /**
     * Constructor.
     * @param students Students that will be in the simulation.
     * @param gManager Object that manage the gui.
     */
    public Simulation(Student[] students,GuiManager gManager,int timeMultiplier) {
        this.students = students;
        this.timeMultiplier = timeMultiplier;
        this.tManager = new TimeManager(this.timeMultiplier,students);
        this.gManager = gManager;
        this.start();
        
        gManager.log("[Info] Simulation started :)");
    }
    
    @Override
    public void run(){
        gManager.startDateTimeUpdater(tManager,10);
        gManager.updateStats();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    /**
     * Getter method for the constant TIME_MULTIPLIER.
     * @return The value of the constant TIME_MULTIPLIER.
     */
    public static int getTimeMultiplier() {
        return timeMultiplier;
    }
    
    //timesInBathroom getter + add
    public static void increaseTimesInBathroom(){
        timesInBathroom++;
    }
    public static int getTimesInBathroom() {
        return timesInBathroom;
    }
    
    //statNoWash getter + add
    public static void increaseStatNoWash(){
        statNoWash++;
    }
    
    public static int getStatNoWash() {
        return statNoWash;
    }
    
    //statNoDry getter + add
    public static void increaseStatNoDry(){
        statNoDry++;
    }
    public static int getStatNoDry() {
        return statNoDry;
    }
    // </editor-fold>
}
