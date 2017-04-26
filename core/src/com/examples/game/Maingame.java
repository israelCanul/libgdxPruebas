package com.examples.game;

;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.examples.game.Screens.Box2dScreen;
import com.examples.game.Screens.MainGameScreen;

public class Maingame extends Game {

	@Override
	public void create() {
		setScreen(new Box2dScreen(this));
	}
}
