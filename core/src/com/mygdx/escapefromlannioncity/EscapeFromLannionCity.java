package com.mygdx.escapefromlannioncity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.escapefromlannioncity.screens.EnssatScreen;

public class EscapeFromLannionCity extends Game {

	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		// au lancement du jeu, affiche l'EnssatScreen
		this.setScreen(new EnssatScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		//dispose des graphismes utilises
		batch.dispose();
		this.getScreen().dispose();
	}

}
