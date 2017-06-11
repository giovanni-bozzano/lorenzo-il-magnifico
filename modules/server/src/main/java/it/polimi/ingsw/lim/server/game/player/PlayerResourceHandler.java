package it.polimi.ingsw.lim.server.game.player;

import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerResourceHandler
{
	private static final Map<Integer, Integer> TERRITORY_SLOTS_CONDITIONS = new HashMap<>();

	static {
		PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.put(0, 0);
		PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.put(1, 0);
		PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.put(2, 3);
		PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.put(3, 7);
		PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.put(4, 12);
		PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.put(5, 18);
	}

	private static final Map<Period, Integer> EXCOMMUNICATION_CONDITIONS = new EnumMap<>(Period.class);

	static {
		PlayerResourceHandler.EXCOMMUNICATION_CONDITIONS.put(Period.FIRST, 3);
		PlayerResourceHandler.EXCOMMUNICATION_CONDITIONS.put(Period.SECOND, 4);
		PlayerResourceHandler.EXCOMMUNICATION_CONDITIONS.put(Period.THIRD, 5);
	}

	private final PlayerInformations playerInformations;
	private final Map<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);
	private final Map<ResourceType, Integer> temporaryResources = new EnumMap<>(ResourceType.class);

	PlayerResourceHandler(PlayerInformations playerInformations, int resourcesServant, int resourcesStone, int resourcesWood)
	{
		this.playerInformations = playerInformations;
		this.resources.put(ResourceType.SERVANT, resourcesServant);
		this.resources.put(ResourceType.STONE, resourcesStone);
		this.resources.put(ResourceType.WOOD, resourcesWood);
		this.resources.put(ResourceType.FAITH_POINT, 0);
		this.resources.put(ResourceType.MILITARY_POINT, 0);
		this.resources.put(ResourceType.VICTORY_POINT, 0);
	}

	public void addResource(ResourceAmount resourceAmount)
	{
		this.resources.put(resourceAmount.getResourceType(), this.resources.get(resourceAmount.getResourceType()) + resourceAmount.getAmount());
	}

	public void addResources(List<ResourceAmount> resourceAmounts)
	{
		for (ResourceAmount resourceAmount : resourceAmounts) {
			this.resources.put(resourceAmount.getResourceType(), this.resources.get(resourceAmount.getResourceType()) + resourceAmount.getAmount());
		}
	}

	public void addTemporaryResource(ResourceAmount resourceAmount)
	{
		this.temporaryResources.put(resourceAmount.getResourceType(), this.temporaryResources.get(resourceAmount.getResourceType()) + resourceAmount.getAmount());
	}

	public void addTemporaryResources(List<ResourceAmount> resourceAmounts)
	{
		for (ResourceAmount resourceAmount : resourceAmounts) {
			this.temporaryResources.put(resourceAmount.getResourceType(), this.temporaryResources.get(resourceAmount.getResourceType()) + resourceAmount.getAmount());
		}
	}

	public void subtractResource(ResourceAmount resourceAmount)
	{
		this.resources.put(resourceAmount.getResourceType(), this.resources.get(resourceAmount.getResourceType()) - resourceAmount.getAmount());
	}

	public void subtractTemporaryResource(ResourceAmount resourceAmount)
	{
		this.temporaryResources.put(resourceAmount.getResourceType(), this.temporaryResources.get(resourceAmount.getResourceType()) - resourceAmount.getAmount());
	}

	public void subtractResources(List<ResourceAmount> resourceAmounts)
	{
		for (ResourceAmount resourceAmount : resourceAmounts) {
			this.resources.put(resourceAmount.getResourceType(), this.resources.get(resourceAmount.getResourceType()) - resourceAmount.getAmount());
		}
	}

	public void subtractTemporaryResources(List<ResourceAmount> resourceAmounts)
	{
		for (ResourceAmount resourceAmount : resourceAmounts) {
			this.temporaryResources.put(resourceAmount.getResourceType(), this.temporaryResources.get(resourceAmount.getResourceType()) - resourceAmount.getAmount());
		}
	}

	public boolean isTerritorySlotAvailable(int territorySlot)
	{
		return PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.containsKey(territorySlot) && this.resources.get(ResourceType.MILITARY_POINT) >= PlayerResourceHandler.TERRITORY_SLOTS_CONDITIONS.get(territorySlot);
	}

	public boolean isExcommunicated(Period period)
	{
		return this.resources.get(ResourceType.FAITH_POINT) < PlayerResourceHandler.EXCOMMUNICATION_CONDITIONS.get(period);
	}

	public int convertToVictoryPoints()
	{
		return (this.resources.get(ResourceType.COIN) + this.resources.get(ResourceType.WOOD) + this.resources.get(ResourceType.STONE) + this.resources.get(ResourceType.SERVANT)) / 5;
	}

	public int getResources(ResourceType resourceType)
	{
		return this.resources.get(resourceType);
	}

	public int getTemporaryResources(ResourceType resourceType)
	{
		return this.temporaryResources.get(resourceType);
	}

	public PlayerInformations getPlayerInformations()
	{
		return this.playerInformations;
	}
}
