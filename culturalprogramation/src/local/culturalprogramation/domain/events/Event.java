package local.culturalprogramation.domain.events;



public class Event {
    private int desiredCapacity;

    public Event(int desiredCapacity){
        this.desiredCapacity = desiredCapacity;
    }
    public int getDesiredCapacity() {
        return desiredCapacity;
    }
}


