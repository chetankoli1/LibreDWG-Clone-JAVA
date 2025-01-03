import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

enum DWG_VERSION_TYPE {
    R_INVALID,
    // Releases based on https://autodesk.blogs.com/between_the_lines/autocad-release-history.html
    R_1_1,	/* MC0.0/1  MicroCAD Release 1 */
    R_1_2,	/* AC1.2/2  AutoCAD Release 2 */
    R_1_3,	/* AC1.3/?  AutoCAD Release 3 */
    R_1_4,	/* AC1.40/3 AutoCAD Release 4 */
    R_2_0b,	/* AC1.50/4 AutoCAD 2.0 beta */
    R_2_0,	/* AC1.50/4 AutoCAD Release 5 */
    R_2_10,	/* AC2.10/5 AutoCAD Release 6 */
    R_2_21,	/* AC2.21/6 AutoCAD Release ? */
    R_2_22,	/* AC2.22/7 AutoCAD Release ? */
    R_2_4,	/* AC1001/8 AutoCAD Release ? */
    R_2_5,	/* AC1002/9 AutoCAD Release 7 */
    R_2_6,	/* AC1003/10 AutoCAD Release 8 */
    R_9,		/* AC1004/0xb AutoCAD Release 9 */
    R_9c1,	/* AC1005/0xc AutoCAD Release 9c1 */
    R_10,		/* AC1006/0xd AutoCAD Release 10 */
    R_11b1,	/* AC1007/0xe AutoCAD 11 beta 1 */
    R_11b2,	/* AC1008/0xf AutoCAD 11 beta 2 */
    R_11,		/* AC1009/0x10 AutoCAD Release 11/12 (LT R1/R2) also use as R_12*/
    R_13b1,	/* AC1010/0x11 AutoCAD 13 beta 1 */
    R_13b2,	/* AC1011/0x12 AutoCAD 13 beta 2 */
    R_13,		/* AC1012/0x13 AutoCAD Release 13 */
    R_13c3,	/* AC1013/0x14 AutoCAD Release 13c3 */
    R_14,		/* AC1014/0x15 AutoCAD Release 14 */
    R_2000b,	/* AC1500/0x16 AutoCAD 2000 beta */
    R_2000,	/* AC1015/0x17 AutoCAD Release 2000 */
    R_2000i,	/* AC1016/0x17 AutoCAD Release 2000i */
    R_2002,	/* AC1017/0x17 AutoCAD Release 2002 */
    R_2004a,	/* AC402a/0x18 AutoCAD 2004 alpha a */
    R_2004b,	/* AC402b/0x18 AutoCAD 2004 alpha b */
    R_2004c,	/* AC1018/0x18 AutoCAD 2004 beta */
    R_2004,	/* AC1018/0x19 AutoCAD Release 2004 - 2006 */
//R_2005,	/* AC1019/0x19 AutoCAD 2005 */
//R_2006,	/* AC1020/0x19 AutoCAD 2006 */
    R_2007a,	/* AC701a/0x19 AutoCAD 2007 alpha 1 */
    R_2007b,	/* AC1021/0x19 AutoCAD 2007 beta */
    R_2007,	/* AC1021/0x1b AutoCAD Release 2007 - 2009 */
//R_2008,	/* AC1022/0x1b AutoCAD 2008 */
//R_2009,	/* AC1023/0x1b AutoCAD 2009 */
    R_2010b,	/* AC1024/0x1b AutoCAD 2009 */
    R_2010,	/* AC1024/0x1c AutoCAD Release 2010 - 2012 */
//R_2011,	/* AC1025/0x1d AutoCAD 2011 */
//R_2012,	/* AC1026/0x1e AutoCAD 2012 */
    R_2013b,	/* AC1027/0x1e AutoCAD 2013 beta */
    R_2013,	/* AC1027/0x1f AutoCAD Release 2013 - 2017 */
//R_2014,	/* AC1028/0x1f AutoCAD 2014 */
//R_2015,	/* AC1029/0x1f AutoCAD 2015 */
//R_2016,	/* AC1030/0x1f AutoCAD 2016 */
//R_2017,	/* AC1031/0x20 AutoCAD 2017 */
    R_2018b,	/* AC1032/0x20 AutoCAD 2018 beta */
    R_2018,	/* AC1032/0x21 AutoCAD Release 2018 - 2021 */
//R_2019,	/* AC1033/0x22 AutoCAD 2019 */
//R_2020,	/* AC1034/0x23 AutoCAD 2020 */
//R_2021,	/* AC1035/0x24 AutoCAD 2021 */
    R_2022b,	/* AC103-4 AutoCAD 2022 beta? */
    R_AFTER
}


public class bits {

    private static final int UINT_MAX =  0xffffffff;
    private static int loglevel;

    public static char bit_read_RC(Bit_Chain dat) {
        char result = 0;
        char _byte;

        if(CHK_OVERFLOW(dat,0))
        {
            return 0;
        }
        _byte = dat.chain[(int)dat._byte];
        if(dat.bit == 0)
        {
            result = _byte;
            dat._byte++;
        }else{
            result = (char) ((_byte << dat.bit) & 0xFF);
            if(dat._byte < dat.size - 1)
            {
                _byte = dat.chain[(int)dat._byte + 1];
                result |= (char) (_byte >> (8-dat.bit));
            }else{
                return (char)result;
            }
            bit_advance_position(dat,8);
        }
        return result;
    }

