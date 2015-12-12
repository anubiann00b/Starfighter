package me.shreyasr.starfighter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import me.shreyasr.starfighter.components.DirComponent;
import me.shreyasr.starfighter.components.PosComponent;
import me.shreyasr.starfighter.components.TextureComponent;
import me.shreyasr.starfighter.components.TextureTransformComponent;

public class ShipGraphicsUpdateSystem extends IteratingSystem {

    public ShipGraphicsUpdateSystem(int priority) {
        super(
                Family.all(PosComponent.class,
                        DirComponent.class,
                        TextureComponent.class,
                        TextureTransformComponent.class)
                        .get(),
                priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureTransformComponent ttc = entity.getComponent(TextureTransformComponent.class);

        ttc.rotation = entity.getComponent(DirComponent.class).dir;

    }
}
