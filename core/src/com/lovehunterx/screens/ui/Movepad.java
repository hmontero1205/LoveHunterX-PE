package com.lovehunterx.screens.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.entities.Player;
import com.lovehunterx.networking.Packet;

public class Movepad extends Touchpad {
    public Movepad() {
        super(5, Assets.SKIN);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        Player player = LoveHunterX.getState().getPlayer();
        if(player != null) {
            Gdx.app.log("player x", player.getX() + "");
            float predictedX = player.getX() + (getKnobPercentX() * 100 * delta);
            Gdx.app.log("predicted x", predictedX + "");
            if ((predictedX < 1000 && predictedX> -500) || ((player.getX() < -500 && predictedX > player.getX()) || (player.getX() > 1000 && predictedX < player.getX()))) {
                Packet move = Packet.createMovementPacket(getKnobPercentX(), getKnobPercentY());
                LoveHunterX.getConnection().send(move);
            } else {
                Packet move = Packet.createMovementPacket(0, 0);
                LoveHunterX.getConnection().send(move);
            }
        }
    }
}
