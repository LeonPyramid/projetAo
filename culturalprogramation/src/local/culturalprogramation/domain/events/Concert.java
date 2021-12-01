package local.culturalprogramation.domain.events;

import java.time.LocalDateTime;

public class Concert extends Event{
    
    private String groupName;
    private LocalDateTime date;
    
    public Concert(String groupName, LocalDateTime date){
        this.groupName =groupName;
        this.date = date;
    }
    
    
    public String getGroupName() {
        return groupName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    
}
