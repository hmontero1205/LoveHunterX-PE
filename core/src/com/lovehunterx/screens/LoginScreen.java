package com.lovehunterx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lovehunterx.Assets;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.screens.ui.LoginButton;
import com.lovehunterx.screens.ui.RegisterButton;

public class LoginScreen extends LHXScreen {
    private Stage stage;

    @Override
    public void show() {
        stage = new Stage(new FitViewport(640, 480));
        Gdx.input.setInputProcessor(stage);

        Image back = new Image(Assets.LHX_LOGO);
        back.setPosition(centerX(back), centerY(back) + 20);
        stage.addActor(back);

        final TextField user = new TextField("username", Assets.SKIN);
        final TextField pass = new TextField("password", Assets.SKIN);

        LoginButton logBut = new LoginButton(user, pass, centerX(user) - 3, 150);
        RegisterButton regBut = new RegisterButton(user, pass, centerX(user) + (logBut.getWidth() / 2) + 30, 150);

        Table fields = new Table();
        fields.setPosition(centerX(fields),centerY(fields)+40);
        fields.add(user).height(40).width(200);
        fields.row();
        fields.add(pass).height(40).width(200);

        Table buttons = new Table();
        buttons.setPosition(centerX(fields),centerY(fields)-40);
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
        LoveHunterX.getConnection().clearListeners();
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
