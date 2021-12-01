package local.culturalprogramation.domain.events;

import java.time.LocalDateTime;


public class Play extends Event {

    
    private String companyName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    
    public Play(String companyName, LocalDateTime start, LocalDateTime end){
        if (end.isBefore(start))
            return; //TODO Return erreur
        this.companyName = companyName;
        this.startDate = start;
        this.endDate = end;
        
    }
    /**
     * 
     * @return get name of play
     */
    public String getCompanyName() {
        return companyName;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    /**
     *  
     * @return String at format *[start:end]*
     * 
     */
    public String getStringDate(){
        String interval = "["+startDate.toString()+":"+endDate.toString()+"]";
        return interval;
    }

}
