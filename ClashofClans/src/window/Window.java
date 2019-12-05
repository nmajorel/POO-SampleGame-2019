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
import shape.Rectangle;
import sprite.Sprite;
import sprite.castle.Castle;
import sprite.castle.Taken;
import sprite.soldier.Soldier;
import window.NotOwnedCastleWindow.enumButton;
import window.NotOwnedCastleWindow.enumHBox;

public abstract class Window extends Sprite{
	
	private Button suppr;
	
	private HBox hboxSuppr;
	
	protected List<HBox> hboxAttackList = new ArrayList<HBox>();
	protected List<Button> buttonAttackPressedList = new ArrayList<Button>();

	private List<Text> texts = new ArrayList<Text>();

	private VBox statusBar; 
	
	
	private boolean keepPlaying;

	private List<Integer> nbSoldiersTmp = new ArrayList<Integer>();
	
	private Castle castleClicked;
	
	private List <Integer>getNbSoldiersList = new ArrayList<Integer>();
	
	
	public enum enumSoldiers{

		Pikers(0),
		Knights(1),
		Catapults(2);
		
		private int indexSoldiers;

		enumSoldiers(int indexSoldiers){
			this.indexSoldiers = indexSoldiers;
		}

		public int getIndexSoldiers(){
			return indexSoldiers;
		}
	}	
	
	public enum enumTexts{

		dukeText(0 , "Duke : "),
		levelText(1, "Level : "),
		incomeText(2, "Income : "),
		nbPikersText(3, "Pikers : "),
		nbKnightsText(4, "Knights : "),
		nbCatapultsText(5, "Catapults : "),
		goldText(6, "Gold : ");
		
		private int indexText;
		private String text;
		
		
		private enumTexts(int indexText, String text) {
			this.indexText = indexText;
			this.text = text;
		}

		public int getIndexText() {
			return indexText;
		}

		public String getText() {
			return text;
		}	
	
	}	

	public Window(Pane layer, Point2D point, double w, double h, Castle c) {
		super(layer, point, Color.DARKGREY, w, h);
		
		this.keepPlaying = false;
		
		this.suppr = new Button("X");
		this.suppr.setMinSize(w/10, w/10);
		
		hboxSuppr = new HBox();
		getLayer().getChildren().add(hboxSuppr);
		hboxSuppr.setPrefSize(w/5, w/5);
		hboxSuppr.relocate(point.getX()+w - w/10, getY());
		hboxSuppr.getChildren().add(suppr);
	
		suppr.setOnAction(event -> removeWindow() );
		
		statusBar = new VBox();
		
		getLayer().getChildren().add(statusBar);
		
		texts.add( enumTexts.dukeText.getIndexText(), new Text(enumTexts.dukeText.getText() + c.getDuke() ) ) ;
		texts.add( enumTexts.levelText.getIndexText(), new Text(enumTexts.levelText.getText() + String.valueOf(c.getLevel()) ) ) ;
		texts.add( enumTexts.incomeText.getIndexText(), new Text(enumTexts.incomeText.getText() + String.valueOf(c.getIncome()) ) ) ;
		texts.add( enumTexts.nbPikersText.getIndexText(), new Text(enumTexts.nbPikersText.getText() + String.valueOf(c.getNbPikers()) ) ) ;
		texts.add( enumTexts.nbKnightsText.getIndexText(), new Text(enumTexts.nbKnightsText.getText() + String.valueOf(c.getNbKnights()) ) ) ;
		texts.add( enumTexts.nbCatapultsText.getIndexText(), new Text(enumTexts.nbCatapultsText.getText() + String.valueOf(c.getNbCatapults()) ) ) ;
		texts.add( enumTexts.goldText.getIndexText(), new Text(enumTexts.goldText.getText()+ String.valueOf(c.getGold()) ) ) ;	
		initText();
		
		nbSoldiersTmp.add(enumSoldiers.Pikers.getIndexSoldiers(), 0);
		nbSoldiersTmp.add(enumSoldiers.Knights.getIndexSoldiers(), 0);
		nbSoldiersTmp.add(enumSoldiers.Catapults.getIndexSoldiers(), 0);
		
		getNbSoldiersList.add(enumSoldiers.Pikers.getIndexSoldiers(), c.getNbPikers());
		getNbSoldiersList.add(enumSoldiers.Knights.getIndexSoldiers(), c.getNbKnights());
		getNbSoldiersList.add(enumSoldiers.Catapults.getIndexSoldiers(), c.getNbCatapults());
		
		this.castleClicked = c;

	}



