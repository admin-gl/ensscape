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

public class ParcStAnne extends UI {

    //Background textures
    private final Texture principal;
    private final Texture zoomChapelle;
    private final Texture zoomBanc;
    private final Texture interChapelle;
    private final Texture principalBatonless;
    private final Texture zoomChapelleOpen;
    private final Texture zoomBancStringOnly;
    private final Texture zoomBancVide;
    private final Texture zoomBancMetalOnly;
    private final Texture interChapelleLadderless;
    private final Texture zoomCadena;

    // tableau principal
    private final GameObject zoneBaton;
    private final GameObject banc;
    private final GameObject grille;
    private final GameObject lac;
    private final GameObject zoneCraft;

    // tableau zoom chapelle
    private final GameObject porte;
    private final GameObject panneau;
    private final GameObject zonePapier;

    // tableau zoom banc
    private final GameObject zoneFil;
    private final GameObject journalDePeche;
    private final GameObject zoneFeraille;

    // tableau zoom cadena
    private final AnimatedGameObject[] zoneChiffres;
    private final int[] code;

    // tableau interieur
    private final GameObject zoneEchelle;

    // pour l'inventaire
    private final GameObject baton;
    private final GameObject ficelle;
    private final GameObject ferraille;
    private final GameObject canneAPeche;
    private final GameObject papierCode;
    private final GameObject echelle;

    //utilitaire
    private final GameObject zoneQuitter;
    private boolean isOpened;
    private boolean isBatonPicked;
    private boolean isStringPicked;
    private boolean isMetalPicked;
    private boolean isPaperPicked;


