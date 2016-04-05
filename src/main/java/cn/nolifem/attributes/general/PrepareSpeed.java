package cn.nolifem.attributes.general;

import java.util.List;

import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IAttributeCalculator;
import cn.nolifem.attributes.player.Proficient;
import cn.nolifem.util.Lang;

public class PrepareSpeed extends GeneralAttribute implements Cloneable{
	
	@Override
	public void addCalc(IAttributeCalculator calculator) {
		calculator.<Double>addCalculationSIGMA(this.getClass().getSimpleName(), (input) -> input + this.getValue());
	}
	
	@Override
	public void addInfo(List arraylist){
		arraylist.add(EnumChatFormatting.BLUE + Lang.translate(super.getLang()) + " " + this.getValue()*100 + "%" + "(WIP)");
	}

	@Override
	public Class<? extends IAttributeCR> getPlayerAttrClass() {
		return Proficient.class;
	}

	public int getPreference(){ return 2;}
}