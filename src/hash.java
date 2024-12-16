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
}
