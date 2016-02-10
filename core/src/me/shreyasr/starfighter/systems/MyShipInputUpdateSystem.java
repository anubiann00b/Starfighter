package me.shreyasr.starfighter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;

import me.shreyasr.starfighter.components.IdComponent;
import me.shreyasr.starfighter.components.PosComponent;
import me.shreyasr.starfighter.components.ShipStatsComponent;
import me.shreyasr.starfighter.components.VelComponent;
import me.shreyasr.starfighter.event.DisableEvent;
import me.shreyasr.starfighter.event.EntityCreateEvent;
import me.shreyasr.starfighter.event.EventQueue;
import me.shreyasr.starfighter.event.MovementEvent;
import me.shreyasr.starfighter.event.TurnEvent;
import me.shreyasr.starfighter.util.AccumulatingKeyboardProcessor;
import me.shreyasr.starfighter.util.EntityFactory;
import me.shreyasr.starfighter.util.Time;

public class MyShipInputUpdateSystem extends PlayerSystem {

    private static final double OFFSET_MS = 100;
    public final AccumulatingKeyboardProcessor input = new AccumulatingKeyboardProcessor(
            Input.Keys.UP, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.DOWN,
            Input.Keys.SPACE
    );

    private final EventQueue eventQueue;
    double lastMoveAccel = 0;
    double lastTurnAccel = 0;
    double lastMoveId = -1;
    double lastTurnId = -1;

    public MyShipInputUpdateSystem(int priority, EventQueue eventQueue) {
        super(priority);
        this.eventQueue = eventQueue;
    }

    @Override
    public void updatePlayer(Entity player, float deltaTime) {
        PosComponent pos = player.getComponent(PosComponent.class);
        VelComponent vel = player.getComponent(VelComponent.class);
        ShipStatsComponent stats = player.getComponent(ShipStatsComponent.class);
        IdComponent id = player.getComponent(IdComponent.class);

        boolean up = input.isKeyPressed(Input.Keys.UP);
        boolean down = input.isKeyPressed(Input.Keys.DOWN);

        double accel;
        if (up && down) {
            accel = 0;
        } else if (up) {
            accel = stats.accel;
        } else if (down) {
            accel = -stats.accel;
        } else {
            accel = 0;
        }
        if (accel != lastMoveAccel) {
            lastMoveAccel = accel;
            if (lastMoveId >= 0) {
                eventQueue.addEvent(new DisableEvent(Time.getMillis() + OFFSET_MS, lastMoveId));
            }
            if (accel == 0) {
                lastMoveId = -1;
            } else {
                MovementEvent newMoveEvent = new MovementEvent(Time.getMillis() + OFFSET_MS, id.id, accel);
                lastMoveId = newMoveEvent.id;
                eventQueue.addEvent(newMoveEvent);
            }
        }

        boolean left = input.isKeyPressed(Input.Keys.LEFT);
        boolean right = input.isKeyPressed(Input.Keys.RIGHT);

        if (left && right) {
            accel = 0;
        } else if (left) {
            accel = stats.turn;
        } else if (right) {
            accel = -stats.turn;
        } else {
            accel = 0;
        }
        if (accel != lastTurnAccel) {
            if (lastTurnId >= 0) {
                eventQueue.addEvent(new DisableEvent(Time.getMillis() + OFFSET_MS, lastTurnId));
            }
            if (accel == 0) {
                lastTurnId = -1;
            } else {
                TurnEvent newTurnEvent = new TurnEvent(Time.getMillis() + OFFSET_MS, id.id, accel);
                lastTurnId = newTurnEvent.id;
                eventQueue.addEvent(newTurnEvent);
            }
            lastTurnAccel = accel;
        }

        if (input.isKeyPressedFirstTime(Input.Keys.SPACE)) {
            double laserTime = Time.getMillis()+OFFSET_MS;
            Entity laser = EntityFactory.createLaser(id, 3, laserTime);
            eventQueue.addEvent(new EntityCreateEvent(laserTime, laser));
        }
    }
}
