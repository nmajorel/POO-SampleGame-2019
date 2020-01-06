package management;

import settings.Settings;
import sprite.castle.Castle;
import sprite.soldier.Catapult;
import sprite.soldier.Knight;
import sprite.soldier.Piker;
import sprite.soldier.Soldier;

public class OrderSupport extends Order{

	public OrderSupport(Castle source, Castle target, int nbPikers, int nbKnights, int nbCatapults) {
		super(source, target, nbPikers, nbKnights, nbCatapults);
		mode = Settings.EXIT_TRANSFER;
	}

	/**
	 * Ajoute le soldat aux troupes du château target
	 */
	protected void whenArrived(Soldier soldier) {
		// TODO Auto-generated method stub
		
		
		if(soldier instanceof Piker) {
			
			target.setNbPikers(target.getNbPikers()+1);
			nb_soldiers_created[Settings.PIKER]--;

		}
		

		if(soldier instanceof Knight) {
			
			target.setNbKnights(target.getNbKnights()+1);
			nb_soldiers_created[Settings.PIKER]--;

		}
		

		if(soldier instanceof Catapult) {
			
			target.setNbCatapults(target.getNbCatapults()+1);
			nb_soldiers_created[Settings.PIKER]--;

		}
		
		soldier.remove();
		if(nb_soldiers_created[Settings.PIKER]+ nb_soldiers_created[Settings.KNIGHT] + nb_soldiers_created[Settings.CATAPULT]==0) {
			battleWon_or_transferDone = true;
			this.remove();
		}
		
	}

}
