package cn.nolifem.api;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;

public interface IOriginalModifier{
		
	public UUID SPEED_MODIFIER = UUID.fromString("2af118ed-281e-4e74-a505-a32b979c9578");
	
	public void applyOriginalModify(IAttributeDealer calculator, EntityLivingBase living);
}
