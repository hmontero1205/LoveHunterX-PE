package com.lovehunterx.networking.listeners;

import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.entities.Player;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class PlayerJoinListener implements Listener {

    @Override

    //create the sidebar
    public void handle(Packet packet) {
        if (LoveHunterX.getState().getEntity(packet.getData("user")) != null) {
            return;
        }

        if(LoveHunterX.getState().getUsername().equals(packet.getData("user"))) {

        }

        Player player = new Player(packet.getData("user"), 0);
        player.setX(Float.valueOf(packet.getData("x")));
        player.setY(Float.valueOf(packet.getData("y")));
        LoveHunterX.getState().spawnEntity(player);
    }
}
