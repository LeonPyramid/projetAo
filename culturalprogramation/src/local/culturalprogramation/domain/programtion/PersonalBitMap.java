package local.culturalprogramation.domain.programtion;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;


import local.culturalprogramation.domain.theater.Theater;
import local.culturalprogramation.domain.theater.TheaterStatus;
/**
 * Contains, for each 3 principals theater, a boolean tab wich say if the day is occupied  
 */
public class PersonalBitMap implements Serializable {
    private static final long serialVersionUID = 1L;
    class TheaterDate{
        private Theater theater;
        private LocalDate date;

        public TheaterDate(Theater theater, LocalDate date){
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
    
    
    public PersonalBitMap(Theater one, Theater two, Theater three, int year){
        theaters[0] = one;
        theaters[1] = two;
        theaters[2] = three;
        LocalDate dateForYear = LocalDate.now().withYear(year);
        int firstMonday = findFirstMonday(dateForYear);
        /*Create empty date for the first week in each theater, if day of week is closed, set all the 
        entry for this day of week as true*/
        TheaterStatus ts = null;
        for( int i = 0 ; i < 7 ; i++){
            LocalDate d = LocalDate.now().withYear(year).withDayOfYear(firstMonday+i);
            
            ts = one.getDateStatus(d);
            if(ts == TheaterStatus.CLOSED){
                for(int week = 0; week < 53; week++){
                    int wday = (week)*7+firstMonday;
                    if(wday < 365)
                        theater1[wday] = true;
                }
            }
            ts = two.getDateStatus(d);
            if(ts == TheaterStatus.CLOSED){
                for(int week = 0; week < 53; week++){
                    int wday = (week)*7+firstMonday;
                    if(wday < 365)
                        theater2[wday] = true;
                }
            }
            ts = three.getDateStatus(d);
            if(ts == TheaterStatus.CLOSED){
                for(int week = 0; week < 53; week++){
                    int wday = (week)*7+firstMonday;
                    if(wday < 365)
                        theater3[wday] = true;
                }
            }

        }
        
    }

    /**
     * Find a date to set an event
     * @param year year to program
     * @param week wich week to check
     * @param theaterToTest MUST BE A THREE BOOLEAN TAB, each boolean corresponding to one of the theater and telling if it needs to be tested
     * @return TheaterDate contanning the date to set in wich theater, null if full
     */
    public TheaterDate findDate(int year,int week,boolean[] theaterToTest){
        LocalDate date = LocalDate.now();
        date = date.withYear(year);
        int firstMonday = findFirstMonday(date);
        TheaterDate bestDay = findTheBestDay(week, firstMonday,date,theaterToTest);
        
        return bestDay;
        

    }

    /**
     * Find a group of juxtaposed dates
     * @param year  year of program
     * @param week  which week to check
     * @param range the number of days
     * @param theaterToTest MUST BE A THREE BOOLEAN TAB, each boolean corresponding to one of the theater and telling if it needs to be tested
     * @return TheaterDate containing the date to set in wich theater, null if full or not able to set the theater in this range
     */
    public TheaterDate findDateRange(int year,int week,int range,boolean[] theaterToTest){
        if(range > 6){
            return  null;
        }
        LocalDate date = LocalDate.now();
        date = date.withYear(year);
        int firstMonday = findFirstMonday(date);
        TheaterDate bestDay = findBestDayRange(week, firstMonday,date,range,theaterToTest);
        
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
     * @param theaterToTest MUST BE A THREE BOOLEAN TAB, each boolean corresponding to one of the theater and telling if it needs to be tested
     * @return TheatherDate contaning a day to set and in wich theater, null if full
     */
    private TheaterDate findTheBestDay(int week, int firstMonday,LocalDate date,boolean[] theaterToTest){
        
        int start = (week -1)*7+firstMonday;
        for (int i = 5; i<7; i++){
            if (!theater1[start+i] && theaterToTest[0]){
                theater1[start+i] = true;
                return  new TheaterDate(theaters[0], date.withDayOfYear(start+i));
            }
            if (!theater2[start+i] && theaterToTest[1]){
                theater2[start+i] = true;
                return  new TheaterDate(theaters[1], date.withDayOfYear(start+i));
            }
            if (!theater3[start+i] && theaterToTest[2]){
                theater3[start+i] = true;
                return  new TheaterDate(theaters[2], date.withDayOfYear(start+i));
            }
        }
        for (int  i = 0; i<5; i++){
            if (!theater1[start+i] && theaterToTest[0]){
                theater1[start+i] = true;
                return  new TheaterDate(theaters[0], date.withDayOfYear(start+i));
            }
            if (!theater2[start+i] && theaterToTest[1]){
                theater2[start+i] = true;
                return  new TheaterDate(theaters[1], date.withDayOfYear(start+i));
            }
            if (!theater3[start+i] && theaterToTest[2]){
                theater3[start+i] = true;
                return  new TheaterDate(theaters[2], date.withDayOfYear(start+i));
            }
        }

        return null;
        
    }

    /**
     * Find The best day to set an event in the week and with range day free after him.
     * WARNING: The day wan be in the week before. The function just compute for a part of the date in the range being in this week.
     * @param week int of wich week too check
     * @param firstMonday int of the first monday of the year
     * @param date date with year set
     * @param range the nubmer of days
     * @param theaterToTest MUST BE A THREE BOOLEAN TAB, each boolean corresponding to one of the theater and telling if it needs to be tested
     * @return TheatherDate contaning a day to set and in wich theater, null if full or if no range of juxtaposed date
     */
    private TheaterDate findBestDayRange(int week, int firstMonday,LocalDate date,int range,boolean[] theaterToTest){
        int start = (week -1)*7+firstMonday;
        for (int i = 5; i<7; i++){
            boolean allFree = true;
            boolean [] selectedTheater = null;
            int theaterNum = -1;
            if (!theater1[start+i] && theaterToTest[0]){
                theaterNum = 0;
                selectedTheater = theater1;
            }
            else if (!theater2[start+i] && theaterToTest[1]){
                theaterNum = 1;
                selectedTheater = theater2;
            }
            else if (!theater3[start+i] && theaterToTest[2]){
                theaterNum = 2;
                selectedTheater = theater3;
            }
            if(selectedTheater != null){
                for( int j = 0; j > -range; j --){
                    allFree = allFree && (!selectedTheater[i+j]);
                }
            }
            if (allFree){
                for(int j = 0; j > -range; j --){
                    selectedTheater[start+i+j] = true;
                }
                return new TheaterDate(theaters[theaterNum],date.withDayOfYear(start+i-(range+1)));
            }
        }
        for (int  i = 0; i<5; i++){
            boolean allFree = true;
            boolean [] selectedTheater = null;
            int theaterNum = -1;
            if (!theater1[start+i] && theaterToTest[0]){
                theaterNum = 0;
                selectedTheater = theater1;
            }
            else if (!theater2[start+i] && theaterToTest[1]){
                theaterNum = 1;
                selectedTheater = theater2;
            }
            else if (!theater3[start+i] && theaterToTest[2]){
                theaterNum = 2;
                selectedTheater = theater3;
            }
            if(selectedTheater != null){
                for( int j = 0; j > -range; j --){
                    allFree = allFree && (!selectedTheater[i+j]);
                }
            }
            if (allFree){
                for(int j = 0; j > -range; j --){
                    selectedTheater[start+i+j] = true;
                }
                return new TheaterDate(theaters[theaterNum],date.withDayOfYear(start+i-(range+1)));
            }
        }

        return null;
    }



}
