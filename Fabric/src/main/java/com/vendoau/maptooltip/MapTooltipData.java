package com.vendoau.maptooltip;

import net.minecraft.client.item.TooltipData;
import net.minecraft.item.map.MapState;

public record MapTooltipData(Integer id, MapState state) implements TooltipData {
}
