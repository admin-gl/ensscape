package com.mygdx.escapefromlannioncity.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.menu.ButtonOpenMenu;
import com.mygdx.escapefromlannioncity.utility.ChangingCursor;
import com.mygdx.escapefromlannioncity.utility.FlavorText;
import com.mygdx.escapefromlannioncity.utility.GameObject;

/**
 * Class d'affichage utilisateur
 * En cas de realisation d'un nouveau tableau, etendre cette classe au tableau en question
 */
public abstract class UI implements Screen {

    public Sprite background;

    private final Music musique;

    private final ButtonOpenMenu buttonMenu;  /* Concerne le Bouton d'affichage du Menu */

    public final EscapeFromLannionCity game;

    public final Viewport viewport;
    public final OrthographicCamera camera;

    public final GameObject zoneGauche;
    public final GameObject zoneDroite;
    public final GameObject textZone;

    private final GameObject zoneTimer;

    private final BitmapFontCache timerText;

    public FlavorText flavorText;

    public final ChangingCursor cursor;

    private long lastTime;
    private long timeAtPause;
    private String timeFromBegin = "00:00";

    public UI(EscapeFromLannionCity game, String pathMusique) {

        this.game = game;

        // on met en place le curseur changeant pour les niveaux
        this.cursor = new ChangingCursor();

        // on initialise la musique
        musique = Gdx.audio.newMusic(Gdx.files.internal(pathMusique));
        musique.setLooping(true);

        /* Initialise le Bouton de Menu */
        buttonMenu = new ButtonOpenMenu();

        // place une camera dans la vue actuelle de la fenêtre
        camera = new OrthographicCamera();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);
        viewport.apply(true);

        // on place les objets recurrents
        zoneDroite = new GameObject(Gdx.files.internal("image/Utilitaire/blacksquare.png"),247, 17.5f, 18, 35);
        zoneGauche = new GameObject(Gdx.files.internal("image/Utilitaire/blacksquare.png"),9, 17.5f, 18, 35);
        zoneTimer = new GameObject(Gdx.files.internal("image/Utilitaire/zoneTimer.png"),238, 138, 36, 12);
        textZone = new GameObject(Gdx.files.internal("image/Utilitaire/zoneTexte.png"),128, 35, 206, 50);

        // on creer un FontCache qui contiendra le texte du timer
        timerText = game.mainFont.newFontCache();
        timerText.setText(timeFromBegin, Gdx.graphics.getWidth()-100,Gdx.graphics.getHeight() - 20, 0, 5, 100, Align.left, true);

        zoneDroite.resize();
        zoneGauche.resize();
        zoneTimer.resize();
        textZone.resize();

        // on cache la zone de texte car non necessaire sans texte de dialogue
        textZone.hide();
        // on initialise le texte de dialogue vide
        flavorText = new FlavorText(game, "");
    }


    @Override
    public void show() {
        musique.setVolume(game.volume);
        musique.play();
        lastTime = TimeUtils.millis() - timeAtPause;
    }

    @Override
    public void render(float delta) {
        timePassed();
        //Affiche le bouton "flottant" de Menu
        buttonMenu.initButtonMenu(game);

        // check pour un clic gauche de la souris
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            if(!flavorText.isDrawing() && !textZone.isHidden()){
                textZone.hide();
                flavorText.setText("");
            } else if(flavorText.isDrawing()){
                flavorText.drawAll(game.batch);
            }

            // prend les coordonnees du clic et les convertis en coordonnees du monde
            Vector2 touched = new Vector2();
            touched.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);

            // Lance le Menu si on clique sur le Bouton correspondant
            if(buttonMenu.contains(touched)){
                game.setScreen(game.menuEtTableau[0]);
            }
            game.inventory.checkZoom(touched, game.batch);
        }

        zoneDroite.drawFix(game.batch);
        zoneGauche.drawFix(game.batch);
        zoneTimer.drawFix(game.batch);
        textZone.drawFix(game.batch);

        timerText.draw(game.batch);

        flavorText.draw(game.batch);

        if(flavorText.isDrawing() && textZone.isHidden()){
            textZone.unhide();
        }

        game.inventory.drawFix(game.batch);

        game.batch.flush();
    }

    @Override
    public void resize(int width, int height) {
        // resize la vue avec la fenetre
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        musique.pause();
        timeAtPause = TimeUtils.millis() - lastTime;
    }

    @Override
    public void resume() {
        musique.setVolume(game.volume);
        musique.play();
        lastTime = TimeUtils.millis() - timeAtPause;
    }

    @Override
    public void hide() {
        musique.pause();
        timeAtPause = TimeUtils.millis() - lastTime;
    }

    @Override
    public void dispose() {
        musique.dispose();
        buttonMenu.dispose();
        zoneGauche.dispose();
        zoneDroite.dispose();
        flavorText.dispose();
    }

    /**
     * Permet de faciliter l'affichage dans les fils par simple appel de cette methode
     */
    public void setupRender(){
        // clear l'affichage avec un fond de couleur choisis et de canal alpha choisis
        Gdx.gl.glClearColor(0.1f, 0.2f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update la camera
        camera.update();

        // update la vue les dernieres updates sont au dessus des premieres
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background.getTexture(), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
    }

    /**
     * Permet de gerer le timer du niveau en cours
     */
    public void timePassed(){
        int min = Integer.parseInt(timeFromBegin.substring(0,2));
        int sec = Integer.parseInt(timeFromBegin.substring(3,5));

        if(TimeUtils.timeSinceMillis(lastTime) >= 1000){
            lastTime = TimeUtils.millis();
            sec += 1;
            if(sec == 60){
                min += 1;
                sec = 0;
            }
            String finalSec;
            String finalMin;
            if(sec < 10){
                 finalSec = "0" + sec;
            } else {
                finalSec = "" + sec;
            }
            if(min < 10){
                finalMin = "0" + min;
            } else {
                finalMin = "" + min;
            }
            timeFromBegin = finalMin + ":" + finalSec;
            timerText.setText(timeFromBegin, Gdx.graphics.getWidth()-100,Gdx.graphics.getHeight() - 20, 0, 5, 100, Align.left, true);
        }
    }
}
