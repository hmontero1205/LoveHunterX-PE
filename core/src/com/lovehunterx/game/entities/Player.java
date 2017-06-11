package com.lovehunterx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;
import com.lovehunterx.screens.RoomScreen;

import java.util.ArrayList;

public class Player extends Group {
    private Animation<TextureRegion> walkAnimation;
    private float stateTime;
    private int lastDirection = 1;
    private Vector2 velocity;

    private TextButton tag;
    private Action saying;

    public Player(final String name, Integer sprite) {
        walkAnimation = new Animation<TextureRegion>(0.08f, Assets.getAnimation(sprite));

        TextureRegion frame = Assets.getAnimation(sprite)[0];

        setName(name);

        velocity = new Vector2();

        tag = new TextButton(name, Assets.SKIN);
        tag.setX(walkAnimation.getKeyFrame(0).getRegionWidth() / 2 - tag.getWidth() / 2);
        tag.setY(walkAnimation.getKeyFrame(0).getRegionHeight());
        tag.addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    Table menu = new Table();
                    menu.top();
                    menu.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tableBack.png")))));
                    menu.setSize(100, 300);
                    float menX = 0;
                    if (getX() < 300 ) {
                        menX = 150;
                    }
                    else {
                        menX = -100;
                    }
                    TextButton viewP = new TextButton("View Profile", Assets.SKIN);
                    viewP.pad(0);
                    menu.add(viewP).width(menu.getWidth());
                    menu.row();
                    TextButton playG = new TextButton("Play Game", Assets.SKIN);
                    playG.pad(0);
                    menu.add(playG).width(menu.getWidth());
                    menu.setPosition(menX, 50);
                    addActor(menu);
                }
            });
        addActor(tag);
        Gdx.app.log("", getX()+"");

        saying = Actions.sequence(Actions.delay(5), Actions.run(new Runnable() {
            @Override

            public void run() {
                tag.setText(getName());
                tag.setSize(tag.getPrefWidth(), tag.getPrefHeight());
                tag.setX(walkAnimation.getKeyFrame(0).getRegionWidth() / 2 - tag.getWidth() / 2);
                tag.setY(walkAnimation.getKeyFrame(0).getRegionHeight());
            }
        }));
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

    public void say(String message) {
        tag.setText(message);
        tag.setSize(tag.getPrefWidth(), tag.getPrefHeight());
        tag.setX(walkAnimation.getKeyFrame(0).getRegionWidth() / 2 - tag.getWidth() / 2);
        tag.setY(walkAnimation.getKeyFrame(0).getRegionHeight());

        saying.reset();
        saying.restart();
        tag.addAction(saying);
    }
}
