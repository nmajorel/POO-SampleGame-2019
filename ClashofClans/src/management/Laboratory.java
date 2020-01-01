package management;

import static settings.Settings.*;


import java.util.concurrent.LinkedBlockingQueue;

import sprite.castle.Castle;


public class Laboratory {
	
	private LinkedBlockingQueue<Integer> productionQueue = new LinkedBlockingQueue<Integer>();
	


	private double timeProductionTab[] = { TIME_PIKER_SECOND, TIME_KNIGHT_SECOND, TIME_CATAPULT_SECOND, TIME_UPGRADE_LEVEL_SECOND};
	
	private int costProductionTab[] = { COST_PIKER, COST_KNIGHT, COST_CATAPULT, COST_UPGRADE_LEVEL };
	
	private long lastUpdate;
	
	private long elapsedNanos;
	
	private boolean resetTimer;

	
	
	public Laboratory() {
		

		this.resetTimer = true;
		
	}
	
	

	public boolean isPikerQueueFirst() {

		return productionQueue.peek() == PIKER;

	}
	public boolean isKnightQueueFirst() {

		return productionQueue.peek() == KNIGHT;

	}

	public boolean isCatapultQueueFirst() {

		return productionQueue.peek() == CATAPULT;

	}

	public boolean isUpgradeLevelQueueFirst() {

		return productionQueue.peek() == UPGRADE_LEVEL;

	}

	
	public boolean finishedProduction(double elapsedSeconds){


		int element = productionQueue.peek();

		if(elapsedSeconds >= timeProductionTab[element]) {

			return true;			
		}		

		return false;

	}

	
	public int getProduction() {
		
		return productionQueue.peek();
		
	}
	
	public int getCostProduction() {
		
		int element = productionQueue.peek();
		int cost = costProductionTab[element];
		
		return cost;
		
	}
	
	
	
	public void removeProductionQueue() {
		
		productionQueue.poll();
		
	}
	
	


	public LinkedBlockingQueue<Integer> getProductionQueue() {
		return productionQueue;
	}


	public void addProductionQueue(int soldier) {
		
		this.productionQueue.offer(soldier);
		
	}



	public boolean isRunning() {
		return !(productionQueue.peek() == null);
	}

	
	public void checkProduction(long currentNanoTime, Castle castlePlayer) {
		
		if(resetTimer) {
			lastUpdate = currentNanoTime - elapsedNanos;
			resetTimer = false;
		}

		elapsedNanos = currentNanoTime - lastUpdate ;	
		
		double elapsedSeconds = elapsedNanos * Math.pow(10, -9) ;
						
		if(finishedProduction(elapsedSeconds)) {

			int element = getProduction();	
			
			removeProductionQueue();

			switch(element) {

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

	public double getElapsedSeconds() {
		return (elapsedNanos * Math.pow(10, -9));
	}


	public void setElapsedNanos(long elapsedNanos) {
		this.elapsedNanos = elapsedNanos;
	}

	
	
	






}
		
		

		
		
