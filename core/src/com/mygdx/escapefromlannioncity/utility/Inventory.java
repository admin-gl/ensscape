package com.mygdx.escapefromlannioncity.utility;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Inventory extends GameObject{

    ArrayList<GameObject> container;

    public Inventory(FileHandle t){
        super(t, 128, 7, 140, 14);

        container = new ArrayList<>();

    }

    /**
     * test si un Gameobject est present dans l'inventaire pour afficher different flavor texts par exemple
     * @param object objet dont on test la presence
     * @return vrai si il y a presence, faux sinon
     */
    public boolean hasIn(GameObject object){
        return container.contains(object);
    }

    /**
     * ajoute un objet ramasse par le joueur a l'inventaire
     * @param object objet a ajouter
     */
    public void add(GameObject object){
        container.add(object);
    }

    /**
     * retire un objet utilise de l'inventaire
     * @param object objet a retirer
     */
    public void remove(GameObject object){
        container.remove(object);
    }

    /**
     * vide completement l'inventaire
     */
    public void clear(){
        container.clear();
    }

}
