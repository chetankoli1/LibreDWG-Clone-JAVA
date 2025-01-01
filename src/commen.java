import java.io.IOException;

class dwg_versions{
    public DWG_VERSION_TYPE r;
    public String type;
    public String hdr;
    public String desc;
    public int dwg_version;

    public dwg_versions(DWG_VERSION_TYPE r, String type, String hdr, String desc, int dwg_version) {
        this.r = r;
        this.type = type;
        this.hdr = hdr;
        this.desc = desc;
        this.dwg_version = dwg_version;
    }

    dwg_versions(){

    }
}

class commonvar{
    public static final String CreatedBy = "LibreDWG UNKNOWN";
    static void Sw_write(Object writeValue) throws IOException
    {
        if (writeValue instanceof Double) {
            Double doubleValue = (Double) writeValue;
            if (!writeValue.toString().contains(".")) {
                config.streamWriter.write(String.format("%.1f", doubleValue));
            } else {
                if (Double.parseDouble(writeValue.toString()) <= 0) {
                    config.streamWriter.write("0.0");
                } else {
                    config.streamWriter.write(writeValue.toString());
                }
            }
        } else if (writeValue instanceof Integer) {
            config.streamWriter.write(writeValue.toString());
        } else if (writeValue instanceof String || writeValue instanceof Character) {
            if (writeValue instanceof String && !((String) writeValue).contains("[")) {
                config.streamWriter.write("\"" + writeValue + "\"");
            } else {
                config.streamWriter.write(writeValue.toString());
            }
        } else {
            config.streamWriter.write(writeValue.toString());
        }
    }

    static String memcpy(String dest, int destPos, String src, int length) {
        return dest.substring(0, destPos) + src.substring(0, length) + dest.substring(destPos + length);
    }

    static char[] memmove(char[] dest, int destPos, int srcPos, int length) {
        if (destPos < srcPos) {
            for (int i = 0; i < length; i++) {
                dest[destPos + i] = dest[srcPos + i];
            }
        } else {
            for (int i = length - 1; i >= 0; i--) {
                dest[destPos + i] = dest[srcPos + i];
            }
        }
        return dest;
    }

    /**
     * Compares two byte arrays for equality up to a given length.
     *
     * @param buf1 the first byte array
     * @param buf2 the second byte array
     * @param size the number of bytes to compare
     * @return 0 if the arrays are equal up to the specified length,
     *         a negative number if the first differing byte in arr1 is less than that in arr2,
     *         or a positive number if the first differing byte in arr1 is greater than that in arr2.
     */
    public static int memcmp(char[] buf1, char[] buf2, long index, long size) {
        if (buf1 == null || buf2 == null) {
            throw new IllegalArgumentException("Input arrays must not be null.");
        }
        if (index < 0 || index + size > buf2.length || size > buf1.length) {
            throw new IllegalArgumentException("Invalid index or size exceeds array bounds.");
        }

        for (int i = 0; i < size; i++) {
            int diff = (buf1[i] & 0xFF) - (buf2[(int) index + i] & 0xFF);
            if (diff != 0) {
                return diff;
            }
        }
        return 0;
    }

}

public class commen {

    static double M_PI = 3.14159265358979323846;
    static double M_PI_2 = 1.57079632679489661923132169163975144;
    /**
     * Converts a 64-bit big-endian value to host byte order.
     * Reverses the byte order of the input long value.
     *
     * @param value the 64-bit big-endian value
     * @return the value in host byte order
     */
    public static long be64toh(long value) {
        return ((value & 0xff00000000000000L) >>> 56)
                | ((value & 0x00ff000000000000L) >>> 40)
                | ((value & 0x0000ff0000000000L) >>> 24)
                | ((value & 0x000000ff00000000L) >>> 8)
                | ((value & 0x00000000ff000000L) << 8)
                | ((value & 0x0000000000ff0000L) << 24)
                | ((value & 0x000000000000ff00L) << 40)
                | ((value & 0x00000000000000ffL) << 56);
    }

    /**
     * Converts a 64-bit little-endian value to host byte order (big-endian or native).
     *
     * @param value The 64-bit little-endian value to be converted.
     * @return The value in host byte order after reversing the bytes.
     */
    public static long le64toh(long value) {
        return Long.reverseBytes(value);
    }

