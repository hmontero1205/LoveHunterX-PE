package com.lovehunterx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.lovehunterx.networking.Connection;
import com.lovehunterx.screens.CharacterScreen;
import com.lovehunterx.screens.LHXScreen;
import com.lovehunterx.screens.LoginScreen;
import com.lovehunterx.screens.RoomScreen;

public class LoveHunterX extends Game {
    public static final LoginScreen LOGIN_SCREEN = new LoginScreen();
    public static final CharacterScreen CHAR_SCREEN = new CharacterScreen();
    public static final RoomScreen ROOM_SCREEN = new RoomScreen();
    private static LoveHunterX lhx;
    private static Connection connection;

    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        LoveHunterX.username = username;
    }

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

        boolean connected = true;
        try {
            connection = new Connection();
            connection.init();
        } catch (Exception e) {
            Gdx.app.log("Error:", "Server connection failed >:(");
            connected = false;
            e.printStackTrace();
        }

        changeScreen(LOGIN_SCREEN);
        if (!connected) {
            displayNotification("Server connection failed >:(");
        }
    }

    @Override
    public void dispose() {
        connection.end();
    }
}
