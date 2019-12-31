package player;

import java.util.BitSet;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import static javafx.scene.input.KeyCode.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Input {

	/**
	 * Bitset which registers if any {@link KeyCode} keeps being pressed or if it is
	 * released.
	 */
	private BitSet keyboardBitSet = new BitSet();

	private Scene scene = null;

	public Input(Scene scene) {
		this.scene = scene;
	}

	public void addListeners() {
		scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
		scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
	}

	public void removeListeners() {
		scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
		scene.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
	}

	/**
	 * "Key Pressed" handler for all input events: register pressed key in the
	 * bitset
	 */
	private EventHandler<KeyEvent> keyPressedEventHandler = event -> {
		// register key down
		keyboardBitSet.set(event.getCode().ordinal(), true);
		event.consume();
	};

	/**
	 * "Key Released" handler for all input events: unregister released key in the
	 * bitset
	 */
	private EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
			// register key up
			keyboardBitSet.set(event.getCode().ordinal(), false);
			event.consume();
		}
	};

	private boolean is(KeyCode key) {
		return keyboardBitSet.get(key.ordinal());
	}

	// -------------------------------------------------
	// Evaluate bitset of pressed keys and return the player input.
	// If direction and its opposite direction are pressed simultaneously, then the
	// direction isn't handled.
	// -------------------------------------------------

	public boolean isMoveUp() {
		return is(UP) && !is(DOWN);
	}

	public boolean isMoveDown() {
		return is(DOWN) && !is(UP);
	}

	public boolean isMoveLeft() {
		return is(LEFT) && !is(RIGHT);
	}

	public boolean isMoveRight() {
		return is(RIGHT) && !is(LEFT);
	}

	public boolean isFire() {
		return is(SPACE);
	}

	public boolean isExit() {
		return is(ESCAPE);
	}
	
	public boolean isP() {
		return is(P);
	}
	public boolean isC() {
		return is(C);
	}
}