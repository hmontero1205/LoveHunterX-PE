package com.lovehunterx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.lovehunterx.Assets;
import com.lovehunterx.screens.ui.FixedGroup;


public abstract class LHXScreen implements Screen {

    abstract Stage getStage();

    public final void displayNotification(String message) {
        FixedGroup fixed = (FixedGroup) getStage().getRoot().findActor("fixed");
        fixed.addActor(new Notification(message));
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
