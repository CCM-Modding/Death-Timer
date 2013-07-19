package ccm.deathTimer.utils;

public class Pair<V, K>
{
    public V key;
    public K value;
    
    public Pair(V key, K value)
    {
        this.key = key;
        this.value = value;
    }
    
    public Pair()
    {   
        
    }
    
    public V getKey()
    {
        return key;
    }
    
    public K getValue()
    {
        return value;
    }
}
