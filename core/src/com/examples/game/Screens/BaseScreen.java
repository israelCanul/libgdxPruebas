package com.examples.game.Screens;

import com.badlogic.gdx.Screen;
import com.examples.game.Maingame;

/**
 * Created by icanul on 4/26/17.
 */

abstract class BaseScreen implements Screen {
    private Maingame game;

    public BaseScreen(Maingame game){
        this.game = game;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
