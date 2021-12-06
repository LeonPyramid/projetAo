package local.culturalprogramation.domain.events;

import java.time.format.DateTimeFormatter;

public class Event {
    private int desiredCapacity;
    protected DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yy");

    public Event(int desiredCapacity){
        this.desiredCapacity = desiredCapacity;
    }
    public int getDesiredCapacity() {
        return desiredCapacity;
    }
}


