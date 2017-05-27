package om.narilearsi.librarys.bodys;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import static om.narilearsi.librarys.ConfigGame.METRICSCENEBOX2D;

/**
 * Created by viane on 04/05/2017.
 */

public class BlockBase extends Sprite {

    public World world;
    public Texture image;
    public float x;
    public float y;
    public boolean movil;
    public Body body;
    public Fixture fixture;
    public float width;
    public float height;

    public BlockBase(Texture texture, World world,float x,float y,float width, float height){
        this.image = texture ;
        this.world = world;
        movil = false;
        this.x= x;
        this.y= y;
        this.width = width;
        this.height = height;
    }
    public BlockBase( World world,float x,float y,float width, float height){

        this.world = world;
        movil = false;
        this.x= x;
        this.y= y;
        this.width = width;
        this.height = height;
    }
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public BodyDef createBody(){
        BodyDef def = new BodyDef();
        def.position.set((getX()/ METRICSCENEBOX2D)+(getWidth() / METRICSCENEBOX2D)/2,(getY()/ METRICSCENEBOX2D) + (getHeight() / METRICSCENEBOX2D)/2);
        if(this.movil) def.type = BodyDef.BodyType.DynamicBody;
            else  def.type = BodyDef.BodyType.StaticBody;

        return def;
    }
    public void dispose(){
        if(body!=null){
            body.destroyFixture(fixture);
            world.destroyBody(body);
        }
    }
    // seccion de getters and setters
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
    public boolean isMovil() {
        return movil;
    }
    public void setMovil(boolean movil) {
        this.movil = movil;
    }
    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
    public float getWidthBox2D(){
        return (getWidth() / METRICSCENEBOX2D)/2;
    }
    public float getHeightBox2D(){
        return (getHeight() / METRICSCENEBOX2D)/2;
    }
}
