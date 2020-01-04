package ennemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import management.Laboratory;
import management.Order;
import settings.Settings;
import sprite.castle.Castle;
import sprite.castle.Castle.enumCastle;
 
public class Ennemy {

	private ArrayList<Castle> castles = new ArrayList<Castle>();
	private boolean resetTimer;
    private long lastUpdate;
    private long elapsedNanos;
    private static final int NB_ROUNDS_ACTION = 1;
    Castle target;
	
	public Ennemy(Castle castle) {
		
		resetTimer = true;	
		target = null;
		castles.add(castle);

	}

	public void checkAction(long currentNanoTime) {
		
		if(resetTimer) {
			lastUpdate = currentNanoTime - elapsedNanos;
			resetTimer = false;
		}	

		elapsedNanos = currentNanoTime - lastUpdate ;	
		
		double elapsedSeconds = elapsedNanos * Math.pow(10, -9) ;
		
		if( elapsedSeconds >= NB_ROUNDS_ACTION * Settings.TIME_ROUND_SECOND ) {
			elapsedNanos = 0;
			resetTimer = true;
			doAction();
			
		}
	}
		
	private void doAction() {
		int rnd = new Random().nextInt(3);
		Random random = new Random();
		if(rnd==0) {
			Castle c = castles.get(random.nextInt(castles.size()));
			Laboratory lab = c.getLab();				
			int nbPikers = c.getNbPikers();
			int nbKnights = c.getNbKnights();
			int nbCatapults = c.getNbCatapults();
			int pikers = (nbPikers==0)? 0 : random.nextInt(nbPikers);
			int knights = (nbKnights==0)? 0 : random.nextInt(nbKnights);
			int catapults = (nbCatapults==0)? 0 : random.nextInt(nbCatapults);
			int i = 0;
			boolean no_valid = true;
			while(i>100 || no_valid) {
				int cost = nbPikers*100 + nbKnights*500 + nbCatapults*1000;
				if(cost <= c.getGold()) {
					lab.addProductionQueue(enumCastle.Piker, pikers);
					lab.addProductionQueue(enumCastle.Knight, knights);
					lab.addProductionQueue(enumCastle.Catapult, catapults);
					no_valid = false;
				}
				pikers = (nbPikers==0)? 0 : random.nextInt(nbPikers);
				knights = (nbKnights==0)? 0 : random.nextInt(nbKnights);
				catapults = (nbCatapults==0)? 0 : random.nextInt(nbCatapults);
				i++;
			}
		}else {
			if(rnd==1) {
				Castle c_up = castles.get(random.nextInt(castles.size()));
				Laboratory lab_up = c_up.getLab();
				lab_up.addProductionQueue(enumCastle.Level, 1);
			}else {
				Castle c = castles.get(random.nextInt(castles.size()));
				int nbPikers = c.getNbPikers();
				int nbKnights = c.getNbKnights();
				int nbCatapults = c.getNbCatapults();
				int pikers = (nbPikers==0)? 0 : random.nextInt(nbPikers);
				int knights = (nbKnights==0)? 0 : random.nextInt(nbKnights);
				int catapults = (nbCatapults==0)? 0 : random.nextInt(nbCatapults);
				c.setNbAllTroops(nbPikers - pikers, nbKnights - knights, nbCatapults - catapults);
				c.addOrder(new OrderAttack(c, getCastleToAttack(), pikers, knights, catapults));
			}
		}
		
	}
	
	public void setCastleToAttack( ArrayList<Castle> othercastles) {
		ArrayList<Castle> targets = new ArrayList<Castle>();
		for(int i =0; i< othercastles.size(); i++) {
			if(!this.castles.contains(othercastles.get(i)) ){
				targets.add(othercastles.get(i));
			}
		}
		target = targets.get(new Random().nextInt(targets.size()));
	}
	
	public Castle getCastleToAttack() {
		return target;
	}

	public ArrayList<Castle> getCastles() {
		return castles;
	}

	public void addCastles(Castle castle) {
		this.castles.add(castle);
	}
	
}