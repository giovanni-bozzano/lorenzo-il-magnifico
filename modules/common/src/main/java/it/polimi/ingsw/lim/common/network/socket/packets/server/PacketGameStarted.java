package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.game.RoomInformations;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketGameStarted extends Packet
{
	private final RoomInformations roomInformations;

	public PacketGameStarted(RoomInformations roomInformations)
	{
		super(PacketType.GAME_STARTED);
		this.roomInformations = roomInformations;
	}

	public RoomInformations getRoomInformations()
	{
		return this.roomInformations;
	}
}
