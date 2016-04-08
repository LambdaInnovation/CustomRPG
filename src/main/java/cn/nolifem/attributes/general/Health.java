package cn.nolifem.attributes.general;

import java.util.List;

import cn.nolifem.api.ICalculator;
import cn.nolifem.api.attributes.GeneralAttribute;
import cn.nolifem.state.EntityState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.attributes.player.Strength;
import cn.nolifem.util.Lang;

public class Health extends GeneralAttribute implements Cloneable{

	@Override
	public <T> void addToDealer(T dealer){
		super.addToDealer(dealer);
		if(dealer instanceof EntityState){
			IAttributeInstance attr = ((EntityState) dealer).getEntity().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
			attr.setBaseValue(((EntityState) dealer).getCalcValue(this.getClass(), 0.0D));
		}
	}

	@Override
	public void addFunction(ICalculator calculator) {
		calculator.<Double>addFunction(SIGMA, this.getClass().getSimpleName(), (input) -> input + this.getValue());
	}

	@Override
	public void addInfo(List arraylist){
		arraylist.add(EnumChatFormatting.RED +  Lang.translate(super.getLang()) + " " +  this.getValue());
	}
	
	@Override
	public Class<? extends IAttributeCR> getPlayerAttrClass() {
		return Strength.class;
	}

	public int getDisplayPreference(){ return 2;}
}
