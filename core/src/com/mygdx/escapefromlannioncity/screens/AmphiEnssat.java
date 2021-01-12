package com.mygdx.escapefromlannioncity.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.utility.AnimatedGameObject;
import com.mygdx.escapefromlannioncity.utility.GameObject;

public class AmphiEnssat extends UI {

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
    private final GameObject zoneCarte;
    private final GameObject carteEtu;
    private final GameObject zoneBadge;
    private final GameObject porteSortie;

    private final AnimatedGameObject[] Interrupteurs;
    private final AnimatedGameObject[] Numbers;

    private final int[] code;
    private final int[] codeOrdi;

    private boolean zoomed;
    private boolean lights;
    private boolean carteValide;
    private boolean porteVerr;
    private int pos;
    private boolean hint2;

    public AmphiEnssat(final EscapeFromLannionCity game) {

        super(game, "music/enigme_1.wav", new String[]{"1. Le panneau electrique contient un Post-it qui indique quels interrupteurs actionner\n",
                                                                    "2. La carte etudiante n'est pas utilisable en l'etat. Un ordinateur pourrait etre utile\n",
                                                                    "3. Le code pour la carte etudiante est relie au tableau blanc. il suffit de faire correspondre les couleurs aux chiffres"});

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

        zoneElec = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 8, 82, 16, 22, "Compteur electrique");
        zoneTableau = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 156, 87 ,156 , 46, "Tableau blanc");
        zonePc = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"),48, 63, 54, 32, "Ordinateur");
        zoneCarte = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 48, 33, 54, 32, "Carte Etudiant");
        carteEtu = new GameObject(Gdx.files.internal("image/Amphi_Enssat/carteEtu.png"), 10, 10, 10,9,"");
        quitZoom = new GameObject(Gdx.files.internal("image/Utilitaire/quitZoom.png"), 222, 126, 5, 5, "");
        zoneBadge = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 227, 63, 15, 15, "Capteur de badge");
        porteSortie = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 200, 71, 30, 140, "Porte de sortie");

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
        zoneCarte.resize();
        carteEtu.resize();
        zoneBadge.resize();
        porteSortie.resize();

        zoomed = false;
        lights = false;
        carteValide = false;
        porteVerr = true;
        code = new int[]{1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0};
        codeOrdi = new int[]{7, 1, 2, 6};
        pos = 0;
        hint2 = false;

    }

    @Override
    public void render(float delta) {
        super.setupRender();

        if(background.getTexture().toString().contentEquals("image/Amphi_Enssat/tableauelec.png") || background.getTexture().toString().contentEquals("image/Amphi_Enssat/tableauelecsombre.png")) {
            for (AnimatedGameObject inter : Interrupteurs) {
                inter.drawFix(game.batch);
            }
        }

        if(background.getTexture().toString().contentEquals("image/Amphi_Enssat/ordiallume.png")){
            for(AnimatedGameObject num: Numbers){
                num.drawFix(game.batch);
            }
        }

        // controle du cursor au survol d'un GameObject avec lequel on peut actuellement interagir
        if(background.getTexture().toString().matches("image/Amphi_Enssat/Amphi137c.piece1.*")){
            if(!cursor.survol(zoneElec, (StretchViewport) viewport)){
                if(!cursor.survol(zoneTableau, (StretchViewport) viewport)){
                    cursor.survol(zonePc, (StretchViewport) viewport);
                }
            }
        } else if(background.getTexture().toString().matches("image/Amphi_Enssat/fondAmphi.png")){
            cursor.survol(zoneCarte, (StretchViewport) viewport);
        } else if(background.getTexture().toString().matches("image/Amphi_Enssat/amphiporte.png")){
            if(!cursor.survol(zoneBadge, (StretchViewport) viewport)){
                cursor.survol(porteSortie, (StretchViewport) viewport);
            }
        } else if(background.getTexture().toString().contentEquals("image/Amphi_Enssat/tableauelec.png") || background.getTexture().toString().contentEquals("image/Amphi_Enssat/tableauelecsombre.png")){
            cursor.survol(Interrupteurs, (StretchViewport) viewport);
        }else if(background.getTexture().toString().matches("image/Amphi_Enssat/ordiallume.png")){
            cursor.survol(Numbers, (StretchViewport) viewport);
        }
        else {
            cursor.reset();
        } // fin control curseur au survol




        // check pour un clic gauche de la souris
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && textZone.isHidden()) {
            // prend les coordonnees du clic et les convertis en coordonnees du monde
            Vector2 touched = new Vector2();
            touched.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);

            if(buttonHint.contains(touched)){

                if(!lights){
                    super.showHint(0);
                } else if(game.inventory.hasIn(carteEtu) && !hint2){
                    super.showHint(1);
                    hint2 = true;
                } else if(game.inventory.hasIn(carteEtu) && hint2){
                    super.showHint(2);
                }
            }

            if (!zoomed) {
                if (zoneTableau.contains(touched) && background.getTexture().toString().matches("image/Amphi_Enssat/Amphi137c.piece1.*") && lights) {
                    zoomed = true;
                    zoneGauche.hide();
                    zoneDroite.hide();
                    background.setRegion(zoomTableau);
                } else if (zoneTableau.contains(touched) && !lights){
                    flavorText.setText("On ne voit rien");
                }

                if (zoneElec.contains(touched) && background.getTexture().toString().matches("image/Amphi_Enssat/Amphi137c-piece1_sombre.*")) {
                    zoomed = true;
                    zoneGauche.hide();
                    zoneDroite.hide();
                    background.setRegion(zoomElecSombre);
                }
                if (zoneElec.contains(touched) && background.getTexture().toString().matches("image/Amphi_Enssat/Amphi137c-piece1.*")) {
                    zoomed = true;
                    zoneGauche.hide();
                    zoneDroite.hide();
                    background.setRegion(zoomElecClair);
                }

                if (zonePc.contains(touched) && background.getTexture().toString().matches("image/Amphi_Enssat/Amphi137c-piece1_sombre.*")) {
                    zoomed = true;
                    zoneGauche.hide();
                    zoneDroite.hide();
                    background.setRegion(zoomOrdiEteint);
                } else if (zonePc.contains(touched) && background.getTexture().toString().matches("image/Amphi_Enssat/Amphi137c-piece1.*")) {
                    zoomed = true;
                    zoneGauche.hide();
                    zoneDroite.hide();
                    background.setRegion(zoomOrdiAllume);
                }

                if (zoneCarte.contains(touched) && background.getTexture().toString().matches("image/Amphi_Enssat/fondAmphi.*")){
                    background.setRegion(fondAmphiSansCarte);
                    game.inventory.add(carteEtu);
                }

                if ((zoneDroite.contains(touched) || zoneGauche.contains(touched)) && !lights){
                    flavorText.setText("Il fait trop noir");
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

                if (carteValide && game.inventory.hasIn(carteEtu) && zoneBadge.contains(touched)){
                    porteVerr = false;
                    game.inventory.remove(carteEtu);
                    flavorText.setText("Je peux enfin sortir de cette salle. Je me demande ce qui m'attends par la suite...");
                }
                if (!porteVerr && porteSortie.contains(touched)){
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
                zoneGauche.hide();
                zoneDroite.hide();
                if(i==11 && !interupt){
                    lights = true;
                    background.setRegion(zoomElecClair);
                } else {
                    lights = false;
                    background.setRegion(zoomElecSombre);
                }

                if(quitZoom.contains(touched) && !lights){
                    zoomed = false;
                    zoneGauche.unhide();
                    zoneDroite.unhide();
                    background.setRegion(darkPlace);
                } else if(quitZoom.contains(touched) && lights){
                    zoomed = false;
                    zoneGauche.unhide();
                    zoneDroite.unhide();
                    background.setRegion(brightPlace);
                }


            }


            else if(background.getTexture().toString().matches("image/Amphi_Enssat/ordipasallume.*")) {

                if(quitZoom.contains(touched) && !lights){
                    zoomed = false;
                    zoneGauche.unhide();
                    zoneDroite.unhide();
                    background.setRegion(darkPlace);
                } else if(quitZoom.contains(touched) && lights){
                    zoomed = false;
                    zoneGauche.unhide();
                    zoneDroite.unhide();
                    background.setRegion(brightPlace);
                }
            }
            else if(background.getTexture().toString().matches("image/Amphi_Enssat/ordiallume.*")) {

                for(AnimatedGameObject num: Numbers){
                    if(num.contains(touched)){
                        num.changeStat(true);
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
                    zoneGauche.unhide();
                    zoneDroite.unhide();
                    background.setRegion(darkPlace);
                } else if(quitZoom.contains(touched) && lights){
                    zoomed = false;
                    zoneGauche.unhide();
                    zoneDroite.unhide();
                    background.setRegion(brightPlace);
                }
            }
            else if(background.getTexture().toString().matches("image/Amphi_Enssat/zoomtableau.*")) {

                if(quitZoom.contains(touched)){
                    zoomed = false;
                    zoneGauche.unhide();
                    zoneDroite.unhide();
                    background.setRegion(brightPlace);
                }
            }
        }

        super.render(delta);

        game.batch.end();
    }


    @Override
    public void dispose(){
        // dispose des textures utilises par la scene
        // IMPORTANT !
        super.dispose();
        darkPlace.dispose();
        brightPlace.dispose();
        zonePc.dispose();
        zoneTableau.dispose();
        zoomElecSombre.dispose();
        quitZoom.dispose();
        zoneBadge.dispose();
        zoomOrdiAllume.dispose();
        zoomElecClair.dispose();
        zoneCarte.dispose();
        zoomOrdiEteint.dispose();
        porteSortie.dispose();
        fondAmphi.dispose();
        fondAmphiSansCarte.dispose();
        amphiPorte.dispose();
        for(AnimatedGameObject inter : Interrupteurs){
            inter.dispose();
        }
        for(AnimatedGameObject num : Numbers){
            num.dispose();
        }
    }
}
