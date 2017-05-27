package com.lovehunterx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

    public static final Skin SKIN = new Skin();
    static {
        FileHandle fontFile = Gdx.files.internal("Roboto-Regular.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 15;

        SKIN.add("roboto", generator.generateFont(parameter), BitmapFont.class);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("neon-ui.atlas"));
        SKIN.addRegions(atlas);
        SKIN.load(Gdx.files.internal("neon-ui.json"));
    }

    public static final Texture LHX_LOGO = new Texture(Gdx.files.internal("LHX.png"));
    static {
        LHX_LOGO.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public static final TextureRegion[] WALK_FRAMES = new TextureRegion[8];
    static {
        Texture walks = new Texture(Gdx.files.internal("walk.png"));
        //Texture walks = new Texture(Gdx.files.internal("boyWalk.png"));
        TextureRegion[][] tmp = TextureRegion.split(walks, walks.getWidth() / 8, walks.getHeight());
        System.arraycopy(tmp[0], 0, WALK_FRAMES, 0, tmp[0].length);
    }

    public static TextureRegion LEFT_ARROW, RIGHT_ARROW;
    static {
        Texture arrows = new Texture(Gdx.files.internal("arrows.png"));
        TextureRegion[][] tmp = TextureRegion.split(arrows, arrows.getWidth() / 2, arrows.getHeight());
        LEFT_ARROW = tmp[0][0];
        RIGHT_ARROW = tmp[0][1];
    }

}
