package com.mygdx.escapefromlannioncity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.escapefromlannioncity.screens.*;
import com.mygdx.escapefromlannioncity.utility.*;

import java.util.ArrayList;

public class EscapeFromLannionCity extends Game {

	public SpriteBatch batch;
	public ArrayList<GameObject> inventory;

	@Override
	public void create () {
		batch = new SpriteBatch();
		inventory = new ArrayList<>(10);
		// au lancement du jeu, affiche l'EnssatScreen
		this.setScreen(new AmphiEnssat(this));
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
