import static javax.swing.text.html.HTML.Tag.U;

public class hash {
    public static int HASH_LOAD = 75;
    public static final long HASH_NOT_FOUND = -1L;


    static dwg_inthash hash_new(long size)
    {
        dwg_inthash hash = new dwg_inthash();
        long cap;

        if(hash == null)
        {
            return null;
        }

        if(size < 15)
        {
            size = 15;
        }
        cap = (long)(size * 100.0 / HASH_LOAD);
        while (size <= cap)
        {
            size <<= 1;
        }
        hash.array = new _hashbucket[(int)size];
        hash.elems = 0;
        hash.size  = size;
        return hash;
    }

    static long hash_get(dwg_inthash hash, long key) {
        long i = 0;
        if(hash_func(key) != 0 && hash.size != 0)
        {
            i = hash_func(key) % hash.size;
        }
        long j = i;
        hash.array = new _hashbucket[(int)hash.size];
        while (hash.array[(int)i].key != 0 && hash.array[(int)i].key != key)
        {
            i++;
            if(i == hash.size)
            {
                i = 0;
            }
            if(i == j)
            {
                return HASH_NOT_FOUND;
            }
        }
        if(hash.array[(int)i].key != 0){
            return hash.array[(int)i].value;
        }
        else{
            return HASH_NOT_FOUND;
        }
    }

    private static long hash_func(long key) {
        key = ((key >> 32) ^ key) * 0xd6e8feb86659fd93L;
        key = ((key >> 32) ^ key) * 0xd6e8feb86659fd93L;
        key = (key >> 32) ^ key;
        return key;
    }
}
