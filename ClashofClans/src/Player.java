

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

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import sprite.castle.Castle;



public class Player {
	
	private List<Castle> owned = new ArrayList<Castle>();

	private Input input;
	
	public Player(List<Castle> o, Input ip) {
		
		this.owned = o;
		this.input = ip;

	}

	/*
	private void init() {
		// calculate movement bounds of the player ship
		// allow half of the player to be outside of the screen
		minX = 0 - getWidth() / 2.0;
		maxX = Settings.SCENE_WIDTH - getWidth() / 2.0;
		minY = 0 - getHeight() / 2.0;
		maxY = Settings.SCENE_HEIGHT - getHeight();
	}

	public void processInput() {
		// vertical direction
		if (input.isMoveUp()) {
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
		}

	}

	@Override
	public void move() {
		super.move();
		// ensure the player can't move outside of the screen
		checkBounds();
	}

	private void checkBounds() {
		// vertical
		y = y < minY ? minY : y;
		y = y > maxY ? maxY : y;

		// horizontal
		x = x < minX ? minX : x;
		x = x > maxX ? maxX : x;
	}

	@Override
	public void checkRemovability() {
	}

	public boolean canFire(long now) {
		return (now - lastFire >= fireFrequency);
	}

	public void fire(long now) {
		lastFire = now;
	}

	public void setFireFrequencyLow() {
		this.fireFrequency = Settings.FIRE_FREQUENCY_LOW;
	}
	
	public void setFireFrequencyMedium() {
		this.fireFrequency = Settings.FIRE_FREQUENCY_MEDIUM;
	}
	
	public void setFireFrequencyHigh() {
		this.fireFrequency = Settings.FIRE_FREQUENCY_HIGH;
	}
	*/
	
	public List<Castle> getOwned() {
		return owned;
	}

	public void setOwned(List<Castle> owned) {
		this.owned = owned;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}
	
	

}
