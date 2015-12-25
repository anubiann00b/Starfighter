package me.shreyasr.starfighter.util;

import com.badlogic.ashley.core.Entity;

import me.shreyasr.starfighter.components.IdComponent;
import me.shreyasr.starfighter.components.OriginComponent;
import me.shreyasr.starfighter.components.PosComponent;
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

        e.add(TextureTransformComponent.create(95, 141, 0.75f));
        e.add(new PosComponent());
        e.add(new VelComponent());
        e.add(IdComponent.create());
        e.add(new TypeComponent.Ship());

        return e;
    }

    public static Entity createLaser(float x, float y, float dir, float speed, double time) {
        Entity e = new Entity();

        TextureComponent tex = new TextureComponent();
        tex.file = Assets.LASER.getFile();
        e.add(tex);

        e.add(TextureTransformComponent.create(15, 460, 0.25f));
        e.add(new PosComponent());
        e.add(VelComponent.create(dir, speed));
        e.add(OriginComponent.create(x, y, time));
        e.add(IdComponent.create());
        e.add(new TypeComponent.Projectile());

        return e;
    }
}
