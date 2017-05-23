package com.lovehunterx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.lovehunterx.networking.Connection;
import com.lovehunterx.screens.LHXScreen;
import com.lovehunterx.screens.LoginScreen;

public class LoveHunterX extends Game {
    public static final LoginScreen LOGIN_SCREEN = new LoginScreen();
    private static LoveHunterX lhx;
    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static void changeScreen(LHXScreen screen) {
        lhx.setScreen(screen);
    }

    public static void displayNotification(String message) {
        LHXScreen screen = (LHXScreen) lhx.getScreen();
        screen.displayNotification(message);
    }

    @Override
    public void create() {
        lhx = this;

        try {
            connection = new Connection();
            connection.init();
        } catch (Exception e) {
            Gdx.app.log("Error:", "Server connection failed >:(");
            e.printStackTrace();
        }

        changeScreen(LOGIN_SCREEN);
    }

    @Override
    public void dispose() {
        connection.end();
    }
}
