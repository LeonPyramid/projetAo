package local.culturalprogramation.domain.theaterAndProgramation;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    
    private final List<Theater> theaters;
    List<boolean []> TheatersBitmap;
    
    
    public PersonalBitMap(List<Theater> thList, int year){
        theaters = new ArrayList<Theater>();
        theaters.addAll(thList);
        TheatersBitmap = new ArrayList<boolean []>();
        for(Theater t : theaters){
            TheatersBitmap.add(new boolean[366]);
        }
        LocalDate dateForYear = LocalDate.now().withYear(year);
        int firstMonday = findFirstMonday(dateForYear);
        /*Create empty date for the first week in each theater, if day of week is closed, set all the 
        entry for this day of week as true*/
        TheaterStatus ts = null;
        for( int i = 0 ; i < 7 ; i++){
            LocalDate d = LocalDate.now().withYear(year).withDayOfYear(firstMonday+i);
            for(Theater t : theaters){
                int index = theaters.indexOf(t);
                ts = t.getDateStatus(d);
                if(ts == TheaterStatus.CLOSED){
                    for(int week = 0; week < 53; week++){
                        int wday = (week)*7+firstMonday;
                        if(wday < 365)
                            TheatersBitmap.get(index)[wday] = true;
                    }
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
            for(Theater t: theaters){
                int index = theaters.indexOf(t);
                if (!TheatersBitmap.get(index)[start+i] && theaterToTest[index]){
                    TheatersBitmap.get(index)[start+i] = true;
                    return  new TheaterDate(t, date.withDayOfYear(start+i));
                }
            }
        }
        for (int  i = 0; i<5; i++){
            for(Theater t: theaters){
                int index = theaters.indexOf(t);
                if (!TheatersBitmap.get(index)[start+i] && theaterToTest[index]){
                    TheatersBitmap.get(index)[start+i] = true;
                    return  new TheaterDate(t, date.withDayOfYear(start+i));
                }
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
        for (int i = 6; i>4; i--){
            boolean allFree = true;
            for(Theater t: theaters){
                int index = theaters.indexOf(t);
                if(theaterToTest[index]){
                    if (!TheatersBitmap.get(index)[start+i]){
                        for( int j = 0; j > -range; j --){
                            if(start+i+j < 1){
                                allFree = false;
                                break;
                            }
                            allFree = allFree && (!TheatersBitmap.get(index)[start+i+j]);
                            
                        }
                        if (allFree){
                            for(int j = 0; j > -range; j --){
                                TheatersBitmap.get(index)[start+i+j] = true;
                            }
                            return new TheaterDate(t,date.withDayOfYear(start+i-(range)+1));
                        }
                    }
                }
            }
        }
        for (int  i = 0; i<5; i++){
            boolean allFree = true;
            for(Theater t: theaters){
                int index = theaters.indexOf(t);
                if(theaterToTest[index]){
                    if (!TheatersBitmap.get(index)[start+i]){
                        for( int j = 0; j > -range; j --){
                            if(start+i+j < 1){
                                allFree = false;
                                break;
                            }
                            allFree = allFree && (!TheatersBitmap.get(index)[start+i+j]);
                        }
                        if (allFree){
                            for(int j = 0; j > -range; j --){
                                TheatersBitmap.get(index)[start+i+j] = true;
                            }
                            return new TheaterDate(t,date.withDayOfYear(start+i-(range)+1));
                        }
                    }
                }
            }
        }

        return null;
    }



}
