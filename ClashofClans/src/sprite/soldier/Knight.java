package sprite.soldier;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import settings.Settings;
import shape.Point2D;

public class Knight extends Soldier {
	
	private static final int HEALTH = 3;
	private static final int COST = 500;
	private static final int TIME_PRODUCTION = 20;
	private static final int SPEED = 3;
	private static final int DAMAGE = 5;
	
	public Knight(Pane layer, Point2D point) {
		super(layer, point, HEALTH, Settings.SIZE_CHEVALIER, Settings.SIZE_CHEVALIER, COST, TIME_PRODUCTION, SPEED, DAMAGE, Color.DARKGRAY);
	}
	
}
