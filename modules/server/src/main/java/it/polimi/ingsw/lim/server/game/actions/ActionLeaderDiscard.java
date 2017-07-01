package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsLeaderDiscard;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.util.Collections;

public class ActionLeaderDiscard extends ActionInformationsLeaderDiscard implements IAction
{
	private transient final Player player;
	private transient LeaderCard leaderCard;

	public ActionLeaderDiscard(int leaderCardIndex, Player player)
	{
		super(leaderCardIndex);
		this.player = player;
	}

	@Override
	public void isLegal() throws GameActionFailedException
	{
		// check if it is the player's turn
		if (this.player != this.player.getRoom().getGameHandler().getTurnPlayer()) {
			throw new GameActionFailedException("It is not this player's turn");
		}
		// check whether the server expects the player to make this action
		if (this.player.getRoom().getGameHandler().getExpectedAction() != null) {
			throw new GameActionFailedException("This action was not expected");
		}
		// check if the player has the leader card
		boolean owned = false;
		for (LeaderCard currentLeaderCard : this.player.getPlayerCardHandler().getLeaderCards()) {
			if (this.getLeaderCardIndex() != currentLeaderCard.getIndex()) {
				continue;
			}
			this.leaderCard = currentLeaderCard;
			owned = true;
			break;
		}
		if (!owned) {
			throw new GameActionFailedException("The Player does not have this Leader Card");
		}
		// check if the leader card has been played
		if (this.leaderCard.isPlayed()) {
			throw new GameActionFailedException("This Leader Card has already been played");
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		this.player.getRoom().getGameHandler().setCurrentPhase(Phase.LEADER);
		this.player.getPlayerCardHandler().getLeaderCards().remove(this.leaderCard);
		EventGainResources eventGainResources = new EventGainResources(this.player, Collections.singletonList(new ResourceAmount(ResourceType.COUNCIL_PRIVILEGE, 1)), ResourcesSource.LEADER_CARDS);
		eventGainResources.applyModifiers(this.player.getActiveModifiers());
		this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		if (Utils.sendCouncilPrivileges(this.player)) {
			return;
		}
		this.player.getRoom().getGameHandler().sendGameUpdate(this.player);
	}
}
