package it.polimi.ingsw.lim.client.game;

import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.CouncilPalaceRewardInformations;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformations;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformations;
import it.polimi.ingsw.lim.common.game.cards.*;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;

import java.util.*;

public class GameStatus
{
	private static final GameStatus INSTANCE = new GameStatus();
	private final Map<Integer, DevelopmentCardBuildingInformations> developmentCardsBuilding = new HashMap<>();
	private final Map<Integer, DevelopmentCardCharacterInformations> developmentCardsCharacter = new HashMap<>();
	private final Map<Integer, DevelopmentCardTerritoryInformations> developmentCardsTerritory = new HashMap<>();
	private final Map<Integer, DevelopmentCardVentureInformations> developmentCardsVenture = new HashMap<>();
	private final Map<Integer, LeaderCardInformations> leaderCards = new HashMap<>();
	private final Map<Integer, ExcommunicationTileInformations> excommunicationTiles = new HashMap<>();
	private final Map<Integer, CouncilPalaceRewardInformations> councilPalaceRewards = new HashMap<>();
	private final Map<Integer, PersonalBonusTileInformations> personalBonusTiles = new HashMap<>();
	private final Map<Period, Integer> currentExcommunicationTiles = new EnumMap<>(Period.class);
	private final Map<Integer, PlayerData> currentPlayerData = new HashMap<>();
	private int ownPlayerIndex;
	private final Map<Row, Integer> currentDevelopmentCardsBuilding = new EnumMap<>(Row.class);
	private final Map<Row, Integer> currentDevelopmentCardsCharacter = new EnumMap<>(Row.class);
	private final Map<Row, Integer> currentDevelopmentCardsTerritory = new EnumMap<>(Row.class);
	private final Map<Row, Integer> currentDevelopmentCardsVenture = new EnumMap<>(Row.class);
	private final Map<Integer, Integer> currentTurnOrder = new HashMap<>();
	private final Map<Integer, Integer> currentCouncilPalaceOrder = new HashMap<>();
	private int currentTurnPlayerIndex;
	private final List<AvailableAction> currentAvailableActions = new ArrayList<>();
	private final List<Integer> availablePersonalBonusTiles = new ArrayList<>();
	private final List<Integer> availableLeaderCards = new ArrayList<>();

	private GameStatus()
	{
	}

	public void setup(Map<Integer, DevelopmentCardBuildingInformations> developmentCardsBuilding, Map<Integer, DevelopmentCardCharacterInformations> developmentCardsCharacter, Map<Integer, DevelopmentCardTerritoryInformations> developmentCardsTerritory, Map<Integer, DevelopmentCardVentureInformations> developmentCardsVenture, Map<Integer, LeaderCardInformations> leaderCards, Map<Integer, ExcommunicationTileInformations> excommunicationTiles, Map<Integer, CouncilPalaceRewardInformations> councilPalaceRewards, Map<Integer, PersonalBonusTileInformations> personalBonusTiles)
	{
		this.developmentCardsBuilding.clear();
		this.developmentCardsBuilding.putAll(developmentCardsBuilding);
		this.developmentCardsCharacter.clear();
		this.developmentCardsCharacter.putAll(developmentCardsCharacter);
		this.developmentCardsTerritory.clear();
		this.developmentCardsTerritory.putAll(developmentCardsTerritory);
		this.developmentCardsVenture.clear();
		this.developmentCardsVenture.putAll(developmentCardsVenture);
		this.leaderCards.clear();
		this.leaderCards.putAll(leaderCards);
		this.excommunicationTiles.clear();
		this.excommunicationTiles.putAll(excommunicationTiles);
		this.councilPalaceRewards.clear();
		this.councilPalaceRewards.putAll(councilPalaceRewards);
		this.personalBonusTiles.clear();
		this.personalBonusTiles.putAll(personalBonusTiles);
	}

