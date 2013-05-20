package ccm.deathTimer.proxy;

import net.minecraftforge.common.MinecraftForge;
import ccm.deathTimer.client.ClientDeathTimer;
import ccm.deathTimer.client.HUD;
import ccm.deathTimer.utils.lib.Locations;

import com.jadarstudios.api.developercapesapi.DeveloperCapesAPI;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy
{
    @Override
    public void init()
    {
        DeveloperCapesAPI.getInstance().init(Locations.CAPES);
        MinecraftForge.EVENT_BUS.register(new HUD());
        MinecraftForge.EVENT_BUS.register(new ClientDeathTimer());
        TickRegistry.registerScheduledTickHandler(new ClientDeathTimer(), Side.CLIENT);
    }
}