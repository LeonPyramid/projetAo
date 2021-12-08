package local.culturalprogramation.domain.events;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int desiredCapacity;
    transient protected DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yy");

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


