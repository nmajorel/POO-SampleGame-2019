package window;


import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import shape.Point2D;
import sprite.castle.Castle;
import sprite.castle.Castle.enumCastle;



import static settings.Settings.*;

import java.util.Hashtable;
import java.util.List;






public class OwnedCastleWindow extends Window implements managementButton{
	
	
	/**
	
	* variable qui donne le montant ( gold ) utilis� dans la fen�tre
	* 
	* 
	* 
	* 
	*/
	
	
	private int nbGoldTmp;
	
	
	/**
	
	* variable qui demande si on doit v�rifier le montant utilis�
	* 
	* 
	* 
	* 
	*/
	
	private boolean verifyNbGoldTmp;
	
	/**
	
	* variable qui demande si on doit v�rifier le nombre de soldats temporaires
	* 
	* 
	* 
	* 
	*/
	
	
	private boolean verifyNbSoldiersTmp;
	
	
	/**
	
	* variable qui v�rifie si ont doit rajouter un texte sp�cifique
	* 
	* 
	* 
	* 
	*/
	
	
	private boolean isTextSign;
	
	
	
	/**
	
	* hashTable qui associe un enumCastle ( Piker, Knight, Catapult ) � son co�t de production
	* 
	* 
	* 
	*/
	
	
	
	private Hashtable <enumCastle,Integer> htCostProduction = new Hashtable<enumCastle,Integer>();
	
/**
	
	 Constructeur
	
	* GoldTmp est �gal au ch�teau sur lequel on clique ( un ch�teau de player )
	* 
	* on initialise les hashTables 
	* 
	* on appelle la fonction mainWindow
	* 
	* 
	*/
	
	


	public OwnedCastleWindow(Pane layer, Point2D point, double w, double h, Castle source, List<Castle> playerCastles) {
		super(layer, point, w, h, source, playerCastles);
		
		this.nbGoldTmp = source.getGold();
		
		htCostProduction.put(enumCastle.Piker, COST_PIKER);
		htCostProduction.put(enumCastle.Knight, COST_KNIGHT);
		htCostProduction.put(enumCastle.Catapult, COST_CATAPULT);
		htCostProduction.put(enumCastle.Level, COST_UPGRADE_LEVEL);
		
		
	    htVariableData.put(enumCastle.Piker, new Text(enumCastle.Piker.getText() +  getHtNbSoldiersTmp().get(enumCastle.Piker) + "\t\tCost : " + htCostProduction.get(enumCastle.Piker) + " coins"));
	    htVariableData.put(enumCastle.Knight, new Text(enumCastle.Knight.getText() +  getHtNbSoldiersTmp().get(enumCastle.Knight) + "\t\tCost : " + htCostProduction.get(enumCastle.Knight) + " coins"));
	    htVariableData.put(enumCastle.Catapult, new Text(enumCastle.Catapult.getText() +  getHtNbSoldiersTmp().get(enumCastle.Catapult) + "\t\tCost : " + htCostProduction.get(enumCastle.Catapult) + " coins"));
	    htVariableData.put(enumCastle.Gold, new Text(enumCastle.Gold.getText() +  getNbGoldTmp() + "/" + source.getGold()));
		
		this.verifyNbGoldTmp = false;
		this.verifyNbSoldiersTmp = false;
		
		this.correctNbSoldiersTmp = true;
		
		mainWindow();


	}
	
