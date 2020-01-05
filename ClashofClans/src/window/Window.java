package window;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
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
import sprite.Sprite;
import sprite.castle.Castle;
import sprite.castle.Castle.enumCastle;
import window.Window.enumButton;

import static settings.Settings.*;

public abstract class Window extends Sprite{
	

	
	private boolean keepPlaying;
	
	private Castle castleClicked;
	
	
	private VBox vboxStatusBar; 
	
	private List<Text> statusBar = new ArrayList<Text>();

	private List<Castle> playerCastles = new ArrayList<Castle>();
	
	
	private short exitCode;
	
	protected Hashtable <enumButton,Button> htButton = new Hashtable<enumButton,Button>();
	
	protected Hashtable <enumHBox,HBox> htHBox = new Hashtable<enumHBox,HBox>();
	
	protected Hashtable <enumHBox,VBox> htVBox = new Hashtable<enumHBox,VBox>();
	
	

	protected Hashtable <enumCastle,Text> htVariableData = new Hashtable<enumCastle,Text>();
	
	protected Hashtable <enumCastle,Integer> htNbSoldiersTmp = new Hashtable<enumCastle,Integer>();
	
	protected Hashtable <enumCastle,Integer> htNbSoldiers = new Hashtable<enumCastle,Integer>();
	
	private int indexCastlePlayer;
	
	protected boolean correctNbSoldiersTmp;
	
	protected Castle castleSelectTroops;
	
	

	public enum enumButton{

		Attack("Attack!"),
		
		Train("Train"),
		CancelOneQueue("CancelOneQueue"),
		CancelAllQueue("CancelAllQueue"),
		Upgrade("Upgrade"),
		Transfer("Transfer"),
		
		suppr("X"),
		PrevCastle("<="),
		Choose("Choose"),
		NextCastle("=>"),
		lessPikers("-"),
		morePikers("+"),
		lessKnights("-"),
		moreKnights("+"),
		lessCatapults("-"),
		moreCatapults("+"),
		Confirm("Confirm");

		private String nameButton;

		private enumButton(String nameButton) {
			this.nameButton = nameButton;
		}

		public String getNameButton() {
			return nameButton;
		}
		
	}


	public enum enumHBox{
		
		
		
		
		hboxAttack(),
		
