package management;

import static settings.Settings.CATAPULT;
import static settings.Settings.KNIGHT;
import static settings.Settings.PIKER;

import java.util.concurrent.LinkedBlockingQueue;

import settings.Settings;
import sprite.castle.Castle;


public class Laboratory {
	
	private LinkedBlockingQueue<Integer> soldiersTrainingQueue = new LinkedBlockingQueue<Integer>();
	
	private boolean isUpgrade;

	int nbRoundsProductionTab[] = { Settings.NB_ROUNDS_PRODUCTION_PIKER, Settings.NB_ROUNDS_PRODUCTION_KNIGHT, Settings.NB_ROUNDS_PRODUCTION_CATAPULT };
	
	private long lastUpdate;
	
	private long elapsedNanos;
	
	private boolean resetTimer;

	
	
	public Laboratory() {
		
	
		this.isUpgrade = false;
		

		this.resetTimer = true;
		
	}
	
	public boolean finishedProduction(double elapsedSeconds){


		int soldier = soldiersTrainingQueue.peek();

		if(elapsedSeconds >= nbRoundsProductionTab[soldier] * Settings.TIME_ROUND_SECOND) {

			return true;			
		}		

		return false;

	}

	
	public int getSoldierProduction() {
		
		return soldiersTrainingQueue.poll();
		
	}
	
	

	public LinkedBlockingQueue<Integer> getSoldiersTrainingQueue() {
		return soldiersTrainingQueue;
	}

	public void addSoldiersTrainingQueue(int soldier) {
		
		this.soldiersTrainingQueue.offer(soldier);
		
	}

	public boolean isUpgrade() {
		return isUpgrade;
	}

	public boolean isRunning() {
		return !(soldiersTrainingQueue.peek() == null);
	}

	
	public void checkProduction(long currentNanoTime, Castle castlePlayer) {
		
		if(resetTimer) {
			lastUpdate = currentNanoTime - elapsedNanos;
			resetTimer = false;
		}

		elapsedNanos = currentNanoTime - lastUpdate ;	
		
		double elapsedSeconds = elapsedNanos * Math.pow(10, -9) ;
						
		if(finishedProduction(elapsedSeconds)) {

			int soldier = getSoldierProduction();	

			switch(soldier) {

			case PIKER:
				
				castlePlayer.setNbPikers(castlePlayer.getNbPikers()+1);
				System.out.println("PIKER : TIME = " + elapsedSeconds);
		
				break;

			case KNIGHT:
				castlePlayer.setNbKnights(castlePlayer.getNbKnights()+1);
				System.out.println("KNIGHT : TIME = " + elapsedSeconds);

				break;

			case CATAPULT:
				castlePlayer.setNbCatapults(castlePlayer.getNbCatapults()+1);
				System.out.println("CATAPULT : TIME = " + elapsedSeconds);

				break;


			}
			elapsedNanos = 0;
			resetTimer = true;


		}		
		
		
	}

	public void resetTimer() {
		this.resetTimer = true;
	}

	
	






}
		
		
