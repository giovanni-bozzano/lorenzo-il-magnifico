package it.polimi.ingsw.lim.client.network.socket;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.client.network.ConnectionHandler;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformation;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.network.AuthenticationInformation;
import it.polimi.ingsw.lim.common.network.AuthenticationInformationGame;
import it.polimi.ingsw.lim.common.network.socket.AuthenticationInformationLobbySocket;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.client.*;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ConnectionHandlerSocket extends ConnectionHandler
{
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private PacketListener packetListener;

	@Override
	public void run()
	{
		try {
			this.socket = new Socket(Client.getInstance().getIp(), Client.getInstance().getPort());
			this.socket.setSoTimeout(12000);
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.in = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException exception) {
			Client.getDebugger().log(Level.OFF, "Could not connect to host.", exception);
			Client.getInstance().getInterfaceHandler().handleConnectionError();
			return;
		}
		this.packetListener = new PacketListener();
		this.packetListener.start();
		this.getHeartbeat().scheduleAtFixedRate(this::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
		Client.getInstance().getInterfaceHandler().handleConnectionSuccess();
	}

	@Override
	public synchronized void disconnect(boolean notifyServer)
	{
		super.disconnect(notifyServer);
		if (this.packetListener != null) {
			this.packetListener.end();
		}
		try {
			if (this.in != null) {
				this.in.close();
			}
			if (this.out != null) {
				this.out.close();
			}
			if (this.socket != null) {
				this.socket.close();
			}
		} catch (IOException exception) {
			Client.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public synchronized void sendHeartbeat()
	{
		super.sendHeartbeat();
		new Packet(PacketType.HEARTBEAT).send(this.out);
	}

	@Override
	public synchronized void sendLogin(String username, String password, RoomType roomType)
	{
		super.sendLogin(username, password, roomType);
		new PacketLogin(username, CommonUtils.encrypt(password), roomType).send(this.out);
	}

	@Override
	public synchronized void sendRegistration(String username, String password, RoomType roomType)
	{
		super.sendRegistration(username, password, roomType);
		new PacketRegistration(username, CommonUtils.encrypt(password), roomType).send(this.out);
	}

	@Override
	public synchronized void sendRoomTimerRequest()
	{
		super.sendRoomTimerRequest();
		new Packet(PacketType.ROOM_TIMER_REQUEST).send(this.out);
	}

	@Override
	public synchronized void sendChatMessage(String text)
	{
		super.sendChatMessage(text);
		new PacketChatMessage(text).send(this.out);
	}

	@Override
	public synchronized void sendGamePersonalBonusTilePlayerChoice(int personalBonusTileIndex)
	{
		super.sendGamePersonalBonusTilePlayerChoice(personalBonusTileIndex);
		new PacketGamePersonalBonusTilePlayerChoice(personalBonusTileIndex).send(this.out);
	}

	@Override
	public synchronized void sendGameLeaderCardPlayerChoice(int leaderCardIndex)
	{
		super.sendGameLeaderCardPlayerChoice(leaderCardIndex);
		new PacketGameLeaderCardPlayerChoice(leaderCardIndex).send(this.out);
	}

	@Override
	public synchronized void sendGameExcommunicationPlayerChoice(boolean excommunicated)
	{
		super.sendGameExcommunicationPlayerChoice(excommunicated);
		new PacketGameExcommunicationPlayerChoice(excommunicated).send(this.out);
	}

	@Override
	public synchronized void sendGameAction(ActionInformation action)
	{
		super.sendGameAction(action);
		new PacketGameAction(action).send(this.out);
	}

	@Override
	public synchronized void sendGoodGame(int playerIndex)
	{
		super.sendGoodGame(playerIndex);
		new PacketGoodGame(playerIndex).send(this.out);
	}

	@Override
	public void handleDisconnectionLogMessage(String text)
	{
		super.handleDisconnectionLogMessage(text);
		this.sendDisconnectionAcknowledgement();
	}

	private synchronized void sendDisconnectionAcknowledgement()
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		new Packet(PacketType.DISCONNECTION_ACKNOWLEDGEMENT).send(this.out);
	}

	void handleAuthenticationConfirmation(AuthenticationInformation authenticationInformation)
	{
		GameStatus.getInstance().setup(authenticationInformation.getDevelopmentCardsInformation(), authenticationInformation.getLeaderCardsInformation(), authenticationInformation.getExcommunicationTilesInformation(), authenticationInformation.getPersonalBonusTilesInformation());
		if (!authenticationInformation.isGameStarted()) {
			Client.getInstance().setUsername(((AuthenticationInformationLobbySocket) authenticationInformation).getUsername());
			Client.getInstance().getInterfaceHandler().handleAuthenticationSuccess(authenticationInformation);
		} else {
			GameStatus.getInstance().setCurrentExcommunicationTiles(((AuthenticationInformationGame) authenticationInformation).getExcommunicationTiles());
			GameStatus.getInstance().setCurrentCouncilPrivilegeRewards(((AuthenticationInformationGame) authenticationInformation).getCouncilPrivilegeRewards());
			Map<Integer, PlayerData> playersData = new HashMap<>();
			for (Entry<Integer, PlayerIdentification> entry : ((AuthenticationInformationGame) authenticationInformation).getPlayersIdentifications().entrySet()) {
				playersData.put(entry.getKey(), new PlayerData(entry.getValue().getUsername(), entry.getValue().getColor()));
			}
			GameStatus.getInstance().setCurrentPlayerData(playersData);
			GameStatus.getInstance().setOwnPlayerIndex(((AuthenticationInformationGame) authenticationInformation).getOwnPlayerIndex());
			Client.getInstance().setUsername(playersData.get(((AuthenticationInformationGame) authenticationInformation).getOwnPlayerIndex()).getUsername());
			Client.getInstance().getInterfaceHandler().handleAuthenticationSuccessGameStarted((AuthenticationInformationGame) authenticationInformation);
		}
	}

	void handleGameActionFailed(String text)
	{
		Client.getInstance().getInterfaceHandler().handleGameActionFailed(text);
	}

	ObjectInputStream getIn()
	{
		return this.in;
	}
}
