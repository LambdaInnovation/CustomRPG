package cn.nolifem.state;

import cn.lambdalib.util.datapart.EntityData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Optional;

/**
 * Created by Nolife_M on 2016/4/8.
 */
public class ModuleState {
    public static EntityState get(EntityLivingBase living){
        if(living instanceof EntityPlayer)
            return EntityData.get(living).getPart(PlayerState.class);
        return EntityData.get(living).getPart(EntityState.class);
    };
}
