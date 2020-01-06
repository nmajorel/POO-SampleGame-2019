package ennemy;

import java.util.ArrayList;
import java.util.Random;
import management.Laboratory;
import management.OrderAttack;
import settings.Settings;
import sprite.castle.Castle;
import sprite.castle.Castle.enumCastle;
import sprite.soldier.Soldier;
 
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

	/**
	* Choisi une action aléatoire toutes les NB_ROUNDS_ACTION tours
	* @param currentNanoTime : le temps actuel du jeu
	**/
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
	
	/**
	 * Fait une de 3 actions possibles pour un ennemi.<br>
	 * Avec chacun une probabilité d'être exécuté <br>
	 * <ul> 
	 * <li> Produire des troupes : 30% </li>
	 * <li> Augmenter le niveau du château : 20% </li>
	 * <li> Attaquer un château aléatoire : 40% </li>
	 * <li> Ne rien faire : 10% </li>
	 * </ul>
	 */
	private void doAction() {
		int rnd = new Random().nextInt(10);
		Random random = new Random();
		if(rnd<=2) {
			Castle c = castles.get(random.nextInt(castles.size()));
			Laboratory lab = c.getLab();			
			int gold = c.getGold();
			int pikers = 1 + random.nextInt(10);
			int knights = random.nextInt(5);
			int catapults = random.nextInt(3);
			int i = 0;
			boolean no_valid = true;
			int cost;
			while(i<100 && no_valid) {
				cost = pikers*Soldier.COST[Settings.PIKER] + knights*Soldier.COST[Settings.KNIGHT] + catapults*Soldier.COST[Settings.CATAPULT];
				if(cost <= gold) {
					lab.addProductionQueue(enumCastle.Piker, pikers);
					lab.addProductionQueue(enumCastle.Knight, knights);
					lab.addProductionQueue(enumCastle.Catapult, catapults);
					c.setGold(gold - cost);
					no_valid = false;
				}
				pikers = 1 + random.nextInt(10);
				knights = random.nextInt(5);
				catapults = random.nextInt(3);
				i++;
			}
		}else {
			if(rnd==3 || rnd==4) {
				Castle c_up = castles.get(random.nextInt(castles.size()));
				Laboratory lab_up = c_up.getLab();
				lab_up.addProductionQueue(enumCastle.Level, 1);
			}else {
				if(rnd>4 && rnd<9) {
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
		
	}
	
	/**
	 * Choisi une cible qui n'appartient pas à l'objet Ennemy parmi la liste de châteaux passé en paramètre 
	 * @param othercastles : liste de châteaux
	 */
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