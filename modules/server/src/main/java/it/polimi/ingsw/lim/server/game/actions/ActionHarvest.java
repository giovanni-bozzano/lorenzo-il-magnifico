package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsHarvest;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionHarvest extends ActionInformationsHarvest implements IAction
{
	private Connection player;

	public ActionHarvest(Connection player, FamilyMemberType familyMemberType)
	{
		super(familyMemberType);
		this.player = player;
	}

	@Override
	public void isLegal()
	{
	}

	@Override
	public void apply()
	{
	}

	@Override
	public Connection getPlayer()
	{
		return this.player;
	}
}
