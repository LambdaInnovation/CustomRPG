package cn.nolifem.api.attributes;

import java.util.List;
import java.util.UUID;

import cn.lambdalib.s11n.SerializeIncluded;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.ICalculator;
import cn.nolifem.core.ModProps;
import cpw.mods.fml.common.Mod;

/**Base Attribute
 * @author Nolife_M
 */
public abstract class BaseAttributeCR implements IAttributeCR, Cloneable{

	@SerializeIncluded
	private boolean isTag = false;

	protected int SIGMA = ModProps.SIGMA;
	protected int PAI = ModProps.PAI;
	protected UUID SPEED_MODIFIER = ModProps.SPEED_MODIFIER;

	public BaseAttributeCR(){}

	@Override
	public <T> void addToDealer(T dealer){
		if(dealer instanceof  ICalculator)
			this.addFunction( (ICalculator)dealer );
	}

    public int getApplyPreference(){
        return 0;
    }

	public abstract void addFunction(ICalculator calculator);

	public void setTag(){
		this.isTag = true;
	}

	public boolean isTag(){
		return this.isTag;
	}

	public abstract void addInfo(List arraylist);
}
