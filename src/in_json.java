import java.io.IOException;
import java.util.Optional;

public class in_json {
    public static int loglevel = 0;
    public static int JSMN_PARENT_LINKS = 0;
    static Bit_Chain g_dat;
    public static int JSMN_STRICT = 0;
    static int dwg_read_json(Bit_Chain dat, Dwg_Data objDwgData) throws IOException {
        int error = -1;
        Dwg_Header obj = objDwgData.header;
        jsmn_parser parser = new jsmn_parser();
        jsmntokens_t  tokens = new jsmntokens_t();
        jsmntok_t thejsmntok = new jsmntok_t();

        objDwgData.opts |= loglevel | dwg.DWG_OPTS_INJSON;
        if(dat.chain == null && dat.fh.length() > 0)
        {
            error = dwg.dat_read_stream(dat, dat.fh);
        }
        g_dat = dat;
        jsmn.jsmn_init(parser);
        tokens.num_tokens = jsmn.jsmn_parse(parser,dat.chain,dat.size,null,0);
        return error;
    }
}
