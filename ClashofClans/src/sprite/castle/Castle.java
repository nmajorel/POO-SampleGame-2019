package sprite.castle;

import player.Player;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
	private ArrayList<Soldier> troops = new ArrayList<>();
	private int nbTroops;
	private Laboratory lab;
	private Directions dir;
	private ArrayList <Order> orders = new ArrayList<Order>();
	private Rectangle door;
	
	private int nbPikers;
	private int nbKnights;
	private int nbCatapults;
	
	private HBox hbox_id = new HBox();
	protected static int static_id = 0;
	protected int id = 0;
	
	
	public enum enumCastle{
		
		Piker(0, "Pikers : "),
		Knight(1, "Knights : "),
		Catapult(2, "Catapults : "),
		Gold(3, "Gold : "),
		Level(4, "Level : "),
		Income(5, "Income : "),
		Duke(6 , "Duke : ");

		
		private int indexElement;
		private String text;
		
		public void setIndexElement(int indexElement) {
			this.indexElement = indexElement;
		}

		private enumCastle(int indexSoldier, String element) {
			this.indexElement = indexSoldier;
			this.text = element;
		}

		public int getIndexElement() {
			return indexElement;
		}


		public String getText() {
			return text;
		}
				
	
	}	
	
	public Castle(Pane layer, Point2D point, Color c, double w, double h) {
		
		super(layer, point, c, w, h);
		this.duke = "Peter";
		this.gold = 2000;
		this.level = Settings.LEVEL_1;
		this.nbPikers = 9;
		this.nbKnights = 4;
		this.nbCatapults = 2;
		this.nbTroops = nbPikers + nbKnights + nbCatapults;
		
		int pick = new Random().nextInt(Directions.values().length);
		this.dir = Directions.values()[pick]; 
	
		this.door = createDoor();
		
		this.lab = new Laboratory();
		
		this.id = newId();
		
		
		
		drawId();

	}
	
	
	public void drawId() {

		getLayer().getChildren().add( hbox_id );	
		Text text = new Text(""+id);
		text.setFont(Font.font("Verdana", FontWeight.LIGHT, 20));
		hbox_id.relocate(getX()+getWidth()/2, getY()+getHeight()/2);
		hbox_id.getChildren().add(text);
		

	    
	}
	
	
	public static int newId() {
		
		static_id++;
		return static_id;
			
	}	
	



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public static boolean canIncome(double elapsedSeconds) {
		
		return elapsedSeconds >= Settings.NB_ROUNDS_INCOME * Settings.TIME_ROUND_SECOND;
		
		
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
	

	public String toString() {
		
		
		return "Pikers : " + nbPikers + "\n\n\n" + "Knights : " + nbKnights + "\n\n\n" +  "Catapults : " + nbCatapults + "\n\n\n" + "Gold : " + gold + "\n\n\n" +  "level : " + level + "\n\n\n" +  "Income : " + getIncome() + "\n\n\n" + 
				"Duke : " + duke + "\n\n\n";
		
		
	}
	
	
	@Override
	public void checkRemovability() {
		// TODO Auto-generated method stub
		
	}
	public void move() {
		
    }
	
	public void remove() {
        this.removable = true;
        troops.clear();
        orders.clear();
        door.removeFromLayer();
    }
	
	public void removeOrders() {
		Iterator<Order> iter = orders.iterator();
		while (iter.hasNext()) {
			Order order = iter.next();

			if (order.isRemovable()) {
				// remove from list
				iter.remove();
			}
		}
			
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

	public int getNbTroops() {
		return nbTroops;
	}

	public Laboratory getLab() {
		return lab;
	}

	public Directions getDir() {
		return dir;
	}

	public ArrayList<Order> getOrder() {
		return orders;
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

	public void setTroops(ArrayList<Soldier> troops) {
		this.troops = troops;
	}

	public void setNbAllTroops(int nb_pikers, int nb_knights, int nb_catapults) {
		this.nbPikers = nb_pikers;
		this.nbKnights = nb_knights;
		this.nbCatapults = nb_catapults;
		this.nbTroops = nb_pikers + nb_knights + nb_catapults;
	}

	public void setLab(Laboratory lab) {
		this.lab = lab;
	}

	public void setDir(Directions dir) {
		this.dir = dir;
	}

	public void addOrder(Order order) {
		this.orders.add(order);
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

	public void setNbTroops(int nbTroops) {
		this.nbTroops = nbTroops;
	}


	public HBox getHbox_id() {
		return hbox_id;
	}


	public void setHbox_id(HBox hbox_id) {
		this.hbox_id = hbox_id;
	}

	

	
}
