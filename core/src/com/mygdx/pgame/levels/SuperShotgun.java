package com.mygdx.pgame.levels;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.pgame.BaseActor;

public class SuperShotgun extends BaseActor{

	public SuperShotgun(float x, float y, Stage s) {
		super(x, y, s);
		loadTexture("shotgunPgun.png");
		
		addAction(Actions.delay(1));
		addAction(Actions.after(Actions.fadeOut(0.05f)));
		addAction(Actions.after(Actions.removeActor()));
		setSpeed(1200);
		setMaxSpeed(1200);
		setDeceleration(0);
	}
	public void act(float dt)
	{
		super.act(dt);
		applyPhysics(dt);
		if(getX()<=0)
			remove();
		if(getX()>=BaseActor.getWorldBounds().width-10)
			remove();
		if(getY()<=0)
			remove();
		if(getY()>=BaseActor.getWorldBounds().height-10)
			remove();
		boundToWorld();
		
	}
}
