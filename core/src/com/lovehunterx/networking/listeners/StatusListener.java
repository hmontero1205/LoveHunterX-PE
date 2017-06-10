package com.lovehunterx.networking.listeners;

import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class StatusListener implements Listener {

    @Override
    public void handle(Packet packet) {
        boolean status = Boolean.parseBoolean(packet.getData("state"));

        if (!status) {
            LoveHunterX.getState().reset();
            LoveHunterX.changeScreen(LoveHunterX.LOGIN_SCREEN);
        }
    }
}
