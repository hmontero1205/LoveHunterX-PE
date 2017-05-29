package com.lovehunterx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.entities.Player;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;
import com.lovehunterx.screens.ui.FixedGroup;
import com.lovehunterx.screens.ui.Movepad;

public class RoomScreen extends LHXScreen {
    private Stage stage;
    private String room;

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(640, 480));

        Gdx.input.setInputProcessor(stage);

        LoveHunterX.getConnection().registerListener("join", new Listener() {
            @Override
            public void handle(Packet packet) {
                System.out.println(packet.toJSON());
                if (packet.getData("user").equals(LoveHunterX.getUsername())) {
                    room = packet.getData("room");
                }

                Player player = new Player(packet.getData("user"));
                player.setX(Float.valueOf(packet.getData("x")));
                player.setY(Float.valueOf(packet.getData("y")));
                stage.addActor(player);
            }
        });

        Packet join = Packet.createJoinRoomPacket("main");
        LoveHunterX.getConnection().send(join);

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
