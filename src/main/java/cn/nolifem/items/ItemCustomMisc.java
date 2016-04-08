package cn.nolifem.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cn.nolifem.CustomRPG;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IAttributeContainer;
import cn.nolifem.api.attributes.BaseAttributeCR;
import cn.nolifem.attributes.item.MaxStackSize;

public class ItemCustomMisc extends Item implements IAttributeContainer{

	public String name;
	
	private Map<String, IAttributeCR> map = new HashMap<>();
	
	public ItemCustomMisc(){
		this.setUnlocalizedName("undefined" + UUID.randomUUID().toString());
		this.setCreativeTab(CustomRPG.mcct);
	}
	
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) {
		for(IAttributeCR attr : getAttrMapAsList()){
			if(attr instanceof BaseAttributeCR){
				((BaseAttributeCR)attr).addInfo(arraylist);
			}
		}
	}

	@Override
	public Map<String, IAttributeCR> getAttrMap() {
		return this.map;
	}

	@Override
	public void initAttr() {
		MaxStackSize mattr = (MaxStackSize) this.getAttr(MaxStackSize.class);
		if(mattr != null){
			this.setMaxStackSize(mattr.getValue());
		}
	}
	
}
