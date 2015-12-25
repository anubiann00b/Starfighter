package me.shreyasr.starfighter.event;

public class ReplacementEvent extends Event {

    private Event newEvent;

    public ReplacementEvent() { }

    public ReplacementEvent(Event newEvent) {
        super(newEvent.startMillis);
        this.newEvent = newEvent;
    }

    @Override
    public boolean resolve(EventResolutionData data, EventQueue.EventQueueUpdater queueUpdater) {
        queueUpdater.cancelEvent(newEvent.id);
        queueUpdater.addEvent(newEvent);
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + " " + newEvent.toString();
    }
}
