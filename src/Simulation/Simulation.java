package Simulation;

import Characters.Student;
import Extra.TimeManager;
import GUI.GuiManager;
import GUI.MainWindow;
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
    public static final Soap soapContainer = new Soap();
    
    /**
     * Describes the paper towel container.
     */
    public static final PaperTowel paperContainer = new PaperTowel();
    
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
    private Student[] students;
    
    /**
     * Describes the time multiplier
     */
    private static int timeMultiplier = 1000;
    
    /**
     * Constructor.
     * @param students Students that will be in the simulation.
     */
    public Simulation(Student[] students,GuiManager gManager) {
        this.students = students;
        this.tManager = new TimeManager(timeMultiplier,students);
        this.gManager = gManager;

        this.start();
        
        gManager.log("[Info] Simulation started :)");
    }
    
    @Override
    public void run(){
        //Gestisce tutto il ciclo
        gManager.startDateTimeUpdater(tManager,10);
    }
    
    public static int getTimeMultiplier() {
        return timeMultiplier;
    }
}
