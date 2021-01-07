package com.mygdx.escapefromlannioncity.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.menu.ButtonOpenMenu;         // Bouton de Menu
import com.mygdx.escapefromlannioncity.utility.AnimatedGameObject;
import com.mygdx.escapefromlannioncity.utility.GameObject;

public class AmphiEnssat implements Screen {

    private final EscapeFromLannionCity game;

    private final Viewport viewport;

    private final Sprite background;

    private final Texture brightPlace;
    private final Texture darkPlace;
    private final Texture zoomElecClair;
    private final Texture zoomElecSombre;
    private final Texture zoomOrdiAllume;
    private final Texture zoomOrdiEteint;
    private final Texture zoomTableau;
    private final Texture amphiPorte;
    private final Texture fondAmphi;
    private final Texture fondAmphiSansCarte;



    private final GameObject zonePc;
    private final GameObject zoneTableau;
    private final GameObject zoneElec;
    private final GameObject quitZoom;
    private final GameObject zoneGauche;
    private final GameObject zoneDroite;
    private final GameObject zoneCarte;
    private final GameObject carteEtu;
    private final GameObject zoneBadge;
    private final GameObject porteSortie;
    private final GameObject[] zoneInv = new GameObject[10];


    private final ButtonOpenMenu buttonMenu;  /* Concerne le Bouton d'affichage du Menu */

    private final AnimatedGameObject[] Interrupteurs;
    private final AnimatedGameObject[] Numbers;

    private final int[] code;
    private final int[] codeOrdi;

    private final OrthographicCamera camera;

    private boolean zoomed;
    private boolean lights;
    private boolean zoomObj;
    private boolean carteValide;
    private boolean porteVerr;
    private int pos;

    private final Music musique;

    public AmphiEnssat(final EscapeFromLannionCity game) {

        this.game = game;

        musique = Gdx.audio.newMusic(Gdx.files.internal("music/enigme_1.wav"));
        musique.setLooping(true);

        // place une camera dans la vue actuelle de la fenêtre
        camera = new OrthographicCamera();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);
        viewport.apply(true);

        darkPlace = new Texture(Gdx.files.internal("image/Amphi_Enssat/Amphi137c-piece1_sombre.jpg"));
        brightPlace = new Texture(Gdx.files.internal("image/Amphi_Enssat/Amphi137c-piece1.png"));
        zoomElecSombre = new Texture(Gdx.files.internal("image/Amphi_Enssat/tableauelecsombre.png"));
        zoomElecClair =  new Texture(Gdx.files.internal("image/Amphi_Enssat/tableauelec.png"));
        zoomOrdiAllume = new Texture(Gdx.files.internal("image/Amphi_Enssat/ordiallume.png"));
        zoomOrdiEteint = new Texture(Gdx.files.internal("image/Amphi_Enssat/ordipasallume.png"));
        zoomTableau = new Texture(Gdx.files.internal("image/Amphi_Enssat/zoomtableau.png"));
        amphiPorte = new Texture(Gdx.files.internal("image/Amphi_Enssat/amphiporte.png"));
        fondAmphi = new Texture(Gdx.files.internal("image/Amphi_Enssat/fondAmphi.png"));
        fondAmphiSansCarte = new Texture(Gdx.files.internal("image/Amphi_Enssat/fondamphi_sanscarte.png"));

        background = new Sprite(darkPlace);

