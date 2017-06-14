package it.polimi.ingsw.lim.client.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXTabPane;
import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.gui.CustomController;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGame extends CustomController
{
	@FXML private Pane gameBoard;
	@FXML private Pane playerBoard1DevelopmentCardsVenture;
	@FXML private Pane playerBoard2DevelopmentCardsVenture;
	@FXML private Pane playerBoard3DevelopmentCardsVenture;
	@FXML private Pane playerBoard4DevelopmentCardsVenture;
	@FXML private Pane playerBoard5DevelopmentCardsVenture;
	@FXML private Pane playerBoard1DevelopmentCardsCharacter;
	@FXML private Pane playerBoard2DevelopmentCardsCharacter;
	@FXML private Pane playerBoard3DevelopmentCardsCharacter;
	@FXML private Pane playerBoard4DevelopmentCardsCharacter;
	@FXML private Pane playerBoard5DevelopmentCardsCharacter;
	@FXML private Pane playerBoard1;
	@FXML private Pane playerBoard2;
	@FXML private Pane playerBoard3;
	@FXML private Pane playerBoard4;
	@FXML private Pane playerBoard5;
	@FXML private VBox rightVBox;
	@FXML private JFXTabPane playerTabPanel;
	@FXML private Pane diceBlack;
	@FXML private Pane diceWhite;
	@FXML private Pane diceOrange;
	@FXML private Pane roundPosition1;
	@FXML private Pane roundPosition2;
	@FXML private Pane roundPosition3;
	@FXML private Pane roundPosition4;
	@FXML private Pane roundPosition5;
	@FXML private Pane victoryPoint0;
	@FXML private Pane victoryPoint1;
	@FXML private Pane victoryPoint2;
	@FXML private Pane victoryPoint3;
	@FXML private Pane victoryPoint4;
	@FXML private Pane victoryPoint5;
	@FXML private Pane victoryPoint6;
	@FXML private Pane victoryPoint7;
	@FXML private Pane victoryPoint8;
	@FXML private Pane victoryPoint9;
	@FXML private Pane victoryPoint10;
	@FXML private Pane victoryPoint11;
	@FXML private Pane victoryPoint12;
	@FXML private Pane victoryPoint13;
	@FXML private Pane victoryPoint14;
	@FXML private Pane victoryPoint15;
	@FXML private Pane victoryPoint16;
	@FXML private Pane victoryPoint17;
	@FXML private Pane victoryPoint18;
	@FXML private Pane victoryPoint19;
	@FXML private Pane victoryPoint20;
	@FXML private Pane victoryPoint21;
	@FXML private Pane victoryPoint22;
	@FXML private Pane victoryPoint23;
	@FXML private Pane victoryPoint24;
	@FXML private Pane victoryPoint25;
	@FXML private Pane victoryPoint26;
	@FXML private Pane victoryPoint27;
	@FXML private Pane victoryPoint28;
	@FXML private Pane victoryPoint29;
	@FXML private Pane victoryPoint30;
	@FXML private Pane victoryPoint31;
	@FXML private Pane victoryPoint32;
	@FXML private Pane victoryPoint33;
	@FXML private Pane victoryPoint34;
	@FXML private Pane victoryPoint35;
	@FXML private Pane victoryPoint36;
	@FXML private Pane victoryPoint37;
	@FXML private Pane victoryPoint38;
	@FXML private Pane victoryPoint39;
	@FXML private Pane victoryPoint40;
	@FXML private Pane victoryPoint41;
	@FXML private Pane victoryPoint42;
	@FXML private Pane victoryPoint43;
	@FXML private Pane victoryPoint44;
	@FXML private Pane victoryPoint45;
	@FXML private Pane victoryPoint46;
	@FXML private Pane victoryPoint47;
	@FXML private Pane victoryPoint48;
	@FXML private Pane victoryPoint49;
	@FXML private Pane victoryPoint50;
	@FXML private Pane victoryPoint51;
	@FXML private Pane victoryPoint52;
	@FXML private Pane victoryPoint53;
	@FXML private Pane victoryPoint54;
	@FXML private Pane victoryPoint55;
	@FXML private Pane victoryPoint56;
	@FXML private Pane victoryPoint57;
	@FXML private Pane victoryPoint58;
	@FXML private Pane victoryPoint59;
	@FXML private Pane victoryPoint60;
	@FXML private Pane victoryPoint61;
	@FXML private Pane victoryPoint62;
	@FXML private Pane victoryPoint63;
	@FXML private Pane victoryPoint64;
	@FXML private Pane victoryPoint65;
	@FXML private Pane victoryPoint66;
	@FXML private Pane victoryPoint67;
	@FXML private Pane victoryPoint68;
	@FXML private Pane victoryPoint69;
	@FXML private Pane victoryPoint70;
	@FXML private Pane victoryPoint71;
	@FXML private Pane victoryPoint72;
	@FXML private Pane victoryPoint73;
	@FXML private Pane victoryPoint74;
	@FXML private Pane victoryPoint75;
	@FXML private Pane victoryPoint76;
	@FXML private Pane victoryPoint77;
	@FXML private Pane victoryPoint78;
	@FXML private Pane victoryPoint79;
	@FXML private Pane victoryPoint80;
	@FXML private Pane victoryPoint81;
	@FXML private Pane victoryPoint82;
	@FXML private Pane victoryPoint83;
	@FXML private Pane victoryPoint84;
	@FXML private Pane victoryPoint85;
	@FXML private Pane victoryPoint86;
	@FXML private Pane victoryPoint87;
	@FXML private Pane victoryPoint88;
	@FXML private Pane victoryPoint89;
	@FXML private Pane victoryPoint90;
	@FXML private Pane victoryPoint91;
	@FXML private Pane victoryPoint92;
	@FXML private Pane victoryPoint93;
	@FXML private Pane victoryPoint94;
	@FXML private Pane victoryPoint95;
	@FXML private Pane victoryPoint96;
	@FXML private Pane victoryPoint97;
	@FXML private Pane victoryPoint98;
	@FXML private Pane victoryPoint99;
	@FXML private Pane militaryPoint0;
	@FXML private Pane militaryPoint1;
	@FXML private Pane militaryPoint2;
	@FXML private Pane militaryPoint3;
	@FXML private Pane militaryPoint4;
	@FXML private Pane militaryPoint5;
	@FXML private Pane militaryPoint6;
	@FXML private Pane militaryPoint7;
	@FXML private Pane militaryPoint8;
	@FXML private Pane militaryPoint9;
	@FXML private Pane militaryPoint10;
	@FXML private Pane militaryPoint11;
	@FXML private Pane militaryPoint12;
	@FXML private Pane militaryPoint13;
	@FXML private Pane militaryPoint14;
	@FXML private Pane militaryPoint15;
	@FXML private Pane militaryPoint16;
	@FXML private Pane militaryPoint17;
	@FXML private Pane militaryPoint18;
	@FXML private Pane militaryPoint19;
	@FXML private Pane militaryPoint20;
	@FXML private Pane militaryPoint21;
	@FXML private Pane militaryPoint22;
	@FXML private Pane militaryPoint23;
	@FXML private Pane militaryPoint24;
	@FXML private Pane militaryPoint25;
	@FXML private Pane faithPoint0;
	@FXML private Pane faithPoint1;
	@FXML private Pane faithPoint2;
	@FXML private Pane faithPoint3;
	@FXML private Pane faithPoint4;
	@FXML private Pane faithPoint5;
	@FXML private Pane faithPoint6;
	@FXML private Pane faithPoint7;
	@FXML private Pane faithPoint8;
	@FXML private Pane faithPoint9;
	@FXML private Pane faithPoint10;
	@FXML private Pane faithPoint11;
	@FXML private Pane faithPoint12;
	@FXML private Pane faithPoint13;
	@FXML private Pane faithPoint14;
	@FXML private Pane faithPoint15;
	@FXML private Pane prestigePoint0;
	@FXML private Pane prestigePoint1;
	@FXML private Pane prestigePoint2;
	@FXML private Pane prestigePoint3;
	@FXML private Pane prestigePoint4;
	@FXML private Pane prestigePoint5;
	@FXML private Pane prestigePoint6;
	@FXML private Pane prestigePoint7;
	@FXML private Pane prestigePoint8;
	@FXML private Pane prestigePoint9;
	@FXML private Pane productionSmall;
	@FXML private Pane productionBig1;
	@FXML private Pane productionBig2;
	@FXML private Pane productionBig3;
	@FXML private Pane productionBig4;
	@FXML private Pane productionBig5;
	@FXML private Pane harvestSmall;
	@FXML private Pane harvestBig1;
	@FXML private Pane harvestBig2;
	@FXML private Pane harvestBig3;
	@FXML private Pane harvestBig4;
	@FXML private Pane harvestBig5;
	@FXML private Pane market1;
	@FXML private Pane market2;
	@FXML private Pane market3;
	@FXML private Pane market4;
	@FXML private Pane market5;
	@FXML private Pane market6;
	@FXML private Pane councilPalace1;
	@FXML private Pane councilPalace2;
	@FXML private Pane councilPalace3;
	@FXML private Pane councilPalace4;
	@FXML private Pane councilPalace5;
	@FXML private Pane territory1;
	@FXML private Pane territory2;
	@FXML private Pane territory3;
	@FXML private Pane territory4;
	@FXML private Pane character1;
	@FXML private Pane character2;
	@FXML private Pane character3;
	@FXML private Pane character4;
	@FXML private Pane building1;
	@FXML private Pane building2;
	@FXML private Pane building3;
	@FXML private Pane building4;
	@FXML private Pane venture1;
	@FXML private Pane venture2;
	@FXML private Pane venture3;
	@FXML private Pane venture4;

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
	}

	@Override
	@PostConstruct
	public void setupGui()
	{
		((Stage) this.getStackPane().getScene().getWindow()).iconifiedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				this.getStackPane().getScene().setCursor(Cursor.HAND);
				this.getStackPane().getScene().setCursor(Cursor.DEFAULT);
			}
		});
		this.getStackPane().getScene().getRoot().requestFocus();
		double originalGameBoardWidth = this.gameBoard.getWidth();
		double originalGameBoardHeight = this.gameBoard.getHeight();
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		double ratio = (bounds.getHeight() - bounds.getHeight() / 15) / this.getStackPane().getScene().getWindow().getHeight();
		this.getStackPane().getScene().getWindow().setWidth(this.getStackPane().getWidth() * ratio);
		this.getStackPane().getScene().getWindow().setHeight(this.getStackPane().getHeight() * ratio);
		this.getStackPane().getScene().getWindow().setX(bounds.getWidth() / 2 - this.getStackPane().getScene().getWindow().getWidth() / 2);
		this.getStackPane().getScene().getWindow().setY(bounds.getHeight() / 2 - this.getStackPane().getScene().getWindow().getHeight() / 2);
		this.getStackPane().setPrefWidth(this.getStackPane().getScene().getWindow().getWidth());
		this.getStackPane().setPrefHeight(this.getStackPane().getScene().getWindow().getHeight());
		this.getStackPane().getStylesheets().add(Client.getInstance().getClass().getResource("/css/jfoenix-nodes-list-button.css").toExternalForm());
		JFXButton button1 = new JFXButton("R1");
		button1.setButtonType(ButtonType.RAISED);
		button1.getStyleClass().addAll("animated-option-button");
		JFXButton button2 = new JFXButton("R2");
		button2.setButtonType(ButtonType.RAISED);
		button2.getStyleClass().addAll("animated-option-button");
		JFXButton button3 = new JFXButton("R3");
		button3.setButtonType(ButtonType.RAISED);
		button3.getStyleClass().addAll("animated-option-button");
		JFXNodesList nodesList = new JFXNodesList();
		nodesList.addAnimatedNode(button1);
		nodesList.addAnimatedNode(button2);
		nodesList.addAnimatedNode(button3);
		this.rightVBox.getChildren().add(nodesList);
		this.getStackPane().getScene().getWindow().sizeToScene();
		double gameBoardWidthRatio = this.gameBoard.getWidth() / originalGameBoardWidth;
		double gameBoardHeightRatio = this.gameBoard.getHeight() / originalGameBoardHeight;
		DropShadow borderGlow = new DropShadow();
		borderGlow.setOffsetY(0.0D);
		borderGlow.setOffsetX(0.0D);
		borderGlow.setColor(Color.WHITE);
		borderGlow.setWidth(40.0D);
		borderGlow.setHeight(40.0D);
		for (Node node : this.gameBoard.getChildren()) {
			if (node instanceof Pane) {
				((Pane) node).setSnapToPixel(false);
				((Pane) node).setPrefWidth(((Pane) node).getPrefWidth() * ratio);
				((Pane) node).setPrefHeight(((Pane) node).getPrefHeight() * ratio);
				node.setOnMouseEntered(event -> node.setEffect(borderGlow));
				node.setOnMouseExited(event -> node.effectProperty().set(null));
			} else if (node instanceof Label) {
				((Label) node).setSnapToPixel(false);
				((Label) node).setPrefWidth(((Label) node).getPrefWidth() * ratio);
				((Label) node).setPrefHeight(((Label) node).getPrefHeight() * ratio);
			}
			node.setLayoutX(node.getLayoutX() * gameBoardWidthRatio);
			node.setLayoutY(node.getLayoutY() * gameBoardHeightRatio);
		}
		double oldWidth = this.playerBoard1DevelopmentCardsVenture.getPrefWidth();
		this.playerBoard1DevelopmentCardsVenture.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard2DevelopmentCardsVenture.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard3DevelopmentCardsVenture.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard4DevelopmentCardsVenture.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard5DevelopmentCardsVenture.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard1DevelopmentCardsVenture.setPrefHeight(this.playerBoard1DevelopmentCardsVenture.getPrefHeight() * this.playerBoard1DevelopmentCardsVenture.getPrefWidth() / oldWidth);
		this.playerBoard2DevelopmentCardsVenture.setPrefHeight(this.playerBoard2DevelopmentCardsVenture.getPrefHeight() * this.playerBoard2DevelopmentCardsVenture.getPrefWidth() / oldWidth);
		this.playerBoard3DevelopmentCardsVenture.setPrefHeight(this.playerBoard3DevelopmentCardsVenture.getPrefHeight() * this.playerBoard3DevelopmentCardsVenture.getPrefWidth() / oldWidth);
		this.playerBoard4DevelopmentCardsVenture.setPrefHeight(this.playerBoard4DevelopmentCardsVenture.getPrefHeight() * this.playerBoard4DevelopmentCardsVenture.getPrefWidth() / oldWidth);
		this.playerBoard5DevelopmentCardsVenture.setPrefHeight(this.playerBoard5DevelopmentCardsVenture.getPrefHeight() * this.playerBoard5DevelopmentCardsVenture.getPrefWidth() / oldWidth);
		oldWidth = this.playerBoard1DevelopmentCardsCharacter.getPrefWidth();
		this.playerBoard1DevelopmentCardsCharacter.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard2DevelopmentCardsCharacter.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard3DevelopmentCardsCharacter.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard4DevelopmentCardsCharacter.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard5DevelopmentCardsCharacter.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard1DevelopmentCardsCharacter.setPrefHeight(this.playerBoard1DevelopmentCardsCharacter.getPrefHeight() * this.playerBoard1DevelopmentCardsCharacter.getPrefWidth() / oldWidth);
		this.playerBoard2DevelopmentCardsCharacter.setPrefHeight(this.playerBoard2DevelopmentCardsCharacter.getPrefHeight() * this.playerBoard2DevelopmentCardsCharacter.getPrefWidth() / oldWidth);
		this.playerBoard3DevelopmentCardsCharacter.setPrefHeight(this.playerBoard3DevelopmentCardsCharacter.getPrefHeight() * this.playerBoard3DevelopmentCardsCharacter.getPrefWidth() / oldWidth);
		this.playerBoard4DevelopmentCardsCharacter.setPrefHeight(this.playerBoard4DevelopmentCardsCharacter.getPrefHeight() * this.playerBoard4DevelopmentCardsCharacter.getPrefWidth() / oldWidth);
		this.playerBoard5DevelopmentCardsCharacter.setPrefHeight(this.playerBoard5DevelopmentCardsCharacter.getPrefHeight() * this.playerBoard5DevelopmentCardsCharacter.getPrefWidth() / oldWidth);
		oldWidth = this.playerBoard1.getPrefWidth();
		this.playerBoard1.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard2.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard3.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard4.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard5.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard1.setPrefHeight(this.playerBoard1.getPrefHeight() * this.playerBoard1.getPrefWidth() / oldWidth);
		this.playerBoard2.setPrefHeight(this.playerBoard2.getPrefHeight() * this.playerBoard2.getPrefWidth() / oldWidth);
		this.playerBoard3.setPrefHeight(this.playerBoard3.getPrefHeight() * this.playerBoard3.getPrefWidth() / oldWidth);
		this.playerBoard4.setPrefHeight(this.playerBoard4.getPrefHeight() * this.playerBoard4.getPrefWidth() / oldWidth);
		this.playerBoard5.setPrefHeight(this.playerBoard5.getPrefHeight() * this.playerBoard5.getPrefWidth() / oldWidth);
		this.playerTabPanel.setMaxHeight(((VBox) this.playerBoard1.getParent()).getHeight());
	}

	@Override
	public void showDialog(String message)
	{
	}
}
