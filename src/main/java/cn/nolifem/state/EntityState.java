package cn.nolifem.state;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.nbt.NBTTagCompound;
import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.util.datapart.DataPart;
import cn.lambdalib.util.datapart.EntityData;
import cn.lambdalib.util.datapart.RegDataPart;

@Registrant
@RegDataPart(value=EntityLivingBase.class)
public class EntityState extends DataPart<EntityLivingBase> {

    public EntityState() {
		setTick(true);
	}

    public void tick(){}
    
    public void updateAttrListForCalc(){}

	//Attack
	public boolean isReadyToAttack(){
		return true;
	}

	//Damage
	public double getAttackDmg() {
		IAttributeInstance attr = getEntity().getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage);
		System.out.println("get Dmg" + attr.getAttributeValue());
		return attr.getAttributeValue();
	}
	//Defence
	public double reducePhyDmg(double dmg) {
		return dmg;
	}

	public void fromNBT(NBTTagCompound tag) {}

	public void toNBT(NBTTagCompound tag) {}
}
