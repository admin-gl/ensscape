package com.mygdx.escapefromlannioncity.identify;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.mygdx.escapefromlannioncity.menu.MenuPrincipal;

import static com.badlogic.gdx.graphics.Color.WHITE;

/**
 * Fenetre de départ du jeu où le joueur peut choisir s'il se connect,
 * s'inscrit ou joue avec un pseudo, cette dernirer option n'étant pas proposée
 * si cette fenetre est appelé depuis le tableau des scores (ReviewScore)
 */
public class Accueil implements Screen {

    public Music musique;

    private final EscapeFromLannionCity game;

    private final Viewport viewport;

    //private final Sprite background;

    //sert à avoir des input et les gèrent
    private final Stage stage;

    //éléments dont on se ressert dans le render du stage
    private final TextButton button;
    private final TextButton button1;
    private final TextButton button2;

    /**
     * Constructeur de l'écran avec le jeu en cours
     * @param pGame jeu en cours
     */
    public Accueil(final EscapeFromLannionCity pGame) {

        this.game = pGame;
        Texture menuing = new Texture(Gdx.files.internal("image/Utilitaire/fondMenuPricipal.jpg"));

        // place une camera dans la vue actuelle de la fenêtre
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);
        viewport.apply(true);


        // on créé le stage
        this.stage = new Stage(viewport);


        // Le style des boutons
        BitmapFont nbb=game.mainFont.newFontCache().getFont();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(null,null,null,nbb);
        style.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxtup.png"))));
        style.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxt.png"))));
        style.checked = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxt.png"))));
        style.over = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxtup.png"))));

        // la table qui structure la position des éléments du tableau
        //gère la répartition des éléments dans le stage
        Table table = new Table();

        //ajout du background
        Image img = new Image(new TextureRegion(menuing));
        img.setFillParent(true);
        stage.addActor(img);

        // au démarrage on peut entrer un pseudo pour jouer hors ligne
        this.button = new TextButton("JOUER HORS LIGNE", style);
        table.add(button).center().padBottom(60).minWidth(350).minHeight(75);
        table.row();
        this.button1 = new TextButton("SE CONNECTER", style);
        table.add(button1).center().padBottom(60).minWidth(350).minHeight(75);
        this.button2 = new TextButton("S'INSCRIRE", style);
        table.row();
        table.add(button2).center().minWidth(350).minHeight(75);



        // on la positionne
        table.setFillParent(true);
        //table.center();

        // on ajoute la table au stage
        stage.addActor(table);

    }



    @Override
    public void show() {
        // le stage peut prendre des inputs et les traiter
        Gdx.input.setInputProcessor(stage);
        musique = ((MenuPrincipal)game.menuEtTableau[3]).musique;
        musique.setVolume(game.volume);
        musique.play();
    }

    @Override
    public void render(float delta) {

        // Screen screenPrecedent = game.getScreen();

        game.batch.begin();

        // on efface le fond d'écran pour afficher sur un écran propre
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //game.batch.draw(background.getTexture(), 0,0, viewport.getWorldWidth(), viewport.getWorldHeight());
        if(game.isLoggedin==2){
            this.button.setVisible(false);
            this.button.setDisabled(true);
        }
        // on affiche le stage et lui permet d'agir
        stage.draw();
        stage.act();


       game.batch.end();


        /* Check pour un clic gauche de la souris */
       if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // prend les coordonnees du clic et les convertit en coordonnees du monde
            Vector2 touched = new Vector2();
            touched.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);
                if(button.isPressed()){
                    game.sfxButton.play(game.volume);
                    game.setScreen(new Pseudo(game));

                }
                if(button1.isPressed()){
                    game.sfxButton.play(game.volume);
                    game.setScreen(new Login(game));

                }
                if(button2.isPressed()){
                    game.sfxButton.play(game.volume);
                    game.setScreen(new Signup(game));

                }



        }


    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height);
        stage.getViewport().update(width,height,true);


      /*  table.invalidateHierarchy();
        table.setSize(WIDTH, HEIGHT);*/

    }

    @Override
    public void pause() {
        musique.pause();
    }

    @Override
    public void resume() {
        musique.setVolume(game.volume);
        musique.play();
    }

    @Override
    public void hide() {
        musique.pause();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}