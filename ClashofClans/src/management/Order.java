package management;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import settings.Settings;
import shape.Point2D;
import sprite.Sprite;
import sprite.castle.Castle;
import sprite.soldier.Knight;
import sprite.soldier.Catapult;
import sprite.soldier.Piker;
import sprite.soldier.Soldier;

public abstract class Order {
	
	private ArrayList<Soldier> troops = new ArrayList<Soldier>();
	protected Castle source, target;
	private int nb_troops;
	private int nbPikers;
	private int nbKnights;
	private int nbCatapults;
	private boolean all_arrived = false;
	private boolean removable = false;
	private double alignment = 20;
	private static final double[] spacement = { 0, Settings.SIZE_PIKER+5.0, Settings.SIZE_PIKER+5.0 +Settings.SIZE_KNIGHT+5.0};
	
	protected int [] nb_soldiers_created = {0, 0, 0};
	private int [] nb_soldiers;
	
	private int [] create_frequency = { 0, 0, 0};
	private static final int [] max_frequency = { 25, 20, 45};
	
	int [][] lifes_tab;
	
	boolean battle_won = false;
	
	
	public Order(Castle source, Castle target, int nbPikers, int nbKnights, int nbCatapults) {
		this.source = source;
		this.target = target;
		this.nbPikers = nbPikers;
		this.nbKnights = nbKnights;
		this.nbCatapults = nbCatapults;
		this.nb_troops = nbPikers + nbKnights + nbCatapults;
		nb_soldiers = new int[]{this.nbPikers, this.nbKnights, this.nbCatapults};
		
		lifes_tab = new int [3][Integer.max(nbCatapults, Integer.max(nbKnights, nbPikers))];
		for(int i = 0; i<3; i++) {
			for(int n = 0; n<nb_soldiers[i]; n++) {
				lifes_tab[i][n] = Soldier.HEALTH[i];
			}
		}
		
	}
	
	public void remove() {
        this.removable = true;
    }
	public boolean isRemovable() {
        return removable;
    }
	
	public boolean ost_move() {
		if(all_arrived){
			movement_for_each(new Point2D(target.getCenterX(), target.getCenterY()) );
		}else{
			ost_moving();
		}
		if(removable) {
			troops.forEach(soldier -> soldier.remove());
		}
		removeSprites(troops);
		
		return battle_won;
	}

	
	private void ost_moving(){
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
		movement_for_each(new Point2D(target.getP() ) );
	}
	
	
	private void movement_for_each(Point2D p){
		double [] i_soldiers = {0, 0, 0};
		Point2D destination = p;
		double w = 0;
		boolean move; 
		
		boolean tmp_arrived = true;
		for (Soldier soldier : troops) {
			if(soldier.isLeft_the_castle()){
				w = soldier.getWidth() + spacement[soldier.getType()];
				destination.translate(0, -w);
				if(!all_arrived) {
					move = soldier.move( destination, i_soldiers[soldier.getType()]*alignment); 
				}else {
					move = soldier.move( destination, 0);
				}
				if(!move) {
					tmp_arrived = false;
				}else {
					
					if(all_arrived) {
						
						whenArrived(soldier);
						
					}
				}
				destination.translate(0, +w);
				
			}else{
				tmp_arrived = false;
			}
			i_soldiers[soldier.getType()]++;
		}
		if(tmp_arrived){
			all_arrived = true;
		}
	}
	
	protected abstract void whenArrived(Soldier soldier);
	

	
	private void removeSprites(ArrayList<Soldier> spriteList) {
		Iterator<Soldier> iter = spriteList.iterator();
		while (iter.hasNext()) {
			Sprite sprite = iter.next();

			if (sprite.isRemovable()) {
				// remove from layer
				sprite.removeFromLayer();
				// remove from list
				iter.remove();
			}
		}
			
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
	
	
	public List<Soldier> getTroops() {
		return troops;
	}

	public Castle getTarget() {
		return target;
	}
}
