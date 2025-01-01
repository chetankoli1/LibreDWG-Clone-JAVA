import java.io.IOException;

public class common_object_handle_data_spec {
    static void common_object_handle_data_spec_read(Bit_Chain dat, Bit_Chain hdl_dat,
                                                    Dwg_Object obj, Dwg_Data objDwgData)
    {
        Dwg_Object_Object _obj = obj.tio.object;
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            _obj.num_reactors = (int)dec_macros.FIELD_BL(dat,"BL",0);
//            #ifdef IS_DECODER
//            if (FIELD_VALUE (num_reactors) * dwg_bits_size[BITS_HANDLE]
//                    > AVAIL_BITS (hdl_dat))
//            {
//                LOG_ERROR ("num_reactors: " FORMAT_BL " > AVAIL_BITS(hdl_dat): %" PRId64 "\n",
//                        FIELD_VALUE (num_reactors), AVAIL_BITS (hdl_dat))
//                FIELD_VALUE (num_reactors) = 0;
//                return DWG_ERR_VALUEOUTOFBOUNDS;
//            }
//            #endif
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {
            _obj.is_xdic_missing = dec_macros.FIELD_B(dat,"B",0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2013,dat))
        {
            _obj.has_ds_data = dec_macros.FIELD_B(dat,"B",0);
        }

//        #ifdef IS_DXF
//        // unused. see out_dxf.c
//        SINCE (R_13b1) {
//        XDICOBJHANDLE (3);
//        REACTORS (4);
//    }
//#endif
//
//#if !defined(IS_FREE) && !defined(IS_JSON)
//        // done later in the dwg.spec, because of num_entries
//        if (!dwg_obj_is_control (obj))
//#endif
//        {
//            SINCE (R_13b1) { FIELD_HANDLE (ownerhandle, 4, 330); }
//
//#ifndef IS_DXF
//            SINCE (R_13b1) {
//            REACTORS (4)
//            XDICOBJHANDLE (3)
//        }
//#endif
//        }
    }

    static int common_object_handle_data_spec_write(Bit_Chain dat, Bit_Chain hdlDat, Dwg_Object obj)
            throws IOException {
        out_json.FIELD_BL(dat,"num_reactors",obj.tio.object.num_reactors,0);
        if(macros.IS_DECODER)
        {
            if(obj.tio.object.num_reactors + Integer.parseInt(String.valueOf(commen.dwg_bits_size[DWG_BITS.BITS_HANDLE.ordinal()])) >=
                 dec_macros.AVAIL_BITS(hdlDat))
            {
                obj.tio.object.num_reactors = 0;
                return DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value;
            }
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_2004, dat))
        {
            out_json.FIELD_B(dat,"is_xdic_missing",obj.tio.object.is_xdic_missing,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2013, dat))
        {
            out_json.FIELD_B(dat,"has_ds_data",obj.tio.object.has_ds_data,0);
        }
//        if(macros.IS_DXF)
//        {
//            out_json.XDICOBJHANDLE(dat,obj,3);
//            out_json.REACTORS(dat,obj,4);
//        }
//        if(commen.SINCE(DWG_VERSION_TYPE.R_13, dat))
//        {
//            out_json.FIELD_HANDLE(dat,"ownerhandle", obj.tio.object.ownerhandle,4,330);
//        }
        //#if IS_DXF
//        if (!macros.IS_DXF)
//        {
//            if (dec_macros.SINCE((int)DWG_VERSION_TYPE.R_13, dat))
//            {
//                out_json.REACTORS(dat, obj, 4);
//                out_json.XDICOBJHANDLE(dat, obj, 3);
//            }
//        }
        //#endif
        return 0;
    }
}
