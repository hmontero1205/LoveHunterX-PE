package com.lovehunterx.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class Player extends Group {
    private Animation<TextureRegion> walkAnimation;
    private Image body;
    private float stateTime;
    private int lastDirection = 1;
    private int direction;

    public Player(final String name) {
        walkAnimation = new Animation<TextureRegion>(0.08f, Assets.WALK_FRAMES);
        setName(name);

        TextButton tag = new TextButton(name, Assets.SKIN);
        tag.setX(walkAnimation.getKeyFrame(0).getRegionWidth() / 2 - tag.getWidth() / 2);
        tag.setY(walkAnimation.getKeyFrame(0).getRegionHeight());
        addActor(tag);

        LoveHunterX.getConnection().registerListener("move", new Listener() {
            @Override
            public void handle(Packet packet) {
                System.out.println(packet.toJSON());
                if (!packet.getData("user").equals(name)) {
                    return;
                }

                changeDirection(Integer.valueOf(packet.getData("direction")));
            }
        });

        LoveHunterX.getConnection().registerListener("leave", new Listener() {
            @Override
            public void handle(Packet packet) {
                if (packet.getData("player").equals(name)) {
                    remove();
                }
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
        setX(getX() + direction * 30 * deltaTime);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        TextureRegion tex = walkAnimation.getKeyFrame(direction == 0 ? 0.32f : stateTime, true);
        if ((lastDirection == -1 && !tex.isFlipX()) || (lastDirection == 1 && tex.isFlipX())) {
            tex.flip(true, false);
        }

        batch.draw(tex, getX(), getY(), tex.getRegionWidth(), tex.getRegionHeight());
    }
}
