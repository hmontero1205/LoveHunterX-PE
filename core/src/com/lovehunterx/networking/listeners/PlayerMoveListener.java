package com.lovehunterx.networking.listeners;

import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.entities.Player;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class PlayerMoveListener implements Listener {

    @Override
    public void handle(Packet packet) {
        Player player = LoveHunterX.getState().getEntity(packet.getData("user"));
        if (player == null) {
            return;
        }

        if (packet.getData("x") != null) {
            player.setX(Float.valueOf(packet.getData("x")));
        }

        if (packet.getData("y") != null) {
            player.setY(Float.valueOf(packet.getData("y")));
        }

        if (packet.getData("vel_x") != null) {
            player.setVelocityX(Float.valueOf(packet.getData("vel_x")));
        }

        if (packet.getData("vel_y") != null) {
            player.setVelocityY(Float.valueOf(packet.getData("vel_y")));
        }
    }
}
