package com.lovehunterx.screens.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.game.GameState;
import com.lovehunterx.networking.Packet;

public class Invite extends Table {
    private ButtonGroup<Button> choices;

    public Invite(final String player) {
        setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("playermenu.png")))));
        setSize(150, 200);

        Button ttt = new Button(new Image(new Texture(Gdx.files.internal("tttbut.png"))), Assets.SKIN);
        ttt.setName("ttt");
        add(ttt);
        row();

        Button wam = new Button(new Image(new Texture(Gdx.files.internal("wambut.png"))), Assets.SKIN);
        wam.setName("wam");
        add(wam);
        row();

        choices = new ButtonGroup<Button>(ttt, wam);

        HorizontalGroup options = new HorizontalGroup();

        TextButton submit = new TextButton("Invite " + player, Assets.SKIN);
        submit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (choices.getChecked() == null) {
                    LoveHunterX.getCurrentScreen().displayNotification("Please select a game in order to invite!");
                    return;
                }

                Packet invitation = Packet.createInvitationPacket(player, choices.getChecked().getName());
                LoveHunterX.getConnection().send(invitation);

                LoveHunterX.getState().toggleMode(GameState.Mode.PLAY);

                remove();
            }
        });

        options.addActor(submit);
        add(options);
    }

}
