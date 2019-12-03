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
	private static final int duke = 0;
	private static final int level = 1;
	private static final int income = 2;
	private static final int nbPikersText = 3;
	private static final int nbKnightsText = 4;
	private static final int nbCatapultsText = 5;
	private static final int gold = 6;
	private VBox statusBar; 
	
	
	private boolean keepPlaying;

	private List<Integer> nbSoldiersTmp = new ArrayList<Integer>();
	
	
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
		
		texts.add( duke, new Text("Duke : " + c.getDuke() ) ) ;
		texts.add( level, new Text("Level : " + String.valueOf(c.getLevel()) ) ) ;
		texts.add( income, new Text("Income : " + String.valueOf(c.getIncome()) ) ) ;
		texts.add( nbPikersText, new Text("Pikers : " + String.valueOf(c.getNbPikers()) ) ) ;
		texts.add( nbKnightsText, new Text("Knights : " + String.valueOf(c.getNbKnights()) ) ) ;
		texts.add( nbCatapultsText, new Text("Catapults : " + String.valueOf(c.getNbCatapults()) ) ) ;
		texts.add( gold, new Text("Gold : " + String.valueOf(c.getGold()) ) ) ;	
		initText();
		
		nbSoldiersTmp.add(enumSoldiers.Pikers.getIndexSoldiers(), 0);
		nbSoldiersTmp.add(enumSoldiers.Knights.getIndexSoldiers(), 0);
		nbSoldiersTmp.add(enumSoldiers.Catapults.getIndexSoldiers(), 0);

	}



	@Override
	public void checkRemovability() {
		// TODO Auto-generated method stub
		
	}
	
	public void modifyNbSoldiersTmp(Boolean plus, enumSoldiers indexSoldiers, Castle c) {
		
		int val = nbSoldiersTmp.get(indexSoldiers.getIndexSoldiers());
		
		if(plus) {
			nbSoldiersTmp.set(indexSoldiers.getIndexSoldiers(), val+1);
		}
		else {
			nbSoldiersTmp.set(indexSoldiers.getIndexSoldiers(), val-1);
			
		}
		
		
			
		
		
	}
	
	public void initText() {
		double y = getY()+getHeight()/12;
		double x = getX() + getWidth()/1.5;
		statusBar.relocate(x, y);
		statusBar.setSpacing(getHeight()/12);
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
		this.keepPlaying = true;
		
		
	}
	
	public boolean keepPlaying() {
		
		return keepPlaying;
	}
	
	public Button getSuppr() {
		return suppr;
	}



	public List<Text> getTexts() {
		return texts;
	}

	public void setTexts(List<Text> texts) {
		this.texts = texts;
	}
	
	
	public VBox getStatusBar() {
		return statusBar;
	}

	public void setStatusBar(VBox statusBar) {
		this.statusBar = statusBar;
	}



	public List<Integer> getNbSoldiersTmp() {
		return nbSoldiersTmp;
	}
	
	
	
	

}
