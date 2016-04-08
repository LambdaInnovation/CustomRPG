package cn.nolifem.attributes.general;

import java.util.List;

import cn.nolifem.api.ICalculator;
import cn.nolifem.api.attributes.GeneralAttribute;
import cn.nolifem.core.ModProps;
import cn.nolifem.state.EntityState;
import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.attributes.player.Proficient;
import cn.nolifem.util.Lang;

public class CriticalMulti extends GeneralAttribute implements Cloneable{

	@Override
	public void addFunction(ICalculator calculator) {
			calculator.<Double>addFunction(SIGMA, this.getClass().getSimpleName(), (input) -> input + this.getValue());
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

	public int getDisplayPreference(){ return 2;}
}