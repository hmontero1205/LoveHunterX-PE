package com.lovehunterx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.lovehunterx.screens.LoginScreen;

public class LoveHunterX extends Game {
	private LoginScreen ls;
	
	@Override
	public void create () {
		ls = new LoginScreen();
		Gdx.app.log("Test","Hey");
		setScreen(ls);
	}

	@Override
	public void dispose () {
	}
}
