import java.util.Arrays;

public class auxheader_spec {
    static int auxheader_spec_read(Bit_Chain dat, Dwg_Data objDwgData)
    {
        Dwg_AuxHeader obj = objDwgData.auxheader;
        obj.aux_intro = new char[3];
        obj.aux_intro  = dec_macros.FIELD_VECTOR_INL_CHAR(dat,"RC",3,0);
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
        obj.minus_1 = dec_macros.FIELD_RLd(dat,"RLd",0);
        obj.numsaves_1 = dec_macros.FIELD_RS(dat,"RS",0);
        obj.numsaves_2 = dec_macros.FIELD_RS(dat,"RS",0);
        obj.zero = dec_macros.FIELD_RL(dat,"RL",0);
        obj.dwg_version_1 = dec_macros.FIELD_RS(dat,"RS",0);

        if(commen.UNTIL(DWG_VERSION_TYPE.R_2013,dat))
        {
            Object maint_version_1 = dec_macros.FIELD_CAST(dat,"RS",0);
            obj.maint_version_1 = Long.parseLong(maint_version_1.toString());
        }else{
            obj.maint_version_1 = dec_macros.FIELD_RLx(dat,"RL",0);
        }
        obj.dwg_version_2 = dec_macros.FIELD_RS(dat,"RS",0);
        if(commen.UNTIL(DWG_VERSION_TYPE.R_2013,dat))
        {
            Object maint_version_2 = dec_macros.FIELD_CAST(dat,"RS",0);
            obj.maint_version_2 = Long.parseLong(maint_version_2.toString());
        }else{
            obj.maint_version_2 = dec_macros.FIELD_RLx(dat,"RL",0);
        }

        obj.unknown_6rs = new long[6];
        obj.unknown_6rs  = dec_macros.FIELD_VECTOR_INL_LONG(dat,"RS",6,0);

        obj.unknown_5rl = new long[5];
        obj.unknown_5rl  = dec_macros.FIELD_VECTOR_INL_LONG(dat,"RL",5,0);
        obj.TDCREATE = dec_macros.FIELD_TIMERLL(dat,0);
        obj.TDUPDATE = dec_macros.FIELD_TIMERLL(dat,0);
        obj.HANDSEED = dec_macros.FIELD_RLx(dat,"RL",0);
        obj.plot_stamp = dec_macros.FIELD_RL(dat,"RL",0);
        obj.zero_1 = dec_macros.FIELD_RL(dat,"RL",0);
        obj.numsaves_3 = dec_macros.FIELD_RS(dat,"RS",0);
        obj.zero_2 = dec_macros.FIELD_RS(dat,"RS",0);
        obj.zero_3 = dec_macros.FIELD_RL(dat,"RL",0);
        obj.zero_4 = dec_macros.FIELD_RL(dat,"RL",0);
        obj.numsaves_4 = dec_macros.FIELD_RL(dat,"RL",0);
        obj.zero_5 = dec_macros.FIELD_RL(dat,"RL",0);
        obj.zero_6 = dec_macros.FIELD_RL(dat,"RL",0);

        if(commen.SINCE(DWG_VERSION_TYPE.R_2004,dat))
        {
            obj.zero_7 = dec_macros.FIELD_RL(dat,"RL",0);
            obj.zero_8 = dec_macros.FIELD_RL(dat,"RL",0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2018,dat))
        {
            obj.zero_18 = new long[5];
            obj.zero_18 = dec_macros.FIELD_VECTOR_INL_LONG(dat,"RS",5,0);
        }
        return 0;
    }
}
