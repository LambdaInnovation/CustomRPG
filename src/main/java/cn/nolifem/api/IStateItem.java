package cn.nolifem.api;

import cn.nolifem.state.item.ItemState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IStateItem<T extends ItemState>{

	public T getState(EntityPlayer player, ItemStack stack, IStateContainer container);
}
