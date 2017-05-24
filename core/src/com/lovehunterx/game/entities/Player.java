package com.lovehunterx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class Player extends Group {
    private Animation<TextureRegion> walkAnimation;
    private Image body;
    private float stateTime;
    private int lastDirection, direction;
    private String name;

    public Player(final String name) {
        walkAnimation = new Animation<TextureRegion>(0.08f, Assets.WALK_FRAMES);
        this.name = name;

        changeDirection(1);

        LoveHunterX.getConnection().registerListener("move", new Listener() {
            @Override
            public void handle(Packet packet) {
                if (!packet.getData("user").equals(name)) {
                    return;
                }

                changeDirection(Integer.valueOf(packet.getData("direction")));
            }
        });
    }

    public void changeDirection(int dir) {
        if (dir != 0) {
            lastDirection = dir;
        }

        direction = dir;
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);

        stateTime += deltaTime;
        if (Gdx.input.justTouched()) {
            changeDirection(direction == -1 ? 1 : -1);
        }

        setX(getX() + direction * 3);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion tex = walkAnimation.getKeyFrame(direction == 0 ? 0.32f : stateTime, true);
        if ((lastDirection == -1 && !tex.isFlipX()) || (lastDirection == 1 && tex.isFlipX())) {
            tex.flip(true, false);
        }

        batch.draw(tex, getX(), getY(), tex.getRegionWidth(), tex.getRegionHeight());
    }
}