	public void updateGameStatus(GameInformations gameInformations, List<PlayerInformations> playersInformations)
	{
		this.setCurrentDevelopmentCardsBuilding(gameInformations.getDevelopmentCardsBuilding());
		this.setCurrentDevelopmentCardsCharacter(gameInformations.getDevelopmentCardsCharacter());
		this.setCurrentDevelopmentCardsTerritory(gameInformations.getDevelopmentCardsTerritory());
		this.setCurrentDevelopmentCardsVenture(gameInformations.getDevelopmentCardsVenture());
		this.setCurrentTurnOrder(gameInformations.getTurnOrder());
		this.setCurrentCouncilPalaceOrder(gameInformations.getCouncilPalaceOrder());
		for (PlayerInformations playerInformations : playersInformations) {
			if (this.currentPlayerData.get(playerInformations.getIndex()) != null) {
				this.currentPlayerData.get(playerInformations.getIndex()).setDevelopmentCardsBuilding(playerInformations.getDevelopmentCardsBuilding());
				this.currentPlayerData.get(playerInformations.getIndex()).setDevelopmentCardsCharacter(playerInformations.getDevelopmentCardsCharacter());
				this.currentPlayerData.get(playerInformations.getIndex()).setDevelopmentCardsTerritory(playerInformations.getDevelopmentCardsTerritory());
				this.currentPlayerData.get(playerInformations.getIndex()).setDevelopmentCardsVenture(playerInformations.getDevelopmentCardsVenture());
				this.currentPlayerData.get(playerInformations.getIndex()).setLeaderCardsStatuses(playerInformations.getLeaderCardsStatuses());
				this.currentPlayerData.get(playerInformations.getIndex()).setResourceAmounts(playerInformations.getResourceAmounts());
				this.currentPlayerData.get(playerInformations.getIndex()).setFamilyMembersPositions(playerInformations.getFamilyMembersPositions());
			}
		}
	}

	public static GameStatus getInstance()
	{
		return GameStatus.INSTANCE;
	}

	public Map<Integer, DevelopmentCardBuildingInformations> getDevelopmentCardsBuilding()
	{
		return this.developmentCardsBuilding;
	}

	public Map<Integer, DevelopmentCardCharacterInformations> getDevelopmentCardsCharacter()
	{
		return this.developmentCardsCharacter;
	}

	public Map<Integer, DevelopmentCardTerritoryInformations> getDevelopmentCardsTerritory()
	{
		return this.developmentCardsTerritory;
	}

	public Map<Integer, DevelopmentCardVentureInformations> getDevelopmentCardsVenture()
	{
		return this.developmentCardsVenture;
	}

	public Map<Integer, LeaderCardInformations> getLeaderCards()
	{
		return this.leaderCards;
	}

	public Map<Integer, ExcommunicationTileInformations> getExcommunicationTiles()
	{
		return this.excommunicationTiles;
	}

	public Map<Integer, CouncilPalaceRewardInformations> getCouncilPalaceRewards()
	{
		return this.councilPalaceRewards;
	}

	public Map<Integer, PersonalBonusTileInformations> getPersonalBonusTiles()
	{
		return this.personalBonusTiles;
	}

	public Map<Period, Integer> getCurrentExcommunicationTiles()
	{
		return this.currentExcommunicationTiles;
	}

	public void setCurrentExcommunicationTiles(Map<Period, Integer> currentExcommunicationTiles)
	{
		this.currentExcommunicationTiles.clear();
		this.currentExcommunicationTiles.putAll(currentExcommunicationTiles);
	}

	public Map<Integer, PlayerData> getCurrentPlayersData()
	{
		return this.currentPlayerData;
	}

	public void setCurrentPlayerData(Map<Integer, PlayerData> currentPlayerData)
	{
		this.currentPlayerData.clear();
		this.currentPlayerData.putAll(currentPlayerData);
	}

	public int getOwnPlayerIndex()
	{
		return this.ownPlayerIndex;
	}

