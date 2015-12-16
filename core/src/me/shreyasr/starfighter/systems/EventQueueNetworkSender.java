package me.shreyasr.starfighter.systems;

import com.badlogic.ashley.core.EntitySystem;

import me.shreyasr.starfighter.event.Event;
import me.shreyasr.starfighter.event.EventQueue;
import me.shreyasr.starfighter.network.WebSocketSender;
import me.shreyasr.starfighter.util.JsonSerializer;

public class EventQueueNetworkSender extends EntitySystem {

    private final WebSocketSender webSocketSender;
    private final EventQueue eventQueue;

    public EventQueueNetworkSender(int priority, WebSocketSender webSocketSender, EventQueue eventQueue) {
        super(priority);
        this.webSocketSender = webSocketSender;
        this.eventQueue = eventQueue;
    }

    @Override
    public void update(float deltaTime) {
        for (Event e : eventQueue.popNewEvents()) {
            webSocketSender.send(JsonSerializer.encode(e));
        }
    }
}
