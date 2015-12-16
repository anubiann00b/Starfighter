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
}
