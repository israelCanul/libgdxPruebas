package om.narilearsi.librarys.actors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


import om.narilearsi.librarys.ConfigGame;
import static om.narilearsi.librarys.ConfigGame.DATAPLAYER;
import static om.narilearsi.librarys.ConfigGame.DOWN;
import static om.narilearsi.librarys.ConfigGame.FRAME_COLS_PLAYER;
import static om.narilearsi.librarys.ConfigGame.FRAME_ROWS_PLAYER;
import static om.narilearsi.librarys.ConfigGame.IMPULSE_JUMP;
import static om.narilearsi.librarys.ConfigGame.INITSPEED;
import static om.narilearsi.librarys.ConfigGame.JUMP;
import static om.narilearsi.librarys.ConfigGame.METRICSCENEBOX2D;

import static om.narilearsi.librarys.ConfigGame.QUIETD;
import static om.narilearsi.librarys.ConfigGame.QUIETI;
import static om.narilearsi.librarys.ConfigGame.WALKD;
import static om.narilearsi.librarys.ConfigGame.WALKI;
import static om.narilearsi.librarys.ConfigGame.convertir;

/**
 * Created by icanul on 4/26/17.
 */


public class ActorJugador extends Entity {
    public int LastState = 0;
    public Body body;
    public Fixture fixture;
    private TextureRegion[][] regionPlayer;
    protected TextureRegion currentFrame;
    private Float PLAYERSPEED = INITSPEED;
    private boolean OverFloor = false;

    protected Texture textureAnimacionWalk;
    protected Animation<TextureRegion> currentAnimation,quietD,quietI,walkD, walkI,jumpD,jumpI,dieD,dieI,shootD,shootI;
    private float time = 0;// for the animation

