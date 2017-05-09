package it.polimi.ingsw.lim.client.gui;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerConnection implements Initializable
{
	@FXML private TextField ipTextField;
	@FXML private TextField portTextField;
	@FXML private TextField nameTextField;
	@FXML private RadioButton rmiRadioButton;
	@FXML private RadioButton socketRadioButton;
	@FXML private Button connectionButton;

	@FXML
	private void handleConnectionButtonAction()
	{
		String ip = this.ipTextField.getText().replace("^\\s+|\\s+$", "");
		String port = this.portTextField.getText().replace("^\\s+|\\s+$", "");
		String name = this.nameTextField.getText().replaceAll("^\\s+|\\s+$", "");
		if (ip.length() < 1) {
			this.ipTextField.clear();
			this.ipTextField.setPromptText("Insert an IP address");
			return;
		}
		if (port.length() < 1) {
			this.portTextField.clear();
			this.portTextField.setPromptText("Insert a port");
			return;
		}
		if (!CommonUtils.isInteger(port)) {
			this.portTextField.clear();
			this.portTextField.setPromptText("Insert a valid port");
			return;
		}
		if (name.length() < 1) {
			this.nameTextField.clear();
			this.nameTextField.setPromptText("Insert a name");
			return;
		}
		if (!name.matches("^[\\w\\-]{4,16}$")) {
			this.nameTextField.clear();
			this.nameTextField.setPromptText("Insert a valid name");
			return;
		}
		if (this.rmiRadioButton.isSelected() && this.socketRadioButton.isSelected()) {
			return;
		}
		Client.getInstance().setup(this.rmiRadioButton.isSelected() ? ConnectionType.RMI : ConnectionType.SOCKET, ip, Integer.parseInt(port), name);
	}

	@FXML
	private void handleRmiRadioButtonAction()
	{
		this.portTextField.setText("8080");
		this.connectionButton.setDisable(false);
	}

	@FXML
	private void handleSocketRadioButtonAction()
	{
		this.portTextField.setText("8081");
		this.connectionButton.setDisable(false);
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		this.connectionButton.prefWidthProperty().bind(((VBox) this.connectionButton.getParent()).widthProperty());
	}
}
