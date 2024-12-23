import static java.lang.System.Logger.Level.DEBUG;

public class primitive_vars {
}

class BITCODE_RL implements IntCommon {
    private static final long MIN_VALUE = 0;
    private static final long MAX_VALUE = 0xFFFFFFFFL; // 2^32 - 1
    public long value;
    public BITCODE_RL(long value)
    {
        if (value < MIN_VALUE || value > MAX_VALUE)
        {
            throw new IllegalArgumentException("Value must be in range 0 to " + MAX_VALUE + "For UInt32");
        }
        else{
            this.value = value;
        }
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public void setValue(long value)
    {
        if (value < MIN_VALUE || value > MAX_VALUE)
        {
            throw new IllegalArgumentException("Value must be in range 0 to " + MAX_VALUE + "For UInt32");
        }
        else{
            this.value = value;
        }
    }

    @Override
    public int hashCode() {
        return Long.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
