package cn.nolifem.attributes.general;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IAttributeCalculator;
import cn.nolifem.api.IAttributeContainer;
import cn.nolifem.api.IOriginalModifier;
import cn.nolifem.attributes.player.Strength;

public class BaseDamage extends GeneralAttribute implements Cloneable{
	
	@Override
	public void addCalc(IAttributeCalculator calculator) {
		calculator.<Double>addCalculationSIGMA(this.getClass().getSimpleName(), (input) -> input + this.getValue());
	}

	@Override
	public void addInfo(List arraylist) {}

/*	@Override
	public void applyOriginalModify(IAttributeCalculator calculator,
			EntityLivingBase living) {
		IAttributeInstance attrdmg = living.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage);
		attrdmg.setBaseValue(calculator.applyCalc(BaseDamage.class.getSimpleName(), 0.0D));
	}*/

	@Override
	public Class<? extends IAttributeCR> getPlayerAttrClass() {
		return Strength.class;
	}

	public int getPreference(){ return 2;}

}
