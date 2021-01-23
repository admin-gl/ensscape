package com.mygdx.escapefromlannioncity.score;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.menu.ButtonOpenMenu;

import java.time.LocalDate;

import static com.badlogic.gdx.graphics.Color.WHITE;

public class ReviewScore implements Screen {
    private final EscapeFromLannionCity game;

    private final Viewport viewport;

    // le stage et la table pour afficher et structurer les éléments
    private final Stage stage;

    // les éléments qu'on modifie avec le rendre
    private final TextButton button;
    private final TextButton retour;




    public ReviewScore(final EscapeFromLannionCity pGame) {

        this.game = pGame;
        Texture menuing = new Texture(Gdx.files.internal("image/Utilitaire/blacksquare.png"));

        // place une camera dans la vue actuelle de la fenêtre
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);
        viewport.apply(true);


        // création du stage
        this.stage = new Stage(viewport);

        //Le style des éléments
        BitmapFont nbb=game.mainFont.newFontCache().getFont();

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(null,null,null,nbb);
        style.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxt.png"))));
        style.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxt.png"))));
        style.checked = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxt.png"))));
        style.over = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxt.png"))));
        Label.LabelStyle rr=new Label.LabelStyle(nbb,WHITE);

        Score score = new Score(game.pseudo, 23, 2, 3, 1, LocalDate.now());
        //éléments du stage
        Label titleLabel = new Label("BRAVO "+score.getPseudo()+" !", rr);
        Label bonusLabel = new Label("Bonus :", rr);
        Label bonusVLabel = new Label(Integer.toString(2), rr);
        Label indiceLabel = new Label("Indices :", rr);
        Label indiceVLabel = new Label(Integer.toString(1), rr);
        Label tempsLabel = new Label("Temps :", rr);
        Label tempsVLabel = new Label(score.getTemps(), rr);
        Label scoreLabel = new Label("Score :", rr);
        Label scoreVLabel = new Label(score.getScore(), rr);
        this.button = new TextButton("AJOUTER AU TABLEAU DES SCORES", style);
        this.retour = new TextButton("MENU PRINCIPAL", style);


        //le tableau pour la structure spatiale
        Table table1 = new Table();
        table1.add(retour).padLeft(10).padTop(15);
        table1.setFillParent(true);
        table1.left().top();

        // on ajoute les éléments au tableau
        Table table = new Table();
        table.add(titleLabel).padBottom(50);
        table.row();
        table.add(bonusLabel).uniform().padRight(10).padBottom(20);
        table.add(bonusVLabel).padBottom(20);
        table.row();
        table.add(indiceLabel).uniform().padRight(10).padBottom(20);
        table.add(indiceVLabel).padBottom(20);
        table.row();
        table.add(tempsLabel).uniform().padRight(10).padBottom(20);
        table.add(tempsVLabel).padBottom(20);
        table.row();
        table.add(scoreLabel).uniform().padRight(10).padBottom(50);
        table.add(scoreVLabel).padBottom(50);
        table.row();
        table.add(button).colspan(2).minWidth(100);


        //ajout du background
        Image img = new Image(new TextureRegion(menuing));
        img.setFillParent(true);
        stage.addActor(img);

        //position du tableau
        table.setFillParent(true);
        table.center();

        // on ajoute le tableau au stage
        stage.addActor(table);
        stage.addActor(table1);



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
        // le stage peut gérer les inputs
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        // Screen screenPrecedent = game.getScreen();

        game.batch.begin();

        //  game.batch.draw(background.getTexture(), 0,0, viewport.getWorldWidth(), viewport.getWorldHeight());

        //on efface ce qu'il y avait sur l'écran avant
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // on afffiche et démarre le stage
        stage.draw();
        stage.act();


        game.batch.end();

        /* Check pour un clic gauche de la souris */
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // prend les coordonnees du clic et les convertit en coordonnees du monde
            Vector2 touched = new Vector2();
            touched.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);

                if(retour.isPressed()){
                    // on revient sur l'écran d'accueil
                    game.setScreen(game.menuEtTableau[0]);
                }

                if (button.isPressed()) {


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
        stage.dispose();

    }
}
