package com.mygdx.pgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.pgame.levels.Level1;
import com.mygdx.pgame.levels.Level3;
import com.mygdx.pgame.levels.Level5;

public class MyGdxGame extends BaseGame {
	public void create()
	{
		super.create();
		setActiveScreen(new Level1());
	}
}
