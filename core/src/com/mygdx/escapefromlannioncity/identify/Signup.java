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
import com.mygdx.escapefromlannioncity.siteweb.AddJoueur;

import static com.badlogic.gdx.graphics.Color.WHITE;

public class Signup implements Screen{

    public Music musique;

    private final EscapeFromLannionCity game;

    private final Viewport viewport;

    //le stage et la table
    private final Stage stageLogin;

    //les éléments du stage et de la table
    private final TextField pseudoText;
    private final TextField nameText;
    private final TextField MdpText;
    private final TextButton button;
    private final Label message;
    private final TextButton retour;


    public Signup(final EscapeFromLannionCity pGame) {

        this.game = pGame;
        Texture menuing = new Texture(Gdx.files.internal("image/Utilitaire/fondMenuPricipal.jpg"));
        // place une camera dans la vue actuelle de la fenêtre
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);
        viewport.apply(true);




        //création du stage
        this.stageLogin = new Stage(viewport);

        //les éléments de style
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

        // éléments du stage  pour l'inscription
        Label pseudoLabel = new Label("Pseudo :", rr);
        this.pseudoText = new TextField("", Tstyle);
        Label nameLabel = new Label("Email :", rr);
        this.nameText = new TextField("", Tstyle);
        Label addressLabel = new Label("Mot de passe :", rr);
        this.MdpText = new TextField("", Tstyle);
        this.MdpText.setPasswordMode(true);
        this.message = new Label("",rr);
        this.button = new TextButton("S'INSCRIRE", style);
        this.retour = new TextButton("RETOUR", style);

        //le tableau pour la structure spatiale
        Table table1 = new Table();
        table1.add(retour).padLeft(10).padTop(15).maxWidth(150);
        table1.setFillParent(true);
        table1.left().top();

        // tableau pour les structurer dans l'espace
        Table table = new Table();
        table.add(pseudoLabel).uniform().padBottom(20);
        table.add(pseudoText).prefWidth(500).uniform().padBottom(20);
        table.row();
        table.add(nameLabel).padBottom(20);
        table.add(nameText).width(500).padBottom(20);
        table.row();
        table.add(addressLabel).padBottom(50);
        table.add(MdpText).width(500).padBottom(50);
        table.row();
        table.add(button).colspan(2).padBottom(50).minWidth(100);
        table.row();
        table.add(this.message).colspan(2);

        //ajout fond d'ecran
        Image img = new Image(new TextureRegion(menuing));
        img.setFillParent(true);
        stageLogin.addActor(img);

        //position du tableau
        table.setFillParent(true);
        table.center();

        //ajout du tableau au stage
        stageLogin.addActor(table);
        stageLogin.addActor(table1);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stageLogin);
        musique = ((MenuPrincipal)game.menuEtTableau[3]).musique;
        musique.setVolume(game.volume);
        musique.play();

    }

    @Override
    public void render(float delta) {

        // Screen screenPrecedent = game.getScreen();

        game.batch.begin();

        // on nettoie le fonc d'écran
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // on dessine et met en route le stage
        stageLogin.draw();
        stageLogin.act();



        game.batch.end();

        /* Check pour un clic gauche de la souris */
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // prend les coordonnees du clic et les convertit en coordonnees du monde
            Vector2 touched = new Vector2();
            touched.set(Gdx.input.getX(), Gdx.input.getY());
            int res;
            viewport.unproject(touched);

                if(retour.isPressed()){
                    game.sfxButton.play(game.volume);
                    // on revient à l'écran d'accueil si retour
                    game.setScreen(game.menuEtTableau[2]);
                    this.dispose();
                }

                if (button.isPressed()) {
                    game.sfxButton.play(game.volume);
                    //si le joueur appuie sur s'inscrire

                    // il faut que tous les champs soient remplis
                    if(this.pseudoText.getText().equals("") || this.pseudoText.getText().equals("") ||
                            this.MdpText.getText().equals("")){
                        this.message.setText("Tous les champs doivent etre remplis !");

                    }else {
                        // on essaie d'ajouter un joueur
                        res = AddJoueur.addJoueur(this.pseudoText.getText(), this.nameText.getText(), this.MdpText.getText());
                        if (res == 2) { //echec
                            this.message.setText("Une erreur s'est produit !");

                        } else if (res == 1) { //pas d'internet
                            this.message.setText("Verifiez votre connection internet !");

                        } else if (res == 3) { //deja pseudo
                            this.message.setText("Ce pseudo existe deja !");

                        } else if (res == 4) { //deja email
                            this.message.setText("Cet email existe deja !");

                        } else if (res == 0) { //ok
                            if(game.isLoggedin==0){
                                game.pseudo = this.pseudoText.getText();
                                game.isLoggedin = 1;
                                // on passe à l'écran suivant
                                game.setScreen(game.menuEtTableau[4]);
                                this.dispose();
                            }else {
                                game.pseudo = this.pseudoText.getText();
                                game.isLoggedin = 1;
                                // on passe à l'écran suivant
                                game.setScreen(game.menuEtTableau[1]);
                                this.dispose();
                            }


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
        stageLogin.dispose();

    }
}
