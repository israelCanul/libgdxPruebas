package om.narilearsi.librarys.bodys;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;



import static om.narilearsi.librarys.ConfigGame.METRICSCENEBOX2D;
import static om.narilearsi.librarys.ConfigGame.SUELO;
import static om.narilearsi.librarys.ConfigGame.convertir;

/**
 * Created by viane on 04/05/2017.
 */

public class GroundBase extends BlockBase {

    public GroundBase(Texture texture, World world, float x, float y, float width, float height) {
        super(texture, world, x, y, width, height);
        body = world.createBody(createBody());
        createShape(body);
    }
    public GroundBase(Texture texture, World world, float x, float y, float width, float height,String dataobject) {
        super(texture, world, x, y, width, height);
        body = world.createBody(createBody());
        createShape(body,dataobject);
    }

    public void createShape(Body body) {
        // por si el body no a sido inicializado
        if (body == null) body = world.createBody(createBody());

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / METRICSCENEBOX2D) / 2, (getHeight() / METRICSCENEBOX2D) / 2);
        fixture = body.createFixture(shape, 1);
        fixture.setUserData(SUELO);
        shape.dispose();
    }
    public void createShape(Body body, String data) {
        // por si el body no a sido inicializado
        if (body == null) body = world.createBody(createBody());

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / METRICSCENEBOX2D) / 2, (getHeight() / METRICSCENEBOX2D) / 2);
        fixture = body.createFixture(shape, 1);
        fixture.setUserData(data);
        shape.dispose();
    }
    @Override
    public void draw(Batch batch) {
        setPosition(convertir((body.getPosition().x - getWidthBox2D())), convertir((body.getPosition().y - getHeightBox2D())));
        batch.draw(image, getX(), getY(), getWidth(), getHeight());
    }

}
