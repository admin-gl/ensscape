package com.mygdx.escapefromlannioncity.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class AnimatedGameObject extends GameObject{

    private final Animation<Texture> object;
    private float increment;
    private int stat = 0;

    public AnimatedGameObject(Texture[] keyFrames){
        object = new Animation<>(0.5f, keyFrames);
        hitbox = new Rectangle();
        increment = Gdx.graphics.getDeltaTime();
        object.setPlayMode(Animation.PlayMode.LOOP);
    }

    public AnimatedGameObject(Texture[] keyFrames, float frameDuration){
        object = new Animation<>(frameDuration, keyFrames);
        hitbox = new Rectangle();
        increment = Gdx.graphics.getDeltaTime();
    }

    public AnimatedGameObject(Texture[] keyFrames, float frameDuration, Animation.PlayMode playMode){
        Array<Texture> kf = new Array<>();
        kf.addAll(keyFrames);
        object = new Animation<>(frameDuration, kf, playMode);
        hitbox = new Rectangle();
        increment = Gdx.graphics.getDeltaTime();
    }

    /**
     * @param playMode : mode d'affichage de l'animation
     * @see com.badlogic.gdx.graphics.g2d.Animation.PlayMode
     */
    public void setPlayMode(Animation.PlayMode playMode) {
        object.setPlayMode(playMode);
    }

    /**
     * @param frameDuration : duree d'affichage d'une frame (en secondes)
     */
    public void setFrameDuration(float frameDuration){
        object.setFrameDuration(frameDuration);
    }
    /**
     * Permet d'afficher l'AnimatedGameObject en temps qu'animation
     * @param batch : le SpriteBatch dans lequel on doit afficher l'AnimatedGameObject
     * @param elapsed : le temps ecoule
     */
    public void draw(SpriteBatch batch, float elapsed){
        batch.draw(object.getKeyFrame(elapsed), hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    /**
     * Permet de changer la frame affichee par l'AnimatedGameObject
     * @param plus : vrai pour incrementer, faux pour decrementer
     * */
    public void changeStat(boolean plus){

        if(plus) {
            increment += object.getFrameDuration();
            stat += 1;
        } else {
            increment -= object.getFrameDuration();
            stat -= 1;
        }
    }

    /**
     * Accede au numero de la frame actuellement en affichage
     * @return entier numero de frame
     */
    public int getState(){
        return stat % object.getKeyFrames().length;
    }


    @Override
    public void drawFix(SpriteBatch batch){
        batch.draw(object.getKeyFrame(increment), hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    @Override
    public void dispose() {
        Texture[] keyFrames = object.getKeyFrames();
        for (Texture keyFrame : keyFrames) {
            keyFrame.dispose();
        }
    }

}
