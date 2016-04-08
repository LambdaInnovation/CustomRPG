package cn.nolifem.attributes.item;

import java.util.List;

import cn.lambdalib.s11n.SerializeIncluded;
import cn.nolifem.api.IAttributeDealer;
import cn.nolifem.api.attributes.ItemAttribute;
import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.util.Lang;

public class MaterialType extends ItemAttribute implements Cloneable{

	@SerializeIncluded
	private String value;
	
	public MaterialType(){
		super();
	}
		
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public void addInfo(List arraylist){
		arraylist.add(EnumChatFormatting.WHITE +  Lang.translate(super.getLang()) + ": " +  Lang.translate(super.getLang() + "_" + this.value));
	}

	@Override
	public void addFunction(IAttributeDealer calculator) {}

	public int getPreference(){ return 1;}
}
