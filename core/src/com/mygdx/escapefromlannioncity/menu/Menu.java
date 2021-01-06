package com.mygdx.escapefromlannioncity.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.utility.GameObject;


public class Menu implements Screen {

    private final EscapeFromLannionCity game;

    private final Viewport viewport;

    private final Sprite background;

    private final GameObject afficherScore;
    private final GameObject sauvegarder;
    private final GameObject reprendre;
    private final GameObject quitter;
    private final GameObject quitterLeJeu;

    public Menu(final EscapeFromLannionCity pGame) {

        this.game = pGame;

        // place une camera dans la vue actuelle de la fenêtre
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);
        viewport.apply(true);

        Texture menuing = new Texture(Gdx.files.internal("image/empty.png"));

        background = new Sprite(menuing);

        afficherScore = new GameObject(Gdx.files.internal("image/Score GL.png"),128, 132, 65, 14);
        sauvegarder = new GameObject(Gdx.files.internal("image/Sauvegarde GL.png"),128, 106, 65, 14);
        reprendre = new GameObject(Gdx.files.internal("image/Reprendre GL.png"),128, 80, 65, 14);
        quitter = new GameObject(Gdx.files.internal("image/Quitter GL.png"),128, 54, 65, 14);
        quitterLeJeu = new GameObject(Gdx.files.internal("image/Quitter le jeu GL.png"),128, 28, 65, 14);

        afficherScore.resize();
        sauvegarder.resize();
        reprendre.resize();
        quitter.resize();
        quitterLeJeu.resize();

    }

    public void goGoGadgettoMenu(Vector2 pTouched, ButtonOpenMenu pButton) {

        if (pButton.isMyButton(pTouched)) {
            //pScreen.hide();
            /*this.render(delta);*/
            game.setScreen(this);

        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Screen screenPrecedent = game.getScreen();

        game.batch.begin();

        /* Affiche le background sur toute la vue */
        game.batch.draw(background.getTexture(), 0,0, viewport.getWorldWidth(), viewport.getWorldHeight());

        /* Contient tout ce qui est affiché quand on ouvre le menu */
        afficherScore.drawFix(game.batch);
        sauvegarder.drawFix(game.batch);
        reprendre.drawFix(game.batch);
        quitter.drawFix(game.batch);
        quitterLeJeu.drawFix(game.batch);

        game.batch.end();

        /* Check pour un clic gauche de la souris */
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // prend les coordonnees du clic et les convertit en coordonnees du monde
            Vector2 touched = new Vector2();
            touched.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);

            if (background.getTexture().toString().matches("image/empty.png")) {

                if (afficherScore.contains(touched)) {
                    System.out.println("J'affiche le score");
                }

                if (sauvegarder.contains(touched)) {
                    System.out.println("Je sauvegarde");
                }

                if (reprendre.contains(touched)) {
                    System.out.println("Reprenons le jeu");
                }

                if (quitter.contains(touched)) {
                    System.out.println("Retournons au menu principal");
                }

                if (quitterLeJeu.contains(touched)) {
                    System.out.println("Ciao bye bye");
                }

            }

        }

    }

    @Override
    public void resize(int width, int height) {
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
        afficherScore.dispose();
        sauvegarder.dispose();
        reprendre.dispose();
        quitter.dispose();
        quitterLeJeu.dispose();
    }
}
