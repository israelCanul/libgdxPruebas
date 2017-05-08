package om.narilearsi.librarys.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.sun.javafx.binding.DoubleConstant;


import om.narilearsi.librarys.BaseScreen;
import om.narilearsi.librarys.Maingame;

import om.narilearsi.librarys.actors.ActorJugador;
import om.narilearsi.librarys.bodys.GroundBase;
import om.narilearsi.librarys.listeners.ContactListeners;
import om.narilearsi.librarys.listeners.Procesador;

import static om.narilearsi.librarys.ConfigGame.BLOQUE;
import static om.narilearsi.librarys.ConfigGame.HEIGHTSCREEN;
import static om.narilearsi.librarys.ConfigGame.METRICSCENEBOX2D;
import static om.narilearsi.librarys.ConfigGame.WIDTHSCREEN;
import static om.narilearsi.librarys.ConfigGame.degreeToRadians;
import static om.narilearsi.librarys.ConfigGame.getAngle;
import static om.narilearsi.librarys.ConfigGame.radiansToDegree;

/**
 * Created by viane on 29/04/2017.
 */

public class GameScreen extends BaseScreen {
    private Texture textureJugador,terrains;
    public ActorJugador player;
    public Array<GroundBase> suelo= new Array<GroundBase>();
    public SpriteBatch batch;
    Stage stage;
    World world;
    public Procesador procesador;


    public Body b;


    // for debug
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    public GameScreen(Maingame game) {
        super(game);

        textureJugador = new Texture("correrSprite.png");
        terrains = new Texture("ground.png");
        world = new World(new Vector2(0, -10f), false);
        stage = new Stage(new FitViewport(WIDTHSCREEN, HEIGHTSCREEN));
        procesador = new Procesador(this);
        Gdx.input.setInputProcessor(procesador);
        world.setContactListener(new ContactListeners(this));




        //se crea el render de box2d solo para debug
        debugRenderer= new Box2DDebugRenderer();
        //System.out.println(Gdx.graphics.getWidth() +" height : "+Gdx.graphics.getHeight());
        //se crea la camara de tile
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTHSCREEN / METRICSCENEBOX2D, HEIGHTSCREEN / METRICSCENEBOX2D);
    }

    @Override
    public void show() {
        player =new ActorJugador(textureJugador,world,0,32);
        batch = new SpriteBatch();
        suelo.add(new GroundBase(terrains,world,0,0,700,16));
        suelo.add(new GroundBase(terrains,world,300,60,80,16,BLOQUE));
        suelo.add(new GroundBase(terrains,world,400,90,80,16,BLOQUE));
        //suelo = new GroundBase(terrains,world,0,0,700,16);// se crea el suelo de la aplicacion
        stage.addActor(player);



        //prueba de chain shape
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(6,10);

        BodyDef bodyDef1 = new BodyDef();
        bodyDef1.type = BodyDef.BodyType.DynamicBody;
        bodyDef1.position.set(7,10);


//        ChainShape ground = new ChainShape();
//        ground.createChain(new Vector2[]{new Vector2(0,0),new Vector2(2,0),new Vector2(2,2),new Vector2(0,2),new Vector2(0,0)});
//
//
//        ChainShape ground2 = new ChainShape();
//        ground2.createChain(new Vector2[]{new Vector2(4,0),new Vector2(6,0),new Vector2(6,2),new Vector2(4,2),new Vector2(4,0)});


        PolygonShape ground = new PolygonShape();
        ground.setAsBox(1f,2);

        PolygonShape ground2 = new PolygonShape();
        ground2.setAsBox(0.25f,1f);



        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ground;
        fixtureDef.restitution = 0;

        FixtureDef fixtureDef1 = new FixtureDef();
        fixtureDef1.shape = ground2;
        fixtureDef1.restitution = 0f;

        Body a =  world.createBody(bodyDef);
        a.createFixture(fixtureDef);
        b = world.createBody(bodyDef1);
        b.createFixture(fixtureDef1);

        // distance between
        RopeJointDef distanceJointDef = new RopeJointDef();
        distanceJointDef.bodyA = a;
        distanceJointDef.bodyB = b;
        distanceJointDef.maxLength = 0f;
        distanceJointDef.localAnchorA.set(0,0);
        distanceJointDef.localAnchorB.set(0f,1f);


        world.createJoint(distanceJointDef);


//
//
//        double aaaa= getAngle(17, 10);
//        System.out.println(aaaa);
        ground.dispose();
    }


    @Override
    public void hide() {
        player.dispose();
        for (int i = 0; i < suelo.size; i++) {
            suelo.get(i).dispose();
        }
        suelo.clear();
        stage.clear();
    }

    @Override
    public void dispose() {
        textureJugador.dispose();
        terrains.dispose();
        player.dispose();
        debugRenderer.dispose();
        world.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // None of the following options work.

        b.setTransform(b.getWorldCenter(), degreeToRadians(90) );
        System.out.println(radiansToDegree(b.getAngle()));

        world.step(1 / 60f, 6, 2);
        stage.act();


        camera.update();
        debugRenderer.render(world,camera.combined);
        stage.draw();
        batch.begin();
        for (int i = 0; i < suelo.size; i++) {
            suelo.get(i).draw(batch);
        }
        batch.end();
    }
}
