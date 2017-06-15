package com.lovehunterx.game.entities;

import com.lovehunterx.LoveHunterX;
import com.lovehunterx.screens.CharacterScreen;

public class Closet extends Furniture {

    public Closet(float x, float y, int uid) {
        super("Closet", x, y, uid);
    }

    @Override
    public void clicked() {
        CharacterScreen.resetCSprite();
        LoveHunterX.changeScreen(LoveHunterX.CHAR_SCREEN);
    }
}
