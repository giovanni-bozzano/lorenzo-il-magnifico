package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.player.Player;

import java.util.List;

/**
 * <p>This class represent a game event, fired to be edited by existing {@link
 * Modifier}s.
 */
public abstract class Event
{
	private final Player player;

	Event(Player player)
	{
		this.player = player;
	}

	/**
	 * <p>Applies all the given {@link Modifier}s to this {@link Event}.
	 *
	 * @param modifiers the {@link Modifier} {@link List} to apply.
	 */
	public void applyModifiers(List<Modifier> modifiers)
	{
		modifiers.forEach(modifier -> modifier.call(this));
	}

	/**
	 * <p>Gets the {@link Player} bound to this {@link Event}.
	 *
	 * @return the {@link Player} bound to this {@link Event}.
	 */
	public Player getPlayer()
	{
		return this.player;
	}
}
