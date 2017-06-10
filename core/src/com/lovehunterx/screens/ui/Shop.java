package com.lovehunterx.screens.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lovehunterx.Assets;

/**
 * Created by Hans on 6/10/2017.
 */

public class Shop extends Group {
    private Table wrapper;
    private Table container;
    private Label wallet;

    public Shop(float x, float y, float w, float h) {
        setPosition(x,y);
        wrapper = new Table();
        wrapper.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.SHOP)));
        wrapper.setSize(w,h);
        addActor(wrapper);

        wallet = new Label("You have $0", Assets.SKIN);
        wallet.setPosition(w/2 - wallet.getWidth()/2, h/2 + 60);
        addActor(wallet);

        container = new Table();
        //container.setWidth(w-1);
        container.setDebug(true);

        ScrollPane pane = new ScrollPane(container);
        pane.setSize(w,h-125);
        pane.setOverscroll(false,false);
        wrapper.addActor(pane);
        wrapper.bottom();





    }
}
