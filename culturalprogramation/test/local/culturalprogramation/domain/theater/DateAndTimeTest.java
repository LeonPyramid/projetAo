package local.culturalprogramation.domain.theater;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateAndTimeTest {
    
    private WeeklyOpeningHours woh = new WeeklyOpeningHours();
    
    
    @Test
    void isWellInitialized(){
        String espectedRes = "[00:00,00:00]";
        String res = woh.getDayHours(DayOfWeek.MONDAY);
        assertTrue(espectedRes.equals(res));
    }

    @Test
    void isEvolvingHour(){
        String espectedRes = "[10:15,00:00]";
        woh.setOpeningHour(DayOfWeek.MONDAY, 10, 15);
        String res = woh.getDayHours(DayOfWeek.MONDAY);
        assertTrue(espectedRes.equals(res));
        res = woh.getDayHours(DayOfWeek.TUESDAY);
        assertTrue(!espectedRes.equals(res));
    }

    @Test
    void isLoadingTime(){
        Theater th = new Theater("test","/home/leon/travail/cremi/approche_objet/projet/DateTimePlanning/");
        LocalDate ldt = LocalDate.of(2021, 05, 10);
        th.CreateDate(ldt);
        String inf = th.getDateInfo(ldt);
        String testInf = "2021-05-10 OPEN 10:00,14:30;";
        assertEquals(inf, testInf);
    }

}
