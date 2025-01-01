import java.io.IOException;

public class dwg_spec {
    static int dwg_decode_BLOCK_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                        Dwg_Data objDwgData,DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_BLOCK_CONTROL blcckcontroll = obj.tio.object.tio.BLOCK_CONTROL;
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            blcckcontroll.common.num_entries = dec_macros.FIELD_RS(dat,"RS",70);
            blcckcontroll.common.flags_r11 = dec_macros.FIELD_RS(dat,"RS",70);
            //JSON { KEY (address); }
            //VALUE_RLx (obj->address & 0xFFFFFFFF, 0);
        }
        else //LATER_VERSIONS
        {
            blcckcontroll.common.num_entries = (int)dec_macros.FIELD_BL(dat,"BL",70);
        }
        obj.tio.object.ownerhandle = new Dwg_Object_Ref();
        dec_macros.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        if( blcckcontroll.common.entres == null){
            blcckcontroll.common.entres = new Dwg_Object_Ref[ blcckcontroll.common.num_entries];
        }
        blcckcontroll.common.entres = dec_macros.HANDLE_VECTOR(hdl_dat,blcckcontroll.common.num_entries,2,obj,objDwgData,0);
        blcckcontroll.model_space = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            blcckcontroll.paper_space = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        }
        return dec_macros.DWG_OBJECT_END(dat, hdl_dat, str_dat, obj, error);
    }

    static int dwg_json_BLOCK_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                       Dwg_Data objDwgData, DWG_OBJECT_TYPE type) throws IOException {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain();
        Bit_Chain str_dat = new Bit_Chain(dat);
        out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);
        return error;
    }
}