		hboxManagementQueue(),
		hboxOtherFeatures(),
		
		
		hboxSuppr(),
		hboxChoose(),
		hboxPikers(),
		hboxKnights(),
		hboxCatapults(),
		hboxConfirm();


		
	}
	

	
	public Window(Pane layer, Point2D point, double w, double h) {
		
		super(layer, point, Color.DARKGREY, w, h);
		
		this.keepPlaying = false;
		
		
		htNbSoldiersTmp.put(enumCastle.Piker, 0);
		htNbSoldiersTmp.put(enumCastle.Knight, 0);
		htNbSoldiersTmp.put(enumCastle.Catapult, 0);
		
		
		
		htButton.put(enumButton.Attack, new Button(enumButton.Attack.getNameButton()));
		
		htButton.put(enumButton.Train, new Button(enumButton.Train.getNameButton()));
		htButton.put(enumButton.CancelOneQueue, new Button(enumButton.CancelOneQueue.getNameButton()));
		htButton.put(enumButton.CancelAllQueue, new Button(enumButton.CancelAllQueue.getNameButton()));
		htButton.put(enumButton.Upgrade, new Button(enumButton.Upgrade.getNameButton()));
		htButton.put(enumButton.Transfer, new Button(enumButton.Transfer.getNameButton()));
		
		htButton.put(enumButton.suppr, new Button(enumButton.suppr.getNameButton()));
		htButton.put(enumButton.PrevCastle, new Button(enumButton.PrevCastle.getNameButton()));
		htButton.put(enumButton.Choose, new Button(enumButton.Choose.getNameButton()));
		htButton.put(enumButton.NextCastle, new Button(enumButton.NextCastle.getNameButton()));
		htButton.put(enumButton.lessPikers, new Button(enumButton.lessPikers.getNameButton()));
		htButton.put(enumButton.morePikers, new Button(enumButton.morePikers.getNameButton()));
		htButton.put(enumButton.lessKnights, new Button(enumButton.lessKnights.getNameButton()));
		htButton.put(enumButton.moreKnights, new Button(enumButton.moreKnights.getNameButton()));
		htButton.put(enumButton.lessCatapults, new Button(enumButton.lessCatapults.getNameButton()));
		htButton.put(enumButton.moreCatapults, new Button(enumButton.moreCatapults.getNameButton()));
		htButton.put(enumButton.Confirm, new Button(enumButton.Confirm.getNameButton()));
		
		
		
		htHBox.put(enumHBox.hboxAttack, new HBox());
		
		htHBox.put(enumHBox.hboxManagementQueue, new HBox());
		htHBox.put(enumHBox.hboxOtherFeatures, new HBox());
		
		htHBox.put(enumHBox.hboxSuppr, new HBox());
		htHBox.put(enumHBox.hboxChoose, new HBox());
		htHBox.put(enumHBox.hboxPikers, new HBox());
		htHBox.put(enumHBox.hboxKnights, new HBox());
		htHBox.put(enumHBox.hboxCatapults, new HBox());
		htHBox.put(enumHBox.hboxConfirm, new HBox());
		
		

		
		addHBoxLayer(htHBox.get(enumHBox.hboxSuppr), point.getX()+w - w/10, getY(), (int)w/10, (int)w/10, 50);
		
		addButtonHBox(enumHBox.hboxSuppr, enumButton.suppr, w/10, w/10);

		htButton.get(enumButton.suppr).setOnAction(event -> {removeWindow();
			this.exitCode = EXIT_ECHAP;} );
		
		
		this.indexCastlePlayer = 0;
		
		vboxStatusBar = new VBox();
		
		addVBoxLayer(vboxStatusBar, point.getX() + w/1.5, point.getY()+h/8, 400, 400, getHeight()/16);
		
		
		
			
	}
	
	public Window(Pane layer, Point2D point, double w, double h, Castle source, List<Castle> playerCastles) {

		this(layer, point, w , h);
		
		addHtNbSoldiers(source);

		this.castleClicked = source;		
		
		addStatusBar(source, "CASTLE PLAYER " + source.getId());

		initStatusBar();
		
		this.playerCastles = playerCastles;
		

	}
	


	public Window(Pane layer, Point2D point, double w, double h, List<Castle> playerCastles, Castle target) {

		this(layer, point, w , h);
		
		
		
		

		this.castleClicked = target;		
		
		addStatusBar(target, "TARGET " + "( CASTLE " + target.getId() + " )");

		initStatusBar();
		
		this.playerCastles = playerCastles;

	}
	
	/**
	
	* initialise la hastable de soldats fixes
	*
	* @param castle 
	* 	château que utilise pour initialiser ces données
	*	
	*

	*/
	
	protected void addHtNbSoldiers(Castle castle){
		
		
		htNbSoldiers.put(enumCastle.Piker, castle.getNbPikers());
		htNbSoldiers.put(enumCastle.Knight, castle.getNbKnights());
		htNbSoldiers.put(enumCastle.Catapult, castle.getNbCatapults());
		

		
		
	}
	
	/**
	
	* rajoute la barre de status d'un château ( ces données )
	*
	* @param castle 
	* 	château que l'on utilise pour initialiser ces données
	*	
	* @param description
	* 	description que l'on rajoute au texte précédent

	*/
	

