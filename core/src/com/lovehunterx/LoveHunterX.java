package com.lovehunterx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.lovehunterx.networking.Connection;
import com.lovehunterx.screens.LoginScreen;
import io.netty.buffer.*;

public class LoveHunterX extends Game {
	private LoginScreen ls;
	public static Connection connection;
	
	@Override
	public void create () {
		ls = new LoginScreen();
		try {
			connection = new Connection();
		} catch (Exception e) {
			Gdx.app.log("Error:", "Server connection failed >:(");
			e.printStackTrace();
		}
		setScreen(ls);
	}

	@Override
	public void dispose () {
	}
}
