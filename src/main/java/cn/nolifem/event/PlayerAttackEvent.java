package cn.nolifem.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * Created by Nolife_M on 2016/3/28.
 */
public class PlayerAttackEvent extends PlayerEvent {
    public final EntityLivingBase target;
    public float dmg;

    public PlayerAttackEvent(EntityPlayer player, EntityLivingBase target, float dmg) {
        super(player);
        this.target = target;
        this.dmg = dmg;
    }
}
