package com.lovehunterx.screens.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class LoginButton extends TextButton {

    public LoginButton(final TextField user, final TextField pass) {
        super("Login", Assets.SKIN);

        addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Packet p = Packet.createAuthPacket(user.getText(), pass.getText());
                LoveHunterX.getState().setUsername(user.getText());
                if (!LoveHunterX.getConnection().send(p)) {
                    LoveHunterX.getCurrentScreen().displayNotification("Connection to server failed >:(");
                }
            }

        });

        LoveHunterX.getConnection().registerListener("auth", new Listener() {
            @Override
            public void handle(Packet p) {
                System.out.println(p.toJSON());
                if (Boolean.parseBoolean(p.getData("success"))) {
                    if (p.getData("require_sprite") != null) {
                        LoveHunterX.changeScreen(LoveHunterX.CHAR_SCREEN);
                    } else {
                        LoveHunterX.changeScreen(LoveHunterX.ROOM_SCREEN);
                    }

                    LoveHunterX.getCurrentScreen().displayNotification("Log in worked dude what's good");
                } else {
                    LoveHunterX.getCurrentScreen().displayNotification("Log in failed goofy");
                }
            }
        });
    }

}
