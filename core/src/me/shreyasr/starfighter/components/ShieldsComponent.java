package me.shreyasr.starfighter.components;

import com.badlogic.ashley.core.Component;

public class ShieldsComponent implements Component {

    public double shield;

    public ShieldsComponent() {

    }

    public ShieldsComponent(int shield) {
        this.shield = shield;
    }
}
