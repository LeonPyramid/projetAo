package local.culturalprogramation.domain.theater;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.time.LocalDateTime;

import local.culturalprogramation.domain.events.Event;

/**
 * Class representing a theather.
 *
 */
public class Theater {

    private String name;
    /**
     * Integer representing the capacity of the theater
     */
    private int theaterCapacity;
    /**
     * Give acces for each day at the information of the theather that day.
     */
    private Hashtable<LocalDateTime,TheaterDateInformation> theaterDate; //Tous les jours de l'année
    
    /**
     * Represent typical weekly hours of the theater
     */
    private WeeklyOpeningHours openingHours;

    public Theater(String name,WeeklyOpeningHours openingHours){
        this.name =name;
        this.openingHours = openingHours;
        theaterDate = new Hashtable<LocalDateTime,TheaterDateInformation>();
    }

    /**
     * Get the theater capacity
     * @return Int representing theatherCapacity
     */
    public int getTheaterCapacity() {
        return theaterCapacity;
    }
    
    /**
     * Set in theaterDate the opening and closing hours of given the day
     * @param date day to set the hours
     */
    public void setDayOpen(LocalDateTime date){
        if(theaterDate.contains(date))
            return; // TODO faire une erreur
        int dayofweek  = date.getDayOfWeek().getValue();
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

        LocalDateTime opening = date.withMinute(intTab.get(1)).withHour(intTab.get(0));
        LocalDateTime closing = date.withMinute(intTab.get(3)).withHour(intTab.get(2));
        TheaterDateInformation newInformation  = new TheaterDateInformation(opening,closing);
        
        theaterDate.put(date,newInformation);  

    }
    
    /**
     * Set in theaterDate, the event given of the day
     * @param date day to set the event
     * @param event the event to set
     */
    public void setDayEvent(LocalDateTime date, Event event){
        if(!theaterDate.contains(date))
            return; //TODO faire une erreur
        if(event.getDesiredCapacity()>this.theaterCapacity)
            return; //TODO faire une erreur
        
        TheaterDateInformation tdi = theaterDate.get(date);
        tdi.setEvent(event);  
    }
    /**
     * Remove in theaterDate, the event of the given day
     * @param date day to remove eventh
     */
    public void removeDayEvent(LocalDateTime date){
        if(!theaterDate.contains(date))
            return; //TODO faire une erreur
        TheaterDateInformation tdi = theaterDate.get(date);
        tdi.removeEvent();  
    }
 
    
    public void setWeeklyDayOpeningHour(int day, int hour, int min ){
    
        openingHours.setOpeningHour(day ,hour, min);
    }
    public void setWeeklyDayClosingHour(int day, int hour, int min){
        openingHours.setClosingHour(day, hour, min);
        
    }

   
    
}
