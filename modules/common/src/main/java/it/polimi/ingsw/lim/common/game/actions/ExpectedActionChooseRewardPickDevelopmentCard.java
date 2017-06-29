package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ExpectedActionChooseRewardPickDevelopmentCard extends ExpectedAction
{
	private final Map<CardType, Row> maximumRows;
	private final List<AvailableActionChooseRewardGetDevelopmentCard> availableActions;
	private final List<List<ResourceAmount>> discountChoices;

	public ExpectedActionChooseRewardPickDevelopmentCard(Map<CardType, Row> maximumRow, List<AvailableActionChooseRewardGetDevelopmentCard> availableActions, List<List<ResourceAmount>> discountChoices)
	{
		super(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD);
		this.maximumRows = new EnumMap<>(maximumRow);
		this.availableActions = new ArrayList<>(availableActions);
		this.discountChoices = new ArrayList<>(discountChoices);
	}

	public Map<CardType, Row> getMaximumRows()
	{
		return this.maximumRows;
	}

	public List<AvailableActionChooseRewardGetDevelopmentCard> getAvailableActions()
	{
		return this.availableActions;
	}

	public List<List<ResourceAmount>> getDiscountChoices()
	{
		return this.discountChoices;
	}
}