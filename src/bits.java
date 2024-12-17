import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.util.RandomAccess;
import java.util.stream.Stream;
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
            result = (char) (_byte << dat.bit);
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

    private static void bit_advance_position(Bit_Chain dat, int advance) {
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

    static int bit_read_RL(Bit_Chain dat) {
        int word1, word2;
        word1 = bit_read_RS(dat);
        if(CHK_OVERFLOW(dat,0))
        {
            return 0;
        }
        word2 = bit_read_RS(dat);
        return ((word2 << 16) | (int)(word1));
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

        for (int i = 0; i < len; i++) {
            int al = (addr[i] ^ (dx & 0xFF)) & 0xFF;
            dx = ((dx >> 8) & 0xFF) ^ crctable[al];
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
        return 0;
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
        this.chain = dat.chain;
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