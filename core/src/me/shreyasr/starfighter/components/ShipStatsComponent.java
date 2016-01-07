package me.shreyasr.starfighter.components;

import com.badlogic.ashley.core.Component;

public class ShipStatsComponent implements Component {

    public double turn;
    public double accel;
    public double maxSpeed;
    public double maxShield;

    public ShipStatsComponent() {
    }

    public ShipStatsComponent(double turn, double accel, double maxSpeed, double maxShield) {
        this.turn = turn;
        this.accel = accel;
        this.maxSpeed = maxSpeed;
        this.maxShield = maxShield;
    }
}
