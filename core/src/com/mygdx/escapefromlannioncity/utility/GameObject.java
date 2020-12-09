package com.mygdx.escapefromlannioncity.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameObject {

    public Texture sprite;
    public Rectangle hitbox;
    float scaleX = 1;
    float scaleY = 1;

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

    /**
     * permet d'affecter un facteur d'echelle a un GameObject
     * pour qu'il soit coherent avec la taille de l'ecran
     * NE PAS APPELER DANS resize() !
     */
    public void resize(){
        int baseWorldWidth = 256;
        int baseWorldHeight = 144;
        int actualWorldWidth = Gdx.graphics.getWidth();
        int actualWorldHeight = Gdx.graphics.getHeight();

        scaleX = actualWorldWidth/(baseWorldWidth*scaleX);
        scaleY = actualWorldHeight/(baseWorldHeight*scaleY);

        hitbox.setSize(hitbox.width*scaleX, hitbox.height*scaleY);
        hitbox.setPosition(hitbox.x*scaleX, hitbox.y*scaleY);

    }

    public void dispose(){
        sprite.dispose();
    }

}
