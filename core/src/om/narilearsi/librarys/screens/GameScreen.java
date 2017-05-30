package om.narilearsi.librarys.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;


import java.util.ArrayList;

import om.narilearsi.librarys.BaseScreen;
import om.narilearsi.librarys.Maingame;

import om.narilearsi.librarys.actors.ActorJugador;
import om.narilearsi.librarys.bodys.BlockTiledMap;
import om.narilearsi.librarys.bodys.GroundBase;
import om.narilearsi.librarys.listeners.ContactListeners;
import om.narilearsi.librarys.listeners.Procesador;
import om.narilearsi.librarys.maps.ExampleMap;

import static om.narilearsi.librarys.ConfigGame.BLOQUE;
import static om.narilearsi.librarys.ConfigGame.HEIGHTSCREEN;
import static om.narilearsi.librarys.ConfigGame.INITSPEED;
import static om.narilearsi.librarys.ConfigGame.JUMP;
import static om.narilearsi.librarys.ConfigGame.METRICSCENEBOX2D;
import static om.narilearsi.librarys.ConfigGame.WALK;
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
    public World world;
    public Procesador procesador;

    // for tiled maps
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    private TiledMapTileLayer suelos;
    public ExampleMap mapa;
    public ArrayList<BlockTiledMap> listaBlockes;

    // for debug
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    FPSLogger loger;

    public GameScreen(Maingame game) {
        super(game);

        textureJugador = new Texture("soldier1.png");
        terrains = new Texture("ground.png");
        //se crea el mapa
        mapa=new ExampleMap(this);




        world = new World(new Vector2(0, -10f), false);



        stage = new Stage(new FitViewport(WIDTHSCREEN, HEIGHTSCREEN));
        procesador = new Procesador(this);
        Gdx.input.setInputProcessor(procesador);
        world.setContactListener(new ContactListeners(this));

        //se crea el render de box2d solo para debug
        debugRenderer= new Box2DDebugRenderer();
        loger = new FPSLogger();
        //System.out.println(Gdx.graphics.getWidth() +" height : "+Gdx.graphics.getHeight());
        //se crea la camara de tile
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTHSCREEN / METRICSCENEBOX2D, HEIGHTSCREEN / METRICSCENEBOX2D);

        //se crea la camara de tile
        renderer = new OrthogonalTiledMapRenderer(mapa.map, 1 / METRICSCENEBOX2D );
    }

    @Override
    public void show() {
        player =new ActorJugador(textureJugador,world,128,128);
        batch = new SpriteBatch();
        //suelo.add(new GroundBase(terrains,world,0,0,700,16));
        //suelo.add(new GroundBase(terrains,world,300,60,80,16,BLOQUE));
        //suelo.add(new GroundBase(terrains,world,400,90,80,16,BLOQUE));
        //suelo.add(new GroundBase(terrains,world,400,200,16,16,BLOQUE));
        //suelo.removeIndex(2);
        //suelo = new GroundBase(terrains,world,0,0,700,16);// se crea el suelo de la aplicacion
        stage.addActor(player);
        //inicializar los objetos incrustados en el mapObject
       mapa.initMapObjects();
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
        mapa.detach();
        player.dispose();
        debugRenderer.dispose();
        world.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float vel = player.body.getLinearVelocity().x;
        float speed = vel * delta * METRICSCENEBOX2D;
        stage.act();
        world.step(1 / 60f, 6, 2);


        camera.update();


        //if (player.getForcesApply(1,2)==1  && player.direction[1]==1 && player.isAlive) {

            //System.out.println(speed);
            stage.getCamera().translate(speed, 0, 0);
            camera.translate(vel * delta, 0);
        //}else if(player.getForcesApply(1,0)==1 && player.direction[0]==1 && player.isAlive){

            //System.out.println(speed);
           // stage.getCamera().translate(-(speed), 0, 0);
            //camera.translate(-(INITSPEED * delta), 0);
        //}



        renderer.setView(camera);
        renderer.getBatch().begin();
        renderer.renderTileLayer(mapa.f_2);
        renderer.renderTileLayer(mapa.f_1);
        renderer.renderTileLayer(mapa.fondo);
        renderer.getBatch().end();
        stage.draw();
        renderer.getBatch().begin();
        renderer.renderTileLayer(mapa.d_1);
        renderer.renderTileLayer(mapa.d_2);
        renderer.getBatch().end();



        batch.begin();
        for (int i = 0; i < suelo.size; i++) {
            suelo.get(i).draw(batch);
        }
        batch.end();
        debugRenderer.render(world,camera.combined);

    }
}
