package me.shreyasr.starfighter.event;

public class CancelEvent extends Event {

    private double cancelId;

    public CancelEvent() { }

    public CancelEvent(double startTime, double cancelId) {
        super(startTime);
        this.cancelId = cancelId;
    }

    @Override
    public boolean resolve(EventResolutionData data, EventQueue.EventCanceler eventCanceler) {
        eventCanceler.cancelEvent(cancelId);
        return false;
    }
}
