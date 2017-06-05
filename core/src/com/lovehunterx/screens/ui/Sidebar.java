package com.lovehunterx.screens.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lovehunterx.Assets;

public class Sidebar extends Group {
    private Table wrapper;
    private Table container;

    public Sidebar(float x, float y, float w, float h) {
        setPosition(x, y);

        wrapper = new Table();
        wrapper.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.SIDE_BAR)));
        wrapper.setSize(w, h);

        container = new Table();
        ScrollPane pane = new ScrollPane(container);
        pane.setSize(w, h);
        pane.setOverscroll(false, false);
        wrapper.addActor(pane);

        final TextButton tab = new TextButton(">", Assets.SKIN);
        tab.setWidth(35);
        tab.setPosition(wrapper.getWidth() - 10, wrapper.getY() + (wrapper.getHeight() / 2) - 10);
        tab.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (tab.getText().toString().equals(">")) {
                    slideOut();
                    tab.setText("<");
                } else if (tab.getText().toString().equals("<")) {
                    slideIn();
                    tab.setText(">");
                }
            }
        });

        addActor(wrapper);
        addActor(tab);
    }

    public void addItem(Actor actor) {
        container.addActor(actor);
    }

    public void slideOut() {
        MoveByAction act = Actions.moveBy(wrapper.getWidth() - 3, 0, .25F, Interpolation.smoother);
        addAction(act);
    }

    public void slideIn() {
        MoveByAction act = Actions.moveBy(-wrapper.getWidth() + 3, 0, .25F, Interpolation.smoother);
        addAction(act);
    }

}
