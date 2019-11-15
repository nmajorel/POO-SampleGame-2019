import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;



public class Castle extends Sprite{
	

	private static enum directions { N, E, S, W};
	
	private String duke;
	private int gold;
	private int level;
	private List<Soldier> troops = new ArrayList<>();
	private int nb_troops = 10 ;
	private Laboratory lab;
	private Order order;
	private directions dir;
	
	
	public Castle(Pane layer, Point2D point, int health, double w, double h) {
		super(layer, point, health, Color.LIMEGREEN, w, h);
		this.duke = "Peter";
		this.gold = 0;
		this.level = Settings.LEVEL_1;
		
		
		this.dir = directions.N;
		
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void checkRemovability() {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
