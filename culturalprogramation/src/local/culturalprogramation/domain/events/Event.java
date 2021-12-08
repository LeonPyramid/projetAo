package local.culturalprogramation.domain.events;

import java.io.Serializable;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int desiredCapacity;

    public Event(int desiredCapacity, String name){
        this.desiredCapacity = desiredCapacity;
        this.name = name;
    }
    public int getDesiredCapacity() {
        return desiredCapacity;
    }

    public String getName() {
        return name;
    }
}


