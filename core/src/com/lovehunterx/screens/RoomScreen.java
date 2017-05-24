package com.lovehunterx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.entities.Player;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class RoomScreen extends LHXScreen {
    private Stage stage;
    private String room;

    @Override
    public void show() {
        stage = new Stage(new FitViewport(640, 480));
        Gdx.input.setInputProcessor(stage);

        stage.addActor(new Player(LoveHunterX.getUsername()));

        LoveHunterX.getConnection().registerListener("join", new Listener() {
            @Override
            public void handle(Packet packet) {
                if (packet.getData("player").equals(LoveHunterX.getUsername())) {
                    room = packet.getData("room");
                }

                stage.addActor(new Player(packet.getData("username")));
            }
        });
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
        LoveHunterX.getConnection().clearListeners();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public Stage getStage() {
        return stage;
    }
}
