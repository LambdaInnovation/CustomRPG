package cn.nolifem.attributes.player;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import cn.nolifem.api.IAttributeDealer;
import cn.nolifem.api.IOriginalModifier;
import cn.nolifem.attributes.general.BaseDamage;
import cn.nolifem.attributes.general.Health;
import cn.nolifem.attributes.general.PhysicalDamage;
import cn.nolifem.attributes.general.Vitality;

public class Strength extends PlayerAttribute implements Cloneable, IOriginalModifier{

	@Override
	public void addFunction(IAttributeDealer calculator) {
		calculator.<Double>addFunctionSIGMA(Health.class.getSimpleName(), (input) -> input + 14.0D + this.getValue());
		calculator.<Double>addFunctionSIGMA(Vitality.class.getSimpleName(), (input) -> input + 20.0D + this.getValue());
		calculator.<Double>addFunctionSIGMA(BaseDamage.class.getSimpleName(), (input) -> input + 1.0D);

		calculator.<Double>addFunctionPAI(BaseDamage.class.getSimpleName(), (input) ->  input * (1.0D + this.getValue()/100.0D) );
		calculator.<Double>addFunctionPAI(PhysicalDamage.class.getSimpleName(), (input) -> input * (1.0D + this.getValue()/100.0D) );
	}
	
	@Override
	public void applyOriginalModify(IAttributeDealer calculator, EntityLivingBase living){
		IAttributeInstance attrhp = living.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
		attrhp.setBaseValue(14.0D + this.getValue());
	}
	
	@Override
	public void addInfo(List arraylist) {
	}

	public int getPreference(){ return 5;}
}
