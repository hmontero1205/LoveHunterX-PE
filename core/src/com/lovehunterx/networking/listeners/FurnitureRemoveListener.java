package com.lovehunterx.networking.listeners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class FurnitureRemoveListener implements Listener {

    @Override
    public void handle(Packet packet) {
        if (packet.getData("uid") == null || LoveHunterX.getState().getEntity(packet.getData("uid")) == null) {
            return;
        } else {
            Actor furn = LoveHunterX.getState().getEntity(packet.getData("uid"));
            furn.remove();
        }
    }
}
