package ccm.deathTimer.timerTypes;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;

/**
 * Use to make a custom timer class.
 * @author Dries007
 *
 */
public interface ITimerBase
{
    public void                     tick();
    public void                     sendAutoUpdate();
    public Packet250CustomPayload   getPacket();
    public boolean                  isRelevantFor(EntityPlayer player);
    public ITimerBase               getUpdate(DataInputStream stream) throws IOException;     
    public String                   getLabel();
    public int                      getTime();
    
    public boolean                  useSound();
    public String                   getSoundName();
    public float                    getSoundVolume();
    public float                    getSoundPitch();
    
    public ArrayList<String>        getTimerString(EntityPlayer player);
}
