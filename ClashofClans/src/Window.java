
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import shape.Point2D;
import shape.Rectangle;
import sprite.Sprite;
import sprite.castle.Castle;
import sprite.castle.Taken;
import sprite.soldier.Soldier;

public class Window extends Sprite{
	
	private Rectangle suppr;
	
	private List<Text> texts = new ArrayList<Text>();
	private static final int duke = 0;
	private static final int level = 1;
	private static final int income = 2;
	private static final int nb_troops = 3;
	private static final int gold = 4;
	HBox statusBar; 
	
	// Liste de texte et liste de nombres
	
	


	public Window(Pane layer, Point2D point, double w, double h, Castle c) {
		super(layer, point, Color.DARKGREY, w, h);
		this.suppr = new Rectangle(layer, new Point2D((point.getX()+w) - (w/10),point.getY()), Color.RED, w/10, w/10);
		
		statusBar = new HBox();
		getLayer().getChildren().add(statusBar);
		
		texts.add( duke, new Text("Duke : " + c.getDuke() + "\n\n") ) ;
		texts.add( level, new Text("Level : " + String.valueOf(c.getLevel()) ) ) ;
		texts.add( income, new Text("Income : " + String.valueOf(c.getIncome()) ) ) ;
		texts.add( nb_troops, new Text("Number of Troops : " + String.valueOf(c.getNb_troops()) ) ) ;
		texts.add( gold, new Text("Gold : " + String.valueOf(c.getGold()) ) ) ;	
		initText();
	}



	@Override
	public void checkRemovability() {
		// TODO Auto-generated method stub
		
	}
	
	public void initText() {
		double y = getY();
		for (Text text : texts) {
			createText(text, getX(), y);
			y  = y + 20;
		}
		
		
	}
	
	public void createText(Text text, double x, double y) {

		statusBar.getChildren().addAll(text);
		//statusBar.getStyleClass().add("statusBar");
		statusBar.relocate(x, y);
		statusBar.setPrefSize(getWidth() , getHeight());
	}
	
	public void removeTexts () {
		getLayer().getChildren().remove(statusBar);
	}
	
	
	public Rectangle getSuppr() {
		return suppr;
	}
	
	public List<Text> getTexts() {
		return texts;
	}

	public void setTexts(List<Text> texts) {
		this.texts = texts;
	}
	
	
	public HBox getStatusBar() {
		return statusBar;
	}

	public void setStatusBar(HBox statusBar) {
		this.statusBar = statusBar;
	}
	
	

}
