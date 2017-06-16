package it.polimi.ingsw.lim.client.gui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformations;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.gui.CustomController;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.*;

public class ControllerGame extends CustomController
{
	private static final Map<ResourceType, String> RESOURCES_NAMES = new EnumMap<>(ResourceType.class);

	static {
		ControllerGame.RESOURCES_NAMES.put(ResourceType.COIN, "Coins");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.COUNCIL_PRIVILEGE, "Council privileges");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.FAITH_POINT, "Faith points");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.MILITARY_POINT, "Military points");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.PRESTIGE_POINT, "Prestige points");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.SERVANT, "Servants");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.STONE, "Stone");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.VICTORY_POINT, "Victory points");
		ControllerGame.RESOURCES_NAMES.put(ResourceType.WOOD, "Wood");
	}

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
	@FXML private Pane slotTerritory1;
	@FXML private Pane slotTerritory2;
	@FXML private Pane slotTerritory3;
	@FXML private Pane slotTerritory4;
	@FXML private Pane slotCharacter1;
	@FXML private Pane slotCharacter2;
	@FXML private Pane slotCharacter3;
	@FXML private Pane slotCharacter4;
	@FXML private Pane slotBuilding1;
	@FXML private Pane slotBuilding2;
	@FXML private Pane slotBuilding3;
	@FXML private Pane slotBuilding4;
	@FXML private Pane slotVenture1;
	@FXML private Pane slotVenture2;
	@FXML private Pane slotVenture3;
	@FXML private Pane slotVenture4;
	@FXML private Pane player1Venture1;
	@FXML private Pane player1Venture2;
	@FXML private Pane player1Venture3;
	@FXML private Pane player1Venture4;
	@FXML private Pane player1Venture5;
	@FXML private Pane player1Venture6;
	@FXML private Pane player1Character1;
	@FXML private Pane player1Character2;
	@FXML private Pane player1Character3;
	@FXML private Pane player1Character4;
	@FXML private Pane player1Character5;
	@FXML private Pane player1Character6;
	@FXML private Pane player1Building1;
	@FXML private Pane player1Building2;
	@FXML private Pane player1Building3;
	@FXML private Pane player1Building4;
	@FXML private Pane player1Building5;
	@FXML private Pane player1Building6;
	@FXML private Pane player1Territory1;
	@FXML private Pane player1Territory2;
	@FXML private Pane player1Territory3;
	@FXML private Pane player1Territory4;
	@FXML private Pane player1Territory5;
	@FXML private Pane player1Territory6;
	@FXML private Label player1Coin;
	@FXML private Label player1Wood;
	@FXML private Label player1Stone;
	@FXML private Label player1Servant;
	@FXML private Pane player2Venture1;
	@FXML private Pane player2Venture2;
	@FXML private Pane player2Venture3;
	@FXML private Pane player2Venture4;
	@FXML private Pane player2Venture5;
	@FXML private Pane player2Venture6;
	@FXML private Pane player2Character1;
	@FXML private Pane player2Character2;
	@FXML private Pane player2Character3;
	@FXML private Pane player2Character4;
	@FXML private Pane player2Character5;
	@FXML private Pane player2Character6;
	@FXML private Pane player2Building1;
	@FXML private Pane player2Building2;
	@FXML private Pane player2Building3;
	@FXML private Pane player2Building4;
	@FXML private Pane player2Building5;
	@FXML private Pane player2Building6;
	@FXML private Pane player2Territory1;
	@FXML private Pane player2Territory2;
	@FXML private Pane player2Territory3;
	@FXML private Pane player2Territory4;
	@FXML private Pane player2Territory5;
	@FXML private Pane player2Territory6;
	@FXML private Label player2Coin;
	@FXML private Label player2Wood;
	@FXML private Label player2Stone;
	@FXML private Label player2Servant;
	@FXML private Pane player3Venture1;
	@FXML private Pane player3Venture2;
	@FXML private Pane player3Venture3;
	@FXML private Pane player3Venture4;
	@FXML private Pane player3Venture5;
	@FXML private Pane player3Venture6;
	@FXML private Pane player3Character1;
	@FXML private Pane player3Character2;
	@FXML private Pane player3Character3;
	@FXML private Pane player3Character4;
	@FXML private Pane player3Character5;
	@FXML private Pane player3Character6;
	@FXML private Pane player3Building1;
	@FXML private Pane player3Building2;
	@FXML private Pane player3Building3;
	@FXML private Pane player3Building4;
	@FXML private Pane player3Building5;
	@FXML private Pane player3Building6;
	@FXML private Pane player3Territory1;
	@FXML private Pane player3Territory2;
	@FXML private Pane player3Territory3;
	@FXML private Pane player3Territory4;
	@FXML private Pane player3Territory5;
	@FXML private Pane player3Territory6;
	@FXML private Label player3Coin;
	@FXML private Label player3Wood;
	@FXML private Label player3Stone;
	@FXML private Label player3Servant;
	@FXML private Pane player4Venture1;
	@FXML private Pane player4Venture2;
	@FXML private Pane player4Venture3;
	@FXML private Pane player4Venture4;
	@FXML private Pane player4Venture5;
	@FXML private Pane player4Venture6;
	@FXML private Pane player4Character1;
	@FXML private Pane player4Character2;
	@FXML private Pane player4Character3;
	@FXML private Pane player4Character4;
	@FXML private Pane player4Character5;
	@FXML private Pane player4Character6;
	@FXML private Pane player4Building1;
	@FXML private Pane player4Building2;
	@FXML private Pane player4Building3;
	@FXML private Pane player4Building4;
	@FXML private Pane player4Building5;
	@FXML private Pane player4Building6;
	@FXML private Pane player4Territory1;
	@FXML private Pane player4Territory2;
	@FXML private Pane player4Territory3;
	@FXML private Pane player4Territory4;
	@FXML private Pane player4Territory5;
	@FXML private Pane player4Territory6;
	@FXML private Label player4Coin;
	@FXML private Label player4Wood;
	@FXML private Label player4Stone;
	@FXML private Label player4Servant;
	@FXML private Pane player5Venture1;
	@FXML private Pane player5Venture2;
	@FXML private Pane player5Venture3;
	@FXML private Pane player5Venture4;
	@FXML private Pane player5Venture5;
	@FXML private Pane player5Venture6;
	@FXML private Pane player5Character1;
	@FXML private Pane player5Character2;
	@FXML private Pane player5Character3;
	@FXML private Pane player5Character4;
	@FXML private Pane player5Character5;
	@FXML private Pane player5Character6;
	@FXML private Pane player5Building1;
	@FXML private Pane player5Building2;
	@FXML private Pane player5Building3;
	@FXML private Pane player5Building4;
	@FXML private Pane player5Building5;
	@FXML private Pane player5Building6;
	@FXML private Pane player5Territory1;
	@FXML private Pane player5Territory2;
	@FXML private Pane player5Territory3;
	@FXML private Pane player5Territory4;
	@FXML private Pane player5Territory5;
	@FXML private Pane player5Territory6;
	@FXML private Label player5Coin;
	@FXML private Label player5Wood;
	@FXML private Label player5Stone;
	@FXML private Label player5Servant;
	@FXML private JFXDialog dialog;
	@FXML private JFXDialogLayout dialogLayout;
	@FXML private Label dialogLabel;
	@FXML private JFXButton dialogOkButton;
	@FXML private JFXDialog cardDialog;
	@FXML private JFXDialogLayout cardDialogLayout;
	@FXML private Pane cardDialogPane;
	@FXML private JFXDialog personalBonusTileDialog;
	@FXML private JFXDialogLayout personalBonusTileDialogLayout;
	@FXML private HBox personalBonusTileDialogHBox;
	@FXML private JFXButton actionsButton;
	private final Map<Period, Pane> excommunicationTilesPanes = new EnumMap<>(Period.class);
	private final Map<Integer, Pane> victoryPointsPanes = new HashMap<>();
	private final Map<Integer, Pane> militaryPointsPanes = new HashMap<>();
	private final Map<Integer, Pane> faithPointsPanes = new HashMap<>();
	private final Map<Integer, Pane> privilegePointsPanes = new HashMap<>();
	private final Map<Integer, Pane> councilPalacePositionsPanes = new HashMap<>();
	private final Map<Pane, Label> councilPalacePositionsLabels = new HashMap<>();
	private final Map<Integer, Pane> turnOrderPositionsPanes = new HashMap<>();
	private final List<Pane> harvestBigPositionsPanes = new LinkedList<>();
	private final Map<Pane, Label> harvestBigPositionsLabels = new HashMap<>();
	private final List<Pane> productionBigPositionsPanes = new LinkedList<>();
	private final Map<Pane, Label> productionBigPositionsLabels = new HashMap<>();
	private final Map<Row, Pane> developmentCardsBuildingPanes = new EnumMap<>(Row.class);
	private final Map<Row, Pane> developmentCardsCharacterPanes = new EnumMap<>(Row.class);
	private final Map<Row, Pane> developmentCardsTerritoryPanes = new EnumMap<>(Row.class);
	private final Map<Row, Pane> developmentCardsVenturePanes = new EnumMap<>(Row.class);
	private final Map<CardType, List<Pane>> player1DevelopmentCards = new EnumMap<>(CardType.class);
	private final Map<CardType, List<Pane>> player2DevelopmentCards = new EnumMap<>(CardType.class);
	private final Map<CardType, List<Pane>> player3DevelopmentCards = new EnumMap<>(CardType.class);
	private final Map<CardType, List<Pane>> player4DevelopmentCards = new EnumMap<>(CardType.class);
	private final Map<CardType, List<Pane>> player5DevelopmentCards = new EnumMap<>(CardType.class);
	private final Map<ResourceType, Pane> player1Resources = new EnumMap<>(ResourceType.class);
	private final Map<ResourceType, Pane> player2Resources = new EnumMap<>(ResourceType.class);
	private final Map<ResourceType, Pane> player3Resources = new EnumMap<>(ResourceType.class);
	private final Map<ResourceType, Pane> player4Resources = new EnumMap<>(ResourceType.class);
	private final Map<ResourceType, Pane> player5Resources = new EnumMap<>(ResourceType.class);
	private final DropShadow borderGlow = new DropShadow();

	@FXML
	private void boardDevelopmentCardPaneMouseClicked(MouseEvent event)
	{
		if (event.getButton() == MouseButton.SECONDARY) {
			this.getStage().getScene().getRoot().requestFocus();
			if (((Pane) event.getSource()).getBackground() != null) {
				this.cardDialogPane.setBackground(((Pane) event.getSource()).getBackground());
				this.cardDialog.show();
			}
		}
	}

	@FXML
	private void playerDevelopmentCardPaneMouseClicked(MouseEvent event)
	{
		if (event.getButton() == MouseButton.SECONDARY) {
			this.getStage().getScene().getRoot().requestFocus();
			if (((Pane) event.getSource()).getBackground() != null) {
				this.cardDialogPane.setBackground(((Pane) event.getSource()).getBackground());
				this.cardDialog.show();
			}
		}
	}

	@FXML
	public void handleDialogOkButtonAction()
	{
		this.dialog.close();
		this.getStage().getScene().getRoot().requestFocus();
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		this.getStackPane().getChildren().remove(this.dialog);
		this.dialog.setTransitionType(DialogTransition.CENTER);
		this.dialog.setDialogContainer(this.getStackPane());
		this.getStackPane().getChildren().remove(this.cardDialog);
		this.cardDialog.setTransitionType(DialogTransition.CENTER);
		this.cardDialog.setDialogContainer(this.getStackPane());
		this.getStackPane().getChildren().remove(this.personalBonusTileDialog);
		this.personalBonusTileDialog.setTransitionType(DialogTransition.CENTER);
		this.personalBonusTileDialog.setDialogContainer(this.getStackPane());
		this.personalBonusTileDialog.setOverlayClose(false);
		this.borderGlow.setOffsetY(0.0D);
		this.borderGlow.setOffsetX(0.0D);
		this.borderGlow.setColor(Color.BLACK);
		this.borderGlow.setWidth(40.0D);
		this.borderGlow.setHeight(40.0D);
		Utils.setEffect(this.building1, this.borderGlow);
		Utils.setEffect(this.building2, this.borderGlow);
		Utils.setEffect(this.building3, this.borderGlow);
		Utils.setEffect(this.building4, this.borderGlow);
		Utils.setEffect(this.character1, this.borderGlow);
		Utils.setEffect(this.character2, this.borderGlow);
		Utils.setEffect(this.character3, this.borderGlow);
		Utils.setEffect(this.character4, this.borderGlow);
		Utils.setEffect(this.territory1, this.borderGlow);
		Utils.setEffect(this.territory2, this.borderGlow);
		Utils.setEffect(this.territory3, this.borderGlow);
		Utils.setEffect(this.territory4, this.borderGlow);
		Utils.setEffect(this.venture1, this.borderGlow);
		Utils.setEffect(this.venture2, this.borderGlow);
		Utils.setEffect(this.venture3, this.borderGlow);
		Utils.setEffect(this.venture4, this.borderGlow);
		Utils.setEffect(this.player1Building1, this.borderGlow);
		Utils.setEffect(this.player1Building2, this.borderGlow);
		Utils.setEffect(this.player1Building3, this.borderGlow);
		Utils.setEffect(this.player1Building4, this.borderGlow);
		Utils.setEffect(this.player1Building5, this.borderGlow);
		Utils.setEffect(this.player1Building6, this.borderGlow);
		Utils.setEffect(this.player1Character1, this.borderGlow);
		Utils.setEffect(this.player1Character2, this.borderGlow);
		Utils.setEffect(this.player1Character3, this.borderGlow);
		Utils.setEffect(this.player1Character4, this.borderGlow);
		Utils.setEffect(this.player1Character5, this.borderGlow);
		Utils.setEffect(this.player1Character6, this.borderGlow);
		Utils.setEffect(this.player1Territory1, this.borderGlow);
		Utils.setEffect(this.player1Territory2, this.borderGlow);
		Utils.setEffect(this.player1Territory3, this.borderGlow);
		Utils.setEffect(this.player1Territory4, this.borderGlow);
		Utils.setEffect(this.player1Territory5, this.borderGlow);
		Utils.setEffect(this.player1Territory6, this.borderGlow);
		Utils.setEffect(this.player1Venture1, this.borderGlow);
		Utils.setEffect(this.player1Venture2, this.borderGlow);
		Utils.setEffect(this.player1Venture3, this.borderGlow);
		Utils.setEffect(this.player1Venture4, this.borderGlow);
		Utils.setEffect(this.player1Venture5, this.borderGlow);
		Utils.setEffect(this.player1Venture6, this.borderGlow);
		Utils.setEffect(this.player2Building1, this.borderGlow);
		Utils.setEffect(this.player2Building2, this.borderGlow);
		Utils.setEffect(this.player2Building3, this.borderGlow);
		Utils.setEffect(this.player2Building4, this.borderGlow);
		Utils.setEffect(this.player2Building5, this.borderGlow);
		Utils.setEffect(this.player2Building6, this.borderGlow);
		Utils.setEffect(this.player2Character1, this.borderGlow);
		Utils.setEffect(this.player2Character2, this.borderGlow);
		Utils.setEffect(this.player2Character3, this.borderGlow);
		Utils.setEffect(this.player2Character4, this.borderGlow);
		Utils.setEffect(this.player2Character5, this.borderGlow);
		Utils.setEffect(this.player2Character6, this.borderGlow);
		Utils.setEffect(this.player2Territory1, this.borderGlow);
		Utils.setEffect(this.player2Territory2, this.borderGlow);
		Utils.setEffect(this.player2Territory3, this.borderGlow);
		Utils.setEffect(this.player2Territory4, this.borderGlow);
		Utils.setEffect(this.player2Territory5, this.borderGlow);
		Utils.setEffect(this.player2Territory6, this.borderGlow);
		Utils.setEffect(this.player2Venture1, this.borderGlow);
		Utils.setEffect(this.player2Venture2, this.borderGlow);
		Utils.setEffect(this.player2Venture3, this.borderGlow);
		Utils.setEffect(this.player2Venture4, this.borderGlow);
		Utils.setEffect(this.player2Venture5, this.borderGlow);
		Utils.setEffect(this.player2Venture6, this.borderGlow);
		Utils.setEffect(this.player3Building1, this.borderGlow);
		Utils.setEffect(this.player3Building2, this.borderGlow);
		Utils.setEffect(this.player3Building3, this.borderGlow);
		Utils.setEffect(this.player3Building4, this.borderGlow);
		Utils.setEffect(this.player3Building5, this.borderGlow);
		Utils.setEffect(this.player3Building6, this.borderGlow);
		Utils.setEffect(this.player3Character1, this.borderGlow);
		Utils.setEffect(this.player3Character2, this.borderGlow);
		Utils.setEffect(this.player3Character3, this.borderGlow);
		Utils.setEffect(this.player3Character4, this.borderGlow);
		Utils.setEffect(this.player3Character5, this.borderGlow);
		Utils.setEffect(this.player3Character6, this.borderGlow);
		Utils.setEffect(this.player3Territory1, this.borderGlow);
		Utils.setEffect(this.player3Territory2, this.borderGlow);
		Utils.setEffect(this.player3Territory3, this.borderGlow);
		Utils.setEffect(this.player3Territory4, this.borderGlow);
		Utils.setEffect(this.player3Territory5, this.borderGlow);
		Utils.setEffect(this.player3Territory6, this.borderGlow);
		Utils.setEffect(this.player3Venture1, this.borderGlow);
		Utils.setEffect(this.player3Venture2, this.borderGlow);
		Utils.setEffect(this.player3Venture3, this.borderGlow);
		Utils.setEffect(this.player3Venture4, this.borderGlow);
		Utils.setEffect(this.player3Venture5, this.borderGlow);
		Utils.setEffect(this.player3Venture6, this.borderGlow);
		Utils.setEffect(this.player4Building1, this.borderGlow);
		Utils.setEffect(this.player4Building2, this.borderGlow);
		Utils.setEffect(this.player4Building3, this.borderGlow);
		Utils.setEffect(this.player4Building4, this.borderGlow);
		Utils.setEffect(this.player4Building5, this.borderGlow);
		Utils.setEffect(this.player4Building6, this.borderGlow);
		Utils.setEffect(this.player4Character1, this.borderGlow);
		Utils.setEffect(this.player4Character2, this.borderGlow);
		Utils.setEffect(this.player4Character3, this.borderGlow);
		Utils.setEffect(this.player4Character4, this.borderGlow);
		Utils.setEffect(this.player4Character5, this.borderGlow);
		Utils.setEffect(this.player4Character6, this.borderGlow);
		Utils.setEffect(this.player4Territory1, this.borderGlow);
		Utils.setEffect(this.player4Territory2, this.borderGlow);
		Utils.setEffect(this.player4Territory3, this.borderGlow);
		Utils.setEffect(this.player4Territory4, this.borderGlow);
		Utils.setEffect(this.player4Territory5, this.borderGlow);
		Utils.setEffect(this.player4Territory6, this.borderGlow);
		Utils.setEffect(this.player4Venture1, this.borderGlow);
		Utils.setEffect(this.player4Venture2, this.borderGlow);
		Utils.setEffect(this.player4Venture3, this.borderGlow);
		Utils.setEffect(this.player4Venture4, this.borderGlow);
		Utils.setEffect(this.player4Venture5, this.borderGlow);
		Utils.setEffect(this.player4Venture6, this.borderGlow);
		Utils.setEffect(this.player5Building1, this.borderGlow);
		Utils.setEffect(this.player5Building2, this.borderGlow);
		Utils.setEffect(this.player5Building3, this.borderGlow);
		Utils.setEffect(this.player5Building4, this.borderGlow);
		Utils.setEffect(this.player5Building5, this.borderGlow);
		Utils.setEffect(this.player5Building6, this.borderGlow);
		Utils.setEffect(this.player5Character1, this.borderGlow);
		Utils.setEffect(this.player5Character2, this.borderGlow);
		Utils.setEffect(this.player5Character3, this.borderGlow);
		Utils.setEffect(this.player5Character4, this.borderGlow);
		Utils.setEffect(this.player5Character5, this.borderGlow);
		Utils.setEffect(this.player5Character6, this.borderGlow);
		Utils.setEffect(this.player5Territory1, this.borderGlow);
		Utils.setEffect(this.player5Territory2, this.borderGlow);
		Utils.setEffect(this.player5Territory3, this.borderGlow);
		Utils.setEffect(this.player5Territory4, this.borderGlow);
		Utils.setEffect(this.player5Territory5, this.borderGlow);
		Utils.setEffect(this.player5Territory6, this.borderGlow);
		Utils.setEffect(this.player5Venture1, this.borderGlow);
		Utils.setEffect(this.player5Venture2, this.borderGlow);
		Utils.setEffect(this.player5Venture3, this.borderGlow);
		Utils.setEffect(this.player5Venture4, this.borderGlow);
		Utils.setEffect(this.player5Venture5, this.borderGlow);
		Utils.setEffect(this.player5Venture6, this.borderGlow);
	}

	@Override
	@PostConstruct
	public void setupGui()
	{
		this.getStage().getScene().getRoot().requestFocus();
		double originalGameBoardWidth = this.gameBoard.getWidth();
		double originalGameBoardHeight = this.gameBoard.getHeight();
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double ratio = (bounds.getHeight() - bounds.getHeight() / 15) / this.getStage().getHeight();
		this.getStage().setWidth(this.getStackPane().getWidth() * ratio);
		this.getStage().setHeight(this.getStackPane().getHeight() * ratio);
		this.getStage().setX(bounds.getWidth() / 2 - this.getStage().getWidth() / 2);
		this.getStage().setY(bounds.getHeight() / 2 - this.getStage().getHeight() / 2);
		this.getStackPane().setPrefWidth(this.getStage().getWidth());
		this.getStackPane().setPrefHeight(this.getStage().getHeight());
		this.getStackPane().getStylesheets().add(Client.getInstance().getClass().getResource("/css/jfoenix-nodes-list-button.css").toExternalForm());
		this.actionsButton = new JFXButton("R1");
		this.actionsButton.setDisable(true);
		this.actionsButton.setButtonType(ButtonType.RAISED);
		this.actionsButton.getStyleClass().addAll("animated-option-button");
		JFXButton button2 = new JFXButton("R2");
		button2.setButtonType(ButtonType.RAISED);
		button2.getStyleClass().addAll("animated-option-button");
		JFXButton button3 = new JFXButton("R3");
		button3.setButtonType(ButtonType.RAISED);
		button3.getStyleClass().addAll("animated-option-button");
		JFXNodesList nodesList = new JFXNodesList();
		nodesList.addAnimatedNode(this.actionsButton);
		nodesList.addAnimatedNode(button2);
		nodesList.addAnimatedNode(button3);
		this.rightVBox.getChildren().add(nodesList);
		this.getStage().sizeToScene();
		double gameBoardWidthRatio = this.gameBoard.getWidth() / originalGameBoardWidth;
		double gameBoardHeightRatio = this.gameBoard.getHeight() / originalGameBoardHeight;
		Utils.resizeChildrenNode(this.gameBoard, gameBoardWidthRatio, gameBoardHeightRatio);
		this.playerTabPanel.setMaxHeight(((VBox) this.playerBoard1.getParent()).getHeight());
		this.cardDialogLayout.setPrefWidth(this.territory1.getWidth() * 5);
		this.cardDialogLayout.setPrefHeight(this.territory1.getHeight() * 5);
		this.cardDialogPane.setPrefWidth(this.territory1.getWidth() * 5);
		this.cardDialogPane.setPrefHeight(this.territory1.getHeight() * 5);
		double oldWidth = this.playerBoard1DevelopmentCardsVenture.getPrefWidth();
		this.playerBoard1DevelopmentCardsVenture.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard2DevelopmentCardsVenture.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard3DevelopmentCardsVenture.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard4DevelopmentCardsVenture.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard5DevelopmentCardsVenture.setPrefWidth(this.playerTabPanel.getWidth());
		ratio = this.playerBoard1DevelopmentCardsVenture.getPrefWidth() / oldWidth;
		this.playerBoard1DevelopmentCardsVenture.setPrefHeight(this.playerBoard1DevelopmentCardsVenture.getPrefHeight() * ratio);
		this.playerBoard2DevelopmentCardsVenture.setPrefHeight(this.playerBoard2DevelopmentCardsVenture.getPrefHeight() * ratio);
		this.playerBoard3DevelopmentCardsVenture.setPrefHeight(this.playerBoard3DevelopmentCardsVenture.getPrefHeight() * ratio);
		this.playerBoard4DevelopmentCardsVenture.setPrefHeight(this.playerBoard4DevelopmentCardsVenture.getPrefHeight() * ratio);
		this.playerBoard5DevelopmentCardsVenture.setPrefHeight(this.playerBoard5DevelopmentCardsVenture.getPrefHeight() * ratio);
		Utils.resizeChildrenNode(this.playerBoard1DevelopmentCardsVenture, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard2DevelopmentCardsVenture, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard3DevelopmentCardsVenture, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard4DevelopmentCardsVenture, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard5DevelopmentCardsVenture, ratio, ratio);
		oldWidth = this.playerBoard1DevelopmentCardsCharacter.getPrefWidth();
		this.playerBoard1DevelopmentCardsCharacter.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard2DevelopmentCardsCharacter.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard3DevelopmentCardsCharacter.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard4DevelopmentCardsCharacter.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard5DevelopmentCardsCharacter.setPrefWidth(this.playerTabPanel.getWidth());
		ratio = this.playerBoard1DevelopmentCardsCharacter.getPrefWidth() / oldWidth;
		this.playerBoard1DevelopmentCardsCharacter.setPrefHeight(this.playerBoard1DevelopmentCardsCharacter.getPrefHeight() * this.playerBoard1DevelopmentCardsCharacter.getPrefWidth() / oldWidth);
		this.playerBoard2DevelopmentCardsCharacter.setPrefHeight(this.playerBoard2DevelopmentCardsCharacter.getPrefHeight() * this.playerBoard2DevelopmentCardsCharacter.getPrefWidth() / oldWidth);
		this.playerBoard3DevelopmentCardsCharacter.setPrefHeight(this.playerBoard3DevelopmentCardsCharacter.getPrefHeight() * this.playerBoard3DevelopmentCardsCharacter.getPrefWidth() / oldWidth);
		this.playerBoard4DevelopmentCardsCharacter.setPrefHeight(this.playerBoard4DevelopmentCardsCharacter.getPrefHeight() * this.playerBoard4DevelopmentCardsCharacter.getPrefWidth() / oldWidth);
		this.playerBoard5DevelopmentCardsCharacter.setPrefHeight(this.playerBoard5DevelopmentCardsCharacter.getPrefHeight() * this.playerBoard5DevelopmentCardsCharacter.getPrefWidth() / oldWidth);
		Utils.resizeChildrenNode(this.playerBoard1DevelopmentCardsCharacter, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard2DevelopmentCardsCharacter, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard3DevelopmentCardsCharacter, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard4DevelopmentCardsCharacter, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard5DevelopmentCardsCharacter, ratio, ratio);
		oldWidth = this.playerBoard1.getPrefWidth();
		this.playerBoard1.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard2.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard3.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard4.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard5.setPrefWidth(this.playerTabPanel.getWidth());
		ratio = this.playerBoard1.getPrefWidth() / oldWidth;
		this.playerBoard1.setPrefHeight(this.playerBoard1.getPrefHeight() * this.playerBoard1.getPrefWidth() / oldWidth);
		this.playerBoard2.setPrefHeight(this.playerBoard2.getPrefHeight() * this.playerBoard2.getPrefWidth() / oldWidth);
		this.playerBoard3.setPrefHeight(this.playerBoard3.getPrefHeight() * this.playerBoard3.getPrefWidth() / oldWidth);
		this.playerBoard4.setPrefHeight(this.playerBoard4.getPrefHeight() * this.playerBoard4.getPrefWidth() / oldWidth);
		this.playerBoard5.setPrefHeight(this.playerBoard5.getPrefHeight() * this.playerBoard5.getPrefWidth() / oldWidth);
		Utils.resizeChildrenNode(this.playerBoard1, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard2, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard3, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard4, ratio, ratio);
		Utils.resizeChildrenNode(this.playerBoard5, ratio, ratio);
		this.runTests();
	}

	@Override
	public void showDialog(String message)
	{
		this.getStage().getScene().getRoot().requestFocus();
		this.dialogLabel.setText(message);
		this.dialog.show();
	}

	private void runTests()
	{
		List<PersonalBonusTileInformations> personalBonusTileInformations = new ArrayList<>();
		List<ResourceAmount> resourceAmounts = new ArrayList<>();
		resourceAmounts.add(new ResourceAmount(ResourceType.MILITARY_POINT, 5));
		resourceAmounts.add(new ResourceAmount(ResourceType.WOOD, 15));
		personalBonusTileInformations.add(new PersonalBonusTileInformations(0, "", 2, resourceAmounts, 2, resourceAmounts));
		personalBonusTileInformations.add(new PersonalBonusTileInformations(0, "", 2, resourceAmounts, 2, resourceAmounts));
		personalBonusTileInformations.add(new PersonalBonusTileInformations(0, "", 2, resourceAmounts, 2, resourceAmounts));
		personalBonusTileInformations.add(new PersonalBonusTileInformations(0, "", 2, resourceAmounts, 2, resourceAmounts));
		personalBonusTileInformations.add(new PersonalBonusTileInformations(0, "", 2, resourceAmounts, 2, resourceAmounts));
		this.showPersonalBonusTileDialog(personalBonusTileInformations);
	}

	public void showPersonalBonusTileDialog(List<PersonalBonusTileInformations> personalBonusTilesInformations)
	{
		this.dialog.close();
		this.cardDialog.close();
		for (PersonalBonusTileInformations personalBonusTileInformations : personalBonusTilesInformations) {
			Pane pane = new Pane();
			Label label = new Label();
			label.setFont(CommonUtils.ROBOTO_REGULAR);
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Production activation cost: ");
			stringBuilder.append(personalBonusTileInformations.getProductionActivationCost());
			if (!personalBonusTileInformations.getProductionInstantResources().isEmpty()) {
				stringBuilder.append("\n\nProduction bonus resources:");
			}
			for (ResourceAmount resourceAmount : personalBonusTileInformations.getProductionInstantResources()) {
				stringBuilder.append('\n');
				stringBuilder.append(ControllerGame.RESOURCES_NAMES.get(resourceAmount.getResourceType()));
				stringBuilder.append(": ");
				stringBuilder.append(resourceAmount.getAmount());
			}
			stringBuilder.append("\n\nHarvest activation cost: ");
			stringBuilder.append(personalBonusTileInformations.getProductionActivationCost());
			if (!personalBonusTileInformations.getHarvestInstantResources().isEmpty()) {
				stringBuilder.append("\n\nHarvest bonus resources:");
			}
			for (ResourceAmount resourceAmount : personalBonusTileInformations.getHarvestInstantResources()) {
				stringBuilder.append('\n');
				stringBuilder.append(ControllerGame.RESOURCES_NAMES.get(resourceAmount.getResourceType()));
				stringBuilder.append(": ");
				stringBuilder.append(resourceAmount.getAmount());
			}
			label.setText(stringBuilder.toString());
			pane.getChildren().add(label);
			Utils.setEffect(pane, this.borderGlow);
			pane.setOnMouseClicked(event -> {
				Client.getInstance().getConnectionHandler().sendGamePersonalBonusTilePlayerChoice(personalBonusTileInformations.getIndex());
				this.personalBonusTileDialog.close();
			});
			this.personalBonusTileDialogHBox.getChildren().add(pane);
		}
		Platform.runLater(() -> {
			this.personalBonusTileDialogLayout.setPrefWidth(((Pane) this.personalBonusTileDialogHBox.getChildren().get(0)).getWidth() * this.personalBonusTileDialogHBox.getChildren().size() + this.personalBonusTileDialogHBox.getSpacing() * (this.personalBonusTileDialogHBox.getChildren().size() - 1) + 48);
			this.getStage().getScene().getRoot().requestFocus();
			this.personalBonusTileDialog.show();
		});
	}

	public JFXButton getActionsButton()
	{
		return this.actionsButton;
	}
}
