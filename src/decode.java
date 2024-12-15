public class decode {

    public static int dwg_decode(Bit_Chain dat, Dwg_Data objDwgData) {
        int error = 0;
        dat._byte = 0;
        dat.bit = 0;
        if(dat.chain == null || dat.size < 50)
        {
            return 1;
        }
        String s = new String(dat.chain,0,6);
        objDwgData.header.from_version = DWG_VERSION_TYPE.R_INVALID;
        for(int i = 0; i < DWG_VERSION_TYPE.R_AFTER.ordinal(); i++)
        {
            String temp_version = new String(dat.chain,0,6);
            if(temp_version.equals(commen.version_codes[i]))
            {
                objDwgData.header.from_version = DWG_VERSION_TYPE.values()[i];
                break;
            }
        }

        if(objDwgData.header.from_version.ordinal() < DWG_VERSION_TYPE.R_INVALID.ordinal())
        {
            if(s.substring(2,4).equals("AC"))
            {
                System.out.println("Invalid DWG"+s);
            }else{
                System.out.println("invalid unimplementd version");
            }
            return 1;
        }
        int irs = 889;
        dat.from_version = objDwgData.header.from_version;
        if(objDwgData.header.version.ordinal() == DWG_VERSION_TYPE.R_INVALID.ordinal())
        {
            dat.version = objDwgData.header.version = dat.from_version;
        }

        dat._byte = 0xb;
        error = decode_R13_R2000(dat,objDwgData);
        irs = 889;
        return 0;
    }

    private static int decode_R13_R2000(Bit_Chain dat, Dwg_Data objDwgData) {
        int error = 0;
        Dwg_Header obj = objDwgData.header;
        dat._byte = 0x06;
        header_spec_read(dat,objDwgData);
        return error;
    }

    private static void header_spec_read(Bit_Chain dat, Dwg_Data objDwgData) {
        objDwgData.header.zero_5 = new char[5];
        for(int i =0; i< 5; i++)
        {
            objDwgData.header.zero_5[i] = bits.bit_read_RC(dat);
        }
        objDwgData.header.is_maint = bits.bit_read_RC(dat);
        objDwgData.header.zero_one_or_three = bits.bit_read_RC(dat);
        int ers = 99;
    }
}
