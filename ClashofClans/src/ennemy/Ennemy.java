package ennemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;
import management.Laboratory;
import management.Order;
import settings.Settings;
import sprite.castle.Castle;
import sprite.castle.Castle.enumCastle;
 
public class Ennemy {

	private List<Castle> castles = new ArrayList<Castle>();
	private boolean resetTimer;
    private long lastUpdate;
    private long elapsedNanos;
    private static final int NB_ROUNDS_ACTION = 2;
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
		switch(rnd) {
			case 0: 
				Castle c = castles.get(random.nextInt(castles.size()));
				Laboratory lab = c.getLab();
				int nbPikers = random.nextInt(c.getNbPikers());
				int nbKnights = random.nextInt(c.getNbKnights());
				int nbCatapults = random.nextInt(c.getNbCatapults());
				int i = 0;
				boolean no_valid = true;
				while(i>100 || no_valid) {
					int cost = nbPikers*100 + nbKnights*500 + nbCatapults*1000;
					if(cost <= c.getGold()) {
						lab.addProductionQueue(enumCastle.Piker, nbPikers);
						lab.addProductionQueue(enumCastle.Knight, nbKnights);
						lab.addProductionQueue(enumCastle.Catapult, nbCatapults);
						no_valid = false;
					}
					nbPikers = random.nextInt(c.getNbPikers());
					nbKnights = random.nextInt(c.getNbKnights());
					nbCatapults = random.nextInt(c.getNbCatapults());
					i++;
				}
				break;
				
			case 1:
				Castle c_up = castles.get(random.nextInt(castles.size()));
				Laboratory lab_up = c_up.getLab();
				lab_up.addProductionQueue(enumCastle.Level, 1);
				break;
				
			case 2:
				Castle c_attack = castles.get(random.nextInt(castles.size()));
				int pikers = random.nextInt(c_attack.getNbPikers());
				int knights = random.nextInt(c_attack.getNbKnights());
				int catapults = random.nextInt(c_attack.getNbCatapults());
				Order order = new Order(c_attack, getCastleToAttack(), pikers, knights, catapults);
				c_attack.addOrder(order);
				break;
			
			default: break;
		}
		
	}
	
	public void setCastleToAttack( ArrayList<Castle> castles) {
		target = castles.get(new Random().nextInt(castles.size()));
	}
	
	public Castle getCastleToAttack() {
		return target;
	}

	public List<Castle> getCastles() {
		return castles;
	}

	public void addCastles(Castle castle) {
		this.castles.add(castle);
	}
	
}