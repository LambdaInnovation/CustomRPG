package cn.nolifem.attributes.general;

import java.util.List;

import cn.nolifem.api.IAttributeDealer;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.attributes.player.Strength;

public class BaseDamage extends GeneralAttribute implements Cloneable{
	
	@Override
	public void addFunction(IAttributeDealer calculator) {
		calculator.<Double>addFunctionSIGMA(this.getClass().getSimpleName(), (input) -> input + this.getValue());
	}

	@Override
	public void addInfo(List arraylist) {}

/*	@Override
	public void applyOriginalModify(IAttributeDealer calculator,
			EntityLivingBase living) {
		IAttributeInstance attrdmg = living.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage);
		attrdmg.setBaseValue(calculator.calc(BaseDamage.class.getSimpleName(), 0.0D));
	}*/

	@Override
	public Class<? extends IAttributeCR> getPlayerAttrClass() {
		return Strength.class;
	}

	public int getPreference(){ return 2;}

}
