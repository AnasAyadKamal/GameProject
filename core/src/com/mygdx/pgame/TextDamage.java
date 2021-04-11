package com.mygdx.pgame;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TextDamage extends Label{

	public TextDamage(CharSequence text, LabelStyle style) {
		super(text, style);
		Action pulse2=Actions.sequence(
				Actions.scaleTo(0.1f, 0.1f,0.1f),
				Actions.scaleTo(1.0f,1.0f, 0.1f));
		addAction(pulse2);
		
		addAction(Actions.after(Actions.fadeOut(0.03f)));
		addAction(Actions.after(Actions.removeActor()));
	}

	
    public void centerAtPosition(float x, float y)
    {
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    public void centerAtActor(BaseActor other)
    {

        this.centerAtPosition(other.getX()+other.getWidth()/2,other.getY()+other.getHeight()/2);
    }
}
