import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class out_json {
    public static int ISFIRST = 0;
    public static int dwg_write_json(Bit_Chain dat, Dwg_Data objDwgData) throws IOException {
        Dwg_Header obj = objDwgData.header;
        int error = 0;

        if(dat.version == null)
        {
            dat.version = dat.from_version;
        }
        if(dat.codepages == 0)
        {
            dat.codepages = objDwgData.header.codepage;
        }
        if(dat.fh == null)
        {
            return 1;
        }

        dat.bit++;

        config.streamWriter.write("{");
        config.streamWriter.write("\n  \"created_by\": \"" + commonvar.CreatedBy + "\",\n");

        json_fileheader_write(dat,objDwgData);

        config.streamWriter.write("\n}");
        config.streamWriter.write("\n");
        return 0;
    }

    static int json_fileheader_write(Bit_Chain dat, Dwg_Data objDwgData) throws IOException {
        RECORD(dat,"FILEHEADER");
        ISFIRST = 0;

        KEY(dat,"version");
        config.streamWriter.write("\"" + commen.dwg_versions[objDwgData.header.version.ordinal()].hdr + "\"");

        header_spec.header_spec_write(dat,dat,objDwgData);

        ENDRECORD(dat);
        return 0;
    }

    static void KEY(Bit_Chain dat, String name) throws IOException {
        if(ISFIRST != 0)
        {
            PRINTFIRST(dat);
        }
        ISFIRST = 1;
        _prefix(dat);

        config.streamWriter.write("\"" + _path_field(name) + "\": ");
    }

    static void ENDRECORD(Bit_Chain dat) throws IOException {
        ENDHASH(dat);
    }

    static void ENDHASH(Bit_Chain dat) throws IOException{
        config.streamWriter.write("\n");
        dat.bit--;

        _prefix(dat);
        config.streamWriter.write("}");
    }

    static void RECORD(Bit_Chain dat,String name) throws IOException {
        PRINTFIRST(dat);
        _prefix(dat);
        config.streamWriter.write("\""+_path_field(name)+"\": ");
        HASH(dat);
    }

    static void HASH(Bit_Chain dat) throws IOException {
        config.streamWriter.write("{");
        config.streamWriter.write("\n");
        dat.opts |= (char)0x20;
        ISFIRST = 0;
        dat.bit++;
    }

    public static String _path_field(String path) {
        int idx = path.lastIndexOf(']');
        String s = "";
        if (idx >= 0) {
            s = path.substring(0, idx);
        }
        if (s.length() > 0 && s.charAt(1) == '.') {
            return Character.toString(s.charAt(2));
        }
        return path;
    }

    static void _prefix(Bit_Chain dat) throws IOException  {
        for(int i = 0; i < dat.bit; i++)
        {
            config.streamWriter.write("  ");
        }
    }

    static void PRINTFIRST(Bit_Chain dat) throws IOException {
        if(ISFIRST != 0)
        {
            config.streamWriter.write(String.format(",\n"));
        }else{
            ISFIRST = 1;
        }
    }

    static void FIELD_RC(String nam, char val, Bit_Chain dat, int dxf) throws IOException {
        FIELD(nam,val,dat,dxf);
    }

    public static void FIELD(String nam, Object val, Bit_Chain dat, int dxf) throws IOException {
        // Regular expression for an integer number
        String regex = "^\\d+"; // Matches positive integers
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(val.toString());

        Pattern negativePattern = Pattern.compile("^-\\d+"); // Matches negative integers
        Matcher negativeMatch = negativePattern.matcher(val.toString());

        if (!commen.memBEGINc(nam, "num_")) {
            FIRSTPREFIX(dat);
            config.streamWriter.write("\"" + _path_field(nam) + "\": ");

            // Check if `val` is a character
            if (val instanceof Character) {
                char tempChar = (Character) val;
                int tempInt;

                if (Character.isDigit(tempChar)) {
                    tempInt = Character.getNumericValue(tempChar);
                    config.streamWriter.write(_path_field(String.valueOf(tempInt)));
                } else {
                    config.streamWriter.write(_path_field(String.valueOf((byte) tempChar)));
                }
            }
            // Check if `val` is a positive integer
            else if (match.find()) {
                config.streamWriter.write(_path_field(val.toString()));
            }
            // Check if `val` is a negative integer
            else if (negativeMatch.find()) {
                config.streamWriter.write(_path_field(val.toString()));
            }
            // If `val` is a string
            else {
                config.streamWriter.write("\"" + _path_field(val.toString()) + "\"");
            }
        }
    }

    private static void FIRSTPREFIX(Bit_Chain dat) throws IOException {
        if(ISFIRST != 0)
        {
            PRINTFIRST(dat);
        }
        ISFIRST = 1;
        _prefix(dat);
    }

    public static void FIELD_RL(String nam, long val, Bit_Chain dat, int dxf) throws IOException {
        FIELD(nam,val,dat,dxf);
    }
}
