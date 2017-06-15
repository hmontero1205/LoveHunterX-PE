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

        if (packet.getData("result").equals("dc")) {
            LoveHunterX.getCurrentScreen().displayNotification(packet.getData("opp") + " left the room!");
        } else if (packet.getData("result").equals("won")) {
            LoveHunterX.getCurrentScreen().displayNotification("You won against " + packet.getData("opp") + " and earned 50 dollars.");
        } else {
            LoveHunterX.getCurrentScreen().displayNotification("You " + packet.getData("result") + " against " + packet.getData("opp") + "!");
        }

        LoveHunterX.getState().toggleMode(GameState.Mode.PLAY);
    }
}
