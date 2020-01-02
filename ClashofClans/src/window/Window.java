package window;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import shape.Point2D;
import sprite.Sprite;
import sprite.castle.Castle;
import sprite.castle.Castle.enumCastle;

import static settings.Settings.*;

public abstract class Window extends Sprite{
	

	
	private boolean keepPlaying;
	
	private Castle castleClicked;
	
	private Button suppr;
	
	private HBox hboxSuppr;
	
	private VBox statusBar; 
	
	private List<Text> statusBarTexts = new ArrayList<Text>();
	
	protected List<HBox> hboxList = new ArrayList<HBox>();
	
	protected List<Button> buttonPressedList = new ArrayList<Button>();


	private List<Castle> playerCastles = new ArrayList<Castle>();
	
	
	
	private short exitCode;
	
	
	

	protected Hashtable <enumCastle,Text> htVariableData = new Hashtable<enumCastle,Text>();
	
	protected Hashtable <enumCastle,Integer> htNbSoldiersTmp = new Hashtable<enumCastle,Integer>();
	
	protected Hashtable <enumCastle,Integer> htNbSoldiers = new Hashtable<enumCastle,Integer>();
	
	
	
	
	
	
	public Window(Pane layer, Point2D point, double w, double h) {
		
		super(layer, point, Color.DARKGREY, w, h);
		
		this.keepPlaying = false;
		
		this.suppr = new Button("X");
		this.suppr.setMinSize(w/10, w/10);
		
		hboxSuppr = new HBox();
		
		addHBoxLayer(hboxSuppr, point.getX()+w - w/10, getY(), (int)w/10, (int)w/10, 50);
		
		hboxSuppr.getChildren().add(suppr);
		suppr.setOnAction(event -> {removeWindow();
		this.exitCode = EXIT_ECHAP;} );
		
		
		htNbSoldiersTmp.put(enumCastle.Piker, 0);
		htNbSoldiersTmp.put(enumCastle.Knight, 0);
		htNbSoldiersTmp.put(enumCastle.Catapult, 0);
		
			
	}
	
	public Window(Pane layer, Point2D point, double w, double h, Castle source) {

		this(layer, point, w , h);
		
		addHtNbSoldiers(source);

		this.castleClicked = source;		
		
		addStatusBarTexts(source, "CASTLE PLAYER");

		initStatusBar(point.getX() + w/1.5, point.getY()+h/8);

	}
	


	public Window(Pane layer, Point2D point, double w, double h, List<Castle> playerCastles, Castle target) {

		this(layer, point, w , h);
		

		this.castleClicked = target;		
		
		addStatusBarTexts(target, "TARGET");

		initStatusBar(point.getX() + w/1.5, point.getY()+h/8);
		
		this.playerCastles = playerCastles;

	}
	
	protected void addHtNbSoldiers(Castle castle){
		
		
		htNbSoldiers.put(enumCastle.Piker, castle.getNbPikers());
		htNbSoldiers.put(enumCastle.Knight, castle.getNbKnights());
		htNbSoldiers.put(enumCastle.Catapult, castle.getNbCatapults());
		

		
		
	}
	
	
	protected void addStatusBarTexts(Castle castle, String description) {
		
		statusBarTexts.add( new Text(description)) ;
		
		statusBarTexts.add( new Text(castle.toString()) )  ;


		
	}
	
	protected void setStatusBarTexts(Castle castle, String description) {
		
		statusBarTexts.get(0).setText(description);
		
		statusBarTexts.get(1).setText(castle.toString());

	}


	private void initStatusBar(double x, double y) {

		statusBar = new VBox();
		
		addVBoxLayer(statusBar, x, y, 400, 400, getHeight()/16);
		
		for (Text text : statusBarTexts) {
			text.setFont(Font.font("Verdana", FontWeight.LIGHT, 16));
			statusBar.getChildren().add(text);

		}

		
	}
	
	
	protected void removeStatusBar () {
		getLayer().getChildren().remove(statusBar);
	}
	
	protected void removeSuppr() {
		
		getLayer().getChildren().remove(hboxSuppr);
	}
	
