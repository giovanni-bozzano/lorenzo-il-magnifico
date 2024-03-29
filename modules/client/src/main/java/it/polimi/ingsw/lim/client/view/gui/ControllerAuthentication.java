package it.polimi.ingsw.lim.client.view.gui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.WindowFactory;
import it.polimi.ingsw.lim.common.view.gui.CustomController;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAuthentication extends CustomController
{
	@FXML private JFXTextField usernameTextField;
	@FXML private JFXPasswordField passwordTextField;
	@FXML private JFXRadioButton normalRoomTypeRadioButton;
	@FXML private JFXRadioButton extendedRoomTypeRadioButton;
	@FXML private JFXButton loginButton;
	@FXML private JFXButton registerButton;
	@FXML private JFXDialog dialog;
	@FXML private JFXDialogLayout dialogLayout;
	@FXML private Label dialogLabel;
	@FXML private JFXButton dialogOkButton;

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		this.getStackPane().getChildren().remove(this.dialog);
		this.dialog.setTransitionType(DialogTransition.CENTER);
		this.dialog.setDialogContainer(this.getStackPane());
		this.loginButton.disableProperty().bind((this.usernameTextField.textProperty().isNotEmpty().and(this.passwordTextField.textProperty().isNotEmpty()).and(this.normalRoomTypeRadioButton.selectedProperty().or(this.extendedRoomTypeRadioButton.selectedProperty()))).not());
		this.registerButton.disableProperty().bind((this.usernameTextField.textProperty().isNotEmpty().and(this.passwordTextField.textProperty().isNotEmpty()).and(this.normalRoomTypeRadioButton.selectedProperty().or(this.extendedRoomTypeRadioButton.selectedProperty()))).not());
		Tooltip tooltip = new Tooltip("Valid username is 3 to 16 characters\nlong and alphanumeric.");
		WindowFactory.setTooltipOpenDelay(tooltip, 250.0D);
		WindowFactory.setTooltipVisibleDuration(tooltip, -1.0D);
		this.usernameTextField.setTooltip(tooltip);
	}

	@Override
	@PostConstruct
	public void setupGui()
	{
		this.getStackPane().getScene().getRoot().requestFocus();
		((StackPane) ((JFXRippler) ((AnchorPane) this.normalRoomTypeRadioButton.getChildrenUnmodifiable().get(1)).getChildren().get(0)).getChildren().get(0)).setPadding(new Insets(0.0D));
		this.normalRoomTypeRadioButton.getChildrenUnmodifiable().get(0).setTranslateX(10.0D);
		((StackPane) ((JFXRippler) ((AnchorPane) this.extendedRoomTypeRadioButton.getChildrenUnmodifiable().get(1)).getChildren().get(0)).getChildren().get(0)).setPadding(new Insets(0.0D));
		this.extendedRoomTypeRadioButton.getChildrenUnmodifiable().get(0).setTranslateX(10.0D);
		this.loginButton.setPrefWidth(((VBox) this.loginButton.getParent()).getWidth());
		this.registerButton.setPrefWidth(((VBox) this.registerButton.getParent()).getWidth());
		this.dialogLayout.setMinWidth(this.dialog.getWidth() - 20.0D);
		this.dialogLayout.setPrefWidth(this.dialog.getWidth() - 20.0D);
		this.dialogLayout.setMaxSize(this.dialog.getWidth() - 20.0D, this.dialog.getHeight() - 20.0D);
		this.dialogOkButton.setPrefWidth(((VBox) this.dialogOkButton.getParent()).getWidth());
	}

	@FXML
	public void handleLoginButtonAction()
	{
		String username = this.usernameTextField.getText().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		if (!username.matches(CommonUtils.REGEX_USERNAME)) {
			return;
		}
		this.getStage().getScene().getRoot().setDisable(true);
		Client.getInstance().getConnectionHandler().sendLogin(username, this.passwordTextField.getText(), this.normalRoomTypeRadioButton.isSelected() ? RoomType.NORMAL : RoomType.EXTENDED);
	}

	@FXML
	public void handleRegisterButtonAction()
	{
		String username = this.usernameTextField.getText().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		if (!username.matches(CommonUtils.REGEX_USERNAME)) {
			return;
		}
		this.getStage().getScene().getRoot().setDisable(true);
		Client.getInstance().getConnectionHandler().sendRegistration(username, this.passwordTextField.getText(), this.normalRoomTypeRadioButton.isSelected() ? RoomType.NORMAL : RoomType.EXTENDED);
	}

	@FXML
	public void handleDialogOkButtonAction()
	{
		this.dialog.close();
		this.getStackPane().getScene().getRoot().requestFocus();
	}

	void showDialog(String message)
	{
		this.getStackPane().getScene().getRoot().requestFocus();
		this.dialogLabel.setText(message);
		this.dialog.show();
	}
}
