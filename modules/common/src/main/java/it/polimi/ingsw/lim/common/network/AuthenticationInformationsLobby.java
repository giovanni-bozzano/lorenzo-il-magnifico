package it.polimi.ingsw.lim.common.network;

import it.polimi.ingsw.lim.common.game.RoomInformations;

public abstract class AuthenticationInformationsLobby extends AuthenticationInformations
{
	private RoomInformations roomInformations;

	public RoomInformations getRoomInformations()
	{
		return this.roomInformations;
	}

	public void setRoomInformations(RoomInformations roomInformations)
	{
		this.roomInformations = roomInformations;
	}
}