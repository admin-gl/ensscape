package com.mygdx.escapefromlannioncity.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
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
    private final Texture zoomArbre;
    private final Texture zoomCadena;

    // tableau extérieur
    private final GameObject chapelle;
    private final GameObject arbre;
    private final GameObject banc;
    private final GameObject grille;
    private final GameObject[] lac;

    // tableau zoom chapelle
    private final GameObject porte;
    private final GameObject panneau;

    // tableau zoom banc
    private final GameObject sousLeBanc;
    private final GameObject journalDePeche;
    private final GameObject zoneCraft;

    // tableau zoom cadena
    private final AnimatedGameObject[] zoneChiffres;
    private final int[] code;

    // tableau interieur
    private final GameObject zoneEchelle;

    // tableau sous l'arbre
    private final GameObject zoneBaton;
    private final GameObject zonePapier;

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



    public ParcStAnne(EscapeFromLannionCity game) {
        super(game, "music/enigme_2.wav", new String[]{"1. Posez vous quelque part avec certains objets afin de pouvoir en creer un nouveau\n",
                                                                    "2. Une canne A peche peut vos servir a atteindre des profondeur, mais aussi des hauteurs\n",
                                                                    "3. Le papier que vous avez ramasser contient un code. Il est surement utilisable quelque part... comme une porte"});

        principal = new Texture(Gdx.files.internal("image/ParcStAnne/principal.png"));
        zoomChapelle = new Texture(Gdx.files.internal("image/ParcStAnne/zoomChapelle.png"));
        zoomBanc = new Texture(Gdx.files.internal("image/ParcStAnne/zoomBanc.png"));
        interChapelle = new Texture(Gdx.files.internal("image/ParcStAnne/interChapelle.png"));
        zoomArbre = new Texture(Gdx.files.internal("image/ParcStAnne/zoomArbre.png"));
        zoomCadena = new Texture(Gdx.files.internal("image/ParcStAnne/zoomCadena.png"));

        background = new Sprite(principal);

        chapelle = new GameObject();
        arbre = new GameObject();
        banc = new GameObject();
        grille = new GameObject();
        lac = new GameObject[]{new GameObject(), new GameObject()};

        chapelle.resize();
        arbre.resize();
        banc.resize();
        grille.resize();
        lac[0].resize();
        lac[1].resize();

        porte = new GameObject();
        panneau = new GameObject();

        porte.resize();
        panneau.resize();

        sousLeBanc = new GameObject();
        journalDePeche = new GameObject();
        zoneCraft = new GameObject();

        sousLeBanc.resize();
        journalDePeche.resize();
        zoneCraft.resize();

        zoneChiffres = new AnimatedGameObject[4];
        Texture[] Chiffre = new Texture[10];
        int i;
        for (i=0;i<10;i++) {
            Chiffre[i] = new Texture(Gdx.files.internal("animation/ParcStAnne/Cadena/"+i+".png"));
        }
        for (i = 0; i <4; i++) {
            zoneChiffres[i] = new AnimatedGameObject(Chiffre);
            zoneChiffres[i].resize();
        }
        code = new int[]{5,2,9,1};

        zoneEchelle = new GameObject();

        zoneEchelle.resize();

        zoneBaton = new GameObject();
        zonePapier = new GameObject();

        zoneBaton.resize();
        zonePapier.resize();

        baton = new GameObject();
        papierCode = new GameObject();
        ficelle = new GameObject();
        ferraille = new GameObject();
        canneAPeche = new GameObject();
        echelle = new GameObject();

        baton.resize();
        papierCode.resize();
        ficelle.resize();
        ferraille.resize();
        canneAPeche.resize();
        echelle.resize();

        zoneQuitter = new GameObject();
        isOpened = false;

        zoneQuitter.resize();

    }

    @Override
    public void render(float delta){
        super.setupRender();

        if(background.getTexture().toString().matches("image/ParcStAnne/zoomCadena.png")){
            if(!zoneGauche.isHidden()){
                zoneGauche.hide();
                zoneDroite.hide();
            }
            for(AnimatedGameObject chiffre : zoneChiffres){
                chiffre.drawFix(game.batch);
            }
        }

        if (background.getTexture().toString().matches("image/ParcStAnne/zoomChapelle*")) {
           if(!cursor.survol(porte, (StretchViewport) viewport)){
               cursor.survol(panneau, (StretchViewport) viewport);
           }
        } else if (background.getTexture().toString().matches("image/ParcStAnne/zoomBanc*")){
            if(!cursor.survol(zoneCraft, (StretchViewport) viewport)){
                if(!cursor.survol(journalDePeche, (StretchViewport) viewport)){
                    cursor.survol(sousLeBanc, (StretchViewport) viewport);
                }
            }
        } else if (background.getTexture().toString().matches("image/ParcStAnne/interChapelle*")){
            cursor.survol(zoneEchelle, (StretchViewport) viewport);
        } else if (background.getTexture().toString().matches("image/ParcStAnne/zoomArbre*")){
            if(!cursor.survol(zoneBaton, (StretchViewport) viewport)){
                cursor.survol(zonePapier, (StretchViewport) viewport);
            }
        } else if (background.getTexture().toString().matches("image/ParcStAnne/zoomCadena*")){
            cursor.survol(zoneChiffres, (StretchViewport) viewport);
        } else if (background.getTexture().toString().matches("image/ParcStAnne/principal*")){
            if(!cursor.survol(chapelle, (StretchViewport) viewport)){
                if(!cursor.survol(banc, (StretchViewport) viewport)){
                    if(!cursor.survol(arbre, (StretchViewport) viewport)){
                        if(!cursor.survol(lac, (StretchViewport) viewport)){
                            cursor.survol(grille, (StretchViewport) viewport);
                        }
                    }
                }
            }
        }

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

            if (background.getTexture().toString().matches("image/ParcStAnne/zoomChapelle*")) {
                if(panneau.contains(touched)){
                    flavorText.setText("\"Acces interdit, renovation de la chapelle en cours\"\nInteressant... Un cadena a chiffre est la seule barriere entre moi et l'interieur de cette chapelle.");
                } else if(porte.contains(touched)){
                    if(isOpened){
                        background.setRegion(interChapelle);
                    } else{
                        background.setRegion(zoomCadena);
                    }
                    zoneGauche.hide();
                    zoneDroite.hide();
                } else if(zoneGauche.contains(touched)){
                    background.setRegion(principal);
                    zoneGauche.hide();
                    zoneDroite.hide();
                } else if(zoneDroite.contains(touched)){
                    background.setRegion(zoomBanc);
                }

            } else if (background.getTexture().toString().matches("image/ParcStAnne/zoomBanc*")){
                if(zoneCraft.contains(touched)){
                    if(game.inventory.hasIn(ficelle) && game.inventory.hasIn(ferraille) && game.inventory.hasIn(baton)){
                        flavorText.setText("avec cette ficelle, ce baton et ce morceau de ferraille je devrais pouvoir me faire une canne a peche.\nIl ne me reste plus qu'a lui trouver une utilite...");
                        game.inventory.remove(ficelle);
                        game.inventory.remove(ferraille);
                        game.inventory.remove(baton);
                        game.inventory.add(canneAPeche);
                    } else {
                        flavorText.setText("Cet endroit et si paisible...");
                    }
                } else if(journalDePeche.contains(touched)){
                    flavorText.setText("\"La peche est un art de plein air et de ce fait, vous pouvez utiliser ce qui vous entour pour le pratiquer !\"\nJe ne suis pas completement vendu sur ce point mais je vais le garder en memoire.");
                } else if(sousLeBanc.contains(touched)){
                    flavorText.setText("Une ficelle et un morceau de fer... Je prends, ca pourrait servir.");
                    game.inventory.add(ficelle);
                    game.inventory.add(ferraille);
                } else if(zoneGauche.contains(touched)){
                    background.setRegion(zoomChapelle);
                } else if(zoneDroite.contains(touched)){
                    background.setRegion(zoomArbre);
                }

            } else if (background.getTexture().toString().matches("image/ParcStAnne/interChapelle*")){
                if(zoneEchelle.contains(touched)){
                    flavorText.setText("Une echelle ! Parfait, avec ca je vais pouvoir passer par dessus la cloture.");
                    game.inventory.add(echelle);
                } else if(zoneQuitter.contains(touched)){
                    background.setRegion(zoomChapelle);
                    zoneGauche.unhide();
                    zoneDroite.unhide();
                }

            } else if (background.getTexture().toString().matches("image/ParcStAnne/zoomArbre*")){
                if(zoneBaton.contains(touched)){
                    flavorText.setText("Un baton... Il semble plutot solide");
                    game.inventory.add(baton);
                } else if(zonePapier.contains(touched) && game.inventory.hasIn(canneAPeche)){
                    flavorText.setText("Quelque chose viens de tomber avec les feuilles !\nUn papier ? Je devrais peut être l'etudier de plus pret.");
                    game.inventory.add(papierCode);
                } else if(zoneDroite.contains(touched)){
                    background.setRegion(principal);
                    zoneGauche.hide();
                    zoneDroite.hide();
                } else if(zoneGauche.contains(touched)){
                    background.setRegion(zoomBanc);
                }

            } else if (background.getTexture().toString().matches("image/ParcStAnne/zoomCadena*")){
                if(zoneQuitter.contains(touched)){
                    background.setRegion(zoomChapelle);
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
                                background.setRegion(zoomChapelle);
                                zoneGauche.unhide();
                                zoneDroite.unhide();
                            }
                        }
                    }
                }

            } else if (background.getTexture().toString().matches("image/ParcStAnne/principal*")){
                if(chapelle.contains(touched)){
                    background.setRegion(zoomChapelle);
                    zoneGauche.unhide();
                    zoneDroite.unhide();
                } else if(arbre.contains(touched)){
                    background.setRegion(zoomArbre);
                    zoneGauche.unhide();
                    zoneDroite.unhide();
                } else if(banc.contains(touched)){
                    background.setRegion(zoomBanc);
                    zoneGauche.unhide();
                    zoneDroite.unhide();
                } else if(grille.contains(touched)){
                    if(game.inventory.hasIn(echelle)){
                        flavorText.setText("Partons d'ici.");
                        finNiveau = true;
                    } else {
                        flavorText.setText("Cette cloture est trop haute pour que je puis passer par dessus sans aide.");
                    }
                } else {
                    for(GameObject part : lac){
                        if(part.contains(touched)){
                            if(game.inventory.hasIn(canneAPeche)){
                                flavorText.setText("...\n...\n... Je pense que je ne devais pas pecher litteralement");
                            } else {
                                flavorText.setText("Ce lac est magnifique, peut etre qu'il y a des poissons dedans.");
                            }
                        }
                    }
                }
            }
        }

        super.render(delta);
    }

    @Override
    public void dispose(){
        super.dispose();
        principal.dispose();
        zoomChapelle.dispose();
        zoomBanc.dispose();
        interChapelle.dispose();
        zoomArbre.dispose();
        zoomCadena.dispose();
        chapelle.dispose();
        arbre.dispose();
        banc.dispose();
        grille.dispose();
        porte.dispose();
        panneau.dispose();
        sousLeBanc.dispose();
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
        for(GameObject part : lac){
            part.dispose();
        }
        for(AnimatedGameObject chiffre : zoneChiffres){
            chiffre.dispose();
        }
    }
}
