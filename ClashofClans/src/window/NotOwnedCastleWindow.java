package window;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import shape.Point2D;
import sprite.castle.Castle;

public class NotOwnedCastleWindow extends Window {

	
	
	public enum enumButton{

		buttonAttack("Attack", 0),
		lessPikers("-", 1),
		morePikers("+", 2),
		lessKnights("-", 3),
		moreKnights("+", 4),
		lessCatapults("-", 5),
		moreCatapults("+", 6),
		buttonConfirm("Confirm", 7);

		private String nameButton;
		private int indexButton;

		enumButton(String nameButton, int indexButton){
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

	public enum enumHBox{

		hboxAttack(0),
		hboxPikers(1),
		hboxKnights(2),
		hboxCatapults(3),
		hboxConfirm(4);

		
		private int indexHBox;

		enumHBox(int indexHBox){
			this.indexHBox = indexHBox;
		}

		public int getIndexHBox(){
			return indexHBox;
		}
	}	
	

	public NotOwnedCastleWindow(Pane layer, Point2D point, double w, double h, Castle c) {
		super(layer, point, w, h, c);
		
		
		for(enumButton button : enumButton.values()) {
			
			
			
			
			
			buttonAttackPressedList.add(button.getIndexButton(), new Button(button.getNameButton()));
			buttonAttackPressedList.get(button.getIndexButton()).setMinSize(40, 40);
			
		}
		
		for(enumHBox hbox : enumHBox.values()) {
			
			hboxAttackList.add(hbox.getIndexHBox(), new HBox());
		}
		
		
		buttonAttackPressedList.get(enumButton.buttonAttack.getIndexButton()).setMinWidth(200);

		getLayer().getChildren().add(hboxAttackList.get(enumHBox.hboxAttack.getIndexHBox()));
		
		hboxAttackList.get(enumHBox.hboxAttack.getIndexHBox()).relocate(getX()+40, getY()+getHeight()/2);
		hboxAttackList.get(enumHBox.hboxAttack.getIndexHBox()).setPrefSize(200 , getHeight());

		
		hboxAttackList.get(enumHBox.hboxAttack.getIndexHBox()).getChildren().add(buttonAttackPressedList.get(enumButton.buttonAttack.getIndexButton()));
		
		buttonAttackPressedList.get(enumButton.buttonAttack.getIndexButton()).setOnAction(event -> buttonAttackPressed(c) );
		 
	}
	
	public void buttonAttackPressed(Castle c) {
		
		hboxAttackList.get(enumHBox.hboxAttack.getIndexHBox()).getChildren().clear();
		
		getLayer().getChildren().remove(hboxAttackList.get(enumHBox.hboxAttack.getIndexHBox()));
		
		double y = getY()+getHeight()/6;
		for(int indexHBbox = enumHBox.hboxPikers.getIndexHBox(); indexHBbox <= enumHBox.hboxConfirm.getIndexHBox(); indexHBbox++) {
			hboxAttackList.get(indexHBbox).relocate(getX()+50, y);
			hboxAttackList.get(indexHBbox).setMaxSize(250, 50);
			getLayer().getChildren().add(hboxAttackList.get(indexHBbox));
			hboxAttackList.get(indexHBbox).setSpacing(50);
		
			y += getHeight()/6;
			

		
			
			/*for(int indexButton = enumButton.moreSoldiers.getIndexButton(); indexButton <= enumButton.lessSoldiers.getIndexButton(); indexButton++) {
				hboxAttackList.get(indexHBbox).getChildren().add(buttonAttackPressedList.get(indexButton));
				
			}*/
	
		}
		

		hboxAttackList.get(enumHBox.hboxPikers.getIndexHBox()).getChildren().add(new Text("Pikers"));
		
		hboxAttackList.get(enumHBox.hboxPikers.getIndexHBox()).getChildren().add(buttonAttackPressedList.get(enumButton.lessPikers.getIndexButton()));
		hboxAttackList.get(enumHBox.hboxPikers.getIndexHBox()).getChildren().add(buttonAttackPressedList.get(enumButton.morePikers.getIndexButton()));
		
		buttonAttackPressedList.get(enumButton.morePikers.getIndexButton()).setOnAction(event -> System.out.println("morePikers") );

		
		hboxAttackList.get(enumHBox.hboxKnights.getIndexHBox()).getChildren().add(new Text("Knights"));
		hboxAttackList.get(enumHBox.hboxKnights.getIndexHBox()).getChildren().add(buttonAttackPressedList.get(enumButton.lessKnights.getIndexButton()));
		hboxAttackList.get(enumHBox.hboxKnights.getIndexHBox()).getChildren().add(buttonAttackPressedList.get(enumButton.moreKnights.getIndexButton()));
		
		hboxAttackList.get(enumHBox.hboxCatapults.getIndexHBox()).getChildren().add(new Text("Catapults"));
		hboxAttackList.get(enumHBox.hboxCatapults.getIndexHBox()).getChildren().add(buttonAttackPressedList.get(enumButton.lessCatapults.getIndexButton()));
		hboxAttackList.get(enumHBox.hboxCatapults.getIndexHBox()).getChildren().add(buttonAttackPressedList.get(enumButton.moreCatapults.getIndexButton()));
		
		
		hboxAttackList.get(enumHBox.hboxConfirm.getIndexHBox()).getChildren().add(buttonAttackPressedList.get(enumButton.buttonConfirm.getIndexButton()));
		
		buttonAttackPressedList.get(enumButton.buttonConfirm.getIndexButton()).setOnAction(event -> buttonConfirmPressed(c) );
		
		
		buttonAttackPressedList.get(enumButton.buttonConfirm.getIndexButton()).setMinWidth(150);
		
			
		
	}
	
	public void buttonConfirmPressed(Castle c) {
		
		
		
		removeWindow();
		
	}
	
	




}
