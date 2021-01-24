
package com.mygdx.escapefromlannioncity.screens;

import com.badlogic.gdx.Game;
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

import javax.swing.text.View;

import java.util.Arrays;

import static java.lang.Math.floor;

public class Warp extends UI {

    //Background textures
    private final Texture principal;
    private final Texture salleMain;
    private final Texture salleMainSansBross;
    private final Texture salleMainSansBross_chev1Bross;
    private final Texture salleMainSansBross_chev2Bross;
    private final Texture salleMainSansBross_chevBross;
    private final Texture principalDecapLess;
    private final Texture principalDecapBottleLess;
    private final Texture zoomBouteille;
    private final Texture zoomChev1;
    private final Texture zoomChev2;
    private final Texture zoomChev1Gratt;
    private final Texture zoomChev2Gratt;
    private final Texture zoomBorne;
    private final Texture zoomBorneAllum;
    private final Texture zoomTableau;
    private final Texture zoomCoffre;
    private final Texture coffreOK;
    private final Texture coffreKO;
    private final Texture coffreOuvert;
    private final Texture coffreOuvertSsCle;
    private final Texture entree;
    private final Texture entree_BAllum;


    // tableau principal comptoir
    private final GameObject bouteille;
    private final GameObject bouchonBouteille;
    private final GameObject zoneObjetDansBouteille;
    private final GameObject zoneDecapsuleur;
    private final GameObject zoneBiere;

    // init objets salle
    private final GameObject table1;
    private final GameObject table2;
    private final GameObject brosse;

    // tableau entree
    private final GameObject porteSortie;
    private final GameObject borneArcade;
    private final GameObject photoEncadree;

    //obj zoom tableau/coffre
    private final GameObject boutonTableau;
    private final GameObject[] coffreBoutons;
    private final Texture[] coffreEtoiles;
    private final GameObject poigneeCoffre;
    private final GameObject zoneClefs;



    //obj zoom borne
    private final GameObject insererPiece;

    //obj zoom tables
    private final GameObject chev1;
    private final GameObject chev2;

    // tableau zoom coffre
    private final int[] code;
    private final int[] codeCompose;

    // pour l'inventaire
    private final GameObject piece;
    private final GameObject decapsuleur;
    private final GameObject clefs;

    //utilitaire
    private final GameObject zoneQuitter;
    private boolean isOpenerPicked;
    private boolean isMoneyPicked;
    private boolean isDecapsuleurPicked;
    private boolean isPiecePicked;
    private boolean isBorneTurnedOn;
    private boolean isBorneAlredyClicked;
    private boolean areChevBross;
    private boolean isBrossePicked;
    private boolean isCoffreDiscovered;
    private boolean isOpened;
    private boolean isChev1Bross;
    private boolean isChev2Bross;
    private boolean isBiereAlreadyClicked;
    private boolean isTableauAlreadyClicked;


