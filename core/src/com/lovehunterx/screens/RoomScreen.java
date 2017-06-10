package com.lovehunterx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.entities.Furniture;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;
import com.lovehunterx.screens.ui.FixedGroup;
import com.lovehunterx.screens.ui.Movepad;
import com.lovehunterx.screens.ui.Sidebar;

import java.util.ArrayList;

public class RoomScreen extends LHXScreen {
    private static int currentId;
    public Stage stage;
    private long hideTime;
    private String room;

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(640, 480));
        LoveHunterX.getState().init(stage);
        Gdx.input.setInputProcessor(stage);


        // background
        Image back = new Image(new Texture(Gdx.files.internal("roomBack.jpg")));
        back.setPosition(centerX(back), centerY(back));
        stage.addActor(back);

        /*
            //test button
            final TextButton button = new TextButton("Select", Assets.SKIN);
            button.setTransform(true);
            button.setScale(1.5f);
            button.setPosition(centerX(button) - 15, centerY(button) + 150);

            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    button.setText("clicked");
                    LoveHunterX.changeScreen(LoveHunterX.CHAR_SCREEN);
                }
            });

            stage.addActor(button);
        */

        Packet join = Packet.createJoinRoomPacket("Hans");
        LoveHunterX.getConnection().send(join);
        LoveHunterX.getState().joinRoom("Hans");

        Movepad pad = new Movepad();
        pad.setSize(100, 100);
        pad.setX(stage.getWidth() - pad.getWidth() - 10);
        pad.setY(10);

        FixedGroup fixed = new FixedGroup();
        fixed.addActor(pad);
        stage.addActor(fixed);
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
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        hideTime = System.currentTimeMillis();
    }

    @Override
    public void resume() {
        long deltaSeconds = (System.currentTimeMillis() - hideTime) / 1000;
        if (deltaSeconds > 4) {
            LoveHunterX.getState().reset();
            LoveHunterX.changeScreen(LoveHunterX.LOGIN_SCREEN);
        }
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    private float centerX(Actor a) {
        return (stage.getWidth() / 2) - (a.getWidth() / 2);
    }

    private float centerY(Actor a) {
        return (stage.getHeight() / 2) - (a.getHeight() / 2);
    }

}
