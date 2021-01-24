package com.mygdx.escapefromlannioncity.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.sauvegarde.SAmphiEnssat;
import com.mygdx.escapefromlannioncity.utility.AnimatedGameObject;
import com.mygdx.escapefromlannioncity.utility.GameObject;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class AmphiEnssat extends UI {

    // Background textures
    private final Texture brightPlace;
    private final Texture darkPlace;
    private final Texture zoomElecClair;
    private final Texture zoomElecSombre;
    private final Texture zoomOrdiAllume;
    private final Texture zoomOrdiEteint;
    private final Texture zoomTableau;
    private final Texture amphiPorte;
    private final Texture amphiPorteOuv;
    private final Texture fondAmphi;
    private final Texture fondAmphiSansCarte;

    // zone principale
    private final GameObject zonePc;
    private final GameObject zoneTableau;
    private final GameObject zoneElec;

    // zone porte
    private final GameObject zoneBadge;
    private final GameObject porteSortie;

    // zone sieges d'amphi
    private final GameObject zoneCarte;

    // zoom panneeau electrique
    private final AnimatedGameObject[] Interrupteurs;

    // zoom ordinateur
    private final AnimatedGameObject[] Numbers;
    private final GameObject carteOk;
    private final GameObject cartePasOk;

    // objet inventaire
    private final GameObject carteEtu;

    // utilitaires
    private final GameObject quitZoom;
    private final int[] code;
    private final int[] codeOrdi;
    private boolean lights;
    private boolean carteValide;
    private boolean porteVerr;
    private boolean hint2;
    private boolean first_light;
    private boolean first_carte;

    public AmphiEnssat(final EscapeFromLannionCity game, String timeTotal,int bonus, int usedHint) {

        super(game, "music/enigme_1.wav", new String[]{"1. Le panneau electrique contient un Post-it qui indique quels interrupteurs actionner\n",
                                                                    "2. La carte etudiante n'est pas utilisable en l'etat. Un ordinateur pourrait etre utile\n",
                                                                    "3. Le code pour la carte etudiante est relie au tableau blanc. il suffit de faire correspondre les couleurs aux chiffres"}
                                                         ,timeTotal,bonus,usedHint );

        // init textures zone principale
        darkPlace = new Texture(Gdx.files.internal("image/Amphi_Enssat/Amphi137c-piece1_sombre.jpg"));
        brightPlace = new Texture(Gdx.files.internal("image/Amphi_Enssat/Amphi137c-piece1.png"));
        // init textures zoom panneau electrique
        zoomElecSombre = new Texture(Gdx.files.internal("image/Amphi_Enssat/tableauelecsombre.png"));
        zoomElecClair =  new Texture(Gdx.files.internal("image/Amphi_Enssat/tableauelec.png"));
        // init textures zoom ordinateur
        zoomOrdiAllume = new Texture(Gdx.files.internal("image/Amphi_Enssat/ordiallume.png"));
        zoomOrdiEteint = new Texture(Gdx.files.internal("image/Amphi_Enssat/ordipasallume.png"));
        // init texture zoom tableau
        zoomTableau = new Texture(Gdx.files.internal("image/Amphi_Enssat/zoomtableau.png"));
        // init texture zone porte
        amphiPorte = new Texture(Gdx.files.internal("image/Amphi_Enssat/amphiporte.png"));
        amphiPorteOuv = new Texture((Gdx.files.internal("image/Amphi_Enssat/amphiporteOuv.png")));
        // init texture zone siege d'amphi
        fondAmphi = new Texture(Gdx.files.internal("image/Amphi_Enssat/fondAmphi.png"));
        fondAmphiSansCarte = new Texture(Gdx.files.internal("image/Amphi_Enssat/fondamphi_sanscarte.png"));
        // mise en place du background principale
        background = new Sprite(darkPlace);

        // init des objets de la zone principale
        zoneElec = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 8, 82, 16, 22, "Compteur electrique");
        zoneTableau = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 156, 87 ,156 , 46, "Tableau blanc");
        zonePc = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"),48, 63, 54, 32, "Ordinateur");

        zoneElec.resize();
        zoneTableau.resize();
        zonePc.resize();

        // init des objets de la zone siege d'amphi
        zoneCarte = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 37.5f, 35.5f, 5, 5, "Carte Etudiant");

        zoneCarte.resize();

        // init des objets zone porte
        zoneBadge = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 227, 63, 15, 15, "Capteur de badge");
        porteSortie = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 200, 71, 30, 140, "Porte de sortie");

        zoneBadge.resize();
        porteSortie.resize();

        // init des objets zoom panneau electrique
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
        code = new int[]{1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0};

        // init des objets zoom ordinateur
        carteOk = new GameObject(Gdx.files.internal("image/Amphi_Enssat/CarteOk.png"), 111, 89, 14, 14, "");
        cartePasOk = new GameObject(Gdx.files.internal("image/Amphi_Enssat/CartePasOk.png"), 111, 89, 14, 14, "");

        carteOk.resize();
        cartePasOk.resize();

        carteOk.hide();

        Numbers = new AnimatedGameObject[4];
        Texture[] spritesNumbers = new Texture[9];
        for (int i=1;i<10;i++) {
            spritesNumbers[i-1] = new Texture(Gdx.files.internal("animation/Amphi_Enssat/number/"+i+".png"));
        }
        for (int i = 0; i<4 ; i++){
            Numbers[i] = new AnimatedGameObject(spritesNumbers);
            Numbers[i].setPlayMode(Animation.PlayMode.LOOP);
            Numbers[i].setSize(12, 11);
            Numbers[i].setCenterPos(75+i*16, 72);
            Numbers[i].resize();
        }
        codeOrdi = new int[]{7, 1, 2, 6};

        // init objet inventaire
        carteEtu = new GameObject(Gdx.files.internal("image/Amphi_Enssat/carteEtu.png"), 10, 10, 10,10,"");

        carteEtu.resize();

        // init des utilitaires
        quitZoom = new GameObject(Gdx.files.internal("image/Utilitaire/quitZoom.png"), 223, 125, 6, 6, "");
        lights = false;
        carteValide = false;
        porteVerr = true;
        hint2 = false;
        first_light = false;
        first_carte = false;

        quitZoom.resize();

        zoneDroite.hide();

    }

    @Override
    public void render(float delta) {
        super.setupRender();

        if(background.getTexture().toString().matches("image/Amphi_Enssat/tableauelec.*")) {
            for (AnimatedGameObject inter : Interrupteurs) {
                inter.drawFix(game.batch);
            }
        }

        if(background.getTexture().toString().contentEquals("image/Amphi_Enssat/ordiallume.png")){
            for(AnimatedGameObject num: Numbers){
                num.drawFix(game.batch);
            }
            carteOk.drawFix(game.batch);
            cartePasOk.drawFix(game.batch);
        }



        // controle du cursor au survol d'un GameObject avec lequel on peut actuellement interagir
        if(background.getTexture().toString().matches("image/Amphi_Enssat/Amphi137c.piece1.*")){
            if(!cursor.survol(zoneElec, (StretchViewport) viewport)){
                if(!cursor.survol(zoneTableau, (StretchViewport) viewport)){
                    cursor.survol(zonePc, (StretchViewport) viewport);
                }
            }
        } else if(background.getTexture().toString().matches("image/Amphi_Enssat/fondAmphi.*")){
            cursor.survol(zoneCarte, (StretchViewport) viewport);
        } else if(background.getTexture().toString().matches("image/Amphi_Enssat/amphiporte.*")){
            if(!cursor.survol(zoneBadge, (StretchViewport) viewport)){
                cursor.survol(porteSortie, (StretchViewport) viewport);
            }
        } else if(background.getTexture().toString().matches("image/Amphi_Enssat/tableauelec.*")){
            cursor.survol(Interrupteurs, (StretchViewport) viewport);
        }else if(background.getTexture().toString().matches("image/Amphi_Enssat/ordiallume.*")){
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

                if(!lights || !game.inventory.hasIn(carteEtu)){
                    super.showHint(0);
                } else if(game.inventory.hasIn(carteEtu) && !hint2){
                    super.showHint(1);
                    hint2 = true;
                } else if(game.inventory.hasIn(carteEtu) && hint2){
                    super.showHint(2);
                }
            }

            if (background.getTexture().toString().matches("image/Amphi_Enssat/Amphi137c-piece1.*")){
                if(!lights) {
                    if (zoneGauche.contains(touched)) {
                        flavorText.setText("Il fait trop sombre pour m'aventurer dans cette piece.");
                    } else if (zoneTableau.contains(touched)) {
                        flavorText.setText("Il semblerait qu'il y ait quelque chose d'ecrit sur ce tableau...\n la penombre m'empeche de discerner ce que c'est");
                    } else if(zonePc.contains(touched)){
                        background.setRegion(zoomOrdiEteint);
                        zoneGauche.hide();
                    } else if(zoneElec.contains(touched)){
                        zoneGauche.hide();
                        background.setRegion(zoomElecSombre);
                    }
                } else {
                    if(zoneGauche.contains(touched)){
                        zoneDroite.unhide();
                        if(porteVerr) {
                            background.setRegion(amphiPorte);
                        } else {
                            background.setRegion(amphiPorteOuv);
                        }
                    } else if (zoneTableau.contains(touched)){
                        background.setRegion(zoomTableau);
                        zoneGauche.hide();
                    } else if(zonePc.contains(touched)){
                        background.setRegion(zoomOrdiAllume);
                        zoneGauche.hide();
                    } else if(zoneElec.contains(touched)){
                        zoneGauche.hide();
                        background.setRegion(zoomElecClair);
                    }
                }
            } else if(background.getTexture().toString().matches("image/Amphi_Enssat/tableauelec.*")){
                if(quitZoom.contains(touched)){
                    if(lights){
                        background.setRegion(brightPlace);
                    } else{
                        background.setRegion(darkPlace);
                    }
                    zoneGauche.unhide();
                } else {
                    for (AnimatedGameObject inter : Interrupteurs) {
                        if (inter.contains(touched)) {
                            inter.changeStat(true);
                            int i = 0;
                            boolean verif = false;
                            while (!verif && i < 11) {
                                if (Interrupteurs[i].getState() != code[i]) {
                                    verif = true;
                                }
                                i++;
                            }
                            if (!verif) {
                                lights = true;
                                background.setRegion(zoomElecClair);
                                if (!first_light) {
                                    flavorText.setText("la lumiere est revenu, je vais pouvoir explorer cette piece.");
                                    first_light = true;
                                }
                            } else {
                                lights = false;
                                background.setRegion(zoomElecSombre);
                            }
                        }
                    }
                }
            } else if(background.getTexture().toString().matches("image/Amphi_Enssat/zoomtableau.*")){
                if(quitZoom.contains(touched)){
                    if(lights){
                        background.setRegion(brightPlace);
                    } else {
                        background.setRegion(darkPlace);
                    }
                    zoneGauche.unhide();
                }
            } else if(background.getTexture().toString().matches("image/Amphi_Enssat/ordi.*")){
                if(lights){
                    if(quitZoom.contains(touched)){
                        background.setRegion(brightPlace);
                        zoneGauche.unhide();
                    } else {
                        for (AnimatedGameObject num : Numbers){
                            if(num.contains(touched)){
                                num.changeStat(true);
                                int i = 0;
                                boolean verif = false;
                                while(!verif && i<4){
                                    if(Numbers[i].getState()+1 != codeOrdi[i]){
                                        verif = true;
                                    }
                                    i++;
                                }
                                if(!verif){
                                    carteValide = true;
                                    carteOk.unhide();
                                    cartePasOk.hide();
                                    if(!first_carte){
                                        first_carte = true;
                                        flavorText.setText("Le code semble bon. Il  me reste plus qu'a utiliser cette carte pour sortir d'ici");
                                    }
                                } else {
                                    carteValide = false;
                                    carteOk.hide();
                                    cartePasOk.unhide();
                                }
                            }
                        }
                    }
                } else {
                    if(quitZoom.contains(touched)){
                        background.setRegion(darkPlace);
                        zoneGauche.unhide();
                    }
                }
            } else if(background.getTexture().toString().matches("image/Amphi_Enssat/amphiporte.*")){
                if(porteSortie.contains(touched)){
                    if(porteVerr){
                        flavorText.setText("Rien a faire, cette porte ne s'ouvre pas...");
                    } else {
                        finNiveau = true;
                    }
                } else if(zoneBadge.contains(touched)){
                    if(carteValide && game.inventory.hasIn(carteEtu)){
                        background.setRegion(amphiPorteOuv);
                        flavorText.setText("La led est passee au vert, la porte serait-elle ouverte ?");
                        porteVerr = false;
                    } else {
                        flavorText.setText("Il y a une led rouge dans ce boitier. Permettrait-il de lire des carte magnetiques ?");
                    }
                } else if(zoneGauche.contains(touched)){
                    if(game.inventory.hasIn(carteEtu)){
                        background.setRegion(fondAmphiSansCarte);
                    } else {
                        background.setRegion(fondAmphi);
                    }
                    zoneGauche.hide();
                } else if(zoneDroite.contains(touched)){
                    background.setRegion(brightPlace);
                    zoneDroite.hide();
                }
            } else if(background.getTexture().toString().matches("image/Amphi_Enssat/fond[aA]mphi.*")){
                if(!game.inventory.hasIn(carteEtu)){
                    if(zoneCarte.contains(touched)){
                        flavorText.setText("une carte magnetique d'un etudiant. Elle semble posseder un code en bas a droite...");
                        game.inventory.add(carteEtu);
                        background.setRegion(fondAmphiSansCarte);
                    }
                } else if (zoneDroite.contains(touched)){
                    if(porteVerr){
                        background.setRegion(amphiPorte);
                    } else {
                        background.setRegion(amphiPorteOuv);
                    }
                    zoneGauche.unhide();
                }
            }

        }

        super.render(delta);
        game.batch.end();
    }

    public void Convertir(int[] interrupteur, int[] number) {
        int i = 0;
        while (i < 11) {
            interrupteur[i] = Interrupteurs[i].getState();
            i++;
        }
        i = 0;
        while (i < 4) {
            number[i] = Numbers[i].getState();
            i++;
        }
    }


    public void Synchronize(int[] interrupteur, int[] number){
        for(int i=0;i< interrupteur.length;i++){
            if(interrupteur[i]!= Interrupteurs[i].getState()){
                Interrupteurs[i].changeStat(true);
            }
        }
        for(int i=0;i< number.length;i++){
            if(number[i]!= Numbers[i].getState()){
                for(int j= 0;j<number[i];j++) {
                    Numbers[i].changeStat(true);
                }
            }
        }
    }
    /* Getter et Setter */
    public boolean getCarte(){
        if(game.inventory.hasIn(carteEtu)){ return true;}
        else{ return false;}
    }
    public void setCarte(boolean carte){
        if(carte) {
            game.inventory.add(carteEtu);
            background.setRegion(fondAmphiSansCarte);
        }
    }
    public boolean isLights() {
        return lights;
    }

    public void setLights(boolean lights) {
        if(lights) {
            background.setRegion(zoomElecClair);
            background.setRegion(zoomOrdiAllume);
            zoneGauche.unhide();
            this.first_light =true;
        }
            this.lights = lights;
    }

    public boolean isCarteValide() {
        return carteValide;
    }

    public void setCarteValide(boolean carteValide) {
        if(carteValide) {
            carteOk.unhide();
            cartePasOk.hide();
        }
        this.carteValide = carteValide;
    }

    public boolean isPorteVerr() {
        return porteVerr;
    }

    public void setPorteVerr(boolean porteVerr) {
        if(porteVerr){
            background.setRegion(amphiPorte);
        } else {
            background.setRegion(amphiPorteOuv);
        }
        this.porteVerr = porteVerr;
    }

    public boolean isHint2() {
        return this.hint2;
    }

    public void setHint2(boolean hint2) {
        this.hint2 = hint2;
    }
    public void setBackground(boolean lights){
        if(lights) {
            background.setRegion(brightPlace);
        }else{
            background.setRegion(darkPlace);
        }
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
        cartePasOk.dispose();
        carteOk.dispose();
        amphiPorteOuv.dispose();
        for(AnimatedGameObject inter : Interrupteurs){
            inter.dispose();
        }
        for(AnimatedGameObject num : Numbers){
            num.dispose();
        }
    }
}
