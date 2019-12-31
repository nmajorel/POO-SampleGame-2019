package sprite.soldier;

import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import shape.Point2D;
import sprite.Sprite;

public class Soldier extends Sprite {
	
	public static final int [] HEALTH = { 2, 3, 5 } ;
	public static final int [] COST =  { 100, 500, 1000}  ;
	public static final int [] TIME_PRODUCTION = { 5, 20, 50}  ;
	public static final int  [] SPEED = { 2, 3, 1}  ;
	public static final int [] DAMAGE = { 1, 5, 10} ;
	
	private static final Color[][] colors = {
			{ Color.rgb(255,255,0), Color.rgb(238,238,0), Color.rgb(205, 205, 0)},
			{ Color.rgb(139, 131, 120), Color.rgb(131, 139, 139), Color.rgb(139, 131, 134)},
			{ Color.rgb(255, 0,0), Color.rgb(238, 0,0), Color.rgb(205, 0, 0)}
	};
	
    private int health;
	private int production_cost;
    private int productionRound;
    protected double speed;
    private int damage;
	private boolean left_the_castle = false;
	private int type = -1;
	
	public Soldier(Pane layer, Point2D point, double w, double h, int type) {
		super(layer, point, colors[type][new Random().nextInt(colors[type].length)], w, h);
		
		this.health = HEALTH[type];
		this.production_cost = COST[type];
		this.productionRound = TIME_PRODUCTION[type];
		this.speed = SPEED[type];
		this.damage = DAMAGE[type];
		
		getImageView().setStroke(Color.BLACK);
		this.type = type;
		
	}
	
	public void checkRemovability() {
		
	}
	
	// Return true if the soldier arrive to the destination
	public boolean move(Point2D destination, double alignement) {
		double x = destination.getX()+alignement,
		y = destination.getY(),
		px = getX(), py = getY();
		double speed = getSpeed();
		boolean arrived = true;

		if(px + speed< x){
        	p.setX(px + speed);
        	arrived = false;
        }else{
        	if(px - speed> x){
        		p.setX(px - speed);
        		arrived = false;
        	}
        }
        if(py + speed< y){
        	p.setY(py + speed);
        	arrived = false;
        }else{
        	if(py - speed> y){
        		p.setY(py - speed);
        		arrived = false;
        	}
        }
        return arrived;
    }

	public double getSpeed() {
		return speed;
	}

	public boolean isLeft_the_castle() {
		return left_the_castle;
	}

	public void setLeft_the_castle(boolean left_the_castle) {
		this.left_the_castle = left_the_castle;
	}

	public int getProductionRound() {
		return productionRound;
	}

	public int getType() {
		return type;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getDamage() {
		return damage;
	}



	
	
    
    

}
