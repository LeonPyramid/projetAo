package local.culturalprogramation.domain.events;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class Play extends Event{

    private static final long serialVersionUID = 1L;
    private LocalDate startDate;
    private LocalDate endDate;
    
    
    public Play(String name, LocalDate dstart, LocalDate dend, int desiredCapacity){
        super(desiredCapacity,name);
        if (dend.isBefore(dstart)){
            System.err.println("Warning: start hour and stop hour are reversed!\n Automatically reversing them");
            LocalDate tmp = dend;
            dend = dstart;
            dstart = tmp;
        }
        
        this.startDate = dstart;
        this.endDate = dend;
        
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
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yy");
        String interval = "["+startDate.format(fmt)+":"+endDate.format(fmt)+"]";
        return interval;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yy");
        return "Play :" + getName() + ",from " + startDate.format(fmt) + " to " + endDate.format(fmt);
    }

}
