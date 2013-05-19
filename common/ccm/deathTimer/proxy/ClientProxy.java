package ccm.deathTimer.proxy;

import ccm.deathTimer.utils.lib.Locations;

import com.jadarstudios.api.developercapesapi.DeveloperCapesAPI;

public class ClientProxy extends CommonProxy
{
    /**
     * Initializes the Capes. Original code that adds capes is found in this
     * Github repository: https://github.com/jadar/DeveloperCapesAPI
     */
    @Override
    public void initCapes()
    {
        // Link for the file that makes the capes work
        DeveloperCapesAPI.getInstance().init(Locations.CAPES);
    }
}