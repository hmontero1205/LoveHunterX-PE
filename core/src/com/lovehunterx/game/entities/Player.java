package com.lovehunterx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private boolean menuToggled;
    private Table playerMenu;

    private TextButton message;

    public Player(final String name, int sprite) {
        walkAnimation = new Animation<TextureRegion>(0.08f, Assets.getAnimation(sprite));

        setName(name);

        velocity = new Vector2();

        TextButton tag = new TextButton(name, Assets.SKIN);
        tag.setX(walkAnimation.getKeyFrame(0).getRegionWidth() / 2 - tag.getWidth() / 2);
        tag.setY(walkAnimation.getKeyFrame(0).getRegionHeight());

        playerMenu = createPlayerMenu();

        tag.addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    if(!getName().equals(LoveHunterX.getState().getUsername()))
                        toggleMenu();

                }
            });
        addActor(tag);
        Gdx.app.log("", getX()+"");


        message = new TextButton("", Assets.SKIN);
    }

    public void toggleMenu() {
        menuToggled = !menuToggled;
        if(menuToggled){
            float menX = 0;
            if (getX() < 300 ) {
                menX = 140;
            }
            else {
                menX = -60;
            }

            playerMenu.setPosition(menX,200);
            addActor(playerMenu);
        }
        else{
            playerMenu.remove();
        }
    }

    private Table createPlayerMenu() {
        Table menu = new Table();
        menu.top();
        menu.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("playermenu.png")))));
        menu.setSize(100, 150);
        Label title = new Label("Options", Assets.SKIN);
        menu.add(title).padTop(5);
        menu.row();
        TextButton viewP = new TextButton("View Profile", Assets.SKIN);
        viewP.pad(0);
        menu.add(viewP).width(menu.getWidth());
        menu.row();
        TextButton playG = new TextButton("Play Game", Assets.SKIN);
        playG.pad(0);
        menu.add(playG).width(menu.getWidth());

        return menu;
    }
    public void setVelocityX(float velocityX) {
        if (velocityX != 0) {
            lastDirection = velocityX > 0 ? 1 : -1;
        }

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

        if(menuToggled && getX() > 300)
            playerMenu.setX(-60);
        else
            if(menuToggled)
                playerMenu.setX(140);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        TextureRegion tex = walkAnimation.getKeyFrame(velocity.x == 0 && velocity.y == 0 || getY() != 0 ? 0.32f : stateTime, true);
        if ((velocity.x < 0 && !tex.isFlipX()) || (velocity.x > 0 && tex.isFlipX())) {
            tex.flip(true, false);
        } else if (velocity.x == 0 && (lastDirection == -1 && !tex.isFlipX() || lastDirection == 1 && tex.isFlipX())) {
            tex.flip(true, false);
        }

        batch.draw(tex, getX(), getY(), tex.getRegionWidth(), tex.getRegionHeight());
    }

    public void say(String msg) {
        message.remove();
        message.setText(msg);
        message.setSize(message.getPrefWidth(), message.getPrefHeight());
        message.setX(walkAnimation.getKeyFrame(0).getRegionWidth() / 2 - message.getWidth() / 2);
        message.setY(walkAnimation.getKeyFrame(0).getRegionHeight() + 35);
        message.clearActions();
        addActor(message);

        message.addAction(Actions.sequence(Actions.delay(4), Actions.removeActor()));
    }
}
