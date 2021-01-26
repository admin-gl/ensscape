package com.mygdx.escapefromlannioncity.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;

import static java.lang.Math.max;

public class MenuPrincipal implements Screen {

    public final Music musique;
    private final EscapeFromLannionCity game;

    private final Texture logoVoid;
    private final Texture logoEnscape;
    private final Texture fondNoir;
    private final Texture fondMenu;

    private final Sprite background;
    private SpriteDrawable fondu;

    private final BitmapFontCache text;

    private final OrthographicCamera camera;
    private final Viewport viewport;

    private boolean isTransition1;
    private boolean isTransition2;
    private boolean fadein;
    private long debTrans;

    public MenuPrincipal(EscapeFromLannionCity game){
        this.game = game;

        text = game.mainFont.newFontCache();

        musique = Gdx.audio.newMusic(Gdx.files.internal("music/mainmenu.wav"));
        musique.setLooping(true);

        logoVoid = new Texture(Gdx.files.internal("image/Utilitaire/logoVoid.jpg"));
        logoEnscape = new Texture(Gdx.files.internal("image/Utilitaire/logoEnsscape.png"));
        fondNoir = new Texture(Gdx.files.internal("image/Utilitaire/blacksquare.png"));
        fondMenu = new Texture(Gdx.files.internal("image/Utilitaire/fondMenuPricipal.jpg"));

        background = new Sprite(fondNoir);
        fondu = new SpriteDrawable(background);

        camera = new OrthographicCamera();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport.setCamera(camera);
        viewport.apply(true);

        isTransition1 = true;
        isTransition2 = false;
        fadein = true;
        fondu = fondu.tint(new Color(0,0,0,1));
        text.setText("presente", 0, Gdx.graphics.getHeight()/10f, Gdx.graphics.getWidth(), Align.center ,true);
    }

    @Override
    public void show() {
        if(!isTransition1 && !isTransition2){
            musique.setVolume(game.volume);
            musique.play();
        }
        debTrans = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.2f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update la camera
        camera.update();

        // update la vue les dernieres updates sont au dessus des premieres
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(background.getTexture(), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        if(isTransition1){
            game.batch.draw(logoVoid, Gdx.graphics.getWidth()*3/10f, Gdx.graphics.getHeight()*3/10f, Gdx.graphics.getWidth()*4/10f, Gdx.graphics.getHeight()*4/10f);
        } else if(isTransition2) {
            game.batch.draw(logoEnscape, Gdx.graphics.getWidth()*2/10f, Gdx.graphics.getHeight()*2/10f, Gdx.graphics.getWidth()*6/10f, Gdx.graphics.getHeight()*6/10f);
        }
        if(isTransition1 || isTransition2) {
            text.draw(game.batch);
            fondu.draw(game.batch, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        } else {
            if(TimeUtils.millis() % 1000 > 500){
                text.draw(game.batch);
            }
        }
        float alpha;
        alpha = max(0, (2500 - TimeUtils.timeSinceMillis(debTrans)) / 2500f);
        if(fadein) {
            fondu = fondu.tint(new Color(0, 0, 0, alpha));
        } else {
            fondu = fondu.tint(new Color(0,0,0, 1-alpha));
        }
        if((alpha == 0 || alpha == 1) && isTransition1){
            if(fadein){
                fadein = false;
            } else {
                isTransition1 = false;
                isTransition2 = true;
                fadein = true;
                text.setText("un jeu par", 0, Gdx.graphics.getHeight() * 9 / 10f, Gdx.graphics.getWidth(), Align.center, true);
            }
            debTrans = TimeUtils.millis();
        } else if((alpha == 0 || alpha == 1) && isTransition2){
            if(fadein){
                fadein = false;
                debTrans = TimeUtils.millis();
            } else {
                isTransition2 = false;
                background.setRegion(fondMenu);
                musique.setVolume(game.volume);
                musique.play();
                text.setText("Appuyer pour continuer", 0, Gdx.graphics.getHeight() / 10f, Gdx.graphics.getWidth(), Align.center, true);
            }
        }

        if(!isTransition1 && !isTransition2) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                game.setScreen(game.menuEtTableau[2]);
                game.sfxButton.play(game.volume);
            }
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        musique.pause();

    }

    @Override
    public void resume() {
        if(!isTransition1 && !isTransition2){
            musique.setVolume(game.volume);
            musique.play();
        }
    }

    @Override
    public void hide() {
        musique.pause();
    }

    @Override
    public void dispose() {
        logoEnscape.dispose();
        logoVoid.dispose();
        fondMenu.dispose();
        fondNoir.dispose();
        musique.dispose();
    }
}
