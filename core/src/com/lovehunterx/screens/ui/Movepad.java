package com.lovehunterx.screens.ui;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Packet;

public class Movepad extends Touchpad {
    private Vector2 previous;

    public Movepad() {
        super(5, Assets.SKIN);

        previous = new Vector2(0, 0);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (previous.x != getKnobPercentX() || previous.y != getKnobPercentY()) {
            Packet move = Packet.createMovementPacket(getKnobPercentX(), getKnobPercentY());
            LoveHunterX.getConnection().send(move);
        }

        previous.x = getKnobPercentX();
        previous.y = getKnobPercentY();
    }
}
