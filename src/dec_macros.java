public class dec_macros {
    static char FIELD_RC(Bit_Chain dat, String type, int dxf)
    {
        return bits.bit_read_RC(dat);
        //FIELD_G_TRACE (nam, type, dxf);
    }

    static int FIELD_RS(Bit_Chain dat, String type, int dxf) {
        return bits.bit_read_RS(dat);
    }
    static long FIELD_RL(Bit_Chain dat, String type, int dxf) {
        return bits.bit_read_RL(dat);
    }
    static int FIELD_RLx(Bit_Chain dat, String type, int dxf)
    {
        return (int)FIELD_CAST(dat,"RL",dxf);
    }
    static Object FIELD_CAST(Bit_Chain dat, String castType, int dxf) {
        switch (castType)
        {
            case "RC":
                return bits.bit_read_RC(dat);
            case "RS":
                return bits.bit_read_RS(dat);
            case "RL":
                return bits.bit_read_RL(dat);
            default:
                return 101;
        }
    }
}
