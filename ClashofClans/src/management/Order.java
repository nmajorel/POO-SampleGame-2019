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
	
	private static final int PIQUIER = 0;
	private static final int CHEVALIER = 1;
	private static final int ONAGRE = 2;
	
	public Order(Castle source, Castle target, int nb_piquiers, int nb_chevaliers, int nb_onagre) {
		this.source = source;
		this.target = target;
		this.nbPikers = nb_piquiers;
		this.nbKnights = nb_chevaliers;
		this.nbCatapults = nb_onagre;
		this.nb_troops = nb_chevaliers + nb_onagre + nb_piquiers;
		this.moved = false;
		
	}
	
	public void ost_move() {
		if(!moved){
			create_soldier(PIQUIER, nbPikers);
			create_soldier(CHEVALIER, nbKnights);
			create_soldier(ONAGRE, nbCatapults);
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
				case PIQUIER : 
					troops.add(new Piker(source.getLayer(),source.getDoorPoint()));
					break;
				case CHEVALIER : 
					troops.add(new Knight(source.getLayer(),source.getDoorPoint()));
					break;
				case ONAGRE : 
					troops.add(new Catapult(source.getLayer(),source.getDoorPoint()));
					break;
				default: break;
			}
			
		}
	}
}
