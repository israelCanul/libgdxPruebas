package om.narilearsi.librarys;

/**
 * Created by viane on 30/04/2017.
 */

public class ConfigGame {
    public static final Float METRICSCENEBOX2D = 16f;
    public static final int WIDTHSCREEN = 640;
    public static final int HEIGHTSCREEN = 480;
    public static final int DOWN=6;
    public static final int JUMP=5;
    public static final int WALK=3;
    public static final int QUIET=1;
    public static final String DATAPLAYER = "PLAYER";
    public static final String SUELO = "SUELO";
    public static final String BLOQUE = "BLOQUE";
    public static final String ENEMY = "ENENY";
    public static final String GOAL = "GOAL";
    public static final float INITSPEED = 9f;
    public static final float IMPULSE_JUMP = 32f;
    public static final int FRAME_COLS_PLAYER = 6;
    public static final int FRAME_ROWS_PLAYER = 10;

    //funciones reutilisables
    public static float convertir(Float valor){
        return METRICSCENEBOX2D * valor;
    }


    public static class DataPLayer{
        private float width, heigth;
        private String name;

        public DataPLayer(float width,float heigth,String name){
            this.width = width;
            this.heigth = heigth;
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public float getHeigth() {
            return heigth;
        }

        public float getWidth() {
            return width;
        }
    }
}
