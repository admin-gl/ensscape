package com.mygdx.escapefromlannioncity.utility;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject {

    public Texture sprite;
    public Rectangle hitbox;

    public GameObject(){
        hitbox = new Rectangle();
    }

    public GameObject(FileHandle sprite, float width, float height){
        hitbox = new Rectangle();
        this.sprite = new Texture(sprite);
        hitbox.setSize(width, height);
        hitbox.setPosition(0, 0);
    }

    public GameObject(FileHandle sprite, Vector2 centerPos, float width, float height){
        hitbox = new Rectangle();
        this.sprite = new Texture(sprite);
        hitbox.setSize(width, height);
        hitbox.setCenter(centerPos);
    }

    public GameObject(FileHandle sprite, float x, float y, float width, float height){
        hitbox = new Rectangle();
        this.sprite = new Texture(sprite);
        hitbox.setSize(width, height);
        hitbox.setCenter(x, y);
    }

    /**
     * Permet de savoir si il y a collision entre un point et le GameObject
     * @param point : point a tester
     * @return vrai si il y a collision, faux sinon
     */
    public boolean contains(Vector2 point){
        return hitbox.contains(point);
    }

    /**
     * Permet de savoir si il y a collision entre un point et le GameObject
     * @param x : absisse du point
     * @param y : ordonne du point
     * @return vrai si il y a collision, faux sinon
     */
    public boolean contains(float x, float y){
        return hitbox.contains(x,y);
    }

    /**
     * Permet de savoir si il y a un Rectangle dans le GameObject
     * @param object : rectangle a tester
     * @return vrai si il y a contenance, faux sinon
     */
    public boolean contains(Rectangle object){
        return hitbox.contains(object);
    }

    /**
     * Permet de savoir si il y a un cercle dans le GameObject
     * @param object : cercle a tester
     * @return vrai si il y a contenance, faux sinon
     */
    public boolean contains(Circle object){
        return hitbox.contains(object);
    }

    /**
     * Permet de savoir si il y a une intersection entre un rectangle et le GameObject
     * @param object rectangle a tester
     * @return vrai si il y a intersection, faux sinon
     */
    public boolean overlaps(Rectangle object){
        return hitbox.overlaps(object);
    }


    /**
     * Permet de changer la position du centre d'un GameObject
     * @param centerPos nouvelle position
     */
    public void setCenterPos(Vector2 centerPos){
        hitbox.setCenter(centerPos);
    }

    /**
     * Permet de changer la position du centre d'un GameObject
     * @param x : abscisse
     * @param y : ordonne
     */
    public void setCenterPos(float x, float y){
        hitbox.setCenter(x, y);
    }

    /**
     * permet de changer la taille d'un GameObject
     * @param width : largeur
     * @param height : hauteur
     */
    public void setSize(float width, float height){
        hitbox.setSize(width, height);
    }

    /**
     * Affiche un sprite a l'emplacement de sa hitbox
     * @param batch : le SpriteBatch dans lequel on doit afficher le GameObject
     */
    public void drawFix(SpriteBatch batch){
        batch.draw(sprite, hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }


    public void dispose(){
        sprite.dispose();
    }

}
