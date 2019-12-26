package window;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import settings.Settings;
import shape.Point2D;
import sprite.castle.Castle;
import sprite.castle.Castle.enumCastles;






public class OwnedCastleWindow extends Window {
	
	
	public enum enumButtonOwnedCastleWindow{

		buttonTrain("Train", 0),
		lessPikers("-", 1),
		morePikers("+", 2),
		lessKnights("-", 3),
		moreKnights("+", 4),
		lessCatapults("-", 5),
		moreCatapults("+", 6),
		buttonConfirm("Confirm", 7);

		private String nameButton;
		private int indexButton;

		enumButtonOwnedCastleWindow(String nameButton, int indexButton){
			this.nameButton = nameButton;
			this.indexButton = indexButton;
		}

		public int getIndexButton(){
			return indexButton;
		}
		
		public String getNameButton() {
			
			return nameButton;
		}
	}

	public enum enumHBoxOwnedCastleWindow{

		hboxTrain(0),
		hboxPikers(1),
		hboxKnights(2),
		hboxCatapults(3),
		hboxConfirm(4);

		
		private int indexHBox;

		enumHBoxOwnedCastleWindow(int indexHBox){
			this.indexHBox = indexHBox;
		}

		public int getIndexHBox(){
			return indexHBox;
		}
	}
	
	private int nbGoldTmp;


	public OwnedCastleWindow(Pane layer, Point2D point, double w, double h, Castle source) {
		super(layer, point, w, h, source);
		
		this.nbGoldTmp = source.getGold();

		
		for(enumButtonOwnedCastleWindow button : enumButtonOwnedCastleWindow.values()) {
			
			
			buttonPressedList.add(button.getIndexButton(), new Button(button.getNameButton()));
			
			buttonPressedList.get(button.getIndexButton()).setMinSize(50, 50);
			
		}
		
		for(enumHBoxOwnedCastleWindow hbox : enumHBoxOwnedCastleWindow.values()) {
			
			hboxList.add(hbox.getIndexHBox(), new HBox());
		}
		
		
		buttonPressedList.get(enumButtonOwnedCastleWindow.buttonTrain.getIndexButton()).setMinSize(200, 50);

	
		addHBoxLayer(hboxList.get(enumHBoxOwnedCastleWindow.hboxTrain.getIndexHBox()), getX()+50, getY()+getHeight()/2, 250, 50, 50);
		
		
		
		hboxList.get(enumHBoxOwnedCastleWindow.hboxTrain.getIndexHBox()).getChildren().add(buttonPressedList.get(enumButtonOwnedCastleWindow.buttonTrain.getIndexButton()));
		
		buttonPressedList.get(enumButtonOwnedCastleWindow.buttonTrain.getIndexButton()).setOnAction(event -> buttonTrainPressed(source) );
		
		this.variableDataTexts[enumCastles.nbPikers.getIndexElement()] = new Text(enumCastles.nbPikers.getText() +  getNbSoldiersTmp().get(enumCastles.nbPikers.getIndexElement()));
		this.variableDataTexts[enumCastles.nbKnights.getIndexElement()] = new Text(enumCastles.nbKnights.getText() +  getNbSoldiersTmp().get(enumCastles.nbKnights.getIndexElement()));
		this.variableDataTexts[enumCastles.nbCatapults.getIndexElement()] = new Text(enumCastles.nbCatapults.getText() +  getNbSoldiersTmp().get(enumCastles.nbCatapults.getIndexElement()));
		this.variableDataTexts[enumCastles.gold.getIndexElement()] = new Text(enumCastles.gold.getText() +  getNbGoldTmp() + "/" + source.getGold());

	}





