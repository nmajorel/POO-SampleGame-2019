package management;

import java.util.Random;

import sprite.castle.Castle;
import sprite.soldier.Catapult;
import sprite.soldier.Knight;
import sprite.soldier.Piker;
import sprite.soldier.Soldier;

public class OrderAttack extends Order{

	public OrderAttack(Castle source, Castle target, int nbPikers, int nbKnights, int nbCatapults) {
		super(source, target, nbPikers, nbKnights, nbCatapults);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void whenArrived(Soldier soldier) {
		
		if(!battle_won) {
			
			if(apply_damage(soldier.getDamage(), soldier.getType())) {
				battleEnd();
			}
			soldier.remove();
			
		}
		// TODO Auto-generated method stub
		
	}

	
	
	private  boolean apply_damage(int damage, int type) {
		int [] tab = {target.getNbPikers(), target.getNbKnights(), target.getNbCatapults()};
		System.out.println("pikers : " + tab[0] + "  knights : " + tab[1] + "  catapults : " + tab[2]);
		for(int d = 0; d<damage; d++) {
			if(tab[0]+ tab[1] + tab[2]==0)
				return true;
			int indice = -1;
			while(indice==-1 ) {
				int i = new Random().nextInt(3);
				if(tab[i]!=0) {
					indice = i;
				}
			}
			int i = new Random().nextInt(lifes_tab[indice].length);
			lifes_tab[indice][i]--;
			if(lifes_tab[indice][i]==0) {
				tab[indice]--;
			}
			if(target.getNbTroops()==0) {
				return true;
			}
		}
		nb_soldiers_created[type]--;
		target.setNbAllTroops(tab[0], tab[1], tab[2]);
		
		return false;
	}
	
	
	
	private void battleEnd() {
		System.out.println("Fini!");
		target.remove();
		battle_won = true;
		this.remove();
		source.setNbPikers(source.getNbPikers() + nb_soldiers_created[0]);
		source.setNbKnights(source.getNbKnights() + nb_soldiers_created[1]);
		source.setNbCatapults(source.getNbCatapults() + nb_soldiers_created[2]);
	}
	
	
	
}