    public ActorJugador(Texture texture,World world){
        super(texture,world);
        // se crea el body del player  y se le agrega el shape
        body = world.createBody(createBody(BodyDef.BodyType.DynamicBody));
        body.setFixedRotation(true);// esto evita que los cuerpos giren
        createShape(body);
    }
    public ActorJugador(Texture texture,World world,int x, int y){
        super(texture,world);
        this.state = QUIETD;
        setX(x);
        setY(y);
        setWidth(32);
        setHeight(32);
        body = world.createBody(createBody(BodyDef.BodyType.DynamicBody));
        body.setFixedRotation(true);// esto evita que los cuerpos giren
        initializeAnimations();
        createShape(body);
    }
    public void createShape(Body body){
        // por si el body no a sido inicializado
        if(body==null) body = world.createBody(createBody(BodyDef.BodyType.DynamicBody));

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / METRICSCENEBOX2D)/2,(getHeight() / METRICSCENEBOX2D)/2);
        FixtureDef def = new FixtureDef();
        def.shape = shape;

        def.friction= 3f;
        fixture = body.createFixture(def);
        fixture.setUserData(new ConfigGame.DataPLayer(getWidthBox2D(),getHeightBox2D(),DATAPLAYER));
        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //System.out.println(convertir((body.getPosition().x - getWidthBox2D()))+","+convertir((body.getPosition().y - getHeightBox2D())));
        setPosition(convertir((body.getPosition().x - getWidthBox2D())), convertir((body.getPosition().y - getHeightBox2D())));
        batch.draw(currentFrame,getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public void act(float delta) {
        if(getVelocity(1,2)==1 && !isColitioned()){
            body.setLinearVelocity(PLAYERSPEED,body.getLinearVelocity().y);
        }else if(getVelocity(1,0)==1 && !isColitioned() ){
            body.setLinearVelocity(-PLAYERSPEED,body.getLinearVelocity().y);
        }else {
            if(isOverFloor()){
                body.setLinearVelocity(0, body.getLinearVelocity().y);
            }
        }
        if(!isJumping &&  !isOverFloor()){
            System.out.println(!isJumping &&  !isOverFloor());
            body.setLinearVelocity(body.getLinearVelocity().x/2, body.getLinearVelocity().y);
            body.applyForceToCenter(body.getLinearVelocity().x/2, -IMPULSE_JUMP * 1.5f,true);
        }

        if (!isJumping && isMustJump() ) {
            jump();
        }
        if(isJump()){
           body.applyForceToCenter(0, -IMPULSE_JUMP * 1.15f,true);
        }
        time+= Gdx.graphics.getDeltaTime();
        currentFrame =  currentAnimation.getKeyFrame(time,true);

        //System.out.print(state);

    }
    public void jump(){
        Vector2 position=body.getPosition();
        body.applyLinearImpulse(0,IMPULSE_JUMP, position.x, position.y, true);
        setJumped(true);
        setVelocity(0,1,0);
    }
    public void initializeAnimations(){
        TextureRegion[] temporal;
        int cont;
        regionPlayer = TextureRegion.split(imagen,imagen.getWidth() / FRAME_COLS_PLAYER, imagen.getHeight() / FRAME_ROWS_PLAYER);

        temporal = new TextureRegion[FRAME_COLS_PLAYER - 3];
        cont = 1;
       //quiet I
        for (int j = 0; j < temporal.length; j++) {

                temporal[j] = regionPlayer[1][j];

        }
        quietI = new Animation(0.3f,temporal);

        temporal = new TextureRegion[FRAME_COLS_PLAYER - 3];
        cont = 1;
        //quiet D
        for (int j = 0; j < temporal.length; j++) {
                temporal[j] = regionPlayer[6][FRAME_COLS_PLAYER-cont];
            cont ++;
        }
        quietD = new Animation(0.3f,temporal);

        temporal = new TextureRegion[FRAME_COLS_PLAYER - 2];
        //walk I
        for (int j = 0; j < temporal.length; j++) {

                temporal[j] = regionPlayer[0][j];

        }
        walkI = new Animation(0.3f,temporal);

        temporal = new TextureRegion[FRAME_COLS_PLAYER - 2];
        cont = 1;
        //walk D
        for (int j = 0; j < temporal.length; j++) {
                temporal[j] = regionPlayer[5][FRAME_COLS_PLAYER - cont];
            cont ++;

        }
        walkD = new Animation(0.3f,temporal);

        temporal = new TextureRegion[FRAME_COLS_PLAYER];
        cont = 1;
        //jump I
        for (int j = 0; j < temporal.length; j++) {
            temporal[j] = regionPlayer[3][j];
        }
        jumpI = new Animation(0.4f,temporal);

        temporal = new TextureRegion[FRAME_COLS_PLAYER];
        cont = 1;
        //jump D
        for (int j = 0; j < temporal.length; j++) {
            temporal[j] = regionPlayer[8][FRAME_COLS_PLAYER-cont];
            cont ++;
        }
        jumpD = new Animation(0.4f,temporal);

       /* //die I
        for (int j = 0; j < (FRAME_COLS_PLAYER - 3); j++) {
            temporal[j] = regionPlayer[4][j];
        }
        dieI = new Animation(0.4f,temporal);
        //die D
        for (int j = 3; j < FRAME_COLS_PLAYER; j++) {
            temporal[j] = regionPlayer[8][j];
        }
        dieD = new Animation(0.4f,temporal);

        //shoot I
        for (int j = 0; j < (FRAME_COLS_PLAYER - 2); j++) {
            temporal[j] = regionPlayer[2][j];
        }
        shootI = new Animation(0.4f,temporal);
        //shoot D
        for (int j = 2; j < FRAME_COLS_PLAYER; j++) {
            temporal[j] = regionPlayer[7][j];
        }
        shootD = new Animation(0.4f,temporal);*/


        currentAnimation = quietD;
        temporal = null;
    }

    // getter and setters

    public void setState(int state){
        if (this.state != state){
            LastState = this.state;
            this.state = state;
            switch(state){
                case WALKD:
                    currentAnimation = walkD;
                    break;
                case WALKI:
                    currentAnimation = walkI;
                    break;
                case JUMP:
                    if(LastState==WALKD || LastState==QUIETD){
                        currentAnimation = jumpD;
                    }else if(LastState==WALKI || LastState==QUIETI){
                        currentAnimation = jumpI;
                    }
                    break;
                case QUIETD:
                    currentAnimation = quietD;
                    break;
                case QUIETI:
                    currentAnimation = quietI;
                    break;
                default:
                    currentAnimation = quietD;
                    break;
            }
        }
    }

    public boolean isOverFloor() {
        return OverFloor;
    }
    public void setOverFloor(boolean overFloor) {
        OverFloor = overFloor;
    }

}

