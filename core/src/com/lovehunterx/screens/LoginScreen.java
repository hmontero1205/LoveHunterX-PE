package com.lovehunterx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by omesh on 5/16/2017.
 */

public class LoginScreen implements Screen {
    private Stage stage;
    private Image icon;
    private TextField user;
    private TextField pass;
    private TextButton logBut;
    private TextButton regBut;

    @Override
    public void show() {
        icon = new Image(new Texture("LoveHunterXIcon.png"));
        icon.setX(100);
        icon.setY(100);

        Skin mySkin = new Skin(Gdx.files.internal("neon-ui.json"));
        user = new TextField("user pls", mySkin);
        pass = new TextField("pass pls", mySkin);
        logBut = new TextButton("Log in",mySkin);
        regBut = new TextButton("Register",mySkin);
        user.setPosition(50,450);
        pass.setPosition(200,450);
        logBut.setPosition(50,400);
        regBut.setPosition(200,400);
        logBut.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y){
                Gdx.app.log("Input","User:"+user.getText()+"; Pass:"+pass.getText());
            }

        });

        stage = new Stage(new FitViewport(640, 480));
        Gdx.input.setInputProcessor(stage);
        stage.addActor(icon);
        stage.addActor(user);
        stage.addActor(pass);
        stage.addActor(logBut);
        stage.addActor(regBut);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

//        icon.setX(icon.getX() + 1);
//        if (icon.getX() > Gdx.graphics.getWidth() / 2) {
//            icon.remove();
//        }
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
}
