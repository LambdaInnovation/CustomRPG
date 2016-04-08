package cn.nolifem.state;

import cn.lambdalib.util.datapart.EntityData;
import cn.nolifem.api.IStateItem;
import cn.nolifem.state.item.ItemState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Optional;

/**
 * Created by Nolife_M on 2016/4/8.
 */
public class ModuleState {
    public static EntityState get(EntityLivingBase living){
        if(living instanceof EntityPlayer)
            return EntityData.get(living).getPart(PlayerState.class);
        return EntityData.get(living).getPart(EntityState.class);
    }

    public static ItemState get(ItemStack stack, EntityLivingBase living){
        return (ItemState) ((IStateItem)stack.getItem())
                .getState(living, stack, PlayerItemStateBuffer.get(living))
                .get();
    }
}