    static void bit_advance_position(Bit_Chain dat, int advance) {
        long pos = bit_position(dat);
        long endpos = dat.size * 8;

        long bitts = dat.bit + advance;
        if(pos+advance > endpos)
        {
            System.out.printf(
                    "Buffer overflow at pos %d.%d, size %d, advance by %d%n",
                    dat._byte, (int) dat.bit, dat.size, advance
            );
        }else if(pos + advance < 0)
        {
            System.out.printf(
                    "Buffer overflow at pos %d.%d, size %d, advance by %d%n",
                    dat._byte, (int) dat.bit, dat.size, advance
            );
            dat._byte = 0;
            dat.bit = 0;
            return;
        }
        dat._byte += (int)(bitts >> 3);
        dat.bit = (char)(bitts & 7);
    }

    static long bit_position(Bit_Chain dat) {
        return (dat._byte * 8) + (dat.bit & 7);
    }

    public static boolean CHK_OVERFLOW(Bit_Chain dat, int retval){
        if(dat._byte >= dat.size)
        {
            loglevel = dat.opts & dwg.DWG_OPTS_LOGLEVEL;
            return true;
        }

        return false;
    }

    public static int bit_read_RS(Bit_Chain dat) {
        char byte1;
        char byte2;
        byte1 = bit_read_RC(dat);
//        if(CHK_OVERFLOW(dat,0));
//        {
//            return 0;
//        }
        byte2 = bit_read_RC(dat);
        return (byte2 << 8 | byte1);

    }

    static long bit_read_RL(Bit_Chain dat) {
        int word1, word2;
        word1 = bit_read_RS(dat);
        if (CHK_OVERFLOW(dat, 0)) {
            return 0;
        }
        word2 = bit_read_RS(dat);

        // Mask both words to ensure they're treated as unsigned 16-bit values
        long result = (((long)(word2 & 0xFFFF) << 16) | (long)(word1 & 0xFFFF));
        return result;
    }

    static Dwg_Bitcode_TimeRLL bit_read_TIMERLL(Bit_Chain dat) {
        Dwg_Bitcode_TimeRLL temp = new Dwg_Bitcode_TimeRLL();

        temp.days = bit_read_RL(dat);
        temp.ms = bit_read_RL(dat);

        temp.value = temp.days +(temp.ms / 86400000.0);
        return temp;
    }
    static Dwg_Bitcode_TimeBLL bit_read_TIMEBLL(Bit_Chain dat) {
        Dwg_Bitcode_TimeBLL temp = new Dwg_Bitcode_TimeBLL();

        if(dat.from_version.ordinal() < DWG_VERSION_TYPE.R_13b1.ordinal())
        {
            temp.days = bit_read_RL(dat);
            temp.ms = bit_read_RL(dat);
        }
        else{
            temp.days = bit_read_BL(dat);
            temp.ms = bit_read_BL(dat);
        }
       // temp.value = temp.days +(temp.ms / 86400000.0);
        temp.value = temp.days + (temp.ms / 1e-8);
        return temp;
    }


    public static int bit_calc_CRC(int seed, int index, char[] addr, long len) {
        int dx = seed;

        int[] crctable = {
                0x0000, 0xC0C1, 0xC181, 0x0140, 0xC301, 0x03C0, 0x0280, 0xC241, 0xC601,
                0x06C0, 0x0780, 0xC741, 0x0500, 0xC5C1, 0xC481, 0x0440, 0xCC01, 0x0CC0,
                0x0D80, 0xCD41, 0x0F00, 0xCFC1, 0xCE81, 0x0E40, 0x0A00, 0xCAC1, 0xCB81,
                0x0B40, 0xC901, 0x09C0, 0x0880, 0xC841, 0xD801, 0x18C0, 0x1980, 0xD941,
                0x1B00, 0xDBC1, 0xDA81, 0x1A40, 0x1E00, 0xDEC1, 0xDF81, 0x1F40, 0xDD01,
                0x1DC0, 0x1C80, 0xDC41, 0x1400, 0xD4C1, 0xD581, 0x1540, 0xD701, 0x17C0,
                0x1680, 0xD641, 0xD201, 0x12C0, 0x1380, 0xD341, 0x1100, 0xD1C1, 0xD081,
                0x1040, 0xF001, 0x30C0, 0x3180, 0xF141, 0x3300, 0xF3C1, 0xF281, 0x3240,
                0x3600, 0xF6C1, 0xF781, 0x3740, 0xF501, 0x35C0, 0x3480, 0xF441, 0x3C00,
                0xFCC1, 0xFD81, 0x3D40, 0xFF01, 0x3FC0, 0x3E80, 0xFE41, 0xFA01, 0x3AC0,
                0x3B80, 0xFB41, 0x3900, 0xF9C1, 0xF881, 0x3840, 0x2800, 0xE8C1, 0xE981,
                0x2940, 0xEB01, 0x2BC0, 0x2A80, 0xEA41, 0xEE01, 0x2EC0, 0x2F80, 0xEF41,
                0x2D00, 0xEDC1, 0xEC81, 0x2C40, 0xE401, 0x24C0, 0x2580, 0xE541, 0x2700,
                0xE7C1, 0xE681, 0x2640, 0x2200, 0xE2C1, 0xE381, 0x2340, 0xE101, 0x21C0,
                0x2080, 0xE041, 0xA001, 0x60C0, 0x6180, 0xA141, 0x6300, 0xA3C1, 0xA281,
                0x6240, 0x6600, 0xA6C1, 0xA781, 0x6740, 0xA501, 0x65C0, 0x6480, 0xA441,
                0x6C00, 0xACC1, 0xAD81, 0x6D40, 0xAF01, 0x6FC0, 0x6E80, 0xAE41, 0xAA01,
                0x6AC0, 0x6B80, 0xAB41, 0x6900, 0xA9C1, 0xA881, 0x6840, 0x7800, 0xB8C1,
                0xB981, 0x7940, 0xBB01, 0x7BC0, 0x7A80, 0xBA41, 0xBE01, 0x7EC0, 0x7F80,
                0xBF41, 0x7D00, 0xBDC1, 0xBC81, 0x7C40, 0xB401, 0x74C0, 0x7580, 0xB541,
                0x7700, 0xB7C1, 0xB681, 0x7640, 0x7200, 0xB2C1, 0xB381, 0x7340, 0xB101,
                0x71C0, 0x7080, 0xB041, 0x5000, 0x90C1, 0x9181, 0x5140, 0x9301, 0x53C0,
                0x5280, 0x9241, 0x9601, 0x56C0, 0x5780, 0x9741, 0x5500, 0x95C1, 0x9481,
                0x5440, 0x9C01, 0x5CC0, 0x5D80, 0x9D41, 0x5F00, 0x9FC1, 0x9E81, 0x5E40,
                0x5A00, 0x9AC1, 0x9B81, 0x5B40, 0x9901, 0x59C0, 0x5880, 0x9841, 0x8801,
                0x48C0, 0x4980, 0x8941, 0x4B00, 0x8BC1, 0x8A81, 0x4A40, 0x4E00, 0x8EC1,
                0x8F81, 0x4F40, 0x8D01, 0x4DC0, 0x4C80, 0x8C41, 0x4400, 0x84C1, 0x8581,
                0x4540, 0x8701, 0x47C0, 0x4680, 0x8641, 0x8201, 0x42C0, 0x4380, 0x8341,
                0x4100, 0x81C1, 0x8081, 0x4040
        };

        for (; len > 0; len--) {
            int al = (addr[index] ^ (dx & 0xFF)) & 0xFF;
            dx = ((dx >> 8) & 0xFF) ^ crctable[al];
            index++;;
        }
        return dx & 0xFFFF; // Ensure the result is a 16-bit unsigned value
    }

