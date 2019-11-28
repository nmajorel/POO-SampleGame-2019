import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import shape.Point2D;
import shape.Rectangle;
import sprite.Sprite;

import sprite.castle.Taken;

public class Window extends Sprite{
	
	private Rectangle suppr;
	
	public Window(Pane layer, Point2D point, double w, double h) {
		super(layer, point, Color.DARKGREY, w, h);
		this.suppr = new Rectangle(layer, new Point2D((point.getX()+w) - (w/10),point.getY()), Color.RED, w/10, w/10);

		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void checkRemovability() {
		// TODO Auto-generated method stub
		
	}
	
	
	public Rectangle getSuppr() {
		return suppr;
	}

}
