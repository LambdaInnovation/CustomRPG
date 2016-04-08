package cn.nolifem.state;

import java.util.HashMap;
import java.util.Map;

import cn.lambdalib.util.datapart.DataPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.util.datapart.EntityData;
import cn.lambdalib.util.datapart.RegDataPart;
import cn.nolifem.api.IStateBuffer;
import cn.nolifem.api.IStateItem;
import cn.nolifem.state.item.ItemState;

/**Just A container for ItemState, to avoid using ThreadLocal, and auto-clear when player log out;
 * @author Nolife_M
 */
@Registrant
@RegDataPart(value=EntityPlayer.class)
public class PlayerItemStateBuffer extends DataPart<EntityPlayer> implements IStateBuffer {
	
	ItemStack weapon;
	ItemState state;
	
	private Map<String, ItemState> statemap = new HashMap<>();
	
	public static PlayerItemStateBuffer get(EntityLivingBase living) {
		PlayerItemStateBuffer part = null;
		if(living instanceof  EntityPlayer)
			part = EntityData.get(living).getPart(PlayerItemStateBuffer.class);
        return part;
    }
	
	public PlayerItemStateBuffer() {
    	setTick(true);
	}
	
	public EntityPlayer getPlayer(){
    	return (EntityPlayer)this.getEntity();
    }

	@Override
	public void tick(){
    	if(this.getStateMap().size() > 30){
    		this.getStateMap().clear();
    	}
    	weapon = getPlayer().getHeldItem();
    	if(weapon != null && weapon.getItem() instanceof IStateItem){
    		state = ModuleState.get(weapon, this.getPlayer());
    		if(state.isTick())
    			state.tick();
    	}
	}
	
	@Override
	public Map<String, ItemState> getStateMap() {
		return this.statemap;
	}	
}
