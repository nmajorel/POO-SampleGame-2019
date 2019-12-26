package sprite.soldier;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import shape.Point2D;
import sprite.Sprite;

public class Soldier extends Sprite {
	
    private int health;
	private int production_cost;
    private int productionRound;
    protected double speed;
    private int damage;
	private boolean left_the_castle = false;
	
	
	public Soldier(Pane layer, Point2D point, int health, double w, double h, int production_cost, int production_round, int speed, int damage, Color c) {
		super(layer, point, c, w, h);
		this.health = health;
		this.color = c;
		this.production_cost = production_cost;
		this.productionRound = productionRound;
		this.speed = speed;
		this.damage = damage;
		getImageView().setStroke(Color.BLACK);
		
	}
	
	public void checkRemovability() {
		
	}
	
	public void move(Point2D destination) {
		double x = destination.getX(),
		y = destination.getY(),
		px = getX(), py = getY();
		double speed = getSpeed();
		if(px + speed< x){
        	p.setX(px + speed);
        }else{
        	if(px - speed> x){
        		p.setX(px - speed);
        	}
        }
        if(py + speed< y){
        	p.setY(py + speed);
        }else{
        	if(py - speed> y){
        		p.setY(py - speed);
        	}
        }
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



	
	
    
    

}
