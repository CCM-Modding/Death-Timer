package ccm.deathTimer;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import ccm.deathTimer.utils.TimerData;
import ccm.deathTimer.utils.lib.Archive;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player playerFake)
    {
        if (!packet.channel.equals(Archive.MOD_CHANNEL)) return;
        
        ByteArrayInputStream streambyte = new ByteArrayInputStream(packet.data);
        DataInputStream stream = new DataInputStream(streambyte);

        try
        {
            int ID = stream.read();

            switch (ID)
            {
                case TimerData.PACKETID:
                    TimerData data = new TimerData();
                    data.label = stream.readUTF();
                    data.time = stream.readInt();
                    
                    data.dimOnly = stream.readBoolean();
                    if (data.dimOnly)
                    {
                        data.dim = stream.readInt();
                    }
                    
                    data.point = stream.readBoolean();
                    if (data.point)
                    {
                        data.X = stream.readInt();
                        data.Y = stream.readInt();
                        data.Z = stream.readInt();
                        data.dim = stream.readInt();
                    }
                break;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}