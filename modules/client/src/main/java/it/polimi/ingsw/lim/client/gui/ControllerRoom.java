package it.polimi.ingsw.lim.client.gui;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.network.Connection;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerRoom implements Initializable
{
	@FXML private Label roomNameLabel;
	@FXML private TextArea chatTextArea;
	@FXML private ListView<String> playersListView;
	@FXML private Button exitButton;

	@FXML
	private void handleChatTextAreaAction(ActionEvent event)
	{
		String text = ((TextField) event.getSource()).getText().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		if (text.length() < 1) {
			return;
		}
		((TextField) event.getSource()).clear();
		Connection.sendChatMessage(text);
		if (this.chatTextArea.getText().length() < 1) {
			this.chatTextArea.appendText("[" + Client.getInstance().getUsername() + "]: " + text);
		} else {
			this.chatTextArea.appendText("\n[" + Client.getInstance().getUsername() + "]: " + text);
		}
	}

	@FXML
	private void handleExitButtonAction()
	{
		Connection.sendRoomExit();
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		this.playersListView.setCellFactory(param -> new ListCell<String>()
		{
			@Override
			protected void updateItem(String playerName, boolean empty)
			{
				super.updateItem(playerName, empty);
				this.setDisable(true);
				if (empty || playerName == null) {
					this.setText(null);
				} else {
					this.setText(playerName);
				}
			}
		});
		this.exitButton.prefWidthProperty().bind(((VBox) this.exitButton.getParent()).widthProperty());
	}

	public void setRoomInformations(String name, List<String> playerNames)
	{
		this.roomNameLabel.setText(name);
		this.playersListView.getItems().addAll(playerNames);
	}

	public ListView<String> getPlayersListView()
	{
		return this.playersListView;
	}

	public TextArea getChatTextArea()
	{
		return this.chatTextArea;
	}
}
