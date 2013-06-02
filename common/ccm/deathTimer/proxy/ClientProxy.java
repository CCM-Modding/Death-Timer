package ccm.deathTimer.proxy;

import lib.com.jadarstudios.api.developercapesapi.DeveloperCapesAPI;

import net.minecraftforge.common.MinecraftForge;

import ccm.deathTimer.client.ClientTimer;
import ccm.deathTimer.client.HUD;
import ccm.deathTimer.utils.lib.Locations;

/**
 * Registers the dev-capes; initiates the client-only timer; registers the renderer.
 * 
 * @author Dries007
 */
public class ClientProxy extends CommonProxy
{

    @Override
    public void init()
    {
        DeveloperCapesAPI.getInstance().init(Locations.CAPES);
        new ClientTimer();
        MinecraftForge.EVENT_BUS.register(new HUD());
    }
}