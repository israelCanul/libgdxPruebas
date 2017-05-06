package om.narilearsi.librarys;

;
import com.badlogic.gdx.Game;

import om.narilearsi.librarys.screens.GameScreen;

public class Maingame extends Game {
	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}
}
