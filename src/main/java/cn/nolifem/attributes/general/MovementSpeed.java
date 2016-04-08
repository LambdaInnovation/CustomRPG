package cn.nolifem.attributes.general;

import java.util.List;

import cn.nolifem.api.ICalculator;
import cn.nolifem.api.attributes.GeneralAttribute;
import cn.nolifem.state.EntityState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.EnumChatFormatting;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.attributes.player.Dexterity;
import cn.nolifem.util.Lang;

public class MovementSpeed extends GeneralAttribute implements Cloneable{

	@Override
	public <T> void addToDealer(T dealer){
		super.addToDealer(dealer);

		if(dealer instanceof EntityState){
			IAttributeInstance attr = ((EntityState) dealer).getEntity().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
			AttributeModifier modifier = new AttributeModifier(SPEED_MODIFIER, "speed_up",
					((EntityState) dealer).getCalcValue(this.getClass(), 0.0D), 1);
			if(attr.getModifier(SPEED_MODIFIER) != null)
				attr.removeModifier(modifier);
			attr.applyModifier(modifier);
		}
	}

	@Override
	public void addFunction(ICalculator calculator) {
		calculator.<Double>addFunction(SIGMA, this.getClass().getSimpleName(), (input) -> input + this.getValue());
	}
	
	@Override	
	public void addInfo(List arraylist){
		arraylist.add(EnumChatFormatting.BLUE + Lang.translate(super.getLang()) + " " + this.getValue()*100 + "%");
	}

	@Override
	public Class<? extends IAttributeCR> getPlayerAttrClass() {
		return Dexterity.class;
	}

	public int getDisplayPreference(){ return 2;}
}