	@Override
	public void checkRemovability() {
		// TODO Auto-generated method stub
		
	}
	
	public void modifyNbSoldiersTmp(Boolean plus, enumSoldiers indexSoldiers, Castle c) {

		
		int val = nbSoldiersTmp.get(indexSoldiers.getIndexSoldiers());
		
		if(plus) {
			if(val < getNbSoldiersList.get(indexSoldiers.getIndexSoldiers())) {
				nbSoldiersTmp.set(indexSoldiers.getIndexSoldiers(), val+1);
			}
		}
		else {
			if(val > 0) {
				nbSoldiersTmp.set(indexSoldiers.getIndexSoldiers(), val-1);
			}
		}
							
		
	}
	
	public void initText() {
		
		double y = getY()+getHeight()/14;
		double x = getX() + getWidth()/1.5;
		statusBar.relocate(x, y);
		statusBar.setSpacing(getHeight()/14);
		statusBar.setPrefSize(getWidth() , getHeight());
		for (Text text : texts) {
			text.setFont(Font.font("Verdana", FontWeight.LIGHT, 16));
			statusBar.getChildren().add(text);

		}

		
	}
	
	
	public void removeTexts () {
		getLayer().getChildren().remove(statusBar);
	}
	
	public void removeSuppr() {
		
		getLayer().getChildren().remove(hboxSuppr);
	}
	
	
	public void removeWindow() {

		
		for(int indexHBbox = enumHBox.hboxAttack.getIndexHBox(); indexHBbox <= enumHBox.hboxConfirm.getIndexHBox(); indexHBbox++) {
			
			if(hboxAttackList.get(indexHBbox)!= null)
				getLayer().getChildren().remove(hboxAttackList.get(indexHBbox));
		}
		removeSuppr();
		removeTexts();
		this.removeFromLayer();
		setKeepPlaying(true);
		
		
	}



	public Button getSuppr() {
		return suppr;
	}



	public HBox getHboxSuppr() {
		return hboxSuppr;
	}



	public List<HBox> getHboxAttackList() {
		return hboxAttackList;
	}



	public List<Button> getButtonAttackPressedList() {
		return buttonAttackPressedList;
	}



	public List<Text> getTexts() {
		return texts;
	}



	public VBox getStatusBar() {
		return statusBar;
	}



	public boolean isKeepPlaying() {
		return keepPlaying;
	}



	public List<Integer> getNbSoldiersTmp() {
		return nbSoldiersTmp;
	}

	


	public Castle getCastleClicked() {
		return castleClicked;
	}
	

	public List<Integer> getGetNbSoldiersList() {
		return getNbSoldiersList;
	}




	public void setSuppr(Button suppr) {
		this.suppr = suppr;
	}



	public void setHboxSuppr(HBox hboxSuppr) {
		this.hboxSuppr = hboxSuppr;
	}



	public void setHboxAttackList(List<HBox> hboxAttackList) {
		this.hboxAttackList = hboxAttackList;
	}



	public void setButtonAttackPressedList(List<Button> buttonAttackPressedList) {
		this.buttonAttackPressedList = buttonAttackPressedList;
	}



	public void setTexts(List<Text> texts) {
		this.texts = texts;
	}



	public void setStatusBar(VBox statusBar) {
		this.statusBar = statusBar;
	}



	public void setKeepPlaying(boolean keepPlaying) {
		this.keepPlaying = keepPlaying;
	}



	public void setNbSoldiersTmp(List<Integer> nbSoldiersTmp) {
		this.nbSoldiersTmp = nbSoldiersTmp;
	}




	public void setCastleClicked(Castle castleClicked) {
		this.castleClicked = castleClicked;
	}
	


	public void setGetNbSoldiersList(List<Integer> getNbSoldiersList) {
		this.getNbSoldiersList = getNbSoldiersList;
	}

	
	
	
	
	
	
	

}
