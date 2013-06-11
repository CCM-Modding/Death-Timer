package ccm.deathTimer.timerTypes;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.command.ICommandSender;
import net.minecraft.network.packet.Packet250CustomPayload;
import ccm.deathTimer.utils.FunctionHelper;
import ccm.deathTimer.utils.lib.Archive;

import com.google.common.base.Strings;

import cpw.mods.fml.common.network.PacketDispatcher;

/**
 * Extend to make it more usefull (or implement {@link ITimerBase}).
 * 
 * @author Dries007
 */

public class BasicTimer implements ITimerBase
{
    public static final int PACKETID = 0;
    
    public int              time;
    
    public String           label;
    
    public String           soundName;
    
    public float            soundVolume;
    
    public float            soundPitch;
    
    @Override
    public String getLabel()
    {
        return this.label;
    }
    
    @Override
    public int getTime()
    {
        return this.time;
    }
    
    @Override
    public void tick()
    {
        this.time--;
    }
    
    @Override
    public boolean useSound()
    {
        return !Strings.isNullOrEmpty(this.soundName);
    }
    
    @Override
    public String getSoundName()
    {
        return this.soundName;
    }
    
    @Override
    public float getSoundVolume()
    {
        return this.soundVolume;
    }
    
    @Override
    public float getSoundPitch()
    {
        return this.soundPitch;
    }
    
    @Override
    public void sendAutoUpdate()
    {
        PacketDispatcher.sendPacketToAllPlayers(this.getPacket());
    }
    
    @Override
    public ITimerBase getUpdate(final DataInputStream stream) throws IOException
    {
        final BasicTimer data = new BasicTimer();
        
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
    public ArrayList<String> getTimerString(final ICommandSender sender)
    {
        final ArrayList<String> text = new ArrayList<String>();
        
        text.add(this.getLabel() + ": " + FunctionHelper.timeColor(this.getTime()) + "T-" + FunctionHelper.parseTime(this.getTime()) + " ");
        
        return text;
    }
    
    @Override
    public Packet250CustomPayload getPacket()
    {
        final ByteArrayOutputStream streambyte = new ByteArrayOutputStream();
        final DataOutputStream stream = new DataOutputStream(streambyte);
        
        try
        {
            stream.writeInt(BasicTimer.PACKETID);
            
            stream.writeUTF(this.getLabel());
            stream.writeInt(this.getTime());
            
            stream.writeBoolean(this.useSound());
            if (this.useSound())
            {
                stream.writeUTF(this.getSoundName());
                stream.writeFloat(this.getSoundVolume());
                stream.writeFloat(this.getSoundPitch());
            }
            
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
    public boolean isRelevantFor(final ICommandSender player)
    {
        return true;
    }
    
    @Override
    public void setLabel(final String label)
    {
        this.label = label;
    }
    
    @Override
    public void setTime(final int time)
    {
        this.time = time;
    }
    
    @Override
    public void setSoundName(final String name)
    {
        this.soundName = name;
    }
    
    @Override
    public void setSoundVolume(final float volume)
    {
        this.soundVolume = volume;
    }
    
    @Override
    public void setSoundPitch(final float pitch)
    {
        this.soundPitch = pitch;
    }
    
    @Override
    public boolean isPersonal()
    {
        return false;
    }
}