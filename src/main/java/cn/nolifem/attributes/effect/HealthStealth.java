package cn.nolifem.attributes.effect;

import cn.nolifem.api.IAttributeCalculator;
import cn.nolifem.attributes.BaseAttributeCR;
import cn.nolifem.event.PlayerAttackEvent;
import cn.nolifem.util.Lang;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

/**
 * Created by Nolife_M on 2016/3/28.
 */
public class HealthStealth extends AttackEffect {

    private double percent = 0;

    public void applyEffect(PlayerAttackEvent e){
        System.out.println("DMG:" + e.dmg);
        System.out.println("Stealth Percent:" + percent);
        double amount = e.dmg * percent;
        double result = e.entityPlayer.getHealth() + amount;
        if(result > e.entityPlayer.getMaxHealth()){
            result = e.entityPlayer.getMaxHealth();
        }
        System.out.println("Stealth HP:" + amount);
        e.entityPlayer.setHealth((float) result);
    }

    @Override
    public void addInfo(List arraylist) {
        arraylist.add(EnumChatFormatting.DARK_GREEN +  Lang.translateFormatted(super.getLang(), (this.percent*100 + "%")));
    }

    public int getPreference(){ return 4;}

    @Override
    public void addCalc(IAttributeCalculator calculator) {

    }
}

