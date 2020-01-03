package sprite.castle;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import settings.Settings;
import shape.Point2D;

public class Taken extends Castle {

	public Taken(Pane layer, Point2D point, double w, double h) {
		super(layer, point, Color.LIMEGREEN, w, h);
		// TODO Auto-generated constructor stub
	}
	
	public Taken(Pane layer, Point2D point, double w, double h, String duke, int gold, int level, int income, int id) {
		super(layer, point, Color.LIMEGREEN, w, h);
		setDuke(duke);
		this.gold = 2000;
		this.id = id;
		getLayer().getChildren().remove(getHbox_id());
		setHbox_id(new HBox());
		drawId();
		setLevel(level);
		setNbKnights(0);
		setNbCatapults(0);
		setNbPikers(0);
		setNbTroops(0);
		
	}

	public  int getIncome() {
		 return getLevel() * 10;
	}
	
}
