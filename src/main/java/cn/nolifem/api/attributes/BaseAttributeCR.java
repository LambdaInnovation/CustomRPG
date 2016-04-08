package cn.nolifem.api.attributes;

import java.util.List;

import cn.lambdalib.s11n.SerializeIncluded;
import cn.nolifem.api.IAttributeCR;

/**Base Attribute
 * @author Nolife_M
 */
public abstract class BaseAttributeCR implements IAttributeCR, Cloneable{

	@SerializeIncluded
	private boolean isTag = false;
	
	public BaseAttributeCR(){}

	public void setTag(){
		this.isTag = true;
	}

	public boolean isTag(){
		return this.isTag;
	}
	
	public abstract void addInfo(List arraylist);
}
