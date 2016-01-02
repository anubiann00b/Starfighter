package me.shreyasr.starfighter.components;

import com.badlogic.ashley.core.Component;

public class VelComponent implements Component {

    public static Component create(float dir, float speed) {
        VelComponent c = new VelComponent();
        c.dir = dir;
        c.speed = speed;
        return c;
    }

    public float dir;
    public float speed;

    @Override
    public String toString() {
        return "VelComponent{" +
                "dir=" + dir +
                ", speed=" + speed +
                '}';
    }
}
