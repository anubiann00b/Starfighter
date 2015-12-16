package me.shreyasr.starfighter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;

import me.shreyasr.starfighter.components.IdComponent;
import me.shreyasr.starfighter.event.CancelEvent;
import me.shreyasr.starfighter.event.EventQueue;
import me.shreyasr.starfighter.event.MovementEvent;
import me.shreyasr.starfighter.event.TurnEvent;
import me.shreyasr.starfighter.util.AccumulatingKeyboardProcessor;

public class MyShipMovementSystem extends PlayerSystem {

    public final AccumulatingKeyboardProcessor input = new AccumulatingKeyboardProcessor(
            Input.Keys.UP, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.DOWN
    );

    private final EventQueue eventQueue;
    private MovementEvent currentMoveEvent = null;
    private TurnEvent currentTurnEvent = null;

    public MyShipMovementSystem(int priority, EventQueue eventQueue) {
        super(priority);
        this.eventQueue = eventQueue;
    }

    @Override
    public void updatePlayer(Entity player, float deltaTime) {
        IdComponent id = player.getComponent(IdComponent.class);

        boolean up = input.isKeyPressed(Input.Keys.UP);
        boolean down = input.isKeyPressed(Input.Keys.DOWN);

        if (up && down) {
            if (currentMoveEvent != null) {
                cancelCurrentMoveEvent();
            }
        } else if (up) {
            if (currentMoveEvent == null) {
                addMovementEvent(id.id, .1);
            } else if (currentMoveEvent.acceleration < 0) {
                cancelCurrentMoveEvent();
                addMovementEvent(id.id, .1);
            }
        } else if (down) {
            if (currentMoveEvent == null) {
                addMovementEvent(id.id, -.1);
            } else if (currentMoveEvent.acceleration > 0) {
                cancelCurrentMoveEvent();
                addMovementEvent(id.id, -.1);
            }
        } else {
            if (currentMoveEvent != null) {
                cancelCurrentMoveEvent();
            }
        }

        boolean left = input.isKeyPressed(Input.Keys.LEFT);
        boolean right = input.isKeyPressed(Input.Keys.RIGHT);

        if (left && right) {
            if (currentTurnEvent != null) {
                cancelCurrentTurnEvent();
            }
        } else if (left) {
            if (currentTurnEvent == null) {
                addTurnEvent(id.id, .015);
            } else if (currentTurnEvent.acceleration < 0) {
                cancelCurrentTurnEvent();
                addTurnEvent(id.id, .015);
            }
        } else if (right) {
            if (currentTurnEvent == null) {
                addTurnEvent(id.id, -.015);
            } else if (currentTurnEvent.acceleration > 0) {
                cancelCurrentTurnEvent();
                addTurnEvent(id.id, -.015);
            }
        } else {
            if (currentTurnEvent != null) {
                cancelCurrentTurnEvent();
            }
        }
    }

    private void cancelCurrentMoveEvent() {
        eventQueue.addEvent(new CancelEvent(System.currentTimeMillis()+100, currentMoveEvent.id));
        currentMoveEvent = null;
    }

    private void addMovementEvent(long id, double acceleration) {
        currentMoveEvent = new MovementEvent(System.currentTimeMillis()+100, id, acceleration);
        eventQueue.addEvent(currentMoveEvent);
    }

    private void cancelCurrentTurnEvent() {
        eventQueue.addEvent(new CancelEvent(System.currentTimeMillis()+100, currentTurnEvent.id));
        currentTurnEvent = null;
    }

    private void addTurnEvent(long id, double acceleration) {
        currentTurnEvent = new TurnEvent(System.currentTimeMillis()+100, id, acceleration);
        eventQueue.addEvent(currentTurnEvent);
    }
}
