package cn.nolifem.attributes.general;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IAttributeCalculator;
import cn.nolifem.api.IOriginalModifier;
import cn.nolifem.attributes.player.Dexterity;
import cn.nolifem.util.Lang;

public class MovementSpeed extends GeneralAttribute implements Cloneable, IOriginalModifier{
	
	@Override
	public void addCalc(IAttributeCalculator calculator) {
		calculator.<Double>addCalculationSIGMA(this.getClass().getSimpleName(), (input) -> input + this.getValue());
	}
	
	@Override	
	public void addInfo(List arraylist){
		arraylist.add(EnumChatFormatting.BLUE + Lang.translate(super.getLang()) + " " + this.getValue()*100 + "%");
	}

	@Override
	public void applyOriginalModify(IAttributeCalculator calculator, EntityLivingBase living){
		IAttributeInstance attr = living.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
		AttributeModifier modifier = new AttributeModifier(SPEED_MODIFIER, "speed_up", 
				calculator.applyCalc(this.getClass().getSimpleName(), 0.0D), 1);
		if(attr.getModifier(SPEED_MODIFIER) != null)
			attr.removeModifier(modifier);
		attr.applyModifier(modifier);
	}
	
	@Override
	public Class<? extends IAttributeCR> getPlayerAttrClass() {
		return Dexterity.class;
	}

	public int getPreference(){ return 2;}
}