package com.lovehunterx.networking.listeners;

import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class InvitationListener implements Listener {

    @Override
    public void handle(Packet packet) {
        LoveHunterX.getCurrentScreen().displayInvitation(packet.getData("player"), packet.getData("game"));
    }
}