package cn.nolifem.state;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.util.datapart.EntityData;
import cn.lambdalib.util.datapart.RegDataPart;
import cn.nolifem.api.IStateContainer;
import cn.nolifem.api.IStateItem;
import cn.nolifem.state.item.ItemState;

/**Just A container for ItemState, to avoid using ThreadLocal, and auto-clear when player log out;
 * @author Nolife_M
 */
@Registrant
@RegDataPart(value=EntityPlayer.class)
public class PlayerItemState extends EntityState implements IStateContainer{
	
	ItemStack weapon;
	ItemState state;
	
	private Map<String, ItemState> statemap = new HashMap<>();
	
	public static PlayerItemState get(EntityLivingBase living) {
		PlayerItemState part = EntityData.get(living).getPart(PlayerItemState.class);
        return part;
    }
	
	public PlayerItemState() {
    	setTick(true);
	}
	
	public EntityPlayer getPlayer(){
    	return (EntityPlayer)this.getEntity();
    }
	
	/**NOT SAFE
	 */
	public ItemState getItemState(ItemStack stack){
		return ((IStateItem)stack.getItem()).getState(getPlayer(), stack, this);
	}
	
	@Override
	public void tick(){
    	if(this.getStateMap().size() > 30){
    		this.getStateMap().clear();
    	}
    	weapon = getPlayer().getHeldItem();
    	if(weapon != null && weapon.getItem() instanceof IStateItem){
    		state = this.getItemState(weapon);
    		if(state.isTick())
    			state.tick();
    	}
	}
	
	@Override
	public Map<String, ItemState> getStateMap() {
		return this.statemap;
	}	
}