    static int bit_search_sentinel(Bit_Chain dat, char[] sentinal)
    {
        int i, j;

        if(dat.size < 16)
        {
            return 0;
        }
        for(i = 0; i <= (int)(dat.size - 16); i++)
        {
            for(j = 0; j < 16; j++)
            {
                if(dat.chain[i+j] != sentinal[j])
                {
                    break;
                }
            }
            if(j == 16)
            {
                dat._byte = i + j;
                dat.bit =0;
                return -1;
            }
        }
        int rs = 99;
        return 0;
    }


    public static void bit_set_position(Bit_Chain dat, long bitpos) {
        dat._byte = (int)bitpos >> 3;
        dat.bit = (char)(bitpos & 7);
        if(dat._byte > dat.size || dat._byte == dat.size && dat.bit != 0)
        {
            loglevel = dat.opts & dwg.DWG_OPTS_LOGLEVEL;
        }
    }

    /** Read 1 bitlonglong (compacted uint64_t) for REQUIREDVERSIONS, preview_size.
     *  ODA doc bug. ODA say 1-3 bits until the first 0 bit. See 3BLL.
     *  The first 3 bits indicate the length len (see paragraph 2.1). Then
     *  len bytes follow, which represent the number (the least significant
     *  byte is first).
     */
    static long bit_read_BLL(Bit_Chain dat) {
        int i = 0, len = 0;
        long result = 0L;
        len = bit_read_BB(dat) << 1 | bit_read_B(dat);
        switch (len)
        {
            case 1:
                return bit_read_RC(dat);
            case 2:
                return bit_read_RS(dat);
            case 4:
                return bit_read_RL(dat);
            default:
                if (CHK_OVERFLOW(dat, 0)) {
                    return 1;
                }
                for(i = 0; i < 8; i++)
                {
                    result <<= 8;
                    if(i < len)
                    {
                        result |= bit_read_RC(dat);
                    }
                }
                return commen.be64toh(result);
        }
    }

    /**
     * Reads a single bit from the bit chain and advances the position by 1.
     * Treats bytes as unsigned and checks for overflow.
     *
     * @param dat The `Bit_Chain` object.
     * @return The bit value (0 or 1), or 1 if overflow occurs.
     */
    static int bit_read_B(Bit_Chain dat) {
        int result = 0;
        int _byte = 0;
        if (CHK_OVERFLOW(dat, 0)) {
            return 1;
        }
        _byte = dat.chain[(int)dat._byte] & 0xFF;
        result = (_byte & (0x80 >> dat.bit)) >> (7 - dat.bit);
        bit_advance_position(dat,1);
        return result & 0xFF;
    }

