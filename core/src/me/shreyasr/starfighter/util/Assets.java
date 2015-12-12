package me.shreyasr.starfighter.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public enum Assets {

    FIGHTER(Texture.class, "fighter.png");

    private final Class type;
    private final String file;

    public String getFile() {
        return file;
    }

    Assets(Class type, String file) {
        this.type = type;
        this.file = file;
    }

    public static void loadAll(AssetManager assetManager) {
//        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        for (Assets asset : Assets.values()) {
            assetManager.load(asset.getFile(), asset.type);
        }
    }
}
