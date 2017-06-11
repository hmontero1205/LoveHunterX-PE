package com.lovehunterx.screens.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Packet;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Hans on 6/10/2017.
 */

public class Shop extends Group {
    private Table wrapper;
    private Table container;
    private Label wallet;
    private double money;

    public Shop(float x, float y, float w, float h) {
        setPosition(x, y);
        wrapper = new Table();
        wrapper.setBackground(new TextureRegionDrawable(new TextureRegion(Assets.SHOP)));
        wrapper.setSize(w, h);
        addActor(wrapper);
        wallet = new Label("You have $" + new DecimalFormat("0.00").format(money), Assets.SKIN);
        wallet.setPosition(w / 2 - wallet.getWidth() / 2, h / 2 + 60);
        addActor(wallet);

        Label sep = new Label("-------------------THIS WEEK'S CATALOGUE-------------------", Assets.SKIN);
        sep.setPosition(w / 2 - sep.getWidth() / 2, h / 2 + 40);
        addActor(sep);

        container = new Table();
        container.pad(5);
        //container.setWidth(w-1);
        //container.setDebug(true);

        ScrollPane pane = new ScrollPane(container);
        pane.setSize(w, h - 145);
        pane.setOverscroll(true, false);
        wrapper.addActor(pane);
        wrapper.bottom();

        container.top();
        container.add(new ShopItem("Love Sofa", "This sofa is mad nice yo buy it please", 6.34));
        container.row();
        container.add(new Image(new Texture(Gdx.files.internal("shopsep.png"))));
        container.row();
        container.add(new ShopItem("Love Sofa","This sofa is almost mad nice buy it please mad cheap",6.33));
    }

    public void setMoney(double m) {
        this.money = m;
        wallet.setText("You have $" + new DecimalFormat("0.00").format(m));
        wallet.setPosition(wrapper.getWidth() / 2 - wallet.getWidth() / 2, wrapper.getHeight() / 2 + 60);
    }

    private class ShopItem extends Table {
        private String name;
        private String desc;
        private Double price;

        public ShopItem(String n, String desc, Double p) {
            this.name = n;
            this.desc = desc;
            this.price = p;
            add(new Image(new Texture(Gdx.files.internal(name + ".png"))));
            Table info = new Table();
            Label flavorText = new Label(desc, Assets.SKIN);
            flavorText.setWrap(true);
            info.add(flavorText).width(260);
            info.row();
            TextButton purButton = new TextButton("$" + price, Assets.SKIN);
            purButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(money - price > 0) {
                        money -= price;
                        setMoney(money);
                        Packet p = Packet.createPurchasePacket(LoveHunterX.getState().getUsername(), money, name);
                        LoveHunterX.getConnection().send(p);
                    }
                }
            });
            info.add(purButton);
            info.right();
            add(info).padLeft(30);
            center();
            pad(5);
        }
    }
}
