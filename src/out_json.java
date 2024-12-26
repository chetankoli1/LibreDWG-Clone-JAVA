import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class out_json {
    public static int ISFIRST = 0;

    public static int dwg_write_json(Bit_Chain dat, Dwg_Data objDwgData) throws IOException {
        Dwg_Header obj = objDwgData.header;
        int error = 0;

        if (dat.version == null) {
            dat.version = dat.from_version;
        }
        if (dat.codepages == 0) {
            dat.codepages = objDwgData.header.codepage;
        }
        if (dat.fh == null) {
            return 1;
        }

        dat.bit++;

        config.streamWriter.write("{");
        config.streamWriter.write("\n  \"created_by\": \"" + commonvar.CreatedBy + "\",\n");

        json_fileheader_write(dat, objDwgData);

        json_header_write(dat, objDwgData);
        //

        //
        if (dat.version.ordinal() >= DWG_VERSION_TYPE.R_13b1.ordinal()) {
            if (json_thumbnail_write(dat, objDwgData) >= DWG_ERROR.DWG_ERR_CRITICAL) {
                return 1;
            }
            if (dat.version.ordinal() <= DWG_VERSION_TYPE.R_2000.ordinal()) {
                if (objDwgData.header.sections >= 6) {
                    error |= json_section_auxheader(dat, objDwgData);
                }
            }
        }
        config.streamWriter.write("}");
        config.streamWriter.write("\n");
        return 0;
    }

    private static void json_header_write(Bit_Chain dat, Dwg_Data objDwgData) throws IOException {
        int error = 0;
        RECORD(dat, "HEADER");
        ISFIRST = 0;
        if (commen.PRE(DWG_VERSION_TYPE.R_13b1, dat)) {
            //error = json_preR13_header_write_private (dat, objDwgData);
        } else {
            error = json_header_write_private(dat, objDwgData);
        }
        ENDRECORD(dat);
    }

    private static int json_header_write_private(Bit_Chain dat, Dwg_Data objDwgData) throws IOException {
        int error = 0;
        Dwg_Object obj = null;

        error = header_variables_spec.header_variables_spec_write(dat, objDwgData);

        return error;
    }

    static int json_thumbnail_write(Bit_Chain dat, Dwg_Data objDwgData) throws IOException {
        Dwg_Chain thumbnail = objDwgData.thumbnail;
        Bit_Chain _obj = new Bit_Chain(dat);
        _obj.bit = thumbnail.bit;
        _obj.chain = thumbnail.chain;
        _obj.size = thumbnail.size;
        _obj._byte = thumbnail._byte;

        if (_obj.chain != null && _obj.size > 10) {
            if (objDwgData.header.from_version.ordinal() >= DWG_VERSION_TYPE.R_2004.ordinal()) {
                _obj.chain = Arrays.copyOfRange(_obj.chain, 16, _obj.chain.length);
            }
            KEY(dat, "THUMBNAILIMAGE");
            HASH(dat);
            KEY(dat, "size");
            config.streamWriter.write(_obj.size + "");

            FIELD_BINARY(dat, "chain", _obj, 310);
            if (objDwgData.header.from_version.ordinal() >= DWG_VERSION_TYPE.R_2004.ordinal()) {
                _obj.chain = Arrays.copyOfRange(_obj.chain, 16, _obj.chain.length);

            }
            ENDHASH(dat);
        }
        return 0;
    }

    private static int json_section_auxheader(Bit_Chain dat, Dwg_Data objDwgData) throws IOException {
        RECORD(dat, "AuxHeader");
        auxheader_spec.auxheader_spec_write(dat, objDwgData);
        ENDRECORD(dat);
        return 0;
    }

    static int json_fileheader_write(Bit_Chain dat, Dwg_Data objDwgData) throws IOException {
        RECORD(dat, "FILEHEADER");
        ISFIRST = 0;

        KEY(dat, "version");
        config.streamWriter.write("\"" + commen.dwg_versions[objDwgData.header.version.ordinal()].hdr + "\"");

        fileheader_spec.fileheader_spec_write(dat, dat, objDwgData);

        ENDRECORD(dat);
        return 0;
    }

    static void KEY(Bit_Chain dat, String name) throws IOException {
        if (ISFIRST != 0) {
            PRINTFIRST(dat);
        }
        ISFIRST = 1;
        _prefix(dat);

        config.streamWriter.write("\"" + _path_field(name) + "\": ");
    }

    static void ENDRECORD(Bit_Chain dat) throws IOException {
        ENDHASH(dat);
    }

    static void ENDHASH(Bit_Chain dat) throws IOException {
        config.streamWriter.write("\n");
        dat.bit--;

        _prefix(dat);
        config.streamWriter.write("}");
    }

    static void RECORD(Bit_Chain dat, String name) throws IOException {
        PRINTFIRST(dat);
        _prefix(dat);
        config.streamWriter.write("\"" + _path_field(name) + "\": ");
        HASH(dat);
    }

    static void HASH(Bit_Chain dat) throws IOException {
        config.streamWriter.write("{");
        config.streamWriter.write("\n");
        dat.opts |= (char) 0x20;
        ISFIRST = 0;
        dat.bit++;
    }

    static String _path_field(String path) {
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

    static void _prefix(Bit_Chain dat) throws IOException {
        for (int i = 0; i < dat.bit; i++) {
            config.streamWriter.write("  ");
        }
    }

    static void PRINTFIRST(Bit_Chain dat) throws IOException {
        if (ISFIRST != 0) {
            config.streamWriter.write(String.format(",\n"));
        } else {
            ISFIRST = 1;
        }
    }

    static void FIRSTPREFIX(Bit_Chain dat) throws IOException {
        if (ISFIRST != 0) {
            PRINTFIRST(dat);
        }
        ISFIRST = 1;
        _prefix(dat);
    }

    static void ARRAY(Bit_Chain dat) throws IOException {
        config.streamWriter.write("[");
        config.streamWriter.write("\n");
        dat.bit++;
        ISFIRST = 0;
    }

    static void ENDARRAY(Bit_Chain dat) throws IOException {
        config.streamWriter.write("\n");
        dat.bit--;
        _prefix(dat);
        config.streamWriter.write("]");
        ISFIRST = 1;
    }


    static void FIELD_RC(String nam, char val, Bit_Chain dat, int dxf) throws IOException {
        FIELD(nam, val, dat, dxf);
    }

    static void FIELD(String nam, Object val, Bit_Chain dat, int dxf) throws IOException {
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

    static void FIELD_RL(String nam, long val, Bit_Chain dat, int dxf) throws IOException {
        FIELD(nam, val, dat, dxf);
    }

    static void FIELD_VECTOR_INL(Object[] nam, int size, String type, String name, Bit_Chain dat, int dxf) throws IOException {
        FIELD_VECTOR_N(nam, size, type, name, dat, dxf);
    }

    static void FIELD_VECTOR_N(Object[] nam, int size, String type, String name, Bit_Chain dat, int dxf) throws IOException {
        KEY(dat, name);
        ARRAY(dat);
        if (nam != null) {
            for (int vcount = 0; vcount < size; vcount++) {
                FIRSTPREFIX(dat);
                if (type == "RC") {
                    config.streamWriter.write(String.valueOf((int) ((char) nam[vcount])));
                }
                if (type == "RS") {
                    config.streamWriter.write(String.valueOf((int) ((long) nam[vcount]) & 0xFFFF));
                }
                if (type == "RL") {
                    config.streamWriter.write(String.valueOf((int) ((long) nam[vcount]) & 0xFFFFFFFFL));
                }
            }
        } else {
            FIRSTPREFIX(dat);
        }
        ENDARRAY(dat);
    }

    static void FIELD_RSx(Bit_Chain dat, Object val, String name, int dxf) throws IOException {
        FIELD_RS(dat, val, name, dxf);
    }

    static void FIELD_RS(Bit_Chain dat, Object val, String name, int dxf) throws IOException {
        FIELD(name, val, dat, dxf);
    }

    static void FIELD_RLd(String name, long val, Bit_Chain dat, int dxf) throws IOException {
        FIELD_RL(name, val, dat, dxf);
    }

    static void FIELD_CAST(Bit_Chain dat, long val, String name, String type, int dxf) throws IOException {
        switch (type) {
            case "RLx":
                FIELD(name, val & 0xFFFFFFFFL, dat, dxf);
                break;
        }

    }

    static void FIELD_RLx(Bit_Chain dat, long val, String name, int dxf) throws IOException {
        FIELD_RL(name, val, dat, dxf);
    }

    static void FIELD_TIMERLL(Bit_Chain dat, String name, Dwg_Bitcode_TimeRLL nam, int dxf) throws IOException {
        Dwg_Bitcode_TimeBLL bll = new Dwg_Bitcode_TimeBLL();
        bll.days = nam.days;
        bll.ms = nam.ms;
        bll.value = nam.value;

        FIELD_TIMEBLL(dat, name, bll, dxf);
    }

    static void FIELD_TIMEBLL(Bit_Chain dat, String name, Dwg_Bitcode_TimeBLL nam, int dxf) throws IOException {
        PRINTFIRST(dat);
        _prefix(dat);
        config.streamWriter.write("\"" + _path_field(name) + "\": ");
        commonvar.Sw_write("[ " + nam.days + ", " + nam.ms + " ]");
    }

    static void FIELD_BINARY(Bit_Chain dat, String name, Bit_Chain obj, int dxf) throws IOException {
        KEY(dat, name);
        config.streamWriter.write("\"");
        if (obj.chain != null) {
            for (long _j = 0; _j < obj.size; _j++) {
                config.streamWriter.write(String.format("%02X", (byte) obj.chain[(int) _j]));
            }
        }
        config.streamWriter.write("\"");
    }

    static void FIELD_RLL(Bit_Chain dat, String name, long val, int dxf) throws IOException {
        FIELD(name, val, dat, dxf);
    }

    static void FIELD_BD(Bit_Chain dat, String name, double val, int dxf) throws IOException {
        if (!bits.bit_isnan(val)) {
            FIRSTPREFIX(dat);
            config.streamWriter.write("\"" + _path_field(name) + "\": ");
            _VALUE_RD(val, dat, dxf);
        }
    }

    static void _VALUE_RD(double value, Bit_Chain dat, int dxf) throws IOException {
        StringBuilder buffer = new StringBuilder();
        buffer.append(String.format("%.6f", value));

        int k = buffer.length();

        if (k > 0 && buffer.lastIndexOf(".") != -1 && buffer.charAt(k - 1) == '0') {
            while (k > 1 && buffer.charAt(k - 1) == '0' && buffer.charAt(k - 2) != '.') {
                buffer.deleteCharAt(k - 1);
                k--;
            }
            if (k > 1 && buffer.charAt(k - 1) == '.') {
                buffer.deleteCharAt(k - 1);
            }
        }
        config.streamWriter.write(buffer.toString());
    }

    static void FIELD_TV(Bit_Chain dat, String name, String value, int dxf) throws IOException {
        FIELD_TEXT(dat, name, value, dxf);
    }

    static void FIELD_TEXT(Bit_Chain dat, String name, String value, int dxf) throws IOException {
        FIRSTPREFIX(dat);
        config.streamWriter.write("\"" + _path_field(name) + "\": ");
        VALUE_TEXT(dat, value);

    }

    static void VALUE_TEXT(Bit_Chain dat, String str) throws IOException {
        if (str != null) {
            long len = str.length();
            if (len < 42) {
                long _len = 6 * len + 1;
                char[] _buf = new char[256];
                String quote_str = json_cquote(new String(_buf), str, _len, dat.codepages);
                config.streamWriter.write("\"" + quote_str.trim() + "\"");
            } else {
                long _len = 6 * len + 1;
                char[] _buf = new char[(int) len];
                String quote_str = json_cquote(new String(_buf), str, _len, dat.codepages);
                config.streamWriter.write("\"" + quote_str + "\"");
            }

        }
    }

    static String json_cquote(String dest, String src, long len, int codepages) {
        String result = "";
        char c;
        String s = src;
        String endp = dest + len;
        String d = dest;
        String tmp = null;
        int index = 0;


        if (src == null) {
            return "";
        }
        if (!src.isEmpty() && codepages > Dwg_Codepages.CP_US_ASCII.value
                && codepages <= Dwg_Codepages.CP_ANSI_1258.value) {
            tmp = bits.bit_TV_to_utf8(src, codepages);
            if (!tmp.isEmpty()) {
                s = tmp;
            }
        }
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (dest.length() >= endp.length()) {
                dest = "";
                if (tmp != null && !tmp.equals(src)) {
                    tmp = null;
                }

                return d;
            }
            if (c == '"' && dest.length() + 1 < endp.length()) {
                dest += '\\';
                dest += c;
            }
            else if (c == '\\' && dest.length() + 2 < endp.length()) {
                if (dest.length() + 5 < endp.length() && s.charAt(0) == 'U' && s.charAt(1) == '+'
                        && bits.ishex(s.charAt(2)) && bits.ishex(s.charAt(3)) &&
                        bits.ishex(s.charAt(4)) && bits.ishex(s.charAt(5))) {
                    dest += '\\';  // Append the backslash
                    dest += 'u';   // Append 'u'
                    s = s.substring(2);  // Skip 'U+' from the string
                } else {
                    dest += '\\';  // Append the backslash
                    dest += c;     // Append the character
                }
            } else if (c == '\n' && dest.length() + 1 < endp.length()) {
                dest += '\\';  // Append the backslash
                dest += 'n';   // Append the 'n' character for newline
            } else if (c == '\r' && dest.length() + 1 < endp.length()) {
                dest += '\\';  // Append the backslash
                dest += 'r';   // Append the 'r' character for carriage return
            } else if (c < 0x1f && dest.length() + 5 < endp.length()) {
                dest += "\\u00";  // Append the Unicode escape sequence
                dest += _hex(c >> 4);  // Append the upper nibble of the character
                dest += _hex(c & 0xf);  // Append the lower nibble of the character
            } else {
                dest += c;  // Append the character as is
            }


        }
        return dest;

    }

    static char _hex(int c) {
        c &= 0xF; // Mask to get the lower nibble (0-15)
        return (c >= 10) ? (char) ('a' + c - 10) : (char) ('0' + c);
    }
}
