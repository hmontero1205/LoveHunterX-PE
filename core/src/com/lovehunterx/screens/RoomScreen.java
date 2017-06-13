package com.lovehunterx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.entities.Player;
import com.lovehunterx.networking.Packet;
import com.lovehunterx.screens.ui.Movepad;

public class RoomScreen extends LHXScreen {
    private Stage ui;
    private Stage stage;
    private long hideTime;
    private String room;
    private Image back;

    @Override
    public void show() {
        ui = new Stage(new StretchViewport(640, 480));
        stage = new Stage(new StretchViewport(640, 480));
        LoveHunterX.getState().init(stage, ui);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(ui);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        // background
        back = new Image(new Texture(Gdx.files.internal("roomBack.jpg")));
        back.setPosition(centerX(back), centerY(back));
        stage.addActor(back);

        //test button
//            final TextButton button = new TextButton("Select", Assets.SKIN);
//            button.setTransform(true);
//            button.setScale(1.5f);
//            button.setPosition(centerX(button) - 15, centerY(button) + 150);
//
//            button.addListener(new ClickListener() {
//                public void clicked(InputEvent event, float x, float y) {
//                    button.setText("clicked");
//                    CharacterScreen.resetCSprite();
//                    LoveHunterX.changeScreen(LoveHunterX.CHAR_SCREEN);
//                }
//            });

//            stage.addActor(button);


        LoveHunterX.getState().joinRoom(LoveHunterX.getState().getUsername());
        Packet join = Packet.createJoinRoomPacket(LoveHunterX.getState().getUsername());
        LoveHunterX.getConnection().send(join);

        Movepad pad = new Movepad();
        pad.setSize(100, 100);
        pad.setX(stage.getWidth() - pad.getWidth() - 10);
        pad.setY(10);

        LoveHunterX.getState().addUI(pad);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Player player = LoveHunterX.getState().getEntity(LoveHunterX.getState().getUsername());
        if (player != null) {
            if(player.getX() >= 775)
                stage.getCamera().position.x = 775;
            else{
                if(player.getX() <= -210)
                    stage.getCamera().position.x = -210;
                else
                    stage.getCamera().position.x += ((player.getX() + 62) - stage.getCamera().position.x) * delta * 2F;
            }
            stage.getCamera().update();
        }

        ui.act(Gdx.graphics.getDeltaTime());
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        ui.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        System.out.println("PAUSED");
    }

    @Override
    public void resume() {
        System.out.println("RESUMED");
        Packet statusCheck = new Packet("status");
        LoveHunterX.getConnection().send(statusCheck);
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
