package cn.nolifem.attributes.item;

import java.util.List;

import cn.lambdalib.s11n.SerializeIncluded;
import cn.nolifem.api.attributes.ItemAttribute;
import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.api.IAttributeDealer;
import cn.nolifem.util.Lang;

public class MaxStackSize extends ItemAttribute implements Cloneable{

	@SerializeIncluded
	private int value;
	
	public MaxStackSize(){super();}
		
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addInfo(List arraylist){
		arraylist.add(EnumChatFormatting.DARK_GREEN +  Lang.translateFormatted(super.getLang(), this.value));
	}

	@Override
	public void addFunction(IAttributeDealer calculator) {}

	public int getPreference(){ return 1;}
}
