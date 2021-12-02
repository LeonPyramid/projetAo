package local.culturalprogramation.domain.theater;

import java.util.Hashtable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private Hashtable<LocalDate,TheaterDateInformation> theaterDate; //Tous les jours de l'année
    
    /**
     * Represent typical weekly hours of the theater
     */
    private WeeklyOpeningHours openingHours;

    public Theater(String name,String folderPath){
        this.name =name;
        this.openingHours = new WeeklyOpeningHours();
        //TODO: get rid of local path

        this.openingHours.loadPlanningFile(folderPath+name+".dtp");
        theaterDate = new Hashtable<LocalDate,TheaterDateInformation>();

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
    public void setDayOpen(LocalDate date){
        CreateDate(date);
    }
    
    /**
     * Set in theaterDate, the event given of the day
     * @param date day to set the event
     * @param event the event to set
     */
    public void setDayEvent(LocalDate date, Event event){
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

    /**
     * Create an Open day at a given date, and set the opening and closing time to the weeklyPlanning 
     * corresponding to the Theater
     * @param date the date which need to be created
     */
    public void CreateDate(LocalDate date){
        if(theaterDate.contains(date)){
            return;//TODO: faire une erreur
        }
        int dayofweek  = date.getDayOfWeek().getValue();
        String hourData = openingHours.getDayHours(dayofweek);
        hourData = hourData.replace("[", "");
        hourData = hourData.replace("]", "");
        String split[] = hourData.split("\\:|\\,");
        int intTab[] = new int[4];
        for (int i =0 ;i<4;i++){
            intTab[i]= Integer.parseInt(split[i]);
        }
        LocalDateTime opening = LocalDateTime.of(date,LocalTime.of(intTab[0],intTab[1]));
        LocalDateTime closing = LocalDateTime.of(date,LocalTime.of(intTab[2],intTab[3]));
        TheaterDateInformation newInformation  = new TheaterDateInformation(opening,closing);
        theaterDate.put(date, newInformation);
    }
    /**
     * Create an Open day at a given date, and set the opening and closing time to the one given in parameters
     * @param date the date which need to be created
     */
    public void CreateDate(LocalDate date, int openingHour, int openingMinute, int closingHour, int closingMinutes){
        if(theaterDate.contains(date)){
            return;//TODO: faire une erreur
        }
        LocalDateTime opening = LocalDateTime.of(date,LocalTime.of(openingHour,openingMinute));
        LocalDateTime closing = LocalDateTime.of(date,LocalTime.of(closingHour,closingMinutes));
        TheaterDateInformation newInformation  = new TheaterDateInformation(opening,closing);
        theaterDate.put(date, newInformation);
    }

    public String getDateInfo(LocalDate date){
        if(!(theaterDate.containsKey(date))){
            return "";//TODO: faire une erreur
        }
        return date.toString() + " " + theaterDate.get(date).toString();
    }
   
    
}
