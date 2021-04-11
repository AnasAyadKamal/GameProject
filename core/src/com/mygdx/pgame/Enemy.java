package com.mygdx.pgame;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Enemy extends CEnemy{


	public Enemy(float x, float y, Stage s) {
		super(x, y, s);
		loadTexture("enemy.png");
		setBoundaryPolygon(8);
		healthE=20;
	
		pause=false;
		setSpeed(60);
		setMaxSpeed(100);
		setDeceleration(1);
		
		setMotionAngle(MathUtils.random(360));
	}
	public void act(float dt)
	{
		if(isPause())
			return;
		super.act(dt);
	
		if ( MathUtils.random(1,120) == 1 )
			setMotionAngle( MathUtils.random(0,360) );
		   if(getSpeed()>0)
		    	setRotation(getMotionAngle());
		applyPhysics(dt);
		boundToWorld();
		
	}
	


}
