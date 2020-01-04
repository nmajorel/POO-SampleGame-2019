package management;

import sprite.castle.Castle;
import sprite.soldier.Catapult;
import sprite.soldier.Knight;
import sprite.soldier.Piker;
import sprite.soldier.Soldier;

public class OrderSupport extends Order{

	public OrderSupport(Castle source, Castle target, int nbPikers, int nbKnights, int nbCatapults) {
		super(source, target, nbPikers, nbKnights, nbCatapults);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void whenArrived(Soldier soldier) {
		// TODO Auto-generated method stub
		
		
		if(soldier instanceof Piker) {
			
			target.setNbPikers(target.getNbPikers()+1);

		}
		

		if(soldier instanceof Knight) {
			
			target.setNbKnights(target.getNbKnights()+1);

		}
		

		if(soldier instanceof Catapult) {
			
			target.setNbCatapults(target.getNbCatapults()+1);

		}
		
		soldier.remove();
		
	}

}
