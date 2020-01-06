package player;


import java.util.ArrayList;
import javafx.scene.layout.Pane;
import sprite.castle.Castle;

public class Player {


	private ArrayList<Castle> castles = new ArrayList<Castle>();
	
	public Player(Pane pane, Castle castle) {

		castles.add(castle);

	}

	public ArrayList<Castle> getCastles() {
		return castles;
	}

	public void addCastles(Castle castle) {
		this.castles.add(castle);
	}
	
	

}