package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionMarket implements IAction
{
	private final Connection player;
	private final FamilyMemberType familyMemberType;
	private final int servants;
	private final MarketSlot marketSlot;

	public ActionMarket(Connection player, FamilyMemberType familyMemberType, int servants, MarketSlot marketSlot)
	{
		this.player = player;
		this.familyMemberType = familyMemberType;
		this.servants = servants;
		this.marketSlot = marketSlot;
	}

	@Override
	public boolean isLegal()
	{
		// check if the player is inside a room
		Room room = Room.getPlayerRoom(this.player);
		if (room == null) {
			return false;
		}
		// check if the game has started
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return false;
		}
		// check if it is the player's turn
		if (this.player != gameHandler.getTurnPlayer()) {
			return false;
		}
		// check whether the server expects the player to make this action
		if (room.getGameHandler().getExpectedAction() != null) {
			return false;
		}
		// check if the board slot is occupied and get effective family member value
		BoardPosition boardPosition = BoardPosition.MARKET_POSITIONS.get(this.marketSlot);
		EventPlaceFamilyMember eventPlaceFamilyMember = new EventPlaceFamilyMember(this.player, this.familyMemberType, boardPosition, gameHandler.getFamilyMemberTypeValues().get(this.familyMemberType));
		eventPlaceFamilyMember.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		int effectiveFamilyMemberValue = eventPlaceFamilyMember.getFamilyMemberValue();
		if (!eventPlaceFamilyMember.isIgnoreOccupied()) {
			for (Connection currentPlayer : room.getPlayers()) {
				if (currentPlayer.getPlayerInformations().isOccupyingBoardPosition(boardPosition)) {
					return false;
				}
			}
		}
		// check if the player has the servants he sent
		if (this.player.getPlayerInformations().getPlayerResourceHandler().getResources(ResourceType.SERVANT) < this.servants) {
			return false;
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.servants);
		eventUseServants.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		int effectiveServants = eventUseServants.getServants();
		// check if the family member and servants value is high enough
		return effectiveFamilyMemberValue + effectiveServants >= BoardHandler.getBoardPositionInformations(boardPosition).getValue();
	}

	@Override
	public void apply()
	{
		Room room = Room.getPlayerRoom(this.player);
		if (room == null) {
			return;
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return;
		}
		this.player.getPlayerInformations().getPlayerResourceHandler().subtractResource(new ResourceAmount(ResourceType.SERVANT, this.servants));
		this.player.getPlayerInformations().getPlayerResourceHandler().addTemporaryResources(BoardHandler.getBoardPositionInformations(BoardPosition.MARKET_POSITIONS.get(this.marketSlot)).getResourceAmounts());
		int councilPrivilegesCount = this.player.getPlayerInformations().getPlayerResourceHandler().getTemporaryResources(ResourceType.COUNCIL_PRIVILEGE);
		if (councilPrivilegesCount > 0) {
			room.getGameHandler().setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
			// TODO manda scelta di privilegio
		} else {
			// TODO turno del prossimo giocatore
		}
	}
}