        zoneElec = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 8, 82, 16, 22);
        zoneTableau = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 156, 87 ,156 , 46);
        zonePc = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"),48, 63, 54, 32);
        zoneDroite = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"),250, 0, 36, 70);
        zoneGauche = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"),0, 0, 36, 70);
        zoneCarte = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 48, 33, 54, 32);
        carteEtu = new GameObject(Gdx.files.internal("image/Amphi_Enssat/carteEtu.png"), 10, 10, 10,9);
        quitZoom = new GameObject(Gdx.files.internal("image/Utilitaire/quitZoom.png"), 222, 126, 5, 5);
        zoneBadge = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 230, 63, 15, 15);
        porteSortie = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 200, 71, 30, 140);
        for (int i = 0; i<10 ; i++){
            zoneInv[i] = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 254+i*44, 33, 40, 40);
        }


        /* Initialise le Bouton de Menu */
        buttonMenu = new ButtonOpenMenu();

        Interrupteurs = new AnimatedGameObject[11];
        Texture[] spritesInter = new Texture[2];
        spritesInter[0] = new Texture(Gdx.files.internal("animation/Amphi_Enssat/interrupteur/1.png"));
        spritesInter[1] = new Texture(Gdx.files.internal("animation/Amphi_Enssat/interrupteur/2.png"));
        for(int i=0; i<11; i++){
            Interrupteurs[i]= new AnimatedGameObject(spritesInter);
            Interrupteurs[i].setPlayMode(Animation.PlayMode.LOOP);
            Interrupteurs[i].setSize(13, 37);
            Interrupteurs[i].setCenterPos( 57+i*14, 76);
            Interrupteurs[i].resize();
        }

        Numbers = new AnimatedGameObject[4];

        Texture[] spritesNumbers = new Texture[9];
        spritesNumbers[0] = new Texture(Gdx.files.internal("animation/Amphi_Enssat/number/1.png"));
        spritesNumbers[1] = new Texture(Gdx.files.internal("animation/Amphi_Enssat/number/2.png"));
        spritesNumbers[2] = new Texture(Gdx.files.internal("animation/Amphi_Enssat/number/3.png"));
        spritesNumbers[3] = new Texture(Gdx.files.internal("animation/Amphi_Enssat/number/4.png"));
        spritesNumbers[4] = new Texture(Gdx.files.internal("animation/Amphi_Enssat/number/5.png"));
        spritesNumbers[5] = new Texture(Gdx.files.internal("animation/Amphi_Enssat/number/6.png"));
        spritesNumbers[6] = new Texture(Gdx.files.internal("animation/Amphi_Enssat/number/7.png"));
        spritesNumbers[7] = new Texture(Gdx.files.internal("animation/Amphi_Enssat/number/8.png"));
        spritesNumbers[8] = new Texture(Gdx.files.internal("animation/Amphi_Enssat/number/9.png"));
        for (int i = 0; i<4 ; i++){
            Numbers[i] = new AnimatedGameObject(spritesNumbers);
            Numbers[i].setPlayMode(Animation.PlayMode.LOOP);
            Numbers[i].setSize(12, 11);
            Numbers[i].setCenterPos(75+i*16, 72);
            Numbers[i].resize();
        }

        zonePc.resize();
        zoneElec.resize();
        zoneTableau.resize();
        quitZoom.resize();
        zoneDroite.resize();
        zoneGauche.resize();
        zoneCarte.resize();
        carteEtu.resize();
        zoneBadge.resize();
        porteSortie.resize();

        zoomed = false;
        zoomObj = false;
        lights = false;
        carteValide = false;
        porteVerr = true;
        code = new int[]{1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0};
        codeOrdi = new int[]{3, 1, 2, 6};
        pos = 0;

    }

    @Override
    public void show() {
        musique.setVolume(game.volume);
        musique.play();
    }

    @Override
    public void render(float delta) {
        // clear l'affichage avec un fond de couleur choisis et de canal alpha choisis
        Gdx.gl.glClearColor(0.1f, 0.2f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update la camera
        camera.update();

        // update la vue les dernieres updates sont au dessus des premieres
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        // affiche le background sur toute la vue
        game.batch.draw(background.getTexture(), 0,0, viewport.getWorldWidth(), viewport.getWorldHeight());
        /* Affiche le bouton "flottant" de Menu */
        buttonMenu.initButtonMenu(game);
        if(background.getTexture().toString().contentEquals("image/Amphi_Enssat/tableauelec.png") || background.getTexture().toString().contentEquals("image/tableauelecsombre.png")) {
            //quitZoom.drawFix(game.batch); //plus besoin de ça si la croix est dessinée sur le bkg

            for (AnimatedGameObject inter : Interrupteurs) {
                inter.drawFix(game.batch);
            }
        }

        if(background.getTexture().toString().contentEquals("image/Amphi_Enssat/ordiallume.png")){
            for(AnimatedGameObject num: Numbers){
                num.drawFix(game.batch);
            }
        }
        game.inventory.drawFix(game.batch);
        if(game.inventory.hasIn(carteEtu)){
            carteEtu.drawFix(game.batch);
        }




        // check pour un clic gauche de la souris
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // prend les coordonnees du clic et les convertis en coordonnees du monde
            Vector2 touched = new Vector2();
            touched.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);

            /* Lance le Menu si on clique sur le Bouton correspondant */
            if(buttonMenu.contains(touched)){
                game.setScreen(game.menuEtTableau[0]);
            }

            if (!zoomed) {
                if (zoneTableau.contains(touched) && background.getTexture().toString().matches("image/Amphi_Enssat/Amphi137c.piece1.*") && lights) {
                    zoomed = true;
                    background.setRegion(zoomTableau);
                } else if (zoneTableau.contains(touched) && !lights){
                    System.out.println("On ne voit rien");
                }

                if (zoneElec.contains(touched) && background.getTexture().toString().matches("image/Amphi_Enssat/Amphi137c-piece1_sombre.*")) {
                    zoomed = true;
                    background.setRegion(zoomElecSombre);
                }
                if (zoneElec.contains(touched) && background.getTexture().toString().matches("image/Amphi_Enssat/Amphi137c-piece1.*")) {
                    zoomed = true;
                    background.setRegion(zoomElecClair);
                }

                if (zonePc.contains(touched) && background.getTexture().toString().matches("image/Amphi_Enssat/Amphi137c-piece1_sombre.*")) {
                    zoomed = true;
                    background.setRegion(zoomOrdiEteint);
                } else if (zonePc.contains(touched) && background.getTexture().toString().matches("image/Amphi_Enssat/Amphi137c-piece1.*")) {
                    zoomed = true;
                    background.setRegion(zoomOrdiAllume);
                }

                if (zoneCarte.contains(touched) && background.getTexture().toString().matches("image/Amphi_Enssat/fondAmphi.*")){
                    background.setRegion(fondAmphiSansCarte);
                    game.inventory.add(carteEtu);
                }

                if ((zoneDroite.contains(touched) || zoneGauche.contains(touched)) && !lights){
                    System.out.println("Il fait trop noir");
                }
                if (zoneGauche.contains(touched) && lights && pos == 1 && !game.inventory.hasIn(carteEtu)){
                    background.setRegion(fondAmphi);
                    pos = 2;
                } else if (zoneGauche.contains(touched) && lights && pos == 1 && game.inventory.hasIn(carteEtu)){
                    background.setRegion(fondAmphiSansCarte);
                    pos = 2;
                }
                if (zoneGauche.contains(touched) && lights && pos == 0){
                    background.setRegion(amphiPorte);
                    pos = 1;
                }

                if (zoneDroite.contains(touched) && lights && pos == 1) {
                    background.setRegion(brightPlace);
                    pos = 0;
                }
                if (zoneDroite.contains(touched) && lights && pos == 2) {
                    background.setRegion(amphiPorte);
                    pos = 1;
                }
                if (zoneInv[0].contains(touched) && !zoomObj && game.inventory.hasIn(carteEtu)){
                    carteEtu.zoom(game.batch);
                    zoomObj = true;
                } else if (zoneInv[0].contains(touched) && zoomObj && game.inventory.hasIn(carteEtu)){
                    zoomObj = false;
                    carteEtu.unzoom(game.batch);
                    //background.setRegion(fondAmphiSansCarte);
                }
                if (carteValide && game.inventory.hasIn(carteEtu) && zoneBadge.contains(touched)){
                    porteVerr = false;
                    game.inventory.remove(carteEtu);
                }
                if (!porteVerr && porteSortie.contains(touched)){
                    System.out.println("Fin Niveau 1");
                    game.dispose();
                }

            } else if(background.getTexture().toString().matches("image/Amphi_Enssat/tableauelec.*")) {

                for(AnimatedGameObject inter: Interrupteurs){
                    if(inter.contains(touched)){
                        inter.changeStat(true);
                    }
                }

                int i = 0;
                boolean interupt = false;
                while(i<11 && !interupt){
                    if(Interrupteurs[i].getState() != code[i]){
                        interupt = true;
                    }
                    i++;
                }
                zoomed = true;
                if(i==11 && !interupt){
                    lights = true;
                    background.setRegion(zoomElecClair);
                } else {
                    lights = false;
                    background.setRegion(zoomElecSombre);
                }

                if(quitZoom.contains(touched) && !lights){
                    zoomed = false;
                    background.setRegion(darkPlace);
                } else if(quitZoom.contains(touched) && lights){
                    zoomed = false;
                    background.setRegion(brightPlace);
                }


            }


            else if(background.getTexture().toString().matches("image/Amphi_Enssat/ordipasallume.*")) {

                if(quitZoom.contains(touched) && !lights){
                    zoomed = false;
                    background.setRegion(darkPlace);
                } else if(quitZoom.contains(touched) && lights){
                    zoomed = false;
                    background.setRegion(brightPlace);
                }
            }
            else if(background.getTexture().toString().matches("image/Amphi_Enssat/ordiallume.*")) {

                for(AnimatedGameObject num: Numbers){
                    if(num.contains(touched)){
                        num.changeStat(true);
                        System.out.println(num.getState());
                    }
                }

                if (Numbers[0].getState()+1 == codeOrdi[0] &&
                        Numbers[1].getState()+1 == codeOrdi[1] &&
                        Numbers[2].getState()+1 == codeOrdi[2] &&
                        Numbers[3].getState()+1 == codeOrdi[3]){
                    carteValide = true;
                }

                if(quitZoom.contains(touched) && !lights){
                    zoomed = false;
                    background.setRegion(darkPlace);
                } else if(quitZoom.contains(touched) && lights){
                    zoomed = false;
                    background.setRegion(brightPlace);
                }
            }
            else if(background.getTexture().toString().matches("image/Amphi_Enssat/zoomtableau.*")) {

                if(quitZoom.contains(touched)){
                    zoomed = false;
                    background.setRegion(brightPlace);
                }
            }
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // resize la vue avec la fenetre
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
    public void dispose(){
        // dispose des textures utilises par la scene
        // IMPORTANT !
        musique.dispose();
        darkPlace.dispose();
        brightPlace.dispose();
        zonePc.dispose();
        zoneTableau.dispose();
        zoomElecSombre.dispose();
        quitZoom.dispose();
        zoneBadge.dispose();
        zoneGauche.dispose();
        zoomOrdiAllume.dispose();
        zoneDroite.dispose();
        zoomElecClair.dispose();
        zoneCarte.dispose();
        zoomOrdiEteint.dispose();
        porteSortie.dispose();
        fondAmphi.dispose();
        fondAmphiSansCarte.dispose();
        amphiPorte.dispose();
        buttonMenu.dispose();
        for(AnimatedGameObject inter : Interrupteurs){
            inter.dispose();
        }
        for(AnimatedGameObject num : Numbers){
            num.dispose();
        }
    }
}
