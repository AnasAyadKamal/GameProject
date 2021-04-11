package com.mygdx.pgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public abstract class BaseGame extends Game{
	
	private static BaseGame game;
	public static LabelStyle labelStyle;
	public static TextButtonStyle textButtonStyle;

	public BaseGame()
	{
		game=this;

	}
	
	public static void setActiveScreen(BaseScreen bs)
	{
		game.setScreen(bs);
	}
	
	public void create()
	{
		InputMultiplexer im=new InputMultiplexer();
		Gdx.input.setInputProcessor(im);
		labelStyle = new LabelStyle();
		FreeTypeFontGenerator fontGenerator =
				new FreeTypeFontGenerator(Gdx.files.internal("Teko-Regular.ttf"));
		FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
		fontParameters.size = 24;
		fontParameters.color = Color.WHITE;
		fontParameters.borderWidth = 2;
		fontParameters.borderColor = Color.BLACK;
		fontParameters.borderStraight = true;
		fontParameters.minFilter = TextureFilter.Linear;
		fontParameters.magFilter = TextureFilter.Linear;
		
		BitmapFont customFont = fontGenerator.generateFont(fontParameters);
		labelStyle.font = customFont;
		
		textButtonStyle=new TextButtonStyle();
		
		Texture buttonTexture=new Texture(Gdx.files.internal("buton.png"));
		
		NinePatch np=new NinePatch(buttonTexture,24,24,24,24);
		
		textButtonStyle.up =new NinePatchDrawable(np);
		
		textButtonStyle.font = customFont;
		textButtonStyle.fontColor = Color.GRAY;
		
	}
	
	
			
	
	
	
	
	

}
