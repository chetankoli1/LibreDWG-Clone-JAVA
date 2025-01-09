import java.nio.charset.StandardCharsets;

enum ENUM_ORDERING {
    REQUIRE_ORDER,
    PERMUTE,
    RETURN_IN_ORDER
}
class _getopt_data_w {
    public int optind;
    public int opterr;
    public int optopt;
    public byte[] optarg;
    public int __initialized;
    public byte[] __nextchar;
    public ENUM_ORDERING __ordering;
    public int __posixly_correct;
    public int __first_nonopt;
    public int __last_nonopt;
    public _getopt_data_w(){}
}
class option_w {
    public byte[] name;
    public int has_arg;
    public int flag;
    public int val;

    public option_w(String name,int has_arg,int flag,int val)
    {
        this.name = name.getBytes(StandardCharsets.US_ASCII);
        this.has_arg = has_arg;
        this.flag = flag;
        this.val = val;
    }
    public option_w(){}
}
public class getopt
{
    public static String optarg_a;
    int optind = 1;
    int opterr = 1;
    int optopt = '?';
}
