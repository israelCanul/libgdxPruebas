package om.narilearsi.librarys;

/**
 * Created by viane on 30/04/2017.
 */

public class ConfigGame {
    public static final Float METRICSCENEBOX2D = 16f;
    public static final int WIDTHSCREEN = 640;
    public static final int HEIGHTSCREEN = 480;
    public static final int DOWN=5;
    public static final int JUMP=4;
    public static final int WALKD=3;
    public static final int WALKI=2;
    public static final int QUIET=1;
    public static final String DATAPLAYER = "PLAYER";
    public static final String SUELO = "SUELO";
    public static final String BLOQUE = "BLOQUE";
    public static final String ENEMY = "ENENY";
    public static final String GOAL = "GOAL";
    public static final float INITSPEED = 8f;
    public static final int IMPULSE_JUMP = 16;

    //funciones reutilisables
    public static float convertir(Float valor){
        return METRICSCENEBOX2D * valor;
    }

    public static Double getAngle(float Ancho, float Alto){
        double angleB = Math.atan(Alto/Ancho);
        double anglec =angleB * (180/Math.PI);
        return anglec;
    }

}
