package com.mygdx.pgame.levels;

import java.util.ArrayList;

import javax.swing.plaf.TableUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.mygdx.pgame.BaseActor;
import com.mygdx.pgame.BaseGame;
import com.mygdx.pgame.BaseScreen;
import com.mygdx.pgame.BulletPick;
import com.mygdx.pgame.CEnemy;
import com.mygdx.pgame.EBullet;
import com.mygdx.pgame.Enemy;
import com.mygdx.pgame.FirePick;
import com.mygdx.pgame.FirePool;
import com.mygdx.pgame.Guns;
import com.mygdx.pgame.Player;
import com.mygdx.pgame.Rock;
import com.mygdx.pgame.SEnemy;
import com.mygdx.pgame.ShotgunPick;
import com.mygdx.pgame.TextDamage;
import com.mygdx.pgame.TilemapActor;

import shaders.ShadowShader;



public class Level4 extends BaseScreen{
	
Player player;
BaseActor background;
Enemy enemy;
BaseActor health;
BaseActor ehealth;
Guns guns;
Guns fire;
Guns shotgun;
float time;
float time2;
float time3;
float time4;
float time5;
float time6;
boolean StopGive;
int random;
int old_ID;
Sound enemyShot;
Label Continue;
BaseActor GameOver;
BaseActor shadow;
boolean collect;
boolean win;
//special level 3 stuff 





//end of the new stuff


    
   
