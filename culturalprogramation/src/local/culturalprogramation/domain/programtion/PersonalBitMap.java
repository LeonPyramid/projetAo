package local.culturalprogramation.domain.programtion;

import java.time.DayOfWeek;
import java.time.LocalDate;


import local.culturalprogramation.domain.theater.Theater;
/**
 * Contains, for each 3 principals theater, a boolean tab wich say if the day is occupied  
 */
public class PersonalBitMap {
    class TheatherDate{
        private Theater theater;
        private LocalDate date;

        public TheatherDate(Theater theater, LocalDate date){
            this.theater=theater;
            this.date=date;
        }

        public LocalDate getDate() {
            return date;
        }
        public Theater getTheater() {
            return theater;
        }
    }
    
    Theater [] theaters = new Theater[3];
    boolean [] theater1 = new boolean [366];
    boolean [] theater2 = new boolean [366];
    boolean [] theater3 = new boolean [366];
    
    
    public PersonalBitMap(Theater one, Theater two, Theater three){
        theaters[0] = one;
        theaters[1] = two;
        theaters[2] = three;
    }

    /**
     * Find a date to set an event
     * @param year year to program
     * @param week wich week to check
     * @return TheaterDate contanning the date to set in wich theater, null if full
     */
    public TheatherDate findDate(int year,int week){
        LocalDate date = LocalDate.now();
        date = date.withYear(year);
        int firstMonday = findFirstMonday(date);
        TheatherDate bestDay = findTheBestDay(week, firstMonday,date);
        
        return bestDay;
        

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
     * Find The best day to set an event in the week
     * @param week int of wich week too check
     * @param firstMonday int of the first monday of the year
     * @param date date with year set
     * @return TheatherDate contaning a day to set and in wich theater, null if full
     */
    private TheatherDate findTheBestDay(int week, int firstMonday,LocalDate date){
        
        int start = (week -1)*7+firstMonday;
        for (int i = 5; i<7; i++){
            if (!theater1[i]){
                return  new TheatherDate(theaters[1], date.withDayOfYear(start+i));
            }
            if (!theater2[i]){
                return  new TheatherDate(theaters[2], date.withDayOfYear(start+i));
            }
            if (!theater3[i]){
                return  new TheatherDate(theaters[3], date.withDayOfYear(start+i));
            }
        }
        for (int  i = 0; i<5; i++){
            if (!theater1[i]){
                return  new TheatherDate(theaters[1], date.withDayOfYear(start+i));
            }
            if (!theater2[i]){
                return  new TheatherDate(theaters[2], date.withDayOfYear(start+i));
            }
            if (!theater3[i]){
                return  new TheatherDate(theaters[3], date.withDayOfYear(start+i));
            }
        }

        return null;
        
    }


}
