package me.shreyasr.starfighter.systems;

import com.badlogic.ashley.core.EntitySystem;

import me.shreyasr.starfighter.event.EventQueue;
import me.shreyasr.starfighter.network.AccumulatingWebSocketListener;
import me.shreyasr.starfighter.util.JsonSerializer;

public class EventQueueNetworkPopulator extends EntitySystem {

    private final AccumulatingWebSocketListener webSocketListener;
    private final EventQueue eventQueue;

    public EventQueueNetworkPopulator(int priority, AccumulatingWebSocketListener webSocketListener,
                                      EventQueue eventQueue) {
        super(priority);
        this.webSocketListener = webSocketListener;
        this.eventQueue = eventQueue;
    }

    @Override
    public void update(float deltaTime) {
        String message;
        while ((message = webSocketListener.popMessage()) != null) {
            eventQueue.addEvent(JsonSerializer.decode(message), true);
        }
    }
}
