package com.lovehunterx.networking.listeners;

import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.GameState;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class GameEndListener implements Listener {

    @Override
    public void handle(Packet packet) {
        System.out.println(packet.toJSON());

        LoveHunterX.getState().getGame().remove();
        LoveHunterX.getCurrentScreen().displayNotification("You " + packet.getData("result") + " against " + packet.getData("opp") + "!");
        LoveHunterX.getState().toggleMode(GameState.Mode.PLAY);
    }
}
