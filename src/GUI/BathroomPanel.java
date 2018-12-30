package GUI;


import Characters.Student;
import Data.ToiletHours;
import Objects.Toilet;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Luca Di Bello
 */
public class BathroomPanel extends JPanel{
    public static List<Student> students = new ArrayList<>(); 
    
    public static void addStudents(Student a){
        students.add(a);
        System.out.println("Added new student to panel: " + a.toString());
    }

    @Override
    public void paintComponent(Graphics g){
        if(MainWindow.bathroomStatus){
            //Set background
            g.setColor(Color.gray);
            g.fillRect(0, 0, getWidth(), getHeight());

            Toilet toilet = new Toilet(100,100,40,120);
            toilet.paint(g);

            //Paint students if exists
            if(students.size() > 0){
                System.out.println("Painting students...");
                for(Student stud : students){
                    stud.paint(g);
                }
            }  
        }
        else{
            drawCenteredString(g, "Bathroom is closed, it will open at " + ToiletHours.TOILET_START, new Rectangle(0,0,getWidth(),getHeight()), new Font("Verdana", Font.BOLD, 12));
        }
    }
    
    /**
    * Draw a String centered in the middle of a Rectangle.
    *
    * @param g The Graphics instance.
    * @param text The String to draw.
    * @param rect The Rectangle to center the text in.
    */
   public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
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
