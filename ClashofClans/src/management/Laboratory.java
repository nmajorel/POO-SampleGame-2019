package management;

import static settings.Settings.*;

import java.util.Hashtable;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.scene.text.Text;
import sprite.castle.Castle;
import sprite.castle.Castle.enumCastle;


public class Laboratory {
	
	private LinkedBlockingQueue<enumCastle> productionQueue = new LinkedBlockingQueue<enumCastle>();
	
	private Hashtable <enumCastle,Double> htTimeProduction = new Hashtable<enumCastle,Double>();
	
	private Hashtable <enumCastle,Integer> htCostProduction = new Hashtable<enumCastle,Integer>();
	
	
	private long lastUpdate;
	
	private long elapsedNanos;
	
	private boolean resetTimer;

	
	
	public Laboratory() {
		
		
		
		htTimeProduction.put(enumCastle.Piker, TIME_PIKER_SECOND);
		htTimeProduction.put(enumCastle.Knight, TIME_KNIGHT_SECOND);
		htTimeProduction.put(enumCastle.Catapult, TIME_CATAPULT_SECOND);
		htTimeProduction.put(enumCastle.Level, TIME_UPGRADE_LEVEL_SECOND);
		
		htCostProduction.put(enumCastle.Piker, COST_PIKER);
		htCostProduction.put(enumCastle.Knight, COST_KNIGHT);
		htCostProduction.put(enumCastle.Catapult, COST_CATAPULT);
		htCostProduction.put(enumCastle.Level, COST_UPGRADE_LEVEL);
		

		this.resetTimer = true;
		
	}
	
	
	
	public boolean finishedProduction(double elapsedSeconds){


		enumCastle element = productionQueue.peek();

		if(elapsedSeconds >= htTimeProduction.get(element)) {

			return true;			
		}		

		return false;

	}

	
	public enumCastle getProduction() {
		
		return productionQueue.peek();
		
	}
	
	public int getCostProduction() {
		
		enumCastle element = productionQueue.peek();
		

		int cost = htCostProduction.get(element);
		
		return cost;
		
	}
	
	
	
	public void removeProductionQueue() {
		
		productionQueue.poll();
		
	}
	
	


	public LinkedBlockingQueue<enumCastle> getProductionQueue() {
		return productionQueue;
	}


	public void addProductionQueue(enumCastle element, int range) {
		
		
		for(int i = 0; i < range; i++) {
			
			this.productionQueue.offer(element);
			
		}
		

		
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

			enumCastle element = getProduction();	
			
			removeProductionQueue();
			
			if(element == enumCastle.Piker) {
				castlePlayer.setNbPikers(castlePlayer.getNbPikers()+1);
				System.out.println("PIKER : TIME = " + elapsedSeconds);
				
			}
			
			if(element == enumCastle.Knight) {
				
				castlePlayer.setNbKnights(castlePlayer.getNbKnights()+1);
				System.out.println("KNIGHT : TIME = " + elapsedSeconds);
				
			}
			
			if(element == enumCastle.Catapult) {
				
				castlePlayer.setNbCatapults(castlePlayer.getNbCatapults()+1);
				System.out.println("CATAPULT : TIME = " + elapsedSeconds);
				
			}
			
			if(element == enumCastle.Level) {
				
				castlePlayer.setLevel(castlePlayer.getLevel()+1);
				System.out.println("UPGRADE : TIME = " + elapsedSeconds);
				
				
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
		
		

		
		
