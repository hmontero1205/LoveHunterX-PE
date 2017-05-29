package com.lovehunterx.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
    private Vector2 velocity;
    private int direction;

    public Player(final String name) {
        walkAnimation = new Animation<TextureRegion>(0.08f, Assets.WALK_FRAMES);
        setName(name);

        velocity = new Vector2();

        TextButton tag = new TextButton(name, Assets.SKIN);
        tag.setX(walkAnimation.getKeyFrame(0).getRegionWidth() / 2 - tag.getWidth() / 2);
        tag.setY(walkAnimation.getKeyFrame(0).getRegionHeight());
        addActor(tag);

        LoveHunterX.getConnection().registerListener("move", new Listener() {
            @Override
            public void handle(Packet packet) {
                if (!packet.getData("user").equals(name)) {
                    return;
                }

                // changeDirection(Integer.valueOf(packet.getData("direction")));
                setVelocityX(Float.valueOf(packet.getData("vel_x")));
                setVelocityY(Float.valueOf(packet.getData("vel_y")));
            }
        });

        LoveHunterX.getConnection().registerListener("leave", new Listener() {
            @Override
            public void handle(Packet packet) {
                if (packet.getData("user").equals(name)) {
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

    public void setVelocityX(float velocityX) {
        this.velocity.x = velocityX;
    }

    public void setVelocityY(float velocityY) {
        this.velocity.y = velocityY;
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);

        stateTime += deltaTime;
        // setX(getX() + direction * 30 * deltaTime);

        Vector2 curr = new Vector2(getX(), getY()).mulAdd(velocity, deltaTime * 100);
        setX(curr.x);
        setY(curr.y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        TextureRegion tex = walkAnimation.getKeyFrame(velocity.x == 0 && velocity.y == 0 ? 0.32f : stateTime, true);
        if ((velocity.x < 0 && !tex.isFlipX()) || (velocity.x > 0 && tex.isFlipX())) {
            tex.flip(true, false);
        }

        batch.draw(tex, getX(), getY(), tex.getRegionWidth(), tex.getRegionHeight());
    }
}
