package com.examples.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.examples.game.Maingame;
import com.examples.game.actors.ActorJugador;
import com.examples.game.actors.ActorPinchos;

/**
 * Created by icanul on 4/26/17.
 */

public class MainGameScreen extends BaseScreen {
    private Stage stage;
    private ActorJugador jugador;
    private ActorPinchos pinchos;
    private Texture textureJugador;
    private Texture texturePinchos;

    public MainGameScreen(Maingame game) {
        super(game);
        textureJugador = new Texture("badlogic.jpg");
        texturePinchos = new Texture("spike.png");
    }


    @Override
    public void show() {
        stage = new Stage();
        stage.setDebugAll(true);
        jugador = new ActorJugador(textureJugador);
        pinchos = new ActorPinchos(texturePinchos);
        stage.addActor(jugador);
        stage.addActor(pinchos);
        pinchos.setHeight(80);
        pinchos.setWidth(80);
        pinchos.setPosition(400,100);
        jugador.setPosition(20,100);
        jugador.setWidth(60);
        jugador.setHeight(60);
    }



    @Override
    public void hide() {
        stage.dispose();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1,1,1,1);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        textureJugador.dispose();
        texturePinchos.dispose();
    }
}
