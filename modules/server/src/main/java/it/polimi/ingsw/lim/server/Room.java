package it.polimi.ingsw.lim.server;

import java.util.ArrayList;
import java.util.List;

public class Room
{
	private final int id;
	private final String name;
	private final List<IConnection> players = new ArrayList<>();

	public Room(int id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public int getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public List<IConnection> getPlayers()
	{
		return this.players;
	}
}