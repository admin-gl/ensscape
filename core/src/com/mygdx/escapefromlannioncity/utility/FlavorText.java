package com.mygdx.escapefromlannioncity.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;

/**
 * Classe permettant d'afficher une bulle de dialogue a l'attention du joueur
 */
public class FlavorText {

    private final BitmapFontCache flavorText;
    private String text;
    private int increment;
    private boolean drawing;

    private long lastTime;

    Sound sfxDiscution;

    public FlavorText(EscapeFromLannionCity game, String text){
        this.flavorText = game.mainFont.newFontCache();
        this.flavorText.setColor(Color.DARK_GRAY);
        this.text = text;
        this.increment = 0;
        this.lastTime = TimeUtils.millis();
        this.sfxDiscution = Gdx.audio.newSound(Gdx.files.internal("sound/dialogueF.wav"));
        this.drawing = false;
    }

    /**
     * Affiche le message caractere par caractere toutes les 75ms et joue un son a l'ajout d'un caractere
     * @param batch le SpriteBatch dans lequel on doit afficher le FlavorText
     */
    public void draw(SpriteBatch batch){
        if(TimeUtils.timeSinceMillis(lastTime) >= 75 && increment < text.length()){
            increment += 1;
            lastTime = TimeUtils.millis();
            sfxDiscution.play(0.25f);
            drawing = true;
        } else if(increment >= text.length()) {
            drawing = false;
        }
        flavorText.setText(text, Gdx.graphics.getWidth()*0.125f, Gdx.graphics.getHeight()*0.379f,0, increment, Gdx.graphics.getWidth()*0.76f, Align.left, true);
        flavorText.draw(batch);
    }

    /**
     * Change le texte du FlavorText
     * @param text le nouveau texte
     */
    public void setText(String text){
        this.text = text;
        increment = 0;
    }

    /**
     * Verifie si le message est en train d'etre afficher
     * @return true si le message s'affiche, false sinon
     */
    public boolean isDrawing(){
        return drawing;
    }

    /**
     * Affiche la totalite du message en une fois
     * @param batch le SpriteBatch dans lequel on doit afficher le FlavorText
     */
    public void drawAll(SpriteBatch batch){
        increment = text.length()-1;
        this.draw(batch);
    }

    public void dispose(){
        sfxDiscution.dispose();
    }

}
