
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.event.EventHandler;
import javafx.stage.Stage;

import management.Laboratory;
import management.Land;
import management.Order;
import static settings.Settings.*;

import shape.Point2D;
import shape.Rectangle;
import sprite.Sprite;
import static sprite.castle.Castle.canIncome;
import sprite.castle.Castle;
import sprite.castle.Neutral;
import sprite.castle.Taken;
import player.*;
import settings.Settings;
import window.NotOwnedCastleWindow;
import window.OwnedCastleWindow;

import javafx.scene.Group;
import javafx.scene.Scene;


import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;


public class Main extends Application {
	
	private Random rnd = new Random();

	private Pane playfieldLayer;

	private ArrayList<Castle> otherCastles = new ArrayList<Castle>();
	
	private ArrayList<Castle> allCastles = new ArrayList<Castle>();
	
	private ArrayList<Land> lands = new ArrayList<Land>(); 
	
	private boolean collision = false;

	
	private Player player;
	
	public static boolean paused = false;
	private OwnedCastleWindow ownedCastleWindow;
	private NotOwnedCastleWindow notOwnedCastleWindow;

	private Scene scene;
	private Input input;
	private AnimationTimer gameLoop;
	
    private boolean resetTimerIncome = true;
    
    private long lastUpdateIncome;
    private long elapsedNanosIncome;
    
    
	Text hudTexts[] = new Text[12];
    

	Group root;

	@Override
	public void start(Stage primaryStage) {

		root = new Group();
		scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.FORESTGREEN );
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		// create layers
		playfieldLayer = new Pane();
		root.getChildren().add(playfieldLayer);
		
