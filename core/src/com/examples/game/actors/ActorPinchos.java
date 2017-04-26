package com.examples.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by icanul on 4/26/17.
 */

public class ActorPinchos extends Actor {
    Texture imagen;

    public ActorPinchos(Texture texture){
        imagen = texture;
    }
    @Override
    public void act(float delta) {
        setX(getX() - (delta * 250));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(imagen,getX(),getY(),getWidth(),getHeight());
    }
}
