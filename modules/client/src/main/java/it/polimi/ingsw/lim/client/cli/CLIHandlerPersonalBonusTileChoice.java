package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerPersonalBonusTileChoice implements ICLIHandler
{
	private final Map<Integer, Integer> personalBonusTiles = new HashMap<>();

	@Override
	public void execute()
	{
		this.showPersonalBonusTiles();
		this.askPersonalBonusTileIndex();
	}

	@Override
	public CLIHandlerPersonalBonusTileChoice newInstance()
	{
		return new CLIHandlerPersonalBonusTileChoice();
	}

	private void showPersonalBonusTiles()
	{
		for (int index = 0; index < GameStatus.getInstance().getAvailablePersonalBonusTiles().size(); index++) {
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Personal Bonus Tile choice...");
		int index = 1;
		for (Integer personalBonusTile : GameStatus.getInstance().getAvailablePersonalBonusTiles()) {
			stringBuilder.append('\n');
			stringBuilder.append("============= ");
			stringBuilder.append(index);
			stringBuilder.append(" =============\n");
			stringBuilder.append(GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTile).getInformations());
			stringBuilder.append("\n=============================");
			this.personalBonusTiles.put(index, personalBonusTile);
			index++;
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askPersonalBonusTileIndex()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.personalBonusTiles.containsKey(Integer.parseInt(input)));
		Client.getInstance().getConnectionHandler().sendGamePersonalBonusTilePlayerChoice(this.personalBonusTiles.get(Integer.parseInt(input)));
	}
}
