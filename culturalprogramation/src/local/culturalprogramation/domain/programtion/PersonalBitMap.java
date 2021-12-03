package local.culturalprogramation.domain.programtion;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test.None;

import local.culturalprogramation.domain.theater.Theater;

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

    public TheatherDate findDate(int year,int week){
        LocalDate date = LocalDate.now();
        date = date.withYear(year);
        int firstMonday = findFirstMonday(date);
        TheatherDate bestDay = findTheBestDay(week, firstMonday,date);
        
        return bestDay;
        

    }

    private int findFirstMonday(LocalDate date){
        for (int i=1; i<=7; i++ ){
            if(date.withDayOfYear(i).getDayOfWeek() == DayOfWeek.MONDAY){
                return i;
            }
        }
        return 0;
    }

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
