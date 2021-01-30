package com.mygdx.escapefromlannioncity.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.utility.GameObject;

/**
 * Classe qui sert à l'affichage et l'interraction avec le bouton d'ouverture du
 * menu depuis les écran de jeu comme l'Amphi, le ParcStAnne ou le Warp
 */
public class ButtonOpenMenu extends GameObject {

    /* Constructeur */
    public ButtonOpenMenu(){

        /* Création d'un GameObject */
        super(Gdx.files.internal("image/Utilitaire/Menu GL.png"), 20, 136, 30, 9, "Bouton Menu");

        /* */
        super.resize();

    }

    /** Cette méthode permet d'afficher le bouton de menu.
     * Dans render, placez simplement cette méthode après
     *   game.batch.begin();
     * et avant
     *   game.batch.end();
     * @param pGame le jeu actuel
     */
    public void initButtonMenu(EscapeFromLannionCity pGame) {

        /* Affiche le bouton "flottant" de Menu */
        drawFix(pGame.batch);

    }

    /** Cette méthode permet d'interagir avec la zone prévue pour le bouton Menu.
     * Dans render, placez cette méthode après avoir calculé les coordonnées d'un click de souris.
     * @param pTouched position du click de souris
     */
    public boolean isMyButton(Vector2 pTouched) {

        if (contains(pTouched)) {
            return true;
        } else {
            return false;
        }

    }

}
