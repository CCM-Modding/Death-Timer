package ccm.deathTimer.proxy;

import net.minecraftforge.common.MinecraftForge;
import ccm.deathTimer.client.ClientTimer;
import ccm.deathTimer.client.HUD;
import ccm.deathTimer.utils.lib.Locations;

import com.jadarstudios.api.developercapesapi.DeveloperCapesAPI;

public class ClientProxy extends CommonProxy
{
    @Override
    public void init()
    {
        DeveloperCapesAPI.getInstance().init(Locations.CAPES);
        new ClientTimer();
        MinecraftForge.EVENT_BUS.register(new HUD());
        
        /*
         * TODO: replace
        MinecraftForge.EVENT_BUS.register(new ClientDeathTimer());
        TickRegistry.registerScheduledTickHandler(new ClientDeathTimer(), Side.CLIENT);
        */
    }
}