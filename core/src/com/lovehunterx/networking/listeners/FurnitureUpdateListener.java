package com.lovehunterx.networking.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.entities.Furniture;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class FurnitureUpdateListener implements Listener {

    @Override
    public void handle(Packet packet) {
        Gdx.app.log("furniture", packet.toJSON());
        Gdx.app.log("", packet.getData("uid"));

        if (packet.getData("uid") == null || LoveHunterX.getState().getEntity(packet.getData("uid")) == null) {
            LoveHunterX.getState().spawnEntity(new Furniture(packet.getData("type"), Float.parseFloat(packet.getData("x")), Float.parseFloat(packet.getData("y")), Integer.valueOf(packet.getData("uid"))));
        } else {
            Actor furn = LoveHunterX.getState().getEntity(packet.getData("uid"));
            furn.setPosition(Float.parseFloat(packet.getData("x")), Float.parseFloat(packet.getData("y")));
        }
    }
}
