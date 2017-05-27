package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsCouncilPalace;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionCouncilPalace extends ActionInformationsCouncilPalace implements IAction
{
	private Connection player;

	public ActionCouncilPalace(Connection player, FamilyMemberType familyMemberType, int councilPalaceRewardIndex)
	{
		super(familyMemberType, councilPalaceRewardIndex);
		this.player = player;
	}

	@Override
	public boolean isLegal()
	{
		return false;
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