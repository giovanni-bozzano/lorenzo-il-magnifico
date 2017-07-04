package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerChooseRewardCouncilPrivilege implements ICLIHandler
{
	private final Map<Integer, Integer> availableCouncilPrivilegeRewards = new HashMap<>();
	private final List<Integer> chosenCouncilPrivilegeRewards = new ArrayList<>();

	@Override
	public void execute()
	{
		this.showResourceChoices();
		this.askResourceChoice();
	}

	@Override
	public CLIHandlerChooseRewardCouncilPrivilege newInstance()
	{
		return new CLIHandlerChooseRewardCouncilPrivilege();
	}

	private void showResourceChoices()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nEnter Council Privilege rewards...");
		int index = 1;
		for (Entry<Integer, List<ResourceAmount>> councilPrivilegeReward : GameStatus.getInstance().getCurrentCouncilPrivilegeRewards().entrySet()) {
			if (!this.availableCouncilPrivilegeRewards.containsValue(councilPrivilegeReward.getKey())) {
				stringBuilder.append(Utils.createListElement(index, ResourceAmount.getResourcesInformations(councilPrivilegeReward.getValue(), false)));
				this.availableCouncilPrivilegeRewards.put(index, councilPrivilegeReward.getKey());
				index++;
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askResourceChoice()
	{
		int index = 0;
		do {
			String input;
			do {
				input = Client.getInstance().getCliScanner().nextLine();
			}
			while (!CommonUtils.isInteger(input) || !this.availableCouncilPrivilegeRewards.containsKey(Integer.parseInt(input)));
			this.chosenCouncilPrivilegeRewards.add(Integer.parseInt(input));
			this.availableCouncilPrivilegeRewards.remove(Integer.parseInt(input));
			Client.getLogger().log(Level.INFO, "{0} registered", new Object[] { CommonUtils.isInteger(input) });
			index++;
		}
		while (index < ((ExpectedActionChooseRewardCouncilPrivilege) GameStatus.getInstance().getCurrentExpectedAction()).getCouncilPrivilegesNumber());
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsChooseRewardCouncilPrivilege(this.chosenCouncilPrivilegeRewards));
	}
}
