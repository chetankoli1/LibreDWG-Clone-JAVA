public class header_spec {
    static void header_spec_read(Bit_Chain dat, Bit_Chain hdl_dat,Dwg_Data objDwgData)
    {
        Dwg_Header header = new Dwg_Header();

        header.is_maint = dec_macros.FIELD_RC(dat,"RC",0);
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_2_0b,DWG_VERSION_TYPE.R_13b1,dat))
        {
            header.zero_one_or_three = dec_macros.FIELD_RC(dat,"RC",0);
            /*
       AC1.50: 3, 5, 74; 3, 5, 83;
       AC2.10: 3, 5, 83
       AC1001: 3, 5, 101
       AC1002: 3, 5, 104; 3, 5, 114
       AC1003: 3, 5, 120; 3, 5, 122
       AC1004: 3, 5, 129
       AC1006: 3, 5, 158; 3, 5, 160
       AC1009: 3, 5, 204; 3, 5, 205
     */
            header.numentity_sections = dec_macros.FIELD_RS(dat,"RS",0);
            header.sections = (int)dec_macros.FIELD_CAST(dat,"RL",0);
            header.numheader_vars = dec_macros.FIELD_RC(dat,"RC",0);
            header.dwg_version = dec_macros.FIELD_RC(dat,"RC",0);

            header.entities_start = dec_macros.FIELD_RLx(dat,"RLx",0);
            header.entities_end = dec_macros.FIELD_RLx(dat,"RLx",0);
            header.blocks_start = dec_macros.FIELD_RLx(dat,"RLx",0);
            header.blocks_size = dec_macros.FIELD_RLx(dat,"RLx",0);
            header.extras_start = dec_macros.FIELD_RLx(dat,"RLx",0);
            header.extras_size = dec_macros.FIELD_RLx(dat,"RLx",0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            header.zero_one_or_three = dec_macros.FIELD_RC(dat,"RC",0);
            header.thumbnail_address = dec_macros.FIELD_RL(dat,"RL",0);
            header.dwg_version = dec_macros.FIELD_RC(dat,"RC",0);
            header.maint_version = dec_macros.FIELD_RC(dat,"RC",0);
            if(specs.ENCODER)
            {
                header.codepage = dat.codepages;
            }
            header.codepage = dec_macros.FIELD_RS(dat,"RS",0);
            //LOG_TRACE ("%s\n", dwg_codepage_dxfstr ((Dwg_Codepage)FIELD_VALUE (codepage)))
            if(specs.DECODER)
            {
                dat.codepages = header.codepage;
            }
            if(commen.PRE(DWG_VERSION_TYPE.R_2004a,dat))
            {
                header.sections = dec_macros.FIELD_RL(dat,"RL",0);
            }
        }


        objDwgData.header = header;
    }
}
