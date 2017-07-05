package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationChooseRewardTemporaryModifier;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerChooseRewardTemporaryModifier implements ICLIHandler
{
	private static final Map<Integer, FamilyMemberType> FAMILY_MEMEBR_TYPES = new HashMap<>();

	static {
		CLIHandlerChooseRewardTemporaryModifier.FAMILY_MEMEBR_TYPES.put(1, FamilyMemberType.BLACK);
		CLIHandlerChooseRewardTemporaryModifier.FAMILY_MEMEBR_TYPES.put(2, FamilyMemberType.ORANGE);
		CLIHandlerChooseRewardTemporaryModifier.FAMILY_MEMEBR_TYPES.put(3, FamilyMemberType.WHITE);
	}

	private static void showFamilyMemberTypes()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nEnter Family Member...");
		for (Entry<Integer, FamilyMemberType> familyMemberType : CLIHandlerChooseRewardTemporaryModifier.FAMILY_MEMEBR_TYPES.entrySet()) {
			stringBuilder.append("\n\n");
			stringBuilder.append(familyMemberType.getKey());
			stringBuilder.append(' ');
			stringBuilder.append(familyMemberType.getValue().name());
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private static void askFamilyMemberType()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerChooseRewardTemporaryModifier.FAMILY_MEMEBR_TYPES.containsKey(Integer.parseInt(input)));
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationChooseRewardTemporaryModifier(CLIHandlerChooseRewardTemporaryModifier.FAMILY_MEMEBR_TYPES.get(Integer.parseInt(input))));
	}

	@Override
	public void execute()
	{
		CLIHandlerChooseRewardTemporaryModifier.showFamilyMemberTypes();
		CLIHandlerChooseRewardTemporaryModifier.askFamilyMemberType();
	}

	@Override
	public CLIHandlerChooseRewardTemporaryModifier newInstance()
	{
		return new CLIHandlerChooseRewardTemporaryModifier();
	}
}

