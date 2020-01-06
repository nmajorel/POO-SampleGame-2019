package management;

import java.util.Random;

import settings.Settings;
import sprite.castle.Castle;
import sprite.soldier.Soldier;

public class OrderAttack extends Order{

	public OrderAttack(Castle source, Castle target, int nbPikers, int nbKnights, int nbCatapults) {
		super(source, target, nbPikers, nbKnights, nbCatapults);
		mode = Settings.EXIT_ATTACK;
	}

	/**
	 * Effectue l'attaque du soldat tant que la bataille n'est pas fini <br>
	 * Si le nombre de troupes du château attaqué à 0, effectue la fonction battleEnd()
	 */
	protected void whenArrived(Soldier soldier) {
		
		if(!battleWon_or_transferDone) {
			
			if(apply_damage(soldier.getDamage(), soldier.getType())) {
				battleEnd();
			}
			soldier.remove();
			
		}
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Applique des points de dégats aléatoires aux troupes du château target <br>
	 * Supprime un soldat du type <b>type</b> dans du tableau <b>nb_soldiers_created</b>
	 * @param damage : les dégats à appliquer
	 * @param type : le type du soldat à supprimer une fois les dégâts appliqués
	 * @return True si le nombre de troupes de target est égale 0, false sinon
	 */
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
	
	
	/**
	 * Termine la bataille en effaçant tout ce qui est doit être supprimé <br>
	 * Les soldats encore en vie retournent au château source 
	 */
	private void battleEnd() {
		System.out.println("Fini!");
		target.remove();
		battleWon_or_transferDone = true;
		this.remove();
		ost_return();
	}
	
	
	
}


