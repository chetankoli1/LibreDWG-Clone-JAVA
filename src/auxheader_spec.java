import java.io.IOException;
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
        obj.zero_1 = dec_macros.FIELD_RS(dat,"RL",0);
        obj.numsaves_3 = dec_macros.FIELD_RS(dat,"RS",0);
        obj.zero_2 = dec_macros.FIELD_RL(dat,"RS",0);
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

    static void auxheader_spec_write(Bit_Chain dat, Dwg_Data objDwgData) throws IOException
    {
        Object[] org = new Object[3];
        for(int i = 0; i < 3; i++)
        {
            org[i] = (Object) objDwgData.auxheader.aux_intro[i];
        }
        out_json.FIELD_VECTOR_INL(org,3,
                "RC","aux_intro",dat,0);

        out_json.FIELD_RSx(dat,objDwgData.auxheader.dwg_version,"dwg_version",0);
        out_json.FIELD_RSx(dat,objDwgData.auxheader.maint_version,"maint_version",0);
        out_json.FIELD_RL("numsaves",objDwgData.auxheader.numsaves,dat,0);
        out_json.FIELD_RLd("minus_1",objDwgData.auxheader.minus_1 & 0xFFFFFFFFL,dat,0);
        out_json.FIELD_RS(dat,objDwgData.auxheader.numsaves_1,"numsaves_1",0);
        out_json.FIELD_RS(dat,objDwgData.auxheader.numsaves_2,"numsaves_2",0);
        out_json.FIELD_RL("zero",objDwgData.auxheader.zero,dat,0);
        out_json.FIELD_RS(dat,objDwgData.auxheader.dwg_version_1,"dwg_version_1",0);
        if(commen.UNTIL(DWG_VERSION_TYPE.R_2013,dat))
        {
            out_json.FIELD_CAST(dat,objDwgData.auxheader.maint_version_1,"maint_version_1","RLx",0);
        }
        else {
            out_json.FIELD_RLx(dat,objDwgData.auxheader.maint_version_1,"maint_version_1",0);
        }
        out_json.FIELD_RS(dat,objDwgData.auxheader.dwg_version_2,"dwg_version_2",0);
        if(commen.UNTIL(DWG_VERSION_TYPE.R_2013,dat))
        {
            out_json.FIELD_CAST(dat,objDwgData.auxheader.maint_version_2,"maint_version_2","RLx",0);
        }
        else {
            out_json.FIELD_RLx(dat,objDwgData.auxheader.maint_version_2,"maint_version_2",0);
        }

        Object[] u6rs = new Object[6];
        for(int i = 0; i < 6; i++)
        {
            u6rs[i] = (Object) objDwgData.auxheader.unknown_6rs[i];
        }

        out_json.FIELD_VECTOR_INL(u6rs,6,"RS","unknown_6rs",dat,0);

        Object[] u5rl = new Object[5];
        for(int i = 0; i < 5; i++)
        {
            u5rl[i] = (Object) objDwgData.auxheader.unknown_5rl[i];
        }
        out_json.FIELD_VECTOR_INL(u5rl,5,"RL","unknown_5rl",dat,0);
        out_json.FIELD_TIMERLL(dat,"TDCREATE",objDwgData.auxheader.TDCREATE,0);
        out_json.FIELD_TIMERLL(dat,"TDUPDATE",objDwgData.auxheader.TDUPDATE,0);
        out_json.FIELD_RLx(dat,objDwgData.auxheader.HANDSEED,"HANDSEED",0);
        out_json.FIELD_RL("plot_stamp",objDwgData.auxheader.plot_stamp,dat,0);
        out_json.FIELD_RS(dat,objDwgData.auxheader.zero_1,"zero_1",0);
        out_json.FIELD_RS(dat,objDwgData.auxheader.numsaves_3,"numsaves_3",0);
        out_json.FIELD_RL("zero_2",objDwgData.auxheader.zero_2,dat,0);
        out_json.FIELD_RL("zero_3",objDwgData.auxheader.zero_3,dat,0);
        out_json.FIELD_RL("zero_4",objDwgData.auxheader.zero_4,dat,0);
        out_json.FIELD_RL("numsaves_4",objDwgData.auxheader.numsaves_4,dat,0);
        out_json.FIELD_RL("zero_5",objDwgData.auxheader.zero_5,dat,0);
        out_json.FIELD_RL("zero_6",objDwgData.auxheader.zero_6,dat,0);
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004,dat))
        {
            out_json.FIELD_RL("zero_7",objDwgData.auxheader.zero_7,dat,0);
            out_json.FIELD_RL("zero_8",objDwgData.auxheader.zero_8,dat,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2018,dat))
        {
            Object[] zero_8 = new Object[5];
            for(int i = 0; i < 5; i++)
            {
                zero_8[i] = (Object) objDwgData.auxheader.zero_18[i];
            }
            out_json.FIELD_VECTOR_INL(zero_8,3,"RS","zero_18",dat,0);
        }
    }
}
