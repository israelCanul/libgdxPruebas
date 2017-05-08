package om.narilearsi.librarys.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static om.narilearsi.librarys.ConfigGame.DOWN;
import static om.narilearsi.librarys.ConfigGame.JUMP;
import static om.narilearsi.librarys.ConfigGame.METRICSCENEBOX2D;

import static om.narilearsi.librarys.ConfigGame.WALKD;

/**
 * Created by viane on 30/04/2017.
 */

public class Entity extends Actor {
    private boolean colicion;
    public Integer state;
    public boolean isAlive;
    public boolean isJumping;
    private boolean isDown;

    public Integer[][] velocity = {{0,0,0},
                                   {0,1,0}};
    public World world;
    public Texture imagen;
    public TextureRegion regionTexture;
    public Body body;
    public Fixture fixture;
    private Animation currentAnimation;


    public Entity(Texture texture, World world){
        imagen = texture;
        this.world = world;
        state = 0;
        isAlive = true;
        colicion = false;
        setX(0);
        setY(0);
        setWidth(50);
        setHeight(50);

        isDown = false;
    }
    public void isDead(){
        isAlive = false;
    }
    public boolean isJump() { return isJumping; }
    public boolean isColitioned(){ return colicion;}
    public void setJumped(Boolean jump){
        isJumping = jump;
    }

    public void setState(int state){
        if (this.state != state){
            this.state = state;
            switch(state){
                case WALKD:
                    //currentAnimation = walkAnimation;
                    break;
                case JUMP:
                    //currentAnimation = jumpAnimation;
                    break;
                case DOWN:
                    //currentAnimation = downAnimation;
                    break;
                default:
                    //currentAnimation = idleAnimation;
                    break;
            }
        }
    }
    public BodyDef createBody(BodyDef.BodyType type){
        BodyDef def = new BodyDef();
        def.position.set((getX()/ METRICSCENEBOX2D)+(getWidth() / METRICSCENEBOX2D)/2,(getY()/ METRICSCENEBOX2D) + (getHeight() / METRICSCENEBOX2D)/2);

        def.type = type;
        return def;
    }


    public void dispose(){
        if(body!=null){
            body.destroyFixture(fixture);
            world.destroyBody(body);
        }
    }


    // getter and setters

    public float getWidthBox2D(){
        return (getWidth() / METRICSCENEBOX2D)/2;
    }
    public float getHeightBox2D(){
        return (getHeight() / METRICSCENEBOX2D)/2;
    }

    public boolean isMustJump() {
        if(getVelocity(0,1)==1) return true;
        else return false;
    }

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

    public Integer getVelocity(int a,int b) {
        return velocity[a][b];
    }

    public void setVelocity(int a,int b,Integer velocity) {
        this.velocity[a][b] = velocity;
    }

    public void setColicion(Boolean coli){
        colicion = coli;
    }
}
