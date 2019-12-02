package sprite.castle;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import shape.Point2D;

public class Neutral extends Castle {

	public Neutral(Pane layer, Point2D point, double w, double h) {
		super(layer, point, Color.DIMGREY, w, h);
		// TODO Auto-generated constructor stub
	}

	public  int getIncome() {
		 return getLevel() ;
	}

}
