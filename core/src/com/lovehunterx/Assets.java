package com.lovehunterx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

    public static final Skin SKIN = new Skin(Gdx.files.internal("neon-ui.json"));

    public static final Texture LHX_LOGO = new Texture("LHX.png");

    public static final TextureRegion[] WALK_FRAMES = new TextureRegion[8];
    static {
        Texture texture = new Texture(Gdx.files.internal("walk.png"));
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 8, texture.getHeight());
        System.arraycopy(tmp[0], 0, WALK_FRAMES, 0, tmp[0].length);
    }
}
