package local.culturalprogramation.domain.programtion;

import local.culturalprogramation.domain.theater.Theater;
import local.culturalprogramation.domain.theater.WeeklyOpeningHours;

public class Programation {
    
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


}
