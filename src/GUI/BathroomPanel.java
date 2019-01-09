package GUI;

import Characters.Student;
import Data.ToiletHours;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import javax.swing.JPanel;

/**
 * This panel it's the container where the visual simulation it's shown.
 * @author Luca Di Bello
 */
public class BathroomPanel extends JPanel{
    public static List<Student> students = new ArrayList<>(); 
    
    /**
     * This methods it's used for adding a student to the visual simulation.
     * @param a Student to add to the simulation.
     */
    public static void addStudent(Student a){
        students.add(a);
    }
    
    /**
     * This methods it's used for adding a group of students to the visual simulation.
     * @param all Students to add to the simulation.
     */
    public static void setStudents(Student[] all){
        students.addAll(Arrays.asList(all));
    }

    @Override
    public void paintComponent(Graphics g){
        if(MainWindow.bathroomStatus){
            //Set background
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());
            
            //Calculate how much students on x and y axis
            int xCount = calculateMaxStudentsX();
            int yCount = calculateMaxStudentsY();
            
            int pointer = 0;
            //WIP -> It doesn't work
            for(int i = 0; i < yCount;i++){
                for(int j = 0; j < xCount; j++){
                    if(pointer < students.size()){
                        Student student = students.get(pointer);
                        
                        student.setPaintPosition(new Point(j,i));
                        student.paint(g);
                    }
                    else{
                        break;
                    }
                    
                    pointer++;
                }
            }
        }
        else{
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            
            drawCenteredString(
                    g,
                    "Bathroom is closed, it will open at " + ToiletHours.TOILET_START,
                    new Rectangle(0,0,getWidth(),getHeight()),
                    new Font("SansSerif", Font.BOLD, 14),
                    Color.WHITE
            );
        }
    }

    /**
     * This method it's used for calculating how many students can be shown in the x axis.
     * @return Number of possible students on x axis.
     */
    private int calculateMaxStudentsX(){
        return (int) getWidth() / (Student.STUDENT_RADIUS*2);
    }
    
    /**
     * This method it's used for calculating how many students can be shown in the y axis.
     * @return Number of possible students on y axis.
     */
    private int calculateMaxStudentsY(){
        return (int) getHeight() / (Student.STUDENT_RADIUS*2);
    }
            
    /**
    * This method draw a string centered in the middle of a determinated area
    * 
    * @param g Graphics object.
    * @param text String to display.
    * @param rect The area where the text it's centered.
    * @param font Font used for the string.
    */
   private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font, Color color) {
       g.setColor(color);
       FontMetrics metrics = g.getFontMetrics(font);
       int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
       int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
       g.setFont(font);
       g.drawString(text, x, y);
   }
}
