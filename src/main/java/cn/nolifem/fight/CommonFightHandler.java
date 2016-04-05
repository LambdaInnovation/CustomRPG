package cn.nolifem.fight;

import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.annoreg.mc.RegEventHandler;
import cn.lambdalib.annoreg.mc.RegEventHandler.Bus;
import cn.nolifem.api.IAttributeContainer;
import cn.nolifem.event.PlayerAttackEvent;
import cn.nolifem.state.EntityState;
import cn.nolifem.state.PlayerState;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

@Registrant
@RegEventHandler(Bus.Forge)
public class CommonFightHandler {
		
	public CommonFightHandler(){}
	
	@SubscribeEvent
	public void onLivingBeenAttack(LivingAttackEvent e){
		EntityLivingBase living = (EntityLivingBase) e.source.getEntity();
		if(living instanceof EntityPlayer){
			ItemStack weapon = living.getHeldItem();
			PlayerState state = PlayerState.get((EntityPlayer)living);
			if(!state.readyToAttack() && weapon != null && weapon.getItem() instanceof IAttributeContainer)
				e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent e){
		EntityLivingBase wounded = e.entityLiving;
		
		if(e.source.damageType == "player" ){
			EntityPlayer attacker = (EntityPlayer) e.source.getEntity();
			PlayerAttackEvent event = new PlayerAttackEvent(attacker, wounded, e.ammount);
			MinecraftForge.EVENT_BUS.post(event);
            e.setCanceled(event.isCanceled());
            e.ammount = event.isCanceled() ? 0 : event.dmg;
			System.out.println("Final Dmg = " + e.ammount);
		}
	}

	@SubscribeEvent
	public void onPlayerAttack(PlayerAttackEvent e){
		PlayerState pstate = PlayerState.get(e.entityPlayer);
		pstate.updateAttrListForCalc();
		EntityState tstate = EntityState.get((EntityLivingBase)e.target);
		double dmg = pstate.getAttackDmg();
		dmg = tstate.reducePhyDmg(dmg);
		e.dmg = (float)dmg;
		pstate.applyAttackEffect(e);

	}
}