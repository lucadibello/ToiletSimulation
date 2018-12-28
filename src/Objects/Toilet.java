package Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class describes a toilet.
 * @author Luca Di Bello
 */
public class Toilet {

    private Point location;
    private static String spritePath = "images/toilet.png";
    private Image image;     
    private int width = 50;
    private int height = 100;
    
    public Toilet(int x, int y, int width, int height,String spritePath) {
        this.location = new Point(x,y);
        this.width = width;
        this.height = height;
        this.spritePath = spritePath;

        loadImage(spritePath);
    }
    
    public Toilet(int x, int y, int width, int height){
        this(x,y,width,height,spritePath);
    }   
    
    private void loadImage(String path){
        try{
            image = ImageIO.read(new File(spritePath)).getScaledInstance(width, height, Image.SCALE_DEFAULT);
        }
        catch(IOException ex){
            System.out.println("Toilet image error: " + ex.getMessage());
        }
    }
    
    public void paint(Graphics g){
        g.drawImage(image, location.x, location.y, null);
    }
}
