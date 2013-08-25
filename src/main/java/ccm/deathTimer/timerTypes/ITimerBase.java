package ccm.deathTimer.timerTypes;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;

/**
 * Use to make a custom timer class.
 * 
 * @author Dries007
 */
public interface ITimerBase
{
    public void tick();
    
    public void sendAutoUpdate();
    
    public Packet250CustomPayload getPacket();
    
    public boolean isRelevantFor(ICommandSender player);
    
    public boolean isPersonal();
    
    public ITimerBase getUpdate(DataInputStream stream) throws IOException;
    
    public String getLabel();
    
    public int getTime();
    
    public void setLabel(String label);
    
    public void setTime(int time);
    
    public boolean useSound();
    
    public String getSoundName();
    
    public float getSoundVolume();
    
    public float getSoundPitch();
    
    public void setSoundName(String name);
    
    public void setSoundVolume(float volume);
    
    public void setSoundPitch(float pitch);
    
    public ArrayList<String> getTimerString(ICommandSender player);
    
    public NBTTagCompound toNBT();
    
    public ITimerBase fromNBT(NBTTagCompound tag);
}