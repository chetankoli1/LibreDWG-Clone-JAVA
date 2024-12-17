public class auxheader_spec {
    static void auxheader_spec_read(Bit_Chain dat, Dwg_Data objDwgData)
    {
        Dwg_AuxHeader obj = objDwgData.auxheader;
        obj.aux_intro = new char[3];
        obj.aux_intro = dec_macros.FIELD_VECTOR_INL(dat,"RC",3,0);
        obj.dwg_version = dec_macros.FIELD_RS(dat,"RS",3);

        if(commen.UNTIL(DWG_VERSION_TYPE.R_2013,dat))
        {
            Object maint_version = dec_macros.FIELD_CAST(dat,"RS",0);
            obj.maint_version = Long.parseLong(maint_version.toString());
        }
        else{
            obj.maint_version = dec_macros.FIELD_RLx(dat,"RL",0);
        }
        obj.numsaves = dec_macros.FIELD_RL(dat,"RL",0);

    }
}
