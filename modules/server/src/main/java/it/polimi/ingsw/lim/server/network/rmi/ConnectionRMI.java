package it.polimi.ingsw.lim.server.network.rmi;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.GameInformation;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformation;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.network.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.Connection;

import java.io.Serializable;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ConnectionRMI extends Connection
{
	private final ExecutorService rmiExecutor = Executors.newSingleThreadExecutor();
	private final IServerSession serverSession;

	public ConnectionRMI(String name, IServerSession serverSession)
	{
		super(name);
		this.serverSession = serverSession;
		this.getHeartbeat().scheduleAtFixedRate(this::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
	}

	ConnectionRMI(String name, IServerSession serverSession, Player player)
	{
		super(name);
		this.serverSession = serverSession;
		this.setPlayer(player);
		this.getHeartbeat().scheduleAtFixedRate(this::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
	}

	@Override
	public void sendChatMessage(String text)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendChatMessage(text);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void disconnect(boolean notifyClient, String message)
	{
		super.disconnect(notifyClient, null);
		for (ClientSession clientSession : Server.getInstance().getConnectionHandler().getLogin().getClientSessions()) {
			if (clientSession.getConnectionRMI() == this) {
				try {
					UnicastRemoteObject.unexportObject(clientSession, true);
				} catch (NoSuchObjectException exception) {
					Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				}
				Server.getInstance().getConnectionHandler().getLogin().getClientSessions().remove(clientSession);
				break;
			}
		}
		if (notifyClient) {
			if (message != null) {
				this.sendDisconnectionLogMessage(message);
			}
			try {
				this.serverSession.sendDisconnect();
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
			}
		}
		this.rmiExecutor.shutdownNow();
		Server.getInstance().getInterfaceHandler().displayToLog("RMI Player: " + this.getUsername() + " disconnected.");
	}

	@Override
	public void sendHeartbeat()
	{
		if (this.serverSession == null) {
			return;
		}
		try {
			this.serverSession.sendHeartbeat();
		} catch (RemoteException exception) {
			Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
			this.disconnect(false, null);
		}
	}

	@Override
	public void sendRoomEntryOther(String name)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendRoomEntryOther(name);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendRoomExitOther(String name)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendRoomExitOther(name);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendRoomTimer(int timer)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendRoomTimer(timer);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendDisconnectionLogMessage(String text)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendDisconnectionLogMessage(text);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, List<ResourceAmount>> councilPrivilegeRewards, Map<Integer, PlayerIdentification> playersData, int ownPlayerIndex)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendGameStarted(excommunicationTiles, councilPrivilegeRewards, playersData, ownPlayerIndex);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameLogMessage(String text)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendGameLogMessage(text);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameTimer(int timer)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendGameTimer(timer);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameDisconnectionOther(int playerIndex)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendGameDisconnectionOther(playerIndex);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGamePersonalBonusTileChoiceRequest(List<Integer> availablePersonalBonusTiles)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendGamePersonalBonusTileChoiceRequest(availablePersonalBonusTiles);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGamePersonalBonusTileChoiceOther(int choicePlayerIndex)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendGamePersonalBonusTileChoiceOther(choicePlayerIndex);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGamePersonalBonusTileChosen(int choicePlayerIndex)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendGamePersonalBonusTileChosen(choicePlayerIndex);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameLeaderCardChoiceRequest(List<Integer> availableLeaderCards)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendGameLeaderCardChoiceRequest(availableLeaderCards);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameExcommunicationChoiceRequest(Period period)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendGameExcommunicationChoiceRequest(period);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameExoommunicationChoiceOther()
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendGameExcommunicationChoiceOther();
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameUpdate(GameInformation gameInformation, List<PlayerInformation> playersInformation, Map<Integer, Boolean> ownLeaderCardsHand, Map<ActionType, List<Serializable>> availableActions)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendGameUpdate(gameInformation, playersInformation, ownLeaderCardsHand, availableActions);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameUpdateExpectedAction(GameInformation gameInformation, List<PlayerInformation> playersInformation, Map<Integer, Boolean> ownLeaderCardsHand, ExpectedAction expectedAction)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendGameUpdateExpectedAction(gameInformation, playersInformation, ownLeaderCardsHand, expectedAction);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameUpdateOtherTurn(GameInformation gameInformation, List<PlayerInformation> playersInformation, Map<Integer, Boolean> ownLeaderCardsHand, int turnPlayerIndex)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendGameUpdateOtherTurn(gameInformation, playersInformation, ownLeaderCardsHand, turnPlayerIndex);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameEnded(Map<Integer, Integer> playersScores, Map<Integer, Integer> playerIndexesVictoryPointsRecord)
	{
		this.rmiExecutor.execute(() -> {
			if (this.serverSession == null) {
				return;
			}
			try {
				this.serverSession.sendGameEnded(playersScores, playerIndexesVictoryPointsRecord);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}
}