	@Override
	public void initialize() 
	{
		
		win=false;
	 	background=new BaseActor(0,0,mainStage);
			background.loadTexture("bg.png");
			background.setSize(1200, 1000);
			BaseActor.setWorldBounds(background);
		
			
		
			TilemapActor tma = new TilemapActor("s4.tmx", mainStage);
		    
	    	for (MapObject obj : tma.getTileList("Enemy") )
	    	{
	    	MapProperties props = obj.getProperties();
	    	new Enemy( (float)props.get("x"), (float)props.get("y"), mainStage );
	    	}
	    	for (MapObject obj : tma.getTileList("SEnemy") )
	    	{
	    	MapProperties props = obj.getProperties();
	    	new SEnemy( (float)props.get("x"), (float)props.get("y"), mainStage );
	    	}
	    	for (MapObject obj : tma.getTileList("Rock") )
	    	{
	    	MapProperties props = obj.getProperties();
	    	new Rock( (float)props.get("x"), (float)props.get("y"), mainStage );
	    	}
		
	    	player=new Player(500,0,mainStage);
	    	 
//		for(int i=10;i>0;i--)
//		{
//			int random1=MathUtils.random(200,600);
//			int random2=MathUtils.random(100,500);
//		
//			if(old_ID<=0)
//			{
//				Enemy g=new Enemy(random1,random2, mainStage);
//				g.setZIndex(7);
//				g.setID(0);
//				old_ID++;
//				System.out.println(g.ID);
//			}
//			else
//			{
//				Enemy g=new Enemy(random1,random2, mainStage);
//				g.setZIndex(7);
//				g.setID(0);
//				old_ID++;
//				g.setID(old_ID);
//				System.out.println(g.getID());
//			}
//		
//		}
		
		
		health=new BaseActor(0,0,uiStage);
		health.loadTexture("health.png");
		health.setWidth(player.health*100);
		shadow=new BaseActor(0,0,mainStage);
		shadow.loadTexture("shadow2.png");
		shadow.setZIndex(2);
		Label playerHealth=new Label("Health",BaseGame.labelStyle);
		
		Continue=new Label("Press C To Continue",BaseGame.labelStyle);
		GameOver=new BaseActor(300,300,uiStage);
		Continue.setVisible(false);
		Continue.setColor(Color.BLUE);
		GameOver.setOpacity(0);;
		GameOver.loadTexture("Gameover.png");
		GameOver.setZIndex(3);
		uiTable.pad(5);
	
		uiTable.add().expandY();
		uiTable.add().expandX();
		uiTable.add(playerHealth).align(Align.bottomLeft);
		
		uiTable.add().bottom().height(5).width(player.health*100);
		uiTable.add(Continue).align(Align.topLeft);
		
		uiTable.row();
		health.setX(125);
		health.setY(14);
	uiStage.addActor(health);



		//for the future
//		ehealth=new BaseActor(0,0,uiStage);
//		ehealth.loadTexture("Ehealth.png");
//
//		ehealth.setVisible(false);
		        guns=Guns.GUN;
		        fire=Guns.Fire;
		        shotgun=Guns.Shotgon;
		        player.setGun(guns);
		        StopGive=true;
		        dropped=true;
		        
		        
		        enemyShot=Gdx.audio.newSound(Gdx.files.internal("enemyShotSound.wav"));
		        
		        collect=true;
		    	
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		System.out.println("player is holding"+player.inventory.toString());
		if(time<=6)
		time+=dt;
		if(time2<=6)
		time2+=dt;
		if(time3<=6)
		time3+=dt;
		if(time4<=6)
		time4+=dt;
		if(time5<=6)
		time5+=dt;
		if(time6<=6)
			time6+=dt;
		shadow.centerAtActor(player);
		if(player.health<=0)
		{
			for(BaseActor f:BaseActor.getList(mainStage, "com.mygdx.pgame.Enemy"))
			{
				((Enemy)f).pause=true;
			}
			player.remove();
			health.setVisible(false);
			GameOver.addAction(Actions.fadeIn(1));
			return;
		}
		for(BaseActor u:BaseActor.getList(mainStage, "com.mygdx.pgame.SEnemy"))
		{
			
			if(player.overLaps(u))
				player.preventOverlap(u);
			EnemyUpdates(u);
			boolean distance=player.isWithinDistance(200, u);
			if(distance)
			{
				
				if(time4>1.3f)
				{
				
					EBullet f=new EBullet(0,0,mainStage);
					f.centerAtActor(u);
					f.addAction(Actions.moveTo(player.getX(), player.getY(),1));
					enemyShot.play();
					time4=0;
				}
				
			
			
			}
			
		}
		for(BaseActor e:BaseActor.getList(mainStage, "com.mygdx.pgame.Enemy"))
		{
			EnemyUpdates(e);
			for(BaseActor u:BaseActor.getList(mainStage, "com.mygdx.pgame.SEnemy"))
			{
				if(e.overLaps(u))
					e.preventOverlap(u);
			}
			for(BaseActor f:BaseActor.getList(mainStage, "com.mygdx.pgame.Enemy"))
			{
				Enemy target=(Enemy)e;
				Enemy target2=(Enemy)f;
				if(target.getID()!=target2.getID())
				{
					target.preventOverlapbyID(target2);
				}
					
			}
//			boolean distance=player.isWithinDistance(200, e);
//		if(distance)
//		{
//			int random=MathUtils.random(5,10);
//			e.addAction(Actions.moveTo(player.getX()-111, player.getY()-111, random));
//		
//		
//		}
		

		for(BaseActor r:BaseActor.getList(mainStage, "com.mygdx.pgame.Rock"))
		{
		
			if(e.overLaps(r))
			{
				e.preventOverlap(r);
			}	
		}
			}
		for(BaseActor r:BaseActor.getList(mainStage, "com.mygdx.pgame.Rock"))
		{
			if(player.overLaps(r))
			{
				player.preventOverlap(r);
			}
		
			for(BaseActor gun:BaseActor.getList(mainStage, "com.mygdx.pgame.Bullet"))
			{
				if(gun.overLaps(r))
					gun.remove();
			}
			for(BaseActor gun:BaseActor.getList(mainStage, "com.mygdx.pgame.Shotgun"))
			{
				if(gun.overLaps(r))
					gun.remove();
			}
			for(BaseActor gun:BaseActor.getList(mainStage, "com.mygdx.pgame.FireBullet"))
			{
				if(gun.overLaps(r))
					gun.remove();
			}
			for(BaseActor gun:BaseActor.getList(mainStage, "com.mygdx.pgame.EBullet"))
			{
				if(gun.overLaps(r))
					gun.remove();
			}
		}
			
		//guns by enemies
		for(BaseActor r:BaseActor.getList(mainStage, "com.mygdx.pgame.EBullet"))
		{
		if(player.overLaps(r)&&time5>1)
		{
			r.remove();
			player.health-=2;
			time5=0;
		}
			
		}
		//pick ups
		for(BaseActor fPick:BaseActor.getList(mainStage, "com.mygdx.pgame.FirePick"))
		{
			if(player.overLaps(fPick)&&!player.getGun().equals(fire)&&time6>3)
			{
				fPick.remove();
				
				dropCurrentGun();
				player.setGun(fire);
				time6=0;
			}
			for(BaseActor r:BaseActor.getList(mainStage, "com.mygdx.pgame.Rock"))
			{
			
				if(fPick.overLaps(r))
				{
					fPick.preventOverlap(r);
				}
			}
		
		}
		for(BaseActor sPick:BaseActor.getList(mainStage, "com.mygdx.pgame.ShotgunPick"))
		{
			if(player.overLaps(sPick)&&!player.getGun().equals(shotgun)&&time6>3)
			{
				sPick.remove();
			
				dropCurrentGun();
				
				player.setGun(shotgun);
			time6=0;
			}
			
			for(BaseActor r:BaseActor.getList(mainStage, "com.mygdx.pgame.Rock"))
			{
			
				if(sPick.overLaps(r))
				{
					sPick.preventOverlap(r);
				}
			}
		}
		for(BaseActor bPick:BaseActor.getList(mainStage, "com.mygdx.pgame.BulletPick"))
		{
			if(player.overLaps(bPick)&&!player.getGun().equals(guns)&&time6>3)
			{
				bPick.remove();
				
				dropCurrentGun();
			
				player.setGun(guns);
				time6=0;
			}
			for(BaseActor r:BaseActor.getList(mainStage, "com.mygdx.pgame.Rock"))
			{
			
				if(bPick.overLaps(r))
				{
					bPick.preventOverlap(r);
				}
				
			}
		}
		
		//player stuff
			
		
		//win
		if(BaseActor.count(mainStage, "com.mygdx.pgame.Enemy")<=0&&BaseActor.count(mainStage, "com.mygdx.pgame.SEnemy")<=0&&collect)
		{
			
			if(player.inventory.get(0).toString().equals("BulletC"))
			{
				int random=MathUtils.random(0, 1);
				if(random==0)
				{
					ShotgunC n=new ShotgunC(64,823,mainStage);
				}else
				{
					FireC n=new FireC(64,823,mainStage);
				}
			}else if(player.inventory.get(0).toString().equals("FireC"))
			{
			
				int random=MathUtils.random(0, 1);
				if(random==0)
				{
					BulletC n=new BulletC(64,823,mainStage);
				}else
				{
					ShotgunC n=new ShotgunC(64,823,mainStage);
				}
			}else
			{
			
				int random=MathUtils.random(0, 1);
				if(random==0)
				{
					BulletC n=new BulletC(64,823,mainStage);
				}else
				{
					FireC n=new FireC(64,823,mainStage);
				}
			}
			
			collect=false;
			
		}
		
		//stuff inv
		for(BaseActor g:BaseActor.getList(mainStage, "com.mygdx.pgame.levels.BulletC"))
		{
			BulletC gn=(BulletC)g;
			if(player.overLaps(gn)&&!win)
			{
				g.remove();
				player.inventory.add(gn);
				Continue.setVisible(true);
				win=true;
			}
		}
		for(BaseActor g:BaseActor.getList(mainStage, "com.mygdx.pgame.levels.FireC"))
		{
			FireC gn=(FireC)g;
			if(player.overLaps(gn)&&!win)
			{
				g.remove();
				player.inventory.add(gn);
				Continue.setVisible(true);
				win=true;
			}
		}
		for(BaseActor g:BaseActor.getList(mainStage, "com.mygdx.pgame.levels.ShotgunC"))
		{
			ShotgunC gn=(ShotgunC)g;
			if(player.overLaps(gn)&&!win)
			{
				g.remove();
				player.inventory.add(gn);
				Continue.setVisible(true);
				win=true;
			}
		}
		//ui stuff
		health.setWidth(player.health*100);
		
		
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}
@Override
	public boolean keyDown(int keycode)
	{
		if(keycode==Keys.SPACE)
			player.shoot();
	
		if(keycode==Keys.C&&win)
		{
			BaseGame.setActiveScreen(new Level5());
		}
		
		return false;
	}
 public void prize(float g,float h)
 {
				int random1=MathUtils.random(0,2);
				
				switch(random1)
				{
				case 0:
					BulletPick f=new BulletPick(g,h,mainStage);
					StopGive=false;
					break;
				case 1:
					FirePick s=new FirePick(g,h,mainStage);
					StopGive=false;
					break;
				case 2:
					ShotgunPick r=new ShotgunPick(g,h,mainStage);
					StopGive=false;
					break;
					
				}
			
			
 }
 boolean dropped;
 public void dropCurrentGun()
 {

	 Guns f= player.getGun();
		switch(f)
		{
		case GUN:
			BulletPick g=new BulletPick(0,0,mainStage);
			g.setZIndex(20);
			if(player.getRotation()>=90&&player.getRotation()<=270)
			g.centerAtPosition(player.getX()+120, player.getY());
			if(player.getRotation()<90||player.getRotation()>270)
				g.centerAtPosition(player.getX()-80, player.getY());
		
//			if(pla)
			dropped=false;
			break;
		case Fire:
			FirePick g2=new FirePick(0,0-50,mainStage);
			g2.setZIndex(20);
			if(player.getRotation()>=90&&player.getRotation()<=270)
			g2.centerAtPosition(player.getX()+120, player.getY());
			if(player.getRotation()<90||player.getRotation()>270)
				g2.centerAtPosition(player.getX()-80, player.getY());
			
			dropped=false;
			break;
		case Shotgon:
			ShotgunPick g3=new ShotgunPick(0,0,mainStage);
			g3.setZIndex(20);
			if(player.getRotation()>=90&&player.getRotation()<=270)
			g3.centerAtPosition(player.getX()+90, player.getY());
			if(player.getRotation()<90||player.getRotation()>270)
				g3.centerAtPosition(player.getX()-80, player.getY());
			dropped=false;
			break;	
			
		default:
			System.out.println("Error");
			break;
		}
		dropped=true;
 }
 
