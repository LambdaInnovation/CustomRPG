package cn.nolifem.attributes;

import java.util.List;

import cn.nolifem.api.IAttributeCR;

/**Base Attribute
 * @author Nolife_M
 */
public abstract class BaseAttributeCR implements IAttributeCR, Cloneable{

	private boolean isTag = false;
	
	public BaseAttributeCR(){}

	public IAttributeCR getClone(){
		try {
			return (IAttributeCR) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setTag(){
		this.isTag = true;
	}

	public boolean isTag(){
		return this.isTag;
	}
	
	public abstract void addInfo(List arraylist);
}
