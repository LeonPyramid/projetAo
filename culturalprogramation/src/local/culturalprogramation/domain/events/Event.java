package local.culturalprogramation.domain.events;

import java.io.Serializable;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    private int desiredCapacity;

    public Event(int desiredCapacity){
        this.desiredCapacity = desiredCapacity;
    }
    public int getDesiredCapacity() {
        return desiredCapacity;
    }
}


