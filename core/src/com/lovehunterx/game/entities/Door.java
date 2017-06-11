package com.lovehunterx.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.GameState;
import com.lovehunterx.networking.Packet;

public class Door extends Furniture {
    private TextButton tag;
    private String destination;

    public Door(String destination, float x, float y, int uid) {
        super("Door", x, y, uid);
        setHeight(getHeight() - 170);
        setWidth(getWidth() - 50);
        this.destination = destination == null ? "Hallway" : destination;

        if (destination != null) {
            tag = new TextButton(destination, Assets.SKIN);
            tag.setPosition((getWidth() / 2) - (tag.getWidth() / 2), getHeight());
            addActor(tag);
        }
    }

    @Override
    public void clicked() {
        LoveHunterX.getState().joinRoom(destination);
        Packet join = Packet.createJoinRoomPacket(destination);
        LoveHunterX.getConnection().send(join);
    }

    @Override
    public boolean allowRemoval() {
        return false;
    }
}
