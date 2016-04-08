package cn.nolifem.attributes.player;

import java.util.List;

import cn.nolifem.api.ICalculator;
import cn.nolifem.api.attributes.PlayerAttribute;
import cn.nolifem.attributes.general.*;
import cn.nolifem.state.EntityState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class Strength extends PlayerAttribute implements Cloneable{

	@Override
	public <T> void addToDealer(T dealer){
		super.addToDealer(dealer);

		if(dealer instanceof EntityState){
			IAttributeInstance attrhp = ((EntityState) dealer).getEntity().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
			attrhp.setBaseValue(14.0D + this.getValue());
		}
	}

	@Override
	public void addFunction(ICalculator calculator) {
		calculator.<Double>addFunction(SIGMA, Health.class.getSimpleName(), (input) -> input + 14.0D + this.getValue());
		calculator.<Double>addFunction(SIGMA, Vitality.class.getSimpleName(), (input) -> input + 20.0D + this.getValue());
		calculator.<Double>addFunction(SIGMA, BaseDamage.class.getSimpleName(), (input) -> input + 1.0D);

		calculator.<Double>addFunction(PAI, BaseDamage.class.getSimpleName(), (input) ->  input * (1.0D + this.getValue()/100.0D) );
		calculator.<Double>addFunction(PAI, PhysicalDamage.class.getSimpleName(), (input) -> input * (1.0D + this.getValue()/100.0D) );
	}

	@Override
	public void addInfo(List arraylist) {
	}

	public int getDisplayPreference(){ return 5;}
}
