package ccm.deathTimer.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import ccm.deathTimer.client.ClientTimer;
import ccm.deathTimer.timerTypes.TimerData;
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
            int ID = stream.readInt();

            switch (ID)
            {
                case TimerData.PACKETID:
                {
                    TimerData data = new TimerData();
                    data.label = stream.readUTF();
                    data.time = stream.readInt();
                    
                    if (stream.readBoolean())
                    {
                        data.dimOnly = true;
                        data.dim = stream.readInt();
                    }
                    
                    if (stream.readBoolean())
                    {
                        data.point = true;
                        data.X = stream.readInt();
                        data.Y = stream.readInt();
                        data.Z = stream.readInt();
                        data.dim = stream.readInt();
                    }
                    
                    if (stream.readBoolean())
                    {
                        data.useSound = true;
                        data.sound = stream.readUTF();
                        data.soundVolume = stream.readFloat();
                        data.soundPitch = stream.readFloat();
                    }
                    
                    ClientTimer.getInstance().updateTimer(data);
                }
                break;
            }
            
            streambyte.close();
            stream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}