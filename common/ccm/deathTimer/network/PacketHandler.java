package ccm.deathTimer.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import ccm.deathTimer.client.ClientTimer;
import ccm.deathTimer.timerTypes.BasicStopwatch;
import ccm.deathTimer.timerTypes.BasicTimer;
import ccm.deathTimer.timerTypes.DeathTimer;
import ccm.deathTimer.timerTypes.PointTimer;
import ccm.deathTimer.utils.lib.Archive;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * Don't forget to add timer types to the list...
 * 
 * @author Dries007
 */
public class PacketHandler implements IPacketHandler
{
    
    @Override
    public void onPacketData(final INetworkManager manager, final Packet250CustomPayload packet, final Player playerFake)
    {
        if (packet.channel.equals(Archive.MOD_CHANNEL_TIMERS))
        {
            final ByteArrayInputStream streambyte = new ByteArrayInputStream(packet.data);
            final DataInputStream stream = new DataInputStream(streambyte);
            
            try
            {
                final int ID = stream.readInt();
                
                switch (ID)
                {
                    /*
                     * Timers
                     */
                    case BasicTimer.PACKETID:
                        ClientTimer.getInstance().updateServerTimer(new BasicTimer().getUpdate(stream));
                        break;
                    case PointTimer.PACKETID:
                        ClientTimer.getInstance().updateServerTimer(new PointTimer().getUpdate(stream));
                        break;
                    case DeathTimer.PACKETID:
                        ClientTimer.getInstance().updateServerTimer(new DeathTimer().getUpdate(stream));
                        break;
                        
                    /*
                     * Stopwatches
                     */
                    case BasicStopwatch.PACKETID:
                        ClientTimer.getInstance().updateServerStopwatch(new BasicStopwatch().getUpdate(stream));
                        break;
                }
                
                streambyte.close();
                stream.close();
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}