package com.lovehunterx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.lovehunterx.game.GameState;
import com.lovehunterx.networking.Connection;
import com.lovehunterx.screens.CharacterScreen;
import com.lovehunterx.screens.LHXScreen;
import com.lovehunterx.screens.LoginScreen;
import com.lovehunterx.screens.RoomScreen;

public class LoveHunterX extends Game {
    public static final LoginScreen LOGIN_SCREEN = new LoginScreen();
    public static final RoomScreen ROOM_SCREEN = new RoomScreen();
    public static final CharacterScreen CHAR_SCREEN = new CharacterScreen();
    
    private static LoveHunterX lhx;
    private static GameState state;
    private static Connection connection;

    public static GameState getState() {
        return state;
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

        state = new GameState();

        changeScreen(LOGIN_SCREEN);
        //changeScreen(CHAR_SCREEN);

        if (!connected) {
            displayNotification("Server connection failed >:(");
        }
    }

    @Override
    public void dispose() {
        connection.end();
    }
}
