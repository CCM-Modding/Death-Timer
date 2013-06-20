package ccm.deathTimer.proxy;

import net.minecraftforge.common.MinecraftForge;
import ccm.deathTimer.client.ClientTimer;
import ccm.deathTimer.client.HUD;

/**
 * Initiates the client-only timer; registers the renderer.
 * 
 * @author Dries007
 */
public class ClientProxy extends CommonProxy
{
    
    @Override
    public void init()
    {
        new ClientTimer();
        MinecraftForge.EVENT_BUS.register(new HUD());
    }
}