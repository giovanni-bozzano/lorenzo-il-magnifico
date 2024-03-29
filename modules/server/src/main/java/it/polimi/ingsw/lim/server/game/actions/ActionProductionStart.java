package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationProductionStart;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.enums.WorkSlotType;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.events.EventProductionStart;
import it.polimi.ingsw.lim.server.game.events.EventUseServants;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

public class ActionProductionStart extends ActionInformationProductionStart implements IAction
{
	private final transient Player player;
	private transient WorkSlotType workSlotType;
	private transient int effectiveActionValue;

	public ActionProductionStart(FamilyMemberType familyMemberType, int servants, Player player)
	{
		super(familyMemberType, servants);
		this.player = player;
	}

	@Override
	public void isLegal() throws GameActionFailedException
	{
		// check if it is the player's turn
		if (this.player != this.player.getRoom().getGameHandler().getTurnPlayer()) {
			throw new GameActionFailedException("It's not your turn");
		}
		// check whether the server expects the player to make this action
		if (this.player.getRoom().getGameHandler().getExpectedAction() != null) {
			throw new GameActionFailedException("This action was not expected");
		}
		// check if the family member is usable
		if (this.player.getFamilyMembersPositions().get(this.getFamilyMemberType()) != BoardPosition.NONE) {
			throw new GameActionFailedException("Selected Family Member has already been used");
		}
		// check if the board slot is occupied and get effective family member value
		EventPlaceFamilyMember eventPlaceFamilyMember = new EventPlaceFamilyMember(this.player, this.getFamilyMemberType(), BoardPosition.PRODUCTION_SMALL, this.player.getRoom().getGameHandler().getFamilyMemberTypeValues().get(this.getFamilyMemberType()));
		eventPlaceFamilyMember.fire();
		int effectiveFamilyMemberValue = eventPlaceFamilyMember.getFamilyMemberValue();
		if (!eventPlaceFamilyMember.isIgnoreOccupied()) {
			for (Player currentPlayer : this.player.getRoom().getGameHandler().getTurnOrder()) {
				if (currentPlayer.isOccupyingBoardPosition(BoardPosition.PRODUCTION_SMALL)) {
					if (this.player.getRoom().getGameHandler().getRoom().getPlayers().size() < 3) {
						throw new GameActionFailedException("This action cannot be performed because the slot has already been occupied");
					} else {
						this.workSlotType = WorkSlotType.BIG;
						break;
					}
				}
			}
		}
		// check if the player has the servants he sent
		if (this.player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT) < this.getServants()) {
			throw new GameActionFailedException("You don't have the amount of servants you want to use");
		}
		// get effective servants value
		EventUseServants eventUseServants = new EventUseServants(this.player, this.getServants());
		eventUseServants.fire();
		int effectiveServants = eventUseServants.getServants();
		// check if the family member and servants value is high enough
		EventProductionStart eventProductionStart = new EventProductionStart(this.player, effectiveFamilyMemberValue + effectiveServants);
		eventProductionStart.fire();
		this.effectiveActionValue = eventProductionStart.getActionValue();
		if (this.effectiveActionValue < (this.workSlotType == WorkSlotType.BIG ? BoardHandler.getBoardPositionInformation(BoardPosition.PRODUCTION_BIG).getValue() : BoardHandler.getBoardPositionInformation(BoardPosition.PRODUCTION_SMALL).getValue())) {
			throw new GameActionFailedException("The value of the selected Family Member is not enough to perform this action");
		}
	}

	@Override
	public void apply()
	{
		this.player.getRoom().getGameHandler().setCurrentPhase(Phase.FAMILY_MEMBER);
		this.player.getFamilyMembersPositions().put(this.getFamilyMemberType(), this.workSlotType == WorkSlotType.BIG ? BoardPosition.PRODUCTION_BIG : BoardPosition.PRODUCTION_SMALL);
		this.player.getPlayerResourceHandler().subtractResource(ResourceType.SERVANT, this.getServants());
		if (this.effectiveActionValue >= this.player.getPersonalBonusTile().getProductionActivationCost()) {
			EventGainResources eventGainResources = new EventGainResources(this.player, this.player.getPersonalBonusTile().getProductionInstantResources(), ResourcesSource.WORK);
			eventGainResources.fire();
			this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		}
		this.player.setCurrentProductionValue(this.effectiveActionValue);
		this.player.getRoom().getGameHandler().setExpectedAction(ActionType.PRODUCTION_TRADE);
		Connection.broadcastLogMessageToOthers(this.player, this.player.getConnection().getUsername() + " started a production with his " + this.getFamilyMemberType().name().toLowerCase() + " family member");
		Utils.checkAvailableProductionCards(this.player);
	}
}
