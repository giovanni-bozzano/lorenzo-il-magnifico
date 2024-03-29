package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardInformation;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.game.utils.Reward;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>This class represents a server side development card.
 */
public abstract class DevelopmentCard extends Card
{
	private final CardType cardType;
	private final List<ResourceCostOption> resourceCostOptions;
	private final Reward reward;

	DevelopmentCard(int index, String texturePath, String displayName, CardType cardType, List<ResourceCostOption> resourceCostOptions, Reward reward)
	{
		super(index, texturePath, displayName);
		this.cardType = cardType;
		this.resourceCostOptions = new ArrayList<>(resourceCostOptions);
		this.reward = reward;
	}

	public abstract DevelopmentCardInformation getInformation();

	public CardType getCardType()
	{
		return this.cardType;
	}

	public List<ResourceCostOption> getResourceCostOptions()
	{
		return this.resourceCostOptions;
	}

	public Reward getReward()
	{
		return this.reward;
	}
}
