package cn.nolifem.attributes.player;

import java.util.List;

import cn.nolifem.api.IAttributeDealer;
import cn.nolifem.api.attributes.PlayerAttribute;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import cn.nolifem.api.IOriginalModifier;
import cn.nolifem.attributes.general.AttackSpeed;
import cn.nolifem.attributes.general.Defence;
import cn.nolifem.attributes.general.MovementSpeed;

public class Dexterity extends PlayerAttribute implements Cloneable, IOriginalModifier{

	@Override
	public void addFunction(IAttributeDealer calculator) {
		calculator.<Double>addFunctionSIGMA(Defence.class.getSimpleName(), (input) -> input + this.getValue() * 0.005);
		calculator.<Double>addFunctionSIGMA(AttackSpeed.class.getSimpleName(), (input) -> input + (1- this.getValue() * 0.005));
		calculator.<Double>addFunctionSIGMA(MovementSpeed.class.getSimpleName(), (input) -> input + this.getValue() * 0.0075);
	}
	
	@Override
	public void addInfo(List arraylist) {
		
	}

	@Override
	public void applyOriginalModify(IAttributeDealer calculator, EntityLivingBase living){
		IAttributeInstance attr = living.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
		AttributeModifier modifier = new AttributeModifier(SPEED_MODIFIER, "speed_up", 
				calculator.calc(MovementSpeed.class.getSimpleName(), 0.0D), 1);
		if(attr.getModifier(SPEED_MODIFIER) != null)
			attr.removeModifier(modifier);
		attr.applyModifier(modifier);
	}

	public int getPreference(){ return 5;}
}
