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
 * 
 * @author Luca Di Bello
 */
public class BathroomPanel extends JPanel{
    public static List<Student> students = new ArrayList<>(); 
    
            
    public static void addStudent(Student a){
        students.add(a);
    }
    
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

    private int calculateMaxStudentsX(){
        return (int) getWidth() / (Student.STUDENT_RADIUS*2);
    }
    
    private int calculateMaxStudentsY(){
        return (int) getHeight() / (Student.STUDENT_RADIUS*2);
    }
            
    /**
    * Draw a String centered in the middle of a Rectangle.
    *
    * @param g The Graphics instance.
    * @param text The String to draw.
    * @param rect The Rectangle to center the text in.
    * @param font Font used for the string.
    */
   private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font, Color color) {
       g.setColor(color);
       // Get the FontMetrics
       FontMetrics metrics = g.getFontMetrics(font);
       // Determine the X coordinate for the text
       int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
       // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
       int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
       // Set the font
       g.setFont(font);
       // Draw the String
       g.drawString(text, x, y);
   }
}
