package com.lovehunterx.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lovehunterx.Assets;

public abstract class LHXScreen implements Screen {

    abstract Stage getStage();

    public final void displayNotification(String message) {
        getStage().addActor(new Notification(message));
    }

    private class Notification extends Group {

        public Notification(String message) {
            TextButton btn = new TextButton(message, Assets.SKIN);
            btn.setX(LHXScreen.this.getStage().getWidth() / 2 - btn.getWidth() / 2);
            btn.setY(LHXScreen.this.getStage().getHeight() - btn.getHeight() - 10);

            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent e, float x, float y) {
                    Notification.this.remove();
                }
            });

            addActor(btn);
        }

    }

}
