package ccm.deathTimer.timerTypes;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.DimensionManager;
import ccm.deathTimer.utils.FunctionHelper;
import ccm.deathTimer.utils.lib.Archive;
import ccm.nucleum_omnium.network.ICustomPacket;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TimerData implements ICustomPacket
{
    public static final int PACKETID = 0;
    /*
     * "Label" MUST BE UNIQUE!
     */
    public String label;
    
    public boolean point = false;
    /*
     * If time is -1 the timer will be canceled.
     */
    public int time;
    
    public int X, Y, Z, dim = 0;
    public boolean dimOnly = false;
    
    /*
     * Amount of seconds before sending an update
     * server only value
     */
    public int updateInteval = 10;
    
    /*
     * Sound info
     */
    public boolean useSound = true;
    public String sound;
    public float soundVolume;
    public float soundPitch;
    
    public TimerData()
    {}
    
    public TimerData(String label, int time)
    {
        this.label = label;
        this.time = time;
    }
    
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
            
            stream.writeBoolean(useSound);
            if (useSound)
            {
                stream.writeUTF(sound);
                stream.writeFloat(soundVolume);
                stream.writeFloat(soundPitch);
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

    public void sendUpdate()
    {
        if (dimOnly) PacketDispatcher.sendPacketToAllInDimension(getPayload(), dim);
        else PacketDispatcher.sendPacketToAllPlayers(getPayload());
    }

    public Collection<? extends String> getTimerString(EntityPlayer player)
    {
        ArrayList<String> text = new ArrayList<String>();
        
        text.add(label + ": " + FunctionHelper.timeColor(time) + "T-" + FunctionHelper.parseTime(time));
        if (point && player.dimension == dim) text.add(getDistance(player) + " " + FunctionHelper.getArrowTo(X, Z, player));
        else if (point && player.dimension != dim) text.add(DimensionManager.getWorld(dim).getProviderName());
        
        return  text;
    }
    
    public int getDistance(EntityPlayer player)
    {        
        double x = player.posX - X;
        double y = player.posY - Y;
        double z = player.posZ - Z;
        
        return (int) Math.sqrt((x*x) + (y*y) + (z*z));
    }
}
