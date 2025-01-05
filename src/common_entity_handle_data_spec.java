import java.io.IOException;

public class common_entity_handle_data_spec {
    static int common_entity_handle_data_spec_read(Bit_Chain dat, Bit_Chain hdl_dat,
                                                   Dwg_Object_Entity ent, Dwg_Object obj,
                                                   Dwg_Data objDwgData)
    {
        int error = 0;
//        #ifdef IS_DXF
//        ENT_REACTORS (4)
//        ENT_XDICOBJHANDLE (3)
//#endif
        if(ent.entmode == 0)
        {
            ent.ownerhandle = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,4,330);
        }
        /*
        * #ifdef IS_DXF
        if (FIELD_VALUE(entmode) != 0)
        {
            if (_ent->ownerhandle || _ent->entmode == 3) {
                VALUE_HANDLE (_ent->ownerhandle, ownerhandle, 5, 330);
            } else if (_ent->entmode == 1) {
                VALUE_HANDLE (dwg->header_vars.BLOCK_RECORD_PSPACE, BLOCK_RECORD_PSPACE, 5, 330);
            } else {
                //assert(_ent->entmode == 2);
                VALUE_HANDLE (dwg->header_vars.BLOCK_RECORD_MSPACE, BLOCK_RECORD_MSPACE, 5, 330);
            }
        }
#else
        *
        * # ifdef IS_DECODER
  if (_obj->num_reactors > 100000)
    {
      LOG_ERROR ("num_reactors: " FORMAT_BL ", AVAIL_BITS(hdl_dat): % " PRId64
                 "\n", _obj->num_reactors, AVAIL_BITS (hdl_dat))
      _obj->num_reactors = 0;
      return DWG_ERR_VALUEOUTOFBOUNDS;
    }
# endif
        *
        * */
        dec_macros.ENT_REACTORS(ent, 4, hdl_dat, obj, objDwgData);
        dec_macros.ENT_XDICOBJHANDLE(ent, 3, hdl_dat, obj, objDwgData);
//        #endif
//        SUBCLASS (AcDbEntity)
//#ifdef IS_DXF
//        // PaperSpace0 BLOCK may have entmode 0
//        if (_ent->entmode == 1 ||
//                (_ent->entmode == 0 && _ent->ownerhandle == obj->parent->header_vars.BLOCK_RECORD_PSPACE))
//            FIELD_BB (entmode, 67); // is paperspace
//#endif
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {

        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_2000,dat))
        {
            if(ent.nolinks == 0)
            {
                ent.prev_entity = new Dwg_Object_Ref();
                ent.prev_entity = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,4,0);

                ent.next_entity = new Dwg_Object_Ref();
                ent.next_entity = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,4,0);
            }
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            ent.layer = new Dwg_Object_Ref();
            ent.layer = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat)){
            if(ent.plotstyle_flags == 3)
            {
                ent.plotstyle = new Dwg_Object_Ref();
                ent.plotstyle = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,390);
            }
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2010b,dat))
        {

        }
        return error;
    }

    static int common_entity_handle_data_spec_write(Bit_Chain dat, Bit_Chain hdl_dat,
                                                     Dwg_Object_Entity ent, Dwg_Object obj,
                                                     Dwg_Data objDwgData) throws IOException {
        int error = 0;
        if(macros.IS_DXF)
        {

        }
        if(ent.entmode == 0)
        {
            out_json.FIELD_HANDLE(hdl_dat, "ownerhandle",ent.ownerhandle, 4, 330);
        }
        if(macros.IS_DXF)
        {

        }
        else {
            //# ifdef IS_DECODER
            if (macros.IS_DECODER)//check here
            {
                if (ent.num_reactor > 100000)
                {
                    // LOG_ERROR("num_reactors: " FORMAT_BL ", AVAIL_BITS(hdl_dat): %lld\n",_obj->num_reactors, AVAIL_BITS(hdl_dat))
                    ent.num_reactor = 0;
                    return DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value;
                }
            }
            //#endif
            out_json.ENT_REACTORS(hdl_dat, ent, 4);
            out_json.ENT_XDICOBJHANDLE(hdl_dat, ent, 3);
        }
        out_json.SUBCLASS(dat,"AcDbEntity");
        //# ifdef IS_DXF
        if (macros.IS_DXF)
        {
            // PaperSpace0 BLOCK may have entmode 0
            if (ent.entmode == 1 ||
                    (ent.entmode == 0 && ent.ownerhandle == obj.parent.header_vars.BLOCK_RECORD_PSPACE))
                ent.entmode = (char)bits.bit_read_BB(dat); // is paperspace
            //#endif
        }
//        if (dec_macros.VERSIONS((int)DWG_VERSION_TYPE.R_13, (int)DWG_VERSION_TYPE.R_14, dat))
//        {
//            out_json.FIELD_HANDLE(hdl_dat, ent.layer, "layer", 5, 0);
//
//            //# ifdef IS_ENCODER
//            if (macros.IS_DECODER)
//            {
//                if (dat.from_version == DWG_VERSION_TYPE.R_2000)
//                    ent.isbylayerlt = (char)(ent.ltype_flags < 3 ? 1 : 0);
//            }
//            //#endif
//            //# ifdef IS_DXF
//            if (macros.IS_DXF)
//            {
//                switch ((int)ent.ltype_flags) //check here
//                {//check here
//                    //case 0: VALUE_TV("ByLayer", 6); break;
//                    //case 1: VALUE_TV("ByBlock", 6); break;
//                    //case 2: VALUE_TV("Continuous", 6); break;
//                    default: break;
//                }
//            }
//            //#endif
//            if ((ent.isbylayerlt) == 0)
//                out_json.FIELD_HANDLE(hdl_dat, ent.ltype, "ltype", 5, 0);//hdl_dat check here
//        }

        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_2000,dat))
        {
            if((ent.nolinks) == 0)
            {
                out_json.FIELD_HANDLE(hdl_dat,"prev_entity",ent.prev_entity, 5, 0);
                out_json.FIELD_HANDLE(hdl_dat,"next_entity", ent.next_entity,5, 0);
            }
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000,dat))
        {
            out_json.FIELD_HANDLE(hdl_dat,"layer",ent.layer, 5, 0);
        }
        if ((ent.ltype_flags) == 3)
            out_json.FIELD_HANDLE(hdl_dat,"ltype",ent.ltype, 5, 0);

        if(macros.IS_DXF)
        {

        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007,dat))
        {

        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000,dat))
        {
            if ((ent.plotstyle_flags) == 3)
                out_json.FIELD_HANDLE(hdl_dat,"plotstyle",ent.plotstyle, 5, 0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2010,dat))
        {

        }

        return error;
    }
}
