import java.io.IOException;

public class objfreespace_spec {
    static int objfreespace_spec_read(Bit_Chain dat, Dwg_Object obj, Dwg_Data objDwgData) {
        int error = 0;
        Dwg_ObjFreeSpace objFreeSpace = objDwgData.objfreespace;

        if (specs.ENCODER) {
            if (objFreeSpace.numnums == 0) {
                objFreeSpace.numhandles = objDwgData.num_objects;

                objFreeSpace.TDUPDATE.ms = objDwgData.header_vars.TDUPDATE.ms;
                objFreeSpace.TDUPDATE.days = objDwgData.header_vars.TDUPDATE.days;
                objFreeSpace.TDUPDATE.value = objDwgData.header_vars.TDUPDATE.value;

                objFreeSpace.numnums = 4;
                objFreeSpace.max32 = 0x32;
                objFreeSpace.max64 = 0x64;
                objFreeSpace.maxtbl = 0x200;
                objFreeSpace.maxrl = 0xffffffffL;

            }
        }

        if (commen.UNTIL(DWG_VERSION_TYPE.R_2007, dat)) {
            objFreeSpace.zero = (long) dec_macros.FIELD_CAST(dat, "RL", 0);
            objFreeSpace.numhandles = (long) dec_macros.FIELD_CAST(dat, "RL", 0);
            objFreeSpace.TDUPDATE = dec_macros.FIELD_TIMERLL(dat, 0);
            objFreeSpace.objects_address = dec_macros.FIELD_RLx(dat, "RLx", 0);
            objFreeSpace.numnums = dec_macros.FIELD_RC(dat, "RC", 0);
            objFreeSpace.max32 = dec_macros.FIELD_RLL(dat, "RLL", 0);
            objFreeSpace.max64 = dec_macros.FIELD_RLL(dat, "RLL", 0);
            objFreeSpace.maxtbl = dec_macros.FIELD_RLL(dat, "RLL", 0);
            objFreeSpace.maxrl = dec_macros.FIELD_RLL(dat, "RLL", 0);
        } else {
            objFreeSpace.zero = dec_macros.FIELD_RLL(dat, "RLL", 0);
            objFreeSpace.numhandles = dec_macros.FIELD_RLL(dat, "RLL", 0);
            objFreeSpace.TDUPDATE = dec_macros.FIELD_TIMERLL(dat, 0);
            objFreeSpace.numnums = dec_macros.FIELD_RC(dat, "RC", 0);
            // num types are not 64 bit, but 128
            objFreeSpace.max32 = dec_macros.FIELD_RLL(dat, "RLL", 0);
            objFreeSpace.max32_hi = dec_macros.FIELD_RLL(dat, "RLL", 0);
            objFreeSpace.max64 = dec_macros.FIELD_RLL(dat, "RLL", 0);
            objFreeSpace.max64_hi = dec_macros.FIELD_RLL(dat, "RLL", 0);
            objFreeSpace.maxtbl = dec_macros.FIELD_RLL(dat, "RLL", 0);
            objFreeSpace.maxtbl_hi = dec_macros.FIELD_RLL(dat, "RLL", 0);
            objFreeSpace.maxrl = dec_macros.FIELD_RLL(dat, "RLL", 0);
            objFreeSpace.maxrl_hi = dec_macros.FIELD_RLL(dat, "RLL", 0);

        }

        return 0;
    }

    static int objfreespace_spec_write(Bit_Chain dat, Dwg_Object obj, Dwg_Data objDwgData)
            throws IOException {
        int error = 0;
        Dwg_ObjFreeSpace objFreeSpace = objDwgData.objfreespace;

        if(specs.ENCODER)
        {
            if(objFreeSpace.numnums == 0)
            {
                objFreeSpace.numhandles = objDwgData.num_objects;

                objFreeSpace.TDUPDATE.ms = objDwgData.header_vars.TDUPDATE.ms;
                objFreeSpace.TDUPDATE.days = objDwgData.header_vars.TDUPDATE.days;
                objFreeSpace.TDUPDATE.value = objDwgData.header_vars.TDUPDATE.value;

                objFreeSpace.numnums = 4;
                objFreeSpace.max32 = 0x32;
                objFreeSpace.max64 = 0x64;
                objFreeSpace.maxtbl = 0x200;
                objFreeSpace.maxrl = 0xffffffffL;

            }
        }

        if(commen.UNTIL(DWG_VERSION_TYPE.R_2007,dat))
        {
            out_json.FIELD_CAST(dat,objFreeSpace.zero,"zero","RL",0);
            out_json.FIELD_CAST(dat,objFreeSpace.numhandles,"numhandles","RL",0);
            out_json.FIELD_TIMERLL(dat,"TDUPDATE",objFreeSpace.TDUPDATE,0);
            out_json.FIELD_RLx(dat,objFreeSpace.objects_address,"objects_address",0);
            out_json.FIELD_RC("numnums",objFreeSpace.numnums,dat,0);
            out_json.FIELD_RLL(dat,"max32",objFreeSpace.max32,0);
            out_json.FIELD_RLL(dat,"max64",objFreeSpace.max64,0);
            out_json.FIELD_RLL(dat,"maxtbl",objFreeSpace.maxtbl,0);
            out_json.FIELD_RLL(dat,"maxrl",objFreeSpace.maxrl,0);
        }
        else{
            out_json.FIELD_RLL(dat,"zero",objFreeSpace.zero,0);
            out_json.FIELD_RLL(dat,"numhandles",objFreeSpace.numhandles,0);
            out_json.FIELD_TIMERLL(dat,"TDUPDATE",objFreeSpace.TDUPDATE,0);
            out_json.FIELD_RC("numnums",objFreeSpace.numnums,dat,0);
            out_json.FIELD_RLL(dat,"max32",objFreeSpace.max32,0);
            out_json.FIELD_RLL(dat,"max32_hi",objFreeSpace.max32_hi,0);
            out_json.FIELD_RLL(dat,"max64",objFreeSpace.max64,0);
            out_json.FIELD_RLL(dat,"max64_hi",objFreeSpace.max64_hi,0);
            out_json.FIELD_RLL(dat,"maxtbl",objFreeSpace.maxtbl,0);
            out_json.FIELD_RLL(dat,"maxtbl_hi",objFreeSpace.maxtbl_hi,0);
            out_json.FIELD_RLL(dat,"maxrl",objFreeSpace.maxrl,0);
            out_json.FIELD_RLL(dat,"maxrl_hi",objFreeSpace.maxrl_hi,0);
        }

        return 0;

    }
}
