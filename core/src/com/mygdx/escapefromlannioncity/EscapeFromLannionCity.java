package com.mygdx.escapefromlannioncity;

import com.badlogic.gdx.Screen;
import com.mygdx.escapefromlannioncity.menu.Menu;
import com.mygdx.escapefromlannioncity.score.TestMain;

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
	public Screen[] menuEtTableau = new Screen[2];
	public float volume = 0f;

	@Override
	public void create () {
		menuEtTableau[0] = new Menu(this);
		menuEtTableau[1] = new AmphiEnssat(this);
		batch = new SpriteBatch();
		inventory = new Inventory(Gdx.files.internal("image/Utilitaire/barreInventaire.jpg"));
		inventory.resize();
		mainFont = new BitmapFont(Gdx.files.internal("MainFont.fnt"));
		mainFont.setColor(Color.WHITE);
		// au lancement du jeu, affiche l'EnssatScreen
		this.setScreen(menuEtTableau[0]);

		// teste la création de fichiers de scores
		TestMain.TestScore();

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
		menuEtTableau[0].dispose();
		menuEtTableau[1].dispose();
	}

}
