package com.lovehunterx.screens.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.lovehunterx.Assets;

import java.text.DecimalFormat;

/**
 * Created by micha on 6/14/2017.
 */

public class Info extends Group {
    private Table wrapper;
    private Table container;

    public Info(float x, float y, float w, float h){
        setPosition(x, y);
        //back popup
        wrapper = new Table();
        wrapper.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.INFO)));
        wrapper.setSize(w, h);
        addActor(wrapper);

        container = new Table();
        container.pad(5);
        //container.debug();

        ScrollPane pane = new ScrollPane(container);
        pane.setSize(w, h - 145);
        pane.setOverscroll(true, false);
        wrapper.addActor(pane);
        wrapper.bottom();

        container.top();
        Label temp = new Label("Welcome to LoveHunterX-PE, a place where you can pursue your interests. Good luck and go get them xD!",Assets.SKIN);
        temp.setWrap(true);
        temp.setAlignment(Align.center);
        container.add(temp).width(400).colspan(4);
        container.row().padTop(10);
        Image chat = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("chatbut.png"))));
        container.add(chat);
        container.add(new Label("Click to Chat!",Assets.SKIN));
        Image cart = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("cart.png"))));
        container.add(cart);
        container.add(new Label("Click to Shop!",Assets.SKIN));
        container.row().height(100);
        Label temp2 = new Label("Feel free to explore and find your love and engage in some passionate activities! But don't forget to invite that special one to your personalized room to chill!",Assets.SKIN);
        temp2.setWrap(true);
        temp2.setAlignment(Align.center);
        container.add(temp2).width(400).colspan(4);
        container.row().padTop(10);
        Label fHeader = new Label("--------------Furniture--------------",Assets.SKIN);
        fHeader.setAlignment(Align.center);
        container.add(fHeader).width(400).colspan(4);
        container.row().padTop(10);
        Label temp3 = new Label("On the left side bar, you can open your inventory to toggle build mode. Tapping on the furniture places it into your room. The checkmark saves the position and the x restores it back to your inventory. Closing the inventory exits build mode.",Assets.SKIN);
        temp3.setWrap(true);
        temp3.setAlignment(Align.center);
        container.add(temp3).width(400).colspan(4);
        //about us
        container.row().padTop(10);;
        Label aHeader = new Label("--------------About LoveHunterX: Pocket Edition--------------",Assets.SKIN);
        aHeader.setAlignment(Align.center);
        container.add(aHeader).width(400).colspan(4);
        container.row().padTop(10);;
        Label temp4 = new Label("Created by Kevin Zheng, Hans Montero, Michael Li and Shoheb Ome for Brooklyn Tech HS's First Annual CS Symposium. (6/15/2017)",Assets.SKIN);
        temp4.setWrap(true);
        temp4.setAlignment(Align.center);
        container.add(temp4).width(400).colspan(4);


        Image logo = new Image(Assets.LHX_LOGO);
        logo.setScale(.5f);
        logo.setPosition(120 , h / 2 + 80);
        addActor(logo);
    }
}
