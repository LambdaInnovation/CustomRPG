package cn.nolifem.attributes.general;

import java.util.List;

import cn.nolifem.api.IAttributeDealer;
import cn.nolifem.api.attributes.GeneralAttribute;
import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.attributes.player.Proficient;
import cn.nolifem.util.Lang;

public class CriticalMulti extends GeneralAttribute implements Cloneable{
	
	@Override
	public void addFunction(IAttributeDealer calculator) {
		calculator.<Double>addFunctionSIGMA(this.getClass().getSimpleName(), (input) -> input + this.getValue());
	}
	
	@Override
	public void addInfo(List arraylist){
		arraylist.add(EnumChatFormatting.GOLD + Lang.translate(super.getLang()) + " "
				+ (getValue()>0 ? EnumChatFormatting.GREEN + "+" : EnumChatFormatting.RED + "-") + getValue()*100 + "%");
	}

	@Override
	public Class<? extends IAttributeCR> getPlayerAttrClass() {
		return Proficient.class;
	}

	public int getPreference(){ return 2;}
}