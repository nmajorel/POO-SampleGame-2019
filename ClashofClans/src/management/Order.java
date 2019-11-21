package management;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import shape.Point2D;
import sprite.castle.Castle;
import sprite.soldier.Chevalier;
import sprite.soldier.Onagre;
import sprite.soldier.Piquier;
import sprite.soldier.Soldier;

public class Order {
	
	private List<Soldier> troops = new ArrayList<Soldier>();
	private Castle source, target;
	private int nb_troops;
	private int nb_piquiers;
	private int nb_chevaliers;
	private int nb_onagre;
	boolean moved;
	
	private static final int PIQUIER = 0;
	private static final int CHEVALIER = 1;
	private static final int ONAGRE = 2;
	
	public Order(Castle source, Castle target, int nb_piquiers, int nb_chevaliers, int nb_onagre) {
		this.source = source;
		this.target = target;
		this.nb_piquiers = nb_piquiers;
		this.nb_chevaliers = nb_chevaliers;
		this.nb_onagre = nb_onagre;
		this.nb_troops = nb_chevaliers + nb_onagre + nb_piquiers;
		this.moved = false;
		
	}
	
	public void ost_move() {
		if(!moved){
			create_soldier(PIQUIER, nb_piquiers);
			create_soldier(CHEVALIER, nb_chevaliers);
			create_soldier(ONAGRE, nb_onagre);
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
					troops.add(new Piquier(source.getLayer(),source.getDoorPoint()));
					break;
				case CHEVALIER : 
					troops.add(new Chevalier(source.getLayer(),source.getDoorPoint()));
					break;
				case ONAGRE : 
					troops.add(new Onagre(source.getLayer(),source.getDoorPoint()));
					break;
				default: break;
			}
			
		}
	}
}
