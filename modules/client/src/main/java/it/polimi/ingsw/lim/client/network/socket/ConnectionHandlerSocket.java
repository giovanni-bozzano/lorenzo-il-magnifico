package it.polimi.ingsw.lim.client.network.socket;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.cli.CLIListenerClient;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.gui.ControllerAuthentication;
import it.polimi.ingsw.lim.client.gui.ControllerRoom;
import it.polimi.ingsw.lim.client.network.ConnectionHandler;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.game.CouncilPalaceRewardInformations;
import it.polimi.ingsw.lim.common.game.RoomInformations;
import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformations;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardInformations;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardInformations;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketLogin;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRegistration;
import it.polimi.ingsw.lim.common.network.socket.packets.server.PacketGamePersonalBonusTilePlayerChoice;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.common.utils.WindowFactory;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
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
			Client.getDebugger().log(Level.INFO, "Could not connect to host.", exception);
			WindowFactory.enableAllWindows();
			WindowFactory.showDialog("Could not connect to host");
			Client.getLogger().log(Level.INFO, "Enter Connection Type...");
			Client.getLogger().log(Level.INFO, "1 - RMI");
			Client.getLogger().log(Level.INFO, "2 - Socket");
			return;
		}
		this.packetListener = new PacketListener();
		this.packetListener.start();
		this.getHeartbeat().scheduleAtFixedRate(this::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
		WindowFactory.getInstance().setNewWindow(Utils.SCENE_AUTHENTICATION, true);
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
			Client.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
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

	void handleDisconnectionLogMessage(String text)
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		Client.getDebugger().log(Level.INFO, text);
		this.sendDisconnectionAcknowledgement();
	}

	void handleAuthenticationConfirmation(List<DevelopmentCardInformations> developmentCardsInformations, List<LeaderCardInformations> leaderCardsInformations, List<ExcommunicationTileInformations> excommunicationTilesInformations, List<CouncilPalaceRewardInformations> councilPalaceRewardInformations, String username, RoomInformations roomInformations)
	{
		if (((CLIListenerClient) Client.getCliListener()).getStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerAuthentication.class)) {
			return;
		}
		GameStatus.getInstance().setup(developmentCardsInformations, leaderCardsInformations, excommunicationTilesInformations, councilPalaceRewardInformations);
		Client.getInstance().setUsername(username);
		WindowFactory.getInstance().setNewWindow(Utils.SCENE_ROOM, true, () -> Platform.runLater(() -> ((ControllerRoom) WindowFactory.getInstance().getCurrentWindow().getController()).setRoomInformations(roomInformations.getRoomType(), roomInformations.getPlayerNames())));
	}

	void handleAuthenticationFailure(String text)
	{
		if (((CLIListenerClient) Client.getCliListener()).getStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerAuthentication.class)) {
			return;
		}
		WindowFactory.enableAllWindows();
		WindowFactory.showDialog(text);
	}

	ObjectInputStream getIn()
	{
		return this.in;
	}
}
