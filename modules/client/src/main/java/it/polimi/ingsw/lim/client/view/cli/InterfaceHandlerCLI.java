package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.view.IInterfaceHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.network.AuthenticationInformations;
import it.polimi.ingsw.lim.common.network.AuthenticationInformationsGame;
import javafx.stage.Stage;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class InterfaceHandlerCLI implements IInterfaceHandler
{
	private static final Map<ActionType, CLIStatus> EXPECTED_ACTION_HANDLERS = new EnumMap<>(ActionType.class);

	static {
		InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_LORENZO_DE_MEDICI_LEADER, CLIStatus.CHOOSE_LORENZO_DE_MEDICI_LEADER);
		InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE, CLIStatus.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
		InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_REWARD_HARVEST, CLIStatus.CHOOSE_REWARD_HARVEST);
		InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD, CLIStatus.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD);
		InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_REWARD_PRODUCTION_START, CLIStatus.CHOOSE_REWARD_PRODUCTION_START);
		InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.put(ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER, CLIStatus.CHOOSE_REWARD_TEMPORARY_MODIFIER);
		InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.put(ActionType.PRODUCTION_TRADE, CLIStatus.PRODUCTION_TRADE);
	}

	@Override
	public void start()
	{
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public void start(Stage stage)
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void disconnect()
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.CONNECTION);
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public void stop()
	{
		Client.getInstance().getCliScanner().close();
		Client.getInstance().getCliListener().shutdownNow();
	}

	@Override
	public void handleRoomEntryOther(String name)
	{
		Client.getLogger().log(Level.INFO, "{0} connected", new Object[] { name });
	}

	@Override
	public void handleRoomExitOther(String name)
	{
		Client.getLogger().log(Level.INFO, "{0} disconnected", new Object[] { name });
	}

	@Override
	public void handleRoomTimer(int timer)
	{
		Client.getLogger().log(Level.INFO, "Game starts in: {0}", new Object[] { Integer.toString(timer) });
	}

	@Override
	public void handleDisconnectionLogMessage(String text)
	{
		Client.getLogger().log(Level.INFO, text);
	}

	@Override
	public void handleChatMessage(String text)
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void handleGameStarted()
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void handleGameLogMessage(String text)
	{
		Client.getLogger().log(Level.INFO, text);
	}

	@Override
	public void handleGameTimer(int timer)
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void handleGameDisconnectionOther(int playerIndex)
	{
		Client.getLogger().log(Level.INFO, GameStatus.getInstance().getCurrentPlayersData().get(playerIndex).getUsername() + " disconnected\"");
	}

	@Override
	public void handleGamePersonalBonusTileChoiceRequest()
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.PERSONAL_BONUS_TILE_CHOICE);
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public void handleGamePersonalBonusTileChoiceOther(int choicePlayerIndex)
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void handleGamePersonalBonusTileChosen(int choicePlayerIndex)
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void handleGameLeaderCardChoiceRequest()
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.LEADER_CARDS_CHOICE);
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public void handleGameExcommunicationChoiceRequest(Period period)
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.EXCOMMUNICATION_CHOICE);
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
	}

	@Override
	public void handleGameExcommunicationChoiceOther()
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void handleGameUpdateLog()
	{
		Client.getLogger().log(Level.INFO, "Your turn...");
	}

	@Override
	public void handleGameUpdate()
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.AVAILABLE_ACTIONS);
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public void handleGameUpdateExpectedAction()
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(InterfaceHandlerCLI.EXPECTED_ACTION_HANDLERS.get(GameStatus.getInstance().getCurrentExpectedAction().getActionType()));
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public void handleGameUpdateOtherTurnLog(int turnPlayerIndex)
	{
		Client.getLogger().log(Level.INFO, "{0}'s turn...", new Object[] { GameStatus.getInstance().getCurrentPlayersData().get(turnPlayerIndex).getUsername() });
	}

	@Override
	public void handleGameUpdateOther()
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.GAME_ENDED);
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
	}

	@Override
	public void handleGameEnded(Map<Integer, Integer> playersScores, Map<Integer, Integer> playerIndexesVictoryPointsRecord)
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.GAME_ENDED);
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public void handleConnectionError()
	{
		Client.getLogger().log(Level.INFO, "Could not connect to host");
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public void handleConnectionSuccess()
	{
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.AUTHENTICATION);
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public void handleAuthenticationFailed(String text)
	{
		Client.getLogger().log(Level.INFO, text);
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public void handleAuthenticationSuccess(AuthenticationInformations authenticationInformations)
	{
		Client.getLogger().log(Level.INFO, "Waiting for other players...");
	}

	@Override
	public void handleAuthenticationSuccessGameStarted(AuthenticationInformationsGame authenticationInformations)
	{
		if (authenticationInformations.isGameInitialized()) {
			GameStatus.getInstance().updateGameStatus(authenticationInformations.getGameInformations(), authenticationInformations.getPlayersInformations(), authenticationInformations.getOwnLeaderCardsHand());
			if (authenticationInformations.getTurnPlayerIndex() != authenticationInformations.getOwnPlayerIndex()) {
				GameStatus.getInstance().setCurrentTurnPlayerIndex(authenticationInformations.getTurnPlayerIndex());
				Client.getLogger().log(Level.INFO, "{0}'s turn...", new Object[] { GameStatus.getInstance().getCurrentPlayersData().get(authenticationInformations.getTurnPlayerIndex()).getUsername() });
			} else {
				GameStatus.getInstance().setCurrentAvailableActions(authenticationInformations.getAvailableActions());
				Client.getLogger().log(Level.INFO, "Your turn...");
			}
		}
	}

	@Override
	public void handleGameActionFailed(String text)
	{
		Client.getLogger().log(Level.INFO, "Action Failed: {0} ", new Object[] { text });
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance().execute());
	}
}