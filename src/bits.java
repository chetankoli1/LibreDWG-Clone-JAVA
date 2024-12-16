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

    private static long bit_position(Bit_Chain dat) {
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