package Data;

import java.sql.Time;

/**
 * This class describes the hours where the toilet is avaible.
 * @author Luca Di Bello
 */
public abstract class ToiletHours {
    public static final Time TOILET_START = new Time(3, 0, 0);
    public static final Time TOILET_END = new Time(17,30,0);
}
