package com.mygdx.escapefromlannioncity.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.utility.AnimatedGameObject;
import com.mygdx.escapefromlannioncity.utility.GameObject;

public class AmphiEnssat implements Screen {

    private final EscapeFromLannionCity game;
    private final Viewport viewport;
    private final Texture background;
    private final GameObject tableauBlanc;
    private final GameObject tableauElectrique;
    private final GameObject zonePc;
    private final AnimatedGameObject ledPc;
    private final AnimatedGameObject pinpon;
    private final OrthographicCamera camera;
    private float elapsed;

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

        // set les textures utilise dans la scene
        background = new Texture(Gdx.files.internal("badlogic.jpg"));

        // initialise un AnimatedGameObject
        ledPc = new AnimatedGameObject(new Texture[]{new Texture(Gdx.files.internal("animation/pinpon/1.jpg")), new Texture(Gdx.files.internal(("animation/pinpon/2.jpg")))});
        ledPc.setCenterPos(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
        ledPc.setSize(64, 64);

        // initialise un AnimatedGameObject, sa duree par frame et son playmode
        pinpon = new AnimatedGameObject(new Texture[]{new Texture(Gdx.files.internal("animation/pinpon/1.jpg")), new Texture(Gdx.files.internal(("animation/pinpon/2.jpg")))}, 0.75f, Animation.PlayMode.LOOP);
        pinpon.setCenterPos(Gdx.graphics.getWidth()*3/4f, Gdx.graphics.getHeight()*3/4f);
        pinpon.setSize(64, 64);

        // initialise les gameObjects avec sprit, position et taille
        tableauBlanc = new GameObject(Gdx.files.internal("image/tableauBlanc.jpg"), viewport.getWorldWidth()/2f, viewport.getWorldHeight()*3/4f, 400, 150);
        tableauElectrique = new GameObject(Gdx.files.internal("image/tableauElectrique.jpg"),32, 200 , 64, 64);
        zonePc = new GameObject(Gdx.files.internal("image/zonePc.jpg"), 200, 200);

        // prend le temps écouler pour les animations
        elapsed = Gdx.graphics.getDeltaTime();
    }

    @Override
    public void show() {
        // demarrer la musique ici
    }

    @Override
    public void render(float delta) {
        // clear l'affichage avec un fond de couleur choisis et de canal alpha choisis
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // incremente le temps ecoule
        elapsed += Gdx.graphics.getDeltaTime();

        // update la camera
        camera.update();

        // update la vue les dernieres updates sont au dessus des premieres
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        // affiche le background sur toute la vue
        game.batch.draw(background, 0,0, viewport.getWorldWidth(), viewport.getWorldHeight());
        // affiche une frame d'une animation
        ledPc.drawFix(game.batch);
        // affiche les textures des objets
        tableauElectrique.drawFix(game.batch);
        tableauBlanc.drawFix(game.batch);
        zonePc.drawFix(game.batch);
        // affiche la frame corespondant au temps ecoule d'une animation
        pinpon.draw(game.batch, elapsed);
        game.batch.end();

        // check pour un clic gauche de la souris
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            // prend les coordonnees du clic et les convertis en coordonnees du monde
            Vector2 touched = new Vector2();
            touched.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);

            // si dans la hitbox, on change l'etat de l'animation
            if(ledPc.contains(touched)){
                ledPc.changeStat(true);
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
        background.dispose();
        ledPc.dispose();
    }
}
