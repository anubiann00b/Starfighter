package me.shreyasr.starfighter.systems;

import com.badlogic.ashley.core.EntitySystem;

import me.shreyasr.starfighter.event.Event;
import me.shreyasr.starfighter.event.EventQueue;
import me.shreyasr.starfighter.network.AccumulatingWebSocketListener;
import me.shreyasr.starfighter.network.GameState;
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
            Object obj = JsonSerializer.decode(message);
            if (obj instanceof Event) {
                System.out.println("Event from Server: " + obj.toString());
                eventQueue.addSilent((Event)obj);
            } else if (obj instanceof GameState) {
                GameState gameState = (GameState) obj;
                System.out.println("Game State: " + gameState.time%10000);
                eventQueue.rollback(gameState);
                eventQueue.resolveEvents(System.currentTimeMillis());
            }
        }
    }
}
