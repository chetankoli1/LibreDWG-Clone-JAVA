import java.math.BigInteger;

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
        BigInteger keyBigInt = BigInteger.valueOf(key).and(new BigInteger("FFFFFFFFFFFFFFFF", 16));
        BigInteger hashValue = hash_func(keyBigInt);
        if(!hashValue.equals(BigInteger.ZERO) && hash.size != 0)
        {
            i = hashValue.mod(BigInteger.valueOf(hash.size)).longValue();
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

//    static long hash_func(long key) {
//        key = ((key >> 32) ^ key) * 0xd6e8feb86659fd93L;
//        key = ((key >> 32) ^ key) * 0xd6e8feb86659fd93L;
//        key = (key >> 32) ^ key;
//        return key;
//    }
    static BigInteger hash_func(BigInteger key) {
        BigInteger mask = new BigInteger("FFFFFFFFFFFFFFFF", 16);

        key = key.shiftRight(32).xor(key).multiply(new BigInteger("D6E8FEB86659FD93", 16));
        key = key.and(mask);

        key = key.shiftRight(32).xor(key).multiply(new BigInteger("D6E8FEB86659FD93", 16));
        key = key.and(mask); // Emulate UInt64 overflow

        key = key.shiftRight(32).xor(key);
        key = key.and(mask);

        return key;
    }

    static void hash_set(dwg_inthash hash, long key, int value)
    {
        BigInteger keyBigInt = BigInteger.valueOf(key).and(new BigInteger("FFFFFFFFFFFFFFFF", 16)); // Emulate UInt64
        BigInteger hashValue = hash_func(keyBigInt);

        BigInteger index = hashValue.mod(BigInteger.valueOf(hash.size));
        int i = index.intValue();
        long j = i;

        if(key == 0)
        {
            return;
        }

        hash.array[i] = new _hashbucket();

        if(hash.array[i].key == 0)
        {
            hash.array[i].key = key;
            hash.array[i].value = value;
            hash.elems++;
        }
        while (hash.array[i].key != 0)
        {
            if(hash.array[i].key == key)
            {
                hash.array[i].value = value;
                return;
            }
            i++;
            if(i == hash.size){
                i = 0;
            }
            if(i == j)
            {
                if(hash_need_resize(hash))
                {
                    hash_resize(hash);
                    hash_set(hash,key,value);
                    return;
                }
                while (hash.array[i].key != 0)
                {
                    i++;
                    if(i == hash.size)
                    {
                        i = 0;
                    }
                    if(i == j)
                    {
                        hash_resize(hash);
                        hash_set(hash,key,value);
                        return;
                    }
                    else{
                        hash.array[i].key = key;
                        hash.array[i].value = value;
                        hash.elems++;
                        return;
                    }
                }
            }
        }
        //empty slot
        hash.array[i].key = key;
        hash.array[i].value = value;
        hash.elems++;
        return;
    }


    static void hash_resize(dwg_inthash hash) {
        dwg_inthash oldhash = new dwg_inthash();
        oldhash.array = hash.array;
        oldhash.size = hash.size;
        oldhash.elems = hash.elems;

        long size = hash.size * 2;

        hash.array = new _hashbucket[(int) size];
        if (hash.array == null) {
            hash.array = oldhash.array;
            hash.size = oldhash.size;
            hash.elems = oldhash.elems;
            return;
        }

        hash.elems = 0;
        hash.size = size;

        for (int j = 0; j < size; j++) {
            hash.array[j] = null;
        }

        for (int i = 0; i < oldhash.size; i++) {
            _hashbucket bucket = oldhash.array[i];
            if (bucket != null && bucket.key != 0) {
                hash_set(hash, bucket.key, (int)bucket.value);
            }
        }

        oldhash.array = null;
    }

    static boolean hash_need_resize(dwg_inthash hash) {
        return (hash.elems * 100.0 / HASH_LOAD) > hash.size;
    }
}
