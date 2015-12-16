package me.shreyasr.starfighter.components;

import com.badlogic.ashley.core.Component;

public class PosComponent implements Component {

    public float x;
    public float y;

    @Override
    public String toString() {
        return "[" + ((int)x*10)/10.0 + " " + ((int)y*10)/10.0 + "]";
    }
}
