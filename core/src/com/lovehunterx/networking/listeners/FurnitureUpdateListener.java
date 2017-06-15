package com.lovehunterx.networking.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.GameState;
import com.lovehunterx.game.entities.Closet;
import com.lovehunterx.game.entities.Door;
import com.lovehunterx.game.entities.Furniture;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class FurnitureUpdateListener implements Listener {

    @Override
    public void handle(Packet packet) {
        String type = packet.getData("type");
        float x = Float.parseFloat(packet.getData("x"));
        float y = Float.parseFloat(packet.getData("y"));
        int uid = Integer.parseInt(packet.getData("uid"));

        if (packet.getData("uid") == null || LoveHunterX.getState().getEntity(packet.getData("uid")) == null) {
            Furniture furniture;

            if (type.equals("Door")) {
                String dest = packet.getData("destination");
                furniture = new Door(dest, x, y, uid);
            } else if (type.equals("Closet")) {
                furniture = new Closet(x, y, uid);
            } else {
                furniture = new Furniture(type, x, y, uid);
            }


            if (LoveHunterX.getState().isInMode(GameState.Mode.CONFIG)) {
                furniture.toggleConfiguration();
            }

            LoveHunterX.getState().spawnFurniture(furniture);
        } else {
            Actor furn = LoveHunterX.getState().getEntity(String.valueOf(uid));
            furn.setPosition(x, y);
        }
    }
}
