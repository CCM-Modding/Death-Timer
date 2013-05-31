package ccm.deathTimer.timerTypes;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.ChunkCoordIntPair;
import ccm.deathTimer.server.EventTracker;
import ccm.deathTimer.server.ServerTimer;
import ccm.deathTimer.utils.lib.Archive;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

/**
 * Specific to deaths. Don't use for other stuff.
 * @author Dries007
 *
 */
public class DeathTimer extends PointTimer
{
    public static final int PACKETID = 2;
    
    public boolean isLoaded;
    public String username;

    public long chunkKey;
    
    public DeathTimer()
    {}
    
    public DeathTimer(EntityPlayer player)
    {
        this.label = "Item despawn - " + player.username;
        this.time = 60 * 5;
        this.isLoaded = true;
        this.username = player.username;
        this.X = (int) player.posX;
        this.Y = (int) player.posY;
        this.Z = (int) player.posZ;
        this.chunkKey = ChunkCoordIntPair.chunkXZ2Int(X/16, Y/16);
        
        ServerTimer.getInstance().addTimer(this);
    }
    
    @Override
    public void tick()
    {
        if (isLoaded)
            time --;
        
        if (time <= 0)
        {
            EventTracker.ChuncksToTrackMap.remove(chunkKey, this);
        }
    }
    
    @Override
    public void sendAutoUpdate()
    {
        Player p = (Player) MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(username);
        if (p != null) PacketDispatcher.sendPacketToPlayer(getPacket(), p);
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
            
            stream.writeBoolean(isLoaded);
            
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
        DeathTimer data = new DeathTimer();
        
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
        
        data.isLoaded = stream.readBoolean();
        
        System.out.println("UPDATE " + data.label + ": " + data.time);
        
        return data;
    }
    
    @Override
    public ArrayList<String> getTimerString(EntityPlayer player)
    {
        ArrayList<String> text = super.getTimerString(player);
        text.add(isLoaded ? EnumChatFormatting.YELLOW + "Death chunk loaded!" : EnumChatFormatting.GREEN + "Death chunk unloaded!");
        return text;
    }
    
    @Override
    public boolean isRelevantFor(EntityPlayer player)
    {
        return player.getCommandSenderName().equals(username);
    }
}
