package com.lovehunterx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.lovehunterx.screens.ui.Sidebar;

import java.util.HashMap;

public class GameState {
    private String username;
    private int cSprite;

    private Stage world;
    private Sidebar invContainer;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void restockInventory(Actor a) {
        invContainer.addActor(a);
    }

    public void bindWorld(Stage stage) {
        this.world = stage;
    }

    public void bindInventoryContainer(Sidebar bar) {
        this.invContainer = bar;
    }

    public void setcSprite(int x){
        cSprite = x;
    }

    public int getcSprite(){
        return cSprite;
    }

}
