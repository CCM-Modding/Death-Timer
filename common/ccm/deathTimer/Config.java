package ccm.deathTimer;

import java.io.File;

import net.minecraftforge.common.Configuration;

/**
 * The config file, forgeconfig style.
 * 
 * @author Dries007
 */
public class Config
{
    
    static final String          cat           = "DeathTimer";
    
    static final String          clientcat     = Config.cat + ".client";
    
    static final String          servercat     = Config.cat + ".server";
    
    private static Configuration config;
    
    /*
     * General
     */
    public static boolean        useRightSide  = false;
    
    /*
     * Config values.
     */
    public static int            updateInteval = 10;
    
    public static void runConfig(final File file)
    {
        Config.config = new Configuration(file);
        
        Config.config.addCustomCategoryComment(Config.clientcat, "These options are client only.");
        
        Config.useRightSide = Config.config.get(Config.clientcat, "useRightSide", Config.useRightSide, "Use the right side of the screen to display the timers.").getBoolean(Config.useRightSide);
        
        Config.config.addCustomCategoryComment(Config.servercat, "These options are server side. The server will override the client.");
        
        Config.updateInteval = Config.config.get(Config.servercat, "updateInterval", Config.updateInteval, "\nIf the servers TPS is low, the timer will run slower then 'realtime'.\nIf you have low TPS, lower the number to sync up the client faster.\nThe amount of seconds between sync packets.").getInt();
        if (Config.updateInteval == 0)
        {
            Config.config.get(Config.servercat, "updateInterval", Config.updateInteval).set(10);
            Config.updateInteval = 10;
        }
        
        Config.config.save();
    }
}