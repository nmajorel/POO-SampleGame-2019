
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.animation.AnimationTimer;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.event.EventHandler;
import javafx.stage.Stage;

import management.Laboratory;
import management.Land;
import management.Order;
import management.OrderAttack;
import management.OrderSupport;

import static settings.Settings.*;

import shape.Point2D;
import sprite.Sprite;
import static sprite.castle.Castle.canIncome;
import sprite.castle.Castle;
import sprite.castle.Neutral;
import sprite.castle.Taken;
import sprite.castle.Castle.enumCastle;
import player.*;
import settings.Settings;
import window.NotOwnedCastleWindow;
import window.OwnedCastleWindow;
import ennemy.Ennemy;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	


	private Pane playfieldLayer;

	private ArrayList<Castle> otherCastles = new ArrayList<Castle>();
	
	private ArrayList<Castle> allCastles = new ArrayList<Castle>();
	
	private ArrayList<Land> lands = new ArrayList<Land>(); 
	

	private Player player;
	
	private ArrayList<Ennemy> ennemies = new ArrayList<Ennemy>();
	public static boolean paused = false;
	private OwnedCastleWindow ownedCastleWindow;
	private NotOwnedCastleWindow notOwnedCastleWindow;

	private Scene scene;
	private Input input;
	private AnimationTimer gameLoop;
	
    private boolean resetTimerIncome = true;
    
    private long lastUpdateIncome;
    private long elapsedNanosIncome;
    
	private int nb_lands = 0;
    private int nb_ennemies = 1;    
	Text hudTexts[] = new Text[6000];
	
	HBox hboxHUD = new HBox();
	
	HBox hboxSave = new HBox();
	
	Button buttonSave = new Button("save");
    

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
					
					actionEnnemy(currentNanoTime);
					
					
					checkOrders(player.getCastles(), currentNanoTime);
					ennemies.forEach(ennemy -> checkOrders(ennemy.getCastles(), currentNanoTime));
					
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
		
		player.getCastles().get(1).setNbAllTroops(15, 17, 10);
		player.getCastles().get(2).setNbAllTroops(20, 11, 19);

		allCastles.addAll(player.getCastles());

		createOtherCastles();
		
		System.out.println("Allcatsles : " + allCastles.size());
		createHUD();
		createSaveHBbox();
	       
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
							
									notOwnedCastleWindow = new NotOwnedCastleWindow(playfieldLayer, new Point2D((SCENE_WIDTH/2) -WINDOW_WIDTH/2, HUD_HEIGHT+10), WINDOW_WIDTH, WINDOW_HEIGHT, player.getCastles(), castle);
					
							

								}
							
							}
							for (Castle castle : player.getCastles()) {
								if (castle.getImageView().contains(e.getX(), e.getY())) {

									player.getCastles().forEach(c -> c.getLab().resetTimer());
								    resetTimerIncome = true;
									paused = true;
									ownedCastleWindow = new OwnedCastleWindow(playfieldLayer, new Point2D((SCENE_WIDTH/2) -WINDOW_WIDTH/2, HUD_HEIGHT+10), WINDOW_WIDTH, WINDOW_HEIGHT, castle, player.getCastles());

								}
							
							}
						} else {

							if(notOwnedCastleWindow != null) {
								if(notOwnedCastleWindow.isKeepPlaying()) {
									paused = false;

									short exitCode = notOwnedCastleWindow.getExitCode();

									switch (exitCode) {

									case EXIT_ECHAP :
										break;

									case EXIT_ATTACK :

										int nbPikers = notOwnedCastleWindow.getNbPikersTmp();
										int nbKnights = notOwnedCastleWindow.getNbKnightsTmp();
										int nbCatapults = notOwnedCastleWindow.getNbCatapultsTmp();

										Castle castlePlayer = player.getCastles().get(notOwnedCastleWindow.getIndexCastlePlayer());

										castlePlayer.setNbPikers(castlePlayer.getNbPikers() - nbPikers);
										castlePlayer.setNbKnights(castlePlayer.getNbKnights() - nbKnights);
										castlePlayer.setNbCatapults(castlePlayer.getNbCatapults() - nbCatapults);
										castlePlayer.addOrder(new OrderAttack(castlePlayer,notOwnedCastleWindow.getCastleClicked(), nbPikers, nbKnights, nbCatapults));

									}

									notOwnedCastleWindow = null;



								}

							}
							if(ownedCastleWindow != null) {
								if(ownedCastleWindow.isKeepPlaying()) {
									paused = false;
									
									short exitCode = ownedCastleWindow.getExitCode();

									Castle castlePlayer = ownedCastleWindow.getCastleClicked();

									Laboratory lab = castlePlayer.getLab();

									int cost;
									
									int nbPikers;
									int nbKnights;
									int nbCatapults;

									switch (exitCode) {
									
									case EXIT_ECHAP :
										break;


									case EXIT_TRAIN :


										nbPikers = ownedCastleWindow.getNbPikersTmp();
										nbKnights = ownedCastleWindow.getNbKnightsTmp();
										nbCatapults = ownedCastleWindow.getNbCatapultsTmp();


										lab.addProductionQueue(enumCastle.Piker, nbPikers);
										lab.addProductionQueue(enumCastle.Knight, nbKnights);
										lab.addProductionQueue(enumCastle.Catapult, nbCatapults);


										castlePlayer.setGold(ownedCastleWindow.getNbGoldTmp());
										break;


									case EXIT_CANCEL_ONE_QUEUE :



										if(!lab.getProductionQueue().isEmpty()) {
											cost =  lab.getCostProduction();

											lab.removeProductionQueue();

											lab.setElapsedNanos(0);


											castlePlayer.setGold(castlePlayer.getGold()+cost);}

										break;


									case EXIT_CANCEL_ALL_QUEUE :

										if(!lab.getProductionQueue().isEmpty()) {

											int size = lab.getProductionQueue().size();

											for(int i = 0; i < size; i++) {


												cost =  lab.getCostProduction();

												lab.removeProductionQueue();

												lab.setElapsedNanos(0);


												castlePlayer.setGold(castlePlayer.getGold()+cost);

											}
										}

										break;


									case EXIT_UPGRADE_LEVEL :

										lab.addProductionQueue(enumCastle.Level, 1);

										castlePlayer.setGold(ownedCastleWindow.getNbGoldTmp());

										break;


									
									
									
									case EXIT_TRANSFER :
										
										
										nbPikers = ownedCastleWindow.getNbPikersTmp();
										nbKnights = ownedCastleWindow.getNbKnightsTmp();
										nbCatapults = ownedCastleWindow.getNbCatapultsTmp();

				
										
										Castle source = ownedCastleWindow.getCastleClicked();
										Castle castleTransfer = player.getCastles().get(ownedCastleWindow.getIndexCastlePlayer());

										source.setNbPikers(source.getNbPikers() - nbPikers);
										source.setNbKnights(source.getNbKnights() - nbKnights);
										source.setNbCatapults(source.getNbCatapults() - nbCatapults);
										
										source.addOrder(new OrderSupport(source,castleTransfer, nbPikers, nbKnights, nbCatapults));
										
										
										
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
		int nb_neutrals = nb_lands - allCastles.size() - nb_ennemies;
		
		for(int i = 0; i<nb_neutrals; i++) {
			Castle c = new Neutral(playfieldLayer, p, SIZE_CASTLE, SIZE_CASTLE);
			otherCastles.add(c);	 
			allCastles.add(c);
			p = nextAvailableLand();
		}	
		for(int i =0; i<nb_ennemies; i++) {
			Castle c = new Taken(playfieldLayer, p, SIZE_CASTLE, SIZE_CASTLE, Color.CRIMSON, "Cersei Lannister");
			ennemies.add(new Ennemy(c));
			otherCastles.add(c);
			allCastles.add(c);
			p = nextAvailableLand();
		}
	}
	
	private void createLands() {
		
		for(double x = DISTANCE_BETWEEN_CASTLES_WIDTH ; x < SCENE_WIDTH ; x = x + SIZE_LAND + DISTANCE_BETWEEN_CASTLES_WIDTH ) {
			for(double y = DISTANCE_BETWEEN_CASTLES_HEIGHT+HUD_HEIGHT; y < SCENE_HEIGHT; y = y + SIZE_LAND + DISTANCE_BETWEEN_CASTLES_HEIGHT ) {
				lands.add(new Land(x, y, true));
				nb_lands++;
			}
		}
		
	}
	
	private Point2D nextAvailableLand () {
		Iterator<Land> itr = lands.iterator();
		while(itr.hasNext() ) {
			Land element = (Land) itr.next();
	        if(element.isAvailable()) {
	        	element.setAvailable(false);
	        	
	        	double x = element.getPoint().getX();
	        	double y = element.getPoint().getY();
	        	
	        	double new_x = (Math.random() * ( ((x+ SIZE_LAND)-SIZE_CASTLE) - x ))+x;
	        	double new_y = (Math.random() * ( ((y+ SIZE_LAND)-SIZE_CASTLE) - y ))+y;

	        	return new Point2D(new_x,new_y);
	  
	        }
	    }
		return null;
	}
	
	public void removeSprites(ArrayList<? extends Sprite> spriteList) {
		Iterator<? extends Sprite> iter = spriteList.iterator();
		while (iter.hasNext()) {
			Sprite sprite = iter.next();

			if (sprite.isRemovable()) {
				sprite.removeFromLayer();
				iter.remove();
				//spriteList.remove(sprite);
			}
		}
			
	}
	
	public void continueOrders( Castle c, ArrayList<Castle> listCastles) {
		
		for(Order order : c.getOrder() ) {
			if(order.ost_move()) {
				Castle target = order.getTarget();
				double w = Settings.SIZE_CASTLE;
				Point2D point = new Point2D(target.getP());
				String duke = c.getDuke();
				int gold = target.getGold();
				int level = target.getLevel();
				int income = target.getIncome();
				int id = target.getId();
				boolean other_contain = false;
				int other_id = 0;
				boolean contain_all = false;
				
				if(allCastles.contains(target)) {
					contain_all =true;
				}
				if(otherCastles.contains(target)) {
					other_id = otherCastles.indexOf(target);
					removeSprites(otherCastles);
					other_contain = true;
				}
				
				removeSprites(player.getCastles());
				
				ennemies.forEach( ennemy -> removeSprites(ennemy.getCastles()));
				
				removeSprites(allCastles);
				
				Castle castle = new Taken(playfieldLayer, point, w, w, duke, gold, level, income, id, c.getColor());
				
				if(contain_all) {
					listCastles.add(castle );
					allCastles.add(id-1, castle);
					
					if(other_contain && listCastles!=player.getCastles()) {
						otherCastles.add(other_id, castle);
					}
				}
				
			}
		}
		c.removeOrders();
	}

	private void actionEnnemy(long currentNanoTime) {
		ennemies.forEach(ennemy -> ennemy.setCastleToAttack(allCastles));
		ennemies.forEach(ennemy -> ennemy.checkAction(currentNanoTime));
	}
	
	private void checkOrders( ArrayList<Castle> Castles, long currentNanoTime) {
		
		for(int i =0; i< Castles.size(); i++) {
			Castle castle = Castles.get(i);
			if(castle.getOrder()!=null) {
				continueOrders(castle, Castles);
				castle.getOrder().forEach(order -> order.getTroops().forEach(sprite -> sprite.updateUI()) );
				
			}
			Laboratory lab = castle.getLab();
			if(castle.getLab().isRunning()) {
				
				lab.checkProduction(currentNanoTime, castle);
			}	
		}
	}
	
	public void createHUD() {
	
		hboxHUD.relocate(0, 0);
		hboxHUD.setSpacing(10);
		hboxHUD.setMinSize(HUD_WIDTH+10,HUD_HEIGHT/2);
		root.getChildren().add(hboxHUD);
		hboxHUD.setBackground(new Background(new BackgroundFill(Color.DEEPSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		for(int i = 0; i < allCastles.size(); i++) {
			
			
			Text text = new Text("\n\nCastle "+ (i+1) +"\t\t \n"+"No production");
			text.setFont(Font.font("Verdana", FontWeight.LIGHT,11));
			hudTexts[i] = text;
		
			hboxHUD.getChildren().add(hudTexts[i]);


		}


	
	}
	
	
	public void updateHUD() {
		
		
		for(int i = 0; i < allCastles.size(); i++) {

			String string = null;
			
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(1);

			double ElapsedSeconds = allCastles.get(i).getLab().getElapsedSeconds();
			
			Castle castle = allCastles.get(i);
			Laboratory lab = castle.getLab();
			
			enumCastle element = lab.getProduction();

			if (lab.isRunning()) {


				if(element == enumCastle.Piker) {

					string = "Piker : " + nf.format(TIME_PIKER_SECOND-ElapsedSeconds)   +"s";

				}

				if(element == enumCastle.Knight) {

					string = "Knight : " + nf.format(TIME_KNIGHT_SECOND-ElapsedSeconds) +"s";

				}

				if(element == enumCastle.Catapult) {

					string = "Catapult : " + nf.format(TIME_CATAPULT_SECOND-ElapsedSeconds)+"s";

				}
				
				if(element == enumCastle.Level) {

					string = "Upgrade : " + nf.format(TIME_UPGRADE_LEVEL_SECOND-ElapsedSeconds)+"s";

				}

			}

			else {

				string = "No production";
			}
			
			hudTexts[i].setText("\n\nCastle "+ (i+1) +"\t\t \n"+string);
			System.out.println("Allcatsles : " + allCastles.size());

		}
	}
	
	public void createSaveHBbox() {
		
		hboxSave.relocate(0, 100);
		hboxSave.setSpacing(20);
		hboxSave.setMinSize(HUD_WIDTH+10,HUD_HEIGHT/2);
		root.getChildren().add(hboxSave);
		hboxSave.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		buttonSave.setMinSize((SCENE_WIDTH+10)/4, SCENE_HEIGHT/24);

	}
	
	
	public void eventSave() {

		System.out.println("TEST");
		
		ObjectOutputStream oos = null;


		try {
			final FileOutputStream fichier = new FileOutputStream("donnees.ser");
			oos = new ObjectOutputStream(fichier);
			oos.writeObject(new java.util.Date());
			final int[] tableau = { 1, 2, 3 };
			oos.writeObject(tableau);
			oos.writeUTF("ma chaine en UTF8");
			oos.writeLong(123456789);
			oos.writeObject("ma chaine de caracteres");

			oos.flush();
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}

		

	}


	public static void main(String[] args) {
		launch(args);
	}


}