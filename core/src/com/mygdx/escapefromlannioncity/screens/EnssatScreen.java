package com.mygdx.escapefromlannioncity.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import org.jetbrains.annotations.NotNull;

public class EnssatScreen implements Screen {

    final EscapeFromLannionCity game;
    private Viewport viewport;
    private Texture background;
    private Animation objet;
    private Texture[] keyFrames;
    private Rectangle hint;
    private OrthographicCamera camera;
    private float elapsed;

    public EnssatScreen(@NotNull final EscapeFromLannionCity game){
        this.game = game;

        // peut etre inutile, a verifier...
        Screen precedent = game.getScreen();
        if(precedent != null) precedent.dispose();

        // set les textures utilise dans la scene
        keyFrames = new Texture[]{new Texture(Gdx.files.internal("pinpon1.jpg")), new Texture(Gdx.files.internal(("pinpon2.jpg")))};
        background = new Texture(Gdx.files.internal("badlogic.jpg"));

        // si il y a des animations, set leurs keyframes et leur mode de rotation
        objet = new Animation(0.5f, keyFrames);
        objet.setPlayMode(Animation.PlayMode.LOOP);

        // place une camera dans la vue actuelle de la fenêtre
        camera = new OrthographicCamera();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);
        viewport.apply(true);

        // creer une hitbox de forme rectangulaire
        hint = new Rectangle();
        hint.height = 64;
        hint.width = 64;
        hint.setCenter(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);

        // prend le temps écouler pour les animations
        elapsed = Gdx.graphics.getDeltaTime();

    }

    @Override
    public void show() {

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

        // update la vue
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        // affiche le background sur toute la vue
        game.batch.draw(background, 0,0, viewport.getWorldWidth(), viewport.getWorldHeight());
        // place la texture actuelle de l'animation à l'emplacement de la hitbox déclare plus tot
        game.batch.draw((Texture)objet.getKeyFrame(elapsed), hint.x, hint.y, hint.getWidth(), hint.getHeight());
        game.batch.end();

        // check pour un clic maintenu gauche de la souris
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            // prend les coordonnees du clic et les convertis en coordonnees du monde
            Vector2 touched = new Vector2();
            touched.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);

            // si dans la hitbox, deplace la hitbox (et donc l'objet)
            if(hint.contains(touched)){
                hint.setCenter(touched);
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
        for(int i=0; i<keyFrames.length;i++){
            keyFrames[i].dispose();
        }
    }
}
