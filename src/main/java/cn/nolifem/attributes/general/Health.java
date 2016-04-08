package cn.nolifem.attributes.general;

import java.util.List;

import cn.nolifem.api.IAttributeDealer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IOriginalModifier;
import cn.nolifem.attributes.player.Strength;
import cn.nolifem.util.Lang;

public class Health extends GeneralAttribute implements Cloneable, IOriginalModifier{
	
	@Override
	public void addFunction(IAttributeDealer calculator) {
		calculator.<Double>addFunctionSIGMA(this.getClass().getSimpleName(), (input) -> input + this.getValue());
	}

	@Override
	public void addInfo(List arraylist){
		arraylist.add(EnumChatFormatting.RED +  Lang.translate(super.getLang()) + " " +  this.getValue());
	}

	@Override
	public void applyOriginalModify(IAttributeDealer calculator, EntityLivingBase living){
		IAttributeInstance attr = living.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
		attr.setBaseValue(calculator.calc(this.getClass().getSimpleName(), 0.0D));
	}
	
	@Override
	public Class<? extends IAttributeCR> getPlayerAttrClass() {
		return Strength.class;
	}

	public int getPreference(){ return 2;}
}
