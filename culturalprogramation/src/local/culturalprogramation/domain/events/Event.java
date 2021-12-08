package local.culturalprogramation.domain.events;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    private int desiredCapacity;
    transient protected DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yy");

    public Event(int desiredCapacity){
        this.desiredCapacity = desiredCapacity;
    }
    public int getDesiredCapacity() {
        return desiredCapacity;
    }
}


