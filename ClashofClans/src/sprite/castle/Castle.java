package sprite.castle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import management.Laboratory;
import management.Order;
import settings.Settings;
import settings.Settings.Directions;
import shape.Point2D;
import sprite.Sprite;
import sprite.soldier.Soldier;


public abstract class Castle extends Sprite{
	
	private String duke;
	protected int gold;
	private int level;
	private List<Soldier> troops = new ArrayList<>();
	private int nb_troops;
	private Laboratory lab;
	private Directions dir;
	private Order order;
	private Door door;
	
	public Castle(Pane layer, Point2D point, Color c, double w, double h) {
		
		super(layer, point, c, w, h);
		this.duke = "Peter";
		this.gold = 0;
		this.level = Settings.LEVEL_1;
		this.nb_troops = 10;
		
		int pick = new Random().nextInt(Directions.values().length);
		this.dir = Directions.values()[pick]; 
	
		this.door = createDoor();

	}
	
	public abstract void income();
	
	public boolean isCastleEmpty() {
		
		return troops.isEmpty();
		
	}
	
	public Door createDoor() {
		
		double x;
		double y;
		
		switch(dir) {
		
			case N :
				x = (getX() + getWidth()/2) - getWidth()/6;
				y = getY();
				return new Door(getLayer(), new Point2D(x,y), getWidth()/3, getHeight()/12);
			
			case E :
				
				x = (getX() + getWidth()) - getWidth()/12;
				y = (getY() + getHeight()/2) - getHeight()/6;
				return new Door(getLayer(), new Point2D(x,y), getWidth()/12, getHeight()/3);
				
			case S :
				x = (getX() + getWidth()/2) - getWidth()/6;
				y = (getY() + getHeight()) - getHeight()/12;
				return new Door(getLayer(), new Point2D(x,y), getWidth()/3, getHeight()/12);
				
			case W :
				
				x = getX();
				y = (getY() + getHeight()/2) - getHeight()/6;
				return new Door(getLayer(), new Point2D(x,y), getWidth()/12, getHeight()/3);
		}
		
		return null;
		
	}
	
	@Override
	public void checkRemovability() {
		// TODO Auto-generated method stub
		
	}
	
	public String getDuke() {
		return duke;
	}

	public int getGold() {
		return gold;
	}

	public int getLevel() {
		return level;
	}

	public List<Soldier> getTroops() {
		return troops;
	}

	public int getNb_troops() {
		return nb_troops;
	}

	public Laboratory getLab() {
		return lab;
	}

	public Order getOrder() {
		return order;
	}

	public Directions getDir() {
		return dir;
	}	

}
