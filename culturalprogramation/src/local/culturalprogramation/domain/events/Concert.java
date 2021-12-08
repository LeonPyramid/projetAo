package local.culturalprogramation.domain.events;

import java.io.Serializable;
import java.time.LocalDate;


public class Concert extends Event implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String groupName;
    private LocalDate date;
    
    public Concert(String groupName, LocalDate date, int desiredCapacity){
        super(desiredCapacity);
        this.groupName =groupName;
        this.date = date;
    }
    
    
    public String getGroupName() {
        return groupName;
    }

    public LocalDate getDate() {
        return date;
    }


    @Override
    public String toString() {
        return "Concert :" + groupName + ", " + date.format(fmt);
    }

    
}
