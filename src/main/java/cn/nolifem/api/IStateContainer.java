package cn.nolifem.api;

import java.util.HashMap;
import java.util.Map;

import cn.nolifem.state.item.ItemState;
import net.minecraft.item.ItemStack;

public interface IStateContainer{

	public Map<String, ItemState> getStateMap();
}
