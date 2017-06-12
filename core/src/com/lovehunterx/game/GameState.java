package com.lovehunterx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.SnapshotArray;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.entities.Furniture;
import com.lovehunterx.game.entities.Player;
import com.lovehunterx.networking.Packet;
import com.lovehunterx.networking.listeners.ChatListener;
import com.lovehunterx.networking.listeners.FurnitureRemoveListener;
import com.lovehunterx.networking.listeners.FurnitureUpdateListener;
import com.lovehunterx.networking.listeners.GetMoneyListener;
import com.lovehunterx.networking.listeners.InventoryUpdateListener;
import com.lovehunterx.networking.listeners.PlayerJoinListener;
import com.lovehunterx.networking.listeners.PlayerLeaveListener;
import com.lovehunterx.networking.listeners.PlayerMoveListener;
import com.lovehunterx.networking.listeners.PurchaseListener;
import com.lovehunterx.networking.listeners.StatusListener;
import com.lovehunterx.screens.ui.Shop;
import com.lovehunterx.screens.ui.Sidebar;

import java.util.Iterator;

public class GameState {
    private Mode mode;
    private String username;
    private String room;

    private Stage ui;
    private Stage world;
    private Sidebar invContainer;
    private int invAmount;

    private Button shopButton;
    private Shop shopContainer;

    private Button chatButton;
    private boolean chatting;

    private Group players;
    private Group furniture;

    public void registerServerListeners() {
        LoveHunterX.getConnection().registerListener("chat", new ChatListener());
        LoveHunterX.getConnection().registerListener("join", new PlayerJoinListener());
        LoveHunterX.getConnection().registerListener("move", new PlayerMoveListener());
        LoveHunterX.getConnection().registerListener("leave", new PlayerLeaveListener());
        LoveHunterX.getConnection().registerListener("update_inventory", new InventoryUpdateListener());
        LoveHunterX.getConnection().registerListener("update_furniture", new FurnitureUpdateListener());
        LoveHunterX.getConnection().registerListener("remove_furniture", new FurnitureRemoveListener());
        LoveHunterX.getConnection().registerListener("get_money", new GetMoneyListener());
        LoveHunterX.getConnection().registerListener("purchase", new PurchaseListener());
        LoveHunterX.getConnection().registerListener("status", new StatusListener());
    }

    public void init(Stage stage, Stage ui) {
        this.ui = ui;
        this.world = stage;

        this.mode = Mode.PLAY;

        Sidebar sidebar = new Sidebar(0, 65, 65, 350);
        shopContainer = new Shop(80, 57.5f, 480, 365);
        bindInventoryContainer(sidebar);

        this.shopButton = createShopButton();
        this.chatButton = createChatButton();

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

<<<<<<< HEAD
    public void spawnPlayer(Player p) {
        this.players.addActor(p);
    }

=======
>>>>>>> 5de2812885b7110514c02b0138efa4c45d7de653
    public void spawnFurniture(Furniture f) {
        this.furniture.addActor(f);
    }

<<<<<<< HEAD
=======
    public SnapshotArray<Actor> getFurniture() {
        return furniture.getChildren();
    }

    public void spawnPlayer(Player p) {
        this.players.addActor(p);
    }

>>>>>>> 5de2812885b7110514c02b0138efa4c45d7de653
    public <T extends Actor> T getEntity(String name) {
        return this.world.getRoot().findActor(name);
    }

    public void reset() {
        this.username = null;
        this.room = null;

        invContainer.clear();
        LoveHunterX.getConnection().clearListeners();
    }

    public boolean isInRoom(String room) {
        return this.room.equals(room);
    }

    public void joinRoom(String room) {
        invContainer.clear();
        if (invContainer.getIsOpen()) {
            invContainer.close();
        }

        // remove players/furniture from last room
        if (furniture != null && players != null) {
            furniture.clear();
            players.clear();
        }

        furniture = new Group();
        this.world.addActor(furniture);

        players = new Group();
        this.world.addActor(players);

        this.room = room;
        if (username.equals(room)) {
            ui.addActor(invContainer);
            ui.addActor(shopButton);
        } else {
            invContainer.remove();
            shopButton.remove();
        }

        ui.addActor(chatButton);
    }

    public void addUI(Actor a) {
        ui.addActor(a);
    }

    private Button createShopButton() {
        Button b = new Button(new Image(new Texture(Gdx.files.internal("cart.png"))), Assets.SKIN);
        b.setPosition(-8, world.getHeight() - 60);
        b.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y) {
                toggleShop();
            }
        });

        return b;
    }

    private Button createChatButton() {
        Button b = new Button(new Image(new Texture(Gdx.files.internal("chatbut.png"))), Assets.SKIN);
        b.setPosition(-20 + shopButton.getWidth(), world.getHeight() - 60);

        b.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                chatting = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (isChatting()) {
                            Packet packet = new Packet("alive");
                            LoveHunterX.getConnection().send(packet);

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                }).start();

                Gdx.input.getTextInput(new Input.TextInputListener() {
                    @Override
                    public void input(String text) {
                        Packet chatPacket = Packet.createChatPacket(text);
                        LoveHunterX.getConnection().send(chatPacket);

                        chatting = false;
                    }

                    @Override
                    public void canceled() {
                        chatting = false;
                    }

                }, "Chat", "", "");
            }
        });

        return b;
    }

    private void toggleShop() {
        if (isInMode(Mode.SHOP)) {
            toggleMode(Mode.PLAY);
            shopContainer.remove();
           // shopButton.setText("Open Shop");
        } else if (isInMode(Mode.PLAY)) {
            toggleMode(Mode.SHOP);
            ui.addActor(shopContainer);
            //shopButton.setText("Exit Shop");

            Packet getMoneyPacket = Packet.createGetMoneyPacket(getUsername());
            LoveHunterX.getConnection().send(getMoneyPacket);
        }
    }

    public boolean isChatting() {
        return chatting;
    }

    public void updateMoney(double m) {
        shopContainer.setMoney(m);
    }

    public enum Mode {
        PLAY, CONFIG, SHOP;
    }

    public Player getPlayer() {
        return getEntity(getUsername());
    }
}