	/**
	
	* fen�tre principale
	* 
	* on ajoute les boutons Train, Upgrade, Transfer, CancelOneQueue, CancelAllQueue � leurs hbox respectives et on leur associe des �v�nements
	* 
	* 
	* 
	* 
	*/
	

	
	public void mainWindow() {
		
		double width = getWidth();
		double height = getHeight();
		

		addButtonHBox(enumHBox.hboxManagementQueue, enumButton.Train, width/4, height/16);
		addButtonHBox(enumHBox.hboxManagementQueue, enumButton.CancelOneQueue, width/4, height/16);
		addButtonHBox(enumHBox.hboxManagementQueue, enumButton.CancelAllQueue, width/4, height/16);
			
		addButtonHBox(enumHBox.hboxOtherFeatures, enumButton.Upgrade, width/4, height/16);
		addButtonHBox(enumHBox.hboxOtherFeatures, enumButton.Transfer, width/4, height/16);

		addHBoxLayer(htHBox.get(enumHBox.hboxManagementQueue), getX()+width/16, (getY()+height)-height/8, width - (width/8), height/16, width/16);
		addHBoxLayer(htHBox.get(enumHBox.hboxOtherFeatures), getX()+width/4, (getY()+height)-height/4, width - (width/8), height/16, width/16);

		htButton.get(enumButton.Train).setOnAction(event -> {
			
			setExitCode(EXIT_TRAIN);	
			buttonTrainPressed(getCastleClicked());

		} );
		
		
		htButton.get(enumButton.CancelOneQueue).setOnAction(event -> { 
																													
			confirmation("Are you sure to cancel the last Element \n of the Production Queue ?", EXIT_CANCEL_ONE_QUEUE);
			} );
		htButton.get(enumButton.CancelAllQueue).setOnAction(event -> { 
									
			confirmation("Are you sure to cancel all Elements \n of the Production Queue ?", EXIT_CANCEL_ALL_QUEUE);
			} );
		
		
		htButton.get(enumButton.Upgrade).setOnAction(event -> { 
			
			this.verifyNbGoldTmp = true;
			String string = "Are you sure to Upgrade ? " + "\t\tCost : " + htCostProduction.get(enumCastle.Level) + " coins \n\n" + enumCastle.Gold.getText() +  getNbGoldTmp() + "/" + getCastleClicked().getGold();
			nbGoldTmp -= COST_UPGRADE_LEVEL;
			confirmation(string, EXIT_UPGRADE_LEVEL);
			checkNbGoldTmp();
			} );
		
		
		htButton.get(enumButton.Transfer).setOnAction(event -> { 
			
			removeStatusBar();
			
			this.verifyNbSoldiersTmp = true;
			this.isTextSign = false;
			
			getLayer().getChildren().remove(htHBox.get(enumHBox.hboxManagementQueue));
			getLayer().getChildren().remove(htHBox.get(enumHBox.hboxOtherFeatures));
			addButtonChoose();
			eventButtonChoose("CASTLE PLAYER " ,EXIT_TRANSFER);
			

		});
		
		
		
	}
	
	/**
	
	* g�n�re une fen�tre sp�cifique qui ajoute un bouton confirm pour v�rifie si le joueur souhaite bien modifie ces donn�es
	* 
	* @param message
	* 
	* 	message de confirmation
	* 
	* @param exitCode
	* 
	* 	code de sortie pour le bouton confirm
	* 
	* 
	* 
	* 
	*/
	

	
	
	public void confirmation (String message, short exitCode) {
		
		removeStatusBar();
		

		getLayer().getChildren().remove(htHBox.get(enumHBox.hboxManagementQueue));
		getLayer().getChildren().remove(htHBox.get(enumHBox.hboxOtherFeatures));
		

		
		addHBoxLayer(htHBox.get(enumHBox.hboxConfirm), getX()+150, getHeight()/2, 250, 50, 50);
		
		addButtonConfirm(250, 50);
		eventButtonConfirm(exitCode);
		
		htHBox.get(enumHBox.hboxConfirm).getChildren().add(new Text(message));
		
	}
	
	/**
	
	* actions du bouton Train
	* 
	* on appelle la fonction de s�lection des troupes selectTroops
	* 
	* 
	* @param c
	* 	ch�teau avec lequel on souhaite entrainer des troupes.
	* 
	* 
	* 
	* 
	*/
	
	

