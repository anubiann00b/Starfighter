package me.shreyasr.starfighter.util;

import com.badlogic.ashley.core.Entity;

import me.shreyasr.starfighter.components.DirComponent;
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
        e.add(new TypeComponent.Ship());

        PosComponent pos = new PosComponent();
        VelComponent vel = new VelComponent();
        DirComponent dir = new DirComponent();
        TextureComponent tex = new TextureComponent();
        TextureTransformComponent ttc = TextureTransformComponent.create(95, 141, 0.75f);

        tex.file = Assets.FIGHTER.getFile();

        e.add(pos);
        e.add(vel);
        e.add(dir);
        e.add(tex);
        e.add(ttc);

        return e;
    }
}
