package it.polimi.ingsw.lim.server.game.cards;

import it.polimi.ingsw.lim.server.game.utils.CardLeaderConditionsOption;

import java.util.List;

public abstract class CardLeader extends Card
{
	private final List<CardLeaderConditionsOption> conditionsOptions;
	private boolean played = false;

	public CardLeader(String displayName, int index, List<CardLeaderConditionsOption> conditionsOptions)
	{
		super(displayName, index);
		this.conditionsOptions = conditionsOptions;
	}

	public List<CardLeaderConditionsOption> getConditionsOptions()
	{
		return this.conditionsOptions;
	}

	public boolean isPlayed()
	{
		return played;
	}

	public void setPlayed(boolean played)
	{
		this.played = played;
	}
}
