package sprite.soldier;


import javafx.scene.layout.Pane;
import settings.Settings;
import shape.Point2D;

public class Catapult extends Soldier {

	public Catapult(Pane layer, Point2D point) {
		super(layer, point, Settings.SIZE_CATAPULT, Settings.SIZE_CATAPULT, Settings.CATAPULT);
	}
	
}
