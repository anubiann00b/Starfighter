package me.shreyasr.starfighter.util;

import com.badlogic.ashley.core.Entity;

import me.shreyasr.starfighter.components.IdComponent;
import me.shreyasr.starfighter.components.OriginComponent;
import me.shreyasr.starfighter.components.OwnerComponent;
import me.shreyasr.starfighter.components.PosComponent;
import me.shreyasr.starfighter.components.ShieldsComponent;
import me.shreyasr.starfighter.components.ShipStatsComponent;
import me.shreyasr.starfighter.components.TextureComponent;
import me.shreyasr.starfighter.components.TextureTransformComponent;
import me.shreyasr.starfighter.components.TypeComponent;
import me.shreyasr.starfighter.components.VelComponent;

public class EntityFactory {

    public static Entity createPlayerShip() {
        Entity e = createShip();
        e.add(new TypeComponent.MyPlayer());
        return e;
    }

    public static Entity createShip() {
        Entity e = new Entity();

        TextureComponent tex = new TextureComponent();
        tex.file = Assets.FIGHTER.getFile();
        e.add(tex);

        e.add(new ShipStatsComponent(0.03, 0.2, 10, 100));
        e.add(new ShieldsComponent(100));
        e.add(new TextureTransformComponent(95, 141, 0.75f));
        e.add(new PosComponent());
        e.add(new VelComponent());
        e.add(new IdComponent());
        e.add(new TypeComponent.Ship());

        return e;
    }

    public static Entity createLaser(IdComponent owner, float speed, double time) {
        Entity e = new Entity();

        TextureComponent tex = new TextureComponent();
        tex.file = Assets.LASER.getFile();
        e.add(tex);

        e.add(new TextureTransformComponent(15, 460, 0.25f));
        e.add(new PosComponent());
        e.add(new OwnerComponent(owner));
        e.add(new VelComponent(0, speed));
        e.add(new OriginComponent(-1, -1, time));
        e.add(new IdComponent());
        e.add(new TypeComponent.Projectile());

        return e;
    }
}
