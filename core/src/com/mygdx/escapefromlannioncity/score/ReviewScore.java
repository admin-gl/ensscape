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
import com.mygdx.escapefromlannioncity.siteweb.AddScore;

import java.time.LocalDate;

import static com.badlogic.gdx.graphics.Color.WHITE;

public class ReviewScore implements Screen {
    private final EscapeFromLannionCity game;

    private final Viewport viewport;

    // le stage et la table pour afficher et structurer les éléments
    private final Stage stage;

    private final Score score;

    // les éléments qu'on modifie avec le rendre
    private final TextButton button;
    private final TextButton retour;
    private final Label message;
    private final Label titleLabel;


    public ReviewScore(final EscapeFromLannionCity pGame, String timeTotal, int bonus, int usedHint) {

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
        style.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxtup.png"))));
        style.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxt.png"))));
        style.checked = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxt.png"))));
        style.over = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxtup.png"))));
        Label.LabelStyle rr=new Label.LabelStyle(nbb,WHITE);

        this.score = new Score(game.pseudo, timeTotal, bonus, usedHint, LocalDate.now());
        //éléments du stage
        this.titleLabel = new Label("Bravo "+score.getPseudo()+" !", rr);
        Label bonusLabel = new Label("Bonus :", rr);
        Label bonusVLabel = new Label(Integer.toString(bonus), rr);
        Label indiceLabel = new Label("Indices :", rr);
        Label indiceVLabel = new Label(Integer.toString(usedHint), rr);
        Label tempsLabel = new Label("Temps :", rr);
        Label tempsVLabel = new Label(score.getTemps(), rr);
        Label scoreLabel = new Label("Score :", rr);
        Label scoreVLabel = new Label(score.getScore(), rr);
        if(game.isLoggedin==1) {
            this.button = new TextButton("AJOUTER AU CLASSEMENT GENERAL", style);
        }else{
            this.button = new TextButton("SE CONNECTER POUR AJOUTER AU CLASSEMENT", style);
        }
        this.retour = new TextButton("MENU PRINCIPAL", style);
        this.message = new Label("",rr);


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
        table.add(indiceLabel).padBottom(20);
        table.add(indiceVLabel).padBottom(20);
        table.row();
        table.add(tempsLabel).padBottom(20);
        table.add(tempsVLabel).padBottom(20);
        table.row();
        table.add(scoreLabel).padBottom(50);
        table.add(scoreVLabel).padBottom(50);
        table.row();
        table.add(button).colspan(2).minWidth(100).padBottom(20);
        table.row();
        table.add(this.message).colspan(2);

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


    @Override
    public void show() {
        // le stage peut gérer les inputs
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        this.score.setPseudo(game.pseudo);
        this.titleLabel.setText("Bravo "+score.getPseudo()+" !");

        // Screen screenPrecedent = game.getScreen();

        game.batch.begin();

        //  game.batch.draw(background.getTexture(), 0,0, viewport.getWorldWidth(), viewport.getWorldHeight());

        //on efface ce qu'il y avait sur l'écran avant
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(game.isLoggedin==1) {
            this.button.setText("AJOUTER AU CLASSEMENT GENERAL");
        }
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
                Score.AddScoreLoc(this.score);
                this.dispose();
                // on revient au menu principal
                game.setScreen(game.menuEtTableau[4]);
            }

            if (button.isPressed()) {
                if(game.isLoggedin==1){
                    int res = AddScore.addScore(this.score);
                    if(res==0){
                        this.message.setText("Votre score a ete ajoute au classement !");
                        button.setDisabled(true);
                    }else if(res==1){
                        this.message.setText("Vous n'etes pas connecte a internet !");
                    }else{
                        this.message.setText("Desole, une erreur s'est produit !");
                    }
                }else if(game.isLoggedin==2){
                    game.setScreen(game.menuEtTableau[2]);
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
        stage.dispose();

    }
}
