package com.mygdx.pgame;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class FirePool extends BaseActor{

	public FirePool(float x, float y, Stage s) {
		super(x, y, s);
		String fileNames[]= {"fires-1.png","fires-2.png","fires-3.png"};
		this.loadAnimationFromFiles(fileNames, 0.1f, true);
		addAction(Actions.delay(1));
		addAction(Actions.after(Actions.fadeOut(4.0f)));
		addAction(Actions.after(Actions.removeActor()));
		
		setZIndex(4);
	}

	
}
