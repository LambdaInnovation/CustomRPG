package cn.nolifem.state;

import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.util.datapart.RegDataPart;
import cn.lambdalib.util.mc.ControlOverrider;
import cn.nolifem.CustomRPG;
import cn.nolifem.api.*;
import cn.nolifem.api.attributes.GeneralAttribute;
import cn.nolifem.attributes.general.*;
import cn.nolifem.attributes.player.Dexterity;
import cn.nolifem.api.attributes.PlayerAttribute;
import cn.nolifem.attributes.player.Proficient;
import cn.nolifem.attributes.player.Strength;
import cn.nolifem.core.ModProps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.*;
import java.util.function.Function;


@Registrant
@RegDataPart(value=EntityPlayer.class)
public class PlayerState extends EntityState implements IAttributeContainer, ICalculator {

	private int respawnUpdateTick = 10;

    private Random r = new Random();

	public int attackCD = 0;

	private Map<String, List<Function>> calcMapSIGMA = new HashMap<>();
	private Map<String, List<Function>> calcMapPAI = new HashMap<>();
	
	private List<IAttributeCR> attrList = new ArrayList<>();

	private final Class[] playerAttrs = new Class[]{
		Strength.class,
		Dexterity.class,
		Proficient.class	
	};

	public PlayerState() {
		setTick(true);
		setNBTStorage();
		initAttr();
	}

