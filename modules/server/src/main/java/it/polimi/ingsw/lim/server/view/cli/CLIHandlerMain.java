package it.polimi.ingsw.lim.server.view.cli;

import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.utils.Utils;

public class CLIHandlerMain implements ICLIHandler
{
	@Override
	public void execute()
	{
		this.askCommand();
	}

	private void askCommand()
	{
		Utils.executeCommand(Server.getInstance().getCliScanner().nextLine());
	}

	@Override
	public CLIHandlerMain newInstance()
	{
		return new CLIHandlerMain();
	}
}