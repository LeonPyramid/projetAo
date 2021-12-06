package local.culturalprogramation.domain.events;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Concert extends Event{
    
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

    
}