    /**
     * Reads 2 bits from the bit chain and advances the position by 2.
     * Handles cases where bits span across two bytes and checks for overflow.
     *
     * @param dat The `Bit_Chain` object.
     * @return The 2-bit value (0 to 3), or 1 if overflow occurs.
     */
    static int bit_read_BB(Bit_Chain dat) {
        int result = 0;
        int _byte;

        if (CHK_OVERFLOW(dat, 0)) {
            return 1;
        }
        _byte = dat.chain[(int)dat._byte] & 0xFF;
        if (dat.bit < 7) {
            result = (_byte & (0xC0 >> dat.bit)) >> (6 - dat.bit);
        } else {
            result = (_byte & 0x01) << 1;
            if (dat._byte < dat.size - 1) {
                _byte = dat.chain[(int)dat._byte + 1] & 0xFF;
                result |= (_byte & 0x80) >> 7;
            }
        }
        bit_advance_position(dat, 2);
        return result & 0xFF;
    }

    /** Read 1 bitdouble (compacted data).
     */
    static double bit_read_BD(Bit_Chain dat) {
        int two_bit_code = bit_read_BB(dat);
        if(two_bit_code == 0)
        {
            if(CHK_OVERFLOW(dat,(int)bit_nan()))
            {
                return 1;
            }
            return  bit_read_RD(dat);
        }
        else if(two_bit_code == 1){
            return 1.0;
        }
        else if(two_bit_code == 2)
        {
            return 0.0;
        }
        else{
            loglevel = dat.opts & dwg.DWG_OPTS_LOGLEVEL;
            // LOG_ERROR ("bit_read_BD: unexpected 2-bit code: '11'")
            return bit_nan();
        }
    }

    static double bit_read_RD(Bit_Chain dat) {
        long res = 0;
        double result = 0;

        res = bits.bit_read_RLL(dat);
        result = Double.longBitsToDouble(res);
        return result;
    }

    /** Read 1 raw 64bit long (8 byte, BE).*/
    static long bit_read_RLL(Bit_Chain dat) {
//        if(dat.bit == 0 && (dat._byte % 8) != 0)
//        {
//            long v = commen.le64toh(dat.chain[(int)dat._byte]);
//            dat._byte+= 8;
//            return v;
//        }else{
            long word1, word2;
            word1 = bit_read_RL(dat);
            if(CHK_OVERFLOW(dat,0))
            {
                return 1;
            }
            word2 = bit_read_RL(dat);

        return ((word2 << 32) | (word1 & 0xFFFFFFFFL));
       // }
    }

    static BigInteger bit_read_RLL1(Bit_Chain dat) {
        // Read least and most significant words as unsigned 32-bit
        long word1 = bit_read_RL(dat) & 0xFFFFFFFFL; // Least significant word
        long word2 = bit_read_RL(dat) & 0xFFFFFFFFL; // Most significant word

        // Combine as unsigned 64-bit using BigInteger
        BigInteger high = BigInteger.valueOf(word2).shiftLeft(32); // Shift high bits
        BigInteger low = BigInteger.valueOf(word1); // Low bits
        return high.or(low); // Combine
    }

    /** create a Not-A-Number (NaN) without libm dependency */
    static double bit_nan() {
        if(specs.IS_RELEASE)
        {
            return 0.0;
        }
        else{
            long result = 0xFFFFFFFFFFFFFFFFL;
//            int[] res = new int[2];
//            res[0] = -1;
//            res[1] = -1;
            return Double.longBitsToDouble(result);
        }
    }

    static boolean bit_isnan(double number)
    {
        long bits = Double.doubleToLongBits(number);

        int lower = (int) (bits & 0xFFFFFFFF);
        int upper = (int) ((bits >> 32) & 0xFFFFFFFF);

        return (lower == -1 && upper == -1);
    }

    static String bit_TV_to_utf8(String src, int codepage) {
        if(codepage == Dwg_Codepages.CP_UTF8.value)
        {
            return bit_u_expand(src);
        }
        else if(src.isEmpty())
        {
            return "";
        }
        else {
            return bit_TV_to_utf8_codepage(src,codepage);
        }
    }

//    static String bit_u_expand(char[] src) {
//        char[] ret = src;
//        char[] p = src;
//        char[] s;
//
//        while ((s = bit_is_U_expand(p)) != null || (s = bit_is_M_expand(p)) != null)
//        {
//            int wc,i;
//            long lp = s.length;
//
//            if(s[1] == 'U' && Arrays.toString(s).matches("\\\\U\\+[0-9A-Fa-f]{4}"))
//            {
//                wc = Integer.parseInt(Arrays.toString(s).substring(3, 7), 16);
//                char[] wp = { (char) wc, '\0' };
//                char[] u8 = bit_convert_TU(wp);
//                long l = u8.length;
//                String _str = commonvar.memcpy(Arrays.toString(s), Arrays.toString(s).indexOf("\\U+"), Arrays.toString(u8), (int)(l + 1));
//                s = _str.toCharArray();
//                if(lp > 7)
//                {
//                    s = commonvar.memmove(s,1,7, (int)(lp-6));
//                }
//            }
//            else if( Arrays.toString(s).matches("\\\\M\\+\\d{1}\\p{XDigit}{4}"))
//            {
//                i = Integer.parseInt(String.valueOf(s[1]).substring(0, 1));
//                wc = Integer.parseInt(String.valueOf(s[1]).substring(1), 16);
//                int uc;
//
//                Dwg_Codepages mif_tbl[]
//                    = { Dwg_Codepages.CP_UNDEFINED, Dwg_Codepages.CP_ANSI_932,  Dwg_Codepages.CP_ANSI_950,
//                    Dwg_Codepages.CP_ANSI_949,  Dwg_Codepages.CP_ANSI_1361, Dwg_Codepages.CP_ANSI_936 };
//                wc = Integer.parseInt(String.valueOf(s[4]).substring(4, 8), 16);
//            }
//        }
//    }

