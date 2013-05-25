package ccm.deathTimer;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class Config
{
    static final String cat = "DeathTimer";
    static final String clientcat = cat + ".client";
    static final String servercat = cat + ".server";
    
    private static Configuration config;
    
    /*
     * General
     */
    public static boolean useRightSide = false;
    
    /*
     * Death sepecific
     */
    public static boolean enableDeathSound = true;
    public static boolean enableDeathTimer = true;
    
    
    public static void runConfig(File file)
    {
        config = new Configuration(file);
        
        config.addCustomCategoryComment(clientcat, "These options are client only.");
        
        useRightSide = config.get(clientcat, "useRightSide", useRightSide, "Use the right side of the screen to display the timers.").getBoolean(useRightSide);
        
        enableDeathSound = config.get(clientcat, "enableDeathSound", enableDeathSound, "Play the dragon death sound when the despawn timer runs out.").getBoolean(enableDeathSound);
        enableDeathTimer = config.get(clientcat, "enableDeathTimer", enableDeathTimer, "Add a 5 minute timer when you die.").getBoolean(enableDeathTimer);
        
        config.save();
    }   
}