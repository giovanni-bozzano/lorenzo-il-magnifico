package it.polimi.ingsw.lim.common.bonus;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.DiscountChoice;

public class BonusAdditionCard
{
	private final int value;
	private final CardType cardType;
	private final DiscountChoice[] discoutChoices;

	public BonusAdditionCard(int value, CardType cardType, DiscountChoice[] discountChoices)
	{
		this.value = value;
		this.cardType = cardType;
		this.discoutChoices = discountChoices;
	}

	public int getValue()
	{
		return this.value;
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public DiscountChoice[] getDiscountChoices()
	{
		return this.discoutChoices;
	}
}
