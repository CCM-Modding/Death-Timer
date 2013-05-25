package ccm.deathTimer.server;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.world.ChunkEvent;
import ccm.deathTimer.utils.DeathData;

import com.google.common.collect.HashMultimap;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public class DeathTracker implements IScheduledTickHandler
{
    static HashMultimap<Long, DeathData> ChuncksToTrack = HashMultimap.create();
    
    public DeathTracker()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @ForgeSubscribe
    public void handleDeath(PlayerDropsEvent e)
    {
        for (long key : ChuncksToTrack.keySet()) for (DeathData dat : ChuncksToTrack.get(key)) if (dat.player.equals(e.entityPlayer.username)) ChuncksToTrack.remove(key, dat);
        DeathData dat = new DeathData(e.entityPlayer);
        ChuncksToTrack.put(dat.key, dat);
    }
    
    @ForgeSubscribe
    public void chunkUnload(ChunkEvent.Unload e)
    {
        if (FMLCommonHandler.instance().getSide().isClient()) return;
        ServerConfigurationManager cm = MinecraftServer.getServer().getConfigurationManager();
        for (DeathData dat : ChuncksToTrack.get(ChunkCoordIntPair.chunkXZ2Int(e.getChunk().xPosition, e.getChunk().zPosition)))
        {
            EntityPlayerMP p = cm.getPlayerForUsername(dat.player);
            if (p == null) continue;
            
            p.sendChatToPlayer("Chunck you died in unloaded.");
        }
    }
    
    @ForgeSubscribe
    public void chunkLoad(ChunkEvent.Load e)
    {
        if (FMLCommonHandler.instance().getSide().isClient()) return;
        ServerConfigurationManager cm = MinecraftServer.getServer().getConfigurationManager();
        for (DeathData dat : ChuncksToTrack.get(ChunkCoordIntPair.chunkXZ2Int(e.getChunk().xPosition, e.getChunk().zPosition)))
        {
            EntityPlayerMP p = cm.getPlayerForUsername(dat.player);
            if (p == null) continue;
            
            p.sendChatToPlayer("Chunck you died in loaded.");
        }
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.SERVER);
    }

    @Override
    public String getLabel()
    {
        return "DeathTimer-Server";
    }

    @Override
    public int nextTickSpacing()
    {
        return 19;
    }
}
