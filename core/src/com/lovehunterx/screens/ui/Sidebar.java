package com.lovehunterx.screens.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.GameState;
import com.lovehunterx.game.entities.Door;
import com.lovehunterx.game.entities.Furniture;
import com.lovehunterx.networking.Packet;

public class Sidebar extends Group {
    private Table wrapper;
    private Table container;
    private TextButton tab;
    private boolean isOpen;

    public Sidebar(float x, float y, float w, float h) {
        setPosition(-w, y);

        wrapper = new Table();
        wrapper.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.SIDE_BAR)));
        wrapper.setSize(w, h);

        container = new Table();
        ScrollPane pane = new ScrollPane(container);
        pane.setSize(w, h);
        pane.setOverscroll(false, false);
        wrapper.add(pane);

        tab = new TextButton(">", Assets.SKIN);
        tab.setWidth(35);
        tab.setPosition(wrapper.getWidth() - 10, wrapper.getY() + (wrapper.getHeight() / 2) - 10);
        tab.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (tab.getText().toString().equals(">") && LoveHunterX.getState().isInMode(GameState.Mode.PLAY)) {
                    open();
                } else if (tab.getText().toString().equals("<")) {
                    close();
                }
            }
        });

        addActor(wrapper);
        addActor(tab);

        container.top();
        wrapper.top();
    }

    public void addItem(String type) {
        container.add(new SidebarItem(type));
        container.row();
    }

    public void removeItem(SidebarItem item) {
        container.removeActor(item);

        // check if top item has a bar; if so, remove
        if (LoveHunterX.getState().getInventoryAmount() > 0) {
            Array<Cell> cells = container.getCells();
            System.out.println(cells.size);
        }
    }

    public void open() {
        isOpen = true;
        tab.setText("<");
        MoveByAction act = Actions.moveBy(wrapper.getWidth() - 3, 0, .25F, Interpolation.smoother);
        addAction(act);

        LoveHunterX.getState().toggleMode(GameState.Mode.CONFIG);
        toggleFurniture();
    }

    public void close() {
        isOpen = false;
        tab.setText(">");
        MoveByAction act = Actions.moveBy(-wrapper.getWidth() + 3, 0, .25F, Interpolation.smoother);
        addAction(act);

        LoveHunterX.getState().toggleMode(GameState.Mode.PLAY);
        toggleFurniture();
    }

    private void toggleFurniture() {
        for (Actor a : LoveHunterX.getState().getWorld().getRoot().getChildren()) {
            if (!(a instanceof Furniture) || a instanceof Door) {
                continue;
            }

            Furniture f = (Furniture) a;
            f.toggleConfiguration();
        }
    }

    public void clear() {
        container.clear();
    }

    public boolean getIsOpen() {
        return this.isOpen;
    }

    public class SidebarItem extends Table {

        public SidebarItem(String type) {
            setName(type);

            // bar
            if (LoveHunterX.getState().getInventoryAmount() > 0) {
                Image bar = new Image(new Texture(Gdx.files.internal("sep.png")));
                bar.setName("bar");
                add(bar);
                row();
            }

            // preview
            Image image = new Image(new Texture(Gdx.files.internal(type + " Icon.png")));
            add(image);
            row();

            // name
            Label desc = new Label(type, Assets.SKIN);
            desc.setFontScale(.65f);
            add(desc);

            addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    LoveHunterX.getState().depleteInventory(SidebarItem.this);

                    Packet packet = Packet.createFurniturePacket(new Furniture(getName(), 200, 30));
                    LoveHunterX.getConnection().send(packet);
                }
            });
        }
    }
}
