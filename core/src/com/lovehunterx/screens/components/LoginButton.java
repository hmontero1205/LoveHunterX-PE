package com.lovehunterx.screens.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class LoginButton extends TextButton {

    public LoginButton(final TextField user, final TextField pass, float x, float y) {
        super("Login", Assets.SKIN);
        setPosition(x, y);

        addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                Packet p = Packet.createAuthPacket(user.getText(), pass.getText());
                if (!LoveHunterX.getConnection().send(p)) {
                    LoveHunterX.displayNotification("Connection to server failed >:(");
                }
            }

        });

        LoveHunterX.getConnection().registerListener("auth", new Listener() {
            @Override
            public void handle(Packet p) {
                String message = (Boolean.parseBoolean(p.getData("success"))) ? "Log in worked dude what's good" : "log in failed goofy";
                LoveHunterX.displayNotification(message);
            }
        });
    }

}
