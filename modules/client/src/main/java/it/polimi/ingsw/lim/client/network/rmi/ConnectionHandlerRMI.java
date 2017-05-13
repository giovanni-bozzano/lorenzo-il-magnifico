package it.polimi.ingsw.lim.client.network.rmi;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.network.Connection;
import it.polimi.ingsw.lim.client.network.ConnectionHandler;
import it.polimi.ingsw.lim.common.network.rmi.IClientSession;
import it.polimi.ingsw.lim.common.network.rmi.IHandshake;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ConnectionHandlerRMI extends ConnectionHandler
{
	private ServerSession serverSession;
	private IClientSession clientSession;

	@Override
	public void run()
	{
		try {
			IHandshake handshake = (IHandshake) Naming.lookup("rmi://" + Client.getInstance().getIp() + ":" + Client.getInstance().getPort() + "/lorenzo-il-magnifico");
			this.serverSession = new ServerSession();
			this.clientSession = handshake.sendLogin(Client.getInstance().getName(), CommonUtils.VERSION, this.serverSession);
		} catch (NotBoundException | MalformedURLException | RemoteException exception) {
			Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
			Client.getLogger().log(Level.INFO, "Could not connect to host", exception);
			return;
		}
		if (this.clientSession == null) {
			Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
			return;
		}
		Client.getInstance().setConnected(true);
		this.getHeartbeat().scheduleAtFixedRate(Connection::sendHeartbeat, 0L, 3000L, TimeUnit.MILLISECONDS);
		CommonUtils.setNewWindow("/fxml/SceneLobby.fxml", null, null, new Thread(Connection::sendRequestRoomList));
	}

	public void disconnect(boolean notifyServer)
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getLogger().log(Level.INFO, LogFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		this.getHeartbeat().shutdownNow();
		try {
			UnicastRemoteObject.unexportObject(this.serverSession, true);
		} catch (NoSuchObjectException exception) {
			Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
		if (!notifyServer) {
			try {
				this.clientSession.sendDisconnect();
			} catch (RemoteException exception) {
				Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
	}

	public IClientSession getClientSession()
	{
		return this.clientSession;
	}
}