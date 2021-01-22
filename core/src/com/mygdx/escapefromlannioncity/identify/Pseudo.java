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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.menu.ButtonOpenMenu;
import com.mygdx.escapefromlannioncity.utility.GameObject;

import static com.badlogic.gdx.graphics.Color.WHITE;

public class Pseudo implements Screen {
    private final EscapeFromLannionCity game;

    private final Viewport viewport;

    private final Sprite background;

    // le stage et la table pour afficher et structurer les éléments
    private final Stage stage;

    // les éléments qu'on modifie avec le rendre
    private final  TextField nameText;
    private final TextButton button;
    private final Label message;

    //pour retourner à l'écran d'accueil
    private final GameObject Retour;

    //peut être à enlever
    private final GameObject quitterLeJeu;


    public Pseudo(final EscapeFromLannionCity pGame) {

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
        this.button = new TextButton("jouer", style);


        // on ajoute les éléments au tableau
        Table table = new Table();
        table.add(nameLabel).uniform().padBottom(20);
        table.add(nameText).prefWidth(500).uniform().padBottom(20);
        table.row();
        table.add(button).colspan(2).padBottom(30);
        table.row();
        table.add(this.message).colspan(2);

        //ajout du background
        background = new Sprite(menuing);
        table.setBackground(new TextureRegionDrawable(new TextureRegion(menuing)));

        //position du tableau
        table.setFillParent(true);
        table.center();

        // on ajoute le tableau au stage
        stage.addActor(table);


        //bouton pour revenir à l'accueil
        Retour = new GameObject(Gdx.files.internal("image/Menu/Retour GL.png"),128, 132, 65, 14, "");
        //pour quitter, peut etre à enlever
        quitterLeJeu = new GameObject(Gdx.files.internal("image/Menu/Quitter le jeu GL.png"),128, 28, 65, 14,"");

        quitterLeJeu.resize();
        Retour.resize();

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

        // on affiche lesboutons fixes
        Retour.drawFix(game.batch);
        quitterLeJeu.drawFix(game.batch);


        game.batch.end();

        /* Check pour un clic gauche de la souris */
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // prend les coordonnees du clic et les convertit en coordonnees du monde
            Vector2 touched = new Vector2();
            touched.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);
            if (background.getTexture().toString().matches("image/Utilitaire/blacksquare.png")) {
                if (quitterLeJeu.contains(touched)) {
                    System.out.println("Ciao bye bye");
                    game.dispose();
                }

                if (Retour.contains(touched)) {
                    // on revient sur l'écran d'accueil
                    game.setScreen(game.menuEtTableau[2]);
                }

                if (button.isPressed()) {
                    // l'utilisateur a cliqué sur le bouton
                    System.out.println(this.nameText.getText());

                    //le text rentré est vide
                    if(this.nameText.getText().equals("")){
                        this.message.setText("Choississez un pseudo pour jouer !");

                    //sinon on va à l'écran suivant sans être connecté
                    }else {
                        game.pseudo = this.nameText.getText();

                        // on passe à l'écran suivant
                        game.setScreen(game.menuEtTableau[1]);
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
        Retour.dispose();
        quitterLeJeu.dispose();
    }
}
