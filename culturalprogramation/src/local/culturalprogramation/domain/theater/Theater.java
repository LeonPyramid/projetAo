package local.culturalprogramation.domain.theater;

import java.util.Calendar;
import java.util.Hashtable;

public class Theater {
    private int theaterCapacity;
    private Hashtable<Calendar,TheaterDateInformation> theaterDate;

    public int getTheaterCapacity() {
        return theaterCapacity;
    }
}
