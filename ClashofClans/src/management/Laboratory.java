package management;

import static settings.Settings.*;

import java.util.Hashtable;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.scene.text.Text;
import sprite.castle.Castle;
import sprite.castle.Castle.enumCastle;


public class Laboratory {
	
	
	
	
	
	
	/**
		file de production FIFO (First in First out ) qui prend des enum de type enumCastle (Piker, Knight, Catapult ..)
	 * 
	 */


	private LinkedBlockingQueue<enumCastle> productionQueue = new LinkedBlockingQueue<enumCastle>();




	/**
	hastable qui associe une type de donn�e ( Piker, Knight .. ) � sa dur�e de production
	 * 
	 */


	private Hashtable <enumCastle,Double> htTimeProduction = new Hashtable<enumCastle,Double>();


	/**
	hastable qui associe une type de donn�e ( Piker, Knight .. ) � son co�t
	 * 
	 */

	private Hashtable <enumCastle,Integer> htCostProduction = new Hashtable<enumCastle,Integer>();
	



	/**
	hastable qui associe une type de donn�e ( Piker, Knight .. ) � son co�t
	 * 
	 */


	
	/**
	derni�re date (en Nano seconds) avant interruption
	 * 
	 */
	
	private long lastUpdate;
	
	
	
	/**
	temps �coul� entre le d�but de la production et le temps actuel en Nanoseconds
	 * 
	 */
	
	
	private long elapsedNanos;
	
	/**
	remet � jour lastUpdate( quand on fait pause avec la fen�tre ou la touche p)
	 * 
	 */
	
	
	private boolean resetTimer;

	/**
	initalise les hashtable avec des donn�es fixes
	 * 
	 */
	
	
	
	
	
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
	
	
	/**
	v�rifie si la production de l'�lement de type enumCastle est termin�e en d�but de file
	
	
	@param  elapsedSeconds
	
		temps �coul� entre le d�but de la production et le temps actuel en secondes
		
	
	@return vrai si production termin�e , faux dans le contraire
	 * 
	 */
	

	public boolean finishedProduction(double elapsedSeconds){


		enumCastle element = productionQueue.peek();

		if(elapsedSeconds >= htTimeProduction.get(element)) {

			return true;			
		}		

		return false;

	}
	
	
	
	/**

	
	
	@return l'�l�ment en d�but de file
	 * 
	 */
	

	
	public enumCastle getProduction() {
		
		return productionQueue.peek();
		
	}
	
	
	
	
	/**

	
	
	@return le prix de l'�l�ment en d�but de file
	 * 
	 */
	
	
	
	
	public int getCostProduction() {
		
		enumCastle element = productionQueue.peek();
		

		int cost = htCostProduction.get(element);
		
		return cost;
		
	}
	
	
	
	/**

	
	
	enl�ve l'�l�ment de la file
	 * 
	 */

	
	public void removeProductionQueue() {
		
		productionQueue.poll();
		
	}
	


	public LinkedBlockingQueue<enumCastle> getProductionQueue() {
		return productionQueue;
	}

	
	/**

	
	
	ajoute l'�l�ment en fin de file range fois
	
	@param element
		element � ajout� (Piker, knight, level ..)
		
	@param range
	
		nombre de fois que l'on rajoute l'�l�ment
	
	
	 * 
	 */
	
	
	

	public void addProductionQueue(enumCastle element, int range) {
		
		
		for(int i = 0; i < range; i++) {
			
			this.productionQueue.offer(element);
			
		}
		

		
	}

	
	/**

	
	@return vrai si le laboratoire est en train de tourner ( il y a au moins un �l�ment dans la file )
	
	 * 
	 */
	
	


	public boolean isRunning() {
		return !(productionQueue.peek() == null);
	}

	
	/**

	v�rifie la production en cours
	
	
	@param currentNanoTime 
	
		temps actuel en nanoSeconds
		
	@param castlePlayer
	
		castle auquelle on rajoute un �l�ment de type Piker Knight  ou on augmente de niveau si la production est termin�e

	
	
	 * 
	 */
	
	

	
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
	
	
	/**

	reset le Timer 



	 * 
	 */




	public void resetTimer() {
		this.resetTimer = true;

	}


	/**

	@return le temps �coul� entre le d�but de la production et le temps actuel en second



	 * 
	 */





	public double getElapsedSeconds() {
		return (elapsedNanos * Math.pow(10, -9));
	}


	public void setElapsedNanos(long elapsedNanos) {
		this.elapsedNanos = elapsedNanos;
	}

	
	
	






}
		
		

		
		
