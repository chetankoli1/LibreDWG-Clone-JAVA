enum jsmntype_t
{
    JSMN_UNDEFINED,JSMN_OBJECT,JSMN_ARRAY,JSMN_STRING,JSMN_PRIMITIVE
}

public class jsmn {
    enum jsmnerr
    {
        /* Not enough tokens were provided */
        JSMN_ERROR_NOMEM (-1),
        /* Invalid character inside JSON string */
        JSMN_ERROR_INVAL(-2),
        /* The string is not a full JSON packet, more bytes expected */
        JSMN_ERROR_PART(-3);
        int value;
        jsmnerr(int value)
        {
            this.value = value;
        }
    };
    static void jsmn_init(jsmn_parser parser) {
        parser.pos = 0;
        parser.toknext = 0;
        parser.toksuper = -1;
    }

    /**
     * Parse JSON string and fill tokens.
     */
    static long jsmn_parse(jsmn_parser parser, char[] js, long len,
                           jsmntok_t[] tokens, int num_tokens) {
        int r = 0; int i = 0;
        jsmntok_t token = new jsmntok_t();
        int count = parser.toknext;
        int jSMN_STRICT = in_json.JSMN_STRICT;

        for(;parser.pos < len && js[parser.pos] != '\0'; parser.pos++)
        {
            char c;
            jsmntype_t type;

            c = js[parser.pos];
            switch (c)
            {
                case '{':
                case '[':
                    count++;
                    if(tokens == null)
                    {
                        break;
                    }
                    token = jsmn_alloc_token(parser, tokens, num_tokens);
                    if(tokens == null)
                    {
                        return jsmnerr.JSMN_ERROR_NOMEM.value;
                    }
                    if(parser.toksuper != -1)
                    {
                        jsmntok_t t = tokens[parser.toksuper];
                        if(in_json.JSMN_STRICT == 1)
                        {
                            if(t.type == jsmntype_t.JSMN_OBJECT)
                            {
                                return jsmnerr.JSMN_ERROR_INVAL.value;
                            }
                        }
                        t.size++;
                        if(in_json.JSMN_PARENT_LINKS == 1)
                        {
                            token.parent = parser.toksuper;
                        }
                    }
                    token.type = (c == '{' ? jsmntype_t.JSMN_OBJECT : jsmntype_t.JSMN_ARRAY);
                    token.size = parser.pos;
                    parser.toksuper = parser.toknext - 1;
                    break;
                case '}':
                case ']':

            }
        }
        //now
        return 0;
    }

    static jsmntok_t jsmn_alloc_token(jsmn_parser parser, jsmntok_t[] tokens, int num_tokens)
    {
        jsmntok_t tok = new jsmntok_t();
        if (parser.toknext >= num_tokens)
        {
            return null;
        }
        tok = tokens[parser.toknext++];
        tok.start = tok.end = -1;
        tok.size = 0;
        if (in_json.JSMN_PARENT_LINKS == 1)
        {
            tok.parent = -1;
        }

        return tok;
    }
}
class jsmn_parser
{
    //test
    public int pos;
    public int toknext;
    public int toksuper;
}
class jsmntokens_t
{
    public int index;
    public jsmntok_t[] tokens;
    public long num_tokens;
}

class jsmntok_t {
    public jsmntype_t type;
    public int start;
    public int end;
    public int size;
    public int parent;
}