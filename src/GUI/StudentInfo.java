package GUI;

import Characters.Student;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Luca Di Bello
 */
public class StudentInfo extends JFrame{

    private static Student student;
    
    public StudentInfo(Student student) {
        super(student.getName() + " | Informations");
        
        this.student = student;
        this.setSize(500,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        build();
    }
    
    public void build(){
        JPanel p = new JPanel(new GridLayout(7, 2));
        p.setBackground(Color.white);
        
        p.add(new JLabel("Student name"));
        p.add(new JLabel(student.getName()));
        
        p.add(new JLabel("School department"));
        p.add(new JLabel(student.getDepartment().toString()));
        
        p.add(new JLabel("School year"));
        p.add(new JLabel(Integer.toString(student.getSchoolYear())));
        
        p.add(new JLabel("Times in bathroom per day"));
        p.add(new JLabel(Integer.toString(student.getTimesPerDay())));
        
        p.add(new JLabel("Current times in bathroom"));
        p.add(new JLabel(Integer.toString(student.getTimesInBathroom())));
        
        p.add(new JLabel("Current times without washing hands: "));
        p.add(new JLabel(Integer.toString(student.getTimesNoWash())));
        
        p.add(new JLabel("Current times without drying hands: "));
        p.add(new JLabel(Integer.toString(student.getTimesNoDry())));
        
        this.setContentPane(p);
    }
}