	protected void addStatusBar(Castle castle, String description) {
		
		statusBar.add( new Text(description)) ;
		
		statusBar.add( new Text(castle.toString()) )  ;


		
	}
	
	
/**
	
	* modifie la barre de status d'un château ( ces données )
	*
	* @param castle 
	* 	château que l'on utilise pour initialiser ces données
	*	
	* @param description
	* 	description que l'on rajoute au texte précédent

	*/
	
	
	protected void setStatusBar(Castle castle, String description) {
		
		statusBar.get(0).setText(description);
		
		statusBar.get(1).setText(castle.toString());

	}
	
/**
	
	* ajoute les textes à la vbox vboxStatusBar
	* 
	* 
	
		

	*/
	
	
	


	private void initStatusBar() {

		for (Text text : statusBar) {
			text.setFont(Font.font("Verdana", FontWeight.LIGHT, 16));
			vboxStatusBar.getChildren().add(text);

		}

		
	}
	
	
/**
	
	* supprime la vbox vboxStatusBar du noeud
	* 
	* 

	*/
	
	
	
	protected void removeStatusBar () {
		getLayer().getChildren().remove(vboxStatusBar);
		
	}
	
	
/**
	
	* supprime la hbox  hboxSuppr du noeud
	* 
	* 

	*/
	

	
	protected void removeSuppr() {
		
		getLayer().getChildren().remove(htHBox.get(enumHBox.hboxSuppr));
	}

	
/**
	
	* supprime toutes les HBox du noeud
	* 
	* 

	*/
	
	
	public void removeHBox() {
		
		 Enumeration<HBox> e = htHBox.elements();

		    while(e.hasMoreElements())
		    	
		    	getLayer().getChildren().remove(e.nextElement());

		  }
	

/**
	
	* supprime touts les élements de Window ( HBox, barre de statut, bouton Suppr)
	* 
	* 
	*/
	

	protected void removeWindow() {
	
		removeHBox();
		removeSuppr();
		removeStatusBar();
		this.keepPlaying = true;
		this.removeFromLayer();
	
	
	}
	
/**
	
	* ajoute les boutons Sign ( + et - ) dans le noeud
	* 
	* @param hbox 
	* 	hbox qui reçoit le bouton
	* 
	* @param button
	* 	bouton que l'on rajoute au noeud
	* 
	* @param indexElement
	* 
	* 	element ( piker, knight ...) que l'on incrémente ou décrémente
	*
	*	@param c
	*	
	*	castle utilisée pour rajouter du texte utile pour eventButtonSign ( Gold etc ..)
	* 
	* 
	*/

	protected void addButtonSign(enumHBox hbox, enumButton button, enumCastle indexElement, Castle c) {
		
		

		enumButton button1 = null;

		if(button == enumButton.lessPikers) {

			button1 = enumButton.morePikers;

		}
		if(button == enumButton.lessKnights) {

			button1 = enumButton.moreKnights;

		}

		if(button == enumButton.lessCatapults) {

			button1 = enumButton.moreCatapults;

		}
		
		addButtonHBox(hbox, button, getWidth()/16, getHeight()/16);
		addButtonHBox(hbox, button1, getWidth()/16, getHeight()/16);

		eventButtonSign(button, indexElement, false, c );
		eventButtonSign(button1, indexElement, true, c );
		
		htHBox.get(hbox).getChildren().add(htVariableData.get(indexElement));
	
	}
	
	
/**
	
	* les événements des boutons sign sont différents selon les sous classes
	* 
	* @param button
	* 
	* 	bouton auquel on va assigner un événement 
	* 
	* @param indexElement
	* 
	* 	element ( piker, knight ...) que l'on incrémente ou décrémente
	* 
	* @param sign
	* 	
	* 	false pour le bouton -, true pour le bouton + 
	*
	*@param c
	*	
	*	castle utilisée pour rajouter du texte utile pour eventButtonSign ( Gold etc ..)
	* 
	* 
	*/

	
	protected abstract void eventButtonSign(enumButton button, enumCastle indexElement, boolean sign, Castle c);
	
	
	
	
/**
	
	* 	ajoute une hbox au noeud 
	* 
	* 	@param hbox
	* 
	* 	hbox que l'on ajouté
	* 
	*	 @param x
	* 
	* 	coordonnées x de la hbox
	* 
	* 	@param y
	* 	
	* 	coordonnées y de la hbox
	*
	*	@param width
	*	
	*	largeur de la hbox
	* 
	* 	@param height
	* 
	* 	hauteur de la hbox
	* 
	* 	@param spacing
	* 
	* 	espacement entre les éléments ( boutons, textes)
	* 
	*/
	
	
	protected void addHBoxLayer(HBox hbox, double x, double y, double width, double height, double spacing) {
		
		
		hbox.relocate(x, y);
		hbox.setMinSize(width, height);
		hbox.setSpacing(spacing);

		getLayer().getChildren().add(hbox);
		
		
	}
	
/**
	
	* 	ajoute une vbox au noeud 
	* 
	* 	@param vbox
	* 
	* 	hbox que l'on ajouté
	* 
	*	 @param x
	* 
	* 	coordonnées x de la vbox
	* 
	* 	@param y
	* 	
	* 	coordonnées y de la vbox
	*
	*	@param width
	*	
	*	largeur de la vbox
	* 
	* 	@param height
	* 
	* 	hauteur de la vbox
	* 
	* 	@param spacing
	* 
	* 	espacement entre les éléments ( boutons, textes)
	* 
	*/
	
	
	