	public abstract void removeHBoxList();
	
	
	protected void removeWindow() {
	
		removeHBoxList();
		removeSuppr();
		removeStatusBar();
		this.keepPlaying = true;
		this.removeFromLayer();
	

		
	}

	
	protected void addButtonSign(int indexHBox, int indexButton, enumCastle indexElement, Castle c) {
		
		addButtonHBox(indexHBox, indexButton, getWidth()/16, getHeight()/16);
		addButtonHBox(indexHBox, indexButton+1, getWidth()/16, getHeight()/16);

		eventButtonSign(indexHBox, indexButton, indexElement, false, c );
		eventButtonSign(indexHBox, indexButton + 1, indexElement, true, c );
	
		hboxList.get(indexHBox).getChildren().add(htVariableData.get(indexElement));	

	}
	
	protected abstract void eventButtonSign(int indexHbox, int indexButton, enumCastle indexElement, boolean sign, Castle c);
	
	
	
	protected void addHBoxLayer(HBox hbox, double x, double y, double width, double height, double spacing) {
		
		
		hbox.relocate(x, y);
		hbox.setMinSize(width, height);
		hbox.setSpacing(spacing);

		getLayer().getChildren().add(hbox);
		
		
	}
	
	protected void addVBoxLayer(VBox vbox, double x, double y, double width, double height, double spacing) {
		
		
		vbox.relocate(x, y);
		vbox.setMinSize(width, height);
		vbox.setSpacing(spacing);

		getLayer().getChildren().add(vbox);
		
		
	}
	
	
	protected void addButtonHBox(int indexHBox, int indexButton, double width, double height) {
		
			buttonPressedList.get(indexButton).setMinSize(width, height);
			hboxList.get(indexHBox).getChildren().add(buttonPressedList.get(indexButton));
		

	}
	
	
	protected void addButtonConfirm(int indexHBbox, int indexButton, int width, int height, short exitCode) {
		
		
		addButtonHBox(indexHBbox, indexButton, width, height);

		buttonPressedList.get(indexButton).setStyle("-fx-background-color: #3CEF18");
		
		buttonPressedList.get(indexButton).setOnAction(event -> buttonConfirmPressed(exitCode) );
		
		
	}

	
	protected abstract boolean canConfirm();
	
	
	protected void buttonConfirmPressed(short exitCode) {


		if(canConfirm()) {
			
			this.exitCode = exitCode;


			removeWindow();}

	}

	
	protected void modifyNbSoldiersTmp(Boolean plus, enumCastle indexSoldier, Castle c) {


		int val = htNbSoldiersTmp.get(indexSoldier);

		if(plus) {

			htNbSoldiersTmp.put(indexSoldier, htNbSoldiersTmp.get(indexSoldier)+1);

		}
		else {
			if(val > 0) {
				htNbSoldiersTmp.put(indexSoldier, htNbSoldiersTmp.get(indexSoldier)-1);
			}
		}

	}
	
	
	public abstract void mainWindow();
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void checkRemovability() {
		// TODO Auto-generated method stub
		
	}
	
	
	public int getNbPikersTmp() {
		
		return htNbSoldiersTmp.get(enumCastle.Piker);
		
	}
	
	public int getNbKnightsTmp() {
		
		return htNbSoldiersTmp.get(enumCastle.Knight);
		
	}
	
	public int getNbCatapultsTmp() {
		
		return htNbSoldiersTmp.get(enumCastle.Catapult);
		
	}
	
	public boolean isKeepPlaying() {
		return keepPlaying;
	}

	public Castle getCastleClicked() {
		return castleClicked;
	}



	public Button getSuppr() {
		return suppr;
	}

	public HBox getHboxSuppr() {
		return hboxSuppr;
	}

	public VBox getStatusBar() {
		return statusBar;
	}

	public List<Text> getStatusBarTexts() {
		return statusBarTexts;
	}

	public List<HBox> getHboxList() {
		return hboxList;
	}

	public List<Button> getButtonPressedList() {
		return buttonPressedList;
	}

	public List<Castle> getPlayerCastles() {
		return playerCastles;
	}


	public short getExitCode() {
		return exitCode;
	}

	public void setExitCode(short exitCode) {
		this.exitCode = exitCode;
	}


	
	
	public Hashtable<enumCastle, Integer> getHtNbSoldiersTmp() {
		return htNbSoldiersTmp;
	}

	public Hashtable<enumCastle, Integer> getHtNbSoldiers() {
		return htNbSoldiers;
	}


	


	

	
	
	
	





}