package ccm.deathTimer.timerTypes;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.DimensionManager;
import ccm.deathTimer.utils.FunctionHelper;
import ccm.deathTimer.utils.lib.Archive;
import cpw.mods.fml.common.network.PacketDispatcher;

public class PointTimer extends BasicTimer
{
    public static final int PACKETID = 1;
    
    public int  X, Y, Z, dim;
    
    @Override
    public void sendAutoUpdate()
    {
        PacketDispatcher.sendPacketToAllInDimension(getPacket(), dim);
    }
    
    @Override
    public Packet250CustomPayload getPacket()
    {
        ByteArrayOutputStream streambyte = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(streambyte);
        
        try
        {
            stream.writeInt(PACKETID);
            
            stream.writeUTF(getLabel());
            stream.writeInt(getTime());
            
            stream.writeBoolean(useSound());
            if (useSound())
            {
                stream.writeUTF(getSoundName());
                stream.writeFloat(getSoundVolume());
                stream.writeFloat(getSoundPitch());
            }
            
            stream.writeInt(X);
            stream.writeInt(Y);
            stream.writeInt(Z);
            stream.writeInt(dim);
            
            stream.close();
            streambyte.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return PacketDispatcher.getPacket(Archive.MOD_CHANNEL, streambyte.toByteArray());
    }

    @Override
    public ITimerBase getUpdate(DataInputStream stream) throws IOException
    {
        PointTimer data = new PointTimer();
        
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
    public ArrayList<String> getTimerString(EntityPlayer player)
    {
        ArrayList<String> text = super.getTimerString(player);
        text.add((player.dimension == dim) ? (getDistance(player) + " " + FunctionHelper.getArrowTo(X, Z, player)) : (DimensionManager.getWorld(dim).getProviderName()));
        return text;
    }
    
    public int getDistance(EntityPlayer player)
    {        
        double x = player.posX - X;
        double y = player.posY - Y;
        double z = player.posZ - Z;
        
        return (int) Math.sqrt((x*x) + (y*y) + (z*z));
    }
}