import java.io.IOException;

public class common_entity_data_spec {
    static void common_entity_data_spec_read(Bit_Chain dat, Bit_Chain hdl_dat, Bit_Chain str_dat, Dwg_Object_Entity ent,
                                             Dwg_Object obj, Dwg_Data objDwgData)
    {
        if(commen.PRE(DWG_VERSION_TYPE.R_2_0b,dat))
        {
            ent.layer = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,2,8);
        }
        else {
            if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
            {

            }

        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            ent.preview_exists = (char)bits.bit_read_B(dat);
            if(ent.preview_exists != 0)
            {

            }
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {

        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            ent.entmode = (char)dec_macros.FIELD_BB(dat,"BB",0);
            ent.num_reactor = (int)dec_macros.FIELD_BL(dat,"BL",0);
        }
        if (commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {

        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004,dat))
        {

        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_2002,dat))
        {
            ent.nolinks = dec_macros.FIELD_B(dat,"B",0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2013,dat))
        {

        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {

        }
        else{
            if(specs.DXF)
            {

            }
            else {
                ent.color = dec_macros.FIELD_CMC(dat,str_dat,62);
            }
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            ent.ltype_scale = dec_macros.FIELD_BD(dat,"BD",48);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000,dat))
        {
            ent.ltype_flags = (char)dec_macros.FIELD_BB(dat,"BB",0);
            ent.plotstyle_flags = (char)dec_macros.FIELD_BB(dat,"BB",0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {

        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2010b,dat))
        {

        }
        if(specs.DXF)
        {

        }else{
            if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
            {
                ent.invisible  = dec_macros.FIELD_BS(dat,"BS",0);
            }
            if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
            {
                ent.linewt = (int)dec_macros.FIELD_RC(dat,"RC",370);
            }
        }
    }

    static int common_entity_data_spec_write(Bit_Chain dat, Bit_Chain hdl_dat,
                                             Dwg_Object_Entity ent, Dwg_Object obj,
                                             Dwg_Data objDwgData) throws IOException {
        int error = 0;
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_B(dat,"preview_exists",ent.preview_exists,0);
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {

        }
        out_json.FIELD_BB(dat,"entmode",ent.entmode,0);
        out_json.FIELD_BL(dat,"num_reactor",ent.num_reactor,0);
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {

        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004,dat))
        {
            out_json.FIELD_B(dat,"is_xdic_missing",ent.is_xdic_missing,0);
        }
        if(commen.PRE(DWG_VERSION_TYPE.R_2004,dat))
        {
            out_json.FIELD_B(dat,"nolinks",ent.nolinks,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2013,dat))
        {

        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004,dat))
        {

        }
        else {
            out_json.FIELD_CMC(dat,"color",ent.color,62);
        }
        if(!specs.DXF)
        {
            out_json.FIELD_BD(dat,"ltype_scale",ent.ltype_scale,48);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000,dat)){
            out_json.FIELD_BB(dat,"ltype_flags",ent.ltype_flags,0);
            out_json.FIELD_BB(dat,"plotstyle_flags",ent.plotstyle_flags,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007,dat))
        {

        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2010,dat))
        {

        }

        if(specs.DXF)
        {

        }else{
            out_json.FIELD_RC("invisible",(char)ent.invisible,dat,60);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000,dat))
        {
            if(!macros.IS_DXF)
            {
                out_json.FIELD_RC("linewt",(char)ent.linewt,dat,370);
            }
        }

        return error;
    }
}
