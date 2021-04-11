package com.mygdx.pgame;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class CEnemy extends BaseActor{
public int healthE;
public boolean pause;
	public CEnemy(float x, float y, Stage s) {
		super(x, y, s);
		
	}
	public void act(float dt)
	{
		if(pause)
			return;
		
	}
	public boolean isPause() {
		return pause;
	}
	public void setPause(boolean pause) {
		this.pause = pause;
	}

}
