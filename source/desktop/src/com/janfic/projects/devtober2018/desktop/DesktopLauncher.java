package com.janfic.projects.devtober2018.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.janfic.projects.devtober2018.DevtoberGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.width = DevtoberGame.WIDTH;
                config.height = DevtoberGame.HEIGHT;
		new LwjglApplication(new DevtoberGame(), config);
	}
}
