package com.mygdx.escapefromlannioncity.utility;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

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

        int len = container.toArray().length;
        Vector2 pos = new Vector2(this.getX(), this.getCenterPos().y);
        Vector2 offset = new Vector2(2*scaleX + object.getWidth()/2 + (len-1)*(4*scaleX + object.getWidth()), 0);
        object.setCenterPos(pos.add(offset));
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
