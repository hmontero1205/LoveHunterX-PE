package com.lovehunterx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
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
    private boolean fixed;

    @Override
    public void show() {
        ui = new Stage(new StretchViewport(640, 480));
        //ui.setDebugAll(true);

        stage = new Stage(new StretchViewport(640, 480));
        //stage.setDebugAll(true);
        LoveHunterX.getState().init(stage, ui);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(ui);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        // background
        back = new Image(new Texture(Gdx.files.internal("roomBack.jpg")));
        back.setPosition(centerX(back), centerY(back));
        stage.addActor(back);

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
            if (player.getX() >= 775) {
                if (!fixed) {
                    stage.getCamera().translate(775 - player.getX(), 0, 0);
                    fixed = true;
                }
            } else {
                if (player.getX() <= -210) {
                    if (!fixed) {
                        stage.getCamera().translate(player.getX() - -210, 0, 0);
                        fixed = true;
                    }
                } else {
                    stage.getCamera().position.x += ((player.getX() + 62) - stage.getCamera().position.x) * delta * 2F;
                    fixed = false;
                }
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
        return ui;
    }

    private float centerX(Actor a) {
        return (stage.getWidth() / 2) - (a.getWidth() / 2);
    }

    private float centerY(Actor a) {
        return (stage.getHeight() / 2) - (a.getHeight() / 2);
    }

}
