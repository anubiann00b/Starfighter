package me.shreyasr.starfighter.event;

public class CancelEvent extends Event {

    private long id;

    public CancelEvent(long startTime, long id) {
        super(startTime);
        this.id = id;
    }

    @Override
    public boolean resolve(EventResolutionData data, EventQueue.EventCanceler eventCanceler) {
        eventCanceler.cancelEvent(id);
        return false;
    }
}