    public static void VALUEOUTOFBOUNDS(Object srcLen, int destLen) {
        if((int)srcLen > destLen)
        {
            System.out.println("value out of bound");
        }
    }

    static long labs(int offset) {
        return Math.abs(offset);
    }

    static boolean strNE(String source, String compare)
    {
        if(strcmp(source, compare) != 0)
        {
            return true;
        }
        return false;
    }

    static enum DWG_SENTINEL
    {
        DWG_SENTINEL_HEADER_END,
        DWG_SENTINEL_THUMBNAIL_BEGIN,
        DWG_SENTINEL_THUMBNAIL_END,
        DWG_SENTINEL_VARIABLE_BEGIN,
        DWG_SENTINEL_VARIABLE_END,
        DWG_SENTINEL_CLASS_BEGIN,
        DWG_SENTINEL_CLASS_END,
        DWG_SENTINEL_2NDHEADER_BEGIN,
        DWG_SENTINEL_2NDHEADER_END,
        DWG_SENTINEL_R11_ENTITIES_BEGIN,
        DWG_SENTINEL_R11_ENTITIES_END,
        DWG_SENTINEL_R11_BLOCK_BEGIN,
        DWG_SENTINEL_R11_BLOCK_END,
        DWG_SENTINEL_R11_LAYER_BEGIN,
        DWG_SENTINEL_R11_LAYER_END,
        DWG_SENTINEL_R11_STYLE_BEGIN,
        DWG_SENTINEL_R11_STYLE_END,
        DWG_SENTINEL_R11_LTYPE_BEGIN,
        DWG_SENTINEL_R11_LTYPE_END,
        DWG_SENTINEL_R11_VIEW_BEGIN,
        DWG_SENTINEL_R11_VIEW_END,
        DWG_SENTINEL_R11_UCS_BEGIN,
        DWG_SENTINEL_R11_UCS_END,
        DWG_SENTINEL_R11_VPORT_BEGIN,
        DWG_SENTINEL_R11_VPORT_END,
        DWG_SENTINEL_R11_APPID_BEGIN,
        DWG_SENTINEL_R11_APPID_END,
        DWG_SENTINEL_R11_DIMSTYLE_BEGIN,
        DWG_SENTINEL_R11_DIMSTYLE_END,
        DWG_SENTINEL_R11_VX_BEGIN,
        DWG_SENTINEL_R11_VX_END,
        DWG_SENTINEL_R11_BLOCK_ENTITIES_BEGIN,
        DWG_SENTINEL_R11_BLOCK_ENTITIES_END,
        DWG_SENTINEL_R11_EXTRA_ENTITIES_BEGIN,
        DWG_SENTINEL_R11_EXTRA_ENTITIES_END,
        DWG_SENTINEL_R11_AUXHEADER_BEGIN,
        DWG_SENTINEL_R11_AUXHEADER_END
    }
    static DWG_VERSION_TYPE dwg_version_hdr_type(String hdr) {
        for(int i = DWG_VERSION_TYPE.R_AFTER.ordinal() - 1; i > 0; i--)
            if (strEQ(dwg_versions[i].hdr, hdr)) {
                return dwg_versions[i].r;
            }
        return DWG_VERSION_TYPE.R_INVALID;
    }

    static DWG_VERSION_TYPE dwg_version_hdr_type2(String hdr, int dwg_version)
    {
        for(int i = DWG_VERSION_TYPE.R_AFTER.ordinal() - 1; i > 0; i--)
            if (strEQ(dwg_versions[i].hdr, hdr)) {
                if(dwg_version == 0 || dwg_versions[i].dwg_version == dwg_version)
                    return dwg_versions[i].r;
            }
        return DWG_VERSION_TYPE.R_INVALID;
    }

    public static void strcpy(char[] src, char[] dest, int length)
    {
        for(int i = 0; i < length; i++)
        {
            src[i] = dest[i];
        }
    }

    public static void strcpy(char[] src, byte[] dest, int length)
    {
        for(int i = 0; i < length; i++)
        {
            src[i] = (char)dest[i];
        }
    }

    public static boolean strEQ(String src, String dest)
    {
        if(src != null && src.equals(dest))
        {
            return true;
        }
        return false;
    }

