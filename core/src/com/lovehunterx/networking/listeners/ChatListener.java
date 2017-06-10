package com.lovehunterx.networking.listeners;

import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.entities.Player;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class ChatListener implements Listener {

    @Override
    public void handle(Packet packet) {
        Player player = LoveHunterX.getState().getEntity(packet.getData("user"));
        if (player != null) {
            player.say(packet.getData("message"));
        }
    }
}
