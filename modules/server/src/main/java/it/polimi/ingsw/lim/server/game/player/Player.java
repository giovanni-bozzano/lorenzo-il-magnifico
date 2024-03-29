package it.polimi.ingsw.lim.server.game.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionReward;
import it.polimi.ingsw.lim.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardVenture;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.network.Connection;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.logging.Level;

/**
 * <p>This class represents the user game information. It is used to store game
 * data concerning only this player.
 */
public class Player
{
	private static final Map<Integer, Integer> TERRITORY_SLOTS_CONDITIONS = new TerritorySlotsConditionsBuilder("/json/territory_slots_conditions.json").initialize();
	private static final Map<Period, Integer> EXCOMMUNICATIONS_CONDITIONS = new ExcommunicationsConditionsBuilder("/json/excommunications_conditions.json").initialize();

	static {
		Player.TERRITORY_SLOTS_CONDITIONS.put(0, 0);
		Player.TERRITORY_SLOTS_CONDITIONS.put(1, 0);
		Player.TERRITORY_SLOTS_CONDITIONS.put(2, 3);
		Player.TERRITORY_SLOTS_CONDITIONS.put(3, 7);
		Player.TERRITORY_SLOTS_CONDITIONS.put(4, 12);
		Player.TERRITORY_SLOTS_CONDITIONS.put(5, 18);
		Player.EXCOMMUNICATIONS_CONDITIONS.put(Period.FIRST, 3);
		Player.EXCOMMUNICATIONS_CONDITIONS.put(Period.SECOND, 4);
		Player.EXCOMMUNICATIONS_CONDITIONS.put(Period.THIRD, 5);
	}

	private final Room room;
	private final int index;
	private final PlayerCardHandler playerCardHandler = new PlayerCardHandler();
	private final PlayerResourceHandler playerResourceHandler = new PlayerResourceHandler(this, 3, 2, 2);
	private final Map<FamilyMemberType, BoardPosition> familyMembersPositions = new EnumMap<>(FamilyMemberType.class);
	private final List<Modifier> activeModifiers = new ArrayList<>();
	private final List<Modifier> temporaryModifiers = new ArrayList<>();
	private Connection connection;
	private PersonalBonusTile personalBonusTile;
	private int availableTurns = 4;
	private boolean isOnline = true;
	private ActionReward currentActionReward;
	private int currentProductionValue = 0;

	public Player(Connection connection, Room room, int index)
	{
		this.connection = connection;
		this.room = room;
		this.index = index;
		this.familyMembersPositions.put(FamilyMemberType.BLACK, BoardPosition.NONE);
		this.familyMembersPositions.put(FamilyMemberType.ORANGE, BoardPosition.NONE);
		this.familyMembersPositions.put(FamilyMemberType.WHITE, BoardPosition.NONE);
		this.familyMembersPositions.put(FamilyMemberType.NEUTRAL, BoardPosition.NONE);
	}

	public boolean isOccupyingBoardPosition(BoardPosition boardPosition)
	{
		for (BoardPosition playerBoardPosition : this.familyMembersPositions.values()) {
			if (playerBoardPosition == boardPosition) {
				return true;
			}
		}
		return false;
	}

	public boolean isTerritorySlotAvailable(int territorySlot)
	{
		return Player.TERRITORY_SLOTS_CONDITIONS.containsKey(territorySlot) && this.playerResourceHandler.getResources().get(ResourceType.MILITARY_POINT) >= Player.TERRITORY_SLOTS_CONDITIONS.get(territorySlot);
	}

	public boolean isExcommunicated(Period period)
	{
		return this.playerResourceHandler.getResources().get(ResourceType.FAITH_POINT) < Player.EXCOMMUNICATIONS_CONDITIONS.get(period);
	}

