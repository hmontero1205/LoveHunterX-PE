package com.lovehunterx.screens.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

/**
 * Created by Hans on 5/27/2017.
 */

public class Field extends TextField {
    boolean firstClicked;
    String defaultText;

    public Field(String text, Skin skin) {
        super(text, skin);
        defaultText = text;

        addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                clearText();
                if(!firstClicked)
                    firstClicked = true;
            }
        });

        addListener(new FocusListener() {
            public void keyboardFocusChanged(FocusListener.FocusEvent event, Actor actor, boolean focused) {
                checkText();
            }
        });
    }

    private void clearText() {
        if(this.getText().equals(defaultText) || !firstClicked)
            this.setText("");
    }

    private void checkText() {
        if(this.getText().equals("")) {
            this.setText(defaultText);
        }
    }


}
