package local.culturalprogramation.domain.theaterAndProgramation;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import local.culturalprogramation.domain.events.*;
import local.culturalprogramation.domain.theaterAndProgramation.PersonalBitMap.TheaterDate;



public class Programation implements Serializable {
    private static final long serialVersionUID = 1L;
    int year;
    WeeklyOpeningHours atablesHours;
    Theater Atabal;

    WeeklyOpeningHours crakatoaHours;
    Theater Krakatoa;

    WeeklyOpeningHours arenaHours;
    Theater Arena;

    WeeklyOpeningHours galaxieHours;
    Theater Galaxie;

    private static Programation instance = null;

    private PersonalBitMap bitMap;
    private Theater[] theaterTab;

    private Programation(){
        atablesHours = new WeeklyOpeningHours();
        Atabal = new Theater("Atabal","../../DateTimePlanning/",350);
        
        crakatoaHours  = new WeeklyOpeningHours();
        Krakatoa = new Theater("Krakatoa","../../DateTimePlanning/",350);
        
        arenaHours  = new WeeklyOpeningHours();
        Arena = new Theater("Arena","../../DateTimePlanning/",100);
        
        galaxieHours  = new WeeklyOpeningHours();
        Galaxie = new Theater("Galaxie","../../DateTimePlanning/",350);
        List<Theater> tList = new ArrayList<Theater>();
        tList.add(Atabal);
        tList.add(Galaxie);
        tList.add(Krakatoa);
        bitMap = new PersonalBitMap(tList,LocalDate.now().getYear());
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
        List<Theater> tList = new ArrayList<Theater>();
        tList.add(Atabal);
        tList.add(Galaxie);
        tList.add(Krakatoa);
        bitMap = new PersonalBitMap(tList,LocalDate.now().getYear());
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
            td = bitMap.findDateRange(year, week, range, theaterToTest);
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
        List<Theater> theaterList = null;
        try {
            theaterList = findTheater(name);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        List<LocalDate> weekdates = dateOfWeek(week);
        String ret = "";
        for(Theater theater : theaterList){
            ret += theater.getName() + " : \n";
            for (LocalDate date : weekdates) {
                ret = ret + "\t" +theater.getDateInfo(date) +"\n";
            }
        }
        return ret;
    }

    /**
     * Try to remove a play specified by a date
     * @param name  name of the play 
     * @param date  a representation of the play
     * @return boolean to check if done or not
     */
    public boolean removeEventPlay(String name, LocalDate date) {
        try {
            Theater theater = inwichTheater(name, date);
            theater.removeDayEventPlay(date);
            return true;
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Try to remove a concert specified by a date
     * @param name  name of the concert
     * @param date  date of the concert
     * @return boolena to check if done or not
     */
    public boolean removeEventConcert(String name, LocalDate date) {
        try {
            Theater theater = inwichTheater(name, date);
            theater.removeDayEvent(date);
            return true;
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return false;
        }
        
    }
    
    /**
     * Set the day to open
     * @param name  Name of the theater
     * @param date  Date to open
     */
    public void open(String name, LocalDate date) {
        try {
            List<Theater> theaters = findTheater(name);
            for (Theater theater : theaters){
                theater.setDayOpen(date);
            }
            return;
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return;
        }
    }

    /**
     * Set the day to close if not occupied
     * @param name Name of the theater
     * @param date Date to close
     */
    public void close(String name, LocalDate date){
        try {
            List<Theater> theaters = findTheater(name);
            for (Theater theater : theaters){
                theater.setDayClosed(date);
            }
            return;
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return;
        }
    }

    /**
     * Change the hours of a day
     * @param theater Name of the theater
     * @param date Date to change hours
     * @param openingHour New opening hour
     * @param openingMinute New opening minute
     * @param closingHour New closing hour
     * @param closingMinutes  New closing minute
     */
    public void change(String theater, LocalDate date, int openingHour, int openingMinute, int closingHour, int closingMinutes) {
        List<Theater> theaterList = findTheater(theater);
        for(Theater t : theaterList){
            try {
                t.RemoveDate(date);
            } catch (Exception e) {
                
            }
            try {
                t.CreateDate(date,openingHour,openingMinute,closingHour,closingMinutes);
            } catch (Exception e) {
                System.err.println("for " + t.getName() + " the date contains an event, hense cannot be changed");
            }
        }
    }


    /**
     * Find the theater specified by his name
     * @param name name of the theater (ALL for all theaters)
     * @return List of the theater asked
     */
    private List<Theater> findTheater(String name){
        ArrayList<Theater> retList = new ArrayList<Theater>();
        if(name.equals("Atabal"))
            retList.add(Atabal);
        else if(name.equals("Krakatoa"))
            retList.add(Krakatoa);
        else if(name.equals("Galaxie"))
            retList.add(Galaxie);
        else if(name.equals("Arena"))
            retList.add(Arena);
        else if(name.equals("ALL"))
            for(Theater t : theaterTab)
                retList.add(t);
        else
            throw new RuntimeException("Error: Theater name is invalid\n");
        return retList;

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

    /**
     * check in wich theater is a event
     * @param date date of the event
     * @param name name of the event
     * @return the theater, null if the event is in no theater
     */
    private Theater inwichTheater (String name,LocalDate date){
        for (Theater theater : theaterTab){
            Event event = theater.getDateEvent(date);
            if (event != null){
                if(event.getName().equals(name)){
                    return theater;
                }

            }         
        }
        return null;
    }

    public String displayYear(String name) {
        List<Theater> theaterList = null;
        try {
            theaterList = findTheater(name);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
        String ret = "";
        LocalDate d = LocalDate.now();
        d = d.withYear(year);
        for(Theater theater : theaterList){
            ret += theater.getName() + " : \n";
            for(int i =1 ; i < 366 ; i++){
                d = d.withDayOfYear(i);
                if(theater.getDateStatus(d)==TheaterStatus.OCCUPIED)
                    ret = ret + "\t" +theater.getDateInfo(d) +"\n";
            }
        }
        return ret;
    }

    
}