    public static String bit_u_expand(String src) {
        StringBuilder ret = new StringBuilder(src);
        StringBuilder p = new StringBuilder(src);
        String s;

        while ((s = bit_is_U_expand(p.toString())) != null || (s = bit_is_M_expand(p.toString())) != null) {
            if (s.charAt(1) == 'U') {
                if (s.length() >= 7 && s.substring(0, 7).matches("\\\\U\\+[0-9A-Fa-f]{4}")) {
                    int wc = Integer.parseInt(s.substring(3, 7), 16);
                    char[] wp = { (char) wc, '\0' };
                    String u8 = bit_convert_TU(wp);
                    int l = u8.length();
                    int start = p.indexOf("\\U+");
                    ret.replace(start, start + 7, u8);
                    p.replace(start, start + 7, u8);
                    if (s.length() > 7) {
                        String remaining = s.substring(7);
                        ret.replace(start + l, start + l + remaining.length(), remaining);
                        p.replace(start + l, start + l + remaining.length(), remaining);
                    }
                }
            } else if (s.charAt(1) == 'M') {
                if (s.length() >= 8 && s.substring(0, 8).matches("\\\\M\\+\\d{1}[0-9A-Fa-f]{4}")) {
                    int i = Integer.parseInt(s.substring(3, 4));
                    int wc = Integer.parseInt(s.substring(4, 8), 16);

                    if (i < 1 || i > 5) {
                        throw new IllegalArgumentException("Invalid \\M+ sequence index: " + i);
                    }

                    Dwg_Codepages[] mif_tbl = {
                            Dwg_Codepages.CP_UNDEFINED, Dwg_Codepages.CP_ANSI_932,
                            Dwg_Codepages.CP_ANSI_950, Dwg_Codepages.CP_ANSI_949,
                            Dwg_Codepages.CP_ANSI_1361, Dwg_Codepages.CP_ANSI_936
                    };

                    int uc = codepages.dwg_codepage_uwc(mif_tbl[i], wc);
                    int start = p.indexOf("\\M+");

                    if (uc < 0x80) {
                        ret.replace(start, start + 7, String.valueOf((char) uc));
                        p.replace(start, start + 7, String.valueOf((char) uc));
                    } else if (uc < 0x800) {
                        String replacement = String.valueOf((char) ((uc >> 6) | 0xC0)) +
                                String.valueOf((char) ((uc & 0x3F) | 0x80));
                        ret.replace(start, start + 7, replacement);
                        p.replace(start, start + 7, replacement);
                    } else {
                        String replacement = String.valueOf((char) ((uc >> 12) | 0xE0)) +
                                String.valueOf((char) (((uc >> 6) & 0x3F) | 0x80)) +
                                String.valueOf((char) ((uc & 0x3F) | 0x80));
                        ret.replace(start, start + 7, replacement);
                        p.replace(start, start + 7, replacement);
                    }
                }
            }
        }

        return ret.toString();
    }

    public static String bit_convert_TU(char[] wstr) {
        char[] tmp = wstr;
        StringBuilder str = new StringBuilder();
        int i = 0;
        int len = 0;
        int c = 0;

        if (wstr == null || wstr.length == 0) {
            return null;
        }

        // First pass to calculate the destination length
        for (int j = 0; j < wstr.length; j++) {
            c = tmp[j];
            if (c == 0) {
                break;
            }
            len++;
            if (c >= 0x80) {
                len++;
                if (c >= 0x800) {
                    len++;
                }
            }
        }

        // Convert to UTF-8
        for (int j = 0; j < wstr.length; j++) {
            c = tmp[j];
            if (c == 0) {
                break;
            }

            if (c < 0x80) {
                str.append((char) c);
            } else if (c < 0x800) {
                str.append((char) ((c >> 6) | 0xC0));
                str.append((char) ((c & 0x3F) | 0x80));
            } else {
                str.append((char) ((c >> 12) | 0xE0));
                str.append((char) (((c >> 6) & 0x3F) | 0x80));
                str.append((char) ((c & 0x3F) | 0x80));
            }
        }

        return str.toString();
    }


    private static String bit_is_U_expand(String p) {
        String s;
        if (p != null && p.length() >= 7 && (s = p.contains("\\U+") ? p.substring(p.indexOf("\\U+")) : null) != null
                && ishex(s.charAt(3)) && ishex(s.charAt(4)) && ishex(s.charAt(5)) && ishex(s.charAt(6))) {
            return s;
        } else {
            return null;
        }
    }

    private static String bit_is_M_expand(String p) {
        String s;
        if (p != null && p.length() >= 8 && (s = p.contains("\\M+") ? p.substring(p.indexOf("\\M+")) : null) != null
                && s.charAt(3) >= '1' && s.charAt(3) <= '5'
                && ishex(s.charAt(4)) && ishex(s.charAt(5)) && ishex(s.charAt(6)) && ishex(s.charAt(7))) {
            return s;
        } else {
            return null;
        }
    }

    static boolean ishex(int c)
    {
        return ((c >= '0' && c<= '9') || (c >= 'a' && c <= 'f')
            || (c >= 'A' && c <= 'F'));
    }

