package local.culturalprogramation.domain.programtion;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import local.culturalprogramation.domain.events.Concert;
import local.culturalprogramation.domain.events.Play;
import local.culturalprogramation.domain.programtion.PersonalBitMap.TheaterDate;
import local.culturalprogramation.domain.theater.Theater;
import local.culturalprogramation.domain.theater.WeeklyOpeningHours;

public class Programation {
    int year;
    WeeklyOpeningHours atablesHours = new WeeklyOpeningHours();
    Theater Atabal = new Theater("Atabal","DateTimePlanning/",350);

    WeeklyOpeningHours crakatoaHours  = new WeeklyOpeningHours();
    Theater Krakatoa = new Theater("Krakatoa","DateTimePlanning/",350);

    WeeklyOpeningHours arenaHours  = new WeeklyOpeningHours();
    Theater Arena = new Theater("Arena","DateTimePlanning/",100);

    WeeklyOpeningHours galaxieHours  = new WeeklyOpeningHours();
    Theater Galaxie = new Theater("Galaxie","DateTimePlanning/",350);

    private static Programation instance = null;

    private PersonalBitMap bitMap;
    private Theater[] theaterTab;

    private Programation(){
        bitMap = new PersonalBitMap(Atabal, Galaxie, Krakatoa,LocalDate.now().getYear());
        theaterTab = new Theater[4];
        theaterTab[0] = Atabal;
        theaterTab[1] = Galaxie;
        theaterTab[2] = Krakatoa;
        theaterTab[3] = Arena;
    }

    public static Programation getInstance(){
        if (instance == null)
            instance = new Programation();
        return instance;
    }
    /**
     * Set the year of programmation, reset the bitMap
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
        bitMap = new PersonalBitMap(Atabal, Galaxie, Krakatoa,year);
    }

    /**
     * Try to set a concert in one of the theaters
     * @param name the name of the concert
     * @param capacityDesired the capacity needed for the concert
     * @param week the week to try to set the concert in, will set in one of the next week if no place is found
     * @return The concert informations
     */
    public String setConcert(String name,int capacityDesired, int week) {
        TheaterDate td = null;
        do{
            boolean[] theaterToTest = new boolean[3];
            theaterToTest[0] = (theaterTab[0].getTheaterCapacity() > capacityDesired);
            theaterToTest[1] = (theaterTab[1].getTheaterCapacity() > capacityDesired);
            theaterToTest[2] = (theaterTab[2].getTheaterCapacity() > capacityDesired);
            td = bitMap.findDate(year, week, theaterToTest);
            if(td==null){
                if(week>52){
                    throw new RuntimeException("Error: No more space in this year\n");
                }
                System.err.println("Warning: no place of the concert this week, next week will be tested");
                week += 1;
            }
        }while(td==null);
        Concert crt =  new Concert(name, td.getDate(), capacityDesired);
        td.getTheater().setDayEvent(td.getDate(), crt);
        return crt.toString() + " at " + td.getTheater().getName();
    }
    
    /**
     * Try to set a play in one of the theaters
     * @param name the name of the Company
     * @param capacityDesired the capacity needed for the play
     * @param range the range of days desired for the play
     * @param week the week to try to set the play in, will set in one of the next week if no place is found
     * @return The play informations
     */
    public String setPlay(String name,int capacityDesired,int range, int week) {
        TheaterDate td = null;
        do{
            boolean[] theaterToTest = new boolean[3];
            theaterToTest[0] = (theaterTab[0].getTheaterCapacity() > capacityDesired);
            theaterToTest[1] = (theaterTab[1].getTheaterCapacity() > capacityDesired);
            theaterToTest[2] = (theaterTab[2].getTheaterCapacity() > capacityDesired);
            td = bitMap.findDate(year, week, theaterToTest);
            if(td==null){
                if(week>52){
                    throw new RuntimeException("Error: No more space in this year\n");
                }
                System.err.println("Warning: no place of the concert this week, next week will be tested");
                week += 1;
            }
        }while(td==null);
        Play pl =  new Play(name, td.getDate(),td.getDate().plusDays(range-1), capacityDesired);
        for(int i =0; i < range; i ++)
            td.getTheater().setDayEvent(td.getDate().plusDays(i), pl);
        return pl.toString()+ " at " + td.getTheater().getName();
    }
    /**
     * Display all the event of a theater in a precise week
     * @param name name of the theater to display
     * @param week week to display
     * @return String contenning the display
     */
    public String displayTheater(String name, int week) {
        Theater theater = null;
        try {
            theater = findTheater(name);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        String ret= name + " : \n";
        List<LocalDate> weekdates = dateOfWeek(week);
        for (LocalDate date : weekdates) {
            ret = ret + "\t" +theater.getDateInfo(date) +"\n";
        }
        return ret;
    }

    
    public boolean removeEventPlay(String name, LocalDate date) {
        return false;
    }

    public boolean removeEventConcert(String name, LocalDate date) {
        return false;
    }
    
    public String save() {
        return null;
    }

    public String load(String path) {
        return null;
    }

    public String displayTheaterHours(String name) {
        return null;
    }
    
    public void close(String theater, LocalDate date){
    }

    public void change(String theater, LocalDateTime dateo, LocalDateTime datef) {
    }

    
    private Theater findTheater(String name){
        if(name == "Atabal")
            return Atabal;
            if(name == "Krakatoa")
            return Krakatoa;
        if(name == "Galaxie")
        return Galaxie;
        if(name=="Arena")
        return Arena;
        
        throw new RuntimeException("Error: Theater name is invalid\n");
        

    }
    /**
     * Find the first monday of the year
     * @param date a date with year set
     * @return int of the firstMonday   
     */
    private int findFirstMonday(LocalDate date){
        for (int i=1; i<=7; i++ ){
            if(date.withDayOfYear(i).getDayOfWeek() == DayOfWeek.MONDAY){
                return i;
            }
        }
        return 0;
    }


    /**
     * Create a list of all dates of a week
     * @param week week to get dates
     * @return List of LocalDate containing all the date of a week
     */
    private List<LocalDate> dateOfWeek(int week) {
    List<LocalDate> weekdates = new ArrayList<>();
        LocalDate dateWithYear = LocalDate.now();
        dateWithYear = dateWithYear.withYear(this.year);
        int firstMonday = findFirstMonday(dateWithYear);
        dateWithYear= dateWithYear.withDayOfYear(firstMonday);
        for(int i  = DayOfWeek.MONDAY.getValue(); i<=DayOfWeek.SUNDAY.getValue();i++){
            LocalDate newDate = dateWithYear.plusWeeks(week-1).plusDays(i-1);
            weekdates.add(newDate);
        }
        return weekdates;
    }
}
