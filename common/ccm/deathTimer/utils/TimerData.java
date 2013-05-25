package ccm.deathTimer.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.network.PacketDispatcher;
import ccm.deathTimer.utils.lib.Archive;
import ccm.nucleum_omnium.network.ICustomPacket;
import net.minecraft.network.packet.Packet250CustomPayload;

public class TimerData implements ICustomPacket
{
    public static final int PACKETID = 0;
    
    public String label;
    public boolean point;
    public int X, Y, Z, dim, time;
    public boolean dimOnly = false;
    /*
     * Amount of seconds before sending an update
     * server only value
     */
    public int updateInteval = 10;
    
    public TimerData()
    {}
    
    @Override
    public Packet250CustomPayload getPayload()
    {
        ByteArrayOutputStream streambyte = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(streambyte);
        
        try
        {
            stream.writeInt(PACKETID);
            
            stream.writeUTF(label);
            stream.writeInt(time);
            
            stream.writeBoolean(dimOnly);
            if (dimOnly)
            {
                stream.writeInt(dim);
            }
            
            stream.writeBoolean(point);
            if (point)
            {
                stream.writeInt(X);
                stream.writeInt(Y);
                stream.writeInt(Z);
                stream.writeInt(dim);
            }
            
            stream.close();
            streambyte.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return PacketDispatcher.getPacket(Archive.MOD_CHANNEL, streambyte.toByteArray());
    }
}
