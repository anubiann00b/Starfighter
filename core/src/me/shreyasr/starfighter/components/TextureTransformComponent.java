package me.shreyasr.starfighter.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class TextureTransformComponent implements Component {

    public int screenWidth;
    public int screenHeight;

    public int originX;
    public int originY;

    public int srcX;
    public int srcY;
    public int srcWidth;
    public int srcHeight;
    public float rotation;

    public boolean hide;

    public Color color = Color.WHITE;

    public TextureTransformComponent() {

    }

    public TextureTransformComponent(int width, int height, float scale) {
        this.srcWidth = width;
        this.srcHeight = height;
        this.screenWidth = Math.round(width*scale);
        this.screenHeight = Math.round(height*scale);
        this.originX = this.screenWidth/2;
        this.originY = this.screenHeight/2;
    }
}
