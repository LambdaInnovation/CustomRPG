package cn.nolifem.network.message;

import io.netty.buffer.ByteBuf;
import cn.nolifem.state.PlayerState;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageAttackCD implements IMessage {
	
	public int attackCD;
	
	public MessageAttackCD(){}
	
	public MessageAttackCD(PlayerState state){
		this.attackCD = state.attackCD;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.attackCD = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.attackCD);
	}

}