    static String bit_TV_to_utf8_codepage(String src, int codepage) {
        boolean is_asian_cp = codepages.dwg_codepage_isasian(Dwg_Codepages.fromValue(codepage));
        long srclen = src.length();
        long destlen = is_asian_cp ? srclen * 3 : (long) Math.floor(srclen * 1.5);
        int i = 0;
        char[] str = new char[(int)destlen + 1];
        char[] temp = src.toCharArray();

        int c = 0;

        if(srclen == 0)
        {
            return "";
        }
        if(codepage == 0)
        {
            return src;
        }
        //UTF8 encode
        int index = 0;
        while (i < destlen && index < srclen)
        {
            char wc;
            c = temp[index++];
            if(is_asian_cp)
            {
                if (codepages.dwg_codepage_is_twobyte(Dwg_Codepages.fromValue(codepage), c))
                {
                    if (index < srclen) {
                        c = (c << 8) | temp[index++];
                    }
                    wc = codepages.dwg_codepage_uwc(Dwg_Codepages.fromValue(codepage), c);
                    c = wc;
                    if(c < 0x80)
                    {
                        str[i++]  = (char) (c & 0xff);
                    }
                }
            }
            else if(c < 0x80)
            {
                str[i++]  = (char) (c & 0xff);
            }
            else if((wc = codepages.dwg_codepage_uc(Dwg_Codepages.fromValue(codepage), c & 0xFF)) > 0){
                c = wc;
                if(c < 0x80)
                    str[i++] = (char) (c & 0xff);
            }
            if(c >= 0x80 && c < 0x800)
            {
                str = EXTEND_SIZE(str,i+1,destlen);
                str[i++] = (char)((c>> 6) | 0xC0);
                str[i++] = (char)((c & 0x3F) | 0x80);
            }
            else if(c >= 0x800)
            {
                str = EXTEND_SIZE(str,i+2,destlen);
                str[i++] = (char) ((c >> 12) | 0xE0);
                str[i++] = (char) (((c >> 6) & 0x3F) | 0x80);
                str[i++] = (char) ((c & 0x3F) | 0x80);
            }
        }
        str = EXTEND_SIZE(str,i+1,destlen);
        str[i] = '\0';
        return bit_u_expand(new String(str));
    }

    private static char[] EXTEND_SIZE(char[] str, int i, long len) {
        if (i >= len) {
            len *= 2;
            char[] newStr = new char[(int) len + 1];
            System.arraycopy(str, 0, newStr, 0, str.length);
            return newStr;
        }
        return str;
    }

    static String bit_read_TV(Bit_Chain dat) {
        int i = 0;
        int length = 0;
        char[] chain = null;

        if(dat.from_version.ordinal() < DWG_VERSION_TYPE.R_13b1.ordinal())
        {
            length = bit_read_RS(dat);
        }else{
            length = bit_read_BS(dat);
        }

        if(loglevel == 0)
            loglevel = dat.opts & dwg.DWG_OPTS_LOGLEVEL;
        chain = new char[length + 1];
        if(chain == null)
        {
            return null;
        }
        for(i = 0; i < length; i++)
        {
            chain[i] = bit_read_RC(dat);
        }
        /*  if (DWG_LOGLEVEL >= DWG_LOGLEVEL_HANDLE)
    {
      // check if the string is already zero-terminated or not.
      // only observed >=r2004 as writer app
      if (length > 0 && dat->from_version > R_2000
          && chain[length - 1] != '\0')
        LOG_HANDLE ("TV-not-ZERO %u\n ", length)
      // and preR2000 the final \0 is not included in the length (ie == strlen)
      else if (length > 0 && dat->from_version < R_2000
               && chain[length - 1] == '\0')
        LOG_HANDLE ("TV-ZERO %u\n", length)
    }*/
        chain[i] = '\0';
        return new String(chain);
    }

    static int bit_read_BS(Bit_Chain dat) {
        int two_bit_code = bit_read_BB(dat);
        if(two_bit_code == 0)
        {
            return bit_read_RS(dat);
        }
        else if(two_bit_code == 1)
        {
            return (int)bit_read_RC(dat);
        }
        else if(two_bit_code == 2)
        {
            return 0;
        }
        else{
            return 256;
        }
    }

    static long bit_read_BL(Bit_Chain dat) {
        int two_bit_code = bit_read_BB(dat);
        if(two_bit_code == 0)
        {
            return bit_read_RL(dat) & IntMask.UINT32_MASK;
        }
        else if(two_bit_code == 1)
        {
            return (int)bit_read_RC(dat) & IntMask.UINT32_MASK;
        }
        else if(two_bit_code == 2)
        {
            return 0;
        }
        else{
            return 256;
        }
    }

    static int bit_read_H(Bit_Chain dat, Dwg_Handle handle) {
        class Union {
            byte[] c = new byte[8];
            long v = 0;
        }

        Union u = new Union();
        long pos = dat._byte;
        handle.code = bit_read_RC(dat);

        if (pos == dat._byte) {
            return DWG_ERROR.DWG_ERR_INVALIDHANDLE.ordinal();
        }

        handle.is_global = '\0';
        handle.value = 0;

        if (dat.from_version.ordinal() < DWG_VERSION_TYPE.R_13b1.ordinal()) {
            handle.size = handle.code;
            if (handle.size > Byte.BYTES) {
                int logLevel = dat.opts & dwg.DWG_OPTS_LOGLEVEL;
                System.err.printf(
                        "Invalid handle-reference, longer than 8 bytes: %s%n", handle.toString()
                );
                return DWG_ERROR.DWG_ERR_INVALIDHANDLE.ordinal();
            }
            handle.code = 0;
        } else {
            handle.size = (char)(handle.code & 0x0F);
            handle.code = (char)((handle.code & 0xf0) >> 4);
        }

        // Ensure size does not exceed 8
        if (handle.size > Byte.BYTES || handle.code > 14) {
            int logLevel = dat.opts & dwg.DWG_OPTS_LOGLEVEL;
            System.err.printf(
                    "Invalid handle-reference, longer than 8 bytes: %s%n", handle.toString()
            );
            return DWG_ERROR.DWG_ERR_INVALIDHANDLE.ordinal();
        }

        u.v = 0L;
        for (int i = 0; i < handle.size; i++) {
            u.v = (u.v << 8) | bit_read_RC(dat);
        }

        //htole64 pending
        handle.value = u.v; // Replaces `htole64` for endianness handling
        return 0;
    }

