package cn.nolifem.attributes.general;

import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IAttributeCalculator;
import cn.nolifem.api.IAttributeContainer;
import cn.nolifem.attributes.player.Strength;
import cn.nolifem.util.Lang;

public class Vitality extends GeneralAttribute implements Cloneable{

	@Override
	public void addCalc(IAttributeCalculator calculator) {
		calculator.<Double>addCalculationSIGMA(this.getClass().getSimpleName(), (input) -> input + this.getValue());
	}
	
	@Override
	public void addInfo(List arraylist){
		arraylist.add(EnumChatFormatting.GREEN +  Lang.translate(super.getLang()) + " + " +  this.getValue() + "(WIP)");
	}

	@Override
	public Class<? extends IAttributeCR> getPlayerAttrClass() {
		return Strength.class;
	}

	public int getPreference(){ return 2;}
}
