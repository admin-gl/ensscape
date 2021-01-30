package com.mygdx.escapefromlannioncity.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.sauvegarde.SAmphiEnssat;
import com.mygdx.escapefromlannioncity.sauvegarde.SParcStAnne;
import com.mygdx.escapefromlannioncity.sauvegarde.SWarp;
import com.mygdx.escapefromlannioncity.score.AffScore;
import com.mygdx.escapefromlannioncity.screens.AmphiEnssat;
import com.mygdx.escapefromlannioncity.siteweb.GetScore;
import com.mygdx.escapefromlannioncity.utility.AnimatedGameObject;
import com.mygdx.escapefromlannioncity.utility.GameObject;


/**
 * Menu principal où le joueur peut commencer une nouvelle partie, continuer,...
 */
public class MenuPrincipal2 implements Screen {
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
    private final TextButton button3;
    private final TextButton button4;

    private final TextButton tutoriel;
    private boolean isTutoOpen;
    private final GameObject quitTuto;
    private final AnimatedGameObject tuto;
    private final GameObject leftarrow;
    private final GameObject rightarrow;


    /**
     * Menu principal où le joueur peut commencer une nouvelle partie, continuer,...
     * @param pGame jeu en cours
     */
    public MenuPrincipal2(final EscapeFromLannionCity pGame) {

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
        style.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("image/Utilitaire/bouttontxt.png"))));
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
        this.button = new TextButton("NOUVELLE PARTIE", style);
        table.add(button).center().padBottom(60).minWidth(500).minHeight(75);
        table.row();
        this.button1 = new TextButton("CONTINUER UNE PARTIE", style);
        table.add(button1).center().padBottom(60).minWidth(500).minHeight(75);
        this.button2 = new TextButton("CONSULTER LES SCORES", style);
        table.row();
        table.add(button2).center().padBottom(60).minWidth(500).minHeight(75);
        this.button3 = new TextButton("CHANGER D'UTILISATEUR", style);
        table.row();
        table.add(button3).center().padBottom(60).minWidth(500).minHeight(75);
        this.button4 = new TextButton("QUITTER LE JEU", style);
        table.row();
        table.add(button4).center().minWidth(500).minHeight(75);

        this.tutoriel = new TextButton("TUTORIEL", style);
        tutoriel.setPosition(10,10);

        // on la positionne
        table.setFillParent(true);
        //table.center();

        // on ajoute la table au stage
        stage.addActor(table);
        stage.addActor(tutoriel);

        isTutoOpen = false;
        quitTuto = new GameObject(Gdx.files.internal("image/Utilitaire/quitZoom.png"), 223, 125, 6, 6, "");
        leftarrow = new GameObject(Gdx.files.internal("image/Utilitaire/quitZoom.png"), 35.5f, 23, 5, 10, "");
        rightarrow = new GameObject(Gdx.files.internal("image/Utilitaire/quitZoom.png"), 219.5f, 23, 5, 10, "");

        quitTuto.resize();
        leftarrow.resize();
        rightarrow.resize();

        Texture[] pagesTuto = new Texture[2];
        for (int i=0;i<2;i++) {
            pagesTuto[i] = new Texture(Gdx.files.internal("animation/Utilitaire/Tutoriel/"+i+".png"));
        }
        tuto = new AnimatedGameObject(pagesTuto);
        tuto.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        tuto.setCenterPos(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
        tuto.setPlayMode(Animation.PlayMode.LOOP);

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


        // on affiche le stage et lui permet d'agir
        stage.draw();
        stage.act();
        if(isTutoOpen){
            tuto.drawFix(game.batch);
        }


        game.batch.end();


        /* Check pour un clic gauche de la souris */
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // prend les coordonnees du clic et les convertit en coordonnees du monde
            Vector2 touched = new Vector2();
            touched.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);
            if(button.isPressed()){
                game.sfxButton.play(game.volume);
                game.inventory.clear();
                // NOUVELLE PARTIE
                game.menuEtTableau[1] = new AmphiEnssat(game, "00:00",0,0);
                game.setScreen(game.menuEtTableau[1]);

            }
            if(!isTutoOpen){
                if(button1.isPressed()){
                    game.sfxButton.play(game.volume);
                    game.inventory.clear();
                    if(!game.menuEtTableau[1].getClass().toString().matches(".*ReviewScore")) {
                        game.menuEtTableau[1].dispose();
                    }
                    // REPRENDRE PARTIE
                    int res =SWarp.isPartie(game.isLoggedin,game.pseudo);
                    if(res==0){
                    }else if(res==1){
                        game.menuEtTableau[1] = SAmphiEnssat.Ouvrir(game);
                        game.setScreen(game.menuEtTableau[1]);
                    }else if(res==2){
                        game.menuEtTableau[1] = SParcStAnne.Ouvrir(game);
                        game.setScreen(game.menuEtTableau[1]);
                    }else if(res==3){
                        game.menuEtTableau[1] = SWarp.Ouvrir(game);
                        game.setScreen(game.menuEtTableau[1]);
                    }
                }
                if(button2.isPressed()){
                    game.sfxButton.play(game.volume);
                    //Score
                    GetScore.StoreScore();
                    AffScore.AffScore();

                }
                if(button3.isPressed()){
                    //return accueil
                    game.sfxButton.play(game.volume);
                    game.isLoggedin = 0;
                    game.setScreen(game.menuEtTableau[2]);

                }
                if(button4.isPressed()){
                    //quitter
                    game.sfxButton.play(game.volume);
                    game.dispose();

                }
                if(tutoriel.isPressed()){
                    game.sfxButton.play(game.volume);
                    isTutoOpen = true;
                }
            } else {
                if(leftarrow.contains(touched)){
                    tuto.changeStat(false);
                } else if(rightarrow.contains(touched)){
                    tuto.changeStat(true);
                } else if(quitTuto.contains(touched)){
                    isTutoOpen = false;
                    if(tuto.getState() % 2 == 1){
                        tuto.changeStat(true);
                    }
                }
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
        tuto.dispose();
        leftarrow.dispose();
        rightarrow.dispose();
    }
}
