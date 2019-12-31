package sprite.soldier;

import javafx.scene.layout.Pane;
import settings.Settings;
import shape.Point2D;

public class Piker extends Soldier {

	public Piker(Pane layer, Point2D point) {
		super(layer, point, Settings.SIZE_PIKER, Settings.SIZE_PIKER, Settings.PIKER);
	}	

}
