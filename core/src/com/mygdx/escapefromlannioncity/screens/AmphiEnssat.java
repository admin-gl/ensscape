package com.mygdx.escapefromlannioncity.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.utility.AnimatedGameObject;
import com.mygdx.escapefromlannioncity.utility.GameObject;

public class AmphiEnssat implements Screen {

    private final EscapeFromLannionCity game;

    private final Viewport viewport;

    private final Sprite background;

    private final Texture brightPlace;
    private final Texture darkPlace;
    //private final Texture zoomElec;

    private final GameObject zonePc;
    private final GameObject zoneTableau;
    private final GameObject zoneElec;
    private final GameObject quitZoom;

    private final AnimatedGameObject[] Interrupteurs;

    private final int[] code;

    private final OrthographicCamera camera;

    private boolean zoomed;

    public AmphiEnssat(final EscapeFromLannionCity game){
        this.game = game;

        // peut etre inutile, a verifier...
        Screen precedent = game.getScreen();
        if(precedent != null) precedent.dispose();

        // place une camera dans la vue actuelle de la fenêtre
        camera = new OrthographicCamera();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);
        viewport.apply(true);

        darkPlace = new Texture(Gdx.files.internal("image/Amphi137c-piece1_sombre.jpg"));
        brightPlace = new Texture(Gdx.files.internal("image/Amphi137c-piece1.jpg"));
        //zoomElec = new Texture(Gdx.files.internal("image/tableauElectrique.jpg"));
                // set les textures utilise dans la scene
        background = new Sprite(darkPlace);

        zonePc = new GameObject(Gdx.files.internal("image/empty.png"), 8, 82, 16, 22);
        zoneTableau = new GameObject(Gdx.files.internal("image/empty.png"), 156, 87 ,156 , 46);
        zoneElec = new GameObject(Gdx.files.internal("image/empty.png"),48, 63, 54, 32);

        quitZoom = new GameObject(Gdx.files.internal("image/empty.png"), 248, 126, 16, 16);

        Interrupteurs = new AnimatedGameObject[11];
        Texture[] spritesInter = new Texture[2];
        spritesInter[0] = new Texture(Gdx.files.internal("animation/pinpon/1.jpg"));
        spritesInter[1] = new Texture(Gdx.files.internal("animation/pinpon/2.jpg"));
        for(int i=0; i<11; i++){
            Interrupteurs[i]= new AnimatedGameObject(spritesInter);
            Interrupteurs[i].setPlayMode(Animation.PlayMode.LOOP);
            Interrupteurs[i].setSize(16, 64);
            Interrupteurs[i].setCenterPos( 80+i*16, 72);
            Interrupteurs[i].resize();
        }
        spritesInter[0].dispose();
        spritesInter[1].dispose();

        game.inventory.resize();
        zonePc.resize();
        zoneElec.resize();
        zoneTableau.resize();

        zoomed = false;

        code = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    }

    @Override
    public void show() {
        // demarrer la musique ici
    }

    @Override
    public void render(float delta) {
        // clear l'affichage avec un fond de couleur choisis et de canal alpha choisis
        Gdx.gl.glClearColor(0.1f, 0.2f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update la camera
        camera.update();

        // update la vue les dernieres updates sont au dessus des premieres
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        // affiche le background sur toute la vue
        game.batch.draw(background.getTexture(), 0,0, viewport.getWorldWidth(), viewport.getWorldHeight());
        // affiche une frame d'une animation
        if(background.getTexture().toString().contentEquals("image/tableauElectrique.jpg")){
            for(AnimatedGameObject inter: Interrupteurs){
                inter.drawFix(game.batch);
            }
        }
        game.inventory.drawFix(game.batch);
        game.batch.end();

        // check pour un clic gauche de la souris
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // prend les coordonnees du clic et les convertis en coordonnees du monde
            Vector2 touched = new Vector2();
            touched.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);
            if (!zoomed) {
                if (zoneTableau.contains(touched)) {
                    System.out.println(background.getTexture().toString());
                }

                if (zoneElec.contains(touched) && background.getTexture().toString().matches("image/Amphi137c-piece1_sombre.*")) {
                    //zoomed = true;
                    //background.setRegion(zoomElec);
                    System.out.println(background.getTexture().toString());
                }

                if (zonePc.contains(touched)) {
                    System.out.println(background.getTexture().toString());
                }
            } else if(background.getTexture().toString().contentEquals("image/tableauElectrique.jpg")) {
                if(quitZoom.contains(touched)){
                    zoomed = false;
                    background.setRegion(darkPlace);
                }

                for(AnimatedGameObject inter: Interrupteurs){
                    if(inter.contains(touched)){
                        inter.changeStat(true);
                    }
                }

                int i = 0;
                boolean interupt = false;
                while(i<11 && !interupt){
                    if(Interrupteurs[i].getState() != code[i]){
                        interupt = true;
                    }
                    i++;
                }
                if(i==11){
                    zoomed = false;
                    background.setRegion(brightPlace);
                } else {
                    background.setRegion(darkPlace);
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // resize la vue avec la fenetre
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        // dispose des textures utilises par la scene
        darkPlace.dispose();
        brightPlace.dispose();
        zonePc.dispose();
        zoneElec.dispose();
        zoneTableau.dispose();
        //zoomElec.dispose();
        quitZoom.dispose();
        for(AnimatedGameObject inter : Interrupteurs){
            inter.dispose();
        }
    }
}
