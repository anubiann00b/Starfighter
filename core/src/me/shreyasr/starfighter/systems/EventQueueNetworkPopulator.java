package me.shreyasr.starfighter.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;

import me.shreyasr.starfighter.components.IdComponent;
import me.shreyasr.starfighter.components.VelComponent;
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
                eventQueue.addEvent((Event)obj, true);
            } else if (obj instanceof GameState) {
                GameState gameState = (GameState) obj;
                for (Entity newEntity : gameState.entities) {
                    for (Entity oldEntity : getEngine().getEntities()) {
                        IdComponent oldId = oldEntity.getComponent(IdComponent.class);
                        IdComponent newId = newEntity.getComponent(IdComponent.class);
                        if (oldId.equals(newId)) {
                            for (Component c : newEntity.getComponents()) {
                                if (c instanceof VelComponent) {
                                    System.out.println(c);
                                    System.out.println(oldEntity.getComponent(VelComponent.class));
                                }
                                oldEntity.add(c);
                                if (c instanceof VelComponent) {
                                    System.out.println(oldEntity.getComponent(VelComponent.class));
                                }
                            }
                        } else {
                            getEngine().addEntity(newEntity);
                        }
                    }
                }
                eventQueue.removeAllEvents();
                for (Event e : gameState.events) {
                    eventQueue.addEvent(e, true);
                }
                eventQueue.resolveEvents(gameState.time, System.currentTimeMillis()-deltaTime);
            }
        }
    }
}
