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
}
