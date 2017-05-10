package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.client.network.rmi.ConnectionHandlerRMI;
import it.polimi.ingsw.lim.client.network.socket.ConnectionHandlerSocket;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.WindowInformations;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

public class Client extends Instance
{
	private ConnectionType connectionType;
	private String ip;
	private int port;
	private String name;
	private ConnectionHandlerRMI connectionHandlerRMI;
	private ConnectionHandlerSocket connectionHandlerSocket;
	private boolean isConnected;

	/**
	 * Tries to connect to an RMI or Socket Server and, if successful, opens the lobby screen.
	 * @param connectionType the type of connection used.
	 * @param ip the IP address of the Server.
	 * @param port the port of the Server.
	 * @param name the name of the Player.
	 */
	public void setup(ConnectionType connectionType, String ip, int port, String name)
	{
		this.connectionType = connectionType;
		this.ip = ip;
		this.port = port;
		this.name = name;
		this.isConnected = false;
		this.getWindowInformations().getStage().getScene().getRoot().setDisable(true);
		if (connectionType == ConnectionType.RMI) {
			this.connectionHandlerRMI = new ConnectionHandlerRMI();
			this.connectionHandlerRMI.start();
		} else {
			this.connectionHandlerSocket = new ConnectionHandlerSocket();
			this.connectionHandlerSocket.start();
		}
	}

	/**
	 * Disconnects from the Server and closes all windows.
	 */
	@Override
	public void stop()
	{
		this.disconnect(true, false);
	}

	/**
	 * Disconnects from the Server.
	 * If the Client is stopping, it closes all the windows, otherwise it closes all the current windows and opens the connection window.
	 * @param isStopping the flag to check whether the Client has to be closed or not.
	 * @param isBeingKicked the flag to check wether the Client has to notify the Server or not.
	 */
	public void disconnect(boolean isStopping, boolean isBeingKicked)
	{
		if (this.isConnected) {
			this.isConnected = false;
			if (isBeingKicked) {
				Client.getLogger().log(Level.INFO, "The Server closed the connection.");
			}
			if (this.connectionHandlerRMI != null) {
				this.connectionHandlerRMI.disconnect(isBeingKicked);
			} else if (this.connectionHandlerSocket != null) {
				this.connectionHandlerSocket.disconnect(isBeingKicked);
			}
			Client.getLogger().log(Level.INFO, "Connection closed.");
		}
		if (isStopping) {
			Platform.runLater(() -> CommonUtils.closeAllWindows(this.getWindowInformations().getStage()));
		} else {
			Platform.runLater(() -> {
				FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fxml/SceneConnection.fxml"));
				try {
					Parent parent = fxmlLoader.load();
					Stage stage = new Stage();
					stage.setScene(new Scene(parent));
					stage.sizeToScene();
					stage.setResizable(false);
					CommonUtils.closeAllWindows(this.getWindowInformations().getStage());
					this.setWindowInformations(new WindowInformations(fxmlLoader.getController(), stage));
					stage.show();
				} catch (IOException exception) {
					Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				}
			});
		}
	}

	public static Client getInstance()
	{
		return (Client) Instance.getInstance();
	}

	public ConnectionType getConnectionType()
	{
		return this.connectionType;
	}

	public String getIp()
	{
		return this.ip;
	}

	public int getPort()
	{
		return this.port;
	}

	public String getName()
	{
		return this.name;
	}

	public ConnectionHandlerRMI getConnectionHandlerRMI()
	{
		return this.connectionHandlerRMI;
	}

	public ConnectionHandlerSocket getConnectionHandlerSocket()
	{
		return this.connectionHandlerSocket;
	}

	public void setConnected(boolean isConnected)
	{
		this.isConnected = isConnected;
	}
}