    public static boolean strcmp(String str1, String str2, int n) {
        if (str1 == null || str2 == null) {
            throw new IllegalArgumentException("Null strings are not allowed");
        }

        // Ensure n doesn't exceed the length of either string
        int minLength = Math.min(Math.min(str1.length(), str2.length()), n);

        for (int i = 0; i < minLength; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return false;
            }
        }

        return minLength == n; // True if exactly n characters were compared
    }

    static int strcmp(String s1, String s2)
    {
        if(s1.equals(s2))
        {
            return 0;
        }
        return 1;
    }

    static boolean VERSIONS(DWG_VERSION_TYPE v1,DWG_VERSION_TYPE v2,Bit_Chain dat)
    {
        return dat.version.ordinal() >= v1.ordinal() && dat.version.ordinal() <= v2.ordinal();
    }

    static boolean VERSION(DWG_VERSION_TYPE v1,Bit_Chain dat)
    {
        return dat.version.ordinal() == v1.ordinal();
    }
    static boolean UNTIL(DWG_VERSION_TYPE v1, Bit_Chain dat)
    {
        return dat.version.ordinal() <= v1.ordinal();
    }

    static boolean SINCE(DWG_VERSION_TYPE version, Bit_Chain dat)
    {
        return dat.version.ordinal() >= version.ordinal();
    }

    static boolean PRE(DWG_VERSION_TYPE version, Bit_Chain dat)
    {
        return dat.version.ordinal() < version.ordinal();
    }
    static boolean memcmp(String a1, String b1, int length) {
        return !a1.substring(0, length).equals(b1);
    }

    static dwg_versions[] dwg_versions =
            {
                    new dwg_versions(DWG_VERSION_TYPE.R_INVALID, "invalid", "INVALI", "No DWG", 0),
                    new dwg_versions(DWG_VERSION_TYPE.R_1_1, "r1.1", "MC0.0", "MicroCAD Release 1.1", 0),
                    new dwg_versions(DWG_VERSION_TYPE.R_1_2, "r1.2", "AC1.2", "AutoCAD Release 1.2", 0),
                    new dwg_versions(DWG_VERSION_TYPE.R_1_3, "r1.3", "AC1.3", "AutoCAD Release 1.3", 1),
                    new dwg_versions(DWG_VERSION_TYPE.R_1_4, "r1.4", "AC1.40", "AutoCAD Release 1.4", 2),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_0b, "r2.0b", "AC1.50", "AutoCAD 2.0 beta", 3),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_0, "r2.0", "AC1.50", "AutoCAD Release 2.0", 4),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_10, "r2.10", "AC2.10", "AutoCAD Release 2.10", 5),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_21, "r2.21", "AC2.21", "AutoCAD Release 2.21", 6),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_22, "r2.22", "AC2.22", "AutoCAD Release 2.22", 7),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_4, "r2.4", "AC1001", "AutoCAD Release 2.4", 8),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_5, "r2.5", "AC1002", "AutoCAD Release 2.5", 9),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_6, "r2.6", "AC1003", "AutoCAD Release 2.6", 10),
                    new dwg_versions(DWG_VERSION_TYPE.R_9, "r9", "AC1004", "AutoCAD Release 9", 0xb),
                    new dwg_versions(DWG_VERSION_TYPE.R_9c1, "r9c1", "AC1005", "AutoCAD Release 9c1", 0xc),
                    new dwg_versions(DWG_VERSION_TYPE.R_10, "r10", "AC1006", "AutoCAD Release 10", 0xd),
                    new dwg_versions(DWG_VERSION_TYPE.R_11b1, "r11b1", "AC1007", "AutoCAD 11 beta 1", 0xe),
                    new dwg_versions(DWG_VERSION_TYPE.R_11b2, "r11b2", "AC1008", "AutoCAD 11 beta 2", 0xf),
                    new dwg_versions(DWG_VERSION_TYPE.R_11, "r11", "AC1009", "AutoCAD Release 11/12 (LT R1/R2)", 0x10),
                    new dwg_versions(DWG_VERSION_TYPE.R_13b1, "r13b1", "AC1010", "AutoCAD pre-R13 a", 0x11),
                    new dwg_versions(DWG_VERSION_TYPE.R_13b2, "r13b2", "AC1011", "AutoCAD pre-R13 b", 0x12),
                    new dwg_versions(DWG_VERSION_TYPE.R_13, "r13", "AC1012", "AutoCAD Release 13", 0x13),
                    new dwg_versions(DWG_VERSION_TYPE.R_13c3, "r13c3", "AC1013", "AutoCAD Release 13c3", 0x14),
                    new dwg_versions(DWG_VERSION_TYPE.R_14, "r14", "AC1014", "AutoCAD Release 14", 0x15),
                    new dwg_versions(DWG_VERSION_TYPE.R_2000b, "r2000b", "AC1500", "AutoCAD 2000 beta", 0x16),
                    new dwg_versions(DWG_VERSION_TYPE.R_2000, "r2000", "AC1015", "AutoCAD Release 2000", 0x17),
                    new dwg_versions(DWG_VERSION_TYPE.R_2000i, "r2000i", "AC1016", "AutoCAD Release 2000i", 0x17),
                    new dwg_versions(DWG_VERSION_TYPE.R_2002, "r2002", "AC1017", "AutoCAD Release 2002", 0x17),
                    new dwg_versions(DWG_VERSION_TYPE.R_2004a, "r2004a", "AC402a", "AutoCAD 2004 alpha a", 0x18),
                    new dwg_versions(DWG_VERSION_TYPE.R_2004b, "r2004b", "AC402b", "AutoCAD 2004 alpha b", 0x18),
                    new dwg_versions(DWG_VERSION_TYPE.R_2004c, "r2004c", "AC1018", "AutoCAD 2004 beta", 0x18),
                    new dwg_versions(DWG_VERSION_TYPE.R_2004, "r2004", "AC1018", "AutoCAD Release 2004", 0x19),
                    new dwg_versions(DWG_VERSION_TYPE.R_2007a, "r2007a", "AC701a", "AutoCAD 2007 alpha", 0x1a),
                    new dwg_versions(DWG_VERSION_TYPE.R_2007b, "r2007b", "AC1021", "AutoCAD 2007 beta", 0x1a),
                    new dwg_versions(DWG_VERSION_TYPE.R_2007, "r2007", "AC1021", "AutoCAD Release 2007", 0x1b),
                    new dwg_versions(DWG_VERSION_TYPE.R_2010b, "r2010b", "AC1024", "AutoCAD 2010 beta", 0x1c),
                    new dwg_versions(DWG_VERSION_TYPE.R_2010, "r2010", "AC1024", "AutoCAD Release 2010", 0x1d),
                    new dwg_versions(DWG_VERSION_TYPE.R_2013b, "r2013b", "AC1027", "AutoCAD 2013 beta", 0x1e),
                    new dwg_versions(DWG_VERSION_TYPE.R_2013, "r2013", "AC1027", "AutoCAD Release 2013", 0x1f),
                    new dwg_versions(DWG_VERSION_TYPE.R_2018b, "r2018b", "AC1032", "AutoCAD 2018 beta", 0x20),
                    new dwg_versions(DWG_VERSION_TYPE.R_2018, "r2018", "AC1032", "AutoCAD Release 2018", 0x21),
                    new dwg_versions(DWG_VERSION_TYPE.R_2022b, "r2022b", "AC103-4", "AutoCAD 2022 beta", 0x24),
                    new dwg_versions(DWG_VERSION_TYPE.R_AFTER, "r>2022", "", "AutoCAD Release >2022", 0),
            };

    public static boolean memBEGINc(String s1, String s2) {
        return (s1.length() >= s2.length() && commen.strcmp(s1,s2,s2.length()));
    }

    static char[] dwg_sentinel(DWG_SENTINEL sentinel_id)
    {
        char[][] sentinels =
                {
                // DWG_SENTINEL_HEADER_END
                { 0x95, 0xA0, 0x4E, 0x28, 0x99, 0x82, 0x1A, 0xE5, 0x5E, 0x41, 0xE0,
                        0x5F, 0x9D, 0x3A, 0x4D, 0x00 },
                // DWG_SENTINEL_THUMBNAIL_BEGIN
                { 0x1F, 0x25, 0x6D, 0x07, 0xD4, 0x36, 0x28, 0x28, 0x9D, 0x57, 0xCA,
                        0x3F, 0x9D, 0x44, 0x10, 0x2B },
                // DWG_SENTINEL_THUMBNAIL_END
                { 0xE0, 0xDA, 0x92, 0xF8, 0x2B, 0xc9, 0xD7, 0xD7, 0x62, 0xA8, 0x35,
                        0xC0, 0x62, 0xBB, 0xEF, 0xD4 },
                // DWG_SENTINEL_VARIABLE_BEGIN
                { 0xCF, 0x7B, 0x1F, 0x23, 0xFD, 0xDE, 0x38, 0xA9, 0x5F, 0x7C, 0x68,
                        0xB8, 0x4E, 0x6D, 0x33, 0x5F },
                // DWG_SENTINEL_VARIABLE_END
                { 0x30, 0x84, 0xE0, 0xDC, 0x02, 0x21, 0xC7, 0x56, 0xA0, 0x83, 0x97,
                        0x47, 0xB1, 0x92, 0xCC, 0xA0 },
                // DWG_SENTINEL_CLASS_BEGIN
                { 0x8D, 0xA1, 0xC4, 0xB8, 0xC4, 0xA9, 0xF8, 0xC5, 0xC0, 0xDC, 0xF4,
                        0x5F, 0xE7, 0xCF, 0xB6, 0x8A },
                // DWG_SENTINEL_CLASS_END
                { 0x72, 0x5E, 0x3B, 0x47, 0x3B, 0x56, 0x07, 0x3A, 0x3F, 0x23, 0x0B,
                        0xA0, 0x18, 0x30, 0x49, 0x75 },
                // DWG_SENTINEL_2NDHEADER_BEGIN
                { 0xD4, 0x7B, 0x21, 0xCE, 0x28, 0x93, 0x9F, 0xBF, 0x53, 0x24, 0x40,
                        0x09, 0x12, 0x3C, 0xAA, 0x01 },
                // DWG_SENTINEL_2NDHEADER_END
                { 0x2B, 0x84, 0xDE, 0x31, 0xD7, 0x6C, 0x60, 0x40, 0xAC, 0xDB, 0xBF,
                        0xF6, 0xED, 0xC3, 0x55, 0xFE },
                // DWG_SENTINEL_R11_ENTITIES_BEGIN C46E6854F86E3330633EC1852ADC9401
                { 0xC4, 0x6E, 0x68, 0x54, 0xF8, 0x6E, 0x33, 0x30, 0x63, 0x3E, 0xC1,
                        0x85, 0x2A, 0xDC, 0x94, 0x01 },
                // DWG_SENTINEL_R11_ENTITIES_END   3B9197AB0791CCCF9CC13E7AD5236BFE
                { 0x3B, 0x91, 0x97, 0xAB, 0x07, 0x91, 0xCC, 0xCF, 0x9C, 0xC1, 0x3E,
                        0x7A, 0xD5, 0x23, 0x6B, 0xFE },
                // DWG_SENTINEL_R11_BLOCK_BEGIN DBEFB3F0C73E6DA6C9B6245C4C6F32CB
                { 0xDB, 0xEF, 0xB3, 0xF0, 0xC7, 0x3E, 0x6D, 0xA6, 0xC9, 0xB6, 0x24,
                        0x5C, 0x4C, 0x6F, 0x32, 0xCB },
                // DWG_SENTINEL_R11_BLOCK_END   24104C0F38C192593649DBA3B390CD34
                { 0x24, 0x10, 0x4C, 0x0F, 0x38, 0xC1, 0x92, 0x59, 0x36, 0x49, 0xDB,
                        0xA3, 0xB3, 0x90, 0xCD, 0x34 },
                // DWG_SENTINEL_R11_LAYER_BEGIN 0EC4646FBB1DD38B0049C2EF18EA6FFB
                { 0x0E, 0xC4, 0x64, 0x6F, 0xBB, 0x1D, 0xD3, 0x8B, 0x00, 0x49, 0xC2,
                        0xEF, 0x18, 0xEA, 0x6F, 0xFB },
                // DWG_SENTINEL_R11_LAYER_END   F13B9B9044E22C74FFB63D10E7159004
                { 0xF1, 0x3B, 0x9B, 0x90, 0x44, 0xE2, 0x2C, 0x74, 0xFF, 0xB6, 0x3D,
                        0x10, 0xE7, 0x15, 0x90, 0x04 },
                // DWG_SENTINEL_R11_STYLE_BEGIN E23EC182439F617750ABC76696000618
                { 0xE2, 0x3E, 0xC1, 0x82, 0x43, 0x9F, 0x61, 0x77, 0x50, 0xAB, 0xC7,
                        0x66, 0x96, 0x00, 0x06, 0x18 },
                // DWG_SENTINEL_R11_STYLE_END   1DC13E7DBC609E88AF54389969FFF9E7
                { 0x1D, 0xC1, 0x3E, 0x7D, 0xBC, 0x60, 0x9E, 0x88, 0xAF, 0x54, 0x38,
                        0x99, 0x69, 0xFF, 0xF9, 0xE7 },
                // DWG_SENTINEL_R11_LTYPE_BEGIN AC901ACA1CBD951516164C14CE1888AF
                { 0xAC, 0x90, 0x1A, 0xCA, 0x1C, 0xBD, 0x95, 0x15, 0x16, 0x16, 0x4C,
                        0x14, 0xCE, 0x18, 0x88, 0xAF },
                // DWG_SENTINEL_R11_LTYPE_END   536FE535E3426AEAE9E9B3EB31E77750
                { 0x53, 0x6F, 0xE5, 0x35, 0xE3, 0x42, 0x6A, 0xEA, 0xE9, 0xE9, 0xB3,
                        0xEB, 0x31, 0xE7, 0x77, 0x50 },
                // DWG_SENTINEL_R11_VIEW_BEGIN  C13CAA5668F4B41E4B74F408424DBFA5
                { 0xC1, 0x3C, 0xAA, 0x56, 0x68, 0xF4, 0xB4, 0x1E, 0x4B, 0x74, 0xF4,
                        0x08, 0x42, 0x4D, 0xBF, 0xA5 },
                // DWG_SENTINEL_R11_VIEW_END    3EC355A9970B4BE1B48B0BF7BDB2405A
                { 0x3E, 0xC3, 0x55, 0xA9, 0x97, 0x0B, 0x4B, 0xE1, 0xB4, 0x8B, 0x0B,
                        0xF7, 0xBD, 0xB2, 0x40, 0x5A },
                // DWG_SENTINEL_R11_UCS_BEGIN   604AFA3D8490CC5BEFE7D6A57F1E61CD
                { 0x60, 0x4A, 0xFA, 0x3D, 0x84, 0x90, 0xCC, 0x5B, 0xEF, 0xE7, 0xD6,
                        0xA5, 0x7F, 0x1E, 0x61, 0xCD },
                // DWG_SENTINEL_R11_UCS_END     9FB505C27B6F33A41018295A80E19E32
                { 0x9F, 0xB5, 0x05, 0xC2, 0x7B, 0x6F, 0x33, 0xA4, 0x10, 0x18, 0x29,
                        0x5A, 0x80, 0xE1, 0x9E, 0x32 },
                // DWG_SENTINEL_R11_VPORT_BEGIN F6ED44612ADCE47B4EB92BBB6660638D
                { 0xF6, 0xED, 0x44, 0x61, 0x2A, 0xDC, 0xE4, 0x7B, 0x4E, 0xB9, 0x2B,
                        0xBB, 0x66, 0x60, 0x63, 0x8D },
                // DWG_SENTINEL_R11_VPORT_END   0912BB9ED5231B84B146D444999F9C72
                { 0x09, 0x12, 0xBB, 0x9E, 0xD5, 0x23, 0x1B, 0x84, 0xB1, 0x46, 0xD4,
                        0x44, 0x99, 0x9F, 0x9C, 0x72 },
                // DWG_SENTINEL_R11_APPID_BEGIN E125C25036686C0C3BD35D56C1791C3A
                { 0xE1, 0x25, 0xC2, 0x50, 0x36, 0x68, 0x6C, 0x0C, 0x3B, 0xD3, 0x5D,
                        0x56, 0xC1, 0x79, 0x1C, 0x3A },
                // DWG_SENTINEL_R11_APPID_END   1EDA3DAFC99793F3C42CA2A93E86E3C5
                { 0x1E, 0xDA, 0x3D, 0xAF, 0xC9, 0x97, 0x93, 0xF3, 0xC4, 0x2C, 0xA2,
                        0xA9, 0x3E, 0x86, 0xE3, 0xC5 },
                // DWG_SENTINEL_R11_DIMSTYLE_BEGIN B4183E42C99FFFE5B6E2CBB375C3C3B0
                { 0xB4, 0x18, 0x3E, 0x42, 0xC9, 0x9F, 0xFF, 0xE5, 0xB6, 0xE2, 0xCB,
                        0xB3, 0x75, 0xC3, 0xC3, 0xB0 },
                // DWG_SENTINEL_R11_DIMSTYLE_END   4BE7C1BD3660001A491D344C8A3C3C4F
                { 0x4B, 0xE7, 0xC1, 0xBD, 0x36, 0x60, 0x00, 0x1A, 0x49, 0x1D, 0x34,
                        0x4C, 0x8A, 0x3C, 0x3C, 0x4F },
                // DWG_SENTINEL_R11_VX_BEGIN    E0CA367CCEE7586F2B7D745505F1447F
                { 0xE0, 0xCA, 0x36, 0x7C, 0xCE, 0xE7, 0x58, 0x6F, 0x2B, 0x7D, 0x74,
                        0x55, 0x05, 0xF1, 0x44, 0x7F },
                // DWG_SENTINEL_R11_VX_END      1F35C9833118A790D4828BAAFA0EBB80
                { 0x1F, 0x35, 0xC9, 0x83, 0x31, 0x18, 0xA7, 0x90, 0xD4, 0x82, 0x8B,
                        0xAA, 0xFA, 0x0E, 0xBB, 0x80 },
                // DWG_SENTINEL_R11_BLOCK_ENTITIES_BEGIN
                // 722B7DEC3E8C886C7A720AFDC86C8426
                { 0x72, 0x2B, 0x7D, 0xEC, 0x3E, 0x8C, 0x88, 0x6C, 0x7A, 0x72, 0x0A,
                        0xFD, 0xC8, 0x6C, 0x84, 0x26 },
                // DWG_SENTINEL_R11_BLOCK_ENTITIES_END
                // 8DD48213C1737793858DF50237937BD9
                { 0x8D, 0xD4, 0x82, 0x13, 0xC1, 0x73, 0x77, 0x93, 0x85, 0x8D, 0xF5,
                        0x02, 0x37, 0x93, 0x7B, 0xD9 },
                // DWG_SENTINEL_R11_EXTRA_ENTITIES_BEGIN
                // D5F9D3BB0AA969A6CD1C87C7EE804B17
                { 0xD5, 0xF9, 0xD3, 0xBB, 0x0A, 0xA9, 0x69, 0xA6, 0xCD, 0x1C, 0x87,
                        0xC7, 0xEE, 0x80, 0x4B, 0x17 },
                // DWG_SENTINEL_R11_EXTRA_ENTITIES_END
                // 2A062C44F556965932E37838117FB4E8
                { 0x2A, 0x06, 0x2C, 0x44, 0xF5, 0x56, 0x96, 0x59, 0x32, 0xE3, 0x78,
                        0x38, 0x11, 0x7F, 0xB4, 0xE8 },
                // DWG_SENTINEL_R11_AUXHEADER_BEGIN 298DD149A9731FEA99DE32F94D0AE019
                { 0x29, 0x8D, 0xD1, 0x49, 0xA9, 0x73, 0x1F, 0xEA, 0x99, 0xDE, 0x32,
                        0xF9, 0x4D, 0x0A, 0xE0, 0x19 },
                // DWG_SENTINEL_R11_AUXHEADER_END D6722EB6568CE0156621CD06B2F51FE6
                { 0xD6, 0x72, 0x2E, 0xB6, 0x56, 0x8C, 0xE0, 0x15, 0x66, 0x21, 0xCD,
                        0x06, 0xB2, 0xF5, 0x1F, 0xE6 },
                // DWG_SENTINEL_ILLEGAL (used for memcmp)
                { 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE, 0xFE,
                        0xFE, 0xFE, 0xFE, 0xFE, 0xFE }
        };

        if(sentinel_id.ordinal() > DWG_SENTINEL.DWG_SENTINEL_R11_AUXHEADER_END.ordinal())
        {
            return sentinels[DWG_SENTINEL.DWG_SENTINEL_R11_AUXHEADER_END.ordinal()+1];
        }
        return sentinels[sentinel_id.ordinal()];
    }

    // keep in sync with common.h DWG_BITS
