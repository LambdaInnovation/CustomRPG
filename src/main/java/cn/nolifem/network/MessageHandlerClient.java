package cn.nolifem.network;

import net.minecraft.client.Minecraft;
import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.annoreg.mc.RegMessageHandler;
import cn.nolifem.network.message.MessageAttackCD;
import cn.nolifem.state.PlayerState;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

@Registrant
@RegMessageHandler(msg = MessageAttackCD.class, side = RegMessageHandler.Side.CLIENT)
public class MessageHandlerClient implements IMessageHandler<MessageAttackCD, IMessage>{

	@Override
	public IMessage onMessage(MessageAttackCD message, MessageContext ctx) {
		System.out.println("Client Pack");
		PlayerState.get(Minecraft.getMinecraft().thePlayer).attackCD = message.attackCD;
		return null;
	}

}
