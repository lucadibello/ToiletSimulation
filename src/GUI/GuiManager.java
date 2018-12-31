package GUI;

import Characters.Student;
import Extra.TimeManager;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

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
    
    public boolean getBathroomStatus(){
        return frame.bathroomStatus;
    }
    
    public void closeBathroom(){
        //Se è aperto lo chiudo
        if(getBathroomStatus()){
            frame.bathroomStatus = false;
            frame.repaint();
            
            log("[Info] Bathroom closed");
        }
    }
    
    public void openBathroom(){
        //Se è chiuso lo apro
        if(!getBathroomStatus()){
            frame.bathroomStatus = true;
            frame.repaint();

            log("[Info] Bathroom is now open");
        }
    }
    
    public void updateStudentHours(Student[] students){
        DefaultTableModel model = (DefaultTableModel) frame.jTableStudentBathroomHours.getModel();
        model.setRowCount(0);

        //Number of rows
        List<Object[]> rows = new ArrayList<>();
        for(Student student : students){
            System.out.println("Student: " + student.getName());
            int howManyTimes = student.getWhenBathroom().length;
            
            for(int i = 0; i < howManyTimes; i++){
                Object[] row = new Object[]{
                    student.getName(),
                    student.getDepartment(),
                    student.getSchoolYear(),
                    student.getWhenBathroom()[i]
                };

                rows.add(row);
            }
        }
        
        //Resize table
        frame.jTableStudentBathroomHours.setPreferredSize(new Dimension(
                (int) frame.jTableStudentBathroomHours
                        .getPreferredSize()
                        .getWidth(),
                (int) frame.jTableStudentBathroomHours
                        .getPreferredSize()
                        .getWidth() + 
                        rows.size() * frame.jTableStudentBathroomHours.getRowHeight()
        ));
        
        for(int i = 0; i < rows.size();i++){
            model.addRow(rows.get(i));
        }
    }
    
    public void log(String text){
        frame.textAreaLog.append(text + "\n");
    }
}
