package om.narilearsi.librarys.bodys;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import om.narilearsi.librarys.ConfigGame;
import om.narilearsi.librarys.maps.ExampleMap;
import om.narilearsi.librarys.screens.GameScreen;

import static om.narilearsi.librarys.ConfigGame.*;
import static om.narilearsi.librarys.ConfigGame.METRICSCENEBOX2D;
import static om.narilearsi.librarys.ConfigGame.SUELO;
import static om.narilearsi.librarys.ConfigGame.convertir;

/**
 * Created by viane on 27/05/2017.
 */

public class BlockTiledMap {
    private World world;
    private Body body;
    private MapObject mapObject;
    public boolean movil;
    public float x;
    public float y;
    public float width;
    public float height;
    private Fixture fixture;

    //bloque simple
    public BlockTiledMap(World world, MapObject temp) {
        this.mapObject=temp;
        this.world = world;
        init();
    }

    public BodyDef getBody(BodyDef.BodyType type){
        BodyDef def = new BodyDef();
        def.position.set((getX()/ METRICSCENEBOX2D) + getWidthBox2D(),(getY()/ METRICSCENEBOX2D) + getHeightBox2D());
        def.type = type;
        return def;
    }

    public void init(){
        boolean suelo = Boolean.valueOf(mapObject.getProperties().get("suelo").toString());
        boolean bloque = Boolean.valueOf(mapObject.getProperties().get("bloque").toString());
        if (mapObject instanceof RectangleMapObject) {
            RectangleMapObject rectangulo = (RectangleMapObject)mapObject;
            this.x = rectangulo.getRectangle().getX();
            this.y = rectangulo.getRectangle().getY();
            this.width = rectangulo.getRectangle().getWidth();
            this.height = rectangulo.getRectangle().getHeight();
            body = this.world.createBody(getBody(BodyDef.BodyType.StaticBody));

            createBox(suelo,bloque);
            //System.out.println();
        }
    }




    public void createBox(boolean suelo, boolean bloque){
        PolygonShape shape = new PolygonShape();
        System.out.println(getWidthBox2D());
        shape.setAsBox(getWidthBox2D(),getHeightBox2D());
        fixture = body.createFixture(shape,0);

        if(bloque){
           fixture.setUserData(new DataPLayer(getWidthBox2D(),getHeightBox2D(),BLOQUE));
        }else if(suelo){
            fixture.setUserData(new DataPLayer(getWidthBox2D(),getHeightBox2D(),SUELO));
        }

        shape.dispose();
    }

    public void dispose(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    private float unitTileToBox2d(float from){
        float to=0;
        to=from/METRICSCENEBOX2D;
        return to;
    }

    // getters y setters
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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
    // getter and setters
    public float getWidthBox2D(){
        return (getWidth() / METRICSCENEBOX2D)/2;
    }
    public float getHeightBox2D(){
        return (getHeight() / METRICSCENEBOX2D)/2;
    }
}
