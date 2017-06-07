package it.polimi.ingsw.lim.server.game.cards.leaders;

import it.polimi.ingsw.lim.server.game.cards.CardLeader;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.utils.CardLeaderConditionsOption;

public class CardLeaderModifier extends CardLeader
{
	private final Modifier modifier;

	public CardLeaderModifier(String displayName, int index, CardLeaderConditionsOption[] conditionsOptions, Modifier modifier)
	{
		super(displayName, index, conditionsOptions);
		this.modifier = modifier;
	}

	public Modifier getModifier()
	{
		return this.modifier;
	}
}