	public void convertToVictoryPoints(boolean countingCharacters, boolean countingTerritories, boolean countingVentures)
	{
		this.playerResourceHandler.addResource(ResourceType.VICTORY_POINT, (this.playerResourceHandler.getResources().get(ResourceType.COIN) + this.playerResourceHandler.getResources().get(ResourceType.SERVANT) + this.playerResourceHandler.getResources().get(ResourceType.STONE) + this.playerResourceHandler.getResources().get(ResourceType.WOOD)) / 5);
		if (countingCharacters) {
			this.playerResourceHandler.addResource(ResourceType.VICTORY_POINT, PlayerCardHandler.getDevelopmentCardsCharacterRewards().get(this.playerCardHandler.getDevelopmentCardsNumber(CardType.CHARACTER)));
		}
		if (countingTerritories) {
			this.playerResourceHandler.addResource(ResourceType.VICTORY_POINT, PlayerCardHandler.getDevelopmentCardsTerritoryRewards().get(this.playerCardHandler.getDevelopmentCardsNumber(CardType.TERRITORY)));
		}
		if (countingVentures) {
			for (DevelopmentCardVenture developmentCardVenture : this.playerCardHandler.getDevelopmentCards(CardType.VENTURE, DevelopmentCardVenture.class)) {
				this.playerResourceHandler.addResource(ResourceType.VICTORY_POINT, developmentCardVenture.getVictoryValue());
			}
		}
	}

	public void decreaseAvailableTurns()
	{
		this.availableTurns--;
	}

	public void resetAvailableTurns()
	{
		this.availableTurns = 4;
	}

	static Map<Integer, Integer> getTerritorySlotsConditions()
	{
		return Player.TERRITORY_SLOTS_CONDITIONS;
	}

	static Map<Period, Integer> getExcommunicationsConditions()
	{
		return Player.EXCOMMUNICATIONS_CONDITIONS;
	}

	public Connection getConnection()
	{
		return this.connection;
	}

	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}

	public Room getRoom()
	{
		return this.room;
	}

	public int getIndex()
	{
		return this.index;
	}

	public PlayerCardHandler getPlayerCardHandler()
	{
		return this.playerCardHandler;
	}

	public PlayerResourceHandler getPlayerResourceHandler()
	{
		return this.playerResourceHandler;
	}

	public Map<FamilyMemberType, BoardPosition> getFamilyMembersPositions()
	{
		return this.familyMembersPositions;
	}

	public List<Modifier> getActiveModifiers()
	{
		return this.activeModifiers;
	}

	public List<Modifier> getTemporaryModifiers()
	{
		return this.temporaryModifiers;
	}

	public PersonalBonusTile getPersonalBonusTile()
	{
		return this.personalBonusTile;
	}

	public void setPersonalBonusTile(PersonalBonusTile personalBonusTile)
	{
		this.personalBonusTile = personalBonusTile;
	}

	public int getAvailableTurns()
	{
		return this.availableTurns;
	}

	public boolean isOnline()
	{
		return this.isOnline;
	}

	public void setOnline(boolean online)
	{
		this.isOnline = online;
	}

	public ActionReward getCurrentActionReward()
	{
		return this.currentActionReward;
	}

	public void setCurrentActionReward(ActionReward actionReward)
	{
		this.currentActionReward = actionReward;
	}

	public int getCurrentProductionValue()
	{
		return this.currentProductionValue;
	}

	public void setCurrentProductionValue(int currentProductionValue)
	{
		this.currentProductionValue = currentProductionValue;
	}

	private static class TerritorySlotsConditionsBuilder
	{
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
		private static final Gson GSON = TerritorySlotsConditionsBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		TerritorySlotsConditionsBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		Map<Integer, Integer> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return TerritorySlotsConditionsBuilder.GSON.fromJson(reader, new TypeToken<Map<Integer, Integer>>()
				{
				}.getType());
			} catch (IOException exception) {
				Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new HashMap<>();
		}
	}

	private static class ExcommunicationsConditionsBuilder
	{
		private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
		private static final Gson GSON = ExcommunicationsConditionsBuilder.GSON_BUILDER.create();
		private final String jsonFile;

		ExcommunicationsConditionsBuilder(String jsonFile)
		{
			this.jsonFile = jsonFile;
		}

		Map<Period, Integer> initialize()
		{
			try (Reader reader = new InputStreamReader(Server.getInstance().getClass().getResourceAsStream(this.jsonFile), "UTF-8")) {
				return ExcommunicationsConditionsBuilder.GSON.fromJson(reader, new TypeToken<Map<Period, Integer>>()
				{
				}.getType());
			} catch (IOException exception) {
				Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return new EnumMap<>(Period.class);
		}
	}
}
