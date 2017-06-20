package it.polimi.ingsw.lim.client.network;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.client.gui.ControllerConnection;
import it.polimi.ingsw.lim.client.gui.ControllerGame;
import it.polimi.ingsw.lim.client.gui.ControllerRoom;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.common.utils.WindowFactory;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;

public abstract class ConnectionHandler extends Thread
{
	private final ScheduledExecutorService heartbeat = Executors.newSingleThreadScheduledExecutor();

	@Override
	public abstract void run();

	public static void printConnectionError()
	{
		WindowFactory.getInstance().enableWindow();
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE) {
			Platform.runLater(() -> ((ControllerConnection) WindowFactory.getInstance().getCurrentWindow()).showDialog("Could not connect to host"));
		} else {
			Client.getLogger().log(Level.INFO, "Could not connect to host");
		}
		if (Client.getInstance().getCliStatus() != CLIStatus.NONE) {
			Client.getLogger().log(Level.INFO, "Enter Connection Type...");
			Client.getLogger().log(Level.INFO, "1 - RMI");
			Client.getLogger().log(Level.INFO, "2 - Socket");
		}
	}

	public void disconnect(boolean notifyServer)
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		this.getHeartbeat().shutdownNow();
	}

	/**
	 * <p>Tries to send an heartbeat to check the connection status.
	 */
	public synchronized void sendHeartbeat()
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * <p>Tries to login with username, password and Client version, sending the
	 * desired {@link RoomType}.
	 *
	 * @param username the username.
	 * @param password the password.
	 * @param roomType the desired {@link RoomType}.
	 */
	public synchronized void sendLogin(String username, String password, RoomType roomType)
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		WindowFactory.getInstance().disableWindow();
	}

	/**
	 * <p>Tries to register with username, password and Client version, sending
	 * the desired {@link RoomType}.
	 *
	 * @param username the username.
	 * @param password the password.
	 * @param roomType the desired {@link RoomType}.
	 */
	public synchronized void sendRegistration(String username, String password, RoomType roomType)
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		WindowFactory.getInstance().disableWindow();
	}

	public synchronized void sendRoomTimerRequest()
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
	}

	public synchronized void sendChatMessage(String text)
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
	}

	public synchronized void sendGamePersonalBonusTilePlayerChoice(int personalBonusTileIndex)
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
	}

	public synchronized void sendGameLeaderCardPlayerChoice(int leaderCardIndex)
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
	}

	public void handleRoomEntryOther(String name)
	{
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerRoom.class)) {
			return;
		}
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE) {
			Platform.runLater(() -> ((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).getPlayersListView().getItems().add(name));
		} else {
			Client.getLogger().log(Level.INFO, "{0} connected", new Object[] { name });
		}
	}

	public void handleRoomExitOther(String name)
	{
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerRoom.class)) {
			return;
		}
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE) {
			Platform.runLater(() -> {
				((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).getPlayersListView().getItems().remove(name);
				if (((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).getPlayersListView().getItems().size() < 2) {
					((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).getTimerLabel().setText("Waiting for other players...");
				}
			});
		} else {
			Client.getLogger().log(Level.INFO, "{0} disconnected", new Object[] { name });
		}
	}

	public void handleRoomTimer(int timer)
	{
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerRoom.class)) {
			return;
		}
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE) {
			Platform.runLater(() -> ((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).getTimerLabel().setText("Game starts in: " + timer));
		} else {
			Client.getLogger().log(Level.INFO, "Game starts in: {0}", new Object[] { Integer.toString(timer) });
		}
	}

	public void handleChatMessage(String text)
	{
		if (!WindowFactory.getInstance().isWindowOpen(ControllerRoom.class) && !WindowFactory.getInstance().isWindowOpen(ControllerGame.class)) {
			return;
		}
		if (WindowFactory.getInstance().isWindowOpen(ControllerRoom.class)) {
			Platform.runLater(() -> ((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).getChatTextArea().appendText((((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).getChatTextArea().getText().length() < 1 ? "" : '\n') + text));
		} else {
			Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getChatTextArea().appendText((((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getChatTextArea().getText().length() < 1 ? "" : '\n') + text));
		}
	}

	public void handleLogMessage(String text)
	{
		Client.getDebugger().log(Level.INFO, text);
	}

	public void handleGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, PlayerIdentification> playersIdentifications, int ownPlayerIndex)
	{
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerRoom.class)) {
			return;
		}
		GameStatus.getInstance().setCurrentExcommunicationTiles(excommunicationTiles);
		Map<Integer, PlayerData> playersData = new HashMap<>();
		for (Entry<Integer, PlayerIdentification> entry : playersIdentifications.entrySet()) {
			playersData.put(entry.getKey(), new PlayerData(entry.getValue().getUsername(), entry.getValue().getColor()));
		}
		GameStatus.getInstance().setCurrentPlayerData(playersData);
		GameStatus.getInstance().setOwnPlayerIndex(ownPlayerIndex);
		WindowFactory.getInstance().setNewWindow(Utils.SCENE_GAME);
	}

	public void handleGameDisconnectionOther(int playerIndex)
	{
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerRoom.class)) {
			return;
		}
	}

	public void handleGamePersonalBonusTileChoiceRequest(List<Integer> availablePersonalBonusTiles)
	{
		try {
			WindowFactory.WINDOW_OPENING_SEMAPHORE.acquire();
		} catch (InterruptedException exception) {
			Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerGame.class)) {
			return;
		}
		WindowFactory.WINDOW_OPENING_SEMAPHORE.release();
		GameStatus.getInstance().setAvailablePersonalBonusTiles(availablePersonalBonusTiles);
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE) {
			Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).showPersonalBonusTilesChoiceDialog());
		}
	}

	public void handleGamePersonalBonusTileChoiceOther(int choicePlayerIndex)
	{
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerGame.class)) {
			return;
		}
		Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().appendText((((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).getGameLogTextArea().getText().length() < 1 ? "" : '\n') + GameStatus.getInstance().getCurrentPlayersData().get(choicePlayerIndex).getUsername() + " is choosing a personal bonus tile"));
	}

	public void handleGamePersonalBonusTileChosen(int choicePlayerIndex)
	{
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerGame.class)) {
			return;
		}
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && choicePlayerIndex == GameStatus.getInstance().getOwnPlayerIndex()) {
			Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).closePersonalBonusTilesChoiceDialog());
		}
	}

	public void handleGameLeaderCardChoiceRequest(List<Integer> availableLeaderCards)
	{
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerGame.class)) {
			return;
		}
		GameStatus.getInstance().setAvailableLeaderCards(availableLeaderCards);
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE) {
			Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).showLeaderCardsChoiceDialog());
		}
	}

	public void handleGameLeaderCardChosen(int choicePlayerIndex, boolean closeDialog)
	{
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerGame.class)) {
			return;
		}
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && choicePlayerIndex == GameStatus.getInstance().getOwnPlayerIndex() && closeDialog) {
			Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).closeLeaderCardsChoiceDialog());
		}
	}

	public void handleGameUpdate(GameInformations gameInformations, List<PlayerInformations> playersInformations, List<Integer> ownLeaderCardsHand, Map<ActionType, List<AvailableAction>> availableActions)
	{
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerGame.class)) {
			return;
		}
		GameStatus.getInstance().updateGameStatus(gameInformations, playersInformations, ownLeaderCardsHand);
		GameStatus.getInstance().setCurrentAvailableActions(availableActions);
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE) {
			Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).setOwnTurn());
		} else {
			Client.getLogger().log(Level.INFO, "Your turn...");
		}
	}

	public void handleGameUpdateExpectedAction(GameInformations gameInformations, List<PlayerInformations> playersInformations, List<Integer> ownLeaderCardsHand, ExpectedAction expectedAction)
	{
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerGame.class)) {
			return;
		}
		GameStatus.getInstance().updateGameStatus(gameInformations, playersInformations, ownLeaderCardsHand);
	}

	public void handleGameUpdateOtherTurn(GameInformations gameInformations, List<PlayerInformations> playersInformations, List<Integer> ownLeaderCardsHand, int turnPlayerIndex)
	{
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerGame.class)) {
			return;
		}
		GameStatus.getInstance().updateGameStatus(gameInformations, playersInformations, ownLeaderCardsHand);
		GameStatus.getInstance().setCurrentTurnPlayerIndex(turnPlayerIndex);
		if (Client.getInstance().getCliStatus() == CLIStatus.NONE) {
			Platform.runLater(() -> ((ControllerGame) WindowFactory.getInstance().getCurrentWindow()).setOtherTurn());
		} else {
			Client.getLogger().log(Level.INFO, "{0}'s turn...", new Object[] { GameStatus.getInstance().getCurrentPlayersData().get(turnPlayerIndex).getUsername() });
		}
	}

	protected ScheduledExecutorService getHeartbeat()
	{
		return this.heartbeat;
	}
}
