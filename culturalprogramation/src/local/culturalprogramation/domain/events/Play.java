package local.culturalprogramation.domain.events;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class Play extends Event {

    
    private String companyName;
    private LocalDate startDate;
    private LocalDate endDate;
    
    
    public Play(String companyName, LocalDate dstart, LocalDate dend, int desiredCapacity){
        super(desiredCapacity);
        if (dend.isBefore(dstart)){
            System.err.println("Warning: start hour and stop hour are reversed!\n Automatically reversing them");
            LocalDate tmp = dend;
            dend = dstart;
            dstart = tmp;
        }
        this.companyName = companyName;
        this.startDate = dstart;
        this.endDate = dend;
        
    }
    /**
     * 
     * @return get name of play
     */
    public String getCompanyName() {
        return companyName;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    /**
     *  
     * @return String at format *[start:end]*
     * 
     */
    public String getStringDate(){
        String interval = "["+startDate.format(fmt)+":"+endDate.format(fmt)+"]";
        return interval;
    }

    @Override
    public String toString() {
        return "Play :" + companyName + ",from " + startDate.format(fmt) + " to " + endDate.format(fmt);
    }

}
