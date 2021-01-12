package com.mygdx.escapefromlannioncity.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
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
    private boolean zoomed;


    public ParcStAnne(EscapeFromLannionCity game) {
        super(game, "music/enigme_2.wav");

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

        porte = new GameObject();
        panneau = new GameObject();

        sousLeBanc = new GameObject();
        journalDePeche = new GameObject();
        zoneCraft = new GameObject();

        zoneChiffres = new AnimatedGameObject[4];
        Texture[] Chiffre = new Texture[10];
        int i;
        for (i=0;i<10;i++) {
            Chiffre[i] = new Texture(Gdx.files.internal("animation/ParcStAnne/Cadena/"+i+".png"));
        }
        for (i = 0; i <4; i++) {
            zoneChiffres[i] = new AnimatedGameObject(Chiffre);
        }
        code = new int[]{5,2,9,1};

        zoneEchelle = new GameObject();

        zoneBaton = new GameObject();
        zonePapier = new GameObject();

        baton = new GameObject();
        papierCode = new GameObject();
        ficelle = new GameObject();
        ferraille = new GameObject();
        canneAPeche = new GameObject();
        echelle = new GameObject();

        zoneQuitter = new GameObject();
        zoomed = false;
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

        //
        // faire verif survol curseur
        //

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !flavorText.isDrawing()) {

            Vector2 touched = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touched);

            if (background.getTexture().toString().matches("image/ParcStAnne/zoomChapelle.png")) {
                if(panneau.contains(touched)){
                    flavorText.setText("\"Acces interdit, renovation de la chapelle en cours\"\nInteressant...");
                } else if(porte.contains(touched)){

                }
            }
        }

        super.render(delta);
    }

    @Override
    public void dispose(){
        super.dispose();
    }
}
