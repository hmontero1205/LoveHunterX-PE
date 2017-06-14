package com.lovehunterx.networking.listeners;

import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;
import com.lovehunterx.screens.ui.TicTacToe;

public class GameTTTMoveListener implements Listener {

    @Override
    public void handle(Packet packet) {
        TicTacToe ttt = (TicTacToe) LoveHunterX.getState().getGame();
        ttt.place(Integer.valueOf(packet.getData("mode")), Integer.valueOf(packet.getData("row")), Integer.valueOf(packet.getData("col")));
        ttt.requestTurn();
    }
}
