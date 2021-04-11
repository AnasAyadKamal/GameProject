package com.mygdx.pgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;


public class BaseActor extends Group
{
	public int ID;
	private static Rectangle worldBounds;
    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;
    private Vector2 velocityVec;
    private Vector2 accelerationVec;
    private Polygon boundaryPolygon;
    private float acceleration;
    private float maxSpeed;
    private float deceleration;

    public BaseActor(float x, float y, Stage s)
    {
        super();

        setPosition(x,y);

        s.addActor(this);

        animation=null;
        elapsedTime =0;
        animationPaused=false;
        velocityVec=new Vector2(0,0);
        accelerationVec=new Vector2(0,0);
        maxSpeed=1000;
        deceleration=0;
    }
    public static Rectangle getWorldBounds()
    {
    return worldBounds;
    }
    public boolean isWithinDistance(float d,BaseActor other)
    {
    	Polygon poly1=this.getBoundaryPolygon();
    	float scaleX=(this.getWidth()+2 *d)/this.getWidth();
    	float scaleY=(this.getHeight()+2 *d)/this.getHeight();
    	poly1.setScale(scaleX, scaleY);
    	Polygon poly2=other.getBoundaryPolygon();
    	if ( !poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()) )
    		return false;
    	return Intersector.overlapConvexPolygons(poly1, poly2);
    }
    public void wrapAroundWorld()
    {
    	if(getX()+getWidth()<0)
    		setX(worldBounds.width);
    	if (getX() > worldBounds.width)
    		setX( -getWidth());
    	if (getY() + getHeight() < 0)
    		setY( worldBounds.height );
    	if (getY() > worldBounds.height)
    		setY( -getHeight() );
    }
    
    public void alignCamera()
    {
    	Camera cam=this.getStage().getCamera();
    	Viewport vw=this.getStage().getViewport(); 
    	
    	cam.position.set(this.getX()+this.getOriginX(),this.getY()+this.getOriginY(),0);
    	
    	cam.position.x = MathUtils.clamp(cam.position.x,
    			cam.viewportWidth/2, worldBounds.width - cam.viewportWidth/2);
    			cam.position.y = MathUtils.clamp(cam.position.y,
    			cam.viewportHeight/2, worldBounds.height - cam.viewportHeight/2);
    			cam.update();
    }
    public static void setWorldBounds(float width,float height)
    {
    	worldBounds=new Rectangle(0,0,width,height);
    }
    public static void setWorldBounds(BaseActor ba)
    {
    	setWorldBounds(ba.getWidth(),ba.getHeight());
    }
    
    public void boundToWorld()
    {
    // check left edge
    if (getX() < 0)
    setX(0);
    // check right edge
    if (getX() + getWidth() > worldBounds.width)
    setX(worldBounds.width - getWidth());
    // check bottom edge
    if (getY() < 0)
    setY(0);
    // check top edge
    if (getY() + getHeight() > worldBounds.height)
    setY(worldBounds.height - getHeight());
    }
    public static ArrayList<BaseActor> getList(Stage stage,String className)
    {
        ArrayList<BaseActor> list=new ArrayList<BaseActor>();

        Class theClass=null;

        try
        {
            theClass=Class.forName(className);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        for(Actor a:stage.getActors())
        {
            if(theClass.isInstance(a))
                list.add((BaseActor)a);
        }
        return list;
    }

    public static int count(Stage stage,String className)
    {
        return getList(stage,className).size();
    }


    public void setMaxSpeed(float speed)
    {
        maxSpeed=speed;

    }
    public void setDeceleration(float dec)
    {
        deceleration=dec;
    }

    public void setBoundaryRectangle()
    {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0,0, w,0, w,h, 0,h};
        boundaryPolygon = new Polygon(vertices);
    }

    public void setBoundaryPolygon(int numSides)
    {
        float w=getWidth();
        float h=getHeight();

        float[] vertices=new float[2*numSides];
        for(int i=0;i<numSides;i++)
        {
            float angle=i* 6.28f/numSides;
            //x coordinate
            vertices[2*i]=w/2 *MathUtils.cos(angle) +w/2;
            //y coordinates
            vertices[2*i+1]=h/2 *MathUtils.sin(angle)+h/2;


        }
        boundaryPolygon=new Polygon(vertices);
    }
    public void centerAtPosition(float x, float y)
    {
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    public void centerAtActor(BaseActor other)
    {
        this.centerAtPosition(other.getX()+other.getWidth()/2,other.getY()+other.getHeight()/2);
    }

    public void setOpacity(float opacity)
    {
        this.getColor().a=opacity;
    }

    public Polygon getBoundaryPolygon()
    {
        boundaryPolygon.setPosition( getX(), getY() );
        boundaryPolygon.setOrigin( getOriginX(), getOriginY() );
        boundaryPolygon.setRotation ( getRotation() );
        boundaryPolygon.setScale( getScaleX(), getScaleY() );
        return boundaryPolygon;
    }

    public boolean overLaps(BaseActor other)
    {
        Polygon pol1=this.getBoundaryPolygon();
        Polygon pol2=other.getBoundaryPolygon();

        if(!pol1.getBoundingRectangle().overlaps(pol2.getBoundingRectangle()))
            return false;


        return Intersector.overlapConvexPolygons(pol1,pol2);


    }


    public void setAnimation(Animation<TextureRegion> anim)
    {
        animation=anim;
        TextureRegion tr=anim.getKeyFrame(0);
        float w=tr.getRegionWidth();
        float h=tr.getRegionHeight();
        setSize(w,h);
        setOrigin(w/2,h/2);
        if(boundaryPolygon==null)
            setBoundaryRectangle();



    }

    public void setAnimationPaused(boolean t)
    {
        animationPaused=t;
    }

    public void act(float dt)
    {
        super.act(dt);

        if(!animationPaused)
        {
            elapsedTime+=dt;
        }
    }

    public void draw(Batch batch, float parentAlpha)
    {
       

        Color c=getColor();
        batch.setColor(c.r, c.g, c.b, c.a);

        if ( animation != null && isVisible() )
            batch.draw( animation.getKeyFrame(elapsedTime),
                    getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
        super.draw(batch,parentAlpha);

    }

    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames,float frameDuration,boolean loop)
    {
        int fileCount=fileNames.length;

        Array<TextureRegion> textureArray=new Array<TextureRegion>();

        for(int i=0;i<fileCount;i++)
        {
            String fileName=fileNames[i];
            Texture texture = new Texture( Gdx.files.internal(fileName) );
            texture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
            textureArray.add(new TextureRegion(texture));
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);

        if (animation == null)
            setAnimation(anim);
         return anim;
    }
    
    public Animation<TextureRegion> loadAnimationFromFilesonebyone(String[] fileNames,float frameDuration,boolean loop,int Thename)
    {
        

        Array<TextureRegion> textureArray=new Array<TextureRegion>();

        
            String fileName=fileNames[Thename];
            Texture texture = new Texture( Gdx.files.internal(fileName) );
            texture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
            textureArray.add(new TextureRegion(texture));
        

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);

        if (animation == null)
            setAnimation(anim);
         return anim;
    }
    // to be continued on page 50~


    public Animation<TextureRegion> loadAnimationFromSheet(String fileName,int rows,int cols,float frameDuration,boolean loop)
    {
        Texture texture=new Texture(Gdx.files.internal(fileName),true);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        int frameWidth= texture.getWidth()/cols;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] temp=TextureRegion.split(texture, frameWidth, frameHeight);

        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for(int r=0;r<rows;r++)
        {
            for(int c=0;c<cols;c++)
            {
                textureArray.add(temp[r][c]);
            }
        }

                Animation<TextureRegion> anim=new Animation<TextureRegion>(frameDuration,textureArray);


        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);


        if (animation == null)
            setAnimation(anim);


        return anim;
    }

    public Animation<TextureRegion> loadTexture(String fileName)
    {
        String[] fileNames=new String[1];
        fileNames[0]=fileName;
        return loadAnimationFromFiles(fileNames, 1, true);
    }
    public boolean isAnimationFinished()
    {
        return animation.isAnimationFinished(elapsedTime);
    }


    public void setSpeed(float speed)
    {
        // if length is zero, then assume motion angle is zero degrees
        if(velocityVec.len()==0)
            velocityVec.set(speed,0);
        else
            velocityVec.setLength(speed);



    }

    public float getSpeed()
    {
        return velocityVec.len();
    }

    public void setMotionAngle(float motion)
    {
        velocityVec.setAngleDeg(motion);
    }

    public float getMotionAngle()
    {
        return velocityVec.angle();
    }

    public boolean isMoving()
    {
        return (getSpeed()>0);
    }

    public void setAcceleration(float acc)
    {
        acceleration=acc;
    }

    public void accelerateAtAngle(float angle)
    {
        accelerationVec.set(new Vector2(acceleration,0).setAngle(angle));
    }

    public void accelerateForward()
    {
        accelerateAtAngle(getRotation());
    }
    //physics
    public void applyPhysics(float dt)
    {
        // apply acceleration
        velocityVec.add(accelerationVec.x * dt,accelerationVec.y * dt);

        float speed=getSpeed();

        if(accelerationVec.len()==0)
            speed-=deceleration*dt;

        speed=MathUtils.clamp(speed,0,maxSpeed);

        setSpeed(speed);

        moveBy(velocityVec.x *dt,velocityVec.y * dt);

        accelerationVec.set(0,0);

    }

    public Vector2 preventOverlap(BaseActor other)
    {
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        if(!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
        {
            return null;
        }

        MinimumTranslationVector mtv = new MinimumTranslationVector();

        boolean polygonOverlap=Intersector.overlapConvexPolygons(poly1,poly2,mtv);

        if(!polygonOverlap)
            return null;

        this.moveBy(mtv.normal.x * mtv.depth,mtv.normal.y*mtv.depth);
        return mtv.normal;

    }

    public Vector2 preventOverlapbyID(BaseActor other)
    {
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();
      
        int ID1=this.getID();
        int ID2=other.getID();
        if(!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle())&&ID1==ID2)
        {
            return null;
        }

        MinimumTranslationVector mtv = new MinimumTranslationVector();

        boolean polygonOverlap=Intersector.overlapConvexPolygons(poly1,poly2,mtv);

        if(!polygonOverlap)
            return null;

        this.moveBy(mtv.normal.x * mtv.depth,mtv.normal.y*mtv.depth);
        return mtv.normal;

    }
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}


}
