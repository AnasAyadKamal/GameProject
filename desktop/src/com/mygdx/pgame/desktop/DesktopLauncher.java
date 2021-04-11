package com.mygdx.pgame.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.pgame.BaseGame;
import com.mygdx.pgame.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Game g=new MyGdxGame();
		LwjglApplication launcher = new LwjglApplication(g,"Shootim out",800,600);
	}
}
