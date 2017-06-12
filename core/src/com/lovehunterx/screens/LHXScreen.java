package com.lovehunterx.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;


public abstract class LHXScreen implements Screen {

    abstract Stage getStage();

    public final void displayNotification(String message) {
        Notification notif = new Notification(message);
        if (this instanceof RoomScreen) {
            LoveHunterX.getState().addUI(notif);
        } else {
            getStage().addActor(notif);
        }
    }

    private class Notification extends Group {

        public Notification(String message) {
            final TextButton btn = new TextButton(message, Assets.SKIN);
            btn.setX(LHXScreen.this.getStage().getWidth() / 2 - btn.getWidth() / 2);
            btn.setY(LHXScreen.this.getStage().getHeight());

            addActor(btn);

            MoveByAction act = Actions.moveBy(0, -btn.getHeight() - 5, .25F, Interpolation.smoother);
            btn.addAction(act);

            MoveByAction dissapear = Actions.moveBy(0, btn.getHeight(), .25F, Interpolation.smoother);
            btn.addAction(Actions.sequence(Actions.delay(3), dissapear, Actions.removeActor()));

        }

    }
}
