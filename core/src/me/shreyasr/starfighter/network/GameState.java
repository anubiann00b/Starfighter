package me.shreyasr.starfighter.network;

import com.badlogic.ashley.core.Entity;

import me.shreyasr.starfighter.event.Event;

public class GameState implements Comparable<GameState> {

    public double time;
    public Entity[] entities;
    public Event[] events;

    public GameState() {

    }

    public GameState(double time, Entity[] entities, Event[] events) {
        this.time = time;
        this.entities = entities;
        this.events = events;
    }

    @Override
    public int compareTo(GameState o) {
        return Double.compare(time, o.time);
    }
}
