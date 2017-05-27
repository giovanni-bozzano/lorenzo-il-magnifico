package it.polimi.ingsw.lim.server.game.bonus;

import it.polimi.ingsw.lim.server.game.events.IEvent;

public abstract class Bonus<T extends IEvent>
{
	private final Class<T> eventClass;

	public Bonus(Class<T> eventClass)
	{
		this.eventClass = eventClass;
	}

	public abstract T apply(T event);

	public void call(IEvent event)
	{
		if (event.getClass() == this.eventClass) {
			this.apply(this.eventClass.cast(event));
		}
	}

	public Class<T> getEventClass()
	{
		return this.eventClass;
	}
}