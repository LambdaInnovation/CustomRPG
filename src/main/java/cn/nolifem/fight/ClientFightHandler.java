package cn.nolifem.fight;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.annoreg.mc.RegEventHandler;
import cn.lambdalib.annoreg.mc.RegEventHandler.Bus;
import cn.lambdalib.util.mc.ControlOverrider;
import cn.nolifem.state.PlayerState;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Registrant
@RegEventHandler(Bus.FML)
public class ClientFightHandler {
		
	public ClientFightHandler(){}
	
	@SubscribeEvent
    public void onPlayerTick(PlayerTickEvent e) {}
		
}