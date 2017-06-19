package it.polimi.ingsw.lim.server.network;

import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.player.Player;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Connection
{
	private String username;
	private final ScheduledExecutorService heartbeat = Executors.newSingleThreadScheduledExecutor();
	private Player player;

	protected Connection()
	{
	}

	protected Connection(String username)
	{
		this.username = username;
	}

	protected Connection(String username, Player player)
	{
		this.username = username;
		this.player = player;
	}

	public static void disconnectAll()
	{
		for (Connection connection : Server.getInstance().getConnections()) {
			connection.disconnect(true, null);
		}
		Server.getInstance().getConnections().clear();
	}

	public static void broadcastChatMessage(String text)
	{
		for (Connection connection : Server.getInstance().getConnections()) {
			if (connection.getUsername() != null && (connection.getPlayer() == null || connection.getPlayer().isOnline())) {
				connection.sendChatMessage(text);
			}
		}
	}

	public void disconnect(boolean flag, String message)
	{
		this.heartbeat.shutdownNow();
		Server.getInstance().getConnections().remove(this);
		if (this.player != null) {
			this.player.setOnline(false);
		}
		Room room = Room.getPlayerRoom(this);
		if (room == null) {
			return;
		}
		room.handlePlayerDisconnection(this);
	}

	public abstract void sendHeartbeat();

	public abstract void sendRoomEntryOther(String name);

	public abstract void sendRoomExitOther(String name);

	public abstract void sendRoomTimer(int timer);

	public abstract void sendLogMessage(String text);

	public abstract void sendChatMessage(String text);

	public abstract void sendGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, PlayerIdentification> playersData, int ownPlayerIndex);

	public abstract void sendGameDisconnectionOther(int playerIndex);

	public abstract void sendGamePersonalBonusTileChoiceRequest(List<Integer> availablePersonalBonusTiles);

	public abstract void sendGamePersonalBonusTileChoiceOther(int choicePlayerIndex);

	public abstract void sendGamePersonalBonusTileChosen(int choicePlayerIndex);

	public abstract void sendGameLeaderCardChoiceRequest(List<Integer> availableLeaderCards);

	public abstract void sendGameLeaderCardChosen(int choicePlayerIndex, boolean closeDialog);

	public abstract void sendGameUpdate(GameInformations gameInformations, List<PlayerInformations> playersInformations, List<Integer> ownLeaderCardsHand, List<AvailableAction> availableActions);

	public abstract void sendGameUpdateExpectedAction(GameInformations gameInformations, List<PlayerInformations> playersInformations, List<Integer> ownLeaderCardsHand, ExpectedAction expectedAction);

	public abstract void sendGameUpdateOtherTurn(GameInformations gameInformations, List<PlayerInformations> playersInformations, List<Integer> ownLeaderCardsHand, int turnPlayerIndex);

	public void handleRoomTimerRequest()
	{
		Room room = Room.getPlayerRoom(this);
		if (room == null) {
			return;
		}
		this.sendRoomTimer(room.getTimer());
	}

	public void handleChatMessage(String text)
	{
		Room room = Room.getPlayerRoom(this);
		if (room == null) {
			return;
		}
		for (Connection otherConnection : room.getPlayers()) {
			if (otherConnection != this && (otherConnection.getPlayer() == null || otherConnection.getPlayer().isOnline())) {
				otherConnection.sendChatMessage("[" + this.getUsername() + "]: " + text);
			}
		}
	}

	public void handleGamePersonalBonusTilePlayerChoice(int personalBonusTileIndex)
	{
		Room room = Room.getPlayerRoom(this);
		if (room == null) {
			return;
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return;
		}
		gameHandler.receivePersonalBonusTileChoice(this.getPlayer(), personalBonusTileIndex);
	}

	public void handleGameLeaderCardPlayerChoice(int leaderCardIndex)
	{
		Room room = Room.getPlayerRoom(this);
		if (room == null) {
			return;
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return;
		}
		gameHandler.receiveLeaderCardChoice(this.getPlayer(), leaderCardIndex);
	}

	public String getUsername()
	{
		return this.username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	protected ScheduledExecutorService getHeartbeat()
	{
		return this.heartbeat;
	}

	public Player getPlayer()
	{
		return this.player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}
}
