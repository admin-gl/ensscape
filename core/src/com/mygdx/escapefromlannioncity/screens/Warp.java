
package com.mygdx.escapefromlannioncity.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.utility.GameObject;

/**
 * Ecran pour l'enigme du Warp
 */
public class Warp extends UI {

    //Background textures
    private final Texture principal;
    private final Texture principalBrLess;
    private final Texture principalBrDecapLess;
    private final Texture principalBrDecapBottleLess;
    private final Texture principalDecapLess;
    private final Texture principalDecapBottleLess;
    private final Texture salleMain;
    private final Texture salleMain_chevBross;
    private final Texture salleMain_chev1Bross;
    private final Texture salleMain_chev2Bross;
    private final Texture zoomBouteille;
    private final Texture zoomChev1;
    private final Texture zoomChev2;
    private final Texture zoomChev1Gratt;
    private final Texture zoomChev2Gratt;
    private final Texture zoomBorne;
    private final Texture zoomBorneAllum;
    private final Texture zoomTableau;
    private Texture zoomCoffre;
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
    private final GameObject zoneBrosse;

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
    private final GameObject brosse;

    //utilitaire
    private final GameObject zoneQuitter;
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
        super(game, "music/enigme_3.wav", new String[]{"1. La bouteille de biere contient un objet singulier ...\n",
                "2. La photo du space-invader semble louche ...\n",
                "3. Les chevalets de la salle ont ete repeint tres recemment. "}
                ,timeTotal,bonus,usedHint);
        // init textures zone comptoir
        principal = new Texture(Gdx.files.internal("image/Warp/comptoir.png"));
        principalBrLess = new Texture(Gdx.files.internal("image/Warp/comptoir_ss_brosse.png"));
        principalBrDecapLess = new Texture(Gdx.files.internal("image/Warp/comptoir_ss_decap_br.png"));
        principalBrDecapBottleLess = new Texture(Gdx.files.internal("image/Warp/comptoir_ss_rien.png"));
        principalDecapLess = new Texture(Gdx.files.internal("image/Warp/comptoir_ss_decap.png"));
        principalDecapBottleLess = new Texture(Gdx.files.internal("image/Warp/comptoir_ss_decap_bout.png"));
        zoomBouteille = new Texture(Gdx.files.internal("image/Warp/zoomBouteille.png"));
        // init textures salle
        salleMain = new Texture(Gdx.files.internal("image/Warp/salleMain.png"));
        salleMain_chevBross = new Texture(Gdx.files.internal("image/Warp/salleMainChB.png"));
        salleMain_chev1Bross = new Texture(Gdx.files.internal("image/Warp/salleMainCh1B.png"));
        salleMain_chev2Bross = new Texture(Gdx.files.internal("image/Warp/salleMainCh2B.png"));
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
        bouteille = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 147+5,144-91+11,10,22,"bouteille");
        bouchonBouteille = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 121+10,144-26+4,20,8,"bouchon");
        zoneDecapsuleur = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 136,144-89,11,5,"décapsuleur");
        zoneObjetDansBouteille = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 128+7,144-117+7,14,14,"???");
        zoneBiere = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 109,144-84,14,14,"biere");
        zoneBrosse = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 106+5,144-63+4,10,8,"brosse metallique");

        zoneBrosse.resize();
        bouteille.resize();
        zoneDecapsuleur.resize();
        zoneObjetDansBouteille.resize();
        bouchonBouteille.resize();
        zoneBiere.resize();

        // init des objets de la salle
        table1 = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 86+28,144-123+7,57,14,"table");
        table2 = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 181+28,144-127+7,57,14,"table");
        photoEncadree = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 117+30,144-54+20,60,40,"photo encadree");

        photoEncadree.resize();
        table1.resize();
        table2.resize();

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
        code = new int[]{4, 9, 2, 9};
        codeCompose = new int[4];
        poigneeCoffre.resize();
        zoneClefs.resize();
        // init des objets dans le zoom borne
        insererPiece = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 93+4,144-100+4,8,8,"une fente pour la monnaie");

        insererPiece.resize();

        //init des objets zoom table1 et 2
        chev1 = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 78+50,144-108+44,100,88,"chevalet");
        chev2 = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 78+50,144-108+44,100,88,"chevalet");

        chev1.resize();
        chev2.resize();


        // init des objets de l'inventaire
        piece = new GameObject(Gdx.files.internal(("image/Warp/jetonArcade.png")), 0,0,10,10,"");
        decapsuleur = new GameObject(Gdx.files.internal(("image/Warp/decapsuleur.png")), 0,0,10,10,"");
        clefs = new GameObject(Gdx.files.internal(("image/Warp/cle.png")), 0,0,10,10,"");
        brosse = new GameObject(Gdx.files.internal(("image/Warp/brosse.png")), 0,0,10,10,"");

        piece.resize();
        brosse.resize();
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

        bg = new SpriteDrawable(background);

    }

    @Override
    public void render(float delta){
        super.setupRender();

        if(timerOn) {
            if (background.getTexture().toString().matches("image/Warp/comptoir*.*") && !isDecapsuleurPicked) {
                if (!cursor.survol(zoneDecapsuleur, (StretchViewport) viewport)) {
                    if (!cursor.survol(bouteille, (StretchViewport) viewport)) {
                        if (!cursor.survol(zoneBiere, (StretchViewport) viewport)) {
                            if (!isBrossePicked) {
                                cursor.survol(zoneBrosse, (StretchViewport) viewport);
                            }
                        }
                    }
                }
            } else if (background.getTexture().toString().matches("image/Warp/comptoir.*") && !isPiecePicked && isDecapsuleurPicked) {
                if (!cursor.survol(bouteille, (StretchViewport) viewport)) {
                    if (!cursor.survol(zoneBiere, (StretchViewport) viewport)) {
                        if (!isBrossePicked) {
                            cursor.survol(zoneBrosse, (StretchViewport) viewport);
                        }
                    }
                }
            } else if (background.getTexture().toString().matches("image/Warp/comptoir.*") && isPiecePicked && isDecapsuleurPicked) {
                if (!cursor.survol(zoneBiere, (StretchViewport) viewport)) {
                    if (!isBrossePicked) {
                        cursor.survol(zoneBrosse, (StretchViewport) viewport);
                    }
                }
            } else if (background.getTexture().toString().matches("image/Warp/zoomBouteille.*") && !isPiecePicked) {
                if (!cursor.survol(zoneObjetDansBouteille, (StretchViewport) viewport)) {
                    cursor.survol(bouchonBouteille, (StretchViewport) viewport);
                }
            } else if (background.getTexture().toString().matches("image/Warp/entree*.*")) {
                if (!cursor.survol(borneArcade, (StretchViewport) viewport)) {
                    cursor.survol(porteSortie, (StretchViewport) viewport);
                }
            } else if (background.getTexture().toString().matches("image/Warp/tableau.*")) {
                cursor.survol(boutonTableau, (StretchViewport) viewport);
            } else if (background.getTexture().toString().matches("image/Warp/coffre*.*")) {
                if (!cursor.survol(coffreBoutons, (StretchViewport) viewport)) {
                    cursor.survol(poigneeCoffre, (StretchViewport) viewport);
                }
            } else if (background.getTexture().toString().matches("image/Warp/Open_coffre.png")) {
                cursor.survol(zoneClefs, (StretchViewport) viewport);
            } else if (background.getTexture().toString().matches("image/Warp/salleMain.*")) {
                if (!cursor.survol(table1, (StretchViewport) viewport)) {
                    if (!cursor.survol(table2, (StretchViewport) viewport)) {
                        cursor.survol(photoEncadree, (StretchViewport) viewport);
                    }
                }
            } else if (background.getTexture().toString().matches("image/Warp/salleMainSansBross*.*")) {
                if (!cursor.survol(table1, (StretchViewport) viewport)) {
                    if (!cursor.survol(table2, (StretchViewport) viewport)) {
                        cursor.survol(photoEncadree, (StretchViewport) viewport);
                    }
                }
            } else if (background.getTexture().toString().matches("image/Warp/zoomBorne*.*")) {
                if (!isBorneTurnedOn) {
                    cursor.survol(insererPiece, (StretchViewport) viewport);
                }
            } else if (background.getTexture().toString().matches("image/Warp/zoomChev*.*")) {
                if (!cursor.survol(chev1, (StretchViewport) viewport)) {
                    cursor.survol(chev2, (StretchViewport) viewport);
                }
            }

            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && textZone.isHidden()) {

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
                        if (!isBiereAlreadyClicked) {
                            flavorText.setText("Une bouteille de biere ... etrange, je ne connais pas la marque.");
                            isBiereAlreadyClicked = true;
                        }
                        background.setRegion(zoomBouteille);
                        zoneDroite.hide();
                        zoneGauche.hide();
                    } else if (zoneDecapsuleur.contains(touched) && !isDecapsuleurPicked && !isBrossePicked) {
                        flavorText.setText("Ceci pourrait m'aider a m'ouvrir la bouteille.\n");
                        game.inventory.add(decapsuleur);
                        isDecapsuleurPicked = true;
                        background.setRegion(principalDecapLess);
                    } else if (zoneDecapsuleur.contains(touched) && !isDecapsuleurPicked && isBrossePicked) {
                        flavorText.setText("Ceci pourrait m'aider a m'ouvrir la bouteille.\n");
                        game.inventory.add(decapsuleur);
                        isDecapsuleurPicked = true;
                        background.setRegion(principalBrDecapLess);
                    } else if (zoneBrosse.contains(touched) && !isBrossePicked) {
                        flavorText.setText("Une brosse metallique...\nCe n'est pas ici qu'elle devrait etre rangee. ");
                        game.inventory.add(brosse);
                        isBrossePicked = true;
                        if (isDecapsuleurPicked && isPiecePicked) {
                            background.setRegion(principalBrDecapBottleLess);
                        } else if (!isDecapsuleurPicked) {
                            background.setRegion(principalBrLess);
                        } else if (isDecapsuleurPicked && !isPiecePicked) {
                            background.setRegion(principalBrDecapLess);
                        }

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
                    } else if (zoneDroite.contains(touched)) {
                        if (!isChev1Bross && !isChev2Bross) {
                            background.setRegion(salleMain);
                        } else if (isChev1Bross && !isChev2Bross) {
                            background.setRegion(salleMain_chev1Bross);
                        } else if (!isChev1Bross && isChev2Bross) {
                            background.setRegion(salleMain_chev2Bross);
                        } else if (isChev1Bross && isChev2Bross) {
                            background.setRegion(salleMain_chevBross);
                        }
                        zoneDroite.hide();
                    }
                } else if (background.getTexture().toString().matches("image/Warp/zoomBouteille.png")) {
                    if (isDecapsuleurPicked && bouchonBouteille.contains(touched)) {
                        flavorText.setText("Je peux enfin voir ce qu'il y a dedans !\nUn jeton d'arcade !");
                        game.inventory.remove(decapsuleur);
                        game.inventory.add(piece);
                        isPiecePicked = true;
                        if (!isBrossePicked) {
                            background.setRegion(principalDecapBottleLess);
                        } else {
                            background.setRegion(principalBrDecapBottleLess);
                        }
                        zoneGauche.unhide();
                        zoneDroite.unhide();
                    } else if (!isDecapsuleurPicked && bouchonBouteille.contains(touched)) {
                        flavorText.setText("J'aimerais bien l'ouvrir pour voir ce qu'il y a dedans ...");
                    } else if (zoneObjetDansBouteille.contains(touched)) {
                        flavorText.setText("Il y a un objet au fond de la bouteille, mais je n'arrive pas a voir ce que c'est.");
                    } else if (zoneQuitter.contains(touched) && isPiecePicked) {
                        background.setRegion(principalDecapBottleLess);
                        if (isBrossePicked) {
                            background.setRegion(principalBrDecapLess);
                        } else {
                            background.setRegion(principalDecapLess);
                        }
                        zoneGauche.unhide();
                        zoneDroite.unhide();
                    } else if (zoneQuitter.contains(touched) && !isPiecePicked && !isDecapsuleurPicked) {
                        if (isBrossePicked) {
                            background.setRegion(principalBrLess);
                        } else {
                            background.setRegion(principal);
                        }
                        zoneDroite.unhide();
                        zoneGauche.unhide();
                    } else if (zoneQuitter.contains(touched) && !isPiecePicked && isDecapsuleurPicked) {
                        if (isBrossePicked) {
                            background.setRegion(principalBrDecapLess);
                        } else {
                            background.setRegion(principalDecapLess);
                        }
                        zoneDroite.unhide();
                        zoneGauche.unhide();
                    }
                } else if (background.getTexture().toString().matches("image/Warp/entree*.*")) {
                    if (borneArcade.contains(touched) && !isBorneTurnedOn) {
                        if (!isBorneAlredyClicked) {
                            flavorText.setText("Une vieille borne d'arcade ... je suis trop jeune pour ces betises.");
                            isBorneAlredyClicked = true;
                        }
                        background.setRegion(zoomBorne);
                        zoneDroite.hide();
                        zoneGauche.hide();
                    } else if (borneArcade.contains(touched) && isBorneTurnedOn) {
                        background.setRegion(zoomBorneAllum);
                        zoneDroite.hide();
                        zoneGauche.hide();
                    } else if (porteSortie.contains(touched) && !game.inventory.hasIn(clefs)) {
                        flavorText.setText("C'est ferme a clef !");
                    } else if (porteSortie.contains(touched) && game.inventory.hasIn(clefs)) {
                        flavorText.setText("Je peux enfin sortir ! Complique comme debut de vacances !");
                        finNiveau = true;
                    } else if (zoneDroite.contains(touched)) {
                        if (isDecapsuleurPicked && isPiecePicked) {
                            if (isBrossePicked) {
                                background.setRegion(principalBrDecapBottleLess);
                            } else {
                                background.setRegion(principalDecapBottleLess);
                            }
                        } else if (isDecapsuleurPicked && !isPiecePicked) {
                            if (isBrossePicked) {
                                background.setRegion(principalBrDecapLess);
                            } else {
                                background.setRegion(principalDecapLess);
                            }
                        } else if (!isDecapsuleurPicked && !isPiecePicked) {
                            if (isBrossePicked) {
                                background.setRegion(principalBrLess);
                            } else {
                                background.setRegion(principal);
                            }
                        }
                        zoneGauche.unhide();
                    }
                } else if (background.getTexture().toString().matches("image/Warp/zoomBorne*.*") && !isBorneTurnedOn) {
                    if (isPiecePicked && insererPiece.contains(touched)) {
                        flavorText.setText("Elle s'est allumee !");
                        game.inventory.remove(piece);
                        isBorneTurnedOn = true;
                        background.setRegion(zoomBorneAllum);
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
                    if (table1.contains(touched) && !isChev1Bross) {
                        background.setRegion(zoomChev1);
                        zoneGauche.hide();
                    } else if (table1.contains(touched) && isChev1Bross) {
                        background.setRegion(zoomChev1Gratt);
                        zoneGauche.hide();
                    } else if (table2.contains(touched) && !isChev2Bross) {
                        background.setRegion(zoomChev2);
                        zoneGauche.hide();
                    } else if (table2.contains(touched) && isChev2Bross) {
                        background.setRegion(zoomChev2Gratt);
                        zoneGauche.hide();
                    } else if (photoEncadree.contains(touched) && !isCoffreDiscovered) {
                        if (!isTableauAlreadyClicked) {
                            flavorText.setText("Ce pixel-art de space-invader est sublime !");
                            isTableauAlreadyClicked = true;
                        }
                        background.setRegion(zoomTableau);
                        zoneGauche.hide();
                    } else if (photoEncadree.contains(touched) && isCoffreDiscovered && !game.inventory.hasIn(clefs) && !isOpened) {
                        background.setRegion(zoomCoffre);
                        zoneGauche.hide();
                    } else if (photoEncadree.contains(touched) && isCoffreDiscovered && game.inventory.hasIn(clefs) && isOpened) {
                        background.setRegion(coffreOuvertSsCle);
                        zoneGauche.hide();
                    } else if (photoEncadree.contains(touched) && isCoffreDiscovered && !game.inventory.hasIn(clefs) && isOpened) {
                        background.setRegion(coffreOuvert);
                        zoneGauche.hide();
                    } else if (zoneGauche.contains(touched)) {
                        if (isDecapsuleurPicked && isPiecePicked) {
                            if (isBrossePicked) {
                                background.setRegion(principalBrDecapBottleLess);
                            } else {
                                background.setRegion(principalDecapBottleLess);
                            }
                        } else if (isDecapsuleurPicked && !isPiecePicked) {
                            if (isBrossePicked) {
                                background.setRegion(principalBrDecapLess);
                            } else {
                                background.setRegion(principalDecapLess);
                            }
                        } else if (!isDecapsuleurPicked && !isPiecePicked) {
                            if (isBrossePicked) {
                                background.setRegion(principalBrLess);
                            } else {
                                background.setRegion(principal);
                            }
                        }
                        zoneDroite.unhide();
                    }

                } else if (background.getTexture().toString().matches("image/Warp/tableau.*") && !isCoffreDiscovered) {
                    if (boutonTableau.contains(touched)) {
                        flavorText.setText("Il y avait un coffre-fort cache derriere !");
                        isCoffreDiscovered = true;
                        background.setRegion(zoomCoffre);
                    } else if (zoneQuitter.contains(touched)) {
                        if (!isBrossePicked) {
                            background.setRegion(salleMain);
                        } else if (isBrossePicked && !isChev1Bross && !isChev2Bross) {
                            background.setRegion(salleMain);
                        } else if (isBrossePicked && isChev1Bross && !isChev2Bross) {
                            background.setRegion(salleMain_chev1Bross);
                        } else if (isBrossePicked && !isChev1Bross && isChev2Bross) {
                            background.setRegion(salleMain_chev2Bross);
                        } else if (isBrossePicked && isChev1Bross && isChev2Bross) {
                            background.setRegion(salleMain_chevBross);
                        }
                        zoneGauche.unhide();
                    }
                } else if (background.getTexture().toString().matches("image/Warp/coffre*.*") && isCoffreDiscovered && !game.inventory.hasIn(clefs)) {

                    if (zoneQuitter.contains(touched)) {
                        if (!isChev1Bross && !isChev2Bross) {
                            background.setRegion(salleMain);
                        } else if (isChev1Bross && !isChev2Bross) {
                            background.setRegion(salleMain_chev1Bross);
                        } else if (!isChev1Bross && isChev2Bross) {
                            background.setRegion(salleMain_chev2Bross);
                        } else if (isChev1Bross && isChev2Bross) {
                            background.setRegion(salleMain_chevBross);
                        }
                        zoneGauche.unhide();
                    } else {
                        if (background.getTexture().toString().matches("image/Warp/coffre0.png")) {
                            for (int i = 0; i < 9; i++) {
                                if (coffreBoutons[i].contains(touched)) {
                                    background.setRegion(coffreEtoiles[1]);
                                    codeCompose[0] = i;
                                }
                            }
                        } else if (background.getTexture().toString().matches("image/Warp/coffre1.png")) {
                            for (int i = 0; i < 9; i++) {
                                if (coffreBoutons[i].contains(touched)) {
                                    background.setRegion(coffreEtoiles[2]);
                                    codeCompose[1] = i;
                                }
                            }
                        } else if (background.getTexture().toString().matches("image/Warp/coffre2.png")) {
                            for (int i = 0; i < 9; i++) {
                                if (coffreBoutons[i].contains(touched)) {
                                    background.setRegion(coffreEtoiles[3]);
                                    codeCompose[2] = i;
                                }
                            }
                        } else if (background.getTexture().toString().matches("image/Warp/coffre3.png")) {
                            for (int i = 0; i < 9; i++) {
                                if (coffreBoutons[i].contains(touched)) {
                                    background.setRegion(coffreEtoiles[4]);
                                    codeCompose[3] = i;
                                }
                            }
                            for (int i = 0; i < 4; i++) {
                                if (codeCompose[0] + 1 == code[0] && codeCompose[1] + 1 == code[1] && codeCompose[2] + 1 == code[2] && codeCompose[3] + 1 == code[3]) {
                                    background.setRegion(coffreOK);
                                    isOpened = true;
                                } else if (codeCompose[i] + 1 != code[i]) {
                                    background.setRegion(coffreKO);
                                    flavorText.setText("Zut...");
                                }
                            }
                        } else if (background.getTexture().toString().matches("image/Warp/coffreko.png")) {
                            for (int i = 0; i < 9; i++) {
                                if (coffreBoutons[i].contains(touched)) {
                                    background.setRegion(coffreEtoiles[0]);
                                }
                            }
                        } else if (background.getTexture().toString().matches("image/Warp/coffreok.png")) {
                            if (poigneeCoffre.contains(touched)) {
                                flavorText.setText("Je devrais probablement rappeler le patron pour lui expliquer ceci ...");
                                background.setRegion(coffreOuvert);
                            }
                        }
                    }
                } else if (background.getTexture().toString().matches("image/Warp/Open_coffre.png")) {
                    if (zoneClefs.contains(touched)) {
                        background.setRegion(coffreOuvertSsCle);
                        game.inventory.add(clefs);
                    } else if (zoneQuitter.contains(touched)) {
                        if (!isChev1Bross && !isChev2Bross) {
                            background.setRegion(salleMain);
                        } else if (isChev1Bross && !isChev2Bross) {
                            background.setRegion(salleMain_chev1Bross);
                        } else if (!isChev1Bross && isChev2Bross) {
                            background.setRegion(salleMain_chev2Bross);
                        } else if (isChev1Bross && isChev2Bross) {
                            background.setRegion(salleMain_chevBross);
                        }
                        zoneGauche.unhide();
                    }

                } else if (background.getTexture().toString().matches("image/Warp/Open_coffre_ss_cle.png")) {
                    if (zoneQuitter.contains(touched)) {
                        if (!isChev1Bross && !isChev2Bross) {
                            background.setRegion(salleMain);
                        } else if (isChev1Bross && !isChev2Bross) {
                            background.setRegion(salleMain_chev1Bross);
                        } else if (!isChev1Bross && isChev2Bross) {
                            background.setRegion(salleMain_chev2Bross);
                        } else if (isChev1Bross && isChev2Bross) {
                            background.setRegion(salleMain_chevBross);
                        }
                        zoneGauche.unhide();
                    }

                } else if (background.getTexture().toString().matches("image/Warp/zoomChev1.*")) {
                    if (isBrossePicked && chev1.contains(touched) && !isChev1Bross) {
                        flavorText.setText("Un chiffre etait cache derriere !");
                        isChev1Bross = true;
                        background.setRegion(zoomChev1Gratt);
                    } else if (!isBrossePicked && chev1.contains(touched)) {
                        flavorText.setText("La peinture s'ecaille...");
                    } else if (zoneQuitter.contains(touched)) {
                        if (!isChev1Bross && !isChev2Bross) {
                            background.setRegion(salleMain);
                        } else if (isChev1Bross && !isChev2Bross) {
                            background.setRegion(salleMain_chev1Bross);
                        } else if (!isChev1Bross && isChev2Bross) {
                            background.setRegion(salleMain_chev2Bross);
                        } else if (isChev1Bross && isChev2Bross) {
                            background.setRegion(salleMain_chevBross);
                        }
                        zoneGauche.unhide();
                    }
                } else if (background.getTexture().toString().matches("image/Warp/zoomChev2.*")) {
                    if (isBrossePicked && chev2.contains(touched) && !isChev2Bross) {
                        flavorText.setText("Un chiffre etait cache derriere !");
                        isChev2Bross = true;
                        background.setRegion(zoomChev2Gratt);
                    } else if (!isBrossePicked && chev2.contains(touched)) {
                        flavorText.setText("La peinture s'ecaille...");
                    } else if (zoneQuitter.contains(touched)) {
                        if (!isChev1Bross && !isChev2Bross) {
                            background.setRegion(salleMain);
                        } else if (isChev1Bross && !isChev2Bross) {
                            background.setRegion(salleMain_chev1Bross);
                        } else if (!isChev1Bross && isChev2Bross) {
                            background.setRegion(salleMain_chev2Bross);
                        } else if (isChev1Bross && isChev2Bross) {
                            background.setRegion(salleMain_chevBross);
                        }
                        zoneGauche.unhide();
                    }
                }
            }
        }
        super.render(delta);
        game.batch.end();
    }


    /* Getter et setter pour la synchronisation des etats*/
    public boolean hasClef(){
        if(game.inventory.hasIn(clefs)){return true;}
        else{return false;}
    }
    public void setClef(boolean clef){
        if (clef && !game.inventory.hasIn(clefs)) {
            game.inventory.add(clefs);
        }
    }
    public boolean isDecapsuleurPicked() {
        return isDecapsuleurPicked;
    }

    public void setDecapsuleurPicked(boolean decapsuleurPicked) {

        if(decapsuleurPicked && (!isPiecePicked && !isBorneTurnedOn) && !game.inventory.hasIn(decapsuleur)){
            game.inventory.add(decapsuleur);
        }
        isDecapsuleurPicked = decapsuleurPicked;
    }

    public boolean isPiecePicked() {
        return isPiecePicked;
    }

    public void setPiecePicked(boolean piecePicked) {
        if(piecePicked && !this.isBorneTurnedOn && !game.inventory.hasIn(piece)) {
            game.inventory.add(piece);
        }
        isPiecePicked = piecePicked;
    }

    public boolean isBorneTurnedOn() {
        return isBorneTurnedOn;
    }

    public void setBorneTurnedOn(boolean borneTurnedOn) {
        if(borneTurnedOn) {
            background.setRegion(zoomBorneAllum);
        }
        isBorneTurnedOn = borneTurnedOn;
    }

    public boolean isBorneAlredyClicked() {
        return isBorneAlredyClicked;
    }

    public void setBorneAlredyClicked(boolean borneAlredyClicked) {
        isBorneAlredyClicked = borneAlredyClicked;
    }

    public boolean isAreChevBross() {
        return areChevBross;
    }

    public void setAreChevBross(boolean areChevBross) {
        this.areChevBross = areChevBross;
    }

    public boolean isBrossePicked() {
        return isBrossePicked;
    }

    public void setBrossePicked(boolean brossePicked) {

        if(brossePicked && !game.inventory.hasIn(brosse)){
            game.inventory.add(brosse);
        }
        isBrossePicked = brossePicked;

    }

    public boolean isCoffreDiscovered() {
        return isCoffreDiscovered;
    }

    public void setCoffreDiscovered(boolean coffreDiscovered) {
        if(coffreDiscovered && game.inventory.hasIn(clefs)){
            background.setRegion(coffreOuvertSsCle);
        }
        isCoffreDiscovered = coffreDiscovered;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        if(opened){
            if(hasClef()) {
                zoomCoffre = coffreOuvertSsCle;
            }else{
                zoomCoffre = coffreOuvert;
            }
        }
        isOpened = opened;
    }

    public boolean isChev1Bross() {
        return isChev1Bross;
    }

    public void setChev1Bross(boolean chev1Bross) {
        if(chev1Bross){
            background.setRegion(zoomChev1Gratt);
        }
        isChev1Bross = chev1Bross;
    }

    public boolean isChev2Bross() {
        return isChev2Bross;
    }

    public void setChev2Bross(boolean chev2Bross) {
        if(chev2Bross){
            background.setRegion(zoomChev2Gratt);
        }
        isChev2Bross = chev2Bross;
    }

    public boolean isBiereAlreadyClicked() {
        return isBiereAlreadyClicked;
    }

    public void setBiereAlreadyClicked(boolean biereAlreadyClicked) {
        isBiereAlreadyClicked = biereAlreadyClicked;
    }

    public boolean isTableauAlreadyClicked() {
        return isTableauAlreadyClicked;
    }

    public void setTableauAlreadyClicked(boolean tableauAlreadyClicked) {
        isTableauAlreadyClicked = tableauAlreadyClicked;
    }

    /**
     * Met toutes les images de fonds dans les etats qu'elles doivent avoir
     */
    public void setBackground(){
        if (!isChev1Bross && !isChev2Bross) {
            background.setRegion(salleMain);
        } else if (isChev1Bross && !isChev2Bross) {
            background.setRegion(salleMain_chev1Bross);
        } else if (!isChev1Bross && isChev2Bross) {
            background.setRegion(salleMain_chev2Bross);
        } else if (isChev1Bross && isChev2Bross) {
            background.setRegion(salleMain_chevBross);
        }
        if (isDecapsuleurPicked && isPiecePicked) {
            if (isBrossePicked) {
                background.setRegion(principalBrDecapBottleLess);
            } else {
                background.setRegion(principalBrDecapLess);
            }
        } else if (isDecapsuleurPicked && !isPiecePicked) {
            if (isBrossePicked) {
                background.setRegion(principalBrDecapLess);
            } else {
                background.setRegion(principalDecapLess);
            }
        } else if (!isDecapsuleurPicked && !isPiecePicked) {
            if (isBrossePicked) {
                background.setRegion(principalBrLess);
            } else {
                background.setRegion(principal);
            }
        }
    }

    @Override
    public void dispose(){
        super.dispose();
        principal.dispose();
        salleMain.dispose();
        salleMain_chevBross.dispose();
        salleMain_chev1Bross.dispose();
        salleMain_chev2Bross.dispose();
        principalDecapLess.dispose();
        principalBrDecapLess.dispose();
        principalDecapBottleLess.dispose();
        principalBrDecapBottleLess.dispose();
        principalBrLess.dispose();
        poigneeCoffre.dispose();
        zoneClefs.dispose();
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
