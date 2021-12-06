package local.culturalprogramation.domain.programtion;

import local.culturalprogramation.domain.events.Concert;
import local.culturalprogramation.domain.events.Play;
import local.culturalprogramation.domain.theater.Theater;
import local.culturalprogramation.domain.theater.WeeklyOpeningHours;

public class Programation {
    int year;
    WeeklyOpeningHours atablesHours = new WeeklyOpeningHours();
    //TODO CREER LES HORAIRES 
    Theater Atabal = new Theater("Atabal",null,350);

    WeeklyOpeningHours crakatoaHours  = new WeeklyOpeningHours();
    //TODO CREER LES HORAIRES
    Theater Krakatoa = new Theater("Krakatoa",null,350);

    WeeklyOpeningHours arenaHours  = new WeeklyOpeningHours();
    //TODO CREER LES HORAIRES
    Theater Arena = new Theater("Arena",null,100);

    WeeklyOpeningHours galaxieHours  = new WeeklyOpeningHours();
    //TODO CREER LES HORAIRES
    Theater Galaxie = new Theater("Galaxie",null,350);

    private static final Programation instance = new Programation();

    private PersonalBitMap bitMap = new PersonalBitMap(Atabal, Galaxie, Krakatoa);

    private Programation(){}

    public static Programation getInstance(){
        return instance;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public boolean setEvent(Concert concert, int week) {
        //TODO FONCTION TRUE if SET 
        return false;
    }
    

    public boolean setEvent(Play play, int week) {
        return false;
    }


}
