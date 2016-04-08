package cn.nolifem.attributes.general;

import java.util.List;

import cn.nolifem.api.IAttributeDealer;
import cn.nolifem.api.attributes.GeneralAttribute;
import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.attributes.player.Dexterity;
import cn.nolifem.util.Lang;

public class Defence extends GeneralAttribute implements Cloneable{
	@Override
	public void addFunction(IAttributeDealer calculator) {
		calculator.<Double>addFunctionSIGMA(this.getClass().getSimpleName(), (input) -> input + this.getValue());
	}
	
	@Override
	public void addInfo(List arraylist){
		arraylist.add(EnumChatFormatting.GOLD + Lang.translate(super.getLang()) + " " + this.getValue()*100 + "%");
	}

	@Override
	public Class<? extends IAttributeCR> getPlayerAttrClass() {
		// TODO Auto-generated method stub
		return Dexterity.class;
	}

	public int getPreference(){ return 2;}
}