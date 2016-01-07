package me.shreyasr.starfighter.components;

import com.badlogic.ashley.core.Component;

public class VelComponent implements Component {

    public double dir;
    public double speed;

    public VelComponent() {

    }

    public VelComponent(float dir, float speed) {
        this.dir = dir;
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "VelComponent{" +
                "dir=" + dir +
                ", speed=" + speed +
                '}';
    }
}
