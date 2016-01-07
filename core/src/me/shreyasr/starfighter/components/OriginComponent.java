package me.shreyasr.starfighter.components;

import com.badlogic.ashley.core.Component;

public class OriginComponent implements Component {

    public float x;
    public float y;

    public double time;
    public boolean updated = false;

    public OriginComponent() {

    }

    public OriginComponent(float x, float y, double time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }
}