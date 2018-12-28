package GUI;

import Characters.Student;
import Extra.TimeManager;
import javax.swing.JFrame;

/**
 *
 * @author Luca Di Bello
 */
public class GuiManager {

    private MainWindow frame;
    private TimeManager tManager;
    
    public GuiManager(MainWindow frame, TimeManager tManager) {
        this.frame = frame;
        this.tManager = tManager;
    }
    
    public void addStudentPainting(Student student){
        if(student.isInBathroom()){
            BathroomPanel.addStudents(student);
        }
    }
    
    /**
     * Questo metodo si occupa di aggiornare il label della data e del tempo.
     */
    public void startDateTimeUpdater(int timeBetweenUpdates){
        //Start new method in different thread
        Thread dateTimeUpdater = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(timeBetweenUpdates);
                        
                        frame.labelTime.setText("Time: " + tManager.getTime(":"));
                        frame.labelDate.setText("Date: " + tManager.getCurrentDate("-"));
                    }
                    catch(InterruptedException ex){
                        System.err.println("[Error] Can't update date and time");
                    }
                }
            }
        });  
        dateTimeUpdater.start();
    }
}
