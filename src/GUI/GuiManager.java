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

    private final MainWindow frame;
    
    /**
     * Constructor method
     * @param frame Main window (where all the object are contained)
     */
    public GuiManager(MainWindow frame) {
        this.frame = frame;
    }
    
    //WIP
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
    
    /**
     * Getter method for the frame's attribute bathroomStatus.
     * @return True if the bathroom is open, otherwise false.
     */
    public boolean getBathroomStatus(){
        return frame.bathroomStatus;
    }
    
    /**
     * This method is used for closing the bathroom. It will also repaint the panel.
     */
    public void closeBathroom(){
        //Se è aperto lo chiudo
        if(getBathroomStatus()){
            frame.bathroomStatus = false;
            frame.repaint();
            
            log("[Info] Bathroom closed");
        }
    }
    
    /**
     * This method is used for opening the bathroom. It will also repaint the panel.
     */
    public void openBathroom(){
        //Se è chiuso lo apro
        if(!getBathroomStatus()){
            frame.bathroomStatus = true;
            frame.repaint();

            log("[Info] Bathroom is now open");
        }
    }
    
    /**
     * This method is used for updating the "student hours" table.
     * @param students Students to add to the table.
     */
    public void updateStudentHours(Student[] students){
        DefaultTableModel model = (DefaultTableModel) frame.jTableStudentBathroomHours.getModel();
        model.setRowCount(0);

        //Number of rows
        List<Object[]> rows = new ArrayList<>();
        for(Student student : students){

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
    
    public void updateStats(){
        frame.labelPaperFill.setText(Integer.toString(Simulation.Simulation.paperContainer.getFillStatus()));
        frame.labelSoapFill.setText(Integer.toString(Simulation.Simulation.soapContainer.getFillStatus()));
        frame.labelNoWashTimes.setText(Integer.toString(Simulation.Simulation.getStatNoWash()));
        frame.labelNoDryTimes.setText(Integer.toString(Simulation.Simulation.getStatNoDry()));
        frame.labelTimesInBathroom.setText(Integer.toString(Simulation.Simulation.getTimesInBathroom()));
    }
    
    /**
     * This method writes a passed text to the textAreaLog textBox.
     * @param text Text to append.
     */
    public void log(String text){
        frame.textAreaLog.append(text + "\n");
    }
}
