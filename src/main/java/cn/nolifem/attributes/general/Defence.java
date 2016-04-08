package cn.nolifem.attributes.general;

import java.util.List;

import cn.nolifem.api.ICalculator;
import cn.nolifem.api.attributes.GeneralAttribute;
import cn.nolifem.core.ModProps;
import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.attributes.player.Dexterity;
import cn.nolifem.util.Lang;

public class Defence extends GeneralAttribute implements Cloneable{
	@Override
	public void addFunction(ICalculator calculator) {
		calculator.<Double>addFunction(SIGMA, this.getClass().getSimpleName(), (input) -> input + this.getValue());
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

	public int getDisplayPreference(){ return 2;}
}