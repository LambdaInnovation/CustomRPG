package cn.nolifem.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.util.datapart.EntityData;
import cn.lambdalib.util.datapart.RegDataPart;
import cn.lambdalib.util.mc.ControlOverrider;
import cn.nolifem.CustomRPG;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IAttributeCalculator;
import cn.nolifem.api.IAttributeContainer;
import cn.nolifem.api.IOriginalModifier;
import cn.nolifem.api.IStateContainer;
import cn.nolifem.api.IStateItem;
import cn.nolifem.attributes.general.AttackSpeed;
import cn.nolifem.attributes.general.BaseDamage;
import cn.nolifem.attributes.general.CriticalMulti;
import cn.nolifem.attributes.general.CriticalRate;
import cn.nolifem.attributes.general.Defence;
import cn.nolifem.attributes.general.GeneralAttribute;
import cn.nolifem.attributes.general.Health;
import cn.nolifem.attributes.general.MovementSpeed;
import cn.nolifem.attributes.general.PhysicalDamage;
import cn.nolifem.attributes.general.PrepareSpeed;
import cn.nolifem.attributes.general.Vitality;
import cn.nolifem.attributes.player.Dexterity;
import cn.nolifem.attributes.player.PlayerAttribute;
import cn.nolifem.attributes.player.Proficient;
import cn.nolifem.attributes.player.Strength;
import cn.nolifem.items.ItemCustomMelee;
import cn.nolifem.items.ItemGrindTool;
import cn.nolifem.state.item.GrindToolState;
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
