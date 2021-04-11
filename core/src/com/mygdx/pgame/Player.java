package com.mygdx.pgame;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.pgame.levels.Cs;

public class Player extends BaseActor{
public int health;
public Guns currentGun;
float time;
Sound gunShot;
Sound shotGunShot;
Sound fireShot;
//for level 3 and 4 and 5
public static ArrayList<Cs> inventory=new ArrayList<Cs>();
//
	public Player(float x, float y, Stage s) {
		super(x, y, s);
		loadTexture("player.png");
		this.setZIndex(10);
//		setSize(40,40);
		setBoundaryPolygon(8);
		health=5;
		time=0;
		gunShot=Gdx.audio.newSound(Gdx.files.internal("gunSound.wav"));
		shotGunShot=Gdx.audio.newSound(Gdx.files.internal("shotgunSound.wav"));
        fireShot=Gdx.audio.newSound(Gdx.files.internal("fireSound.wav"));
		setAcceleration(400);
		setMaxSpeed(200);
		setDeceleration(400);
		
	}
	
	public void act(float dt)
	{
		super.act(dt);
		time+=dt;
	    if (Gdx.input.isKeyPressed(Keys.LEFT))//&&!Gdx.input.isKeyPressed(Keys.UP)&&!Gdx.input.isKeyPressed(Keys.DOWN)
	        accelerateAtAngle(180);
//	    if (Gdx.input.isKeyPressed(Keys.LEFT)&&Gdx.input.isKeyPressed(Keys.UP))
//	        accelerateAtAngle(135);
//	    if (Gdx.input.isKeyPressed(Keys.LEFT)&&Gdx.input.isKeyPressed(Keys.DOWN))
//	        accelerateAtAngle(225);
	    if (Gdx.input.isKeyPressed(Keys.RIGHT))//&&!Gdx.input.isKeyPressed(Keys.UP)&&!Gdx.input.isKeyPressed(Keys.DOWN)
	        accelerateAtAngle(0);
//	    if (Gdx.input.isKeyPressed(Keys.RIGHT)&&Gdx.input.isKeyPressed(Keys.UP))
//	        accelerateAtAngle(45);
//	    if (Gdx.input.isKeyPressed(Keys.RIGHT)&&Gdx.input.isKeyPressed(Keys.DOWN))
//	        accelerateAtAngle(315);
	    if (Gdx.input.isKeyPressed(Keys.UP))//&&!Gdx.input.isKeyPressed(Keys.RIGHT)&&!Gdx.input.isKeyPressed(Keys.LEFT)
	        accelerateAtAngle(90);
	    if (Gdx.input.isKeyPressed(Keys.DOWN))//&&!Gdx.input.isKeyPressed(Keys.RIGHT)&&!Gdx.input.isKeyPressed(Keys.LEFT)
	        accelerateAtAngle(270);
	    
	    if(getSpeed()>0)
	    	setRotation(getMotionAngle());
		applyPhysics(dt);
		alignCamera();
		boundToWorld();
	}
	
	public void shoot()
	{
		if(this.getStage()==null)
			return;
		if(currentGun.equals(Guns.GUN))
		if(BaseActor.count(this.getStage(), "com.mygdx.pgame.Bullet")<=3)
		{
			Bullet bullet=new Bullet(0,0,this.getStage());
			bullet.setZIndex(7);
			bullet.centerAtActor(this);
			bullet.setRotation(this.getRotation());
			bullet.setMotionAngle(this.getRotation());
			gunShot.play();
			
		}
		if(currentGun.equals(Guns.Fire))
			if(time>=0.5)
			{
				FireBullet bullet=new FireBullet(0,0,this.getStage());
				bullet.setZIndex(7);
				bullet.centerAtActor(this);
				bullet.setRotation(this.getRotation());
				bullet.setMotionAngle(this.getRotation());
				fireShot.play();
				time=0;
			}
		if(currentGun.equals(Guns.Shotgon))
			if(time>=0.5)
			{
				Shotgun bullet=new Shotgun(0,0,this.getStage());
				Shotgun bullet2=new Shotgun(0,0,this.getStage());
				Shotgun bullet3=new Shotgun(0,0,this.getStage());
				bullet.setZIndex(7);
				bullet2.setZIndex(7);
				bullet3.setZIndex(7);
				bullet.centerAtActor(this);
				bullet2.centerAtActor(this);
				bullet3.centerAtActor(this);
				bullet.setRotation(this.getRotation());
				bullet2.setRotation(this.getRotation()-10);
				bullet3.setRotation(this.getRotation()+10);
				bullet.setMotionAngle(this.getRotation());
				bullet2.setMotionAngle(this.getRotation()-10);
				bullet3.setMotionAngle(this.getRotation()+10);
				shotGunShot.play();
				time=0;
			}
	}

	public void setGun(Guns gun)
	{
		currentGun=gun;
	}
	public Guns getGun()
	{
		return currentGun;
	}
}
