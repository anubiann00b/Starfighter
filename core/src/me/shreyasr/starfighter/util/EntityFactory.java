package me.shreyasr.starfighter.util;

import com.badlogic.ashley.core.Entity;

import me.shreyasr.starfighter.components.DirComponent;
import me.shreyasr.starfighter.components.IdComponent;
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
        e.add(new DirComponent());
        e.add(new TypeComponent.Ship());

        return e;
    }
}