		loadGame();
		
	
		gameLoop = new AnimationTimer() {
			

		 
			
			@Override
			public void handle(long currentNanoTime) {
				
				
				try {
					processInput(input, currentNanoTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
		
				
				
				
				
		
				if(!paused){
					
	
					otherCastles.forEach(sprite -> sprite.move());
					otherCastles.forEach(sprite -> sprite.updateUI());
					
					player.getCastles().forEach(sprite -> sprite.move());
					player.getCastles().forEach(sprite -> sprite.updateUI());
					
					
					for(int i =0; i< player.getCastles().size(); i++) {
						Castle castle = player.getCastles().get(i);
						if(castle.getOrder()!=null) {
							continueOrders(castle);
							castle.getOrder().forEach(order -> order.getTroops().forEach(sprite -> sprite.updateUI()) );
							
						}
						
						Laboratory lab = castle.getLab();
						if(castle.getLab().isRunning()) {
							
							lab.checkProduction(currentNanoTime, castle);
							
						}	
						
					}/*
					Iterator <Castle> iter = player.getCastles().iterator();
					while (iter.hasNext()) {
						Castle castle = iter.next();
						
						if(castle.getOrder()!=null) {
							continueOrders(castle);
							castle.getOrder().forEach(order -> order.getTroops().forEach(sprite -> sprite.updateUI()) );
							
						}
						
						Laboratory lab = castle.getLab();
						if(castle.getLab().isRunning()) {
							
							lab.checkProduction(currentNanoTime, castle);
							
						}	
					}
					/*
					for(Castle castle : player.getCastles()) {
						
						if(castle.getOrder()!=null) {
							continueOrders(castle);
							castle.getOrder().forEach(order -> order.getTroops().forEach(sprite -> sprite.updateUI()) );
							
						}
						
						Laboratory lab = castle.getLab();
						if(castle.getLab().isRunning()) {
							
							lab.checkProduction(currentNanoTime, castle);
							
						}	
						
					}*/
					
					checkIncome(currentNanoTime);
					
					updateHUD();


				}
	

			}

			private void processInput(Input input, long now) throws InterruptedException {
				if (input.isExit()) {
					Platform.exit();
					System.exit(0);
				} 
				else if(input.isP()) {

					player.getCastles().forEach(castle -> castle.getLab().resetTimer());
				    resetTimerIncome = true;
					paused = true;
				}
				else if(input.isC()){
					paused = false;
				}
				
			}

		};
		gameLoop.start();
	}	
	
	
	public void checkIncome(long currentNanoTime) {
		
		
		if(resetTimerIncome) {
			lastUpdateIncome = currentNanoTime - elapsedNanosIncome;
			resetTimerIncome = false;
		}	

		elapsedNanosIncome = currentNanoTime - lastUpdateIncome ;	
		
		double elapsedSeconds = elapsedNanosIncome * Math.pow(10, -9) ;
		
		if( canIncome(elapsedSeconds) ) {
			
			System.out.println("MONEY : TIME = " + elapsedSeconds);
			player.getCastles().forEach(castle -> castle.income());
			otherCastles.forEach(castle -> castle.income());
			elapsedNanosIncome = 0;
			resetTimerIncome = true;
			
		}
		
	}

	private void loadGame() {

		input = new Input(scene);
		input.addListeners();
		
		
		createLands();
		
		player = new Player(playfieldLayer, input, new Taken(playfieldLayer, nextAvailableLand(), SIZE_CASTLE, SIZE_CASTLE));
		
		
		player.addCastles(new Taken(playfieldLayer, nextAvailableLand(), SIZE_CASTLE, SIZE_CASTLE));
		player.addCastles(new Taken(playfieldLayer, nextAvailableLand(), SIZE_CASTLE, SIZE_CASTLE));
		
		
		player.getCastles().get(1).setNbPikers(40);
		player.getCastles().get(1).setNbKnights(20);
		player.getCastles().get(1).setNbCatapults(10);
		
		player.getCastles().get(2).setNbPikers(20);
		player.getCastles().get(2).setNbKnights(7);
		player.getCastles().get(2).setNbCatapults(14);
		
		allCastles.addAll(player.getCastles());
	
		

		createOtherCastles();

		createHUD();
	       
        scene.setOnMouseClicked(
                new EventHandler<MouseEvent>()
				{
					public void handle(MouseEvent e) {

						if (!paused) {
							for (Castle castle : otherCastles) {
								if (castle.getImageView().contains(e.getX(), e.getY())) {
									
									player.getCastles().forEach(c -> c.getLab().resetTimer());
								    resetTimerIncome = true;
									paused = true;
							
									notOwnedCastleWindow = new NotOwnedCastleWindow(playfieldLayer, new Point2D((SCENE_WIDTH/2) -WINDOW_WIDTH/2, (SCENE_HEIGHT/2) - WINDOW_HEIGHT/2), WINDOW_WIDTH, WINDOW_HEIGHT, player.getCastles(), castle);
					
							

								}
							
							}
							for (Castle castle : player.getCastles()) {
								if (castle.getImageView().contains(e.getX(), e.getY())) {

									player.getCastles().forEach(c -> c.getLab().resetTimer());
								    resetTimerIncome = true;
									paused = true;
									ownedCastleWindow = new OwnedCastleWindow(playfieldLayer, new Point2D((SCENE_WIDTH/2) -WINDOW_WIDTH/2, (SCENE_HEIGHT/2) - WINDOW_HEIGHT/2), WINDOW_WIDTH, WINDOW_HEIGHT, castle);
									
							

								}
							
							}
						} else {
							
							if(notOwnedCastleWindow != null) {
								if(notOwnedCastleWindow.isKeepPlaying()) {
									paused = false;
									if(notOwnedCastleWindow.isMakeAnOrderWindow()) {


										List<Integer> nbSoldiersTmp = notOwnedCastleWindow.getNbSoldiersTmp();

										Castle castlePlayer = player.getCastles().get(notOwnedCastleWindow.getIndexCastlePlayer());

										castlePlayer.setNbPikers(castlePlayer.getNbPikers()-nbSoldiersTmp.get(0));
										castlePlayer.setNbKnights(castlePlayer.getNbKnights()-nbSoldiersTmp.get(1));
										castlePlayer.setNbCatapults(castlePlayer.getNbCatapults()-nbSoldiersTmp.get(2));
										castlePlayer.addOrder(new Order(castlePlayer,notOwnedCastleWindow.getCastleClicked(), nbSoldiersTmp.get(0),nbSoldiersTmp.get(1),nbSoldiersTmp.get(2)));




									}
									notOwnedCastleWindow = null;


								}

							}
							if(ownedCastleWindow != null) {
								if(ownedCastleWindow.isKeepPlaying()) {
									paused = false;
									if(ownedCastleWindow.isMakeAnOrderWindow()) {
					
										
										Castle castlePlayer = ownedCastleWindow.getCastleClicked();
										
										List<Integer> nbSoldiersTmp = ownedCastleWindow.getNbSoldiersTmp();
										
										for(int i = PIKER; i <= CATAPULT; i++) {
											
											for(int j =  0; j < nbSoldiersTmp.get(i); j++ ){
										
												castlePlayer.getLab().addSoldiersTrainingQueue(i);
											}
											
											
										}
										
										castlePlayer.setGold(ownedCastleWindow.getNbGoldTmp());
										
								
										}
									ownedCastleWindow = null;
										
										
										
									}
									
									
									
								}

							}



						}
					
				});

		
	}
	
	


	private void createOtherCastles() {
		
		Point2D p = nextAvailableLand();
		while(p!=null ) {
			Castle c = new Neutral(playfieldLayer, p, SIZE_CASTLE, SIZE_CASTLE);
			otherCastles.add(c);	 
			allCastles.add(c);
			p = nextAvailableLand();
	    }		
		
	}
	
	private void createLands() {
		
		for(double x = 50; x < SCENE_WIDTH ; x = x + SIZE_LAND + DISTANCE_BETWEEN_CASTLES) {
			for(double y = 250; y < SCENE_HEIGHT; y = y + SIZE_LAND + DISTANCE_BETWEEN_CASTLES) {
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
	
	public void removeSprites(ArrayList<? extends Sprite> spriteList) {
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
	
	public void continueOrders( Castle c) {
		
		for(Order order : c.getOrder() ) {
			if(order.ost_move()) {
				Castle target = order.getTarget();
				double w = Settings.SIZE_CASTLE;
				Point2D point = new Point2D(target.getP());
				String duke = c.getDuke();
				int gold = c.getGold();
				int level = c.getLevel();
				int income = c.getIncome();
				
				removeSprites(otherCastles);

				player.addCastles(new Taken(playfieldLayer, point, w, w, duke, gold, level, income) );
				
			}
		}
		c.removeOrders();
	}

	
	
	public void createHUD() {
		HBox hud = new HBox();
		hud.relocate(0, 0);
		hud.setSpacing(20);
		hud.setMinSize(SCENE_WIDTH+10,SCENE_HEIGHT/6);
		root.getChildren().add(hud);
		hud.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
		
		for(int i = 0; i < allCastles.size(); i++) {
			
			
			Text text = new Text("\n\n\n\n\nCastles n�"+ (i+1) +"\t \n"+"No production");
			text.setFont(Font.font("Verdana", FontWeight.LIGHT,13));
			hudTexts[i] = text;
		
			hud.getChildren().add(hudTexts[i]);

			

		}


	
	}
	
	
	private void updateHUD() {
		
		
		for(int i = 0; i < allCastles.size(); i++) {

			String string = null;
			
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(1);
			
			double ElapsedSeconds = allCastles.get(i).getLab().getElapsedSeconds();

			if (allCastles.get(i).getLab().isRunning()) {

				int soldier =  allCastles.get(i).getLab().getSoldierProduction();


				switch(soldier) {

				case PIKER:

					string = "Piker : " + nf.format(TIME_PIKER_SECOND-ElapsedSeconds)   +"s";

					break;

				case KNIGHT:

					string = "Knight : " + nf.format(TIME_KNIGHT_SECOND-ElapsedSeconds) +"s";

					break;

				case CATAPULT:

					string = "Catapult : " + nf.format(TIME_CATAPULT_SECOND-ElapsedSeconds)+"s";

					break;


				}

			}

			else {

				string = "No production";
			}
			
			hudTexts[i].setText("\n\n\n\n\nCastles n�"+ (i+1) +"\t \n"+string);

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