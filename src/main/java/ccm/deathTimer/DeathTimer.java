package ccm.deathTimer;

import ccm.deathTimer.commands.Stopwatch;
import ccm.deathTimer.commands.Timer;
import ccm.deathTimer.network.PacketHandler;
import ccm.deathTimer.proxy.CommonProxy;
import ccm.deathTimer.server.EventTracker;
import ccm.deathTimer.server.ServerTimer;
import ccm.deathTimer.utils.lib.Archive;
import ccm.deathTimer.utils.lib.Locations;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;

/**
 * Main mod class. Main author: Dries007, Idea and concept: Claycorp
 *
 * @author Dries007
 */
@Mod(modid = Archive.MOD_ID, name = Archive.MOD_NAME, dependencies = Archive.MOD_DEPENDANCIES)
@NetworkMod(clientSideRequired = false, serverSideRequired = false, channels = {Archive.MOD_CHANNEL_TIMERS}, packetHandler = PacketHandler.class)
public class DeathTimer
{
    @Instance(Archive.MOD_ID)
    public static DeathTimer instance;

    @SidedProxy(serverSide = Locations.SERVER_PROXY, clientSide = Locations.CLIENT_PROXY)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event)
    {
        Config.runConfig(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void init(final FMLInitializationEvent event)
    {
        DeathTimer.proxy.init();
        new EventTracker();
    }

    @EventHandler
    public void serverStart(final FMLServerStartingEvent event)
    {
        event.registerServerCommand(new Timer());
        event.registerServerCommand(new Stopwatch());

        new ServerTimer();

        ServerTimer.getInstance().load();
    }

    @EventHandler
    public void serverStopping(final FMLServerStoppingEvent event)
    {
        ServerTimer.getInstance().save();
    }
}