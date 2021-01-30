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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.menu.ButtonOpenMenu;
import com.mygdx.escapefromlannioncity.menu.MenuPrincipal;

import static com.badlogic.gdx.graphics.Color.WHITE;



/**
 * Ecran du renseignement du pseudo pour jouer
 */
public class Pseudo implements Screen {

    public Music musique;

    private final EscapeFromLannionCity game;

    private final Viewport viewport;

   // private final Sprite background;

    // le stage et la table pour afficher et structurer les éléments
    private final Stage stage;

    // les éléments qu'on modifie avec le rendre
    private final  TextField nameText;
    private final TextButton button;
    private final Label message;
    private final TextButton retour;


    /**
     * Ecran de renseignement du pseudo pour jouer
     * @param pGame partie en cours
     */
    public Pseudo(final EscapeFromLannionCity pGame) {

        this.game = pGame;
        Texture menuing = new Texture(Gdx.files.internal("image/Utilitaire/fondMenuPricipal.jpg"));

        // place une camera dans la vue actuelle de la fenêtre
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);
        viewport.apply(true);


        // création du stage
        this.stage = new Stage(viewport);

        //Le style des éléments
        BitmapFont nbb=game.mainFont.newFontCache().getFont();
        TextField.TextFieldStyle Tstyle= new TextField.TextFieldStyle(nbb,WHITE,
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/cursortxt.png")))),
                null, new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/textinput.png")))));

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(null,null,null,nbb);
        style.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxtup.png"))));
        style.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxt.png"))));
        style.checked = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxt.png"))));
        style.over = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxtup.png"))));
        Label.LabelStyle rr=new Label.LabelStyle(nbb,WHITE);

        //éléments du stage
        Label nameLabel = new Label("Pseudo :", rr);
        this.nameText = new TextField("", Tstyle);
        this.message = new Label("",rr);
        this.button = new TextButton("JOUER", style);
        this.retour = new TextButton("RETOUR", style);


        //le tableau pour la structure spatiale
        Table table1 = new Table();
        table1.add(retour).padLeft(10).padTop(15).maxWidth(150);
        table1.setFillParent(true);
        table1.left().top();

        // on ajoute les éléments au tableau
        Table table = new Table();
        table.add(nameLabel).uniform().padBottom(50);
        table.add(nameText).prefWidth(500).uniform().padBottom(50);
        table.row();
        table.add(button).colspan(2).padBottom(50).minWidth(100);
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
        musique = ((MenuPrincipal)game.menuEtTableau[3]).musique;
        musique.setVolume(game.volume);
        musique.play();
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
                    game.sfxButton.play(game.volume);
                    // on revient sur l'écran d'accueil
                    game.setScreen(game.menuEtTableau[2]);
                    this.dispose();
                }

                if (button.isPressed()) {
                    game.sfxButton.play(game.volume);
                    // l'utilisateur a cliqué sur le bouton

                    //le text rentré est vide
                    if(this.nameText.getText().equals("")){
                        this.message.setText("Choississez un pseudo pour jouer !");

                    //sinon on va à l'écran suivant sans être connecté
                    }else {
                        if(game.isLoggedin==0){
                            //si appelé depuis l'accueil
                            game.pseudo = this.nameText.getText();
                            game.isLoggedin = 2;
                            // on passe à l'écran suivant
                            game.setScreen(game.menuEtTableau[4]);
                            this.dispose();
                        }else {
                            //si appelé depuis le tableau des scores
                            game.pseudo = this.nameText.getText();
                            game.isLoggedin = 2;
                            // on passe à l'écran suivant
                            game.setScreen(game.menuEtTableau[1]);
                            this.dispose();
                        }
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
