package cn.nolifem.state;

import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IAttributeContainer;
import cn.nolifem.api.ICalculator;
import cn.nolifem.api.IStateItem;
import cn.nolifem.api.attributes.BuffPlacer;
import cn.nolifem.api.attributes.Effect;
import cn.nolifem.api.attributes.GeneralAttribute;
import cn.nolifem.api.util.SortApply;
import cn.nolifem.api.util.SortDisplay;
import cn.nolifem.event.PlayerAttackEvent;
import cn.nolifem.items.ItemGrindTool;
import cn.nolifem.state.item.ItemState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.util.datapart.DataPart;
import cn.lambdalib.util.datapart.RegDataPart;

import java.util.*;
import java.util.function.Function;

@Registrant
@RegDataPart(value=EntityLivingBase.class)
public class EntityState extends DataPart<EntityLivingBase> implements IAttributeContainer,ICalculator {

	static final Comparator displayComp = SortDisplay.INSTANCE;
	static final Comparator applyComp = SortApply.INSTANCE;

	private Map<String, IAttributeCR> attrMap = new HashMap<>();
	private List<BuffPlacer> buffPlacerList = new ArrayList<>();
	private List<Effect> effectList = new ArrayList<>();

	private StringBuilder[] equipmentsUUID = new StringBuilder[5];

    public EntityState() {
		setTick(true);
	}

    public void tick(){}

	//DealAttr
	public void updateDealedAttr(){
		cleanDealedAttr();
		addDealedAttr();
	}

	public void cleanDealedAttr(){
		buffPlacerList.clear();
		effectList.clear();
	}
    public void addDealedAttr(){
		ItemStack equipment;
		for(int i = 0 ; i < 5; i++){
			equipment = this.getEntity().getEquipmentInSlot(i);
			if(equipment != null && equipment.getItem() instanceof IStateItem && !(equipment.getItem() instanceof ItemGrindTool) ){
				ItemState state = ModuleState.get(equipment, this.getEntity());
				for(IAttributeCR attr : state.getAttrMapAsList()){
					attr.addToDealer(this);
				}
			}
		}

	}
	//Equipments
	private boolean updateEquipmentsUUID(){
		boolean flag = false;
		ItemStack equipment;
		for(int i = 0 ; i < 5; i++){
			equipment = i == 0 ? this.getEntity().getHeldItem() : this.getEntity().getEquipmentInSlot(i - 1);
			if( !equipmentsUUID[i].equals(ItemState.getStackID(equipment)) ){
				equipmentsUUID[i].delete(0, equipmentsUUID[i].length());
				equipmentsUUID[i].append( ItemState.getStackID(equipment) );
				flag = true;
			}
		}
		return flag;
	}
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
	public double reduceComingDmg(double dmg) {
		return dmg;
	}

	public void fromNBT(NBTTagCompound tag) {}

	public void toNBT(NBTTagCompound tag) {}

	//Interface
	@Override
	public void initAttr() {}

	@Override
	public Map<String, IAttributeCR> getAttrMap() {
		return this.attrMap;
	}

	//FinalValue
	public double getCalcValue(Class<? extends GeneralAttribute> clazz, double value){
		return value;
	}

	//Deal buff
	public void addBuffPlacer(BuffPlacer placer){
		buffPlacerList.add(placer);
	}

	public void addEffect(Effect effect){
		effectList.add(effect);
	}

	public void placeBuff(PlayerAttackEvent e){
		buffPlacerList.sort(applyComp);
		for(BuffPlacer buffPlacer : buffPlacerList){
			buffPlacer.place(e);
		}
	}

	public void tickEffect(PlayerAttackEvent e){
		effectList.sort(applyComp);
		for(Effect effect : effectList){
			effect.tick(e);
		}
	}


	//Calculator
	@Override
	public <S> void addFunction(int caltype, String key, Function<S, S> function){}

	@Override
	public <T> T calc(int caltype, String key, T value) {
		return value;
	}

}
