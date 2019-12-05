
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import management.Land;
import management.Order;
import settings.Settings;
import shape.Point2D;
import sprite.Sprite;
import sprite.castle.Castle;
import sprite.castle.Neutral;
import sprite.castle.Taken;
import sprite.soldier.Soldier;
import window.NotOwnedCastleWindow;
import window.OwnedCastleWindow;
import window.Window;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Main extends Application {
	
	private Random rnd = new Random();

	private Pane playfieldLayer;

	private List<Castle> other_castles = new ArrayList<Castle>();
	private ArrayList<Land> lands = new ArrayList<Land>(); 
	
	private boolean collision = false;

	//private Text scoreMessage = new Text();
	//private int scoreValue = 0;
	
	private Player player;
	
	public static boolean paused = false;
	private OwnedCastleWindow ownedCastleWindow;
	private NotOwnedCastleWindow notOwnedCastleWindow;

	private Scene scene;
	private Input input;
	private AnimationTimer gameLoop;
	
	Group root;

	@Override
	public void start(Stage primaryStage) {

		root = new Group();
		scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT, Color.FORESTGREEN );
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		// create layers
		playfieldLayer = new Pane();
		root.getChildren().add(playfieldLayer);
		
		loadGame();
		
		final long startNanoTime = System.nanoTime();
		
		gameLoop = new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
				try {
					processInput(input, currentNanoTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(!paused){				
					other_castles.forEach(sprite -> sprite.move());
					other_castles.forEach(sprite -> sprite.updateUI());
					player.getCastles().forEach(sprite -> sprite.move());
					
					if(player.getCastles().get(0).getOrder()!=null) {
						player.getCastles().get(0).continueOrders();
						player.getCastles().get(0).getOrder().forEach(order -> order.getTroops().forEach(sprite -> sprite.updateUI()) );
					}
					
					//player.getCastles().forEach(sprite -> sprite.updateUI());
					
					
				}
				

				//player.processInput();
				//castle.processInput();

				// movement
				//player.move();
				//enemies.forEach(sprite -> sprite.move());
				//missiles.forEach(sprite -> sprite.move());
				
				// update sprites in scene
				//player.updateUI();
				//enemies.forEach(sprite -> sprite.updateUI());
				//missiles.forEach(sprite -> sprite.updateUI());

				// check if sprite can be removed
				//enemies.forEach(sprite -> sprite.checkRemovability());
				//missiles.forEach(sprite -> sprite.checkRemovability());

				// remove removables from list, layer, etc
				//removeSprites(enemies);
				//removeSprites(missiles);

				// update score, health, etc
				//update();
			}

			private void processInput(Input input, long now) throws InterruptedException {
				if (input.isExit()) {
					Platform.exit();
					System.exit(0);
				} 
				else if(input.isP()) {
					paused = true;
				}
				else if(input.isC()){
					paused = false;
				}
				
			}

		};
		gameLoop.start();
	}

	private void loadGame() {

		input = new Input(scene);
		input.addListeners();
		
		createLands();
		
		player = new Player(playfieldLayer, input, new Taken(playfieldLayer, nextAvailableLand(), Settings.SIZE_CASTLE, Settings.SIZE_CASTLE));

		createOtherCastles();
		
		Canvas canvas = new Canvas( 500, 500 );	
	    
		GraphicsContext gc = canvas.getGraphicsContext2D();

	    root.getChildren().add( canvas );
	       
	    
	    Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );

        scene.setOnMouseClicked(
                new EventHandler<MouseEvent>()
				{
					public void handle(MouseEvent e) {

						if (!paused) {
							for (Castle castle : other_castles) {
								if (castle.getImageView().contains(e.getX(), e.getY())) {


									paused = true;
									notOwnedCastleWindow = new NotOwnedCastleWindow(playfieldLayer, new Point2D((Settings.SCENE_WIDTH/2) -300, (Settings.SCENE_HEIGHT/2) -300), 600, 600, castle);
					
							

								}
							
							}
							for (Castle castle : player.getCastles()) {
								if (castle.getImageView().contains(e.getX(), e.getY())) {


							
									paused = true;
									ownedCastleWindow = new OwnedCastleWindow(playfieldLayer, new Point2D((Settings.SCENE_WIDTH/2) -300, (Settings.SCENE_HEIGHT/2) -300), 600, 600, castle);
									
							

								}
							
							}
						} else {
							
							if(notOwnedCastleWindow.isKeepPlaying()) {
								paused = false;
								if(notOwnedCastleWindow.isMakeAnOrderWindow()) {
									
									List<Integer> nbSoldiersTmp = notOwnedCastleWindow.getNbSoldiersTmp();
									
									player.getCastles().get(0).addOrder(new Order(player.getCastles().get(0),notOwnedCastleWindow.getCastleClicked(), nbSoldiersTmp.get(0),nbSoldiersTmp.get(1),nbSoldiersTmp.get(2)));
									
								
								}
								
								
							}
			
							
							
							

						}
					}
				});
		
		
	}
	



	private void createOtherCastles() {
		
		Point2D p = nextAvailableLand();
		while(p!=null ) {
			other_castles.add(new Neutral(playfieldLayer, p, Settings.SIZE_CASTLE, Settings.SIZE_CASTLE));	 
			p = nextAvailableLand();
	    }		
		
	}
	
	private void createLands() {
		
		for(double x = 50; x < Settings.SCENE_WIDTH ; x = x + Settings.SIZE_LAND + Settings.DISTANCE_BETWEEN_CASTLES) {
			for(double y = 50; y < Settings.SCENE_HEIGHT; y = y + Settings.SIZE_LAND + Settings.DISTANCE_BETWEEN_CASTLES) {
				lands.add(new Land(x, y, true));
			}
		}
		
	}
	
	private Point2D nextAvailableLand () {
		Iterator itr = lands.iterator();
		while(itr.hasNext() ) {
			Land element = (Land) itr.next();
	        if(element.isAvailable()) {
	        	element.setAvailable(false);
	        	return element.getPoint();
	        }
	    }
		return null;
	}
	
	private void removeSprites(List<? extends Sprite> spriteList) {
		Iterator<? extends Sprite> iter = spriteList.iterator();
		while (iter.hasNext()) {
			Sprite sprite = iter.next();

			if (sprite.isRemovable()) {
				// remove from layer
				sprite.removeFromLayer();
				// remove from list
				iter.remove();
			}
		}
			
	}
	

/*
	private void checkCollisions() {
		collision = false;

		for (Enemy enemy : enemies) {
			for (Missile missile : missiles) {
				if (missile.collidesWith(enemy)) {
					enemy.damagedBy(missile);
					missile.remove();
					collision = true;
					scoreValue += 10 + (Settings.SCENE_HEIGHT - player.getY()) / 10;
				}
			}

			if (player.collidesWith(enemy)) {
				collision = true;
				enemy.remove();
				player.damagedBy(enemy);
				if (player.getHealth() < 1)
					gameOver();
			}
		}

	}

	}*/

	public static void main(String[] args) {
		launch(args);
	}

}