package com.lovehunterx.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lovehunterx.LoveHunterX;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new LoveHunterX(), config);
		config.addIcon("LHXIconM.png", Files.FileType.Internal);
		config.addIcon("LHXIconW.png", Files.FileType.Internal);
	}
}
