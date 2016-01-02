package me.shreyasr.starfighter.systems;

import com.badlogic.ashley.core.EntitySystem;

import me.shreyasr.starfighter.event.EventQueue;

public class EventQueueUpdateSystem extends EntitySystem {

    private final EventQueue eventQueue;

    public EventQueueUpdateSystem(int priority, EventQueue eventQueue) {
        super(priority);
        this.eventQueue = eventQueue;
    }

    @Override
    public void update(float deltaTime) {
        eventQueue.resolveEvents(System.currentTimeMillis());
    }
}
