package com.lovehunterx.networking.listeners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.entities.Player;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;


public class PlayerLeaveListener implements Listener {

    @Override
    public void handle(Packet packet) {
        Player player = LoveHunterX.getState().getEntity(packet.getData("user"));
        if (player != null) {
            player.remove();
        }
    }
}
