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

import java.util.ArrayList;

public class Player extends Group {
    private Animation<TextureRegion> walkAnimation;
    private Image body;
    private float stateTime;
    private int lastDirection = 1;
    private Vector2 velocity;
    private ArrayList<TextureRegion[]> TR = new ArrayList<TextureRegion[]>();

    public Player(final String name) {
        //set up sprites
        TR.add(Assets.WALK_FRAMES1);
        TR.add(Assets.WALK_FRAMES1g);
        TR.add(Assets.WALK_FRAMES2);
        TR.add(Assets.WALK_FRAMES2g);

        walkAnimation = new Animation<TextureRegion>(0.08f, Assets.WALK_FRAMES1g);

        setName(name);

        velocity = new Vector2();

        TextButton tag = new TextButton(name, Assets.SKIN);
        tag.setX(walkAnimation.getKeyFrame(0).getRegionWidth() / 2 - tag.getWidth() / 2);
        tag.setY(walkAnimation.getKeyFrame(0).getRegionHeight());
        addActor(tag);

        LoveHunterX.getConnection().registerListener("move", new Listener() {
            @Override
            public void handle(Packet packet) {
                System.out.println("Called: " + packet.toJSON());
                if (!packet.getData("user").equals(name)) {
                    return;
                }

                // changeDirection(Integer.valueOf(packet.getData("direction")));
                if (packet.getData("x") != null) {
                    setX(Float.valueOf(packet.getData("x")));
                }

                if (packet.getData("y") != null) {
                    setY(Float.valueOf(packet.getData("y")));
                }

                if (packet.getData("vel_x") != null) {
                    setVelocityX(Float.valueOf(packet.getData("vel_x")));
                }

                if (packet.getData("vel_y") != null) {
                    setVelocityY(Float.valueOf(packet.getData("vel_y")));
                }
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

    public void setVelocityX(float velocityX) {
        this.velocity.x = velocityX;
    }

    public void setVelocityY(float velocityY) {
        this.velocity.y = velocityY;
    }

    public void setWalkAnimation(Animation<TextureRegion> animate){
        this.walkAnimation = animate;
    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        stateTime += deltaTime;

        setX(getX() + velocity.x * deltaTime * 100);
        setY(Math.max(0, getY() + velocity.y * deltaTime * 200));

        velocity.y = getY() != 0 ? velocity.y - 2F * deltaTime : 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        TextureRegion tex = walkAnimation.getKeyFrame(velocity.x == 0 && velocity.y == 0 || getY() != 0 ? 0.32f : stateTime, true);
        if ((velocity.x < 0 && !tex.isFlipX()) || (velocity.x >= 0 && tex.isFlipX())) {
            tex.flip(true, false);
        }

        batch.draw(tex, getX(), getY(), tex.getRegionWidth(), tex.getRegionHeight());
    }
}
