package com.lovehunterx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.entities.Furniture;
import com.lovehunterx.game.entities.Player;
import com.lovehunterx.networking.listeners.FurnitureRemoveListener;
import com.lovehunterx.networking.listeners.FurnitureUpdateListener;
import com.lovehunterx.networking.listeners.InventoryUpdateListener;
import com.lovehunterx.networking.listeners.PlayerJoinListener;
import com.lovehunterx.networking.listeners.PlayerLeaveListener;
import com.lovehunterx.networking.listeners.PlayerMoveListener;
import com.lovehunterx.screens.ui.Shop;
import com.lovehunterx.screens.ui.Sidebar;

import java.util.Iterator;

public class GameState {
    private Mode mode;
    private String username;
    private String room;

    private Stage world;
    private Sidebar invContainer;
    private TextButton shopButton;
    private boolean showShop;
    private Shop shopContainer;
    private int invAmount;

    private Group players = new Group();
    private Group furniture = new Group();


    public void registerServerListeners() {
        LoveHunterX.getConnection().registerListener("join", new PlayerJoinListener());
        LoveHunterX.getConnection().registerListener("move", new PlayerMoveListener());
        LoveHunterX.getConnection().registerListener("leave", new PlayerLeaveListener());
        LoveHunterX.getConnection().registerListener("update_inventory", new InventoryUpdateListener());
        LoveHunterX.getConnection().registerListener("update_furniture", new FurnitureUpdateListener());
        LoveHunterX.getConnection().registerListener("remove_furniture", new FurnitureRemoveListener());
    }

    public void init(Stage stage) {
        this.world = stage;
        this.world.addActor(players);
        this.world.addActor(furniture);

        this.mode = Mode.PLAY;

        Sidebar sidebar = new Sidebar(0, 65, 65, 350);
        shopContainer = new Shop(80, 57.5f, 480, 365);
        bindInventoryContainer(sidebar);
        this.shopButton = createShopButton();

        registerServerListeners();
    }

    public Stage getWorld() {
        return world;
    }

    public void bindInventoryContainer(Sidebar bar) {
        this.invContainer = bar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isInMode(Mode mode) {
        return this.mode == mode;
    }

    public void toggleMode(Mode mode) {
        this.mode = mode;
    }

    public void restockInventory(String type) {
        invAmount++;
        invContainer.addItem(type);
    }

    public void depleteInventory(Sidebar.SidebarItem item) {
        invAmount--;
        invContainer.removeItem(item);
    }

    public int getInventoryAmount() {
        return invAmount;
    }

    public void spawnEntity(Actor a) {
        this.world.addActor(a);
    }

    public <T extends Actor> T getEntity(String name) {
        return this.world.getRoot().findActor(name);
    }

    public void reset() {
        invContainer.clear();
        LoveHunterX.getConnection().clearListeners();
    }

    public boolean isInRoom(String room) {
        return this.room.equals(room);
    }

    public void joinRoom(String room) {
        invContainer.clear();

        // remove players/furniture from last room
        Iterator<Actor> actors = world.getRoot().getChildren().iterator();
        while (actors.hasNext()) {
            Actor actor = actors.next();
            if (actor instanceof Player || actor instanceof Furniture) {
                actors.remove();
            }
        }

        this.room = room;
        if(username.equals(room)) {
            world.addActor(invContainer);
            world.addActor(shopButton);
        } else {
            invContainer.remove();
            shopButton.remove();
        }
    }

    private TextButton createShopButton () {
        TextButton b = new TextButton("Open Shop", Assets.SKIN);
        b.setPosition(10, world.getHeight() - 50);
        b.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                toggleShop();
            }
        });

        return b;
    }

    private void toggleShop() {
        showShop = !showShop;
        String butText = showShop ? "Exit Shop" : "Open Shop";
        shopButton.setText(butText);

        Gdx.app.log("shop status",Boolean.toString(showShop));
        if(showShop) {
            world.addActor(shopContainer);
        } else {
            shopContainer.remove();
        }
    }

    public enum Mode {
        PLAY, CONFIG;
    }
}
