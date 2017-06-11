package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.enums.WorkSlotType;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.events.EventStartProduction;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionProductionStart implements IAction
{
	private final Connection player;
	private final FamilyMemberType familyMemberType;
	private int servants;
	private WorkSlotType workSlotType;
	private int effectiveActionValue;

	public ActionProductionStart(Connection player, FamilyMemberType familyMemberType, int servants)
	{
		this.player = player;
		this.familyMemberType = familyMemberType;
		this.servants = servants;
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
		EventPlaceFamilyMember eventPlaceFamilyMember = new EventPlaceFamilyMember(this.player, this.familyMemberType, BoardPosition.PRODUCTION_SMALL, gameHandler.getFamilyMemberTypeValues().get(this.familyMemberType));
		eventPlaceFamilyMember.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		int effectiveFamilyMemberValue = eventPlaceFamilyMember.getFamilyMemberValue();
		if (!eventPlaceFamilyMember.isIgnoreOccupied()) {
			for (Connection currentPlayer : room.getPlayers()) {
				if (currentPlayer.getPlayerInformations().isOccupyingBoardPosition(BoardPosition.PRODUCTION_SMALL)) {
					this.workSlotType = WorkSlotType.BIG;
					break;
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
		EventStartProduction eventStartProduction = new EventStartProduction(this.player, effectiveFamilyMemberValue + effectiveServants);
		eventStartProduction.applyModifiers(this.player.getPlayerInformations().getActiveModifiers());
		this.effectiveActionValue = eventStartProduction.getActionValue();
		return this.effectiveActionValue >= (this.workSlotType == WorkSlotType.BIG ? BoardHandler.getBoardPositionInformations(BoardPosition.PRODUCTION_BIG).getValue() : BoardHandler.getBoardPositionInformations(BoardPosition.PRODUCTION_SMALL).getValue());
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
		this.player.getPlayerInformations().getPlayerResourceHandler().addTemporaryResources(this.player.getPlayerInformations().getPersonalBonusTile().getHarvestInstantResources());
		// TODO aggiorno tutti
		this.player.getPlayerInformations().setCurrentProductionValue(this.effectiveActionValue);
		room.getGameHandler().setExpectedAction(ActionType.PRODUCTION_TRADE);
		// TODO mando azione trade
	}
}