    public ParcStAnne(EscapeFromLannionCity game) {
        super(game, "music/enigme_2.wav", new String[]{"1. Posez vous quelque part avec certains objets afin de pouvoir en creer un nouveau\n",
                                                                    "2. Une canne A peche peut vos servir a atteindre des profondeur, mais aussi des hauteurs\n",
                                                                    "3. Le papier que vous avez ramasser contient un code. Il est surement utilisable quelque part... comme une porte"});
        // init textures zone principale
        principal = new Texture(Gdx.files.internal("image/ParcStAnne/parcVueGloballe.png"));
        principalBatonless = new Texture(Gdx.files.internal("image/ParcStAnne/parcVueGloballeBatonless.png"));
        // init textures chapelle
        zoomChapelle = new Texture(Gdx.files.internal("image/ParcStAnne/parcStAnneChap.png"));
        zoomChapelleOpen = new Texture(Gdx.files.internal("image/ParcStAnne/parcStAnneChapOuv.png"));
        // init textures zoom banc
        zoomBanc = new Texture(Gdx.files.internal("image/ParcStAnne/banczoomed.png"));
        zoomBancVide = new Texture(Gdx.files.internal("image/ParcStAnne/sansobjetbanc.png"));
        zoomBancStringOnly = new Texture(Gdx.files.internal("image/ParcStAnne/sansFerailleParc.png"));
        zoomBancMetalOnly = new Texture(Gdx.files.internal("image/ParcStAnne/sansFilBanc.png"));
        // init textures interrieur chapelle
        interChapelle = new Texture(Gdx.files.internal("image/ParcStAnne/intChapelle.png"));
        interChapelleLadderless = new Texture(Gdx.files.internal("image/ParcStAnne/SansEchelleChapelle.png"));
        // init texture zoom cadena
        zoomCadena = new Texture(Gdx.files.internal("image/ParcStAnne/CadenasStAnne.png"));
        // mise en place du background principal
        background = new Sprite(principal);

        // init des objets de la scene principale
        banc = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 92,27,58,16,"banc");
        grille = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 156,129,38,30,"grille");
        lac = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 170,70,170,68,"lac");
        zoneCraft = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 45.5f,89,31,22,"etabli");
        zoneBaton = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 26,65.5f,24,7,"baton");

        banc.resize();
        grille.resize();
        lac.resize();
        zoneCraft.resize();
        zoneBaton.resize();

        // init des objets de la chapelle
        porte = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 127.5f,65,31,48,"porte");
        panneau = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 86,80,24,28,"notice");
        zonePapier = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 219.5f,103.5f,31,13,"???");

        porte.resize();
        panneau.resize();
        zonePapier.resize();

        // init des objets du zoom banc
        zoneFil = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 172,73,62,40,"ficelle");
        zoneFeraille = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 65.5f,81,25,26,"feraille");
        journalDePeche = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 119.5f,33.5f,127,35,"journal de peche");

        zoneFil.resize();
        zoneFeraille.resize();
        journalDePeche.resize();

        // init des objets du zoom cadena
        zoneChiffres = new AnimatedGameObject[4];
        Texture[] Chiffre = new Texture[10];
        int i;
        for (i=0;i<10;i++) {
            Chiffre[i] = new Texture(Gdx.files.internal("animation/ParcStAnne/Cadena/num"+i+".png"));
        }
        for (i = 0; i <4; i++) {
            zoneChiffres[i] = new AnimatedGameObject(Chiffre);
            zoneChiffres[i].setPlayMode(Animation.PlayMode.LOOP);
            zoneChiffres[i].setSize(21, 46);
            zoneChiffres[i].setCenterPos( 81.5f+i*31, 62);
            zoneChiffres[i].resize();
        }
        code = new int[]{1, 8, 2, 6};

        // init des objets de l'interieur de la chapelle
        zoneEchelle = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 113,74,26,66,"echelle");

        zoneEchelle.resize();

        // init des objets de l'inventaire
        baton = new GameObject(Gdx.files.internal(("image/ParcStAnne/baton.png")), 0,0,10,10,"");
        papierCode = new GameObject(Gdx.files.internal(("image/ParcStAnne/codeCadenas.png")), 0,0,10,10,"");
        ficelle = new GameObject(Gdx.files.internal(("image/ParcStAnne/bobine.png")), 0,0,10,10,"");
        ferraille = new GameObject(Gdx.files.internal(("image/ParcStAnne/feraille.png")), 0,0,10,10,"");
        canneAPeche = new GameObject(Gdx.files.internal(("image/ParcStAnne/canapeche.png")), 0,0,10,10,"");
        echelle = new GameObject(Gdx.files.internal(("image/ParcStAnne/echelle.png")), 0,0,10,10,"");

        baton.resize();
        papierCode.resize();
        ficelle.resize();
        ferraille.resize();
        canneAPeche.resize();
        echelle.resize();

        // init des utilitaires
        zoneQuitter = new GameObject(Gdx.files.internal(("image/Utilitaire/empty.png")), 223,125,6,6,"");
        isOpened = false;
        isBatonPicked = false;
        isMetalPicked = false;
        isStringPicked = false;
        isPaperPicked = false;

        zoneQuitter.resize();

        zoneDroite.hide();

    }

    @Override
    public void render(float delta){
        super.setupRender();

        if(background.getTexture().toString().matches("image/ParcStAnne/CadenasStAnne.*")){
            for(AnimatedGameObject chiffre : zoneChiffres){
                chiffre.drawFix(game.batch);
            }
        }

        if (background.getTexture().toString().matches("image/ParcStAnne/parcStAnneChap.*")){
            if(!cursor.survol(porte, (StretchViewport) viewport)){
                if(!cursor.survol(panneau, (StretchViewport) viewport) && !game.inventory.hasIn(papierCode) && game.inventory.hasIn(canneAPeche)){
                    cursor.survol(zonePapier, (StretchViewport) viewport);
                }
            }
        }
        else if (background.getTexture().toString().matches("image/ParcStAnne/banczoomed.*")){
            if(!cursor.survol(zoneFeraille, (StretchViewport) viewport)){
                if(!cursor.survol(journalDePeche, (StretchViewport) viewport)){
                    cursor.survol(zoneFil, (StretchViewport) viewport);
                }
            }
        }
        else if (background.getTexture().toString().matches("image/ParcStAnne/sansFerailleParc.*")){
            if(!cursor.survol(zoneFil, (StretchViewport) viewport)){
                cursor.survol(journalDePeche, (StretchViewport) viewport);
            }
        }
        else if (background.getTexture().toString().matches("image/ParcStAnne/sansFilBanc.*")){
            if(!cursor.survol(zoneFeraille, (StretchViewport) viewport)){
                cursor.survol(journalDePeche, (StretchViewport) viewport);
            }
        }
        else if (background.getTexture().toString().matches("image/ParcStAnne/sansobjetbanc.*")){
            cursor.survol(journalDePeche, (StretchViewport) viewport);
        }
        else if (background.getTexture().toString().matches("image/ParcStAnne/intChapelle.*")){
            cursor.survol(zoneEchelle, (StretchViewport) viewport);
        }
        else if (background.getTexture().toString().matches("image/ParcStAnne/CadenasStAnne.*")){
            cursor.survol(zoneChiffres, (StretchViewport) viewport);
        }
        else if (background.getTexture().toString().matches("image/ParcStAnne/parcVueGloballe.*")){
            if(!cursor.survol(zoneCraft, (StretchViewport) viewport)){
                if(!cursor.survol(banc, (StretchViewport) viewport)){
                    if(!cursor.survol(grille, (StretchViewport) viewport)){
                        if(!cursor.survol(lac, (StretchViewport) viewport) && !isBatonPicked){
                            cursor.survol(zoneBaton, (StretchViewport) viewport);
                        }
                    }
                }
            }
        } // fin control curseur au survol

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && textZone.isHidden()) {

            Vector2 touched = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);

            if(buttonHint.contains(touched)){

                if(!game.inventory.hasIn(canneAPeche)){
                    super.showHint(0);
                } else if(game.inventory.hasIn(canneAPeche) && !game.inventory.hasIn(papierCode)){
                    super.showHint(1);
                } else if(game.inventory.hasIn(papierCode)){
                    super.showHint(2);
                }
            }

            if (background.getTexture().toString().matches("image/ParcStAnne/parcStAnneChap.*")) {
                if(panneau.contains(touched)){
                    flavorText.setText("\"Acces interdit, renovation de la chapelle en cours\"\nInteressant... Un cadena a chiffre est la seule barriere entre moi et l'interieur de cette chapelle.");
                } else if(porte.contains(touched)){
                    if(isOpened){
                        if(game.inventory.hasIn(echelle)){
                            background.setRegion(interChapelleLadderless);
                        } else {
                            background.setRegion(interChapelle);
                        }
                        zoneGauche.unhide();
                    } else {
                        background.setRegion(zoomCadena);
                        zoneGauche.hide();
                        zoneDroite.hide();
                    }

                } else if(zonePapier.contains(touched) && game.inventory.hasIn(canneAPeche) && !isPaperPicked){
                    game.inventory.add(papierCode);
                    isPaperPicked = true;
                }else if(zoneDroite.contains(touched)){
                    if(isBatonPicked){
                        background.setRegion(principalBatonless);
                    } else {
                        background.setRegion(principal);
                    }
                    zoneDroite.hide();
                    zoneGauche.unhide();
                }

            }
            else if (background.getTexture().toString().matches("image/ParcStAnne/banczoomed.*")){
                if(journalDePeche.contains(touched)){
                    flavorText.setText("\"La peche est un art de plein air et de ce fait, vous pouvez utiliser ce qui vous entour pour le pratiquer !\"\nJe ne suis pas completement vendu sur ce point mais je vais le garder en memoire.");
                } else if(zoneFil.contains(touched)) {
                    flavorText.setText("Une ficelle... Elle n'est pas assez solide pour me soutenir mais pourait m'etre utile.");
                    game.inventory.add(ficelle);
                    isStringPicked = true;
                    background.setRegion(zoomBancMetalOnly);
                } else if(zoneFeraille.contains(touched)) {
                    flavorText.setText("Un vielle opercule de canette. Je le prend pour ma collection !");
                    game.inventory.add(ferraille);
                    isMetalPicked = true;
                    background.setRegion(zoomBancStringOnly);
                } else if(zoneQuitter.contains(touched)){
                    if(isBatonPicked){
                        background.setRegion(principalBatonless);
                    } else {
                        background.setRegion(principal);
                    }
                    zoneGauche.unhide();
                }

            }
            else if (background.getTexture().toString().matches("image/ParcStAnne/sansFerailleParc.*")){
                if(journalDePeche.contains(touched)){
                    flavorText.setText("\"La peche est un art de plein air et de ce fait, vous pouvez utiliser ce qui vous entour pour le pratiquer !\"\nJe ne suis pas completement vendu sur ce point mais je vais le garder en memoire.");
                } else if(zoneFil.contains(touched)) {
                    flavorText.setText("Une ficelle... Elle n'est pas assez solide pour me soutenir mais pourait m'etre utile.");
                    game.inventory.add(ficelle);
                    isStringPicked = true;
                    background.setRegion(zoomBancVide);
                } else if(zoneQuitter.contains(touched)){
                    if(isBatonPicked){
                        background.setRegion(principalBatonless);
                    } else {
                        background.setRegion(principal);
                    }
                    zoneGauche.unhide();
                }

            }
            else if (background.getTexture().toString().matches("image/ParcStAnne/sansFilBanc.*")){
                if(journalDePeche.contains(touched)){
                    flavorText.setText("\"La peche est un art de plein air et de ce fait, vous pouvez utiliser ce qui vous entour pour le pratiquer !\"\nJe ne suis pas completement vendu sur ce point mais je vais le garder en memoire.");
                } else if(zoneFeraille.contains(touched)) {
                    flavorText.setText("Un vielle opercule de canette. Je le prend pour ma collection !");
                    game.inventory.add(ferraille);
                    isMetalPicked = true;
                    background.setRegion(zoomBancVide);
                } else if(zoneQuitter.contains(touched)){
                    if(isBatonPicked){
                        background.setRegion(principalBatonless);
                    } else {
                        background.setRegion(principal);
                    }
                    zoneGauche.unhide();
                }

            }
            else if (background.getTexture().toString().matches("image/ParcStAnne/sansobjetbanc.*")){
                if(journalDePeche.contains(touched)){
                    flavorText.setText("\"La peche est un art de plein air et de ce fait, vous pouvez utiliser ce qui vous entour pour le pratiquer !\"\nJe ne suis pas completement vendu sur ce point mais je vais le garder en memoire.");
                } else if(zoneQuitter.contains(touched)){
                    if(isBatonPicked){
                        background.setRegion(principalBatonless);
                    } else {
                        background.setRegion(principal);
                    }
                    zoneGauche.unhide();
                }
            }
            else if (background.getTexture().toString().matches("image/ParcStAnne/intChapelle.*")){
                if(zoneEchelle.contains(touched)){
                    flavorText.setText("Une echelle ! Parfait, avec ca je vais pouvoir passer par dessus la cloture.");
                    game.inventory.add(echelle);
                    background.setRegion(interChapelleLadderless);
                } else if(zoneGauche.contains(touched) || zoneDroite.contains(touched)){
                    background.setRegion(zoomChapelleOpen);
                    zoneGauche.hide();
                }

            }
            else if (background.getTexture().toString().matches("image/ParcStAnne/SansEchelleChapelle.*")){
                if(zoneGauche.contains(touched) || zoneDroite.contains(touched)){
                    background.setRegion(zoomChapelleOpen);
                    zoneGauche.hide();
                }
            }
            else if (background.getTexture().toString().matches("image/ParcStAnne/CadenasStAnne.*")){
                if(zoneQuitter.contains(touched)){
                    background.setRegion(zoomChapelle);
                    zoneDroite.unhide();
                } else {
                    for (AnimatedGameObject chiffre : zoneChiffres) {
                        if(chiffre.contains(touched)){
                            chiffre.changeStat(true);
                            int i = 0;
                            boolean verif = false;
                            while(!verif && i<4){
                                if(zoneChiffres[i].getState() != code[i]){
                                    verif = true;
                                }
                                i++;
                            }
                            if(!verif){
                                isOpened = true;
                                flavorText.setText("J'ai trouve le bon code, je vais pouvoir rentrer dans cette chapelle maintenant.");
                                background.setRegion(zoomChapelleOpen);
                                zoneDroite.unhide();
                            }
                        }
                    }
                }

            }
            else if (background.getTexture().toString().matches("image/ParcStAnne/parcVueGloballe.*")){

                if(zoneCraft.contains(touched)){
                    if(game.inventory.hasIn(ficelle) && game.inventory.hasIn(ferraille) && game.inventory.hasIn(baton)){
                        flavorText.setText("avec cette ficelle, ce baton et ce morceau de ferraille je devrais pouvoir me faire une canne a peche.\nIl ne me reste plus qu'a lui trouver une utilite...");
                        game.inventory.remove(ficelle);
                        game.inventory.remove(ferraille);
                        game.inventory.remove(baton);
                        game.inventory.add(canneAPeche);
                    } else {
                        flavorText.setText("Cet etablie ne me sert pas a grand chose pour le moment...");
                    }
                } else if(banc.contains(touched)){
                    if(isStringPicked && isMetalPicked){
                        background.setRegion(zoomBancVide);
                    } else if(isStringPicked && !isMetalPicked){
                        background.setRegion(zoomBancMetalOnly);
                    } else if(!isStringPicked && isMetalPicked){
                        background.setRegion(zoomBancStringOnly);
                    } else {
                        background.setRegion(zoomBanc);
                    }
                    zoneGauche.hide();
                } else if(grille.contains(touched)){
                    if(game.inventory.hasIn(echelle)){
                        flavorText.setText("Partons d'ici.");
                        finNiveau = true;
                    } else {
                        flavorText.setText("Cette cloture est trop haute pour que je puis passer par dessus sans aide.");
                    }
                } else if(lac.contains(touched)){
                    if(game.inventory.hasIn(canneAPeche)){
                        flavorText.setText("...\n...\n... Je pense que je ne devais pas pecher litteralement...");
                    } else {
                        flavorText.setText("Ce lac est magnifique, peut etre qu'il y a des poissons dedans.");
                    }
                } else if(zoneBaton.contains(touched)){
                    game.inventory.add(baton);
                    isBatonPicked = true;
                    background.setRegion(principalBatonless);
                } else if(zoneGauche.contains(touched)){
                    if(isOpened){
                        background.setRegion(zoomChapelleOpen);
                    } else {
                        background.setRegion(zoomChapelle);
                    }
                    zoneDroite.unhide();
                    zoneGauche.hide();
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
        zoneFil.dispose();
        zoneFeraille.dispose();
        zoomChapelle.dispose();
        zoomBanc.dispose();
        interChapelle.dispose();
        zoomCadena.dispose();
        banc.dispose();
        grille.dispose();
        porte.dispose();
        panneau.dispose();
        journalDePeche.dispose();
        zoneCraft.dispose();
        zoneEchelle.dispose();
        zoneBaton.dispose();
        zonePapier.dispose();
        baton.dispose();
        papierCode.dispose();
        ficelle.dispose();
        ferraille.dispose();
        canneAPeche.dispose();
        echelle.dispose();
        lac.dispose();
        zoneQuitter.dispose();
        for(AnimatedGameObject chiffre : zoneChiffres){
            chiffre.dispose();
        }
    }
}
