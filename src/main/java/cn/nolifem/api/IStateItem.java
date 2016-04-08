package cn.nolifem.api;

import cn.nolifem.state.item.ItemState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import java.util.Optional;

public interface IStateItem<T extends ItemState>{

	public Optional<T> getState(EntityLivingBase living, ItemStack stack, IStateBuffer container);
}
