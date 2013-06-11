package ccm.deathTimer.timerTypes;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.DimensionManager;
import ccm.deathTimer.utils.FunctionHelper;
import ccm.deathTimer.utils.lib.Archive;
import cpw.mods.fml.common.network.PacketDispatcher;

/**
 * Use to make a timer with objective.
 * 
 * @author Dries007
 */
public class PointTimer extends BasicTimer
{
    
    public static final int PACKETID = 1;
    
    public int              X, Y, Z, dim;
    
    @Override
    public void sendAutoUpdate()
    {
        PacketDispatcher.sendPacketToAllInDimension(this.getPacket(), this.dim);
    }
    
    @Override
    public Packet250CustomPayload getPacket()
    {
        final ByteArrayOutputStream streambyte = new ByteArrayOutputStream();
        final DataOutputStream stream = new DataOutputStream(streambyte);
        
        try
        {
            stream.writeInt(PointTimer.PACKETID);
            
            stream.writeUTF(this.getLabel());
            stream.writeInt(this.getTime());
            
            stream.writeBoolean(this.useSound());
            if (this.useSound())
            {
                stream.writeUTF(this.getSoundName());
                stream.writeFloat(this.getSoundVolume());
                stream.writeFloat(this.getSoundPitch());
            }
            
            stream.writeInt(this.X);
            stream.writeInt(this.Y);
            stream.writeInt(this.Z);
            stream.writeInt(this.dim);
            
            stream.close();
            streambyte.close();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        
        return PacketDispatcher.getPacket(Archive.MOD_CHANNEL_TIMERS, streambyte.toByteArray());
    }
    
    @Override
    public ITimerBase getUpdate(final DataInputStream stream) throws IOException
    {
        final PointTimer data = new PointTimer();
        
        data.label = stream.readUTF();
        data.time = stream.readInt();
        
        if (stream.readBoolean())
        {
            data.soundName = stream.readUTF();
            data.soundVolume = stream.readFloat();
            data.soundPitch = stream.readFloat();
        }
        
        data.X = stream.readInt();
        data.Y = stream.readInt();
        data.Z = stream.readInt();
        data.dim = stream.readInt();
        
        return data;
    }
    
    @Override
    public ArrayList<String> getTimerString(final ICommandSender sender)
    {
        final ArrayList<String> text = super.getTimerString(sender);
        if (sender instanceof EntityPlayer)
        {
            final EntityPlayer player = (EntityPlayer) sender;
            text.add(player.dimension == this.dim ? this.getDistance(player) + "m " + FunctionHelper.getArrowTo(this.X, this.Z, player) : DimensionManager.getWorld(this.dim).getProviderName());
        }
        return text;
    }
    
    public int getDistance(final EntityPlayer player)
    {
        final double x = player.posX - this.X;
        final double y = player.posY - this.Y;
        final double z = player.posZ - this.Z;
        
        return (int) Math.sqrt(x * x + y * y + z * z);
    }
}