package management;
import sprite.soldier.Soldier;

public class Laboratory {
	
	private Soldier soldier;
	private boolean isUpgrade = false;
	private int nb_rounds;
	
	
	public Soldier getSoldier() {
		return soldier;
	}
	public void setSoldier(Soldier soldier) {
		this.soldier = soldier;
	}
	public boolean isUpgrade() {
		return isUpgrade;
	}
	public void setUpgrade(boolean isUpgrade) {
		this.isUpgrade = isUpgrade;
	}
	public int getNb_rounds() {
		return nb_rounds;
	}
	public void setNb_rounds(int nb_rounds) {
		this.nb_rounds = nb_rounds;
	}
	

}
