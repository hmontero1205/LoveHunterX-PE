package com.lovehunterx.screens.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Listener;
import com.lovehunterx.networking.Packet;

public class RegisterButton extends TextButton {


    public RegisterButton(final Field user, final Field pass) {
        super("Register", Assets.SKIN);

        addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if(user.getText().equals("username") || pass.getText().equals("password")){
                    LoveHunterX.getCurrentScreen().displayNotification("make a legit account please");
                    return;
                }
                Packet p = Packet.createRegPacket(user.getText(), pass.getText());
                if (!LoveHunterX.getConnection().send(p)) {
                    LoveHunterX.getCurrentScreen().displayNotification("Connection to server failed >:(");
                }
            }
        });

        LoveHunterX.getConnection().registerListener("reg", new Listener() {
            @Override
            public void handle(Packet p) {
                String message = (Boolean.parseBoolean(p.getData("success"))) ? "Registration worked dude log in now" : "you goofy this account already exists";
                LoveHunterX.getCurrentScreen().displayNotification(message);
            }
        });
    }

}
