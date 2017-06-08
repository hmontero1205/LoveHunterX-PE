package com.lovehunterx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.entities.Furniture;
import com.lovehunterx.game.entities.Player;
import com.lovehunterx.networking.listeners.FurnitureRemoveListener;
import com.lovehunterx.networking.listeners.FurnitureUpdateListener;
import com.lovehunterx.networking.listeners.InventoryUpdateListener;
import com.lovehunterx.networking.listeners.PlayerJoinListener;
import com.lovehunterx.networking.listeners.PlayerLeaveListener;
import com.lovehunterx.networking.listeners.PlayerMoveListener;
import com.lovehunterx.screens.ui.Sidebar;

public class GameState {
    private String username;
    private String room;

    private Stage world;
    private Sidebar invContainer;
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

        registerServerListeners();
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

    @Deprecated
    public void spawnPlayer(Player p) {
        this.players.addActor(p);
    }

    @Deprecated
    public void spawnFurniture(Furniture f) {
        this.furniture.addActor(f);
    }

    public Actor getEntity(String name) {
        return this.world.getRoot().findActor(name);
    }

    public Player getPlayer(String username) {
        return this.world.getRoot().findActor(username);
    }

    public Furniture getFurniture(String uid) {
        return this.world.getRoot().findActor(uid);
    }

}
