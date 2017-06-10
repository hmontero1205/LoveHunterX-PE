package com.lovehunterx.networking.listeners;

import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

/**
 * Created by Hans on 6/10/2017.
 */

public class GetMoneyListener implements Listener {
    @Override
    public void handle(Packet packet) {
        LoveHunterX.getState().updateMoney(Double.parseDouble(packet.getData("money")));
    }
}
