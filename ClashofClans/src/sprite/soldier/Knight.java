package sprite.soldier;

import javafx.scene.layout.Pane;
import settings.Settings;
import shape.Point2D;

public class Knight extends Soldier {
	
	public Knight(Pane layer, Point2D point) {
		super(layer, point, Settings.SIZE_KNIGHT, Settings.SIZE_KNIGHT, Settings.KNIGHT);
	}
	
}