String[] dwg_bits_name
    = { "UNKNOWN", "RC",  "RS",  "RL",  "B",       "BB",  "3B",     "4BITS",
            "BS",      "BL",  "BLd", "RLL", "RD",      "BD",  "MC",     "UMC",
            "MS",      "TV",  "TU",  "T",   "TF",      "T32", "HANDLE", "BE",
            "DD",      "BT",  "BOT", "BLL", "TIMEBLL", "CMC", "ENC",    "2RD",
            "3RD",     "2BD", "3BD", "2DD", "3DD",     "CRC", "CRC64",  "RLLd" };

// minimal size of type in bits
// keep in sync with above
// used by unit-tests
static char[] dwg_bits_size = {
            0,   //"UNKNOWN",
            8,   //"RC",
            16,  //"RS",
            32,  //"RL",
            1,   //"B",
            2,   //"BB",
            3,   //"3B",
            4,   //"4BITS",
            2,   //"BS", 10,18
            2,   //"BL", 10,34
            2,   //"BLd", 10,34
            64,  //"RLL",
            64,  //"RD",
            2,   //"BD", 66
            1,   //"MC", 1-4
            1,   //"UMC", 1-4
            16,  //"MS", 32
            2,   //"TV",
            18,  //"TU",
            2,   //"T",
            1,   //"TF",
            2,   //"T32",
            4,   //"TU32",
            8,   //"HANDLE",
            1,   //"BE", or 3BD
            2,   //"DD",
            1,   //"BT",
            10,  //"BOT",
            3,   //"BLL",
            4,   //"TIMEBLL", 2xBL
            2,   //"CMC", r2004+: +2
            4,   //"ENC", r2004+
            128, //"2RD",
            196, //"3RD",
            4,   //"2BD",
            6,   //"3BD",
            4,   //"2DD",
            6,   //"3DD",
            8,   //"CRC",
            64,  //"CRC64",
            64,  //"RLLd",
    };
}
// keep in sync with common.c dwg_bits_name
enum DWG_BITS
{
    BITS_UNKNOWN,
    BITS_RC,      /** raw char (not compressed) */
BITS_RS,      /** raw 2-byte short (not compressed, big-endian) */
BITS_RL,      /** raw 4-byte long (not compressed, big-endian) */
BITS_B,       /** bit (1 or 0) */
BITS_BB,      /** special 2-bit code (entmode in entities, for instance) */
BITS_3B,      /** special 3-bit code R24+ */
BITS_4BITS,   /** 4 bits, r2000+ for VIEWMODE */
BITS_BS,      /** bitshort */
BITS_BL,      /** bitlong uint32_t */
BITS_BLd,     /** signed bitlong int32_t */
BITS_RLL,     /** raw 8-byte long long (not compressed, big-endian) */
BITS_RD,      /** raw double (not compressed, big-endian) */
BITS_BD,      /** bitdouble */
BITS_MC,      /** modular char */
BITS_UMC,     /** unsigned modular char, max 4 bytes (handlestream_size) */
BITS_MS,      /** modular short */
BITS_TV,      /** text value, -r2007 */
BITS_TU,      /** Unicode text (bitshort character length, followed by
 UCS-2 string). Unicode text is read from the
 “string stream” within the object data. r2007+ */
BITS_T,       /** text, version dependent: TV or TU */
BITS_TF,      /** fixed-length text */
BITS_T32,     /** String32 type */
BITS_TU32,    /** StringU32 type (FileDepList.features) */
BITS_HANDLE,  /** handle reference (see the HANDLE REFERENCES section) */
BITS_BE,      /** BitExtrusion */
BITS_DD,      /** BitDouble With Default */
BITS_BT,      /** BitThickness */
BITS_BOT,     /** Bit object type: 2010+ (BB + 1-2RC) */
BITS_BLL,     /** bitlonglong R24+ */
BITS_TIMEBLL, /** time long.long */
BITS_CMC,     /** CmColor value */
BITS_ENC,     /** Entity CmColor value */
BITS_2RD,     /** 2 raw doubles **/
BITS_3RD,     /** 3 raw doubles **/
BITS_2BD,     /** 2D point (2 bitdoubles) **/
BITS_3BD,     /** 3D point (3 bitdoubles) **/
BITS_2DD,     /** 2 doubles with default **/
BITS_3DD,     /** 3 doubles with default **/
BITS_CRC,
    BITS_CRC64,
    BITS_RLLd
};