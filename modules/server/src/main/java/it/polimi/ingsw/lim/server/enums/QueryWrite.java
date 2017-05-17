package it.polimi.ingsw.lim.server.enums;

import it.polimi.ingsw.lim.server.database.Database;

public enum QueryWrite
{
	INSERT_USERNAME_AND_PASSWORD_AND_SALT("INSERT INTO " + Database.PREFIX + Database.TABLE_PLAYERS + " (" + Database.TABLE_PLAYERS_COLUMN_USERNAME + ", " + Database.TABLE_PLAYERS_COLUMN_PASSWORD + ", " + Database.TABLE_PLAYERS_COLUMN_SALT + ") VALUES (?, ?, ?);");
	private final String text;

	QueryWrite(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return this.text;
	}
}