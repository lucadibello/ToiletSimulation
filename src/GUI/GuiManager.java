package GUI;

import Characters.Student;
import Extra.TimeManager;

/**
 * This class is used for manage the GUI dynamically
 * @author Luca Di Bello
 */
public class GuiManager {

    private MainWindow frame;
    
    public GuiManager(MainWindow frame) {
        this.frame = frame;
    }
    
    public void addStudentPainting(Student student){
        if(student.isInBathroom()){
            BathroomPanel.addStudents(student);
        }
    }
    
    /**
     * Questo metodo si occupa di aggiornare il label della data e del tempo.
     */
    public void startDateTimeUpdater(TimeManager tManager, int timeBetweenUpdates){
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
                        log("[Error] Can't update date and time");
                    }
                }
            }
        });  
        dateTimeUpdater.start();
    }
    
    public void log(String text){
        frame.textAreaLog.append(text + "\n");
    }
}
