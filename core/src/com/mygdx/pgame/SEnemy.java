package com.mygdx.pgame;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class SEnemy extends CEnemy{

	public SEnemy(float x, float y, Stage s) {
		super(x, y, s);
		loadTexture("Senemy.png");
		healthE=20;

		
	}

}