	protected void addVBoxLayer(VBox vbox, double x, double y, double width, double height, double spacing) {
		
		
		vbox.relocate(x, y);
		vbox.setMinSize(width, height);
		vbox.setSpacing(spacing);

		getLayer().getChildren().add(vbox);
	
		
	}
	
	
	
	
	
/**
	
	* 	ajoute un bouton à la hbox
	* 
	* 	@param hbox
	* 
	* 	hbox auquelle on rajoute un bouton
	* 
	*	 @param button
	* 
	* 	bouton rajouté
	* 
	* 	@param width
	* 	
	* 	largeur du bouton
	*
	*	@param height
	*	
	*	hauteur du bouton
	* 

	*/
	
	
	protected void addButtonHBox(enumHBox hbox, enumButton button, double width, double height) {
		
			htButton.get(button).setMinSize(width, height);
			
			htHBox.get(hbox).getChildren().add(htButton.get(button));
			

	}
	
	
/**
	
	* 	ajoute le bouton confirm au noeud
	* 
	* 	@param width
	* 
	* 	largeur du bouton confirm
	* 
	*	 @param height
	*
	*	hauteur du bouton confirm
	* 


	*/
	

	
	protected void addButtonConfirm(int width, int height) {
		
		
		addButtonHBox(enumHBox.hboxConfirm, enumButton.Confirm, width, height);

		htButton.get(enumButton.Confirm).setStyle("-fx-background-color: #3CEF18");

		
	}
	
/**
	
	* 	événement du bouton confirm, appelle la fonction buttonConfirmPressed
	* 
	* 	@param exitCode
	* 	
	* 
	* 	code de sortie
	* 


	*/
	
	
	
	protected void eventButtonConfirm(short exitCode) {
		
		htButton.get(enumButton.Confirm).setOnAction(event -> buttonConfirmPressed(exitCode) );
		
	}


	
	/**
	
	* 	le prédicat peut on confirmer est différent selon les sous classes

		@return vrai si on peut confirmer faux dans le cas contraire

	*/	
	

	protected abstract boolean canConfirm();
	
	
	
	/**
	
	* 	on vérifie si on peut confirmer, si oui on supprime la fenêtre

	* 	@param exitCode

	* 		code de sortie

	*/	

	protected void buttonConfirmPressed(short exitCode) {


		if(canConfirm()) {
			
			this.exitCode = exitCode;


			removeWindow();}

	}
	
	
	
	
	/**
	
	* 	modifie la hastable de nombre de soldats temporaires

	* 	@param plus

	* 		si vrai on incrémente dans le cas contraire on décrémente
	* 
	* 
	* 	@param indexSoldier
	* 
	* 		quel nombre de soldats temporaires on modifie ( Piker, Knight , .. )

	*/	
	

