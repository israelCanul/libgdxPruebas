package com.examples.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.examples.game.Maingame;

/**
 * Created by icanul on 4/26/17.
 */

public class Box2dScreen extends BaseScreen{
    public Box2dScreen(Maingame game) {
        super(game);
    }

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private Body minijoe,sueloBody,pinchoBody;
    private Fixture minijorFixture,sueloFixture,pinchoFixture;

    @Override
    public void show() {
        System.out.println(Gdx.graphics.getWidth() + "," + Gdx.graphics.getHeight());
        world = new World(new Vector2(0,-10),true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera((5.33f ),(4));
        camera.translate(0,1);

        BodyDef minijoeDef = createJoeBodyDef();
        minijoe = world.createBody(minijoeDef);
        sueloBody = world.createBody(createSueloBodyDef());
        pinchoBody = world.createBody(createPinchoBodyDef(1));


        PolygonShape minijoeShape = new PolygonShape();
        minijoeShape.setAsBox(0.5f,0.5f);
        minijorFixture = minijoe.createFixture(minijoeShape,1);
        minijoeShape.dispose();

        PolygonShape sueloShape = new PolygonShape();
        sueloShape.setAsBox(500,1);
        sueloFixture = sueloBody.createFixture(sueloShape,1);
        sueloShape.dispose();

        pinchoFixture = createPinchoFixture(pinchoBody);

    }

    public BodyDef createSueloBodyDef(){
        BodyDef def = new BodyDef();
        def.position.set(0,-1);
        def.type = BodyDef.BodyType.StaticBody;
        return def;
    }
    public BodyDef createPinchoBodyDef(float x){
        BodyDef def = new BodyDef();
        def.position.set(x,0.5f);
        def.type = BodyDef.BodyType.StaticBody;
        return def;
    }
    public BodyDef createJoeBodyDef(){
        BodyDef def = new BodyDef();
        def.position.set(-1,1);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }

    public Fixture createPinchoFixture(Body pinchoBody){
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-0.5f,-0.5f);
        vertices[1] = new Vector2(0,0.5f);
        vertices[2] = new Vector2(0.5f,0.5f);
        PolygonShape p = new PolygonShape();
        p.set(vertices);
        Fixture fix = pinchoBody.createFixture(p,1);
        p.dispose();
        return fix;
    }
    @Override
    public void dispose() {
        minijoe.destroyFixture(minijorFixture);
        sueloBody.destroyFixture(sueloFixture);
        pinchoBody.destroyFixture(pinchoFixture);
        world.dispose();
        debugRenderer.dispose();
        world.destroyBody(minijoe);
        world.destroyBody(sueloBody);
        world.destroyBody(pinchoBody);
    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1/60f,6,2);
        camera.update();
        debugRenderer.render(world,camera.combined);
    }
}
