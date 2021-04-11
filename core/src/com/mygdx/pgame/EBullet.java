package com.mygdx.pgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class EBullet extends BaseActor{

	public EBullet(float x, float y, Stage s) {
		super(x, y, s);
		loadTexture("bullet.png");
		setColor(Color.RED);
		addAction(Actions.delay(1));
		addAction(Actions.after(Actions.fadeOut(1.05f)));
		addAction(Actions.after(Actions.removeActor()));
		setSpeed(400);
		setMaxSpeed(400);
		setDeceleration(0);
	}

}