	public void buttonTrainPressed(Castle c) {

		
		removeStatusBar();
		
		this.verifyNbGoldTmp = true;
		this.isTextSign = true;
		
		getLayer().getChildren().remove(htHBox.get(enumHBox.hboxManagementQueue));
		getLayer().getChildren().remove(htHBox.get(enumHBox.hboxOtherFeatures));
	    
	    selectTroops(c, EXIT_TRAIN);

	    htHBox.get(enumHBox.hboxConfirm).getChildren().add(htVariableData.get(enumCastle.Gold));
			
		
	}
	
	
/**
	
	*
	* on appelle checkNbGoldTmp() ou  checkNbSoldiersTmp() ( changer la couleur du bouton Confirm ) si on doit v�rifier ce pr�dicat � chaque fois
	* on modife la valeur temporaire de gold
	* 
	* On modifie le texte variable, texte que l'on rajoute � la hbox ( hboxPikers, .... )
	* 
	* isTextSign ( bouton Train )
	* 
	* !isTextSign ( bouton Transfer )
	* 
	* 
	* 
	*/
	


	@Override
	public void eventButtonSign(enumButton button, enumCastle indexElement, boolean sign, Castle c) {


		
		htButton.get(button).setOnAction(event -> {modifyNbSoldiersTmp(sign, indexElement);
		modifyGoldTmp(sign, indexElement ,c);
		
		if(verifyNbGoldTmp) {
			checkNbGoldTmp();
		}
		if(verifyNbSoldiersTmp){
			
			checkNbSoldiersTmp();
			
		}
		
		
		if(isTextSign) {
			
			
			htVariableData.get(indexElement).setText(indexElement.getText() +  getHtNbSoldiersTmp().get(indexElement) + "\t\tCost : " + htCostProduction.get(indexElement) + " coins");
						
			htVariableData.get(enumCastle.Gold).setText(enumCastle.Gold.getText() +  getNbGoldTmp() + "/" + c.getGold());
		
		}
		
		else {
			
			htVariableData.get(indexElement).setText(indexElement.getText() +  getHtNbSoldiersTmp().get(indexElement) + "/" + getHtNbSoldiers().get(indexElement));
		}
		
	

		} );	




	}
	
	
	
	
/**
	
	*
	* m�me fonction que modifyNbSoldierTmp mais avec l'argent cette fois ci
	* 
	* 
	* 
	*/
	
	
	
	
	public void modifyGoldTmp(Boolean plus, enumCastle indexElement, Castle c) {
		
		int val = htCostProduction.get(indexElement);


		if(plus) {

			nbGoldTmp -= val;

		}
		else if (nbGoldTmp + val <= c.getGold()) {

			nbGoldTmp += val;

		}

	}
	
	
	
	
	
	
	
	
/**
	
	*
	* modifie la couleur du bouton Confirm
	* 
	* vert si le montant d�pens� est correct
	* rouge dans le cas contraire
	* 
	* 
	* 
	*/
	
	
	
	

	void checkNbGoldTmp(){

		if(nbGoldTmp < 0) {

			htButton.get(enumButton.Confirm).setStyle("-fx-background-color: #FF0000");

		}

		else {

			htButton.get(enumButton.Confirm).setStyle("-fx-background-color: #3CEF18");
		}

	}
	
	
/**
	
	*
	* Soit on v�rifie que le joueur ne d�pense pas trop ( bouton Train )
	* 
	* Soit on v�rifie que le nombre de soldats temporaires est correct ( bouton Transfer )
	* 
	* 
	* 
	*/
	
	
	
	


	public boolean canConfirm() {

		if(verifyNbGoldTmp) {
			return nbGoldTmp >= 0;
		}
		if(verifyNbSoldiersTmp){
			
			return correctNbSoldiersTmp;
			
		}
		
		
		
		return true;
	}



	
	
	public int getNbGoldTmp() {
		return nbGoldTmp;
	}

	




	public void setNbGoldTmp(int nbGoldTmp) {
		this.nbGoldTmp = nbGoldTmp;
	}

	
	
/**
	
	*
		le ch�teau que l'on choisit pour la s�lection des troupes est le ch�teau sur lequel on a cliqu�
	* 
	* 
	* 
	*/


	@Override
	protected Castle castleSelectTroops() {
		// TODO Auto-generated method stub
		return getCastleClicked();
	}





	


}
