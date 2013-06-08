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

    static final String          cat              = "DeathTimer";

    static final String          clientcat        = cat + ".client";

    static final String          servercat        = cat + ".server";

    private static Configuration config;

    /*
     * General
     */
    public static boolean        useRightSide     = false;

    /*
     * Death sepecific
     */
    public static boolean        enableDeathSound = true;

    public static boolean        enableDeathTimer = true;

    /*
     * Config values.
     */
    public static int            updateInteval    = 10;

    public static void runConfig(final File file)
    {
        config = new Configuration(file);

        config.addCustomCategoryComment(clientcat, "These options are client only.");

        useRightSide = config.get(clientcat, "useRightSide", useRightSide, "Use the right side of the screen to display the timers.").getBoolean(useRightSide);

        config.addCustomCategoryComment(servercat, "These options are server side. The server will override the client.");

        updateInteval = config.get(servercat, "updateInterval", updateInteval, "\nIf the servers TPS is low, the timer will run slower then 'realtime'.\nIf you have low TPS, lower the number to sync up the client faster.\nThe amount of seconds between sync packets.").getInt();
        if (updateInteval == 0)
        {
            config.get(servercat, "updateInterval", updateInteval).set(10);
            updateInteval = 10;
        }

        config.save();
    }
}