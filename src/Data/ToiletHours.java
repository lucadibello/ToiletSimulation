package Data;

import java.sql.Time;

/**
 * This class describes the hours where the toilet is avaible.
 * @author Luca Di Bello
 */
public abstract class ToiletHours {
    /**
     * Time when the toilet is being opened.
     */
    public static final Time TOILET_START = new Time(1, 0, 0);
    
    /**
     * Time when the toilet is being closed.
     */
    public static final Time TOILET_END = new Time(17,30,0);
}
