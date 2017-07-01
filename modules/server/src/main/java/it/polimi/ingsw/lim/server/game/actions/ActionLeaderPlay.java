package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsLeaderPlay;
import it.polimi.ingsw.lim.common.game.utils.CardAmount;
import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardModifier;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardReward;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.utils.Utils;

public class ActionLeaderPlay extends ActionInformationsLeaderPlay implements IAction
{
	private transient final Player player;
	private transient LeaderCard leaderCard;

	public ActionLeaderPlay(int leaderCardIndex, Player player)
	{
		super(leaderCardIndex);
		this.player = player;
	}

	@Override
	public void isLegal() throws GameActionFailedException
	{
		// check if it is the player's turn
		if (this.player != this.player.getRoom().getGameHandler().getTurnPlayer()) {
			throw new GameActionFailedException("It's not this player's turn");
		}
		// check whether the server expects the player to make this action
		if (this.player.getRoom().getGameHandler().getExpectedAction() != null) {
			throw new GameActionFailedException("This action was not expected");
		}
		// check if the player has the leader card
		boolean owned = false;
		for (LeaderCard currentLeaderCard : this.player.getPlayerCardHandler().getLeaderCards()) {
			if (this.getLeaderCardIndex() == currentLeaderCard.getIndex()) {
				this.leaderCard = currentLeaderCard;
				owned = true;
				break;
			}
		}
		if (!owned) {
			throw new GameActionFailedException("Player doesn't have this Leader Card");
		}
		// check if the player's resources are enough
		if (this.leaderCard.getConditionsOptions().isEmpty()) {
			return;
		}
		if (this.leaderCard.getIndex() == 14) {
			boolean availableCardsToCopy = false;
			for (Player otherPlayer : this.player.getRoom().getGameHandler().getTurnOrder()) {
				if (otherPlayer != this.player) {
					for (LeaderCard leaderCard : otherPlayer.getPlayerCardHandler().getLeaderCards()) {
						if (leaderCard.isPlayed()) {
							availableCardsToCopy = true;
							break;
						}
					}
				}
				if (availableCardsToCopy) {
					break;
				}
			}
			if (!availableCardsToCopy) {
				throw new GameActionFailedException("No other Leader Cards are available");
			}
		}
		for (LeaderCardConditionsOption leaderCardConditionsOption : this.leaderCard.getConditionsOptions()) {
			boolean availableConditionOption = true;
			if (leaderCardConditionsOption.getResourceAmounts() != null) {
				for (ResourceAmount requiredResources : leaderCardConditionsOption.getResourceAmounts()) {
					int playerResources = this.player.getPlayerResourceHandler().getResources().get(requiredResources.getResourceType());
					if (playerResources < requiredResources.getAmount()) {
						availableConditionOption = false;
						break;
					}
				}
			}
			if (leaderCardConditionsOption.getCardAmounts() != null) {
				for (CardAmount requiredCards : leaderCardConditionsOption.getCardAmounts()) {
					int playerCards = this.player.getPlayerCardHandler().getDevelopmentCardsNumber(requiredCards.getCardType());
					if (playerCards < requiredCards.getAmount()) {
						availableConditionOption = false;
						break;
					}
				}
			}
			if (availableConditionOption) {
				return;
			}
		}
		throw new GameActionFailedException("Player doesn't have the necessary resources to perform this action");
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		this.player.getRoom().getGameHandler().setCurrentPhase(Phase.LEADER);
		// check Lorenzo Il Magnifico
		if (this.leaderCard.getIndex() == 14) {
			this.player.setCurrentActionReward(((LeaderCardReward) this.leaderCard).getReward().getActionReward());
			this.player.getRoom().getGameHandler().setExpectedAction(((LeaderCardReward) this.leaderCard).getReward().getActionReward().getRequestedAction());
			this.player.getRoom().getGameHandler().sendGameUpdateExpectedAction(this.player, ((LeaderCardReward) this.leaderCard).getReward().getActionReward().createExpectedAction(this.player));
			return;
		}
		if (this.leaderCard instanceof LeaderCardModifier) {
			this.player.getActiveModifiers().add(((LeaderCardModifier) this.leaderCard).getModifier());
		} else {
			((LeaderCardReward) this.leaderCard).setActivated(true);
			EventGainResources eventGainResources = new EventGainResources(this.player, ((LeaderCardReward) this.leaderCard).getReward().getResourceAmounts(), ResourcesSource.LEADER_CARDS);
			eventGainResources.applyModifiers(this.player.getActiveModifiers());
			this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
			if (Utils.sendActionReward(this.player, ((LeaderCardReward) this.leaderCard).getReward().getActionReward())) {
				return;
			}
			if (Utils.sendCouncilPrivileges(this.player)) {
				return;
			}
		}
		this.player.getRoom().getGameHandler().sendGameUpdate(this.player);
	}
}
