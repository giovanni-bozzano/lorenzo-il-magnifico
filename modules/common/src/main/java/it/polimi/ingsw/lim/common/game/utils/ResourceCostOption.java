package it.polimi.ingsw.lim.common.game.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResourceCostOption implements Serializable
{
	private final List<ResourceAmount> requiredResources = new ArrayList<>();
	private final List<ResourceAmount> spentResources = new ArrayList<>();

	public ResourceCostOption(List<ResourceAmount> requiredResources, List<ResourceAmount> spentResources)
	{
		this.requiredResources.addAll(requiredResources);
		this.spentResources.addAll(spentResources);
	}

	public ResourceCostOption(ResourceCostOption resourceCostOption)
	{
		this(resourceCostOption.requiredResources, resourceCostOption.spentResources);
	}

	public List<ResourceAmount> getRequiredResources()
	{
		return this.requiredResources;
	}

	public List<ResourceAmount> getSpentResources()
	{
		return this.spentResources;
	}

	@Override
	public boolean equals(Object resourceCostOption)
	{
		return resourceCostOption instanceof ResourceCostOption && this.requiredResources.equals(((ResourceCostOption) resourceCostOption).requiredResources) && this.spentResources.equals(((ResourceCostOption) resourceCostOption).spentResources);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), this.requiredResources, this.spentResources);
	}
}
