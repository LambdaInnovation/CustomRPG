package cn.nolifem.attributes.item;

import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.api.IAttributeCalculator;
import cn.nolifem.attributes.BaseAttributeCR;
import cn.nolifem.util.Lang;

public class MaterialType extends BaseAttributeCR implements Cloneable{
	
	private String value;
	
	public MaterialType(){
		setTag();
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
	public void addCalc(IAttributeCalculator calculator) {}

	public int getPreference(){ return 1;}
}
