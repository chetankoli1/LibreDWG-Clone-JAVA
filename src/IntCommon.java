public class IntCommon {
    // Signed integer types
    public static final byte INT8_MIN = (byte) (-(127 + 1));
    public static final byte INT8_MAX = 127;
    public static final short INT16_MIN = (short) (-(32767 + 1));
    public static final short INT16_MAX = 32767;
    public static final int INT32_MIN = -(2147483647 + 1);
    public static final int INT32_MAX = 2147483647;
    public static final long INT64_MIN = -(9223372036854775807L + 1);
    public static final long INT64_MAX = 9223372036854775807L;

    // Unsigned integer types
    public static final int UINT8_MAX = 0xff;
    public static final int UINT16_MAX = 0xffff;
    public static final long UINT32_MAX = 0xffffffffL;
    public static final long UINT64_MAX = 0xffffffffffffffffL;

    // Least type ranges
    public static final byte INT_LEAST8_MIN = INT8_MIN;
    public static final byte INT_LEAST8_MAX = INT8_MAX;
    public static final short INT_LEAST16_MIN = INT16_MIN;
    public static final short INT_LEAST16_MAX = INT16_MAX;
    public static final int INT_LEAST32_MIN = INT32_MIN;
    public static final int INT_LEAST32_MAX = INT32_MAX;
    public static final long INT_LEAST64_MIN = INT64_MIN;
    public static final long INT_LEAST64_MAX = INT64_MAX;
    public static final int UINT_LEAST8_MAX = UINT8_MAX;
    public static final int UINT_LEAST16_MAX = UINT16_MAX;
    public static final long UINT_LEAST32_MAX = UINT32_MAX;
    public static final long UINT_LEAST64_MAX = UINT64_MAX;

    // Fast type ranges
    public static final byte INT_FAST8_MIN = INT8_MIN;
    public static final byte INT_FAST8_MAX = INT8_MAX;
    public static final int INT_FAST16_MIN = INT32_MIN;
    public static final int INT_FAST16_MAX = INT32_MAX;
    public static final int INT_FAST32_MIN = INT32_MIN;
    public static final int INT_FAST32_MAX = INT32_MAX;
    public static final long INT_FAST64_MIN = INT64_MIN;
    public static final long INT_FAST64_MAX = INT64_MAX;
    public static final int UINT_FAST8_MAX = UINT8_MAX;
    public static final long UINT_FAST16_MAX = UINT32_MAX;
    public static final long UINT_FAST32_MAX = UINT32_MAX;
    public static final long UINT_FAST64_MAX = UINT64_MAX;

    // Pointer type ranges based on architecture
    public static final long INTPTR_MIN;
    public static final long INTPTR_MAX;
    public static final long UINTPTR_MAX;

    static {
        if (System.getProperty("os.arch").equals("amd64") || System.getProperty("os.arch").equals("x86_64")) {
            INTPTR_MIN = INT64_MIN;
            INTPTR_MAX = INT64_MAX;
            UINTPTR_MAX = UINT64_MAX;
        } else {
            INTPTR_MIN = INT32_MIN;
            INTPTR_MAX = INT32_MAX;
            UINTPTR_MAX = UINT32_MAX;
        }
    }

    // Intmax type ranges
    public static final long INTMAX_MIN = INT64_MIN;
    public static final long INTMAX_MAX = INT64_MAX;
    public static final long UINTMAX_MAX = UINT64_MAX;

    // Size type range
    public static final long SIZE_MAX = (System.getProperty("os.arch").equals("amd64") || System.getProperty("os.arch").equals("x86_64")) ?
            UINT64_MAX : UINT32_MAX;

    // Sig atomic ranges
    public static final int SIG_ATOMIC_MIN = INT32_MIN;
    public static final int SIG_ATOMIC_MAX = INT32_MAX;

    // WCHAR and WINT ranges
    public static final int WCHAR_MIN = 0x0000;
    public static final int WCHAR_MAX = 0xffff;
    public static final int WINT_MIN = 0x0000;
    public static final int WINT_MAX = 0xffff;

    // Macros for constants (in Java, they can be replaced with direct constants)
    public static final int INT8_C(int x) { return x; }
    public static final int INT16_C(int x) { return x; }
    public static final int INT32_C(int x) { return x; }
    public static final long INT64_C(long x) { return x; }

    public static final int UINT8_C(int x) { return x; }
    public static final int UINT16_C(int x) { return x; }
    public static final long UINT32_C(long x) { return x; }
    public static final long UINT64_C(long x) { return x; }

    public static final long INTMAX_C(long x) { return x; }
    public static final long UINTMAX_C(long x) { return x; }
}
