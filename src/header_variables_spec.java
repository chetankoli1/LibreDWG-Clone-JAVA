import java.io.IOException;

public class header_variables_spec {

    public static void header_variables_spec_read(Bit_Chain dat, Bit_Chain hdlDat, Bit_Chain strDat, Dwg_Data objDwgData) {

        Dwg_Header_Variables headerVars = objDwgData.header_vars;
        if(commen.SINCE(DWG_VERSION_TYPE.R_2013b,dat))
        {
            objDwgData.header_vars.REQUIREDVERSIONS = dec_macros.FIELD_BLL(dat,"BLL",160);
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            if(specs.IF_ENCODE_FROM_EARLIER_OR_DXF)
            {
                headerVars.unit1_ratio = 412148564080.0;
                headerVars.unit2_ratio = 1.0;
                headerVars.unit3_ratio = 1.0;
                headerVars.unit4_ratio = 1.0;
            }

            headerVars.unit1_ratio = dec_macros.FIELD_BD(dat,"BD",0);
            headerVars.unit2_ratio = dec_macros.FIELD_BD(dat,"BD",0);
            headerVars.unit3_ratio = dec_macros.FIELD_BD(dat,"BD",0);
            headerVars.unit4_ratio = dec_macros.FIELD_BD(dat,"BD",0);
        }
    }

    static int header_variables_spec_write(Bit_Chain dat,  Dwg_Data objDwgData) throws IOException {
        int error = 0;
        Dwg_Header_Variables headerVars = objDwgData.header_vars;
        Dwg_Object obj = null;
        char[] buf = new char[2096];
        double ms = 0.0;
        String codepage = (objDwgData.header.codepage == 30 || objDwgData.header.codepage == 0)
                ? "ANSI_1252"
                : (objDwgData.header.version.ordinal() >= DWG_VERSION_TYPE.R_2007.ordinal())
                ? "UTF-8" : "ANSI_1252";


        if(commen.SINCE(DWG_VERSION_TYPE.R_2013b,dat))
        {
            out_json.FIELD_RLL(dat,"REQUIREDVERSIONS",headerVars.REQUIREDVERSIONS,160);
        }


        return error;
    }
}
