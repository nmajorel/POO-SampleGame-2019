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
import shape.Rectangle;
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
	private Rectangle door;
	
	private int nbPikers;
	private int nbKnights;
	private int nbCatapults;
	
	public Castle(Pane layer, Point2D point, Color c, double w, double h) {
		
		super(layer, point, c, w, h);
		this.duke = "Peter";
		this.gold = 0;
		this.level = Settings.LEVEL_1;
		this.nbPikers = 9;
		this.nbKnights = 4;
		this.nbCatapults = 2;
		this.nb_troops = nbPikers + nbKnights + nbCatapults;
		
		int pick = new Random().nextInt(Directions.values().length);
		this.dir = Directions.values()[pick]; 
	
		this.door = createDoor();

	}

	public void income() {
		this.gold = this.gold + getIncome();
	}
	
	public abstract int getIncome();
	
	public boolean isCastleEmpty() {
		
		return troops.isEmpty();
		
	}
	
	public Rectangle createDoor() {
		
		double x;
		double y;
		
		switch(dir) {
		
			case N :
				x = (getX() + getWidth()/2) - getWidth()/6;
				y = getY();
				return new Rectangle(getLayer(), new Point2D(x,y), Color.BLACK, getWidth()/3, getHeight()/12);
			
			case E :
				
				x = (getX() + getWidth()) - getWidth()/12;
				y = (getY() + getHeight()/2) - getHeight()/6;
				return new Rectangle(getLayer(), new Point2D(x,y), Color.BLACK, getWidth()/12, getHeight()/3);
				
			case S :
				x = (getX() + getWidth()/2) - getWidth()/6;
				y = (getY() + getHeight()) - getHeight()/12;
				return new Rectangle(getLayer(), new Point2D(x,y), Color.BLACK, getWidth()/3, getHeight()/12);
				
			case W :
				
				x = getX();
				y = (getY() + getHeight()/2) - getHeight()/6;
				return new Rectangle(getLayer(), new Point2D(x,y), Color.BLACK, getWidth()/12, getHeight()/3);
		}
		
		return null;
		
	}
	
	
	
	@Override
	public void checkRemovability() {
		// TODO Auto-generated method stub
		
	}
	public void move() {
		
    }

	public void makeAnOrder() {
		this.order.ost_move();
	}
	

	public Point2D getDoorPoint() {
		return door.getP();
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

	public Directions getDir() {
		return dir;
	}

	public Order getOrder() {
		return order;
	}

	public int getNbPikers() {
		return nbPikers;
	}

	public int getNbKnights() {
		return nbKnights;
	}

	public int getNbCatapults() {
		return nbCatapults;
	}

	
	
	
	
	
	public void setDuke(String duke) {
		this.duke = duke;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setTroops(List<Soldier> troops) {
		this.troops = troops;
	}

	public void setNb_troops(int nb_troops) {
		this.nb_troops = nb_troops;
	}

	public void setLab(Laboratory lab) {
		this.lab = lab;
	}

	public void setDir(Directions dir) {
		this.dir = dir;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setNbPikers(int nbPikers) {
		this.nbPikers = nbPikers;
	}

	public void setNbKnights(int nbKnights) {
		this.nbKnights = nbKnights;
	}

	public void setNbCatapults(int nbCatapults) {
		this.nbCatapults = nbCatapults;
	}

	

	
}
