package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsChooseRewardTemporaryModifier;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.events.EventPlaceFamilyMember;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.player.Player;

public class ActionChooseRewardTemporaryModifier extends ActionInformationsChooseRewardTemporaryModifier implements IAction
{
	private transient final Player player;

	public ActionChooseRewardTemporaryModifier(FamilyMemberType familyMemberType, Player player)
	{
		super(familyMemberType);
		this.player = player;
	}

	@Override
	public void isLegal() throws GameActionFailedException
	{
		// check if the player is inside a room
		Room room = Room.getPlayerRoom(this.player.getConnection());
		if (room == null) {
			throw new GameActionFailedException("");
		}
		// check if the game has started
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			throw new GameActionFailedException("");
		}
		// check if it is the player's turn
		if (this.player != gameHandler.getTurnPlayer()) {
			throw new GameActionFailedException("");
		}
		// check whether the server expects the player to make this action
		if (gameHandler.getExpectedAction() != ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER) {
			throw new GameActionFailedException("");
		}
		// check whether the chosen family member il colored
		if (this.getFamilyMemberType() == FamilyMemberType.NEUTRAL) {
			throw new GameActionFailedException("");
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		Room room = Room.getPlayerRoom(this.player.getConnection());
		if (room == null) {
			throw new GameActionFailedException("");
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			throw new GameActionFailedException("");
		}
		Modifier<EventPlaceFamilyMember> modifier = new Modifier<EventPlaceFamilyMember>(EventPlaceFamilyMember.class, "")
		{
			@Override
			public void apply(EventPlaceFamilyMember event)
			{
				if (event.getFamilyMemberType() != ActionChooseRewardTemporaryModifier.this.getFamilyMemberType()) {
					return;
				}
				event.setFamilyMemberValue(6);
			}
		};
		this.player.getTemporaryModifiers().add(modifier);
		this.player.getActiveModifiers().add(modifier);
		gameHandler.setExpectedAction(null);
		gameHandler.sendGameUpdate(this.player);
	}
}