	@Override
	public void initAttr() {
		try {
			//init all map
			for(Class clazz : playerAttrs){
				this.getAttrMap().put(clazz.getSimpleName(), (PlayerAttribute) CustomRPG.construct(clazz.getName()));
			}
		} catch (InstantiationException | IllegalAccessException| ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

    public void tick() {
    	super.tick();
    	if(respawnUpdateTick > 0){
    		respawnUpdateTick--;
            updateDealedAttr();
    	}
    	KeyBinding bind = Minecraft.getMinecraft().gameSettings.keyBindAttack;
    	ItemStack weapon = getPlayer().getHeldItem();

    	if(getPlayer().isSwingInProgress){
    		this.attacking();
            //debug();
            /*System.out.println( calcMapSIGMA.get(PhysicalDamage.class.getSimpleName()) );
            System.out.println( calcMapPAI.get(PhysicalDamage.class.getSimpleName()) );*/
    	}
    	//testing
    	((PlayerAttribute)this.getAttr(Strength.class)).setValue(40);
    	((PlayerAttribute)this.getAttr(Dexterity.class)).setValue(40);
    	((PlayerAttribute)this.getAttr(Proficient.class)).setValue(0);

    	if(this.attackCD > 0){
    		this.attackCD--;
    		//System.out.println((isClient() ? "Client" : "Server") + "----" + this.attackCD);
    	}	
			
		if(isClient()){
			ControlOverrider.override("stop_attack", bind.getKeyCode());
			if(weapon == null || !(weapon.getItem() instanceof IAttributeContainer) || isReadyToAttack()){
				ControlOverrider.endOverride("stop_attack");
			}
		}
    }

	public EntityPlayer getPlayer(){
		return (EntityPlayer)this.getEntity();
	}
    //FinalValue
    @Override
    public double getCalcValue(Class<? extends GeneralAttribute> clazz, double value){
    	value = calc(ModProps.SIGMA, clazz.getSimpleName(), value);
        value = calc(ModProps.PAI, clazz.getSimpleName(), value);
        return value;
    }

    //Update Attr
    @Override
    public void cleanDealedAttr(){
        super.cleanDealedAttr();

        calcMapSIGMA.clear();
        calcMapPAI.clear();
	}

    @Override
    public void addDealedAttr(){
        super.addDealedAttr();

		//add player attrs
    	for(IAttributeCR attr : this.getAttrMapAsList()){
			attr.addToDealer(this);
    	}
    }   

    //Deal Attacking
	@Override
    public boolean isReadyToAttack(){
    	return attackCD == 0;
    }

	private void attacking() {
        updateDealedAttr();
		this.attackCD = (int)(Math.round(this.getCalcValue(AttackSpeed.class, 0)));
		if(!isClient())
			sync();
	}

    //GetDamage
    @Override
	public double getAttackDmg() {
		double dmg;
		if(this.getPlayer().getHeldItem() == null || !(this.getPlayer().getHeldItem().getItem() instanceof IAttributeContainer))
			dmg = getCalcValue(BaseDamage.class, 0);
		else
			dmg = getCalcValue(BaseDamage.class, 0) + getCalcValue(PhysicalDamage.class, 0);
        System.out.println("DMG" + dmg);
        double cRate = getCalcValue(CriticalRate.class, 0);
        double cMulti = getCalcValue(CriticalMulti.class, 0);
        double rate = r.nextDouble();

        if(rate < cRate){
            dmg = dmg * cMulti;
            System.out.println("Critical Hit!" + "R:" + cRate + "M:" + cMulti);
        }
		System.out.println("get Dmg = " + dmg);
		return dmg;
	}

    //Calculator
    private Map<String, List<Function>> getCalcMapType(int caltype){
        if(caltype == ModProps.SIGMA){
            return calcMapSIGMA;
        }else if(caltype == ModProps.PAI){
            return calcMapPAI;
        }
        return null;
    }

    @Override
    public <S> void addFunction(int caltype, String key, Function<S, S> function){
        Map<String, List<Function>> map = getCalcMapType(caltype);

        if(map != null){
            List<Function> list = map.get(key);
            if(list == null){
                list = new ArrayList<>();
                map.put(key, list);
            }
            list.add(function);
        }
    }

    @Override
    public <T> T calc(int caltype, String key, T value){
        Map<String, List<Function>> map = getCalcMapType(caltype);

        List<Function> list = map.get(key);
        if(list != null){
            for(Function _f : list){
                Function<T, T>f = (Function<T, T>) _f;
                value = f.apply(value);
            }
        }
        return value;
    }

    //Defence
    @Override
    public double reduceComingDmg(double dmg) {
        System.out.println("减免后伤害" + dmg * (1.0D - getCalcValue(Defence.class, 0)));
        return dmg * (1.0D - getCalcValue(Defence.class, 0));
    }
	//NBT
    public void fromNBT(NBTTagCompound tag) {
    	super.fromNBT(tag);
    	this.attackCD = tag.getInteger("AttackCD");
    	NBTTagCompound atag = tag.getCompoundTag("Attr");
    	((PlayerAttribute)getAttr(Strength.class)).setValue(atag.getDouble("Strength"));
    	((PlayerAttribute)getAttr(Dexterity.class)).setValue(atag.getDouble("Dexterity"));
    	((PlayerAttribute)getAttr(Proficient.class)).setValue(atag.getDouble("Proficient"));
        updateDealedAttr();
    }

    public void toNBT(NBTTagCompound tag) {
    	tag.setInteger("AttackCD", this.attackCD);
    	NBTTagCompound atag = tag.getCompoundTag("Attr");
    	atag.setDouble("Strength", ((PlayerAttribute)getAttr(Strength.class)).getValue());
    	atag.setDouble("Dexterity", ((PlayerAttribute)getAttr(Dexterity.class)).getValue());
    	atag.setDouble("Proficient", ((PlayerAttribute)getAttr(Proficient.class)).getValue());
    	tag.setTag("Attr", atag);
    }

    private void debug(){
        System.out.println("========================Stat===========================");
        System.out.println(BaseDamage.class.getSimpleName() + getCalcValue(BaseDamage.class , 0));
        System.out.println(Health.class.getSimpleName() + getCalcValue(Health.class , 0));
        System.out.println(Vitality.class.getSimpleName() + getCalcValue(Vitality.class , 0));

        System.out.println(AttackSpeed.class.getSimpleName() + getCalcValue(AttackSpeed.class , 0));
        System.out.println(Defence.class.getSimpleName() + getCalcValue(Defence.class , 0));
        System.out.println(MovementSpeed.class.getSimpleName() + getCalcValue(MovementSpeed.class , 0));

        System.out.println(CriticalMulti.class.getSimpleName() + getCalcValue(CriticalMulti.class , 0));
        System.out.println(CriticalRate.class.getSimpleName() + getCalcValue(CriticalRate.class , 0));
        System.out.println(PrepareSpeed.class.getSimpleName() + getCalcValue(PrepareSpeed.class , 0));
        System.out.println("=======================================================");
    }
}
