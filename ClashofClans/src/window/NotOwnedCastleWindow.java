package window;





import java.util.List;


import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import static settings.Settings.*;
import shape.Point2D;
import sprite.castle.Castle;
import sprite.castle.Castle.enumCastle;




public class NotOwnedCastleWindow extends Window {

	
	
	private boolean correctNbSoldiersTmp;


	

	public NotOwnedCastleWindow(Pane layer, Point2D point, double w, double h, List<Castle> playerCastles, Castle target) {
		super(layer, point, w, h, playerCastles, target);
		
		
		this.correctNbSoldiersTmp = true;

		mainWindow();
			
		 
	}
	
	
	

	public void mainWindow() {
		
		
		htButton.get(enumButton.Attack).setMinSize(200, 50);

	
		addHBoxLayer(htHBox.get(enumHBox.hboxAttack), getX()+50, getY()+getHeight()/2, 250, 50, 50);
		
		
		htHBox.get(enumHBox.hboxAttack).getChildren().add(htButton.get(enumButton.Attack));
		
		htButton.get(enumButton.Attack).setOnAction(event -> buttonAttackPressed() );
		
	}

	


	
	public void buttonAttackPressed() {
		

		removeStatusBar();
		
		getLayer().getChildren().remove(htHBox.get(enumHBox.hboxAttack));
		
		addButtonChoose();
		
		eventButtonChoose( "CASTLE PLAYER ", EXIT_ATTACK);
		

	}
	





	@Override
	public void eventButtonSign(enumButton button, enumCastle indexElement, boolean sign, Castle c) {


		htButton.get(button).setOnAction(event -> {modifyNbSoldiersTmp(sign, indexElement ,c);
		checkNbSoldiersTmp();
		
		htVariableData.get(indexElement).setText(indexElement.getText() +  getHtNbSoldiersTmp().get(indexElement) + "/" + getHtNbSoldiers().get(indexElement));

		} );	


	}
	
	
	
	
	void checkNbSoldiersTmp(){


		if(getHtNbSoldiersTmp().get(enumCastle.Piker) > getHtNbSoldiers().get(enumCastle.Piker) || getHtNbSoldiersTmp().get(enumCastle.Knight) > getHtNbSoldiers().get(enumCastle.Knight)  || getHtNbSoldiersTmp().get(enumCastle.Catapult) > getHtNbSoldiers().get(enumCastle.Catapult) ) {
			correctNbSoldiersTmp = false;
			htButton.get(enumButton.Confirm).setStyle("-fx-background-color: #FF0000");

		}


		else {
			correctNbSoldiersTmp = true;
			htButton.get(enumButton.Confirm).setStyle("-fx-background-color: #3CEF18");
		}

	}



	@Override
	public boolean canConfirm() {


		return correctNbSoldiersTmp;
	}


















	
	
	
	










}