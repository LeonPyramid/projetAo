package local.culturalprogramation.domain.theater;
import java.util.Calendar;

import local.culturalprogramation.domain.events.Event;

/**
 * Class containing information of a Theater. It is linked to a given day in
 * Theater.
 */
public class TheaterDateInformation {
    private Calendar openingHour;
    private Calendar closingHour;
    private Event event;
    private TheaterStatus status;
    
    /**
     * Creating a new date information. The theater is set to OPEN and no event is linked.
     * @param opHour 
     * @param clHour
     */
    public TheaterDateInformation(Calendar opHour, Calendar clHour){
        this.event = null;
        this.openingHour = opHour;
        this.closingHour = clHour;
        this.status = TheaterStatus.OPEN;
    }


    public TheaterDateInformation(){
        this.event = null;
        this.openingHour = null;
        this.closingHour = null;
        this.status = TheaterStatus.CLOSED;
    }

    
    /** 
     * @return Event
     */
    public Event getEvent() {
        return event;
    }

    
    /** 
     * @return Date
     */
    public Calendar getOpeningHour() {
        return openingHour;
    }

    
    /** 
     * @return Date
     */
    public Calendar getClosingHour() {
        return closingHour;
    }

    
    /** 
     * @return TheaterStatus
     */
    public TheaterStatus getStatus() {
        return status;
    }

    
    /** 
     * @param event
     */
    public void setEvent(Event event) {
        if(event==null){
            //TODO:empty field error
        }
        if(status == TheaterStatus.OPEN){
            this.event = event;
            this.status = TheaterStatus.OCCUPIED;
        }
        else{
            //TODO:Renvoyer une erreur
        }
    }

    public void removeEvent(){
        if(this.event != null){
            this.event = null;
            this.status = TheaterStatus.OPEN;
        }
        else{
            //TODO:Renvoyer une erreur
        }
    }
}
