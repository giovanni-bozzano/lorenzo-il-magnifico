package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerAvailableActions implements ICLIHandler
{
	private static final Map<CLIStatus, String> ACTION_NAMES = new EnumMap<>(CLIStatus.class);

	static {
		ACTION_NAMES.put(CLIStatus.SHOW_BOARD_DEVELOPMENT_CARDS, "Show Development Cards");
		ACTION_NAMES.put(CLIStatus.SHOW_OWN_LEADER_CARDS, "Show Leaders");
		ACTION_NAMES.put(CLIStatus.COUNCIL_PALACE, "Council Palace");
		ACTION_NAMES.put(CLIStatus.HARVEST, "Harvest");
		ACTION_NAMES.put(CLIStatus.MARKET, "Market");
		ACTION_NAMES.put(CLIStatus.PICK_DEVELOPMENT_CARD, "Pick Development Card");
		ACTION_NAMES.put(CLIStatus.PRODUCTION_START, "Start Production");
		ACTION_NAMES.put(CLIStatus.LEADER_ACTIVATE, "Activate Leader");
		ACTION_NAMES.put(CLIStatus.LEADER_DISCARD, "Discard Leader");
		ACTION_NAMES.put(CLIStatus.LEADER_PLAY, "Play Leader");
	}

	private final Map<Integer, CLIStatus> availableActions = new HashMap<>();

	@Override
	public void execute()
	{
		this.availableActions.put(1, CLIStatus.SHOW_BOARD_DEVELOPMENT_CARDS);
		this.availableActions.put(2, CLIStatus.SHOW_OWN_LEADER_CARDS);
		this.showAvailableActions();
		this.askAction();
	}

	@Override
	public CLIHandlerAvailableActions newInstance()
	{
		return new CLIHandlerAvailableActions();
	}

	private void showAvailableActions()
	{
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.COUNCIL_PALACE).isEmpty()) {
			this.availableActions.put(3, CLIStatus.COUNCIL_PALACE);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.HARVEST).isEmpty()) {
			this.availableActions.put(this.availableActions.size() + 1, CLIStatus.HARVEST);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.MARKET).isEmpty()) {
			this.availableActions.put(this.availableActions.size() + 1, CLIStatus.MARKET);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD).isEmpty()) {
			this.availableActions.put(this.availableActions.size() + 1, CLIStatus.PICK_DEVELOPMENT_CARD);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PRODUCTION_START).isEmpty()) {
			this.availableActions.put(this.availableActions.size() + 1, CLIStatus.PRODUCTION_START);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_ACTIVATE).isEmpty()) {
			this.availableActions.put(this.availableActions.size() + 1, CLIStatus.LEADER_ACTIVATE);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_DISCARD).isEmpty()) {
			this.availableActions.put(this.availableActions.size() + 1, CLIStatus.LEADER_DISCARD);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_PLAY).isEmpty()) {
			this.availableActions.put(this.availableActions.size() + 1, CLIStatus.LEADER_PLAY);
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Action choice...");
		for (Entry<Integer, CLIStatus> availableAction : this.availableActions.entrySet()) {
			stringBuilder.append(Utils.createListElement(availableAction.getKey(), CLIHandlerAvailableActions.ACTION_NAMES.get(availableAction.getValue())));
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askAction()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableActions.containsKey(Integer.parseInt(input)));
		Client.getInstance().setCliStatus(this.availableActions.get(Integer.parseInt(input)));
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).execute());
	}
}