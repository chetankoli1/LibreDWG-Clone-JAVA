import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class out_json {
    public static int ISFIRST = 0;

    public static int dwg_write_json(Bit_Chain dat, Dwg_Data objDwgData) throws IOException {
        Dwg_Header obj = objDwgData.header;
        specs.IS_JSON = true;
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
        if(dat.version.ordinal() >= DWG_VERSION_TYPE.R_13b1.ordinal())
        {
            if (json_classes_write(dat, objDwgData) >= DWG_ERROR.DWG_ERR_CRITICAL) {
                return 1;
            }
        }
        if(json_objects_write(dat,objDwgData) >= DWG_ERROR.DWG_ERR_CRITICAL)
        {
            return 1;
        }
        if (dat.version.ordinal() >= DWG_VERSION_TYPE.R_13b1.ordinal()) {
            if (json_thumbnail_write(dat, objDwgData) >= DWG_ERROR.DWG_ERR_CRITICAL) {
                return 1;
            }
            if (dat.version.ordinal() <= DWG_VERSION_TYPE.R_2000.ordinal()) {
                if (objDwgData.header.sections >= 3 && objDwgData.objfreespace.numnums != 0) {
                    error |= json_section_objfreespace(dat, objDwgData);
                }
                if(objDwgData.secondheader.num_sections != 0)
                {
                    error |= json_section_2ndheader(dat, objDwgData, objDwgData.secondheader);
                }
                if (objDwgData.header.sections >= 4) {
                    error |= json_section_template(dat, objDwgData);
                }
                if (objDwgData.header.sections >= 6) {
                    error |= json_section_auxheader(dat, objDwgData);
                }
            }
        }
        config.streamWriter.write("}");
        config.streamWriter.write("\n");
        return 0;
    }

    static int json_objects_write(Bit_Chain dat, Dwg_Data objDwgData) throws IOException
    {
        CLEARFIRST(dat);
        SECTION(dat,"OBJECTS");
        for(int i = 0; i < objDwgData.num_objects; i++)
        {
            int error = 0;
            Dwg_Object obj = objDwgData.object[i];
            FIRSTPREFIX(dat); HASH(dat);
            error = dwg_json_object(dat,obj,objDwgData);
            ENDHASH(dat);
            CLEARFIRST(dat);

        }
        ENDSEC(dat);
        return 0;
    }

    static int json_section_template(Bit_Chain dat, Dwg_Data objDwgData) throws IOException {
        Dwg_Template temp = objDwgData.template;
        Dwg_Object obj = null;
        int error = 0;

        RECORD(dat,"Template");
        error = template_spec.template_spec_write(dat,obj,objDwgData,temp);
        ENDRECORD(dat);

        return error;
    }

    static int json_section_2ndheader(Bit_Chain dat, Dwg_Data objDwgData,Dwg_SecondHeader _obj) throws IOException {
        Bit_Chain str_dat = dat;
        Dwg_Object obj = new Dwg_Object();
        int error = 0;

        RECORD(dat,"SecondHeader");

        second_header_spec.second_header_spec_write(dat, obj, objDwgData, _obj);

        if(commen.VERSIONS(DWG_VERSION_TYPE.R_14,DWG_VERSION_TYPE.R_2000,dat))
        {
            out_json.FIELD_RLL1("junk_r14",_obj.junk_r14.toString(),dat,0);
        }
        ENDRECORD(dat);
        return error;
    }

    static void FIELD_RLL1(String name, String val, Bit_Chain dat, int dxf) throws IOException {
        FIELD(name, val, dat, dxf);
    }

    static int json_section_objfreespace(Bit_Chain dat, Dwg_Data objDwgData) throws IOException {
        RECORD(dat,"ObjFreeSpace");
        Dwg_Object obj = null;
        int error = 0;
        error = objfreespace_spec.objfreespace_spec_write(dat,obj,objDwgData);

        ENDRECORD(dat);
        return error;
    }

    static int json_classes_write(Bit_Chain dat, Dwg_Data objDwgData) throws IOException {
        SECTION(dat,"CLASSES");
        for(int i = 0; i < objDwgData.num_classes; i++)
        {
            Dwg_Class klass = objDwgData.dwg_class[i];
            FIRSTPREFIX(dat); HASH(dat);
            FIELD_BS(dat,"number",klass.number,0);
            FIELD_TV(dat,"dxfname",new String(klass.dxfname),1);
            FIELD_T(dat,"cppname",new String(klass.cppname),2);
            FIELD_T(dat,"appname",new String(klass.appname),2);
            FIELD_BS(dat,"proxyflag",klass.proxyflag,90);
            FIELD_BL(dat,"num_instances",klass.num_instances,91);
            FIELD_B(dat,"is_zombie",klass.is_zombie,280);
            FIELD_BS(dat,"item_class_id",klass.item_class_id,281);
            ENDHASH(dat);
            CLEARFIRST(dat);
        }
        ENDSEC(dat);
        return 0;
    }

    static void CLEARFIRST(Bit_Chain dat) {
        dat.opts &= (char) ~dwg.DWG_OPTS_JSONFIRST;
    }

    static void FIELD_T(Bit_Chain dat, String name, String val, int dxf) throws IOException {
        if(bits.IS_FROM_TU(dat))
        {
           // FIELD_TU(dat,name,val,dxf);
        }
        else{
            FIELD_TV(dat,name,val,dxf);
        }
    }

    static void SECTION(Bit_Chain dat,String name) throws IOException {
        KEY(dat,name);
        ARRAY(dat);
    }
    static void ENDSEC(Bit_Chain dat) throws IOException {
        ENDARRAY(dat);
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

        error = header_variables_spec.header_variables_spec_write(dat,dat,dat ,objDwgData);

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
    static void KEYs(Bit_Chain dat, String name) throws IOException
    {
        if (ISFIRST != 0)
        {
            PRINTFIRST(dat);
        }

        ISFIRST = 1;

        _prefix(dat);
        config.streamWriter.write("\""+name+"\": ");
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
            case "RLL":
                FIELD(name, val & 0xFFFFFFFFL, dat, dxf);
                break;
            case "RL":
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
            _VALUE_BD(val, dat, dxf);
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

    static void _VALUE_BD(double value, Bit_Chain dat, int dxf) throws IOException {
        StringBuilder buffer = new StringBuilder();
        buffer.append(String.format("%.14f", value));

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
                s = tmp.trim();
            }
        }
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
//            if (dest.length() >= endp.length()) {
//                dest = "";
//                if (tmp != null && !tmp.equals(src)) {
//                    tmp = null;
//                }
//
//                return d;
//            }
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

    static void FIELD_BL(Bit_Chain dat, String name, long value, int dxf) throws IOException {
        FIELD(name,value,dat,dxf);
    }

    static void FIELD_BS(Bit_Chain dat, String name, int value, int dxf) throws IOException {
        FIELD(name,value,dat,dxf);
    }

    static void FIELD_HANDLE(Bit_Chain hdl_dat, String name, Dwg_Object_Ref valRef, int code, int dxf) throws IOException {
        if(valRef != null)
        {
            if(commen.PRE(DWG_VERSION_TYPE.R_13b1,hdl_dat))
            {
                KEY(hdl_dat,name);
                ARGS_HREF11(valRef);

            }
            else{
                KEY(hdl_dat,name);
                ARGS_HREF11(valRef);
            }
        }
    }

    private static final String PRIu64 = "%d";
    private static final String FORMAT_RLL = PRIu64;
    private static final String FORMAT_HREF11 = "[%d,%d," + FORMAT_RLL + "]";
    private static final String JSON_KEY = "\"%s\":%s";
    private static void ARGS_HREF11(Dwg_Object_Ref ref) throws IOException {
        commonvar.Sw_write("["+(byte)ref.handleref.code+","+(byte)ref.handleref.size
                +","+ref.handleref.value+","+ref.absolute_ref+"]");
    }

    static void FIELD_B(Bit_Chain dat, String name, char value, int dxf) throws IOException {
        FIELD(name,value,dat,dxf);
    }

    static void FIELD_BSd(Bit_Chain dat, String name, short value, int dxf) throws IOException {
        FIELD(name,value,dat,dxf);
    }
    static void FIELD_BSx(Bit_Chain dat, String name, short value, int dxf) throws IOException {
        FIELD(name,value,dat,dxf);
    }

    static void FIELD_CMC(Bit_Chain dat, String name, Dwg_Color value, int dxf) throws IOException {
        if(dat.version.ordinal() >= DWG_VERSION_TYPE.R_2004.ordinal())
        {
            KEYs(dat,name);
            HASH(dat);
            if(value.index != 0)
            {
                FIELD_BS(dat,"index",value.index,62);
            }
            FIRSTPREFIX(dat);
            config.streamWriter.write("\""+_path_field("rgb")+ "\"");
            config.streamWriter.write("\""+_path_field(String.format("%06x", value.rgb))+ "\"");
            if(value.flag != 0)
            {
                FIELD_BS(dat,"flag",value.flag,62);
            }
            //pending
            if(value.flag > 0 && value.flag < 8)
            {

            }
            ENDHASH(dat);
        }
        else {
            KEY(dat,_path_field(name));
            config.streamWriter.write(value.index+"");
        }
    }
    static void SUB_FIELD_CMC(Bit_Chain dat, String name, Dwg_Color value, int dxf) throws IOException {
        if(dat.version.ordinal() >= DWG_VERSION_TYPE.R_2004.ordinal())
        {
            KEYs(dat,name);
            HASH(dat);
            if(value.index != 0)
            {
                FIELD_BS(dat,"index",value.index,62);
            }
            FIRSTPREFIX(dat);
            config.streamWriter.write("\""+_path_field("rgb")+ "\"");
            config.streamWriter.write("\""+_path_field(String.format("%06x", value.rgb))+ "\"");
            if(value.flag != 0)
            {
                FIELD_BS(dat,"flag",value.flag,62);
            }
            //pending
            if(value.flag > 0 && value.flag < 8)
            {

            }
            ENDHASH(dat);
        }
        else {
            KEY(dat,_path_field(name));
            config.streamWriter.write(value.index+"");
        }
    }

    public static void FIELD_DATAHANDLE(Bit_Chain hdl_dat, String name, Dwg_Object_Ref valRef, int code, int dxf) throws IOException {
        FIELD_HANDLE(hdl_dat,name,valRef,code,dxf);
    }

    static void FIELD_3BD(Bit_Chain dat, String name, Dwg_Bitcode_3BD value, int dxf) throws IOException {
        KEY(dat,_path_field(name));
        config.streamWriter.write("[ ");
        _VALUE_BD(value.x,dat,dxf);
        config.streamWriter.write(", ");
        _VALUE_BD(value.y,dat,dxf);
        config.streamWriter.write(", ");
        _VALUE_BD(value.z,dat,dxf);
        config.streamWriter.write(" ] ");
    }
    static void FIELD_3RD(Bit_Chain dat, String name, Dwg_Bitcode_3RD value, int dxf) throws IOException {
        KEY(dat,_path_field(name));
        config.streamWriter.write("[ ");
        _VALUE_RD(value.x,dat,dxf);
        config.streamWriter.write(", ");
        _VALUE_RD(value.y,dat,dxf);
        config.streamWriter.write(", ");
        _VALUE_RD(value.z,dat,dxf);
        config.streamWriter.write(" ] ");
    }

    static void FIELD_2RD(Bit_Chain dat, String name, Dwg_Bitcode_2RD value, int dxf) throws IOException {
        KEY(dat,_path_field(name));
        config.streamWriter.write("[ ");
        _VALUE_RD(value.x,dat,dxf);
        config.streamWriter.write(", ");
        _VALUE_RD(value.y,dat,dxf);
        config.streamWriter.write(" ] ");
    }
    static void FIELD_2BD(Bit_Chain dat, String name, Dwg_Bitcode_2BD value, int dxf) throws IOException {
        KEY(dat,_path_field(name));
        config.streamWriter.write("[ ");
        _VALUE_BD(value.x,dat,dxf);
        config.streamWriter.write(", ");
        _VALUE_BD(value.y,dat,dxf);
        config.streamWriter.write(" ] ");
    }

    static void FIELD_BLx(Bit_Chain dat, String name, long value, int dxf) throws IOException {
        FIELD_BL(dat,name,value,dxf);
    }

    static void FIELD_TFF(Bit_Chain dat, String name, String value, int dxf) throws IOException {
        FIELD_TF(dat,name,value,dxf);
    }

    static void FIELD_TF(Bit_Chain dat, String name, String value, int dxf) throws IOException {
        FIRSTPREFIX(dat);
        config.streamWriter.write("\""+_path_field(name)+ "\": ");
        json_write_TF(dat,value,value.length());
    }
    static void json_write_TF(Bit_Chain dat, String src, int len) {
        int slen = (src != null) ? src.length() : 0;
        boolean hasSlack = false;

        try {
            config.streamWriter.write('"');

            if (slen == 0) {
                config.streamWriter.write('"');
                return;
            }

            for (int i = 0; i < len; i++) {
                char c = src.charAt(i);

                if (c == '\r' || c == '\n' || c == '"' || c == '\\') {
                    config.streamWriter.write('\\');
                    if (c == '\r') {
                        config.streamWriter.write('r');
                    } else if (c == '\n') {
                        config.streamWriter.write('n');
                    } else {
                        config.streamWriter.write(c);
                    }
                } else if (c == '\\' && i + 6 < len && src.charAt(i + 1) == 'U' && src.charAt(i + 2) == '+'
                        && isHex(src.charAt(i + 3)) && isHex(src.charAt(i + 4))
                        && isHex(src.charAt(i + 5)) && isHex(src.charAt(i + 6))) {
                    config.streamWriter.write('\\');
                    config.streamWriter.write('u');
                    i += 3; // Skip 'U+'
                } else if (c == '\0') {
                    if (!hasSlack && i == slen) {
                        for (int j = i; j < len; j++) {
                            if (src.charAt(j) != '\0') {
                                hasSlack = true;
                                break;
                            }
                        }
                    }
                    if (hasSlack) {
                        config.streamWriter.write("\\u0000");
                    } else {
                        config.streamWriter.write('"');
                        return;
                    }
                } else if (c < 0x1F) {
                    config.streamWriter.write(String.format("\\u00%02x", (int) c));
                } else {
                    config.streamWriter.write(c);
                }
            }
            config.streamWriter.write('"');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to check if a character is a hexadecimal digit
    static boolean isHex(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }

    static void REPEAT(Bit_Chain dat, String sections) throws IOException {
        KEY(dat,sections);
        ARRAY(dat);
    }

    static void ENDREPEAT(Bit_Chain dat) throws IOException {
        ENDARRAY(dat);
    }

    static void REPEAT_BLOCK(Bit_Chain dat) throws IOException
    {
        FIRSTPREFIX(dat); HASH(dat);
    }
    static void END_REPEAT_BLOCK(Bit_Chain dat) throws IOException
    {
        ENDHASH(dat);
    }

    static void SUB_FIELD_RCd(Bit_Chain dat, String name, char value, int dxf) throws IOException {
        FIELD(name,value,dat,dxf);
    }

    public static void SUB_FIELD_BL(Bit_Chain dat, String name, long value, int dxf) throws IOException {
        FIELD(name,value,dat,dxf);
    }

    static void SUB_FIELD_VECTOR_INL(Bit_Chain dat, String name, char[] arr, int size, int dxf) throws IOException {
        SUB_FIELD_VECTOR_N(dat,name,arr,size,dxf);
    }

    static void SUB_FIELD_VECTOR_N(Bit_Chain dat, String name, char[] arr, int size, int dxf) throws IOException {
        KEY(dat,name);
        ARRAY(dat);
        if(arr != null)
        {
            for(int i = 0; i < size; i++)
            {
                FIRSTPREFIX(dat);
                byte val = (byte)arr[i];
                config.streamWriter.write(_path_field(val+""));
            }
        }else{
            PRINTFIRST(dat);
        }
        ENDARRAY(dat);
    }

    static void FIELD_T16(Bit_Chain dat, String name, String value, int dxf) throws IOException {
        FIELD_T(dat,name,value,dxf);
    }

    static int dwg_json_object(Bit_Chain dat, Dwg_Object obj, Dwg_Data objDwgData) throws IOException {
        int error = 0;

        if(obj == null || obj.parent == null){
            return DWG_ERROR.DWG_ERR_INTERNALERROR.value;
        }
        int type = obj.fixedtype.value;

        switch (obj.fixedtype)
        {
            case DWG_TYPE_BLOCK_CONTROL:
                error = dwg_spec.dwg_json_BLOCK_CONTROL("BLOCK_CONTROL", obj, dat, objDwgData, DWG_OBJECT_TYPE.DWG_TYPE_BLOCK_CONTROL);
                break;
            default:
                System.out.println("NOt found for writting");
                break;
        }

        return error;
    }

    static int dwg_json_token(Bit_Chain dat, Dwg_Object obj,
                               String name,DWG_OBJECT_TYPE type, Bit_Chain hdl_dat, Bit_Chain str_dat) throws IOException {
        int error = 0;
        Object _obj = new Object();
        if(!name.contains("UNKNOWN_"))
        {
            _obj = getObject(name,obj);
        }
        else {
            _obj = getObject(obj.dxfname,obj);
        }
        if(name.charAt(0) == '_')
        {
            FIELD_TEXT(dat,"object", String.valueOf(name.charAt(1)),0);
        }
        else {
            FIELD_TEXT(dat,"object",name,0);
        }
        if(!obj.dxfname.isEmpty() && commen.strNE(obj.dxfname,name))
        {
            FIELD_TEXT(dat,"dxfname",obj.dxfname,0);
        }
        FIELD("index",obj.index,dat,0);
        FIELD("type",obj.type,dat,0);
        KEY(dat,"handle");
        VALUE_H(obj.handle,dat,5);
        FIELD("size",obj.size,dat,0);
        FIELD("bitsize",obj.bitsize,dat,0);
        //error |= json_eed(dat, obj.tio._object, name);
        error |= json_common_object_handle_data(dat, hdl_dat, obj);
        if(!name.contains("UNKNOWN_"))
        {
            return error | dwg_json_token_private(dat,hdl_dat,str_dat,obj,name);
        }
        else {
            return error | dwg_json_token_private(dat, hdl_dat, str_dat, obj, obj.dxfname);
        }
    }

    static int dwg_json_token_private(Bit_Chain dat, Bit_Chain hdl_dat, Bit_Chain str_dat,
                                      Dwg_Object obj, String dxfname)
    {
        int error = 0;
        Dwg_Data objdwg = obj.parent;
        return error;
    }

    static int json_common_object_handle_data(Bit_Chain dat, Bit_Chain hdl_dat, Dwg_Object obj)
            throws IOException {
        int error = 0;
        error = common_object_handle_data_spec.common_object_handle_data_spec_write(dat, hdl_dat, obj);
        return error;
    }

    static void VALUE_H(Dwg_Handle hdl, Bit_Chain dat, int code) throws IOException {
        commonvar.Sw_write("[" + (byte)hdl.code + "," + (byte)hdl.size + "," + hdl.value +"]");
    }

    static Object getObject(String type, Dwg_Object obj) {
        switch (type)
        {
            case "BLOCK_CONTROL": return obj.tio.object.tio.BLOCK_CONTROL;
            default: return null;
        }
    }

    static void CONTROL_HANDLE_STREAM(Dwg_Object obj, Bit_Chain hdl_dat, Bit_Chain dat,
                                      Dwg_Data objDwgData, Dwg_Object_Ref ref) throws IOException {
        assert obj.supertype == DWG_OBJECT_SUPERTYPE.DWG_SUPERTYPE_OBJECT;
        if(commen.PRE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            hdl_dat._byte = dat._byte;
            hdl_dat.bit = dat.bit;
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
           // out_json.VALUE_H(ref.handleref,hdl_dat,5);
            out_json.FIELD_HANDLE(dat,"ownerhandle",ref,4,330);
          //  ref = dec_macros.VALUE_HANDLE(hdl_dat,ref,4,obj,objDwgData,0);
           // obj.tio.object.ownerhandle = ref;
//            REACTORS(4,obj,hdl_dat,objDwgData);
//            XDICOBJHANDLE(hdl_dat,3,obj,objDwgData);
        }
    }

    static void HANDLE_VECTOR(Bit_Chain dat, Dwg_Object_Ref[] nam, String name,
                              int size, int code, int dxf) throws IOException {
        HANDLE_VECTOR_N(dat,nam,name,size,code,dxf);
    }

    static void HANDLE_VECTOR_N(Bit_Chain dat, Dwg_Object_Ref[] nam, String name,
                                int size, int code, int dxf) throws IOException {
        KEY(dat,name);
        ARRAY(dat);
        for(int i = 0; i < size; i++)
        {
            FIELD_HANDLE_N(nam[i],dat,code,dxf);
        }
        ENDARRAY(dat);
    }

    static void FIELD_HANDLE_N(Dwg_Object_Ref nam, Bit_Chain dat, int code, int dxf) throws IOException {
        PRINTFIRST(dat);
        if(nam != null)
        {
            _prefix(dat);
            ARGS_HREF11(nam);
        }
        else {
            _prefix(dat);
            commonvar.Sw_write("[0, 0, 0]");
        }
    }

//    static void XDICOBJHANDLE(Bit_Chain dat,int code, Dwg_Object obj, Dwg_Data objDwgData) throws IOException {
//        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
//        {
//            if(obj.tio.object.is_xdic_missing == 0)
//            {
//                obj.tio.object.xdicobjhandle = new Dwg_Object_Ref();
//                obj.tio.object.xdicobjhandle = VALUE_HANDLE(dat,obj.tio.object.xdicobjhandle,code,obj,objDwgData,360);
//                KEY(dat,"xdicobjhandle");
//                VALUE_H(obj.tio.object.xdicobjhandle,);
//                if(obj.tio.object.xdicobjhandle == null)
//                {
//                    obj.tio.object.is_xdic_missing = 1;
//                }
//            }
//        }else {
//            if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
//            {
//                obj.tio.object.xdicobjhandle = new Dwg_Object_Ref();
//                obj.tio.object.xdicobjhandle = VALUE_HANDLE(dat,obj.tio.object.xdicobjhandle,code,obj,objDwgData,360);
//            }
//        }
//    }
//
//    static void REACTORS(int code, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData) {
//        if (obj.tio.object.num_reactors > 0) {
//            _VECTOR_CHKCOUNT(obj.tio.object.num_reactors, TYPE_MAXELEMSIZE("RS"), dat, obj, obj.tio.object.reactors);
//            obj.tio.object.reactors = Arrays.copyOf(obj.tio.object.reactors, (int) obj.tio.object.num_reactors);
//
//            for (int vcount = 0; vcount < obj.tio.object.num_reactors; vcount++) {
//                obj.tio.object.reactors[vcount] = new Dwg_Object_Ref();
//                VALUE_HANDLE_N(dat, code, objDwgData, obj, obj.tio.object.reactors[vcount], 330);
//            }
//        }
//    }
}
