

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import management.Land;
import settings.Settings;
import sprite.castle.Castle;
import sprite.castle.Taken;
import sprite.soldier.Soldier;

public class Player {

	
	private Input input;
	private List<Castle> castles = new ArrayList<Castle>();
	
	public Player(Pane pane, Input input, Castle castle) {

		this.input = input;
		castles.add(castle);

	}

	public void processInput() {
		// vertical direction
		/*if (input.isMoveUp()) {
			dy = -speed;
		} else if (input.isMoveDown()) {
			dy = speed;
		} else {
			dy = 0d;
		}

		// horizontal direction
		if (input.isMoveLeft()) {
			dx = -speed;
		} else if (input.isMoveRight()) {
			dx = speed;
		} else {
			dx = 0d;
		}*/

	}
	

}
