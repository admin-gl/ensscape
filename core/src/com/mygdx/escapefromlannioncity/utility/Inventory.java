package com.mygdx.escapefromlannioncity.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Classe d'inventaire
 * Indispensable pour tous les tableaux, contiens les objets utilisables par le joueur
 */
public class Inventory extends GameObject{

    public ArrayList<GameObject> container;
    public boolean zoomObj;
    private final GameObject[] zoneInv = new GameObject[10];
    private final boolean[] zoneInvZoom = {false,false,false,false,false,false,false,false,false,false};

    public Inventory(FileHandle t){
        super(t, 128, 7, 140, 14);
        zoomObj = false;
        container = new ArrayList<>();
        for (int i = 0; i<10 ; i++){
            zoneInv[i] = new GameObject(Gdx.files.internal("image/Utilitaire/empty.png"), 65+i*14, 7, 10, 10);
            zoneInv[i].resize();
        }

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
     * Zoom sur un objet de l'inventaire
     * @param i position de l'objet dans l'ArrayList container
     * @param batch le SpriteBatch dans lequel on doit afficher le GameObject
     */
    public void zoom(int i, SpriteBatch batch) {
        GameObject object = container.get(i);
        object.setSize(object.getWidth()*4, object.getHeight()*4);
        object.setCenterPos(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/4f);
        drawFix(batch);
    }

    /**
     * replace l'objet a sa place dans l'inventaire
     * @param i position de l'objet dans l'ArrayList container
     * @param batch le SpriteBatch dans lequel on doit afficher le GameObject
     */
    public void unzoom(int i, SpriteBatch batch) {
        GameObject object = container.get(i);
        object.setSize(object.getWidth()/4, object.getHeight()/4);
        Vector2 pos = new Vector2(this.getX(), this.getCenterPos().y);
        Vector2 offset = new Vector2(2*scaleX + object.getWidth()/2 + i*(4*scaleX + object.getWidth()), 0);
        object.setCenterPos(pos.add(offset));
        drawFix(batch);
    }

    /**
     * Verifie si un zoom sur un object de l'inventaire est en cour ou demander
     * Si un object est zomme, le dezoom, puis si dans le meme temps un autre objet doit etre zoomer, zoom sur cet objet
     * @param touched Vector2 position du clique gauche de souris
     * @param batch le SpriteBatch dans lequel on doit afficher le GameObject
     */
    public void checkZoom(Vector2 touched, SpriteBatch batch){
        int i;
        int verif = -1;
        if(zoomObj){
            for(i=0;i<10;i++) {
                if(zoneInvZoom[i]) {
                    zoomObj = false;
                    this.unzoom(i, batch);
                    zoneInvZoom[i] = false;
                    verif = i;
                }
            }
        }
        if(!zoomObj){
            for(i=0;i<10;i++) {
                if (zoneInv[i].contains(touched) && verif != i) {
                    this.zoom(i, batch);
                    zoomObj = true;
                    zoneInvZoom[i] = true;
                }
            }
        }
    }

    /**
     * retire un objet utilise de l'inventaire
     * @param object objet a retirer
     */
    public void remove(GameObject object){
        int j = container.indexOf(object);
        container.remove(object);
        int i;
        for(i=j;i<container.toArray().length;i++){
            GameObject temp = container.get(i);
            Vector2 pos = new Vector2(this.getX(), this.getCenterPos().y);
            Vector2 offset = new Vector2(2*scaleX + temp.getWidth()/2 + i*(4*scaleX + temp.getWidth()), 0);
            temp.setCenterPos(pos.add(offset));
        }
    }

    /**
     * vide completement l'inventaire
     */
    public void clear(){
        container.clear();
    }

}