	protected void modifyNbSoldiersTmp(Boolean plus, enumCastle indexSoldier) {


		int val = htNbSoldiersTmp.get(indexSoldier);

		if(plus) {

			htNbSoldiersTmp.put(indexSoldier, htNbSoldiersTmp.get(indexSoldier)+1);

		}
		else {
			if(val > 0) {
				htNbSoldiersTmp.put(indexSoldier, htNbSoldiersTmp.get(indexSoldier)-1);
			}
		}

	}
	
	
	/**
	
	* 	vérifie si le nombre de soldats temporaires ( de la fenêtre ) ne dépasse pas le nombre de soldats fixes ( selon le château, htNbSoldiers est fixé par un château)

	* 	@param plus

	* 		si vrai on incrémente dans le cas contraire on décrémente
	* 
	* 
	* 	@param indexSoldier
	* 
	* 		quel nombre de soldats temporaires on modifie ( Piker, Knight , .. )

	*/	
	
	
	
	protected void checkNbSoldiersTmp(){


		if(getHtNbSoldiersTmp().get(enumCastle.Piker) > getHtNbSoldiers().get(enumCastle.Piker) || getHtNbSoldiersTmp().get(enumCastle.Knight) > getHtNbSoldiers().get(enumCastle.Knight)  || getHtNbSoldiersTmp().get(enumCastle.Catapult) > getHtNbSoldiers().get(enumCastle.Catapult) ) {
			correctNbSoldiersTmp = false;
			htButton.get(enumButton.Confirm).setStyle("-fx-background-color: #FF0000");

		}


		else {
			correctNbSoldiersTmp = true;
			htButton.get(enumButton.Confirm).setStyle("-fx-background-color: #3CEF18");
		}

	}
	
	
	/**
	
	
	* ce que l'on ajoute dans la fenêtre principale dépend des sous classes

	*/	
	

	public abstract void mainWindow();
	
	
	
	
	
	
	
	public void addButtonChoose() {
		
		double width = getWidth();
		double height = getHeight();

		addButtonHBox(enumHBox.hboxChoose, enumButton.PrevCastle, width/4, height/16);
		addButtonHBox(enumHBox.hboxChoose, enumButton.Choose, width/4, height/16);
		addButtonHBox(enumHBox.hboxChoose, enumButton.NextCastle, width/4, height/16);
		

		addVBoxLayer(vboxStatusBar, getX() + width/2-100, getY() + 50, 400, 400, getHeight()/16);
	
		addHBoxLayer(htHBox.get(enumHBox.hboxChoose), getX()+width/16, (getY()+height)-height/4, width - (width/8), height/16, width/16);
		
	}
	
	
	
