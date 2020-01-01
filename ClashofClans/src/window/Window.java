package window;

import java.util.ArrayList;
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
import sprite.castle.Castle.enumCastles;



public abstract class Window extends Sprite{
	

	
	private boolean keepPlaying;
	
	private Castle castleClicked;

	private boolean makeAnOrderWindow;
	
	private Button suppr;
	
	private HBox hboxSuppr;
	
	private VBox statusBar; 
	
	private List<Text> statusBarTexts = new ArrayList<Text>();
	
	protected List<HBox> hboxList = new ArrayList<HBox>();
	
	protected List<Button> buttonPressedList = new ArrayList<Button>();

	private List<Integer> nbSoldiersTmp = new ArrayList<Integer>();
	
	private List <Integer>nbSoldiersList = new ArrayList<Integer>();
	
	private List<Castle> playerCastles = new ArrayList<Castle>();
	
	protected Text variableDataTexts[] = new Text[4];
	
	
	private short exitCode;


	
	
	
	public Window(Pane layer, Point2D point, double w, double h) {
		
		super(layer, point, Color.DARKGREY, w, h);
		
		this.keepPlaying = false;
		
		this.suppr = new Button("X");
		this.suppr.setMinSize(w/10, w/10);
		
		hboxSuppr = new HBox();
		
		addHBoxLayer(hboxSuppr, point.getX()+w - w/10, getY(), (int)w/10, (int)w/10, 50);
		
		hboxSuppr.getChildren().add(suppr);
		suppr.setOnAction(event -> removeWindow() );
		
		nbSoldiersTmp.add(enumCastles.nbPikers.getIndexElement(), 0);
		nbSoldiersTmp.add(enumCastles.nbKnights.getIndexElement(), 0);
		nbSoldiersTmp.add(enumCastles.nbCatapults.getIndexElement(), 0);

		this.makeAnOrderWindow = false;

			
	}
	
	public Window(Pane layer, Point2D point, double w, double h, Castle source) {

		this(layer, point, w , h);
		
		addNbSoldiersList(source);

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
	
	protected void addNbSoldiersList(Castle castle){
		
		nbSoldiersList.add(enumCastles.nbPikers.getIndexElement(), castle.getNbPikers());
		nbSoldiersList.add(enumCastles.nbKnights.getIndexElement(), castle.getNbKnights());
		nbSoldiersList.add(enumCastles.nbCatapults.getIndexElement(), castle.getNbCatapults());
		
		
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
		this.removeFromLayer();
		this.keepPlaying = true;

		
	}

	
	protected void addButtonSign(int indexHBox, int indexButton, enumCastles indexElement, Castle c) {
		
		addButtonHBox(indexHBox, indexButton, getWidth()/16, getHeight()/16);
		addButtonHBox(indexHBox, indexButton+1, getWidth()/16, getHeight()/16);

		eventButtonSign(indexHBox, indexButton, indexElement, false, c );
		eventButtonSign(indexHBox, indexButton + 1, indexElement, true, c );
	
		hboxList.get(indexHBox).getChildren().add(variableDataTexts[indexElement.getIndexElement()]);	

	}
	
	protected abstract void eventButtonSign(int indexHbox, int indexButton, enumCastles indexElement, boolean sign, Castle c);
	
	
	
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
	
	
	protected void addButtonConfirm(int indexHBbox, int indexButton, int width, int height) {
		
		
		addButtonHBox(indexHBbox, indexButton, width, height);

		buttonPressedList.get(indexButton).setStyle("-fx-background-color: #3CEF18");
		
		buttonPressedList.get(indexButton).setOnAction(event -> buttonConfirmPressed() );
		
		
	}

	
	protected abstract boolean canConfirm();
	
	
	protected void buttonConfirmPressed() {


		if(canConfirm()) {
			
			this.makeAnOrderWindow = true;

			removeWindow();}

	}


	
	protected void modifyNbSoldiersTmp(Boolean plus, enumCastles indexSoldiers, Castle c) {


		int val = nbSoldiersTmp.get(indexSoldiers.getIndexElement());

		if(plus) {

			nbSoldiersTmp.set(indexSoldiers.getIndexElement(), val+1);

		}
		else {
			if(val > 0) {
				nbSoldiersTmp.set(indexSoldiers.getIndexElement(), val-1);
			}
		}

	}
	
	
	public int getNbPikersTmp() {
		
		return nbSoldiersTmp.get(enumCastles.nbPikers.getIndexElement());
		
	}
	
	
	public int getNbKnightsTmp() {
		
		return nbSoldiersTmp.get(enumCastles.nbKnights.getIndexElement());
		
	}

	public int getNbCatapultsTmp() {
	
		return nbSoldiersTmp.get(enumCastles.nbCatapults.getIndexElement());
	
	}
	
	@Override
	public void checkRemovability() {
		// TODO Auto-generated method stub
		
	}

	public boolean isKeepPlaying() {
		return keepPlaying;
	}

	public Castle getCastleClicked() {
		return castleClicked;
	}

	public boolean isMakeAnOrderWindow() {
		return makeAnOrderWindow;
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

	public List<Integer> getNbSoldiersTmp() {
		return nbSoldiersTmp;
	}

	public List<Integer> getNbSoldiersList() {
		return nbSoldiersList;
	}

	public List<Castle> getPlayerCastles() {
		return playerCastles;
	}

	public Text[] getVariableDataTexts() {
		return variableDataTexts;
	}

	public short getExitCode() {
		return exitCode;
	}

	
	
	
	
	public void setExitCode(short exitCode) {
		this.exitCode = exitCode;
	}

	public void setMakeAnOrderWindow(boolean makeAnOrderWindow) {
		this.makeAnOrderWindow = makeAnOrderWindow;
	}

	
	


	

	
	
	
	





}