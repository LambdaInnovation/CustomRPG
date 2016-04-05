package cn.nolifem.attributes.general;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IAttributeCalculator;
import cn.nolifem.api.IAttributeContainer;
import cn.nolifem.attributes.BaseAttributeCR;
import cn.nolifem.attributes.player.Dexterity;
import cn.nolifem.util.Lang;

public class AttackSpeed extends GeneralAttribute implements Cloneable{

	@Override
	public void addCalc(IAttributeCalculator calculator) {
		calculator.<Double>addCalculationSIGMA(this.getClass().getSimpleName(), (input) -> input * 20.0d/(1.0D + this.getValue()));
	}
	
	@Override
	public void addInfo(List arraylist){
		EnumChatFormatting color = 20/this.getValue() < 1 ? EnumChatFormatting.RED 
				: 20/this.getValue() > 2 ? EnumChatFormatting.GREEN 
				: EnumChatFormatting.YELLOW;
		arraylist.add(color +  Lang.translate(super.getLang()) + " " + getValue() * 100.0 +"%");
	}

	@Override
	public Class<? extends IAttributeCR> getPlayerAttrClass() {
		return Dexterity.class;
	}

	public int getPreference(){ return 2;}
}
