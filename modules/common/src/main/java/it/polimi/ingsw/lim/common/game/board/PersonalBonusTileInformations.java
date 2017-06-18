package it.polimi.ingsw.lim.common.game.board;

import it.polimi.ingsw.lim.common.game.ObjectInformations;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.util.ArrayList;
import java.util.List;

public class PersonalBonusTileInformations extends ObjectInformations
{
	private final int productionActivationCost;
	private final List<ResourceAmount> productionInstantResources;
	private final int harvestActivationCost;
	private final List<ResourceAmount> harvestInstantResources;

	public PersonalBonusTileInformations(String texture, int productionActivationCost, List<ResourceAmount> productionInstantResources, int harvestActivationCost, List<ResourceAmount> harvestInstantResources)
	{
		super(texture);
		this.productionActivationCost = productionActivationCost;
		this.productionInstantResources = new ArrayList<>(productionInstantResources);
		this.harvestActivationCost = harvestActivationCost;
		this.harvestInstantResources = new ArrayList<>(harvestInstantResources);
	}

	public int getProductionActivationCost()
	{
		return this.productionActivationCost;
	}

	public List<ResourceAmount> getProductionInstantResources()
	{
		return this.productionInstantResources;
	}

	public int getHarvestActivationCost()
	{
		return this.harvestActivationCost;
	}

	public List<ResourceAmount> getHarvestInstantResources()
	{
		return this.harvestInstantResources;
	}
}