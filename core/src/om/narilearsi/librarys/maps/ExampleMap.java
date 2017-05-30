package om.narilearsi.librarys.maps;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import com.badlogic.gdx.physics.box2d.Body;

import com.badlogic.gdx.physics.box2d.Shape;


import java.util.ArrayList;

import om.narilearsi.librarys.bodys.BlockTiledMap;
import om.narilearsi.librarys.screens.GameScreen;


/**
 * Created by icanul on 4/25/16.
 */
public class ExampleMap {

    public final TiledMap map;

    public final TiledMapTileLayer objetoslayer;
    public final TiledMapTileLayer d_1;
    public final TiledMapTileLayer d_2;
    public final TiledMapTileLayer fondo;
    public final TiledMapTileLayer f_1;
    public final TiledMapTileLayer f_2;
    public ArrayList<BlockTiledMap> listaBlockes;




    public GameScreen mainGameScreen;
    private MapObjects objects;



    public ExampleMap(GameScreen mainGameScreen){
        this.mainGameScreen=mainGameScreen;
        map = new TmxMapLoader().load("mimundo.tmx");
        d_1 = (TiledMapTileLayer)map.getLayers().get("d_1");
        d_2 = (TiledMapTileLayer)map.getLayers().get("d_2");
        fondo= (TiledMapTileLayer)map.getLayers().get("fondo");
        f_1 = (TiledMapTileLayer)map.getLayers().get("f_1");
        f_2 = (TiledMapTileLayer)map.getLayers().get("f_2");

        objetoslayer = (TiledMapTileLayer)map.getLayers().get("objetos");

        objects = map.getLayers().get("colicion").getObjects();
        listaBlockes = new ArrayList<BlockTiledMap>();
    }


    public void initMapObjects(){
        for (MapObject object:objects) {
            if (object instanceof TextureMapObject) {
                continue;
            }
            Shape shape;
            MapObject temp=object;

            boolean suelo = Boolean.valueOf(temp.getProperties().get("suelo").toString());
            boolean bloque = Boolean.valueOf(temp.getProperties().get("bloque").toString());
            listaBlockes.add(new BlockTiledMap(this.mainGameScreen.world,temp));
        }
    }

    public void detach(){
        map.dispose();
    }

}
