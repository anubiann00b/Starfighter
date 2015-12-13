package me.shreyasr.starfighter.components;

import com.badlogic.ashley.core.Component;

import java.security.SecureRandom;

public class IdComponent implements Component {

    private static SecureRandom random = new SecureRandom();

    public static IdComponent create() {
        IdComponent id = new IdComponent();
        id.id = random.nextLong();
        return id;
    }

    public long id;
}
