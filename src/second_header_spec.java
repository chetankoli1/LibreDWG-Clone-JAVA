import java.io.IOException;

public class second_header_spec {
    static void second_header_spec_read(Bit_Chain dat, Dwg_Object obj, Dwg_Data objDwgData, Dwg_SecondHeader secondHeader) {
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13,DWG_VERSION_TYPE.R_2000,dat))
        {
            secondHeader.size = dec_macros.FIELD_RL(dat,"RL",0);
            /*
             * #ifdef IS_DECODER
             *   _VECTOR_CHKCOUNT_STATIC(size, _obj->size, 8, dat)
             * #endif
             * */
            secondHeader.address = dec_macros.FIELD_BL(dat,"BL",0);
            secondHeader.version = dec_macros.FIELD_TFF(dat,11,"TFF",0).toCharArray();
            secondHeader.is_maint = dec_macros.FIELD_RC(dat,"RC",0);
            secondHeader.zero_one_or_three = dec_macros.FIELD_RC(dat,"RC",0);
            secondHeader.dwg_version = dec_macros.FIELD_BSx(dat,"BSx",0);
            if(specs.DECODER)
            {
                //LOG_TRACE("=> header dwg_version: 0x%x\n", _obj->dwg_version & 0xFF);
                //LOG_TRACE("=> header maint_version: 0x%x\n", (_obj->dwg_version >> 8) & 0xFF);
            }
            secondHeader.codepage = dec_macros.FIELD_RS(dat,"RS",0);
            secondHeader.num_sections = dec_macros.FIELD_BS(dat,"BS",0);
            commen.VALUEOUTOFBOUNDS(secondHeader.num_sections,6);
            for(int i = 0; i < secondHeader.num_sections; i++)
            {
                Dwg_SecondHeader_Sections sec = new Dwg_SecondHeader_Sections();
                sec.nr = dec_macros.SUB_FIELD_RCd(dat,"RCd",0);
                sec.address = dec_macros.SUB_FIELD_BL(dat,"BL",0);
                sec.size = dec_macros.SUB_FIELD_BL(dat,"BL",0);
                secondHeader.sections[i] = sec;
            }
            secondHeader.num_handles = dec_macros.FIELD_BS(dat,"BS",0);
            commen.VALUEOUTOFBOUNDS(secondHeader.num_handles,14);
            for(int i = 0; i < secondHeader.num_handles; i++)
            {
                Dwg_SecondHeader_Handles hdl = secondHeader.handles[i];
                hdl.num_hdl = dec_macros.SUB_FIELD_RCd(dat,"RCd",0);
                hdl.nr = dec_macros.SUB_FIELD_RCd(dat,"RCd",0);
                hdl.hdl = dec_macros.SUB_FIELD_VECTOR_INL(dat,"RC",(int)hdl.num_hdl,0);
            }
        }
    }
    static void second_header_spec_write(Bit_Chain dat, Dwg_Object obj, Dwg_Data objDwgData,
                                         Dwg_SecondHeader secondHeader) throws IOException {
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13,DWG_VERSION_TYPE.R_2000,dat))
        {
            out_json.FIELD_RL("size",secondHeader.size,dat,0);
            /*
             * #ifdef IS_DECODER
             *   _VECTOR_CHKCOUNT_STATIC(size, _obj->size, 8, dat)
             * #endif
             * */
            out_json.FIELD_RL("address",secondHeader.address,dat,0);
            out_json.FIELD_TFF(dat,"version",new String(secondHeader.version),0);
            out_json.FIELD_RC("is_maint",secondHeader.is_maint,dat,0);
            out_json.FIELD_RC("zero_one_or_three",secondHeader.zero_one_or_three,dat,0);
            out_json.FIELD_BSx(dat,"dwg_version",(short)secondHeader.dwg_version,0);
            if(specs.DECODER)
            {
                //LOG_TRACE("=> header dwg_version: 0x%x\n", _obj->dwg_version & 0xFF);
                //LOG_TRACE("=> header maint_version: 0x%x\n", (_obj->dwg_version >> 8) & 0xFF);
            }
            out_json.FIELD_RS(dat,secondHeader.codepage,"codepage",0);
            out_json.FIELD_BS(dat,"num_sections",secondHeader.num_sections,0);

            out_json.REPEAT(dat,"sections");
            for(int i = 0; i < secondHeader.num_sections; i++)
            {
                out_json.REPEAT_BLOCK(dat);
                Dwg_SecondHeader_Sections sec = secondHeader.sections[i];
                out_json.SUB_FIELD_RCd(dat,"nr",sec.nr,0);
                out_json.SUB_FIELD_BL(dat,"address",sec.address,0);
                out_json.SUB_FIELD_BL(dat,"size",sec.size,0);
                out_json.END_REPEAT_BLOCK(dat);
            }
            out_json.ENDREPEAT(dat);

            out_json.FIELD_BS(dat,"num_handles",secondHeader.num_handles,0);
            out_json.REPEAT(dat,"handles");
            for(int i = 0; i < secondHeader.num_handles; i++)
            {
                out_json.REPEAT_BLOCK(dat);
                Dwg_SecondHeader_Handles hdl = secondHeader.handles[i];
                out_json.SUB_FIELD_RCd(dat,"num_hdl",hdl.num_hdl,0);
                out_json.SUB_FIELD_RCd(dat,"nr",hdl.nr,0);
                out_json.SUB_FIELD_VECTOR_INL(dat,"hdl",hdl.hdl,(int)hdl.num_hdl,0);
                out_json.END_REPEAT_BLOCK(dat);
            }
            out_json.ENDREPEAT(dat);
        }
    }
}