	public void eventButtonChoose(String message, short exitCode) {


		setStatusBar(playerCastles.get(indexCastlePlayer), message + playerCastles.get(indexCastlePlayer).getId());

		
		htButton.get(enumButton.PrevCastle).setOnAction(event -> {

			if(indexCastlePlayer > 0) {
				indexCastlePlayer--;}
			setStatusBar(playerCastles.get(indexCastlePlayer), message + playerCastles.get(indexCastlePlayer).getId());
			
		} );	

		htButton.get(enumButton.Choose).setOnAction(event -> buttonChoosePressed(exitCode) );

		htButton.get(enumButton.NextCastle).setOnAction(event -> {
			
			if(indexCastlePlayer < playerCastles.size()-1) {
				indexCastlePlayer++;}
			
			setStatusBar(playerCastles.get(indexCastlePlayer), message + playerCastles.get(indexCastlePlayer).getId());
		} );	

	}
	

	
	public void buttonChoosePressed(short exitCode) {
		
		removeStatusBar();
		
		Castle castlePlayer = castleSelectTroops();
		
		
	    htVariableData.put(enumCastle.Piker, new Text(enumCastle.Piker.getText() +  htNbSoldiersTmp.get(enumCastle.Piker) + "/" + castlePlayer.getNbPikers()));
	    htVariableData.put(enumCastle.Knight, new Text(enumCastle.Knight.getText() +  htNbSoldiersTmp.get(enumCastle.Knight) + "/" + castlePlayer.getNbKnights()));
	    htVariableData.put(enumCastle.Catapult, new Text(enumCastle.Catapult.getText() +  htNbSoldiersTmp.get(enumCastle.Catapult) + "/" + castlePlayer.getNbCatapults()));
		
		addHtNbSoldiers(castlePlayer);
		
		getLayer().getChildren().remove(htHBox.get(enumHBox.hboxChoose));

		selectTroops(castlePlayer, exitCode);
		


	}
	
	
	protected abstract Castle castleSelectTroops();
	
	
	public void selectTroops(Castle castlePlayer, short exitCode) {
		

		double y = getY()+getHeight()/6;
		
		addHBoxLayer(htHBox.get(enumHBox.hboxPikers), getX()+150, y, 250, 50, 50);
		y += getHeight()/8;
		addHBoxLayer(htHBox.get(enumHBox.hboxKnights), getX()+150, y, 250, 50, 50);
		y += getHeight()/8;
		addHBoxLayer(htHBox.get(enumHBox.hboxCatapults), getX()+150, y, 250, 50, 50);
		y += getHeight()/8;
		addHBoxLayer(htHBox.get(enumHBox.hboxConfirm), getX()+150, y, 250, 50, 50);
		
	    addButtonSign(enumHBox.hboxPikers,enumButton.lessPikers, enumCastle.Piker,  castlePlayer);
	    addButtonSign(enumHBox.hboxKnights,enumButton.lessKnights, enumCastle.Knight, castlePlayer);
	    addButtonSign(enumHBox.hboxCatapults,enumButton.lessCatapults, enumCastle.Catapult, castlePlayer);

		addButtonConfirm(150,50);
		eventButtonConfirm(exitCode);
		
		
	}
		


	@Override
	public void checkRemovability() {
		// TODO Auto-generated method stub
		
	}
	
	
	public int getNbPikersTmp() {
		
		return htNbSoldiersTmp.get(enumCastle.Piker);
		
	}
	
	public int getNbKnightsTmp() {
		
		return htNbSoldiersTmp.get(enumCastle.Knight);
		
	}
	
	public int getNbCatapultsTmp() {
		
		return htNbSoldiersTmp.get(enumCastle.Catapult);
		
	}
	
	
	
	
	public boolean isKeepPlaying() {
		return keepPlaying;
	}

	public Castle getCastleClicked() {
		return castleClicked;
	}

	public VBox getVboxStatusBar() {
		return vboxStatusBar;
	}

	public List<Text> getStatusBar() {
		return statusBar;
	}

	public List<Castle> getPlayerCastles() {
		return playerCastles;
	}

	public short getExitCode() {
		return exitCode;
	}

	public Hashtable<enumButton, Button> getHtButton() {
		return htButton;
	}

	public Hashtable<enumHBox, HBox> getHtHBox() {
		return htHBox;
	}

	public Hashtable<enumHBox, VBox> getHtVBox() {
		return htVBox;
	}

	public Hashtable<enumCastle, Text> getHtVariableData() {
		return htVariableData;
	}

	public Hashtable<enumCastle, Integer> getHtNbSoldiersTmp() {
		return htNbSoldiersTmp;
	}

	public Hashtable<enumCastle, Integer> getHtNbSoldiers() {
		return htNbSoldiers;
	}

	public int getIndexCastlePlayer() {
		return indexCastlePlayer;
	}

	public void setExitCode(short exitCode) {
		this.exitCode = exitCode;
	}

	

	
	
	
	





}