package me.shreyasr.starfighter.components;

import com.badlogic.ashley.core.Component;

public class OriginComponent implements Component {

    public static Component create(float x, float y, double time) {
        OriginComponent c = new OriginComponent();
        c.x = x;
        c.y = y;
        c.time = time;
        return c;
    }

    public float x;
    public float y;

    public double time;
    public boolean updated = false;
}