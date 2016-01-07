package me.shreyasr.starfighter.components;

import com.badlogic.ashley.core.Component;

public class OwnerComponent implements Component {

    public IdComponent id;

    public static OwnerComponent create(IdComponent owner) {
        OwnerComponent id = new OwnerComponent();
        id.id = owner;
        return id;
    }
}
