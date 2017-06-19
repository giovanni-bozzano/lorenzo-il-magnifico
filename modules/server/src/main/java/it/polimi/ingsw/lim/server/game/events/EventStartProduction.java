package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.game.player.Player;

public class EventStartProduction extends Event
{
	private int actionValue;

	public EventStartProduction(Player player, int actionValue)
	{
		super(player);
		this.actionValue = actionValue;
	}

	public int getActionValue()
	{
		return this.actionValue;
	}

	public void setActionValue(int actionValue)
	{
		this.actionValue = actionValue;
	}
}
