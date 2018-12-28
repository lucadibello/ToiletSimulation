package GUI;


import Characters.Student;
import Objects.Toilet;
import java.awt.Color;
import java.awt.Graphics;
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
}
