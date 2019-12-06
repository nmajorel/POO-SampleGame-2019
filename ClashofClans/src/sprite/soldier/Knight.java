package sprite.soldier;

import java.util.Random;

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
	
	private static final Color[] grays  = { Color.rgb(139, 131, 120), Color.rgb(131, 139, 139), Color.rgb(139, 131, 134)};
	
	public Knight(Pane layer, Point2D point) {
		super(layer, point, HEALTH, Settings.SIZE_KNIGHT, Settings.SIZE_KNIGHT, COST, TIME_PRODUCTION, SPEED, DAMAGE, grays[new Random().nextInt(grays.length)], Settings.KNIGHT);
	}
	
}
