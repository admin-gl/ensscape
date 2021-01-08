package com.mygdx.escapefromlannioncity.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Inventory extends GameObject{

    public ArrayList<GameObject> container;
    public boolean zoomObj;
    private final GameObject[] zoneInv = new GameObject[10];

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

    public void zoom(int i, SpriteBatch batch) {
        GameObject object = container.get(i);
        object.setSize(object.getWidth()*4, object.getHeight()*4);
        object.setCenterPos(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
        drawFix(batch);
    }
    public void unzoom(int i, SpriteBatch batch) {
        GameObject object = container.get(i);
        object.setSize(object.getWidth()/4, object.getHeight()/4);
        Vector2 pos = new Vector2(this.getX(), this.getCenterPos().y);
        Vector2 offset = new Vector2(2*scaleX + object.getWidth()/2 + i*(4*scaleX + object.getWidth()), 0);
        object.setCenterPos(pos.add(offset));
        drawFix(batch);
    }

    public void checkZoom(Vector2 touched, SpriteBatch batch){
        int i;
        for(i=0;i<10;i++){
            if (zoneInv[i].contains(touched) && !zoomObj){
                this.zoom(i, batch);
                zoomObj = true;
            } else if (zoneInv[i].contains(touched) && zoomObj){
                zoomObj = false;
                this.unzoom(i, batch);
            }
        }
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
