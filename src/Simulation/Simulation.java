package Simulation;


import Characters.Student;
import Data.SchoolDepartment;
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
    private final GUI.GuiManager gManager;
    
    /**
     * Describes the students that are in the simulation.
     */
    private Student[] students;
    
    /**
     * Constructor.
     * @param students Students that will be in the simulation.
     * @param window Program's main window.
     */
    public Simulation(Student[] students,MainWindow window) {
        this.students = students;
        this.tManager = new TimeManager(1000,students);
        this.gManager = new GuiManager(window, tManager);
        
        System.out.println(students[0].getWhenBathroom()[0]);
        this.start();
    }
    
    @Override
    public void run(){
        System.out.println("[Info] Simulation started");
        //Gestisce tutto il ciclo
        gManager.startDateTimeUpdater(10);
        
        gManager.addStudentPainting(students[0]);
        System.err.println("[Info] Simulation ended");
    }
}
