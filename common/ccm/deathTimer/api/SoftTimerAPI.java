package ccm.deathTimer.api;

import java.lang.reflect.Method;

@SuppressWarnings({"unchecked","rawtypes"})
public class SoftTimerAPI
{
    public static final String APICLASSNAME = "ccm.deathTimer.api.HardTimerAPI";
    private static Class c = getAPIClass();
    private static boolean loaded;
    
    private static final boolean DEBUG = true;
    
    private static Class getAPIClass()
    {
        try
        {
            c = Class.forName(APICLASSNAME);
            loaded = true;
            return c;
        }
        catch (Exception e)
        {
            if (DEBUG) e.printStackTrace();
            loaded = false;
        }
        return null;
    }
    
    /**
     * If false, the class is not loaded and you can't use the timer functions.
     * @return
     */
    public static boolean isLoaded()
    {
        return loaded;
    }
    
    /**
     * Add a timer.
     * 
     * @param label UNIQUE KEY
     * @param time
     * @return
     */
    public static boolean newBasicTimer(String label, int time)
    {
        try
        {
            Method m = c.getMethod("newBasicTimer", String.class, int.class);
            m.invoke(null, label, time);
            
            return true;
        }
        catch (Exception e)
        {
            if (DEBUG) e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Add a timer with arrow, distance and point.
     * 
     * @param label UNIQUE KEY
     * @param time
     * @param X
     * @param Y
     * @param Z
     * @param dim
     * @return
     */
    public static boolean newPointTimer(String label, int time, int X, int Y, int Z, int dim)
    {
        try
        {
            Method m = c.getMethod("newPointTimer", String.class, int.class, int.class, int.class, int.class, int.class);
            m.invoke(null, label, time, X, Y, Z, dim);
            
            return true;
        }
        catch (Exception e)
        {
            if (DEBUG) e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Set the sound settings for a timer.
     * 
     * @param label UNIQUE KEY
     * @param sound
     * @param volume
     * @param pitch
     * @return
     */
    public static boolean setSound(String label, String sound, float volume, float pitch)
    {
        try
        {
            Method m = c.getMethod("setSound", String.class, String.class, float.class, float.class);
            m.invoke(null, label, sound, volume, pitch);
            
            return true;
        }
        catch (Exception e)
        {
            if (DEBUG) e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Call to stop the timer.
     * 
     * @param label UNIQUE KEY
     * @return
     */
    public static boolean stopTimer(String label)
    {
        try
        {
            Method m = c.getMethod("stopTimer", String.class);
            m.invoke(null, label);
            
            return true;
        }
        catch (Exception e)
        {
            if (DEBUG) e.printStackTrace();
            return false;
        }
    }
}
