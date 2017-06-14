package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;

public class ExpectedActionChooseRewardTemporaryModifier extends ExpectedAction
{
	private final FamilyMemberType familyMemberType;

	public ExpectedActionChooseRewardTemporaryModifier(FamilyMemberType familyMemberType)
	{
		super(ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER);
		this.familyMemberType = familyMemberType;
	}

	public FamilyMemberType getFamilyMemberType()
	{
		return this.familyMemberType;
	}
}