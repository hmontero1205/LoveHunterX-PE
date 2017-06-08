package com.lovehunterx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.screens.ui.Field;
import com.lovehunterx.screens.ui.FixedGroup;
import com.lovehunterx.screens.ui.LoginButton;
import com.lovehunterx.screens.ui.RegisterButton;

import java.util.ArrayList;

public class LoginScreen extends LHXScreen {
    private Stage stage;

    @Override
    public void show() {
        StretchViewport view = new StretchViewport(640, 480);
        stage = new Stage(view);
        Gdx.input.setInputProcessor(stage);

        Image back = new Image(Assets.LHX_LOGO);
        back.setPosition(centerX(back), centerY(back) + 20);
        stage.addActor(back);

        final Field user = new Field("username", Assets.SKIN);
        user.setScale(2);
        final Field pass = new Field("password", Assets.SKIN);
        pass.setPasswordCharacter('*');
        pass.setPasswordMode(true);

        LoginButton logBut = new LoginButton(user, pass);
        RegisterButton regBut = new RegisterButton(user, pass);

        Table fields = new Table(Assets.SKIN);
        fields.setPosition(centerX(fields), centerY(fields) + 40);
        fields.add(user).height(40).width(200);
        fields.row();
        fields.add(pass).height(40).width(200);

        Table buttons = new Table();
        buttons.setPosition(centerX(fields), centerY(fields) - 40);
        buttons.add(logBut).height(50).width(100).fillX();
        buttons.add(regBut).height(50).width(100).fillX();

        final Group ui = new Group();
        ui.addActor(fields);
        ui.addActor(buttons);

        // animation
        MoveToAction moveTo = Actions.moveTo(back.getX(), 325, 1, Interpolation.pow2Out);
        SequenceAction seq = Actions.sequence(Actions.delay(2), moveTo, Actions.run(new Runnable() {
            @Override
            public void run() {
                stage.addActor(ui);
            }
        }));

        back.addAction(seq);

        FixedGroup fixed = new FixedGroup();
        stage.addActor(fixed);

    }

    private float centerX(Actor a) {
        return (stage.getWidth() / 2) - (a.getWidth() / 2);
    }

    private float centerY(Actor a) {
        return (stage.getHeight() / 2) - (a.getHeight() / 2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, .914f, .91f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public Stage getStage() {
        return stage;
    }
}
