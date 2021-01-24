package com.mygdx.escapefromlannioncity;

import com.badlogic.gdx.Screen;
import com.mygdx.escapefromlannioncity.identify.Accueil;
import com.mygdx.escapefromlannioncity.menu.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.escapefromlannioncity.screens.*;
import com.mygdx.escapefromlannioncity.utility.*;

public class EscapeFromLannionCity extends Game {

	public SpriteBatch batch;
	public Inventory inventory;
	public BitmapFont mainFont;
	public int isLoggedin = 0; //0 au début, 1 si connecté, 2 si joue hors ligne
	public String pseudo ="";
	public Screen[] menuEtTableau = new Screen[3];
	public float volume = 0.0f;

	@Override
	public void create () {
		batch = new SpriteBatch();
		inventory = new Inventory(Gdx.files.internal("image/Utilitaire/barreInventaire.jpg"));
		inventory.resize();
		mainFont = new BitmapFont(Gdx.files.internal("MainFont.fnt"));
		// au lancement du jeu, affiche l'EnssatScreen
		menuEtTableau[0] = new Menu(this);
		menuEtTableau[1] = new Warp(this, "00:00",0,0);
		menuEtTableau[2] = new Accueil(this);

		this.setScreen(menuEtTableau[2]);

		// teste la création de fichiers de scores
		//TestMain.TestScore();

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
		menuEtTableau[2].dispose();

	}

}
