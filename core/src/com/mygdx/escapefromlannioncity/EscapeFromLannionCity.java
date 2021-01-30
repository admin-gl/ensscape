package com.mygdx.escapefromlannioncity;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.escapefromlannioncity.identify.Accueil;
import com.mygdx.escapefromlannioncity.menu.Menu;
import com.mygdx.escapefromlannioncity.menu.MenuPrincipal;
import com.mygdx.escapefromlannioncity.menu.MenuPrincipal2;
import com.mygdx.escapefromlannioncity.sauvegarde.SAmphiEnssat;
import com.mygdx.escapefromlannioncity.sauvegarde.SParcStAnne;
import com.mygdx.escapefromlannioncity.sauvegarde.SWarp;
import com.mygdx.escapefromlannioncity.screens.*;
import com.mygdx.escapefromlannioncity.utility.*;

/**
 * Classe qui gère les ecrans, l'inventaire et les états de connections du jeu,
 * et son déroulement.
 */
public class EscapeFromLannionCity extends Game {

	public SpriteBatch batch;
	/**
	 * Inventaire du joueur
	 */
	public Inventory inventory;
	/**
	 * police d'ecriture
	 */
	public BitmapFont mainFont;
	/**
	 * Etat de connection du joueur
	 * 0 au début, 1 si connecté, 2 si joue hors ligne
	 */
	public int isLoggedin = 0;
	/**
	 * pseudo du joueur
	 */
	public String pseudo ="";
	/**
	 * les ecrans qu'on garde tout au long du jeu
	 */
	public Screen[] menuEtTableau = new Screen[5];
	public float volume = 0.2f;
	public Sound sfxButton;

	@Override
	public void create () {
		batch = new SpriteBatch();
		inventory = new Inventory(Gdx.files.internal("image/Utilitaire/barreInventaire.jpg"));
		inventory.resize();
		mainFont = new BitmapFont(Gdx.files.internal("MainFont.fnt"));
		sfxButton = Gdx.audio.newSound(Gdx.files.internal("sound/Button.wav"));

		// au lancement du jeu, affiche l'EnssatScreen
		menuEtTableau[0] = new Menu(this);
		menuEtTableau[1] = new AmphiEnssat(this, "00:00",0,0);
		menuEtTableau[2] = new Accueil(this);
		menuEtTableau[3] = new MenuPrincipal(this);
		menuEtTableau[4] = new MenuPrincipal2(this);

		//on commence par les transition de début avant l'accueil
		this.setScreen(menuEtTableau[3]);

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
		menuEtTableau[3].dispose();
		menuEtTableau[4].dispose();
		sfxButton.dispose();

	}

}
