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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lovehunterx.LoveHunterX;
import com.lovehunterx.networking.Packet;

/**
 * Created by omesh on 5/16/2017.
 */

public class LoginScreen implements Screen {
    private Stage stage;
    private Image back;
    private TextField user;
    private TextField pass;
    private TextButton logBut;
    private TextButton regBut;
    private Label messLabel;
    private Timer timer;

    @Override
    public void show() {
        //icon = new Image(new Texture("LoveHunterXIcon.png"));
        stage = new Stage(new FitViewport(640, 480));
        Gdx.input.setInputProcessor(stage);
        back = new Image(new Texture("LHX.png"));
        back.setPosition((stage.getWidth() / 2) - back.getWidth() / 2, ((stage.getHeight() / 2) - (back.getHeight() / 2) + 20));

        Skin mySkin = new Skin(Gdx.files.internal("neon-ui.json"));;
        user = new TextField("user pls", mySkin);
        pass = new TextField("pass pls", mySkin);
        logBut = new TextButton("Log in",mySkin);
        regBut = new TextButton("Register",mySkin);
        messLabel = new Label("", mySkin);
        messLabel.setPosition(100,100);
        user.setPosition(100,200);
        pass.setPosition(375,200);
        logBut.setPosition(100,150);
        regBut.setPosition(375,150);
        logBut.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y){
                //Gdx.app.log("Input","User:"+user.getText()+"; Pass:"+pass.getText());
                Packet p = Packet.createAuthPacket(user.getText(), pass.getText());
                //LoveHunterX.connection.getChannel().writeAndFlush("auth;"+user.getText()+";"+pass.getText());
                LoveHunterX.connection.getChannel().writeAndFlush(p.toJSON());
            }

        });
        regBut.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y){
                Packet p = Packet.createRegPacket(user.getText(), pass.getText());
                LoveHunterX.connection.getChannel().writeAndFlush(p.toJSON());

            }

        });
        timer = new Timer();
        timer.scheduleTask(new Timer.Task() {

            @Override
            public void run() {
                timer.scheduleTask(new Timer.Task() {
                    public void run() {
                        //Gdx.app.log("", String.valueOf(back.getY()));
                        back.setY(back.getY() + 1);
                        if(back.getY() > 350) {
                            this.cancel();
                            stage.addActor(user);
                            stage.addActor(pass);
                            stage.addActor(logBut);
                            stage.addActor(regBut);
                            stage.addActor(messLabel);
                        }
                    }
                }, 3, .01F);
            }
        }, 3);
        stage.addActor(back);
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

    public void showMessage(String m) {
        messLabel.setText(m);
    }

}
