package com.lovehunterx.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Packet;


public abstract class LHXScreen implements Screen {

    public abstract Stage getStage();

    public final void displayNotification(String message) {
        Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.SKIN.get(Label.LabelStyle.class));
        labelStyle.font = Assets.SKIN.getFont("roboto");

        Label label = new Label(message, labelStyle);
        Notification notif = new Notification(label);
        getStage().addActor(notif);
    }

    public final void displayInvitation(final String player, String game) {
        final HorizontalGroup group = new HorizontalGroup();
        group.space(10);

        Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.SKIN.get(Label.LabelStyle.class));
        labelStyle.font = Assets.SKIN.getFont("roboto");

        game = game.equals("ttt") ? "Tic Tac Toe" : "Whack a Mole";

        Label msg = new Label(player + " challenged you to " + game, labelStyle);
        group.addActor(msg);
        System.out.println(msg.getText());

        Image check = new Image(new Texture("check.png"));
        check.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Packet packet = new Packet("decision");
                packet.addData("player", player);
                packet.addData("choice", "true");
                LoveHunterX.getConnection().send(packet);

                group.getParent().remove();
            }
        });
        group.addActor(check);

        Image close = new Image(new Texture("x.png"));
        close.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Packet packet = new Packet("decision");
                packet.addData("player", player);
                packet.addData("choice", "false");
                LoveHunterX.getConnection().send(packet);

                group.getParent().remove();
            }
        });
        group.addActor(close);

        group.padLeft(5);
        group.padRight(5);

        Notification notif = new Notification(group);
        getStage().addActor(notif);
    }

    private class Notification extends Group {

        public Notification(Actor a) {
            final Button btn = new Button(a, Assets.SKIN);
            btn.setX(LHXScreen.this.getStage().getWidth() / 2 - btn.getWidth() / 2);
            btn.setY(LHXScreen.this.getStage().getHeight());

            addActor(btn);

            MoveByAction act = Actions.moveBy(0, -btn.getHeight() - 5, .25F, Interpolation.smoother);
            btn.addAction(act);

            MoveByAction disappear = Actions.moveBy(0, btn.getHeight(), .25F, Interpolation.smoother);
            btn.addAction(Actions.sequence(Actions.delay(3), disappear, Actions.removeActor()));
        }

    }
}
