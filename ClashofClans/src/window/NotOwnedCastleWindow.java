package window;





import java.util.List;


import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import static settings.Settings.*;
import shape.Point2D;
import sprite.castle.Castle;
import sprite.castle.Castle.enumCastle;




public class NotOwnedCastleWindow extends Window {

	
	
	/**
	
	* appelle le super constructeur et la fonction fenêtre principale
	* 
	* 
	*/



	

	public NotOwnedCastleWindow(Pane layer, Point2D point, double w, double h, List<Castle> playerCastles, Castle target) {
		super(layer, point, w, h, playerCastles, target);
		
		
		this.correctNbSoldiersTmp = true;

		mainWindow();
			
		 
	}
	
	
	
	/**
	
	* fenêtre principale
	* 
	* on ajoute le boutcon Attack et on lui associe son événement
	*/
	
	

	public void mainWindow() {
		
		
		htButton.get(enumButton.Attack).setMinSize(200, 50);

	
		addHBoxLayer(htHBox.get(enumHBox.hboxAttack), getX()+50, getY()+getHeight()/2, 250, 50, 50);
		
		
		htHBox.get(enumHBox.hboxAttack).getChildren().add(htButton.get(enumButton.Attack));
		
		htButton.get(enumButton.Attack).setOnAction(event -> buttonAttackPressed() );
		
	}

	/**
	
	* événement du bouton Attack
	* 
	* on ajoute la hboxChoose et ses boutons associés en lui spécifiant le code de sortie
	*/
	


	
	public void buttonAttackPressed() {
		

		removeStatusBar();
		
		getLayer().getChildren().remove(htHBox.get(enumHBox.hboxAttack));
		
		addButtonChoose();
		
		eventButtonChoose( "CASTLE PLAYER ", EXIT_ATTACK);
		

	}
	

	

	/**
	
	* événement des boutons Sign
	* 
	* on modifie le nombre de soldats temporaires et on vérifie si ce nombre est supérieur au nombre de soldats fixes
	* 
	*/




	@Override
	public void eventButtonSign(enumButton button, enumCastle indexElement, boolean sign, Castle c) {


		htButton.get(button).setOnAction(event -> {modifyNbSoldiersTmp(sign, indexElement);
		checkNbSoldiersTmp();
		
		htVariableData.get(indexElement).setText(indexElement.getText() +  getHtNbSoldiersTmp().get(indexElement) + "/" + getHtNbSoldiers().get(indexElement));

		} );	


	}
	
	
	/**
	
	* si le nombre de soldats temporaires est supérieur au nombre de soldats fixes on ne peut pas confirmer
	* 
	*/
	



	@Override
	public boolean canConfirm() {


		return correctNbSoldiersTmp;
	}


	/**
	
	* @return le château que l'on a choisit dans choose ( indexCastlePlayer())
	*/
	
	
	
	


	@Override
	protected Castle castleSelectTroops() {
		// TODO Auto-generated method stub
		return getPlayerCastles().get(getIndexCastlePlayer());
	}


















	
	
	
	










}