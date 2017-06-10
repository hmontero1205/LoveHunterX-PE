package com.lovehunterx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.GameState;
import com.lovehunterx.networking.Packet;

public class Furniture extends Table {
    private Table options;
    private Vector2 startPosition;

    private String type;

    public Furniture(String type, float x, float y, int uid) {
        this.options = createOptions();
        this.startPosition = new Vector2(x, y);

        this.type = type;
        setPosition(x, y);
        setSize(170, 150);
        setName(String.valueOf(uid));

        Image item = new Image(new Texture(Gdx.files.internal(type + ".png")));
        item.setSize(170, 150);

        item.addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                if (!LoveHunterX.getState().isInMode(GameState.Mode.CONFIG)) {
                    return;
                }

                float deltaY = y - getHeight() / 2;
                float deltaX = x - getWidth() / 2;
                moveBy(deltaX, deltaY);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (!LoveHunterX.getState().isInMode(GameState.Mode.CONFIG)) {
                    return;
                }

                if (getX() > 470)
                    setX(470);
                if (getX() < 0)
                    setX(0);
                if (getY() < 30)
                    setY(30);
                if (getY() > 60)
                    setY(60);
            }
        });

        item.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (LoveHunterX.getState().isInMode(GameState.Mode.PLAY)) {
                    Furniture.this.clicked();
                }
            }
        });

        add(item);
        row();

        align(Align.top | Align.center);
    }

    public Furniture(String type, float x, float y) {
        this(type, x, y, -1);
    }

    public void clicked() {
    }

    private Table createOptions() {
        Table opts = new Table();

        TextButton cancel = new TextButton("X", Assets.SKIN);
        cancel.setWidth(35);
        cancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!allowRemoval()) {
                    return;
                }

                LoveHunterX.getState().restockInventory(type);

                Packet p = Packet.createRemoveFurniturePacket(Furniture.this);
                LoveHunterX.getConnection().send(p);
            }
        });

        TextButton confirm = new TextButton("/", Assets.SKIN);
        confirm.setWidth(35);
        confirm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveStart();

                Packet p = Packet.createFurniturePacket(Furniture.this);
                LoveHunterX.getConnection().send(p);
            }
        });

        opts.add(cancel, confirm);
        opts.align(Align.center);
        return opts;
    }

    public void toggleConfiguration() {
        if (LoveHunterX.getState().isInMode(GameState.Mode.PLAY)) {
            removeActor(options);
            saveStart();
        } else if (LoveHunterX.getState().isInMode(GameState.Mode.CONFIG)) {
            add(options);
            row();

            setPosition(startPosition.x, startPosition.y);
        }
    }

    public boolean allowRemoval() {
        return true;
    }

    private void saveStart() {
        startPosition.x = getX();
        startPosition.y = getY();
    }

    public String getType() {
        return type;
    }

    public int getUniqueId() {
        return Integer.valueOf(getName());
    }

}
