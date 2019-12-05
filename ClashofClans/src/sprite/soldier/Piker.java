package sprite.soldier;

import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import settings.Settings;
import shape.Point2D;

public class Piker extends Soldier {

	private static final int HEALTH = 2;
	private static final int COST = 100;
	private static final int TIME_PRODUCTION = 5;
	private static final int SPEED = 2;
	private static final int DAMAGE = 1;
	
	private static Color[] yellows  = { Color.rgb(255,255,0), Color.rgb(238,238,0), Color.rgb(205, 205, 0)};
	
	public Piker(Pane layer, Point2D point) {
		super(layer, point, HEALTH, Settings.SIZE_PIKER, Settings.SIZE_PIKER, COST, TIME_PRODUCTION, SPEED, DAMAGE, yellows[new Random().nextInt(yellows.length)]);
	}	

}
