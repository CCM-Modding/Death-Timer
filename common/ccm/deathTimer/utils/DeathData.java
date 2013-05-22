package ccm.deathTimer.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.ChunkCoordIntPair;

public class DeathData
{
    public String player;
    public int X, Y, Z, dim, time;
    public ChunkCoordIntPair chunkCoords;
    public long key;
    
    public DeathData(EntityPlayer player)
    {
        this.player = player.username;
        this.X = (int) player.posX;
        this.Y = (int) player.posY;
        this.Z = (int) player.posZ;
        this.dim = player.dimension;
        this.time = 60 * 5;
        this.chunkCoords = new ChunkCoordIntPair(X/16, Y/16);
        this.key = ChunkCoordIntPair.chunkXZ2Int(chunkCoords.chunkXPos, chunkCoords.chunkZPos);
    }
    
    public DeathData(int X, int Y, int Z, int dim, String player)
    {
        this.player = player;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.dim = dim;
        this.time = 60 * 5;
        this.chunkCoords = new ChunkCoordIntPair(X/16, Y/16);
        this.key = ChunkCoordIntPair.chunkXZ2Int(chunkCoords.chunkXPos, chunkCoords.chunkZPos);
    }
}
