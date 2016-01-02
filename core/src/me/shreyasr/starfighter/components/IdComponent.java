package me.shreyasr.starfighter.components;

import com.badlogic.ashley.core.Component;

import java.util.Random;

public class IdComponent implements Component {

    private static Random random = new Random();

    public static IdComponent create() {
        IdComponent id = new IdComponent();
        id.id = random.nextDouble();
        return id;
    }

    public double id;

    @Override
    public boolean equals(Object o) {
        return (o instanceof IdComponent) && ((IdComponent) o).id == id;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(id);
        return (int) (temp ^ (temp >>> 32));
    }
}
