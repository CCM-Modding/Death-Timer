package ccm.deathTimer.timerTypes;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import ccm.deathTimer.utils.FunctionHelper;
import ccm.deathTimer.utils.lib.Archive;

import com.google.common.base.Strings;

import cpw.mods.fml.common.network.PacketDispatcher;

/**
 * Extend to make it more usefull (or implement {@link ITimerBase}).
 * 
 * @author Dries007
 *
 */

public class BasicTimer implements ITimerBase
{
    public static final int PACKETID = 0;
    
    public int      time;
    public String   label;
    public String   soundName;
    public float    soundVolume;
    public float    soundPitch;
    
    @Override
    public String getLabel()
    {
        return label;
    }

    @Override
    public int getTime()
    {
        return time;
    }    

    @Override
    public void tick()
    {
        time --;
    }

    @Override
    public boolean useSound()
    {
        return !Strings.isNullOrEmpty(soundName);
    }

    @Override
    public String getSoundName()
    {
        return soundName;
    }

    @Override
    public float getSoundVolume()
    {
        return soundVolume;
    }

    @Override
    public float getSoundPitch()
    {
        return soundPitch;
    }
    
    @Override
    public void sendAutoUpdate()
    {
        PacketDispatcher.sendPacketToAllPlayers(getPacket());
    }

    @Override
    public ITimerBase getUpdate(DataInputStream stream) throws IOException
    {
        BasicTimer data = new BasicTimer();
        
        data.label = stream.readUTF();
        data.time = stream.readInt();
        
        if (stream.readBoolean())
        {
            data.soundName = stream.readUTF();
            data.soundVolume = stream.readFloat();
            data.soundPitch = stream.readFloat();
        }
        
        return data;
    }
    
    @Override
    public ArrayList<String> getTimerString(EntityPlayer player)
    {
        ArrayList<String> text = new ArrayList<String>();
        
        text.add(getLabel() + ": " + FunctionHelper.timeColor(getTime()) + "T-" + FunctionHelper.parseTime(getTime()));;
        
        return text;
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
    public boolean isRelevantFor(EntityPlayer player)
    {
        return true;
    }
}