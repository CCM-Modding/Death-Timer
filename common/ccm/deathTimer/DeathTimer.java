package ccm.deathTimer;
import java.util.logging.Level;

import ccm.deathTimer.commands.Timer;
import ccm.deathTimer.network.PacketHandler;
import ccm.deathTimer.proxy.CommonProxy;
import ccm.deathTimer.server.EventTracker;
import ccm.deathTimer.server.ServerTimer;
import ccm.deathTimer.utils.lib.Archive;
import ccm.deathTimer.utils.lib.Locations;
import ccm.nucleum_omnium.IMod;
import ccm.nucleum_omnium.handler.Handler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.FingerprintWarning;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;

/**
 * Main mod class.
 * 
 * Main author: Dries007
 * Idea and concept: Claycorp
 * 
 * @author Dries007
 *
 */
@Mod(modid = Archive.MOD_ID,
    name = Archive.MOD_NAME,
    version = Archive.MOD_VERSION,
    useMetadata = false,
    dependencies = Archive.MOD_DEPENDANCIES,
    certificateFingerprint = Archive.MOD_FIGERPRINT)
@NetworkMod(clientSideRequired = false,
       serverSideRequired = false,
       channels = Archive.MOD_CHANNEL,
       packetHandler = PacketHandler.class)
public class DeathTimer implements IMod
{
    @Instance(Archive.MOD_ID)
    public static DeathTimer instance;
    
    @SidedProxy(serverSide = Locations.SERVER_PROXY,
            clientSide = Locations.CLIENT_PROXY)
    public static CommonProxy   proxy;
    
    @Override
    public String getId()
    {
        return Archive.MOD_ID;
    }

    @Override
    public String getName()
    {
        return Archive.MOD_NAME;
    }

    @Override
    public String getPrefix()
    {
        return Archive.MOD_PREFIX;
    }

    @Override
    public String getVersion()
    {
        return Archive.MOD_VERSION;
    }
    
    @FingerprintWarning
    public void invalidFingerprint(final FMLFingerprintViolationEvent event)
    {
        Handler.log(this, Level.SEVERE, Archive.INVALID_FINGERPRINT_MSG);
    }
    
    @PreInit
    public void preInit(final FMLPreInitializationEvent event)
    {
        Config.runConfig(event.getSuggestedConfigurationFile());
    }
    
    @Init
    public void init(final FMLInitializationEvent event)
    {
        proxy.init();
        new EventTracker();
    }
    
    @ServerStarting
    public void serverStart(final FMLServerStartingEvent event)
    {
        event.registerServerCommand(new Timer());
        
        new ServerTimer();
    }
}
