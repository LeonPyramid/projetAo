package local.culturalprogramation.domain.events;

import java.io.Serializable;
import java.time.LocalDate;


public class Concert extends Event{
    
    private static final long serialVersionUID = 1L;
    private LocalDate date;
    
    public Concert(String groupName, LocalDate date, int desiredCapacity){
        super(desiredCapacity,groupName);
        
        this.date = date;
    }
    
    

    public LocalDate getDate() {
        return date;
    }


    @Override
    public String toString() {
        return "Concert :" + getName() + ", " + date.format(fmt);
    }

    
}
