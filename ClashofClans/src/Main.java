
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
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

	private Image playerImage;
	private Image enemyImage;
	private Image missileImage;

	private List<Castle> castles = new ArrayList<Castle>();
	private List<Soldier> soldiers = new ArrayList<Soldier>();
	private Castle castle;
	private ArrayList<Land> lands = createLand();
	private Text scoreMessage = new Text();
	private int scoreValue = 0;
	private boolean collision = false;
	
	private boolean paused = false;
	
	private Scene scene;
	private Input input;
	private AnimationTimer gameLoop;
	
	Group root;

	@Override
	public void start(Stage primaryStage) {

		root = new Group();
		scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT + Settings.STATUS_BAR_HEIGHT);
		//scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!paused){				
					castles.forEach(sprite -> sprite.move());
					castles.get(0).doOrder();
					castles.forEach(sprite -> sprite.updateUI());
					soldiers.forEach(sprite -> sprite.updateUI());
				}
					/*
				castles.forEach(sprite -> sprite.remove());
				
				removeSprites(castles);
				double t = (currentNanoTime - startNanoTime);
				if(t>10000000000.0) {
					//startNanoTime = currentNanoTime;
					System.out.println("coucou");
					
				}*/

				// player input
				//player.processInput();
				//castle.processInput();

				// add random enemies
				//spawnEnemies(true);

				// movement
				//player.move();
				//enemies.forEach(sprite -> sprite.move());
				//missiles.forEach(sprite -> sprite.move());

				// check collisions
				//checkCollisions();

				
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
				else if (input.isFire()) {
					//fire(now);
				}
				
			}

		};
		gameLoop.start();
	}

	private void loadGame() {
		//playerImage = new Image(getClass().getResource("/images/alien.png").toExternalForm(), 100, 100, true, true);
		//enemyImage = new Image(getClass().getResource("/images/enemy.png").toExternalForm(), 50, 50, true, true);
		//missileImage = new Image(getClass().getResource("/images/pinapple.png").toExternalForm(), 20, 20, true, true);

		input = new Input(scene);
		input.addListeners();

		//createPlayer();
		//createStatusBar();
		
		createLand();
		createCastle();
		
		Canvas canvas = new Canvas( 500, 500 );
	        //Image restart = new Image("restart.png");
		
	    
		GraphicsContext gc = canvas.getGraphicsContext2D();

	    root.getChildren().add( canvas );
	    
	
	    
	    
	    Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        //gc.setFont( theFont );
        //gc.setStroke( Color.BLACK );
        //gc.setLineWidth(1);
		
        scene.setOnMouseClicked(
                new EventHandler<MouseEvent>()
				{
					public void handle(MouseEvent e) {

						if (!paused) {
							for (Castle castle : castles) {
								if (castle.getImageView().contains(e.getX(), e.getY())) {

									//System.out.println(castle.getGold());

									//gc.setFill(Color.BLUE);

									String pointsText = "Points: ";
									//gc.fillText(pointsText, 360, 36);
									//gc.strokeText(pointsText, 360, 36);
									System.out.println("paused = false");
									paused = true;
									createWindow();

								}
							}
						} else {
							
							
							if(window.getSuppr().getImageView().contains(e.getX(), e.getY())) {
								System.out.println("paused = true");
								removeWindow(window);
								paused = false;
							}
							
							
							

						}
					}
				});
		
		
		/*scene.setOnMousePressed(e -> {
			player.setX(e.getX() - (player.getWidth() / 2));
			player.setY(e.getY() - (player.getHeight() / 2));
		});*/
	}
	
	private void createWindow() {
		
		window = new Window(playfieldLayer, new Point2D(200, 200), 400, 400);

		
	}


	private void createCastle() {
		
		
		Iterator itr = lands.iterator();


		Land element = (Land) itr.next();
		castles.add(new Taken(playfieldLayer, element.getPoint(), Settings.SIZE_CASTLE, Settings.SIZE_CASTLE));
		element.setAvailable(false);
		while(itr.hasNext() ) {
			element = (Land) itr.next();
	        if(element.isAvailable()) {
	        	castles.add(new Neutral(playfieldLayer, element.getPoint(), Settings.SIZE_CASTLE, Settings.SIZE_CASTLE));
	        	element.setAvailable(false);
	        	
	        }
	    }
		castles.get(0).setOrder(new Order(castles.get(0), castles.get(2), 9, 4, 2));
		
		soldiers = castles.get(0).getOrder().getTroops();
		/*castles.add(new Castle(playfieldLayer, new Point2D(150, 50), 100, Settings.SIZE_CASTLE, Settings.SIZE_CASTLE));
		castles.add(new Castle(playfieldLayer, new Point2D(400, 90), 100, Settings.SIZE_CASTLE, Settings.SIZE_CASTLE));
		castles.add(new Castle(playfieldLayer, new Point2D(150, 50), 100, Settings.SIZE_CASTLE, Settings.SIZE_CASTLE));
		castles.add(new Castle(playfieldLayer, new Point2D(400, 90), 100, Settings.SIZE_CASTLE, Settings.SIZE_CASTLE));*/
		
		
	}
	
	public ArrayList<Land> createLand() {
		ArrayList<Land> tmp_land = new ArrayList<Land>(); 
		for(double x = 50; x < Settings.SCENE_WIDTH ; x = x + Settings.SIZE_LAND + Settings.DISTANCE_BETWEEN_CASTLES) {
			for(double y = 50; y < Settings.SCENE_HEIGHT; y = y + Settings.SIZE_LAND + Settings.DISTANCE_BETWEEN_CASTLES) {
				tmp_land.add(new Land(x, y, true));
			}
		}
		return tmp_land;		
	}
	
	public shape.Point2D nextAvailaibleLand(){
		Iterator itr = lands.iterator();
		Land element;
		while(itr.hasNext()){
			element = (Land) itr.next();
	        if(element.isAvailable()) {
	        	element.setAvailable(false);
	        	return element.getPoint();	        	
	        }
		}
		return null;
	}


	/*public void createStatusBar() {
		HBox statusBar = new HBox();
		scoreMessage.setText("Score : 0          Life : " + player.getHealth());
		statusBar.getChildren().addAll(scoreMessage);
		statusBar.getStyleClass().add("statusBar");
		statusBar.relocate(0, Settings.SCENE_HEIGHT);
		statusBar.setPrefSize(Settings.SCENE_WIDTH, Settings.STATUS_BAR_HEIGHT);
		root.getChildren().add(statusBar);
	}

	private void createPlayer() {
		double x = (Settings.SCENE_WIDTH - playerImage.getWidth()) / 2.0;
		double y = Settings.SCENE_HEIGHT * 0.7;
		player = new Player(playfieldLayer, playerImage, x, y, Settings.PLAYER_HEALTH, Settings.PLAYER_DAMAGE,
				Settings.PLAYER_SPEED, input);
		
		player.getView().setOnMousePressed(e -> {
			System.out.println("Click on player");
			e.consume();
		});
		
		player.getView().setOnContextMenuRequested(e -> {
			ContextMenu contextMenu = new ContextMenu();
			MenuItem low = new MenuItem("Slow");
			MenuItem medium= new MenuItem("Regular");
			MenuItem high= new MenuItem("Fast");
			low.setOnAction(evt -> player.setFireFrequencyLow());
			medium.setOnAction(evt -> player.setFireFrequencyMedium());
			high.setOnAction(evt -> player.setFireFrequencyHigh());
			contextMenu.getItems().addAll(low, medium, high);
			contextMenu.show(player.getView(), e.getScreenX(), e.getScreenY());
		});
	}

	private void spawnEnemies(boolean random) {
		if (random && rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
			return;
		}
		double speed = rnd.nextDouble() * 3 + 1.0;
		double x = rnd.nextDouble() * (Settings.SCENE_WIDTH - enemyImage.getWidth());
		double y = -enemyImage.getHeight();
		int health = 1 + rnd.nextInt(5 - 1);
		Enemy enemy = new Enemy(playfieldLayer, enemyImage, x, y, health, 1, speed);
		enemies.add(enemy);
	}

	private void fire(long now) {
		if (player.canFire(now)) {
			Missile missile = new Missile(playfieldLayer, missileImage, player.getCenterX(), player.getY(),
					Settings.MISSILE_DAMAGE, Settings.MISSILE_SPEED);
			missiles.add(missile);
			player.fire(now);
		}
	}
*/ 
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
	
	private void removeWindow(Window window) {
		window.getSuppr().removeFromLayer();
		window.removeFromLayer();
		
		
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

	private void gameOver() {
		HBox hbox = new HBox();
		hbox.setPrefSize(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		hbox.getStyleClass().add("message");
		Text message = new Text();
		message.getStyleClass().add("message");
		message.setText("Game over");
		hbox.getChildren().add(message);
		root.getChildren().add(hbox);
		gameLoop.stop();
	}

	private void update() {
		if (collision) {
			scoreMessage.setText("Score : " + scoreValue + "          Life : " + player.getHealth());
		}
	}*/

	public static void main(String[] args) {
		launch(args);
	}


}