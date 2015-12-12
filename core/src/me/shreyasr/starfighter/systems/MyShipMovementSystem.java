package me.shreyasr.starfighter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;

import me.shreyasr.starfighter.components.DirComponent;
import me.shreyasr.starfighter.components.VelComponent;
import me.shreyasr.starfighter.util.AccumulatingKeyboardProcessor;

public class MyShipMovementSystem extends PlayerSystem {

    public final AccumulatingKeyboardProcessor input = new AccumulatingKeyboardProcessor(
            Input.Keys.UP, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.DOWN
    );

    public MyShipMovementSystem(int priority) {
        super(priority);
    }

    @Override
    public void updatePlayer(Entity player, float deltaTime) {
        DirComponent dir = player.getComponent(DirComponent.class);
        VelComponent vel = player.getComponent(VelComponent.class);

        if (input.isKeyPressed(Input.Keys.LEFT))  dir.dir += .015;
        if (input.isKeyPressed(Input.Keys.RIGHT)) dir.dir -= .015;

        if (input.isKeyPressed(Input.Keys.UP))   vel.velocity += .1;
        if (input.isKeyPressed(Input.Keys.DOWN)) vel.velocity -= .1;
    }
}
