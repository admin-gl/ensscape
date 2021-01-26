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

    private Sound sfxDiscution;
    private float volume;

    public FlavorText(EscapeFromLannionCity game, String text, Color color, String type){
        this.flavorText = game.mainFont.newFontCache();
        this.flavorText.setColor(color);
        this.text = text;
        this.increment = 0;
        this.lastTime = TimeUtils.millis();
        this.sfxDiscution = Gdx.audio.newSound(Gdx.files.internal("sound/"+type+".wav"));
        this.drawing = false;
        this.volume = 0.25f;
    }

    /**
     * Affiche le message caractere par caractere toutes les 75ms et joue un son a l'ajout d'un caractere
     * @param batch le SpriteBatch dans lequel on doit afficher le FlavorText
     */
    public void draw(SpriteBatch batch){
        if(TimeUtils.timeSinceMillis(lastTime) >= 75 && increment < text.length()){
            increment += 1;
            lastTime = TimeUtils.millis();
            sfxDiscution.play(volume);
            drawing = true;
        } else if(increment >= text.length()) {
            drawing = false;
        }
        String OS = System.getProperty("os.name").toLowerCase();
        boolean IS_MAC = (OS.indexOf("mac") >= 0);
        boolean IS_WINDOWS = (OS.indexOf("win") >= 0);
        boolean IS_UNIX = (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
        if (IS_MAC){
            flavorText.setText(text, Gdx.graphics.getWidth()*0.125f, Gdx.graphics.getHeight()*0.379f-17,0, increment, Gdx.graphics.getWidth()*0.76f, Align.left, true);
        } else {
            flavorText.setText(text, Gdx.graphics.getWidth()*0.125f, Gdx.graphics.getHeight()*0.379f,0, increment, Gdx.graphics.getWidth()*0.76f, Align.left, true);
        }
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

    public void setVolume(float volume){
        this.volume = volume;
    }

    public void dispose(){
        sfxDiscution.dispose();
    }

}
