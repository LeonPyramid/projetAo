package local.culturalprogramation.domain.theater;

import java.util.Hashtable;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import local.culturalprogramation.domain.events.Event;
import local.culturalprogramation.domain.events.Play;

/**
 * Class representing a theather.
 *
 */
public class Theater  implements Serializable{
    private static final long serialVersionUID = 1L;
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

    public Theater(String name,String folderPath,int capacity){
        this.name =name;
        this.openingHours = new WeeklyOpeningHours();
        this.theaterCapacity=capacity;

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

    public String getName(){
        return this.name;
    }
    
    /**
     * Set the informations of the day according to the Weekly planning.
     * If the weekly planning says that this day of week is closed, will set the day to closed too and give a warning.
     * @param date day to set the informations
     */
    public void setDayOpen(LocalDate date){
        CreateDate(date);
        if (theaterDate.get(date).getStatus()!=TheaterStatus.OPEN){
            System.err.println(("Warning: This day has no opening hours set. Day is set as CLOSED"));
        }
    }
    
    /**
     * Set the information of the day but with closed parameters.
     * @param date day to set the informations
     */
    public void setDayClosed(LocalDate date){
        if(!theaterDate.containsKey(date)){
            CreateDate(date, 00, 00, 00, 00);       
        }
        else{
            theaterDate.put(date, new  TheaterDateInformation());
        }

    }

    /**
     * Set in theaterDate, the event given of the day
     * @param date day to set the event
     * @param event the event to set
     */
    public void setDayEvent(LocalDate date, Event event){
        if(event.getDesiredCapacity()>this.theaterCapacity)
            throw new RuntimeException("Event needs more place than the theater "+name+" has\n");
        if(!theaterDate.containsKey(date))
            setDayOpen(date);
        if(this.theaterDate.get(date).getStatus()!=TheaterStatus.OPEN)
            throw new RuntimeException("The theater "+name+" is not-free or closed at this day\n");
        TheaterDateInformation tdi = theaterDate.get(date);
        tdi.setEvent(event);  
    }
    /**
     * Remove in theaterDate, the event of the given day
     * @param date day to remove eventh
     */
    public void removeDayEvent(LocalDate date){
        if(!theaterDate.containsKey(date))
            throw new RuntimeException("The theater "+name+" hasn't prepared the date yet\n");
        if(theaterDate.get(date).getStatus()==TheaterStatus.CLOSED)
            throw new RuntimeException("The theater "+name+" hasn't prepared the date yet\n");
        TheaterDateInformation tdi = theaterDate.get(date);
        tdi.removeEvent();  
    }

    public void removeDayEventPlay(LocalDate date) {
        if(!theaterDate.containsKey(date))
            throw new RuntimeException("The theater "+name+" hasn't prepared the date yet\n");
        if(theaterDate.get(date).getStatus()==TheaterStatus.CLOSED)
            throw new RuntimeException("The theater "+name+" hasn't prepared the date yet\n");
        TheaterDateInformation tdi = theaterDate.get(date);
        Play play = (Play) tdi.getEvent();
        LocalDate start = play.getStartDate();
        LocalDate end = play.getEndDate();
        for(; start.compareTo(end)<=0; start =start.plusDays(1)){
            removeDayEvent(start);

        }
    }
   
 
    
    public void setWeeklyDayOpeningHour(DayOfWeek day, int hour, int min ){
        openingHours.setOpeningHour(day ,hour, min);
    }
    public void setWeeklyDayClosingHour(DayOfWeek day, int hour, int min){
        openingHours.setClosingHour(day, hour, min);
        
    }

    /**
     * Create an Open day at a given date, and set the opening and closing time to the weeklyPlanning 
     * corresponding to the Theater
     * @param date the date which need to be created
     */
    public void CreateDate(LocalDate date){
        if(theaterDate.containsKey(date)){
            throw new RuntimeException("The date already exists\n");
        }
        DayOfWeek dayofweek  = date.getDayOfWeek();
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
        if(theaterDate.containsKey(date)){
            throw new RuntimeException("The date already exists\n");
        }
        LocalDateTime opening = LocalDateTime.of(date,LocalTime.of(openingHour,openingMinute));
        LocalDateTime closing = LocalDateTime.of(date,LocalTime.of(closingHour,closingMinutes));
        TheaterDateInformation newInformation  = new TheaterDateInformation(opening,closing);
        theaterDate.put(date, newInformation);
    }

    public void RemoveDate(LocalDate date){
        if(!theaterDate.containsKey(date)){
            throw new RuntimeException("The date doesn't exists\n");
        }
        if(getDateStatus(date)==TheaterStatus.OCCUPIED){
            throw new RuntimeException("The date has an event, hense cannot be changed\n");
        }

        theaterDate.remove(date);
    }

    public TheaterStatus getDateStatus(LocalDate date){
        if(!(theaterDate.containsKey(date))){
            this.CreateDate(date);
        }
        return theaterDate.get(date).getStatus();
    }

    public String getDateInfo(LocalDate date){
        if(!(theaterDate.containsKey(date))){
            this.CreateDate(date);
        }
        return date.toString() + " " + theaterDate.get(date).toString();
    }
    
    
    public Event getDateEvent(LocalDate date){
        if(!(theaterDate.containsKey(date))){
            return null;
        }
        return theaterDate.get(date).getEvent();
    
    }

    

    
}
