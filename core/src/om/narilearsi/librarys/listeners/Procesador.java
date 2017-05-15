package om.narilearsi.librarys.listeners;


import com.badlogic.gdx.InputAdapter;

import om.narilearsi.librarys.screens.GameScreen;

import static om.narilearsi.librarys.ConfigGame.JUMP;



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
        //System.out.println(keycode);
        switch (keycode){
            case 29:
                    game.player.setForcesApply(1, 2, 0);
                    game.player.setForcesApply(1,0,1);
                    game.player.direction[0] = 1;
                    game.player.direction[1] = 0;

                ;break;
            case 32:
                    game.player.setForcesApply(1, 2, 1);
                    game.player.setForcesApply(1, 0, 0);
                    game.player.direction[1] = 1;
                    game.player.direction[0] = 0;

                ;break;
            case 51:
                if(!game.player.isJumping){
                    game.player.setForcesApply(0,1,1);
                    game.player.setState(JUMP);
                }
                ;break;
            case 47:

                ;break;
            case 131: ;break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case 29:
                game.player.setForcesApply(1,0,0);
                ;break;
            case 32:
                game.player.setForcesApply(1,2,0);
                ;break;
            case 51:

                ;break;
            case 47:

                ;break;
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
