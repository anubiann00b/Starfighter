package me.shreyasr.starfighter.util;

import com.badlogic.ashley.core.Entity;

import me.shreyasr.starfighter.components.DirComponent;
import me.shreyasr.starfighter.components.PosComponent;
import me.shreyasr.starfighter.components.TextureComponent;
import me.shreyasr.starfighter.components.TextureTransformComponent;
import me.shreyasr.starfighter.components.TypeComponent;

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
        pos.x = 100;
        pos.y = 100;
        DirComponent dir = new DirComponent();
        TextureComponent tex = new TextureComponent();
        TextureTransformComponent ttc = TextureTransformComponent.create(95, 141, 1);

        tex.file = Assets.FIGHTER.getFile();

        e.add(dir);
        e.add(pos);
        e.add(tex);
        e.add(ttc);

        return e;
    }
}
