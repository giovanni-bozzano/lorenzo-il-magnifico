package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerShowOwnLeaders implements ICLIHandler
{
	private static final Map<Integer, Map<Integer, Boolean>> LEADER_CARDS_CHOICE = new HashMap<>();

	static {
		LEADER_CARDS_CHOICE.put(1, GameStatus.getInstance().getCurrentOwnLeaderCardsHand());
		LEADER_CARDS_CHOICE.put(2, GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getLeaderCardsPlayed());
	}

	@Override
	public void execute()
	{
		this.askLeaderCardChoice();
	}

	@Override
	public CLIHandlerShowOwnLeaders newInstance()
	{
		return new CLIHandlerShowOwnLeaders();
	}

	private void showLeaderCardsChoice(Map<Integer, Boolean> chosenLeaderCards)
	{
		int index = 0;
		for (int leaderCard : chosenLeaderCards.keySet()) {
			index++;
			Client.getLogger().log(Level.INFO, "{0}===", new Object[] { index });
			Client.getLogger().log(Level.INFO, "{0}\n", new Object[] { GameStatus.getInstance().getLeaderCards().get(leaderCard).getDisplayName() });
			if (!GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getLeaderCardsPlayed().get(leaderCard)) {
				Client.getLogger().log(Level.INFO, "The current card cannot be activated\n");
				Client.getLogger().log(Level.INFO, "====================================\n");
			}
		}
	}

	private void askLeaderCardChoice()
	{
		Client.getLogger().log(Level.INFO, "Enter Leader Cards ...");
		Client.getLogger().log(Level.INFO, "1 - Leader Cards in your hand");
		Client.getLogger().log(Level.INFO, "2 - Your Played Leader Cards");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerShowOwnLeaders.LEADER_CARDS_CHOICE.containsKey(Integer.parseInt(input)));
		this.showLeaderCardsChoice(CLIHandlerShowOwnLeaders.LEADER_CARDS_CHOICE.get(Integer.parseInt(input)));
	}
}