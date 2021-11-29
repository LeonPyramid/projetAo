package local.culturalprogramation.domain.theater;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Theater {
    private int theaterCapacity;
    private Hashtable<Calendar,TheaterDateInformation> theaterDate; //Tous les jours de l'année
    private WeeklyOpeningHours openingHours;

    public Theater(WeeklyOpeningHours openingHours, int year){
        this.openingHours = openingHours;
        theaterDate = new Hashtable<Calendar,TheaterDateInformation>();
    }


    public int getTheaterCapacity() {
        return theaterCapacity;
    }

    public void setDayOpen(Calendar date){
        if(theaterDate.contains(date))
            return; // TODO faire une erreur
        int dayofweek  = date.get(Calendar.DAY_OF_WEEK);
        String hours = openingHours.getDayHours(dayofweek);
        //[00:00,00:00]
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(hours);
        List<String> tab = new ArrayList<String>();
        
        while(!m.find()){
            tab.add(m.group());   
        }

        List<Integer> intTab= new ArrayList<Integer>(4);
        for (int i =0 ;i<tab.size();i++){
            intTab.set(i, Integer.parseInt(tab.get(i)));
        }
        Calendar opening = Calendar.getInstance();
        Calendar closing = Calendar.getInstance();
        opening.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), intTab.get(0), intTab.get(1));
        closing.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), intTab.get(2), intTab.get(3));
        TheaterDateInformation newInformation  = new TheaterDateInformation(opening,closing);
        
        theaterDate.put(date,newInformation);  

    }
    
    public void setWeeklyDayOpeningHour(int day, int hour, int min ){
        openingHours.setOpeningHour(day ,hour, min);
    }

    public void setWeeklyDayClosingHour(int day, int hour, int min){
        openingHours.setClosingHour(day, hour, min);
        
    }

    /**
     * Check if year is leap
     * @param year
     * @return true if leap, false otherwise
     */
    private boolean is_leap_year(int year){
        if (year%400 ==0)
            return true;
        else if (year%100  == 0)
            return false;
        else if (year %4 == 0)
            return true;
        
        else
            return false;
   }
    
}
