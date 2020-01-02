package window;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import settings.Settings;
import shape.Point2D;
import sprite.castle.Castle;
import sprite.castle.Castle.enumCastle;
import window.NotOwnedCastleWindow.enumButtonNotOwnedCastleWindow;
import window.NotOwnedCastleWindow.enumHBoxNotOwnedCastleWindow;

import static settings.Settings.*;

import java.util.Hashtable;






public class OwnedCastleWindow extends Window implements managementButton{
	
	
	public enum enumButtonOwnedCastleWindow{

		buttonTrain("Train", 0),
		buttonCancelOneQueue("CancelOneQueue", 1),
		buttonCancelAllQueue("CancelAllQueue", 2),
		buttonUpgrade("Upgrade", 3),
		buttonTransfer("Transfer", 4),
		lessPikers("-", 5),
		morePikers("+", 6),
		lessKnights("-", 7),
		moreKnights("+", 8),
		lessCatapults("-", 9),
		moreCatapults("+", 10),
		buttonConfirm("Confirm", 11);

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

		hboxManagementQueue(0),
		hboxOtherFeatures(1),
		hboxPikers(2),
		hboxKnights(3),
		hboxCatapults(4),
		hboxConfirm(5);

		
		private int indexHBox;

		enumHBoxOwnedCastleWindow(int indexHBox){
			this.indexHBox = indexHBox;
		}

		public int getIndexHBox(){
			return indexHBox;
		}
	}
	
	private int nbGoldTmp;
	
	
	private Hashtable <enumCastle,Integer> htCostProduction = new Hashtable<enumCastle,Integer>();


	public OwnedCastleWindow(Pane layer, Point2D point, double w, double h, Castle source) {
		super(layer, point, w, h, source);
		
		this.nbGoldTmp = source.getGold();
		
		htCostProduction.put(enumCastle.Piker, COST_PIKER);
		htCostProduction.put(enumCastle.Knight, COST_KNIGHT);
		htCostProduction.put(enumCastle.Catapult, COST_CATAPULT);
		htCostProduction.put(enumCastle.Level, COST_UPGRADE_LEVEL);
		
		
	    htVariableData.put(enumCastle.Piker, new Text(enumCastle.Piker.getText() +  getHtNbSoldiersTmp().get(enumCastle.Piker) + "\t\tCost : " + htCostProduction.get(enumCastle.Piker) + " coins"));
	    htVariableData.put(enumCastle.Knight, new Text(enumCastle.Knight.getText() +  getHtNbSoldiersTmp().get(enumCastle.Knight) + "\t\tCost : " + htCostProduction.get(enumCastle.Knight) + " coins"));
	    htVariableData.put(enumCastle.Catapult, new Text(enumCastle.Catapult.getText() +  getHtNbSoldiersTmp().get(enumCastle.Catapult) + "\t\tCost : " + htCostProduction.get(enumCastle.Catapult) + " coins"));
	    htVariableData.put(enumCastle.Gold, new Text(enumCastle.Gold.getText() +  getNbGoldTmp() + "/" + source.getGold()));
		
		

		for(enumButtonOwnedCastleWindow button : enumButtonOwnedCastleWindow.values()) {
			
			buttonPressedList.add(button.getIndexButton(), new Button(button.getNameButton()));

		}
		
		for(enumHBoxOwnedCastleWindow hbox : enumHBoxOwnedCastleWindow.values()) {
			
			hboxList.add(hbox.getIndexHBox(), new HBox());
		}
		
		
		mainWindow();


	}
	
	
	public void mainWindow() {
		
		double width = getWidth();
		double height = getHeight();
		
		int indexHBoxManagementQueue = enumHBoxOwnedCastleWindow.hboxManagementQueue.getIndexHBox();
		int indexHBoxOtherFeatures = enumHBoxOwnedCastleWindow.hboxOtherFeatures.getIndexHBox();

		addHBoxLayer(hboxList.get(indexHBoxManagementQueue), getX()+width/16, (getY()+height)-height/8, width - (width/8), height/16, width/16);
		addHBoxLayer(hboxList.get(indexHBoxOtherFeatures), getX()+width/4, (getY()+height)-height/4, width - (width/8), height/16, width/16);

		
		for(int indexButton = enumButtonOwnedCastleWindow.buttonTrain.getIndexButton(); indexButton <= enumButtonOwnedCastleWindow.buttonCancelAllQueue.getIndexButton(); indexButton++) {
			
			addButtonHBox(indexHBoxManagementQueue, indexButton, width/4, height/16);

		}
		
		for(int indexButton = enumButtonOwnedCastleWindow.buttonUpgrade.getIndexButton(); indexButton <= enumButtonOwnedCastleWindow.buttonTransfer.getIndexButton(); indexButton++) {
			
			addButtonHBox(indexHBoxOtherFeatures, indexButton, width/4, height/16);

		}
		

		buttonPressedList.get(enumButtonOwnedCastleWindow.buttonTrain.getIndexButton()).setOnAction(event -> {
			
			setExitCode(EXIT_TRAIN);	
			buttonTrainPressed(getCastleClicked());
																													
		
		} );
		
		
		buttonPressedList.get(enumButtonOwnedCastleWindow.buttonCancelOneQueue.getIndexButton()).setOnAction(event -> { 
																													
			confirmation("Are you sure to cancel the last Element \n of the Production Queue ?", EXIT_CANCEL_ONE_QUEUE);
			} );
		buttonPressedList.get(enumButtonOwnedCastleWindow.buttonCancelAllQueue.getIndexButton()).setOnAction(event -> { 
									
			confirmation("Are you sure to cancel all Elements \n of the Production Queue ?", EXIT_CANCEL_ALL_QUEUE);
			} );
		
		
		buttonPressedList.get(enumButtonOwnedCastleWindow.buttonUpgrade.getIndexButton()).setOnAction(event -> { 
			
			String string = "Are you sure to Upgrade ? " + "\t\tCost : " + htCostProduction.get(enumCastle.Level) + " coins \n\n" + enumCastle.Gold.getText() +  getNbGoldTmp() + "/" + getCastleClicked().getGold();
			nbGoldTmp -= COST_UPGRADE_LEVEL;
			confirmation(string, EXIT_UPGRADE_LEVEL);
			checkNbGoldTmp();
			} );
		
	}

	
	