    public static Dwg_Color bit_read_CMC(Bit_Chain dat, Bit_Chain str_dat) {
        Dwg_Color color = new Dwg_Color();
        if(dat.from_version.ordinal() < DWG_VERSION_TYPE.R_13b1.ordinal())
            color.index = (short)bit_read_RS(dat);
        else
            color.index = (short)bit_read_BS(dat);
        if(dat.from_version.ordinal() >= DWG_VERSION_TYPE.R_2004.ordinal())
        {
            color.rgb = bit_read_BL(dat);
            color.method = (int)(color.rgb >> 0x18);
            color.flag = bit_read_RC(dat);
            if (color.flag < 4)
            {
                color.name = (color.flag & 1) != 0 ? bit_read_T(str_dat) : null;
                color.book_name = (color.flag & 2) != 0 ? bit_read_T(str_dat) : null;
            }
            else{
                loglevel = dat.opts & dwg.DWG_OPTS_LOGLEVEL;
                color.flag = 0;
            }
            if(color.method < 0xc0 || color.method > 0xc8)
            {
                loglevel = dat.opts & dwg.DWG_OPTS_LOGLEVEL;
                color.method = 0xc2;
                color.rgb = 0xc2000000L | (color.rgb & 0xffffff);
            }

            color.index = dwg.dwg_find_color_index(color.rgb);
        }
        return color;
    }

    static String bit_read_T(Bit_Chain dat) {
        if(IS_FROM_TU(dat))
        {
            return bit_read_TU(dat);
        }else{
            return bit_read_TV(dat);
        }
    }

    /** Read UCS-2 unicode text. no supplementary planes
     *  See also bfr_read_string()
     */
    static String bit_read_TU(Bit_Chain dat) {
        int i;
        int length;
        char[] ws;
        length = bit_read_BS(dat);
        ws = new char[length + 1];

        for (i = 0; i < length; i++) {
            ws[i] = (char)(int)bit_read_RS(dat);
        }
        ws[length] = 0;
        return new String(ws, 0, length);
    }


    static boolean IS_FROM_TU(Bit_Chain dat) {
        return (dat.from_version.ordinal() >= DWG_VERSION_TYPE.R_2007.ordinal())
                && (dat.opts & dwg.DWG_OPTS_IN) == 0;
    }

    static int bit_check_CRC(Bit_Chain dat, long start_address, int seed) {
        loglevel = dat.opts & (char) dwg.DWG_OPTS_LOGLEVEL;
        int calculated;
        int read;
        long size;

        if(dat.bit > 0)
        {
            dat._byte++;
            dat.bit = 0;
        }
        if(start_address > dat._byte || dat._byte >= dat.size)
        {
            loglevel = dat.opts & (char) dwg.DWG_OPTS_LOGLEVEL;
            return 0;
        }
        assert dat._byte >= start_address;
        size = dat._byte - start_address;
        calculated = bits.bit_calc_CRC(seed,(int)start_address,dat.chain,size);
        read = bits.bit_read_RS(dat);
        if(calculated == read)
        {
            return 1;
        }
        else{
            return 0;
        }
    }

    /** read ASCII string, with length as RS */
    static String bit_read_T16(Bit_Chain dat) {
        int length = bit_read_RS(dat);
        char[] chain = new char[length+1];
        if(chain == null)
        {
            loglevel = dat.opts & dwg.DWG_OPTS_LOGLEVEL;
            return null;
        }
        for(int i = 0; i < length; i++)
        {
            chain[i] = bit_read_RC(dat);
        }
        chain[length] = 0;
        return new String(chain).trim();
    }
    /** read UCS-2 string, with length as RS */
    static String bit_read_TU16(Bit_Chain dat) {
        return "";
    }

    /** Read 1 raw short (BE).
     */
    static int bit_read_RS_BE(Bit_Chain dat) {
        char byte1, byte2;
        byte1 = bit_read_RC(dat);
        if(CHK_OVERFLOW(dat,0))
        {
            return -1;
        }
        byte2 = bit_read_RC(dat);
        return ((byte1 & 0xFF) << 8) | (byte2 & 0xFF);
    }

    /** Read unsigned modular char (max 5 bytes, unsigned).
     Can be quite large if there are many deleted handles.
     */
    static long bit_read_UMC(Bit_Chain dat) {
        int i  = 0, j = 0;
        int MAX_BYTE_UMC = 8;
        char[] _byte = new char[MAX_BYTE_UMC];
        long result = 0;

        for(i = MAX_BYTE_UMC - 1; i >= 0; i--, j+= 7)
        {
            _byte[i] = bits.bit_read_RC(dat);
            if((_byte[i] & 0x80) == 0)
            {
                result |= (((long)_byte[i]) << j);
                return result;
            }
            else {
                _byte[i] &= 0x7f;
            }
            result |= ((long)_byte[i]) << j;
        }
        loglevel = dat.opts & dwg.DWG_OPTS_LOGLEVEL;
        return 0;
    }

