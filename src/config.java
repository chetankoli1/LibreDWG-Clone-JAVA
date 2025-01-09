import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStreamWriter;

public class config {
    public static String outFilePath = "";
    public static BufferedWriter streamWriter = null;
    /** Define to 1 if you have the <getopt.h> header file. */
    public static boolean HAVE_GETOPT_H = true;

    /** Define to 1 if you have the `getopt_long' function. */
    public static boolean HAVE_GETOPT_LONG = true;
}
