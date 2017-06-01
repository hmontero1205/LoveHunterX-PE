package com.lovehunterx.screens.ui;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Packet;

public class Movepad extends Touchpad {
    public Movepad() {
        super(5, Assets.SKIN);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        Packet move = Packet.createMovementPacket(getKnobPercentX(), getKnobPercentY());
        LoveHunterX.getConnection().send(move);
    }
}
