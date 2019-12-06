package sprite.soldier;

import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import settings.Settings;
import shape.Point2D;

public class Catapult extends Soldier {

	private static final int HEALTH = 5;
	private static final int COST = 1000;
	private static final int TIME_PRODUCTION = 50;
	private static final int SPEED = 1;
	private static final int DAMAGE = 10;
	
	private static final Color[] reds  = { Color.rgb(255, 0,0), Color.rgb(238, 0,0), Color.rgb(205, 0, 0)};
	
	public Catapult(Pane layer, Point2D point) {
		super(layer, point,  HEALTH, Settings.SIZE_CATAPULT, Settings.SIZE_CATAPULT, COST, TIME_PRODUCTION, SPEED, DAMAGE, reds[new Random().nextInt(reds.length)], Settings.CATAPULT);
	}
	
}
