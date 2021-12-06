package local.culturalprogramation.domain.programtion;

import java.time.DayOfWeek;
import java.time.LocalDate;


import local.culturalprogramation.domain.theater.Theater;
import local.culturalprogramation.domain.theater.TheaterStatus;
/**
 * Contains, for each 3 principals theater, a boolean tab wich say if the day is occupied  
 */
public class PersonalBitMap {
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
     * @return TheaterDate contanning the date to set in wich theater, null if full
     */
    public TheaterDate findDate(int year,int week){
        LocalDate date = LocalDate.now();
        date = date.withYear(year);
        int firstMonday = findFirstMonday(date);
        TheaterDate bestDay = findTheBestDay(week, firstMonday,date);
        
        return bestDay;
        

    }

    public TheaterDate findDateRange(int year,int week,int range){
        if(range > 6){
            return  null;
        }
        LocalDate date = LocalDate.now();
        date = date.withYear(year);
        int firstMonday = findFirstMonday(date);
        TheaterDate bestDay = findBestDayRange(week, firstMonday,date,range);
        
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
     * @param week int of wich week too checkif (!theater1[start+i]){
                return  new TheaterDate(theaters[0], date.withDayOfYear(start+i));
            }
            if (!theater2[start+i]){
                return  new TheaterDate(theaters[1], date.withDayOfYear(start+i));
            }
            if (!theater3[start+i]){
                return  new TheaterDate(theaters[2], date.withDayOfYear(start+i));
            }
     * @param firstMonday int of the first monday of the year
     * @param date date with year set
     * @return TheatherDate contaning a day to set and in wich theater, null if full
     */
    private TheaterDate findTheBestDay(int week, int firstMonday,LocalDate date){
        
        int start = (week -1)*7+firstMonday;
        for (int i = 5; i<7; i++){
            if (!theater1[start+i]){
                theater1[start+i] = true;
                return  new TheaterDate(theaters[0], date.withDayOfYear(start+i));
            }
            if (!theater2[start+i]){
                theater2[start+i] = true;
                return  new TheaterDate(theaters[1], date.withDayOfYear(start+i));
            }
            if (!theater3[start+i]){
                theater3[start+i] = true;
                return  new TheaterDate(theaters[2], date.withDayOfYear(start+i));
            }
        }
        for (int  i = 0; i<5; i++){
            if (!theater1[start+i]){
                theater1[start+i] = true;
                return  new TheaterDate(theaters[0], date.withDayOfYear(start+i));
            }
            if (!theater2[start+i]){
                theater2[start+i] = true;
                return  new TheaterDate(theaters[1], date.withDayOfYear(start+i));
            }
            if (!theater3[start+i]){
                theater3[start+i] = true;
                return  new TheaterDate(theaters[2], date.withDayOfYear(start+i));
            }
        }

        return null;
        
    }

    private TheaterDate findBestDayRange(int week, int firstMonday,LocalDate date,int range){
        int start = (week -1)*7+firstMonday;
        boolean allFree = true;
        for (int i = 5; i<7; i++){
            boolean [] selectedTheater = null;
            int theaterNum = -1;
            if (!theater1[start+i]){
                theaterNum = 0;
                selectedTheater = theater1;
            }
            else if (!theater2[start+i]){
                theaterNum = 1;
                selectedTheater = theater2;
            }
            else if (!theater3[start+i]){
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
            boolean [] selectedTheater = null;
            int theaterNum = -1;
            if (!theater1[start+i]){
                theaterNum = 0;
                selectedTheater = theater1;
            }
            else if (!theater2[start+i]){
                theaterNum = 1;
                selectedTheater = theater2;
            }
            else if (!theater3[start+i]){
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
