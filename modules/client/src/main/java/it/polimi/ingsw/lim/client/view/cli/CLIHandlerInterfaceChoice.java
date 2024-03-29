package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.Main;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.view.gui.InterfaceHandlerGUI;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.cli.IInputHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerInterfaceChoice implements ICLIHandler
{
	private static final Map<Integer, IInputHandler> INPUT_HANDLERS = new HashMap<>();

	static {
		CLIHandlerInterfaceChoice.INPUT_HANDLERS.put(1, cliHandler -> {
			Client.getInstance().setCliStatus(CLIStatus.NONE);
			Client.getInstance().setInterfaceHandler(new InterfaceHandlerGUI());
			try {
				Main.launch(Main.class, Main.getArgs());
			} catch (Exception exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
		});
		CLIHandlerInterfaceChoice.INPUT_HANDLERS.put(2, cliHandler -> {
			Client.getInstance().setCliStatus(CLIStatus.CONNECTION);
			Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).execute());
		});
	}

	@Override
	public void execute()
	{
		this.askInterfaceType();
	}

	@Override
	public CLIHandlerInterfaceChoice newInstance()
	{
		return new CLIHandlerInterfaceChoice();
	}

	/**
	 * <p>Asks which {@code interfaceType} the player wants to use to start the
	 * game and set the corresponding interface to proceed.
	 */
	private void askInterfaceType()
	{
		Client.getLogger().log(Level.INFO, "Enter Interface Type...");
		Client.getLogger().log(Level.INFO, "1 - GUI");
		Client.getLogger().log(Level.INFO, "2 - CLI");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerInterfaceChoice.INPUT_HANDLERS.containsKey(Integer.parseInt(input)));
		CLIHandlerInterfaceChoice.INPUT_HANDLERS.get(Integer.parseInt(input)).execute(this);
	}
}
