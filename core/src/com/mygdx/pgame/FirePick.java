package com.mygdx.pgame;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class FirePick extends BaseActor{

	public FirePick(float x, float y, Stage s) {
		super(x, y, s);
		loadTexture("firePick.png");

		Action pulse=Actions.sequence(Actions.scaleTo(1.0f, 1.0f, 1), Actions.scaleTo(0.85f, 0.85f, 1));
		Action pulse2=Actions.sequence(
				Actions.scaleTo(1.5f, 1.5f,0.1f),
				Actions.scaleTo(1.0f,1.0f, 0.1f));
		addAction(pulse2);
		addAction(Actions.after(Actions.forever(pulse)));
	}
public void act(float dt)
{
	boundToWorld();
}
}