 public void EnemyUpdates(BaseActor e)
 {
		if(player.overLaps(e))
		{
			player.preventOverlap(e);
			if(time>=1.00f)
			{
				player.health--;
				time=0;
			}
		}
		if(player.getGun().equals(guns))
		for(BaseActor gun:BaseActor.getList(mainStage, "com.mygdx.pgame.Bullet"))
		{
			//normal bullet
			if(gun.overLaps(e))
			{
				
				int random1=MathUtils.random(5,6);
				TextDamage text=new TextDamage(random1+"",BaseGame.labelStyle);
				text.setColor(Color.BLUE);
				text.setPosition(gun.getX(), gun.getY());
				gun.remove();
				
				
				((CEnemy) e).healthE-=random1;
				
				mainStage.addActor(text);
				
			
	//-------for future-------\\
				
//					ehealth.setPosition(e.getX(),e.getY()+60);
//				ehealth.setVisible(true);
//				ehealth.setWidth(((Enemy) e).healthE*10);
//				if(((Enemy) e).healthE<=0)
//				{
//					ehealth.setVisible(false);
//				}
//   \\-----------------------//
			}
			}
		//fire bullet
		if(player.getGun().equals(fire))
			for(BaseActor gun:BaseActor.getList(mainStage, "com.mygdx.pgame.FireBullet"))
			{
				if(gun.overLaps(e))
				{
			
					if(time3>=0.2f)
					{
						int damage=4;
						TextDamage text=new TextDamage(damage+"",BaseGame.labelStyle);
						text.setColor(Color.RED);
						text.setPosition(gun.getX(), gun.getY());
						
						((CEnemy) e).healthE-=damage;
						
						FirePool firepool=new FirePool(0,0,mainStage);
						firepool.centerAtActor(e);
						mainStage.addActor(text);
						time3=0;
					}
				}
				
			}

		//shotgun
		if(player.getGun().equals(shotgun))
		for(BaseActor gun:BaseActor.getList(mainStage, "com.mygdx.pgame.Shotgun"))
		{
			if(gun.overLaps(e))
			{
				int random=MathUtils.random(4, 6);
				int damage=random;
				TextDamage text=new TextDamage(damage+"",BaseGame.labelStyle);
				text.setColor(Color.ORANGE);
				text.setPosition(gun.getX(), gun.getY());
				gun.remove();
				mainStage.addActor(text);
				((CEnemy)e).healthE-=damage;
			}
		}
		for(BaseActor firepool:BaseActor.getList(mainStage, "com.mygdx.pgame.FirePool"))
		{
			if(e.overLaps(firepool))
			{
				if(time2>=0.4)
				{
					double damage=3;
					TextDamage text=new TextDamage(damage+"",BaseGame.labelStyle);
					text.setColor(Color.RED);
			
					text.setPosition(firepool.getX(), firepool.getY());
					((CEnemy) e).healthE-=damage;
					mainStage.addActor(text);
					time2=0;
				}
				
			}
		}
		if(((CEnemy) e).healthE<=0)
		{
			e.remove();
			random=MathUtils.random(0,1);
			if(random==1)
			prize(e.getX(),e.getY());
		}
 }
  }
