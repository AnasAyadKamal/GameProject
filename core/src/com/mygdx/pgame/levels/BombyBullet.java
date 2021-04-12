package com.mygdx.pgame.levels;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.pgame.BaseActor;

public class BombyBullet extends BaseActor{

	public BombyBullet(float x, float y, Stage s) {
		super(x, y, s);
		loadTexture("bulletPfire.png");
	
		addAction(Actions.delay(1));
		addAction(Actions.after(Actions.fadeOut(0.05f)));
		addAction(Actions.after(Actions.removeActor()));
		setSpeed(800);
		setMaxSpeed(800);
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
