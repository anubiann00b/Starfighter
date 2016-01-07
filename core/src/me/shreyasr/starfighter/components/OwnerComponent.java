package me.shreyasr.starfighter.components;

import com.badlogic.ashley.core.Component;

public class OwnerComponent implements Component {

    public IdComponent id;

    public OwnerComponent() {

    }

    public OwnerComponent(IdComponent owner) {
        this.id = owner;
    }
}
