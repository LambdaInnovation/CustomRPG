package cn.nolifem.attributes;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IAttributeCalculator;
import cn.nolifem.api.IAttributeContainer;

/**Base Attribute
 * @author Nolife_M
 */
public abstract class BaseAttributeCR implements IAttributeCR, Cloneable{

	private boolean isTag = false;
	
	public BaseAttributeCR(){}

	public void setTag(){
		this.isTag = true;
	}
	
	public IAttributeCR getClone(){
		try {
			return (IAttributeCR) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean isTag(){
		return this.isTag;
	}
	
	public abstract void addInfo(List arraylist);
}
