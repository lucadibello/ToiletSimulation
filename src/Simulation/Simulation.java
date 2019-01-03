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
    private final static int TIME_MULTIPLIER = 1000;
    
    /**
     * Constructor.
     * @param students Students that will be in the simulation.
     * @param gManager Object that manage the gui.
     */
    public Simulation(Student[] students,GuiManager gManager) {
        this.students = students;
        this.tManager = new TimeManager(TIME_MULTIPLIER,students);
        this.gManager = gManager;

        this.start();
        
        gManager.log("[Info] Simulation started :)");
    }
    
    @Override
    public void run(){
        gManager.startDateTimeUpdater(tManager,10);
        
    }
    
    public static int getTimeMultiplier() {
        return TIME_MULTIPLIER;
    }
}
