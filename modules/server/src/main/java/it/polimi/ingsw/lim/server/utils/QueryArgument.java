package it.polimi.ingsw.lim.server.utils;

import it.polimi.ingsw.lim.server.enums.QueryValueType;

public class QueryArgument
{
	private final QueryValueType valueType;
	private final Object value;

	public QueryArgument(QueryValueType valueType, Object value)
	{
		this.valueType = valueType;
		this.value = value;
	}

	QueryValueType getValueType()
	{
		return this.valueType;
	}

	Object getValue()
	{
		return this.value;
	}
}

