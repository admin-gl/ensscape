package com.mygdx.escapefromlannioncity.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Classe de curseur changeant
 * A appeler lorsque l'on veut acceder aux deux curseurs possibles durant la realisation d'un tableau
 */
public class ChangingCursor{

    private final Cursor cursorBase;
    private final Cursor cursorSelectable;

    public ChangingCursor(){
        Texture temptext1 = new Texture(Gdx.files.internal("image/Utilitaire/cursor1.png"));
        Texture temptext2 = new Texture(Gdx.files.internal("image/Utilitaire/cursor2.png"));
        temptext1.getTextureData().prepare();
        temptext2.getTextureData().prepare();
        cursorBase = Gdx.graphics.newCursor(temptext1.getTextureData().consumePixmap(), 0, 0);
        cursorSelectable = Gdx.graphics.newCursor(temptext2.getTextureData().consumePixmap(), 0, 0);
        temptext1.dispose();
        temptext2.dispose();

    }

    /**
     * Permet de verifier si on survol un GameObject parmi plusieurs
     * @param objects liste de GameObject a tester
     * @param viewport le viewport du Screen afficher actuellement
     * @return true si on est en survol de l'un des GameObject, false sinon
     */
    public boolean survol(GameObject[] objects, StretchViewport viewport) {
        int i = 0;
        boolean contain = false;
        Vector2 touched = new Vector2();
        touched.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(touched);
        while (i < objects.length && !contain) {
            if (objects[i].contains(touched)) {
                Gdx.graphics.setCursor(cursorSelectable);
                contain = true;
            }
            i++;
        }
        if(!contain){
            Gdx.graphics.setCursor(cursorBase);
        }
        return contain;
    }

    /**
     * Permet de verifier si on survol un GameObject avec le joueur peut interagir
     * @param object GameObject a tester
     * @param viewport le viewport du Screen afficher actuellement
     * @return true si on est actuellement en survol du GameObject false sinon
     */
    public boolean survol(GameObject object, StretchViewport viewport) {
        Vector2 touched = new Vector2();
        touched.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(touched);
        if (object.contains(touched)) {
            Gdx.graphics.setCursor(cursorSelectable);
            return true;
        } else {
            Gdx.graphics.setCursor(cursorBase);
            return false;
        }
    }

    /**
     * Permet de mettre de cursor de base
     */
    public void reset(){
        Gdx.graphics.setCursor(cursorBase);
    }
}
