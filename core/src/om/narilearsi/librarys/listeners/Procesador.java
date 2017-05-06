package om.narilearsi.librarys.listeners;


import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

import om.narilearsi.librarys.Maingame;
import om.narilearsi.librarys.screens.GameScreen;

import static om.narilearsi.librarys.ConfigGame.DOWN;
import static om.narilearsi.librarys.ConfigGame.JUMP;
import static om.narilearsi.librarys.ConfigGame.QUIET;
import static om.narilearsi.librarys.ConfigGame.WALKD;
import static om.narilearsi.librarys.ConfigGame.WALKI;

/**
 * Created by viane on 24/04/2017.
 */

public class Procesador extends InputAdapter{

    private final GameScreen game;

    public Procesador(GameScreen game){
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode);
        switch (keycode){
            case 29:game.player.setState(WALKI);;break;
            case 32: game.player.setState(WALKD); ;break;
            case 51: game.player.setState(JUMP);;break;
            case 47: game.player.setState(DOWN);;break;
            case 131: ;break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case 29: if(game.player.state == WALKI) game.player.setState(QUIET);;break;
            case 32: if(game.player.state == WALKD) game.player.setState(QUIET);;break;
            case 51: if(game.player.state == JUMP) game.player.setState(QUIET);;break;
            case 47: if(game.player.state == DOWN) game.player.setState(QUIET);;break;
            case 131: ;break;
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return true;
    }
}
