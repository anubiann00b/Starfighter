package me.shreyasr.starfighter.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class TextureTransformComponent implements Component {

    public static TextureTransformComponent create(int width, int height, float scale) {
        TextureTransformComponent ttc = new TextureTransformComponent();
        ttc.srcWidth = width;
        ttc.srcHeight = height;
        ttc.originX = width/2;
        ttc.originY = height/2;
        ttc.screenWidth = Math.round(width*scale);
        ttc.screenHeight = Math.round(height*scale);
        return ttc;
    }

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
}