    public Warp(EscapeFromLannionCity game, String timeTotal,int bonus, int usedHint) {
        super(game, "music/enigme_2.wav", new String[]{"1. La bouteille bleue contient un objet singulier ...\n",
                "2. La photo du space invader semble louche ...\n",
                "3. Les chevalets de la salle ont été repeint très récemment. "}
                ,timeTotal,bonus,usedHint);
        // init textures zone comptoir
        principal = new Texture(Gdx.files.internal("image/Warp/comptoir.png"));
        principalDecapLess = new Texture(Gdx.files.internal("image/Warp/comptoir_ss_decap.png"));
        principalDecapBottleLess = new Texture(Gdx.files.internal("image/Warp/comptoir_ss_decap_bout.png"));
        zoomBouteille = new Texture(Gdx.files.internal("image/Warp/zoomBouteille.png"));
        // init textures salle
        salleMain = new Texture(Gdx.files.internal("image/Warp/salleMain.png"));
        salleMainSansBross = new Texture(Gdx.files.internal("image/Warp/salleMainSansBross.png"));
        salleMainSansBross_chevBross = new Texture(Gdx.files.internal("image/Warp/salleMainChB.png"));
        salleMainSansBross_chev1Bross = new Texture(Gdx.files.internal("image/Warp/salleMainSansBrossCh1B.png"));
        salleMainSansBross_chev2Bross = new Texture(Gdx.files.internal("image/Warp/salleMainSansBrossCh2B.png"));
        zoomChev1 = new Texture(Gdx.files.internal("image/Warp/zoomChev1.png"));
        zoomChev2 = new Texture(Gdx.files.internal("image/Warp/zoomChev2.png"));
        zoomChev1Gratt = new Texture(Gdx.files.internal("image/Warp/zoomChev1Gratt.png"));
        zoomChev2Gratt = new Texture(Gdx.files.internal("image/Warp/zoomChev2Gratt.png"));
        // init textures entree
        entree = new Texture(Gdx.files.internal("image/Warp/entree.png"));
        entree_BAllum = new Texture(Gdx.files.internal("image/Warp/entreeBorneAllum.png"));
        zoomBorne = new Texture(Gdx.files.internal("image/Warp/zoomBorne.png"));
        zoomBorneAllum = new Texture(Gdx.files.internal("image/Warp/zoomBorneAllum.png"));
        zoomTableau = new Texture(Gdx.files.internal("image/Warp/tableau.png"));
        zoomCoffre = new Texture(Gdx.files.internal("image/Warp/coffre0.png"));
        coffreKO = new Texture(Gdx.files.internal("image/Warp/coffreko.png"));
        coffreOK = new Texture(Gdx.files.internal("image/Warp/coffreok.png"));
        coffreOuvert = new Texture(Gdx.files.internal("image/Warp/Open_coffre.png"));
        coffreOuvertSsCle = new Texture(Gdx.files.internal("image/Warp/Open_coffre_ss_cle.png"));
        // mise en place du background principal
        background = new Sprite(principal);

        // init des objets du comptoir
        bouteille = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 151,144-82+10,8,21,"bouteille");
        bouchonBouteille = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 92+29,144-27+8,58,16,"bouchon");
        zoneDecapsuleur = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 136,144-89,11,5,"décapsuleur");
        zoneObjetDansBouteille = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 156,144-129,38,30,"???");
        zoneBiere = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 109,144-84,14,14,"biere");

        bouteille.resize();
        zoneDecapsuleur.resize();
        zoneObjetDansBouteille.resize();
        bouchonBouteille.resize();
        zoneBiere.resize();

        // init des objets de la salle
        table1 = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 86+28,144-123+7,57,14,"table");
        table2 = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 181+28,144-127+7,57,14,"table");
        brosse = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 4+5,144-110+4,10,8,"brosse metallique");
        photoEncadree = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 117+30,144-54+20,60,40,"photo encadrée");

        photoEncadree.resize();
        table1.resize();
        table2.resize();
        brosse.resize();

        // init des objets de l'entree
        porteSortie = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 69+49,144-89+41,90,82,"sortie");
        borneArcade = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 161+27,144-137+40,55,81,"borne d'arcade");

        porteSortie.resize();
        borneArcade.resize();

        //init des objets à l'intérieur du zoom photoEncadrée
        boutonTableau = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 211,144-63,9,9,"bouton discret");

        boutonTableau.resize();

        // init des objets du zoom coffre
        coffreBoutons = new GameObject[9];
        zoneClefs = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 80+14,144-108+9,28,18,"clefs du Warp");
        poigneeCoffre = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 63+25,144-93+24,50,48,"ouvrir");
        for (int i=0;i<9;i++){
            coffreBoutons[i] = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 148+i%3*16,47+Math.floorDiv(i,3)*16,11,11,"bouton "+(i+1));
            coffreBoutons[i].resize();
        }
        coffreEtoiles = new Texture[5];
        for (int i=0;i<5;i++){
            coffreEtoiles[i] = new Texture("image/Warp/coffre"+i+".png");
        }
        code = new int[]{4, 7, 2, 5};
        codeCompose = new int[4];
        poigneeCoffre.resize();
        zoneClefs.resize();
        // init des objets dans le zoom borne
        insererPiece = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 113,74,26,66,"une fente pour la monnaie");

        insererPiece.resize();

        //init des objets zoom table1
        chev1 = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 113,74,26,66,"chevalet abîmé");
        chev2 = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 113,74,26,66,"chevalet abîmé");

        chev1.resize();
        chev2.resize();


        // init des objets de l'inventaire
        piece = new GameObject(Gdx.files.internal(("image/Warp/jetonArcade.png")), 0,0,10,10,"");
        decapsuleur = new GameObject(Gdx.files.internal(("image/Warp/decapsuleur.png")), 0,0,10,10,"");
        clefs = new GameObject(Gdx.files.internal(("image/Warp/cle.png")), 0,0,10,10,"");

        piece.resize();
        decapsuleur.resize();
        clefs.resize();


        // init des utilitaires
        zoneQuitter = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 223,125,6,6,"");
        isDecapsuleurPicked = false;
        isPiecePicked = false;
        isBorneTurnedOn = false;
        isBorneAlredyClicked = false;
        areChevBross = false;
        isChev1Bross = false;
        isChev2Bross = false;
        isBrossePicked = false;
        isOpened = false;
        isBiereAlreadyClicked = false;
        isTableauAlreadyClicked = false;

        zoneQuitter.resize();

    }

    @Override
    public void render(float delta){
        super.setupRender();

        if (background.getTexture().toString().matches("image/Warp/comptoir*.*") && !isDecapsuleurPicked){
            if (!cursor.survol(zoneDecapsuleur, (StretchViewport) viewport)){
                if (!cursor.survol(bouteille, (StretchViewport) viewport)){
                    cursor.survol(zoneBiere, (StretchViewport) viewport);
                }
            }
        }
        else if (background.getTexture().toString().matches("image/Warp/comptoir.*") && !isPiecePicked && isDecapsuleurPicked){
            if (!cursor.survol(bouteille, (StretchViewport) viewport)){
                cursor.survol(zoneBiere, (StretchViewport) viewport);
            }
        }
        else if (background.getTexture().toString().matches("image/Warp/comptoir.*") && isPiecePicked && isDecapsuleurPicked){
                cursor.survol(zoneBiere, (StretchViewport) viewport);
        }

        else if (background.getTexture().toString().matches("image/Warp/zoomBouteille.*") && !isPiecePicked){
            if (!cursor.survol(zoneObjetDansBouteille, (StretchViewport) viewport)){
                cursor.survol(bouchonBouteille, (StretchViewport) viewport);
            }
        }
        else if (background.getTexture().toString().matches("image/Warp/entree*.*")){
            if(!cursor.survol(borneArcade, (StretchViewport) viewport)){
                cursor.survol(porteSortie, (StretchViewport) viewport);
            }
        }
        else if (background.getTexture().toString().matches("image/Warp/tableau.*")){
            cursor.survol(boutonTableau, (StretchViewport) viewport);
        }
        else if (background.getTexture().toString().matches("image/Warp/coffre*.*")){
            if (!cursor.survol(coffreBoutons, (StretchViewport) viewport)){
                cursor.survol(poigneeCoffre, (StretchViewport) viewport);
            }
        } else if (background.getTexture().toString().matches("image/Warp/Open_coffre.png")){
            cursor.survol(zoneClefs, (StretchViewport) viewport);
        }
        else if (background.getTexture().toString().matches("image/Warp/salleMain.*") && !isBrossePicked){
            if(!cursor.survol(table1, (StretchViewport) viewport)){
                if(!cursor.survol(table2, (StretchViewport) viewport)){
                    if (!cursor.survol(photoEncadree, (StretchViewport) viewport)){
                        cursor.survol(brosse, (StretchViewport) viewport);
                    }
                }
            }
        }
        else if (background.getTexture().toString().matches("image/Warp/salleMainSansGratt.*") && isBrossePicked){
            if(!cursor.survol(table1, (StretchViewport) viewport)){
                if(!cursor.survol(table2, (StretchViewport) viewport)){
                    cursor.survol(photoEncadree, (StretchViewport) viewport);
                }
            }
        }


        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && textZone.isHidden()) {

            Vector2 touched = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);

            if (buttonHint.contains(touched)) {

                if (!game.inventory.hasIn(piece)) {
                    super.showHint(0);
                } else if (!isCoffreDiscovered) {
                    super.showHint(1);
                } else if (!areChevBross) {
                    super.showHint(2);
                }
            }

            if (background.getTexture().toString().matches("image/Warp/comptoir*.*")) {
                if (bouteille.contains(touched)) {
                    if (!isBiereAlreadyClicked){
                        flavorText.setText("Une bouteille de bière ... étrange, je ne connais pas la marque.");
                        isBiereAlreadyClicked = true;
                    }
                    background.setRegion(zoomBouteille);
                    zoneDroite.hide();
                    zoneGauche.hide();
                } else if (zoneDecapsuleur.contains(touched) && !isDecapsuleurPicked) {
                    flavorText.setText("Ceci pourrait m'aider à m'ouvrir la bouteille.\n");
                    game.inventory.add(decapsuleur);
                    isDecapsuleurPicked = true;
                    background.setRegion(principalDecapLess);
                } else if (zoneBiere.contains(touched)) {
                    flavorText.setText("Ce n'est vraiment pas le moment !");
                } else if (zoneGauche.contains(touched)) {
                    if (isBorneTurnedOn) {
                        background.setRegion(entree_BAllum);
                        zoneGauche.hide();
                    } else if (!isBorneTurnedOn) {
                        background.setRegion(entree);
                        zoneGauche.hide();
                    }
                } else if (zoneDroite.contains(touched) && !isBrossePicked) {
                    if (!isBrossePicked){
                        background.setRegion(salleMain);
                        zoneDroite.hide();
                    } else if (isBrossePicked && !isChev1Bross && !isChev2Bross){
                        background.setRegion(salleMainSansBross);
                        zoneDroite.hide();
                    } else if (isBrossePicked && isChev1Bross && !isChev2Bross){
                        background.setRegion(salleMainSansBross_chev1Bross);
                        zoneDroite.hide();
                    } else if (isBrossePicked && !isChev1Bross && isChev2Bross){
                        background.setRegion(salleMainSansBross_chev2Bross);
                        zoneDroite.hide();
                    } else if (isBrossePicked && isChev1Bross && isChev2Bross){
                        background.setRegion(salleMainSansBross_chevBross);
                        zoneDroite.hide();
                    }
                }
            } else if (background.getTexture().toString().matches("image/Warp/zoomBouteille.png")) {
                if (isDecapsuleurPicked && bouchonBouteille.contains(touched)) {
                    flavorText.setText("Je peux enfin voir ce qu'il y a dedans !");
                    game.inventory.remove(decapsuleur);
                    game.inventory.add(piece);
                    isPiecePicked = true;
                    flavorText.setText("Un jeton d'arcade !");
                    background.setRegion(principalDecapBottleLess);
                    zoneGauche.unhide();
                    zoneDroite.unhide();
                } else if (!isDecapsuleurPicked && bouchonBouteille.contains(touched)) {
                    flavorText.setText("J'aimerais bien l'ouvrir pour voir ce qu'il y a dedans ...");
                } else if (zoneObjetDansBouteille.contains(touched)) {
                    flavorText.setText("Il y a un objet au fond de la bouteille, mais je n'arrive pas à voir ce que c'est.");
                } else if (zoneQuitter.contains(touched) && isPiecePicked) {
                    background.setRegion(principalDecapBottleLess);
                    zoneGauche.unhide();
                    zoneDroite.unhide();
                } else if (zoneQuitter.contains(touched) && !isPiecePicked) {
                    background.setRegion(principalDecapLess);
                    zoneDroite.unhide();
                    zoneGauche.unhide();
                }
            } else if (background.getTexture().toString().matches("image/Warp/entree*.*")) {
                if (borneArcade.contains(touched) && !isBorneTurnedOn) {
                    if (!isBorneAlredyClicked){
                        flavorText.setText("Une vieille borne d'arcade ... je suis trop jeune pour ces bêtises.");
                        isBorneAlredyClicked = true;
                    }
                    background.setRegion(zoomBorne);
                    zoneDroite.hide();
                    zoneGauche.hide();
                } else if (porteSortie.contains(touched) && !game.inventory.hasIn(clefs)){
                    flavorText.setText("C'est fermé a clef !");
                } else if (porteSortie.contains(touched) && game.inventory.hasIn(clefs)){
                    flavorText.setText("Je peux enfin sortir ! Compliqué comme début de vacances !");
                    finNiveau = true;
                } else if (zoneDroite.contains(touched)) {
                    if (isDecapsuleurPicked && isPiecePicked) {
                        background.setRegion(principalDecapBottleLess);
                    } else if (isDecapsuleurPicked && !isPiecePicked) {
                        background.setRegion(principalDecapLess);
                    } else if (!isDecapsuleurPicked && !isPiecePicked) {
                        background.setRegion(principal);
                    }
                    zoneGauche.unhide();
                }
            } else if (background.getTexture().toString().matches("image/Warp/zoomBorne*.*") && !isBorneTurnedOn) {
                if (isPiecePicked && insererPiece.contains(touched)) {
                    flavorText.setText("Elle s'est allumée !");
                    game.inventory.remove(piece);
                    isBorneTurnedOn = true;
                    background.setRegion(zoomBorneAllum);
                    flavorText.setText("Tiens, pas de jeu : à la place, deux chiffres sont apparus ...");
                } else if (zoneQuitter.contains(touched)) {
                    if (isBorneTurnedOn) {
                        background.setRegion(entree_BAllum);
                    } else if (!isBorneTurnedOn) {
                        background.setRegion(entree);
                    }
                    zoneDroite.unhide();
                }
            } else if (background.getTexture().toString().matches("image/Warp/zoomBorne*.*") && isBorneTurnedOn) {
                if (zoneQuitter.contains(touched)) {
                    background.setRegion(entree_BAllum);
                    zoneDroite.unhide();
                }

            } else if (background.getTexture().toString().matches("image/Warp/salle*.*")) {
                if (brosse.contains(touched)) {
                    flavorText.setText("Une brosse métallique ...");
                    game.inventory.add(brosse);
                    background.setRegion(salleMainSansBross);
                    isBrossePicked = true;
                } else if (table1.contains(touched) && !isChev1Bross) {
                    background.setRegion(zoomChev1Gratt);
                    zoneGauche.hide();
                } else if (table1.contains(touched) && !isChev2Bross) {
                    background.setRegion(zoomChev2Gratt);
                    zoneGauche.hide();
                } else if (table1.contains(touched) && isChev1Bross) {
                    background.setRegion(zoomChev1);
                    zoneGauche.hide();
                } else if (table2.contains(touched) && isChev2Bross) {
                    background.setRegion(zoomChev2);
                    zoneGauche.hide();
                } else if (photoEncadree.contains(touched) && !isCoffreDiscovered) {
                    if (!isTableauAlreadyClicked){
                        flavorText.setText("Ce pixel-art de space-invader est sublime !");
                        isTableauAlreadyClicked = true;
                    }
                    background.setRegion(zoomTableau);
                    zoneGauche.hide();
                } else if (photoEncadree.contains(touched) && isCoffreDiscovered && !game.inventory.hasIn(clefs)){
                    background.setRegion(zoomCoffre);
                    zoneGauche.hide();
                } else if(photoEncadree.contains(touched) && isCoffreDiscovered && game.inventory.hasIn(clefs)){
                    background.setRegion(coffreOuvertSsCle);
                    zoneGauche.hide();
                } else if (zoneGauche.contains(touched)) {
                    if (isDecapsuleurPicked && isPiecePicked) {
                        background.setRegion(principalDecapBottleLess);
                    } else if (isDecapsuleurPicked && !isPiecePicked) {
                        background.setRegion(principalDecapLess);
                    } else if (!isDecapsuleurPicked && !isPiecePicked) {
                        background.setRegion(principal);
                    }
                    zoneDroite.unhide();
                }

            } else if (background.getTexture().toString().matches("image/Warp/tableau.*") && !isCoffreDiscovered) {
                if (boutonTableau.contains(touched)) {
                    flavorText.setText("Il y avait un coffre-fort caché derrière !");
                    isCoffreDiscovered = true;
                    background.setRegion(zoomCoffre);
                } else if (zoneQuitter.contains(touched)) {
                    if (!isBrossePicked){
                        background.setRegion(salleMain);
                    } else if (isBrossePicked && !isChev1Bross && !isChev2Bross){
                        background.setRegion(salleMainSansBross);
                    } else if (isBrossePicked && isChev1Bross && !isChev2Bross){
                        background.setRegion(salleMainSansBross_chev1Bross);
                    } else if (isBrossePicked && !isChev1Bross && isChev2Bross){
                        background.setRegion(salleMainSansBross_chev2Bross);
                    } else if (isBrossePicked && isChev1Bross && isChev2Bross){
                        background.setRegion(salleMainSansBross_chevBross);
                    }
                    zoneGauche.unhide();
                }
            } else if (background.getTexture().toString().matches("image/Warp/coffre*.*") && isCoffreDiscovered && !game.inventory.hasIn(clefs)) {

                if (zoneQuitter.contains(touched)) {
                    if (!isBrossePicked){
                        background.setRegion(salleMain);
                    } else if (isBrossePicked && !isChev1Bross && !isChev2Bross){
                        background.setRegion(salleMainSansBross);
                    } else if (isBrossePicked && isChev1Bross && !isChev2Bross){
                        background.setRegion(salleMainSansBross_chev1Bross);
                    } else if (isBrossePicked && !isChev1Bross && isChev2Bross){
                        background.setRegion(salleMainSansBross_chev2Bross);
                    } else if (isBrossePicked && isChev1Bross && isChev2Bross){
                        background.setRegion(salleMainSansBross_chevBross);
                    }
                    zoneGauche.unhide();
                } else {
                    if (background.getTexture().toString().matches("image/Warp/coffre0.png")){
                        for (int i=0;i<9;i++){
                            if(coffreBoutons[i].contains(touched)){
                                background.setRegion(coffreEtoiles[1]);
                                codeCompose[0] = i;
                            }
                        }
                    } else if (background.getTexture().toString().matches("image/Warp/coffre1.png")){
                        for (int i=0;i<9;i++){
                            if(coffreBoutons[i].contains(touched)){
                                background.setRegion(coffreEtoiles[2]);
                                codeCompose[1] = i;
                            }
                        }
                    } else if (background.getTexture().toString().matches("image/Warp/coffre2.png")){
                        for (int i=0;i<9;i++){
                            if(coffreBoutons[i].contains(touched)){
                                background.setRegion(coffreEtoiles[3]);
                                codeCompose[2] = i;
                            }
                        }
                    } else if (background.getTexture().toString().matches("image/Warp/coffre3.png")){
                        for (int i=0;i<9;i++){
                            if(coffreBoutons[i].contains(touched)){
                                background.setRegion(coffreEtoiles[4]);
                                codeCompose[3] = i;
                            }
                        }
                        for (int i=0;i<4;i++){
                            if (codeCompose[i]+1 == code[i]){
                                background.setRegion(coffreOK);
                                isOpened = true;
                            } else {
                                background.setRegion(coffreKO);
                            }
                        }
                    } else if(background.getTexture().toString().matches("image/Warp/coffreKO.png")){
                        for (int i=0;i<9;i++){
                            if(coffreBoutons[i].contains(touched)){
                                background.setRegion(coffreEtoiles[0]);
                            }
                        }
                    } else if(background.getTexture().toString().matches("image/Warp/coffreOK.png")){
                        if(poigneeCoffre.contains(touched)){
                            background.setRegion(coffreOuvert);
                        }
                    }
                }
            } else if(background.getTexture().toString().matches("image/Warp/Open_coffre.png")){
                if(zoneClefs.contains(touched)){
                    background.setRegion(coffreOuvertSsCle);
                    game.inventory.add(clefs);
                } else if (zoneQuitter.contains(touched)) {
                    if (!isBrossePicked){
                        background.setRegion(salleMain);
                    } else if (isBrossePicked && !isChev1Bross && !isChev2Bross){
                        background.setRegion(salleMainSansBross);
                    } else if (isBrossePicked && isChev1Bross && !isChev2Bross){
                        background.setRegion(salleMainSansBross_chev1Bross);
                    } else if (isBrossePicked && !isChev1Bross && isChev2Bross){
                        background.setRegion(salleMainSansBross_chev2Bross);
                    } else if (isBrossePicked && isChev1Bross && isChev2Bross){
                        background.setRegion(salleMainSansBross_chevBross);
                    }
                    zoneGauche.unhide();
                }

            } else if(background.getTexture().toString().matches("image/Warp/Open_coffre_ss_cle.png")){
                 if (zoneQuitter.contains(touched)) {
                    if (!isBrossePicked){
                        background.setRegion(salleMain);
                    } else if (isBrossePicked && !isChev1Bross && !isChev2Bross){
                        background.setRegion(salleMainSansBross);
                    } else if (isBrossePicked && isChev1Bross && !isChev2Bross){
                        background.setRegion(salleMainSansBross_chev1Bross);
                    } else if (isBrossePicked && !isChev1Bross && isChev2Bross){
                        background.setRegion(salleMainSansBross_chev2Bross);
                    } else if (isBrossePicked && isChev1Bross && isChev2Bross){
                        background.setRegion(salleMainSansBross_chevBross);
                    }
                    zoneGauche.unhide();
                }

            } else if (background.getTexture().toString().matches("image/Warp/zoomChev1.*")) {
                if (isBrossePicked && chev1.contains(touched) && !isChev1Bross) {
                    flavorText.setText("La peinture s'écaille, en grattant avec la brosse je pourrais voir ce qu'il y avait écris avant.");
                    isChev1Bross = true;
                    background.setRegion(zoomChev1Gratt);
                } else if (!isBrossePicked && chev1.contains(touched)) {
                    flavorText.setText("La peinture s'écaille...");
                } else if (zoneQuitter.contains(touched)) {
                    if (!isBrossePicked){
                        background.setRegion(salleMain);
                        zoneGauche.unhide();
                    } else if (isBrossePicked && !isChev1Bross && !isChev2Bross){
                        background.setRegion(salleMainSansBross);
                        zoneGauche.unhide();
                    } else if (isBrossePicked && isChev1Bross && !isChev2Bross){
                        background.setRegion(salleMainSansBross_chev1Bross);
                        zoneGauche.unhide();
                    } else if (isBrossePicked && !isChev1Bross && isChev2Bross){
                        background.setRegion(salleMainSansBross_chev2Bross);
                        zoneGauche.unhide();
                    } else if (isBrossePicked && isChev1Bross && isChev2Bross){
                        background.setRegion(salleMainSansBross_chevBross);
                        zoneGauche.unhide();
                    }
                }
            } else if (background.getTexture().toString().matches("image/Warp/zoomChev2.*")) {
                if (isBrossePicked && chev2.contains(touched) && !isChev2Bross) {
                    flavorText.setText("La peinture s'écaille, en grattant avec la brosse je pourrais voir ce qu'il y avait écris avant.");
                    isChev2Bross = true;
                    background.setRegion(zoomChev1Gratt);
                } else if (!isBrossePicked && chev2.contains(touched)) {
                    flavorText.setText("La peinture s'écaille...");
                } else if (zoneQuitter.contains(touched)) {
                    if (!isBrossePicked){
                        background.setRegion(salleMain);
                        zoneGauche.unhide();
                    } else if (isBrossePicked && !isChev1Bross && !isChev2Bross){
                        background.setRegion(salleMainSansBross);
                        zoneGauche.unhide();
                    } else if (isBrossePicked && isChev1Bross && !isChev2Bross){
                        background.setRegion(salleMainSansBross_chev1Bross);
                        zoneGauche.unhide();
                    } else if (isBrossePicked && !isChev1Bross && isChev2Bross){
                        background.setRegion(salleMainSansBross_chev2Bross);
                        zoneGauche.unhide();
                    } else if (isBrossePicked && isChev1Bross && isChev2Bross){
                        background.setRegion(salleMainSansBross_chevBross);
                        zoneGauche.unhide();
                    }
                }
            }
        }
        super.render(delta);
        game.batch.end();
    }

    @Override
    public void dispose(){
        super.dispose();
        principal.dispose();
        salleMain.dispose();
        salleMainSansBross.dispose();
        principalDecapLess.dispose();
        principalDecapBottleLess.dispose();
        zoomBouteille.dispose();
        zoomChev1.dispose();
        zoomChev2.dispose();
        zoomChev1Gratt.dispose();
        zoomChev2Gratt.dispose();
        zoomBorne.dispose();
        zoomBorneAllum.dispose();
        zoomTableau.dispose();
        zoomCoffre.dispose();
        boutonTableau.dispose();
        entree.dispose();
        entree_BAllum.dispose();
        bouteille.dispose();
        bouchonBouteille.dispose();
        zoneObjetDansBouteille.dispose();
        zoneDecapsuleur.dispose();
        zoneBiere.dispose();
        table1.dispose();
        table2.dispose();
        brosse.dispose();
        porteSortie.dispose();
        borneArcade.dispose();
        photoEncadree.dispose();
        insererPiece.dispose();
        chev1.dispose();
        chev2.dispose();
        clefs.dispose();
        brosse.dispose();
        decapsuleur.dispose();
        for (GameObject chiffre : coffreBoutons){
            chiffre.dispose();
        }
        for (Texture etoiles : coffreEtoiles){
            etoiles.dispose();
        }
        zoneQuitter.dispose();
    }
}
