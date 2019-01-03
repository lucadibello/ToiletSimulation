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
public class Simulation{
    
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
    private int timeMultiplier;

    private int totalTimesInBathroom = 0;
    
    private int totalTimesNoWash = 0;

    private int totalTimesNoDry = 0;
    
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
        
        //Start processes
        gManager.setSimulation(this);
        gManager.startDateTimeUpdater(tManager,10);
        gManager.updateStats();
        gManager.setStudentsPainting(students);
        
        gManager.log("[Info] Simulation started");
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    /**
     * Getter method for the constant TIME_MULTIPLIER.
     * @return The value of the constant TIME_MULTIPLIER.
     */
    public int getTimeMultiplier() {
        return timeMultiplier;
    }
    
    public int getTotalTimesInBathroom() {
        return totalTimesInBathroom;
    }

    public int getTotalTimesNoWash() {
        return totalTimesNoWash;
    }

    public int getTotalTimesNoDry() {
        return totalTimesNoDry;
    }
    
    public void increaseTotalTimesInBathroom() {
        this.totalTimesInBathroom++;
    }

    public void increaseTotalTimesNoWash() {
        this.totalTimesNoWash++;
    }

    public void increaseTotalTimesNoDry() {
        this.totalTimesNoDry++;
    }
    // </editor-fold>
}
