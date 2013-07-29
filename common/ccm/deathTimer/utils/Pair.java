package ccm.deathTimer.utils;

/**
 * Simple Key Value Pair
 * 
 * @author Dries007
 *
 * @param <K> Key
 * @param <V> Value
 */
public class Pair<K, V>
{
    public K key;
    public V value;
    
    public Pair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
        
    public K getKey()
    {
        return key;
    }
    
    public V getValue()
    {
        return value;
    }
}