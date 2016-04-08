package cn.nolifem.attributes.player;

import java.util.List;

import cn.nolifem.api.ICalculator;
import cn.nolifem.api.attributes.PlayerAttribute;
import cn.nolifem.core.ModProps;
import cn.nolifem.state.EntityState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import cn.nolifem.attributes.general.AttackSpeed;
import cn.nolifem.attributes.general.Defence;
import cn.nolifem.attributes.general.MovementSpeed;

public class Dexterity extends PlayerAttribute implements Cloneable{

	@Override
	public <T> void addToDealer(T dealer){
		super.addToDealer(dealer);

		if(dealer instanceof EntityState){
			IAttributeInstance attr = ((EntityState) dealer).getEntity().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
			AttributeModifier modifier = new AttributeModifier(ModProps.SPEED_MODIFIER, "speed_up",
					((EntityState) dealer).getCalcValue(MovementSpeed.class, 0.0D), 1);
			if(attr.getModifier(ModProps.SPEED_MODIFIER) != null)
				attr.removeModifier(modifier);
			attr.applyModifier(modifier);
		}
	}

	@Override
	public void addFunction(ICalculator calculator) {
		calculator.<Double>addFunction(SIGMA, Defence.class.getSimpleName(), (input) -> input + this.getValue() * 0.005);
		calculator.<Double>addFunction(SIGMA, AttackSpeed.class.getSimpleName(), (input) -> input + (1- this.getValue() * 0.005));
		calculator.<Double>addFunction(SIGMA, MovementSpeed.class.getSimpleName(), (input) -> input + this.getValue() * 0.0075);
	}
	
	@Override
	public void addInfo(List arraylist) {
		
	}

	public int getDisplayPreference(){ return 5;}
}
