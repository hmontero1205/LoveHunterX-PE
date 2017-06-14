package com.lovehunterx.networking.listeners;

import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.GameState;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class GameStartListener implements Listener {

    @Override
    public void handle(Packet packet) {
        LoveHunterX.getState().toggleMode(GameState.Mode.MINIGAME);
        LoveHunterX.getState().startGame(packet.getData("game"), packet.getData("start"));
    }
}
