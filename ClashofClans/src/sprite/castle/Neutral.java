package sprite.castle;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import shape.Point2D;

public class Neutral extends Castle {

	public Neutral(Pane layer, Point2D point, double w, double h) {
		super(layer, point, Color.LIGHTGREY, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void income() {
		
		this.gold = getGold() + getLevel();
		// TODO Auto-generated method stub
		
	}

}
