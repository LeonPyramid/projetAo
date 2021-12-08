package local.culturalprogramation.domain.theaterAndProgramation;


import java.io.Serializable;
import java.time.LocalDateTime;
import local.culturalprogramation.domain.events.Event;

/**
 * Class containing information of a Theater. It is linked to a given day in
 * Theater.
 */
public class TheaterDateInformation implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDateTime openingHour;
    private LocalDateTime closingHour;
    private Event event;
    private TheaterStatus status;
    
    /**
     * Creating a new date information. The theater is set to OPEN and no event is linked.
     * @param opHour 
     * @param clHour
     */
    public TheaterDateInformation(LocalDateTime opHour, LocalDateTime clHour){
        if(clHour.isBefore(opHour)){
            System.err.println("Warning: Opening hour and closing hour are reversed!\n Automatically reversing them");
            LocalDateTime tmp = clHour;
            clHour = opHour;
            opHour = tmp;
        } 
        this.event = null;
        this.openingHour = opHour;
        this.closingHour = clHour;
        if(!opHour.equals(clHour))
            this.status = TheaterStatus.OPEN;
        else
            this.status = TheaterStatus.CLOSED;
    }


    public TheaterDateInformation(){
        this.event = null;
        this.openingHour = null;
        this.closingHour = null;
        this.status = TheaterStatus.CLOSED;
    }

    public TheaterDateInformation(Event event,LocalDateTime opHour, LocalDateTime clHour){
        if(clHour.isBefore(opHour)){
            System.err.println("Warning: Opening hour and closing hour are reversed!\n Automatically reversing them");
            LocalDateTime tmp = clHour;
            clHour = opHour;
            opHour = tmp;
        } 
        this.event = event;
        this.openingHour = opHour;
        this.closingHour = clHour;
        if(!opHour.equals(clHour))
            this.status = TheaterStatus.OCCUPIED;
        else
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
    public LocalDateTime getOpeningHour() {
        return openingHour;
    }

    
    /** 
     * @return Date
     */
    public LocalDateTime getClosingHour() {
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
            throw new RuntimeException("Event cannot be null");
        }
        if(status == TheaterStatus.OPEN){
            this.event = event;
            this.status = TheaterStatus.OCCUPIED;
        }
        else{
            throw new RuntimeException("Cannot set an event in a closed or occupied date of the theater");
        }
    }

    public void removeEvent(){
        if(this.event != null){
            this.event = null;
            this.status = TheaterStatus.OPEN;
        }
        else{
            throw new RuntimeException("Cannot remove an event at this date, no event set");
        }
    }
    @Override
    public String toString(){
        String ret = "";
        ret += status + " ";
        if(status==TheaterStatus.OCCUPIED){
            ret += event + " ";
        }
        if(status!=TheaterStatus.CLOSED){
            ret += this.openingHour.toLocalTime();
            ret += ",";
            ret += this.closingHour.toLocalTime();
        }
        ret += ";";
        return ret;
    }
}
