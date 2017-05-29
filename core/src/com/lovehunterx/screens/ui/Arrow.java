package com.lovehunterx.screens.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Packet;

public class Arrow extends Image {
    public static final int LEFT = -1;
    public static final int RIGHT = 1;
    private int direction;

    public Arrow(int dir) {
        super(dir == LEFT ? Assets.LEFT_ARROW : Assets.RIGHT_ARROW);
        this.direction = dir;

        /*
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Packet packet = Packet.createMovementPacket(direction);
                LoveHunterX.getConnection().send(packet);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Packet packet = Packet.createMovementPacket(0);
                LoveHunterX.getConnection().send(packet);
            }
        });
        */
    }

}
