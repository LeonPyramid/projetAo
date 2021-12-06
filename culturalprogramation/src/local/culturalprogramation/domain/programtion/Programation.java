package local.culturalprogramation.domain.programtion;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public char[] displayTheater(String name, int week) {
        return null;
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

    public char[] displayTheaterHours(String name) {
        return null;
    }

    public void close(String theater, LocalDate date) {
    }

    public void change(String theater, LocalDateTime dateo, LocalDateTime datef) {
    }


}
