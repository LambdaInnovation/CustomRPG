package cn.nolifem.attributes.general;

import java.util.List;

import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.ICalculator;
import cn.nolifem.api.attributes.GeneralAttribute;
import cn.nolifem.attributes.player.Strength;
import cn.nolifem.core.ModProps;
import cn.nolifem.state.EntityState;

public class BaseDamage extends GeneralAttribute implements Cloneable{

	@Override
	public void addFunction(ICalculator calculator) {
			calculator.<Double>addFunction(SIGMA, this.getClass().getSimpleName(), (input) -> input * 20.0d/(1.0D + this.getValue()));
	}

	@Override
	public void addInfo(List arraylist) {}

	@Override
	public Class<? extends IAttributeCR> getPlayerAttrClass() {
		return Strength.class;
	}

	public int getDisplayPreference(){ return 2;}

}
