package sprite.castle;

import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import shape.Point2D;

public class Neutral extends Castle {

	private static String[] dukes_names  = { "Tyrion Lannister", "Ramsay Bolton", "Arya Stark", 
											"Theon Grejoy", "Edgar Targaeryan", "Knight King"};
	
	private final static int MAX_PIKERS = 1;
	private final static int MAX_KNIGHT = 1;
	private final static int MAX_CATAPULT = 1;
	
	public Neutral(Pane layer, Point2D point, double w, double h) {
		super(layer, point, Color.DIMGREY, w, h);
		// TODO Auto-generated constructor stub
		int pick = new Random().nextInt(dukes_names.length);
		setDuke(dukes_names[pick]);
		int nb_troops = 0;
		pick = new Random().nextInt(MAX_PIKERS);
		nb_troops = pick;
		setNbPikers(pick);
		pick = new Random().nextInt(MAX_KNIGHT );
		nb_troops = nb_troops + pick;
		setNbKnights(pick);
		pick = new Random().nextInt(MAX_CATAPULT );
		nb_troops = nb_troops + pick;
		setNbPikers(pick);
		setNbTroops(nb_troops);		
		
	}

	public  int getIncome() {
		 return getLevel() ;
	}

}
