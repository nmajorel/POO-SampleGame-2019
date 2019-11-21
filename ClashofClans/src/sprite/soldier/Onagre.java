package sprite.soldier;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import settings.Settings;
import shape.Point2D;

public class Onagre extends Soldier {

	private static final int HEALTH = 5;
	private static final int COST = 1000;
	private static final int TIME_PRODUCTION = 50;
	private static final int SPEED = 1;
	private static final int DAMAGE = 10;
	
	public Onagre(Pane layer, Point2D point) {
		super(layer, point,  HEALTH, Settings.SIZE_Onagre, Settings.SIZE_Onagre, COST, TIME_PRODUCTION, SPEED, DAMAGE, Color.CRIMSON);
	}
	
}
