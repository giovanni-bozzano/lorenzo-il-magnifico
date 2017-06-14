package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;

import java.util.ArrayList;
import java.util.List;

public class AvailableActionGetDevelopmentCard extends AvailableAction
{
	private final CardType cardType;
	private final Row row;
	private final List<FamilyMemberType> familyMemberTypes;
	private final List<ResourceCostOption> resourceCostOptions;
	private final List<List<ResourceAmount>> discountChoices;

	public AvailableActionGetDevelopmentCard(CardType cardType, Row row, List<FamilyMemberType> familyMemberTypes, List<ResourceCostOption> resourceCostOptions, List<List<ResourceAmount>> discountChoices)
	{
		super(ActionType.GET_DEVELOPMENT_CARD);
		this.cardType = cardType;
		this.row = row;
		this.familyMemberTypes = new ArrayList<>(familyMemberTypes);
		this.resourceCostOptions = new ArrayList<>(resourceCostOptions);
		this.discountChoices = new ArrayList<>(discountChoices);
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public Row getRow()
	{
		return this.row;
	}

	public List<FamilyMemberType> getFamilyMemberTypes()
	{
		return this.familyMemberTypes;
	}

	public List<ResourceCostOption> getResourceCostOptions()
	{
		return this.resourceCostOptions;
	}

	public List<List<ResourceAmount>> getDiscountChoices()
	{
		return this.discountChoices;
	}
}