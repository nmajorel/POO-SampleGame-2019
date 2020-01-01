package window;




import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import static settings.Settings.*;
import shape.Point2D;
import sprite.castle.Castle;
import sprite.castle.Castle.enumCastles;




public class NotOwnedCastleWindow extends Window {

	
	
	private boolean correctNbSoldiersTmp;
	private int indexCastlePlayer;

	
	public enum enumButtonNotOwnedCastleWindow{

		buttonAttack("Attack!", 0),
		buttonPrevCastle("<=",1),
		buttonChoose("Choose", 2),
		buttonNextCastle("=>",3),
		lessPikers("-", 4),
		morePikers("+", 5),
		lessKnights("-", 6),
		moreKnights("+", 7),
		lessCatapults("-", 8),
		moreCatapults("+", 9),
		buttonConfirm("Confirm", 10);

		private String nameButton;
		private int indexButton;

		enumButtonNotOwnedCastleWindow(String nameButton, int indexButton){
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

	public enum enumHBoxNotOwnedCastleWindow{

		hboxAttack(0),
		hboxChoose(1),
		hboxPikers(2),
		hboxKnights(3),
		hboxCatapults(4),
		hboxConfirm(5);

		
		private int indexHBox;

		enumHBoxNotOwnedCastleWindow(int indexHBox){
			this.indexHBox = indexHBox;
		}

		public int getIndexHBox(){
			return indexHBox;
		}
	}

	

	

	public NotOwnedCastleWindow(Pane layer, Point2D point, double w, double h, List<Castle> playerCastles, Castle target) {
		super(layer, point, w, h, playerCastles, target);
		
		
		this.correctNbSoldiersTmp = true;
		this.indexCastlePlayer = 0;
		
		for(enumButtonNotOwnedCastleWindow button : enumButtonNotOwnedCastleWindow.values()) {

			
			buttonPressedList.add(button.getIndexButton(), new Button(button.getNameButton()));
	
			
		}
		
		for(enumHBoxNotOwnedCastleWindow hbox : enumHBoxNotOwnedCastleWindow.values()) {
			
			hboxList.add(hbox.getIndexHBox(), new HBox());
		}

		
		buttonPressedList.get(enumButtonNotOwnedCastleWindow.buttonAttack.getIndexButton()).setMinSize(200, 50);

	
		addHBoxLayer(hboxList.get(enumHBoxNotOwnedCastleWindow.hboxAttack.getIndexHBox()), getX()+50, getY()+getHeight()/2, 250, 50, 50);
		
		
		
		hboxList.get(enumHBoxNotOwnedCastleWindow.hboxAttack.getIndexHBox()).getChildren().add(buttonPressedList.get(enumButtonNotOwnedCastleWindow.buttonAttack.getIndexButton()));
		
		buttonPressedList.get(enumButtonNotOwnedCastleWindow.buttonAttack.getIndexButton()).setOnAction(event -> buttonAttackPressed() );
		
		
			
		 
	}


	
	public void buttonAttackPressed() {
		

		removeStatusBar();

		int indexHBoxChoose = enumHBoxNotOwnedCastleWindow.hboxChoose.getIndexHBox();
		double width = getWidth();
		double height = getHeight();
		
		getLayer().getChildren().remove(hboxList.get(enumHBoxNotOwnedCastleWindow.hboxAttack.getIndexHBox()));

		for(int indexButton = enumButtonNotOwnedCastleWindow.buttonPrevCastle.getIndexButton(); indexButton <= enumButtonNotOwnedCastleWindow.buttonNextCastle.getIndexButton(); indexButton++) {
			
			addButtonHBox(indexHBoxChoose, indexButton, width/4, height/16);

		}
		
		setStatusBarTexts(getPlayerCastles().get(indexCastlePlayer), "CASTLE PLAYER N ° "+ getPlayerCastles().get(indexCastlePlayer).getId());
		addVBoxLayer(getStatusBar(), getX() + width/2-100, getY() + 50, 400, 400, getHeight()/16);
	
		addHBoxLayer(hboxList.get(indexHBoxChoose), getX()+width/16, (getY()+height)-height/4, width - (width/8), height/16, width/16);
		
		buttonPressedList.get(enumButtonNotOwnedCastleWindow.buttonPrevCastle.getIndexButton()).setOnAction(event -> {

			if(indexCastlePlayer > 0) {
				indexCastlePlayer--;}

			setStatusBarTexts(getPlayerCastles().get(indexCastlePlayer), "CASTLE PLAYER N ° "+ getPlayerCastles().get(indexCastlePlayer).getId());
		} );	

		buttonPressedList.get(enumButtonNotOwnedCastleWindow.buttonChoose.getIndexButton()).setOnAction(event -> buttonChoosePressed(getPlayerCastles().get(indexCastlePlayer)) );

		buttonPressedList.get(enumButtonNotOwnedCastleWindow.buttonNextCastle.getIndexButton()).setOnAction(event -> {
			if(indexCastlePlayer < getPlayerCastles().size()-1) {
				indexCastlePlayer++;}
			
			setStatusBarTexts(getPlayerCastles().get(indexCastlePlayer), "CASTLE PLAYER N ° "+getPlayerCastles().get(indexCastlePlayer).getId());
		} );	

		
	}
	
	
	public void buttonChoosePressed(Castle c) {
		
		removeStatusBar();
		
		createVariableDataTexts(c);
		
		addNbSoldiersList(c);
		
		getLayer().getChildren().remove(hboxList.get(enumHBoxNotOwnedCastleWindow.hboxChoose.getIndexHBox()));
		
		
		double y = getY()+getHeight()/6;
		
		for(int indexHBox = enumHBoxNotOwnedCastleWindow.hboxPikers.getIndexHBox(); indexHBox <= enumHBoxNotOwnedCastleWindow.hboxConfirm.getIndexHBox(); indexHBox++) {
			
			addHBoxLayer(hboxList.get(indexHBox), getX()+150, y, 250, 50, 50);

			y += getHeight()/8;
				
			}
		
	    addButtonSign(enumHBoxNotOwnedCastleWindow.hboxPikers.getIndexHBox(),enumButtonNotOwnedCastleWindow.lessPikers.getIndexButton(), enumCastles.nbPikers,  c);
	    addButtonSign(enumHBoxNotOwnedCastleWindow.hboxKnights.getIndexHBox(),enumButtonNotOwnedCastleWindow.lessKnights.getIndexButton(), enumCastles.nbKnights, c);
	    addButtonSign(enumHBoxNotOwnedCastleWindow.hboxCatapults.getIndexHBox(),enumButtonNotOwnedCastleWindow.lessCatapults.getIndexButton(), enumCastles.nbCatapults, c);
	    
	    addButtonConfirm(enumHBoxNotOwnedCastleWindow.hboxConfirm.getIndexHBox(),enumButtonNotOwnedCastleWindow.buttonConfirm.getIndexButton(), 150, 50);
		
	}
	
	
	public void createVariableDataTexts(Castle c) {
		
		this.variableDataTexts[enumCastles.nbPikers.getIndexElement()] = new Text(enumCastles.nbPikers.getText() +  getNbSoldiersTmp().get(enumCastles.nbPikers.getIndexElement()) + "/" + c.getNbPikers());
		this.variableDataTexts[enumCastles.nbKnights.getIndexElement()] = new Text(enumCastles.nbKnights.getText() +  getNbSoldiersTmp().get(enumCastles.nbKnights.getIndexElement()) + "/" + c.getNbKnights());
		this.variableDataTexts[enumCastles.nbCatapults.getIndexElement()] = new Text(enumCastles.nbCatapults.getText() +  getNbSoldiersTmp().get(enumCastles.nbCatapults.getIndexElement()) + "/" + c.getNbCatapults());
		
	}



	@Override
	public void eventButtonSign(int indexHBox, int indexButton, enumCastles indexElement, boolean sign, Castle c) {


		buttonPressedList.get(indexButton).setOnAction(event -> {modifyNbSoldiersTmp(sign, indexElement ,c);
		checkNbSoldiersTmp();
		variableDataTexts[indexElement.getIndexElement()].setText(indexElement.getText() +  getNbSoldiersTmp().get(indexElement.getIndexElement()) + "/" + getNbSoldiersList().get(indexElement.getIndexElement()));} );	


	}
	
	
	
	
	void checkNbSoldiersTmp(){

		boolean red = false;


		for(int indexElement = enumCastles.nbPikers.getIndexElement(); indexElement <= enumCastles.nbCatapults.getIndexElement(); indexElement++ ) {

			if(getNbSoldiersTmp().get(indexElement) > getNbSoldiersList().get(indexElement)) {
				red = true;
				
				break;
			}

		}

		if(red) {
			correctNbSoldiersTmp = false;
			buttonPressedList.get(enumButtonNotOwnedCastleWindow.buttonConfirm.getIndexButton()).setStyle("-fx-background-color: #FF0000");

		}
		else {
			correctNbSoldiersTmp = true;
			buttonPressedList.get(enumButtonNotOwnedCastleWindow.buttonConfirm.getIndexButton()).setStyle("-fx-background-color: #3CEF18");
		}

	}



	@Override
	public boolean canConfirm() {


		return correctNbSoldiersTmp;
	}



	@Override
	public void removeHBoxList() {

		for(int indexHBox = enumHBoxNotOwnedCastleWindow.hboxAttack.getIndexHBox(); indexHBox <= enumHBoxNotOwnedCastleWindow.hboxConfirm.getIndexHBox(); indexHBox++) {

			getLayer().getChildren().remove(hboxList.get(indexHBox));}

		// TODO Auto-generated method stub

	}

	public int getIndexCastlePlayer() {
		return indexCastlePlayer;
	}



	
	
	
	










}