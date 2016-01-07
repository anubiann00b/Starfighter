package me.shreyasr.starfighter.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import me.shreyasr.starfighter.components.OriginComponent;
import me.shreyasr.starfighter.components.OwnerComponent;
import me.shreyasr.starfighter.components.PosComponent;
import me.shreyasr.starfighter.components.TypeComponent;
import me.shreyasr.starfighter.components.VelComponent;
import me.shreyasr.starfighter.util.EngineUtils;
import me.shreyasr.starfighter.util.Time;

public class ProjectileUpdateSystem extends IteratingSystem {

    public ProjectileUpdateSystem(int priority) {
        super(Family.all(TypeComponent.Projectile.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        OriginComponent origin = entity.getComponent(OriginComponent.class);
        OwnerComponent ownerComponent = entity.getComponent(OwnerComponent.class);
        PosComponent pos = entity.getComponent(PosComponent.class);
        VelComponent vel = entity.getComponent(VelComponent.class);

        if (Time.getMillis() >= origin.time && !origin.updated) {
            Entity owner = EngineUtils.getById(getEngine(), ownerComponent.id);
            vel.dir = owner.getComponent(VelComponent.class).dir;
            origin.x = owner.getComponent(PosComponent.class).x;
            origin.y = owner.getComponent(PosComponent.class).y;
            origin.updated = true;
        }

        double time = Time.getMillis() - origin.time;
        pos.x = (float) (origin.x + vel.speed * Math.cos(vel.dir) * time/16 * 10);
        pos.y = (float) (origin.y + vel.speed * Math.sin(vel.dir) * time/16 * 10);
    }
}
