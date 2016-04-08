package cn.nolifem.attributes.general;

import java.util.List;

import cn.nolifem.api.attributes.GeneralAttribute;
import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IAttributeDealer;
import cn.nolifem.attributes.player.Strength;
import cn.nolifem.util.Lang;

public class PhysicalDamage extends GeneralAttribute implements Cloneable{
	
	@Override
	public void addFunction(IAttributeDealer calculator) {
		calculator.<Double>addFunctionSIGMA(this.getClass().getSimpleName(), (input) -> input + this.getValue());
	}

	@Override
	public void addInfo(List arraylist) {
		arraylist.add(EnumChatFormatting.RED +  Lang.translate(super.getLang()) + " " + getValue());
	}
	
	@Override
	public Class<? extends IAttributeCR> getPlayerAttrClass() {
		return Strength.class;
	}

	public int getPreference(){ return 2;}
}