	public void buttonTrainPressed(Castle c) {

		
		removeStatusBar();
		
		getLayer().getChildren().remove(hboxList.get(enumHBoxOwnedCastleWindow.hboxTrain.getIndexHBox()));
		
		
		double y = getY()+getHeight()/6;
		
		for(int indexHBox = enumHBoxOwnedCastleWindow.hboxPikers.getIndexHBox(); indexHBox <= enumHBoxOwnedCastleWindow.hboxConfirm.getIndexHBox(); indexHBox++) {
			
			addHBoxLayer(hboxList.get(indexHBox), getX()+150, y, 250, 50, 50);

			y += getHeight()/8;
				
			}
		
		
		
	    addButtonSign(enumHBoxOwnedCastleWindow.hboxPikers.getIndexHBox(),enumButtonOwnedCastleWindow.lessPikers.getIndexButton(), enumCastles.nbPikers,  c);
	    addButtonSign(enumHBoxOwnedCastleWindow.hboxKnights.getIndexHBox(),enumButtonOwnedCastleWindow.lessKnights.getIndexButton(), enumCastles.nbKnights, c);
	    addButtonSign(enumHBoxOwnedCastleWindow.hboxCatapults.getIndexHBox(),enumButtonOwnedCastleWindow.lessCatapults.getIndexButton(), enumCastles.nbCatapults, c);
		
		
	    addButtonConfirm(enumHBoxOwnedCastleWindow.hboxConfirm.getIndexHBox(),enumButtonOwnedCastleWindow.buttonConfirm.getIndexButton(), 150, 50 , c);
		
		
		hboxList.get(enumHBoxOwnedCastleWindow.hboxConfirm.getIndexHBox()).getChildren().add(variableDataTexts[enumCastles.gold.getIndexElement()]);
			
		
	}
	




	@Override
	public void eventButtonSign(int indexHBox, int indexButton, enumCastles indexElement, boolean sign, Castle c) {


		
		buttonPressedList.get(indexButton).setOnAction(event -> {modifyNbSoldiersTmp(sign, indexElement ,c);
		modifyGoldTmp(sign, indexElement ,c);
		checkNbGoldTmp();
		variableDataTexts[indexElement.getIndexElement()].setText(indexElement.getText() +  getNbSoldiersTmp().get(indexElement.getIndexElement()));	
		variableDataTexts[enumCastles.gold.getIndexElement()].setText(enumCastles.gold.getText() +  getNbGoldTmp() + "/" + c.getGold());
		} );	




	}
	
	public void modifyGoldTmp(Boolean plus, enumCastles indexSoldiers, Castle c) {

		
		int tab[] = {Settings.COST_PIKER, Settings.COST_KNIGHT, Settings.COST_CATAPULT};
		
		int val = tab[indexSoldiers.getIndexElement()];


		if(plus) {

			nbGoldTmp -= val;

		}
		else if (nbGoldTmp + val <= c.getGold()) {

			nbGoldTmp += val;

		}

	}

	void checkNbGoldTmp(){

		if(nbGoldTmp < 0) {

			buttonPressedList.get(enumButtonOwnedCastleWindow.buttonConfirm.getIndexButton()).setStyle("-fx-background-color: #FF0000");

		}

		else {

			buttonPressedList.get(enumButtonOwnedCastleWindow.buttonConfirm.getIndexButton()).setStyle("-fx-background-color: #3CEF18");
		}

	}


	public boolean canConfirm() {
		return nbGoldTmp >= 0;
	}


	@Override
	public void removeHBoxList() {

		for(int indexHBox = enumHBoxOwnedCastleWindow.hboxTrain.getIndexHBox(); indexHBox <= enumHBoxOwnedCastleWindow.hboxConfirm.getIndexHBox(); indexHBox++) {

			getLayer().getChildren().remove(hboxList.get(indexHBox));}


		// TODO Auto-generated method stub

	}
	
	
	public int getNbGoldTmp() {
		return nbGoldTmp;
	}

	




	public void setNbGoldTmp(int nbGoldTmp) {
		this.nbGoldTmp = nbGoldTmp;
	}



	


}