    static int bit_read_MC(Bit_Chain dat) {
        int i = 0, j = 0;
        int negative = 0;
        char[] _byte = new char[8];
        long result = 0;
        for(i = 4, j = 0; i >= 0; i--,j +=7 )
        {
            _byte[i] = bits.bit_read_RC(dat);
            if((_byte[i] & 0x80) == 0)
            {
                if((_byte[i] & 0x40) != 0)
                {
                    negative = 1;
                    _byte[i] &= 0xbf;
                }
                result |= (((long)_byte[i]) << j);

                return (negative != 0 ? -((int)result) : (int) result);
            }
            else{
                _byte[i] &= 0x7f;
            }
            result |= ((long)_byte[i]) << j;
        }
        loglevel = dat.opts & dwg.DWG_OPTS_LOGLEVEL;
        return 0;
    }

    static int bit_read_MS(Bit_Chain dat) {
        int i = 0, j = 0;
        int[] word = new int[2];
        int result = 0;

        for(i = 1, j = 0; i >= 0; i--, j += 15)
        {
            word[i] = bits.bit_read_RS(dat);
            if((word[i] & 0x8000) == 0)
            {
                result |= ((int)word[i] << j);
                return result;
            }
            else{
                word[i] &= 0x7fff;
            }
            result |= ((int)word[i] << j);
        }
        loglevel = dat.opts & dwg.DWG_OPTS_LOGLEVEL;
        return 0;
    }

    public static void bit_reset_chain(Bit_Chain dat) {
        long pos = dat._byte;
        dat._byte = 0;
        if (pos < dat.size) {
            char[] newChain = new char[dat.chain.length - (int) pos];
            System.arraycopy(dat.chain, (int) pos, newChain, 0, newChain.length);
            dat.chain = newChain;
        }
        if (dat.size > 0) {
            dat.size -= pos;
        }
    }

    static void bit_chain_init(Bit_Chain dat, int size) {
        dat.chain = new char[size];
        if (dat.chain.length == 0)
            return; //abort();
        dat.size = size;
        dat._byte = 0;
        dat.bit = '\0';
    }

    static String bit_read_TF(Bit_Chain dat, int size)
    {
        char[] chain = new char[size + 1];
        return bit_read_fixed(dat, size);
    }

    static String bit_read_fixed(Bit_Chain dat, long len) {
        String dest = "";
        if(dat._byte >= dec_macros.MAX_MEM_ALLOC || len >= dec_macros.MAX_MEM_ALLOC ||
                (dat.bit != 0 ? (((dat._byte + len) * 8) +
                        dat.bit > dat.size * 8) : (dat._byte + len > dat.size)))
        {
            loglevel = dat.opts & dwg.DWG_OPTS_LOGLEVEL;
            if (len < dat.size - dat._byte) {
                char[] tempDest = new char[(int) len];
                for (int i = 0; i < len; i++) {
                    tempDest[i] = 0;
                }
                dest = new String(tempDest);
            }
        }
        if(dat.bit == 0)
        {
            assert dat._byte + len <= dat.size;
            System.arraycopy(dat.chain, (int) dat._byte, dest, 0, (int) len);
            dat._byte += len;
        }
        else{
            char[] temp = new char[(int)len];
            for(long i = 0; i < len; i++)
            {
                temp[(int)i] = bits.bit_read_RC(dat);
            }
            dest = new String(temp);
        }
        return dest;
    }

    /** Read fixed text with zero-termination.
     *  After usage, the allocated memory must be properly freed.
     *  preR11
     */
    static String bit_read_bits(Bit_Chain dat, long bits) {
        String strBits = "";
        int bytes = (int)(bits / 8) & UINT_MAX;
        int rest = (int)(bits  % 8);
        strBits = bit_read_fixed(dat,bytes);
        if(strBits.isEmpty())
        {
            return "";
        }

        if(rest != 0)
        {
            dat.size++;
            char[] arr = new char[bytes + (rest > 0 ? 1 : 0)];

            for(int i = 0; i < rest; i++)
            {
                char last = (char)bit_read_B(dat);
                arr[bytes] |= last << i;
            }
            dat.size--;

            String arrs = new String(arr);
            strBits.concat(arrs).trim();
        }

        return strBits;
    }
}
class Bit_Chain {
    public char[] chain;
    public long size;
    public long _byte;
    public char bit;
    public char opts;
    public RandomAccessFile fh;
    public DWG_VERSION_TYPE version;
    public DWG_VERSION_TYPE from_version;
    public int codepages;

    Bit_Chain(Bit_Chain dat) {
        if (dat.chain != null) {
            this.chain = new char[dat.chain.length];
            System.arraycopy(dat.chain, 0, this.chain, 0, dat.chain.length);
        } else {
            this.chain = null;
        }

        this.size = dat.size;
        this._byte = dat._byte;
        this.bit = dat.bit;
        this.opts = dat.opts;
        this.fh = dat.fh;
        this.version = dat.version;
        this.from_version = dat.from_version;
        this.codepages = dat.codepages;
    }

    Bit_Chain ()
    {

    }
}