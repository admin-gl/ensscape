package com.mygdx.escapefromlannioncity.identify;


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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.menu.ButtonOpenMenu;
import com.mygdx.escapefromlannioncity.utility.GameObject;


public class Accueil implements Screen {
    private final EscapeFromLannionCity game;

    private final Viewport viewport;

    private final Sprite background;

    //sert à avoir des input et les gèrent
    private final Stage stage;

    //éléments dont on se ressert dans le render du stage
    private final TextButton button;
    private final TextButton button1;
    private final TextButton button2;

    private final GameObject quitterLeJeu;

    public Accueil(final EscapeFromLannionCity pGame) {

        this.game = pGame;
        Texture menuing = new Texture(Gdx.files.internal("image/Utilitaire/blacksquare.png"));

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
        background = new Sprite(menuing);
        table.setBackground(new TextureRegionDrawable(new TextureRegion(menuing)));

        this.button = new TextButton("jouer hors ligne", style);
        table.add(button).center().padBottom(50);
        this.button1 = new TextButton("se connecter", style);
        table.row();
        table.add(button1).center().padBottom(50);
        this.button2 = new TextButton("s'inscrire", style);
        table.row();
        table.add(button2).center().padBottom(50);



        // on la positionne
        table.setFillParent(true);
        table.center();

        // on ajoute la table au stage
        stage.addActor(table);



        //bouton fixe
        quitterLeJeu = new GameObject(Gdx.files.internal("image/Menu/Quitter le jeu GL.png"),128, 28, 65, 14,"");
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
        // le stage peut prendre des inputs et les traiter
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        // Screen screenPrecedent = game.getScreen();

        game.batch.begin();

        /* Le background est affiché par le stage */
       // game.batch.draw(background.getTexture(), 0,0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // on efface le fond d'écran pour afficher sur un écran propre
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // on affiche le stage et lui permet d'agir
        stage.draw();
        stage.act();

        quitterLeJeu.drawFix(game.batch);

        game.batch.end();


        /* Check pour un clic gauche de la souris */
       if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // prend les coordonnees du clic et les convertit en coordonnees du monde
            Vector2 touched = new Vector2();
            touched.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);
            if (background.getTexture().toString().matches("image/Utilitaire/blacksquare.png")) {
                if (quitterLeJeu.contains(touched)) {
                    System.out.println("Ciao bye bye");
                    game.dispose();
                }

                if(button.isPressed()){
                    System.out.println( "go Pseudo");
                    game.setScreen(new Pseudo(game));

                }
                if(button1.isPressed()){
                    System.out.println( "go to se connecter");
                    game.setScreen(new Login(game));

                }
                if(button2.isPressed()){
                    System.out.println( "go to s'inscire");
                    game.setScreen(new Signup(game));

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
        quitterLeJeu.dispose();
    }
}