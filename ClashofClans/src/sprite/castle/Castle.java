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
	
	private int nb_piquiers;
	private int nb_chevaliers;
	private int nb_onagre;
	
	public Castle(Pane layer, Point2D point, Color c, double w, double h) {
		
		super(layer, point, c, w, h);
		this.duke = "Peter";
		this.gold = 0;
		this.level = Settings.LEVEL_1;
		this.nb_piquiers = 9;
		this.nb_chevaliers = 4;
		this.nb_onagre = 2;
		this.nb_troops = nb_piquiers + nb_chevaliers + nb_onagre;
		
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

	public void doOrder() {
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

	public Rectangle getDoor() {
		return door;
	}

	public int getNb_piquiers() {
		return nb_piquiers;
	}

	public int getNb_chevaliers() {
		return nb_chevaliers;
	}

	public int getNb_onagre() {
		return nb_onagre;
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

	public void setNb_piquiers(int nb_piquiers) {
		this.nb_piquiers = nb_piquiers;
	}

	public void setNb_chevaliers(int nb_chevaliers) {
		this.nb_chevaliers = nb_chevaliers;
	}

	public void setNb_onagre(int nb_onagre) {
		this.nb_onagre = nb_onagre;
	}
	
	

	
}
