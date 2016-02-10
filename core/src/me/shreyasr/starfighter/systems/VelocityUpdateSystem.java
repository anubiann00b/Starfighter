package me.shreyasr.starfighter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import me.shreyasr.starfighter.components.PosComponent;
import me.shreyasr.starfighter.components.ShipStatsComponent;
import me.shreyasr.starfighter.components.VelComponent;

public class VelocityUpdateSystem extends IteratingSystem {

    public VelocityUpdateSystem(int priority) {
        super(Family.all(PosComponent.class, VelComponent.class).get(),  priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelComponent vel = entity.getComponent(VelComponent.class);
        PosComponent pos = entity.getComponent(PosComponent.class);
        ShipStatsComponent stats = entity.getComponent(ShipStatsComponent.class);

        if (stats != null && Math.abs(vel.speed) > stats.maxSpeed) {
            vel.speed = stats.maxSpeed * Math.signum(vel.speed);
        }

        pos.x += vel.speed * Math.cos(vel.dir);
        pos.y += vel.speed * Math.sin(vel.dir);
    }
}
