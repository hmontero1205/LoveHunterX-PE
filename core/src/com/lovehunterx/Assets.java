package com.lovehunterx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

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

    public static final TextureRegion[] WALK_FRAMES1 = new TextureRegion[8];

    static {
        Texture walks = new Texture(Gdx.files.internal("walk1.png"));
        TextureRegion[][] tmp = TextureRegion.split(walks, walks.getWidth() / 8, walks.getHeight());
        System.arraycopy(tmp[0], 0, WALK_FRAMES1, 0, tmp[0].length);
    }

    public static final TextureRegion[] WALK_FRAMES2 = new TextureRegion[8];

    static {
        Texture walks = new Texture(Gdx.files.internal("walk2.png"));
        TextureRegion[][] tmp = TextureRegion.split(walks, walks.getWidth() / 8, walks.getHeight());
        System.arraycopy(tmp[0], 0, WALK_FRAMES2, 0, tmp[0].length);
    }

    public static final TextureRegion[] WALK_FRAMES1g = new TextureRegion[8];

    static {
        Texture walks = new Texture(Gdx.files.internal("test.png"));
        ;
        TextureRegion[][] tmp = TextureRegion.split(walks, walks.getWidth() / 8, walks.getHeight());
        System.arraycopy(tmp[0], 0, WALK_FRAMES1g, 0, tmp[0].length);
    }

    public static final TextureRegion[] WALK_FRAMES2g = new TextureRegion[8];

    static {
        Texture walks = new Texture(Gdx.files.internal("walk2g.png"));
        ;
        TextureRegion[][] tmp = TextureRegion.split(walks, walks.getWidth() / 8, walks.getHeight());
        System.arraycopy(tmp[0], 0, WALK_FRAMES2g, 0, tmp[0].length);
    }

    public static TextureRegion LEFT_ARROW, RIGHT_ARROW;

    static {
        Texture arrows = new Texture(Gdx.files.internal("arrows.png"));
        TextureRegion[][] tmp = TextureRegion.split(arrows, arrows.getWidth() / 2, arrows.getHeight());
        LEFT_ARROW = tmp[0][0];
        RIGHT_ARROW = tmp[0][1];
    }

    public static final Texture SIDE_BAR = new Texture(Gdx.files.internal("tableBack.png"));

    public static final TextureRegion[] getAnimation(int sprite) {
        switch (sprite) {
            case 0:
                return WALK_FRAMES1;
            case 1:
                return WALK_FRAMES1g;
            case 2:
                return WALK_FRAMES2;
            case 3:
                return WALK_FRAMES2g;
            default:
                return WALK_FRAMES1;
        }
    }
}


