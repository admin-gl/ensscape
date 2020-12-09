package com.mygdx.escapefromlannioncity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.escapefromlannioncity.screens.*;
import com.mygdx.escapefromlannioncity.utility.*;

public class EscapeFromLannionCity extends Game {

	public SpriteBatch batch;
	public Inventory inventory;
	public BitmapFont mainFont;

	@Override
	public void create () {
		batch = new SpriteBatch();
		inventory = new Inventory(Gdx.files.internal("image/barreInventaire.jpg"));
		mainFont = new BitmapFont(Gdx.files.internal("MainFont.fnt"));
		mainFont.setColor(Color.WHITE);
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
		inventory.clear();
		inventory.dispose();
		this.getScreen().dispose();
	}

}