	public void confirmation (String message, short exitCode) {
		
		removeStatusBar();
		
		getLayer().getChildren().remove(hboxList.get(enumHBoxOwnedCastleWindow.hboxManagementQueue.getIndexHBox()));
		getLayer().getChildren().remove(hboxList.get(enumHBoxOwnedCastleWindow.hboxOtherFeatures.getIndexHBox()));
		
		addHBoxLayer(hboxList.get(enumHBoxOwnedCastleWindow.hboxConfirm.getIndexHBox()), getX()+150, getHeight()/2, 250, 50, 50);
		addButtonConfirm(enumHBoxOwnedCastleWindow.hboxConfirm.getIndexHBox(),enumButtonOwnedCastleWindow.buttonConfirm.getIndexButton(), 250, 50, exitCode);
		
		hboxList.get(enumHBoxOwnedCastleWindow.hboxConfirm.getIndexHBox()).getChildren().add(new Text(message));
		
	}





	public void buttonTrainPressed(Castle c) {

		
		removeStatusBar();
		
		getLayer().getChildren().remove(hboxList.get(enumHBoxOwnedCastleWindow.hboxManagementQueue.getIndexHBox()));
		getLayer().getChildren().remove(hboxList.get(enumHBoxOwnedCastleWindow.hboxOtherFeatures.getIndexHBox()));
		
		
		double y = getY()+getHeight()/6;
		
		for(int indexHBox = enumHBoxOwnedCastleWindow.hboxPikers.getIndexHBox(); indexHBox <= enumHBoxOwnedCastleWindow.hboxConfirm.getIndexHBox(); indexHBox++) {
			
			addHBoxLayer(hboxList.get(indexHBox), getX()+150, y, 250, 50, 50);

			y += getHeight()/8;
				
			}
		
		
		
	    addButtonSign(enumHBoxOwnedCastleWindow.hboxPikers.getIndexHBox(),enumButtonOwnedCastleWindow.lessPikers.getIndexButton(), enumCastle.Piker,  c);
	    addButtonSign(enumHBoxOwnedCastleWindow.hboxKnights.getIndexHBox(),enumButtonOwnedCastleWindow.lessKnights.getIndexButton(), enumCastle.Knight, c);
	    addButtonSign(enumHBoxOwnedCastleWindow.hboxCatapults.getIndexHBox(),enumButtonOwnedCastleWindow.lessCatapults.getIndexButton(), enumCastle.Catapult, c);
		
		
	    addButtonConfirm(enumHBoxOwnedCastleWindow.hboxConfirm.getIndexHBox(),enumButtonOwnedCastleWindow.buttonConfirm.getIndexButton(), 150, 50, EXIT_TRAIN);
		
		
		hboxList.get(enumHBoxOwnedCastleWindow.hboxConfirm.getIndexHBox()).getChildren().add(htVariableData.get(enumCastle.Gold));
			
		
	}
	




	@Override
	public void eventButtonSign(int indexHBox, int indexButton, enumCastle indexElement, boolean sign, Castle c) {


		
		buttonPressedList.get(indexButton).setOnAction(event -> {modifyNbSoldiersTmp(sign, indexElement ,c);
		modifyGoldTmp(sign, indexElement ,c);
		checkNbGoldTmp();
		
		htVariableData.get(indexElement).setText(indexElement.getText() +  getHtNbSoldiersTmp().get(indexElement) + "\t\tCost : " + htCostProduction.get(indexElement) + " coins");
		htVariableData.get(enumCastle.Gold).setText(enumCastle.Gold.getText() +  getNbGoldTmp() + "/" + c.getGold());

		} );	




	}
	
	public void modifyGoldTmp(Boolean plus, enumCastle indexElement, Castle c) {
		
		int val = htCostProduction.get(indexElement);


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

		for(int indexHBox = enumHBoxOwnedCastleWindow.hboxManagementQueue.getIndexHBox(); indexHBox <= enumHBoxOwnedCastleWindow.hboxConfirm.getIndexHBox(); indexHBox++) {

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
