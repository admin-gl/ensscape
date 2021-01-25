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
import com.mygdx.escapefromlannioncity.sauvegarde.SAmphiEnssat;
import com.mygdx.escapefromlannioncity.sauvegarde.SParcStAnne;
import com.mygdx.escapefromlannioncity.sauvegarde.SWarp;
import com.mygdx.escapefromlannioncity.score.AffScore;
import com.mygdx.escapefromlannioncity.score.ReviewScore;
import com.mygdx.escapefromlannioncity.screens.UI;
import com.mygdx.escapefromlannioncity.siteweb.GetScore;
import com.mygdx.escapefromlannioncity.utility.ChangingCursor;
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
    private final GameObject setterVolume;
    private final GameObject sliderVolume;
    private final GameObject imageVolume;

    private final ChangingCursor cursor;

    public Menu(final EscapeFromLannionCity pGame) {

        this.game = pGame;

        this.cursor = new ChangingCursor(pGame);

        // place une camera dans la vue actuelle de la fenêtre
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);
        viewport.apply(true);

        Texture menuing = new Texture(Gdx.files.internal("image/Utilitaire/blacksquare.png"));

        background = new Sprite(menuing);

        reprendre = new GameObject(Gdx.files.internal("image/Menu/Reprendre GL.png"),128, 132, 65, 14, "");
        sauvegarder = new GameObject(Gdx.files.internal("image/Menu/Sauvegarde GL.png"),128, 106, 65, 14,"");
        afficherScore = new GameObject(Gdx.files.internal("image/Menu/Score GL.png"),128, 80, 65, 14,"");
        quitter = new GameObject(Gdx.files.internal("image/Menu/Quitter GL.png"),128, 54, 65, 14,"");
        quitterLeJeu = new GameObject(Gdx.files.internal("image/Menu/Quitter le jeu GL.png"),128, 28, 65, 14,"");

        imageVolume = new GameObject(Gdx.files.internal("image/Menu/Volume.png"),7, 106,14 , 14,"");
        sliderVolume = new GameObject(Gdx.files.internal("image/Menu/slider_Volume.png"),40, 106,50 , 14,"");
        setterVolume = new GameObject(Gdx.files.internal("image/Menu/Selecteur_Volume.png"),15+game.volume*50, 106,3 , 4 ,"");

        afficherScore.resize();
        sauvegarder.resize();
        reprendre.resize();
        quitter.resize();
        quitterLeJeu.resize();
        setterVolume.resize();
        sliderVolume.resize();
        imageVolume.resize();

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
        background.setRegion(((UI)game.menuEtTableau[1]).background.getTexture());
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
        sliderVolume.drawFix(game.batch);
        imageVolume.drawFix(game.batch);
        setterVolume.drawFix(game.batch);

        game.batch.end();

        /* Check pour un clic gauche de la souris */
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // prend les coordonnees du clic et les convertit en coordonnees du monde
            Vector2 touched = new Vector2();
            touched.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);
                if (afficherScore.contains(touched)) {
                    System.out.println("J'affiche le score");
                    GetScore.StoreScore();
                    AffScore.AffScore();

                }

                if (sauvegarder.contains(touched)) {
                    System.out.println("Je sauvegarde");
                    if(game.menuEtTableau[1].getClass().toString().matches(".*AmphiEnssat")) {
                        SAmphiEnssat.Enregistrer(game.menuEtTableau[1]);
                    }else if(game.menuEtTableau[1].getClass().toString().matches(".*ParcStAnne")){
                        SParcStAnne.Enregistrer(game.menuEtTableau[1]);
                    }else if(game.menuEtTableau[1].getClass().toString().matches(".*Warp")){
                        System.out.println("here");
                        SWarp.Enregistrer(game.menuEtTableau[1]);
                    }
                }

                if (reprendre.contains(touched)) {
                    System.out.println("Reprenons le jeu");
                    game.setScreen(game.menuEtTableau[1]);
                }

                if (quitter.contains(touched)) {
                    System.out.println("Retournons au menu principal");
                    game.menuEtTableau[1].dispose();
                    game.setScreen(game.menuEtTableau[4]);
                }

                if (quitterLeJeu.contains(touched)) {
                    System.out.println("Ciao bye bye");
                    game.dispose();
                }

        }
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            Vector2 touched = new Vector2();
            touched.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);

            if(sliderVolume.contains(touched) && sliderVolume.contains(setterVolume.getCenterPos())){
                setterVolume.setCenterPos(touched.x, setterVolume.getCenterPos().y);
                game.volume = ((setterVolume.getCenterPos().x)/setterVolume.getScaleX() - 15)/50;
            } else if(setterVolume.getCenterPos().x > sliderVolume.getX()+sliderVolume.getWidth()){
                setterVolume.setCenterPos(sliderVolume.getX()+sliderVolume.getWidth(), setterVolume.getCenterPos().y);
                game.volume = 1;
            } else if(setterVolume.getCenterPos().x < sliderVolume.getX()){
                setterVolume.setCenterPos(sliderVolume.getX(), setterVolume.getCenterPos().y);
                game.volume = 0;
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
        background.setRegion(((UI)game.menuEtTableau[1]).background.getTexture());
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
