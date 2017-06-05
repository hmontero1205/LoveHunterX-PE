package com.lovehunterx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;

import java.util.ArrayList;

public class CharacterScreen extends LHXScreen {
    private final static int NEXT = 0;
    private final static int PREV = 1;
    public Stage stage;
    private ArrayList<Texture> sprite;
    private Image slot1;
    private Image slot2;
    private Image slot3;
    private int cSprite = 1;

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void show() {
        //stage setup
        stage = new Stage(new StretchViewport(640, 480));
        Gdx.input.setInputProcessor(stage);
        Image back = new Image(new Texture(Gdx.files.internal("customizeBack.png")));
        back.setSize(645, 485);
        stage.addActor(back);

        //button set up
        Image left = new Image(new Texture(Gdx.files.internal("arrowLeft.png")));
        left.setSize(100, 50);
        left.setPosition(centerX(left) - 100, centerY(left) - 150);

        Image right = new Image(new Texture(Gdx.files.internal("arrowRight.png")));
        right.setSize(100, 50);
        right.setPosition(centerX(right) + 100, centerY(right) - 150);

        final TextButton button = new TextButton("Select", Assets.SKIN);
        button.setTransform(true);
        button.setScale(1.5f);
        button.setPosition(centerX(button) - 15, centerY(button) - 150);

        left.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                button.setText("left");
                loopSprites(PREV);
            }
        });

        button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                button.setText("clicked");
                LoveHunterX.getState().setcSprite(cSprite);
                LoveHunterX.changeScreen(LoveHunterX.ROOM_SCREEN);
            }
        });

        right.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                button.setText("right");
                loopSprites(NEXT);
            }
        });

        //sprite set up
        sprite = new ArrayList<Texture>();
        Texture s1 = new Texture(Gdx.files.internal("sprite1.png"));
        Texture s2 = new Texture(Gdx.files.internal("sprite2.png"));
        Texture s3 = new Texture(Gdx.files.internal("sprite3.png"));
        Texture s4 = new Texture(Gdx.files.internal("sprite4.png"));
        sprite.add(s1);
        sprite.add(s2);
        sprite.add(s3);
        sprite.add(s4);

        slot1 = new Image(s1);
        slot1.setWidth(150);
        slot1.setHeight(150);
        slot1.setPosition(centerX(slot1) - 200, centerY(slot1) + 100);

        slot2 = new Image(s2);
        slot2.setWidth(150);
        slot2.setHeight(150);
        slot2.setPosition(centerX(slot2), centerY(slot2));

        slot3 = new Image(s3);
        slot3.setWidth(150);
        slot3.setHeight(150);
        slot3.setPosition(centerX(slot3) + 200, centerY(slot3) + 100);

        stage.addActor(right);
        stage.addActor(left);
        stage.addActor(button);

        stage.addActor(slot1);
        stage.addActor(slot2);
        stage.addActor(slot3);

    }

    public void loopSprites(int dir) {
        if (dir == NEXT) {
            slot1.setDrawable(new TextureRegionDrawable(new TextureRegion(sprite.get(getNext(cSprite, 1)))));
            slot2.setDrawable(new TextureRegionDrawable(new TextureRegion(sprite.get(getNext(cSprite, 2)))));
            slot3.setDrawable(new TextureRegionDrawable(new TextureRegion(sprite.get(getNext(cSprite, 3)))));
            cSprite = cSprite + 1 % sprite.size();
        } else {
            slot1.setDrawable(new TextureRegionDrawable(new TextureRegion(sprite.get(getNext(cSprite, -1)))));
            slot2.setDrawable(new TextureRegionDrawable(new TextureRegion(sprite.get(getNext(cSprite, -2)))));
            slot3.setDrawable(new TextureRegionDrawable(new TextureRegion(sprite.get(getNext(cSprite, -3)))));
            cSprite = cSprite - 1 % sprite.size();
        }
    }

    public int correctMod(int a, int b){
        return (a % b + b) % b;
    }

    public int getNext(int current, int change) {
        return correctMod(current+change,sprite.size());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private float centerX(Actor a) {
        return (stage.getWidth() / 2) - (a.getWidth() / 2);
    }

    private float centerY(Actor a) {
        return (stage.getHeight() / 2) - (a.getHeight() / 2);
    }
}
