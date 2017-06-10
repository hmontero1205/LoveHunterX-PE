package com.lovehunterx.networking.listeners;

import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class InventoryUpdateListener implements Listener {

    @Override
    public void handle(Packet packet) {
        for (int a = 0; a < Integer.parseInt(packet.getData("amount")); a++) {
            LoveHunterX.getState().restockInventory(packet.getData("type"));
        }
    }
}
