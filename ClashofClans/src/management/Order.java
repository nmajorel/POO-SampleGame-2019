package management;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import settings.Settings;
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
	
	private int [] nb_soldiers_created = {0, 0, 0};
	private int [] nb_soldiers;
	
	private int [] create_frequency = { 0, 0, 0};
	private static final int [] max_frequency = { 25, 20, 45};
	
	
	public Order(Castle source, Castle target, int nbPikers, int nbKnights, int nbCatapults) {
		this.source = source;
		this.target = target;
		this.nbPikers = nbPikers;
		this.nbKnights = nbKnights;
		this.nbCatapults = nbCatapults;
		this.nb_troops = nbPikers + nbKnights + nbCatapults;
		nb_soldiers = new int[]{this.nbPikers, this.nbKnights, this.nbCatapults};
		
	}
	
	public void ost_move() {
		
		for(int i = 0; i< Settings.NB_TYPE_SOLDIERS; i++){
			if(nb_soldiers_created[i] < nb_soldiers[i] && create_frequency[i]==0)
				create_soldier(i);
		}
		for(int i = 0; i < Settings.NB_TYPE_SOLDIERS; i++){
			create_frequency[i]++;
			if(create_frequency[i] > max_frequency[i])
				create_frequency[i] = 0;
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
						soldier.setY(soldier.getY() - soldier.getSpeed() ); break;
					case E : 
						soldier.setX(soldier.getX() + soldier.getSpeed() ); break;
					case S : 
						soldier.setY(soldier.getY() + soldier.getSpeed() ); break;
					case W : 
						soldier.setX(soldier.getX() - soldier.getSpeed() ); break;
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
	private void create_soldier(int type){
		int nb = 0;
		switch(type){
			case Settings.PIKER : 
				nb = nbPikers;break;
			case Settings.KNIGHT : 
				nb = nbKnights;break;
			case Settings.CATAPULT : 
				nb = nbCatapults;break;
			default: break;
		}
		if(nb_soldiers_created[type]<nb){
			switch(type){
				case Settings.PIKER : 
					troops.add(new Piker(source.getLayer(),source.getDoorPoint()));
					nb_soldiers_created[type]++; break;
				case Settings.KNIGHT : 
					troops.add(new Knight(source.getLayer(),source.getDoorPoint()));
					nb_soldiers_created[type]++; break;
				case Settings.CATAPULT : 
					troops.add(new Catapult(source.getLayer(),source.getDoorPoint()));
					nb_soldiers_created[type]++; break;
				default: break;
			}
		}		
	}
}
