package om.narilearsi.librarys.listeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import om.narilearsi.librarys.screens.GameScreen;

import static om.narilearsi.librarys.ConfigGame.BLOQUE;
import static om.narilearsi.librarys.ConfigGame.DATAPLAYER;
import static om.narilearsi.librarys.ConfigGame.DOWN;
import static om.narilearsi.librarys.ConfigGame.ENEMY;
import static om.narilearsi.librarys.ConfigGame.JUMP;
import static om.narilearsi.librarys.ConfigGame.QUIET;
import static om.narilearsi.librarys.ConfigGame.SUELO;


/**
 * Created by viane on 02/05/2017.
 */

public class ContactListeners implements ContactListener {
    private final GameScreen screen;

    public ContactListeners(GameScreen screen){
        this.screen=screen;
    }


    private boolean areCollided(Contact contact, Object userA, Object userB) {
        Object userDataA = contact.getFixtureA().getUserData();
        Object userDataB = contact.getFixtureB().getUserData();
        //System.out.print(userDataA);
        // This is not in the video! It is a good idea to check that user data is not null.
        // Sometimes you forget to put user data or you get collisions by entities you didn't
        // expect. Not preventing this will probably result in a NullPointerException.
        if (userDataA == null || userDataB == null) {
            return false;
        }

        // Because you never know what is A and what is B, you have to do both checks.
        return (userDataA.equals(userA) && userDataB.equals(userB)) ||
                (userDataA.equals(userB) && userDataB.equals(userA));
    }



    /**
     * This method is executed when a contact has started: when two fixtures just collided.
     */
    @Override
    public void beginContact(Contact contact) {
        // The player has collided with the floor.
        if (areCollided(contact, DATAPLAYER, SUELO)) {
            screen.player.setJumped(false);
        }
        if (areCollided(contact, DATAPLAYER, BLOQUE)) {
            screen.player.setColicion(true);
            //screen.player.setJumped(false);
        }
        // The player has collided with something that hurts.
        if (areCollided(contact, DATAPLAYER, ENEMY)) {

        }
    }

    /**
     * This method is executed when a contact has finished: two fixtures are no more colliding.
     */
    @Override
    public void endContact(Contact contact) {
        // The player is jumping and it is not touchinghhd the floor.
        if (areCollided(contact, DATAPLAYER, SUELO)) {
            //screen.player.setColicion(false);
        }
        if (areCollided(contact, DATAPLAYER, BLOQUE)) {
            screen.player.setColicion(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
