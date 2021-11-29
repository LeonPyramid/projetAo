package local.culturalprogramation.domain.theater;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

public class DateAndTimeTest {
    
    private WeeklyOpeningHours woh = new WeeklyOpeningHours();
    
    
    @Test
    void isWellInitialized(){
        String espectedRes = "[00:00,00:00]";
        String res = woh.getDayHours(Calendar.MONDAY);
        assertTrue(espectedRes.equals(res));
    }

    @Test
    void isEvolvingHour(){
        String espectedRes = "[10:15,00:00]";
        woh.setOpeningHour(Calendar.MONDAY, 10, 15);
        String res = woh.getDayHours(Calendar.MONDAY);
        assertTrue(espectedRes.equals(res));
        res = woh.getDayHours(Calendar.TUESDAY);
        assertTrue(!espectedRes.equals(res));
    }

}
