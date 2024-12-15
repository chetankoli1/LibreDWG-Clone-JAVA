import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.util.RandomAccess;
import java.util.stream.Stream;
enum DWG_VERSION_TYPE{
    R_INVALID, R_2000, R_2004, R_AFTER
}
public class bits {
    public static char bit_read_RC(Bit_Chain dat) {
        byte result = 0;
        byte _byte;

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
            result = (byte) (_byte << dat.bit);
            if(dat._byte < dat.size - 1)
            {
                _byte = dat.chain[(int)dat._byte + 1];
                result |= (byte) (_byte >> (8-dat.bit));
            }else{
                return (char)result;
            }
            bit_advance_position(dat,8);
        }
        return (char)result;
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
            return true;
        }

        return false;
    }
}
class Bit_Chain {
    public byte[] chain;
    public long size;
    public long _byte;
    public char bit;
    public char opts;
    public RandomAccessFile fh;
    public DWG_VERSION_TYPE version;
    public DWG_VERSION_TYPE from_version;
    public int codepages;
}