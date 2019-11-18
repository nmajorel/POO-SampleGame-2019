package sprite.castle;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import shape.Point2D;

public class Taken extends Castle {

	public Taken(Pane layer, Point2D point, double w, double h) {
		super(layer, point, Color.LIMEGREEN, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void income() {
		
		this.gold = getGold() + getLevel()*10;
		// TODO Auto-generated method stub
		
	}

}
