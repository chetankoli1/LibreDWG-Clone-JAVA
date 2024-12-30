import java.io.IOException;

public class template_spec {
    static int template_spec_read(Bit_Chain dat, Dwg_Object obj, Dwg_Data objDwgData,
                                  Dwg_Template _obj)
    {
        int error = 0;
        _obj.description = dec_macros.FIELD_T16(dat,"T16",0);
        _obj.MEASUREMENT = dec_macros.FIELD_RS(dat,"RS",0);
        return error;
    }

    static int template_spec_write(Bit_Chain dat, Dwg_Object obj, Dwg_Data objDwgData,
                                   Dwg_Template _obj) throws IOException {
        int error = 0;
        out_json.FIELD_T16(dat,"description",_obj.description,0);
        out_json.FIELD_RS(dat,_obj.MEASUREMENT,"MEASUREMENT",0);
        return error;
    }
}
