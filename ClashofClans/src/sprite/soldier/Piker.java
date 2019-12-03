package sprite.soldier;

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
	
	public Piker(Pane layer, Point2D point) {
		super(layer, point, HEALTH, Settings.SIZE_PIKER, Settings.SIZE_PIKER, COST, TIME_PRODUCTION, SPEED, DAMAGE, Color.YELLOW);
	}	

}
