package om.narilearsi.librarys.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;



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
        suelo.add(new GroundBase(terrains,world,300,90,160,16,BLOQUE));
        //suelo = new GroundBase(terrains,world,0,0,700,16);// se crea el suelo de la aplicacion
        stage.addActor(player);
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
        stage.act();
        world.step(1 / 60f, 6, 2);
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