	public void setOwnPlayerIndex(int ownPlayerIndex)
	{
		this.ownPlayerIndex = ownPlayerIndex;
	}

	public Map<Row, Integer> getCurrentDevelopmentCardsBuilding()
	{
		return this.currentDevelopmentCardsBuilding;
	}

	private void setCurrentDevelopmentCardsBuilding(Map<Row, Integer> currentDevelopmentCardsBuilding)
	{
		this.currentDevelopmentCardsBuilding.clear();
		this.currentDevelopmentCardsBuilding.putAll(currentDevelopmentCardsBuilding);
	}

	public Map<Row, Integer> getCurrentDevelopmentCardsCharacter()
	{
		return this.currentDevelopmentCardsCharacter;
	}

	private void setCurrentDevelopmentCardsCharacter(Map<Row, Integer> currentDevelopmentCardsCharacter)
	{
		this.currentDevelopmentCardsCharacter.clear();
		this.currentDevelopmentCardsCharacter.putAll(currentDevelopmentCardsCharacter);
	}

	public Map<Row, Integer> getCurrentDevelopmentCardsTerritory()
	{
		return this.currentDevelopmentCardsTerritory;
	}

	private void setCurrentDevelopmentCardsTerritory(Map<Row, Integer> currentDevelopmentCardsTerritory)
	{
		this.currentDevelopmentCardsTerritory.clear();
		this.currentDevelopmentCardsTerritory.putAll(currentDevelopmentCardsTerritory);
	}

	public Map<Row, Integer> getCurrentDevelopmentCardsVenture()
	{
		return this.currentDevelopmentCardsVenture;
	}

	private void setCurrentDevelopmentCardsVenture(Map<Row, Integer> currentDevelopmentCardsVenture)
	{
		this.currentDevelopmentCardsVenture.clear();
		this.currentDevelopmentCardsVenture.putAll(currentDevelopmentCardsVenture);
	}

	public Map<Integer, Integer> getCurrentTurnOrder()
	{
		return this.currentTurnOrder;
	}

	private void setCurrentTurnOrder(Map<Integer, Integer> currentTurnOrder)
	{
		this.currentTurnOrder.clear();
		this.currentTurnOrder.putAll(currentTurnOrder);
	}

	public Map<Integer, Integer> getCurrentCouncilPalaceOrder()
	{
		return this.currentCouncilPalaceOrder;
	}

	private void setCurrentCouncilPalaceOrder(Map<Integer, Integer> currentCouncilPalaceOrder)
	{
		this.currentCouncilPalaceOrder.clear();
		this.currentCouncilPalaceOrder.putAll(currentCouncilPalaceOrder);
	}

	public int getCurrentTurnPlayerIndex()
	{
		return this.currentTurnPlayerIndex;
	}

	public void setCurrentTurnPlayerIndex(int currentTurnPlayerIndex)
	{
		this.currentTurnPlayerIndex = currentTurnPlayerIndex;
	}

	public List<AvailableAction> getCurrentAvailableActions()
	{
		return this.currentAvailableActions;
	}

	public void setCurrentAvailableActions(List<AvailableAction> currentAvailableActions)
	{
		this.currentAvailableActions.clear();
		this.currentAvailableActions.addAll(currentAvailableActions);
	}

	public List<Integer> getAvailablePersonalBonusTiles()
	{
		return this.availablePersonalBonusTiles;
	}

	public void setAvailablePersonalBonusTiles(List<Integer> availablePersonalBonusTiles)
	{
		this.availablePersonalBonusTiles.clear();
		this.availablePersonalBonusTiles.addAll(availablePersonalBonusTiles);
	}

	public List<Integer> getAvailableLeaderCards()
	{
		return this.availableLeaderCards;
	}

	public void setAvailableLeaderCards(List<Integer> availableLeaderCards)
	{
		this.availableLeaderCards.clear();
		this.availableLeaderCards.addAll(availableLeaderCards);
	}
}