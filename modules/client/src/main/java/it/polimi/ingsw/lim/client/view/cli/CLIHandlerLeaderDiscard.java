package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationLeaderDiscard;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerLeaderDiscard implements ICLIHandler
{
	private final Map<Integer, Integer> leaderCards = new HashMap<>();

	@Override
	public void execute()
	{
		this.showHandLeaderCards();
		this.askDiscardLeaderCardIndex();
	}

	@Override
	public CLIHandlerLeaderDiscard newInstance()
	{
		return new CLIHandlerLeaderDiscard();
	}

	private void showHandLeaderCards()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nEnter Leader Card...");
		int index = 1;
		for (int leaderCard : GameStatus.getInstance().getCurrentOwnLeaderCardsHand().keySet()) {
			stringBuilder.append(Utils.createListElement(index, GameStatus.getInstance().getLeaderCards().get(leaderCard).getInformation()));
			this.leaderCards.put(index, leaderCard);
			index++;
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askDiscardLeaderCardIndex()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.leaderCards.containsKey(Integer.parseInt(input)));
		Client.getInstance().setCliStatus(CLIStatus.AVAILABLE_ACTIONS);
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationLeaderDiscard(this.leaderCards.get(Integer.parseInt(input))));
	}
}
