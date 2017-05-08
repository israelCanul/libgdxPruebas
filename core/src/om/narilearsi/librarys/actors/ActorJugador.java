package om.narilearsi.librarys.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


import static om.narilearsi.librarys.ConfigGame.DATAPLAYER;
import static om.narilearsi.librarys.ConfigGame.DOWN;
import static om.narilearsi.librarys.ConfigGame.IMPULSE_JUMP;
import static om.narilearsi.librarys.ConfigGame.INITSPEED;
import static om.narilearsi.librarys.ConfigGame.JUMP;
import static om.narilearsi.librarys.ConfigGame.METRICSCENEBOX2D;
import static om.narilearsi.librarys.ConfigGame.QUIET;
import static om.narilearsi.librarys.ConfigGame.WALKD;
import static om.narilearsi.librarys.ConfigGame.WALKI;
import static om.narilearsi.librarys.ConfigGame.convertir;

/**
 * Created by icanul on 4/26/17.
 */

public class ActorJugador extends Entity {

    public Body body;
    public Fixture fixture;
    private TextureRegion regionPlayer;
    private Float PLAYER_SPEED = INITSPEED;
    public boolean collitionOn = false;
    public float lastPointY = 0;

    public ActorJugador(Texture texture,World world){
        super(texture,world);
        // se crea el body del player  y se le agrega el shape
       body = world.createBody(createBody(BodyDef.BodyType.DynamicBody));
        body.setFixedRotation(true);// esto evita que los cuerpos giren
        createShape(body);
    }
    public ActorJugador(Texture texture,World world,int x, int y){
        super(texture,world);
        setX(x);
        setY(y);
        setWidth(16);
        setHeight(32);
        body = world.createBody(createBody(BodyDef.BodyType.DynamicBody));
        body.setFixedRotation(true);// esto evita que los cuerpos giren
        createShape(body);
    }
    public void createShape(Body body){
        // por si el body no a sido inicializado
        if(body==null) body = world.createBody(createBody(BodyDef.BodyType.DynamicBody));

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / METRICSCENEBOX2D)/2,(getHeight() / METRICSCENEBOX2D)/2);
        FixtureDef def = new FixtureDef();
        def.shape = shape;
        def.friction= 0.4f;

        //def.density = 10.0f;

        fixture = body.createFixture(def);
        fixture.setUserData(DATAPLAYER);
        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //System.out.println(convertir((body.getPosition().x - getWidthBox2D()))+","+convertir((body.getPosition().y - getHeightBox2D())));
        setPosition(convertir((body.getPosition().x - getWidthBox2D())), convertir((body.getPosition().y - getHeightBox2D())));
        batch.draw(imagen,getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public void act(float delta) {

        if(getVelocity(1,2)==1  && !isJumping){
            //System.out.println("Avanza a la derecha");
            body.setLinearVelocity(INITSPEED,body.getLinearVelocity().y);
        }else if(getVelocity(1,0)==1 && !isJumping){
            //System.out.println("Avanza a la izquierda");
            body.setLinearVelocity(-INITSPEED,body.getLinearVelocity().y);
        }else {
            if (!isJumping) body.setLinearVelocity(0, body.getLinearVelocity().y);
        }

        if (!isJumping && isMustJump() ) {
            //System.out.println("debe brincar");
            jump();
        }
        //System.out.println(body.getLinearVelocity().y);

        if(isJump()){
            body.applyForceToCenter(0, -IMPULSE_JUMP * 1.15f,true);
        }
        if(body.getLinearVelocity().y !=0 && lastPointY!=0.0){
            setJumped(true);
        }else {
            setJumped(false);
        }

        lastPointY = body.getLinearVelocity().y;


    }
    public void jump(){
        Vector2 position=body.getPosition();
        float fuerzax = body.getLinearVelocity().x;
        body.setLinearVelocity(0,0);
        body.applyLinearImpulse(fuerzax,IMPULSE_JUMP, position.x, position.y, true);
        setJumped(true);
        setVelocity(0,1,0);
    }
}

