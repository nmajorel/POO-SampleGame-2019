package management;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import shape.Point2D;
import sprite.castle.Castle;
import sprite.soldier.Knight;
import sprite.soldier.Catapult;
import sprite.soldier.Piker;
import sprite.soldier.Soldier;

public class Order {
	
	private List<Soldier> troops = new ArrayList<Soldier>();
	private Castle source, target;
	private int nb_troops;
	private int nbPikers;
	private int nbKnights;
	private int nbCatapults;
	boolean moved;
	
	private static final int PIKER = 0;
	private static final int KNIGHT = 1;
	private static final int CATAPULT = 2;
	
	public Order(Castle source, Castle target, int nbPikers, int nbKnights, int nbCatapults) {
		this.source = source;
		this.target = target;
		this.nbPikers = nbPikers;
		this.nbKnights = nbKnights;
		this.nbCatapults = nbCatapults;
		this.nb_troops = nbPikers + nbKnights + nbCatapults;
		this.moved = false;
		
	}
	
	public void ost_move() {
		if(!moved){
			create_soldier(PIKER, nbPikers);
			create_soldier(KNIGHT, nbKnights);
			create_soldier(CATAPULT, nbCatapults);
			moved = true;
		}
		leave_the_castle();
		for (Soldier soldier : troops) {
			if(soldier.isLeft_the_castle())
				soldier.move(front_of_the_door(soldier));
		}
	}

	public List<Soldier> getTroops() {
		return troops;
	}
	
	private void leave_the_castle() 
	 {
		for (Soldier soldier : troops) {
			if(!soldier.isLeft_the_castle()){
				switch(source.getDir()){
					case N : 
						soldier.setY(soldier.getY() - soldier.getSpeed()); break;
					case E : 
						soldier.setX(soldier.getX() + soldier.getSpeed()); break;
					case S : 
						soldier.setY(soldier.getY() + soldier.getSpeed()); break;
					case W : 
						soldier.setX(soldier.getX() - soldier.getSpeed()); break;
					default : break;
				}
				if(!source.inside(soldier))
					soldier.setLeft_the_castle(true);
			}
		}
	 }
	
	private Point2D front_of_the_door(Soldier soldier){
		Point2D front_point = new Point2D(target.getDoorPoint());
		double w = soldier.getWidth();
		switch(target.getDir()){
			case N : 
				front_point.translate(0, -w); break;
			case E : 
				front_point.translate(w, 0); break;
			case S : 
				front_point.translate(0, w); break;
			case W : 
				front_point.translate(-w, 0); break;
			default : break;
		}
		return front_point;
	}
	private void create_soldier(int type, int nb_soldiers){
		for(int i = 0; i < nb_soldiers; i++) {
			switch(type){
				case PIKER : 
					troops.add(new Piker(source.getLayer(),source.getDoorPoint()));
					break;
				case KNIGHT : 
					troops.add(new Knight(source.getLayer(),source.getDoorPoint()));
					break;
				case CATAPULT : 
					troops.add(new Catapult(source.getLayer(),source.getDoorPoint()));
					break;
				default: break;
			}
			
		}
	}
}
