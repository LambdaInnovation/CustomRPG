package cn.nolifem.items;

import java.lang.reflect.Field;
import java.util.*;

import cn.nolifem.state.ModuleState;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cn.nolifem.CustomRPG;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IAttributeContainer;
import cn.nolifem.api.IStateContainer;
import cn.nolifem.api.IStateItem;
import cn.nolifem.attributes.general.GeneralAttribute;
import cn.nolifem.attributes.general.PhysicalDamage;
import cn.nolifem.state.PlayerItemState;
import cn.nolifem.state.PlayerState;
import cn.nolifem.state.item.MeleeState;

public class ItemCustomMelee extends ItemSword implements IAttributeContainer, IStateItem<MeleeState>{

	public String name;
	private Map<String, IAttributeCR> attrMap = new HashMap<String, IAttributeCR>();	
	public ItemCustomMelee(){
		this(ToolMaterial.IRON);
		this.setUnlocalizedName("undefined" + UUID.randomUUID().toString());
		setCreativeTab(CustomRPG.wcct);
	}
	
	public ItemCustomMelee(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
	}
	
	@Override
	public void initAttr() {
		GeneralAttribute attr = (GeneralAttribute) this.getAttr(PhysicalDamage.class);
		if(attr != null){
			try {
				Field f = ItemSword.class.getDeclaredField("field_150934_a");
				f.setAccessible(true);	
				f.set(this, (float)attr.getValue());
				f.setAccessible(false);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	/////hit dealing
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity){
		PlayerState state = (PlayerState) ModuleState.get(player);
		if(state.attackCD > 0)
			return true;
		else
			entity.hurtResistantTime = 0;
        return false;
    }
    
	public boolean hitEntity(ItemStack stack, EntityLivingBase hite, EntityLivingBase e){
		if(e instanceof EntityPlayer)
			((MeleeState) PlayerItemState.get(e).getItemState(stack)).dealAttack((EntityPlayer)e);
	    return true;
	}
	
    public boolean onBlockDestroyed(ItemStack stack, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase e){
    	if(e instanceof EntityPlayer)
    		((MeleeState) PlayerItemState.get(e).getItemState(stack)).dealAttack((EntityPlayer)e);
    	return true;
    }
    ////Info Dealing
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List arraylist, boolean flag) {
		((MeleeState) PlayerItemState.get(player).getItemState(stack)).addInfo(arraylist);;
	}
	
	public static String translate(String s){
		return StatCollector.translateToLocal(s);
	}
	
	//Interface
	@Override
	public Map<String, IAttributeCR> getAttrMap() {
		return this.attrMap;
	}

	@Override
	public MeleeState getState(EntityPlayer player, ItemStack stack, IStateContainer container) {
		return MeleeState.get(player, stack, container);
	}
}
