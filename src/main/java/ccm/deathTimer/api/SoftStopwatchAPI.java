package ccm.deathTimer.api;

import java.lang.reflect.Method;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SoftStopwatchAPI
{
    
    public static final String   APICLASSNAME = "ccm.deathTimer.api.HardStopwatchAPI";
    
    private static Class         c            = SoftStopwatchAPI.getAPIClass();
    
    private static boolean       loaded;
    
    private static final boolean DEBUG        = true;
    
    private static Class getAPIClass()
    {
        try
        {
            SoftStopwatchAPI.c = Class.forName(SoftStopwatchAPI.APICLASSNAME);
            SoftStopwatchAPI.loaded = true;
            return SoftStopwatchAPI.c;
        }
        catch (final Exception e)
        {
            if (SoftStopwatchAPI.DEBUG) e.printStackTrace();
            SoftStopwatchAPI.loaded = false;
        }
        return null;
    }
    
    /**
     * If false, the class is not loaded and you can't use the stopwatch functions.
     * 
     * @return
     */
    public static boolean isLoaded()
    {
        return SoftStopwatchAPI.loaded;
    }
    
    /**
     * Add a stopwatch.
     * 
     * @param label
     *            UNIQUE KEY
     * @param time
     * @return
     */
    public static boolean newStopwatch(final String label)
    {
        try
        {
            final Method m = SoftStopwatchAPI.c.getMethod("newStopwatch", String.class);
            m.invoke(null, label);
            
            return true;
        }
        catch (final Exception e)
        {
            if (SoftStopwatchAPI.DEBUG) e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Add a personal stopwatch.
     * 
     * @param label
     *            UNIQUE KEY
     * @param username
     */
    public static boolean newStopwatch(final String label, final String username)
    {
        try
        {
            final Method m = SoftStopwatchAPI.c.getMethod("newStopwatch", String.class, String.class);
            m.invoke(null, label, username);
            
            return true;
        }
        catch (final Exception e)
        {
            if (SoftStopwatchAPI.DEBUG) e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Call to stop the stopwatch.
     * 
     * @param label
     *            UNIQUE KEY
     * @return
     */
    public static boolean stopStopwatch(final String label)
    {
        try
        {
            final Method m = SoftStopwatchAPI.c.getMethod("stopStopwatch", String.class);
            m.invoke(null, label);
            
            return true;
        }
        catch (final Exception e)
        {
            if (SoftStopwatchAPI.DEBUG) e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Call to stop the stopwatch.
     * 
     * @param label
     *            UNIQUE KEY
     * @return
     */
    public static boolean pauseStopwatch(final String label, final boolean pause)
    {
        try
        {
            final Method m = SoftStopwatchAPI.c.getMethod("pauseStopwatch", String.class, boolean.class);
            m.invoke(null, label, pause);
            
            return true;
        }
        catch (final Exception e)
        {
            if (SoftStopwatchAPI.DEBUG) e.printStackTrace();
            return false;
        }
    }
}