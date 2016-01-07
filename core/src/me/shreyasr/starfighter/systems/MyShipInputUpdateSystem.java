package me.shreyasr.starfighter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;

import me.shreyasr.starfighter.components.IdComponent;
import me.shreyasr.starfighter.components.PosComponent;
import me.shreyasr.starfighter.components.VelComponent;
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
    private TurnEvent turnEvent;
    private MovementEvent moveEvent;

    public MyShipInputUpdateSystem(int priority, EventQueue eventQueue) {
        super(priority);
        this.eventQueue = eventQueue;
    }

    @Override
    public void init(Entity player) {
        moveEvent = new MovementEvent(0, player.getComponent(IdComponent.class).id, 0);
        turnEvent = new TurnEvent(0, player.getComponent(IdComponent.class).id, 0);
    }

    @Override
    public void updatePlayer(Entity player, float deltaTime) {
        PosComponent pos = player.getComponent(PosComponent.class);
        VelComponent vel = player.getComponent(VelComponent.class);
        IdComponent id = player.getComponent(IdComponent.class);

        boolean up = input.isKeyPressed(Input.Keys.UP);
        boolean down = input.isKeyPressed(Input.Keys.DOWN);

        double accel;
        if (up && down) {
            accel = 0;
        } else if (up) {
            accel = .2;
        } else if (down) {
            accel = -.2;
        } else {
            accel = 0;
        }
        if (accel != moveEvent.accel) {
            double oldId = moveEvent.id;
            moveEvent = new MovementEvent(Time.getMillis() + OFFSET_MS, id.id, accel);
            moveEvent.id = oldId;
            eventQueue.addEvent(moveEvent);
        }

        boolean left = input.isKeyPressed(Input.Keys.LEFT);
        boolean right = input.isKeyPressed(Input.Keys.RIGHT);

        if (left && right) {
            accel = 0;
        } else if (left) {
            accel = 0.03;
        } else if (right) {
            accel = -0.03;
        } else {
            accel = 0;
        }
        if (accel != turnEvent.accel) {
            double oldId = turnEvent.id;
            turnEvent = new TurnEvent(Time.getMillis()+OFFSET_MS, id.id, accel);
            turnEvent.id = oldId;
            eventQueue.addEvent(turnEvent);
        }

        if (input.isKeyPressedFirstTime(Input.Keys.SPACE)) {
            double laserTime = Time.getMillis()+OFFSET_MS;
            Entity laser = EntityFactory.createLaser(id, 3, laserTime);
            eventQueue.addEvent(new EntityCreateEvent(laserTime, laser));
        }
    }
}
