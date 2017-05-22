package it.polimi.ingsw.lim.server;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.database.Database;
import it.polimi.ingsw.lim.server.database.DatabaseSQLite;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.network.ConnectionHandler;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.logging.Level;

public class Server extends Instance
{
	private int rmiPort;
	private int socketPort;
	private String externalIp;
	private Database database;
	private final ExecutorService databaseSaver = Executors.newSingleThreadExecutor();
	private final ScheduledExecutorService databaseKeeper = Executors.newSingleThreadScheduledExecutor();
	private ConnectionHandler connectionHandler;
	private final ConcurrentLinkedQueue<Connection> connections = new ConcurrentLinkedQueue<>();
	private int connectionId;
	private final ConcurrentLinkedQueue<Room> rooms = new ConcurrentLinkedQueue<>();
	private int roomId;

	/**
	 * Initializes RMI and Socket Servers and, if successful, opens the main
	 * screen.
	 *
	 * @param rmiPort the port of the RMI Server.
	 * @param socketPort the port of the Socket Server.
	 */
	public synchronized void setup(int rmiPort, int socketPort)
	{
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(() -> {
			this.rmiPort = rmiPort;
			this.socketPort = socketPort;
			this.connectionId = 0;
			this.roomId = 0;
			this.database = new DatabaseSQLite(Database.DATABASE_FILE);
			this.database.createTables();
			this.databaseKeeper.scheduleAtFixedRate(() -> {
				try {
					this.database.getConnection().prepareStatement("SELECT 1").executeQuery();
				} catch (SQLException exception) {
					Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				}
			}, 0L, 60L, TimeUnit.SECONDS);
			this.getWindowInformations().getStage().getScene().getRoot().setDisable(true);
			this.connectionHandler = new ConnectionHandler(rmiPort, socketPort);
			this.connectionHandler.start();
		});
		executorService.shutdown();
	}

	/**
	 * Disconnects all Clients, waiting for every thread to terminate properly.
	 */
	@Override
	public synchronized void stop()
	{
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(() -> {
			if (this.connectionHandler != null) {
				Connection.broadcastChatMessage("Server shutting down...");
				Connection.disconnectAll();
				if (this.connectionHandler.getRegistry() != null) {
					try {
						this.connectionHandler.getRegistry().unbind("lorenzo-il-magnifico");
						UnicastRemoteObject.unexportObject(this.connectionHandler.getRegistry(), true);
						UnicastRemoteObject.unexportObject(this.connectionHandler.getLogin(), true);
					} catch (RemoteException | NotBoundException exception) {
						Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
					}
				}
				this.connectionHandler.end();
				try (Socket socket = new Socket("localhost", this.socketPort)) {
					socket.close();
				} catch (IOException exception) {
					Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				}
				try {
					this.connectionHandler.join();
				} catch (InterruptedException exception) {
					Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
					Thread.currentThread().interrupt();
				}
				this.databaseSaver.shutdown();
				this.databaseKeeper.shutdownNow();
				this.database.closeConnection();
				this.connectionHandler = null;
			}
			Platform.runLater(() -> CommonUtils.closeAllWindows(this.getWindowInformations().getStage()));
		});
		executorService.shutdown();
	}

	public static Server getInstance()
	{
		return (Server) Instance.getInstance();
	}

	public int getRmiPort()
	{
		return this.rmiPort;
	}

	public int getSocketPort()
	{
		return this.socketPort;
	}

	public String getExternalIp()
	{
		return this.externalIp;
	}

	public void setExternalIp(String externalIp)
	{
		this.externalIp = externalIp;
	}

	public Database getDatabase()
	{
		return this.database;
	}

	public ExecutorService getDatabaseSaver()
	{
		return this.databaseSaver;
	}

	public ConnectionHandler getConnectionHandler()
	{
		return this.connectionHandler;
	}

	public Queue<Connection> getConnections()
	{
		return this.connections;
	}

	public int getConnectionId()
	{
		return this.connectionId++;
	}

	public Queue<Room> getRooms()
	{
		return this.rooms;
	}

	public int getRoomId()
	{
		return this.roomId++;
	}
}
