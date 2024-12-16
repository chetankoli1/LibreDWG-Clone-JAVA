public class decode {

    static int loglevel;
    public static int dwg_decode(Bit_Chain dat, Dwg_Data objDwgData) {
        int error = 0;
        char[] magic = new char[11];
        objDwgData.num_object_refs = 0;
        objDwgData.num_entities = 0;
        objDwgData.num_objects = 0;
        objDwgData.num_classes = 0;
        objDwgData.thumbnail.size = 0;
        objDwgData.thumbnail.chain = null;
        objDwgData.header.num_sections = 0;
        objDwgData.header.section_infohdr.num_desc = 0;
        objDwgData.dwg_class = null;
        objDwgData.object_ref = null;
        objDwgData.object = null;
        objDwgData.object_map = hash.hash_new(1663131);



        if(objDwgData.object_map == null)
        {
            objDwgData.object_map = hash.hash_new(1024);
            System.out.println("out of memory");
            return DWG_ERROR.DWG_ERR_OUTOFMEM.getValue();
        }
        objDwgData.dirty_refs = 1;

        /*
        *memset (&dwg->header_vars, 0, sizeof (dwg->header_vars));
memset (&dwg->summaryinfo, 0, sizeof (dwg->summaryinfo));
memset (&dwg->fhdr.r2004_header, 0, sizeof (dwg->fhdr.r2004_header));
memset (&dwg->auxheader, 0, sizeof (dwg->auxheader));
memset (&dwg->secondheader, 0, sizeof (dwg->secondheader));
memset (&dwg->objfreespace, 0, sizeof (dwg->objfreespace));
        * */
        if(objDwgData.opts != 0)
        {
            loglevel = objDwgData.opts & dwg.DWG_OPTS_LOGLEVEL;
            dat.opts = (char)objDwgData.opts;
        }
        /*#ifdef USE_TRACING
        if (!env_var_checked_p)
        {
            char *probe = getenv ("LIBREDWG_TRACE");
            if (probe)
                loglevel = atoi (probe);
            env_var_checked_p = true;
        }
#endif*/
        dat._byte = 0;
        dat.bit = 0;
        if(dat.chain == null || dat.size < 58)
        {
            return DWG_ERROR.DWG_ERR_INVALIDDWG.getValue();
        }
        commen.strcpy(magic,dat.chain,11);
        magic[10] = '\0';
        String magic_str = new String(magic).trim();
        objDwgData.header.from_version = commen.dwg_version_hdr_type(magic_str);

        if(objDwgData.header.from_version.ordinal() == 0)
        {
            if(commen.strcmp(magic_str,"AC",2))
            {
                System.out.println("Invalid DWG, magic: %s "+magic_str);
            }else{
                System.out.println("Invalid or unimplemented DWG version code %s "+magic_str);
            }
            return DWG_ERROR.DWG_ERR_INVALIDDWG.getValue();
        }

        dat.from_version = objDwgData.header.from_version;
        if(objDwgData.header.version == null)
        {
            dat.version = objDwgData.header.version = dat.from_version;
        }
        dat._byte = 0xb;
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_2000,dat))
        {
            error = decode_R13_R2000(dat,objDwgData);
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_2004a,DWG_VERSION_TYPE.R_2004,dat))
        {
            error = decode_R2004(dat,objDwgData);
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_2007a,DWG_VERSION_TYPE.R_2007,dat))
        {
            error = decode_R2007(dat,objDwgData);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2010b,dat))
        {
            read_r2007_init (objDwgData);
            error = decode_R2004(dat,objDwgData);
        }
        return error;
    }

    private static void read_r2007_init(Dwg_Data objDwgData) {
        if(objDwgData.opts != 0)
        {
            loglevel = objDwgData.opts & dwg.DWG_OPTS_LOGLEVEL;
        }
    }


    private static int decode_R13_R2000(Bit_Chain dat, Dwg_Data objDwgData) {
        int error = 0;

        {
            Dwg_Header obj = objDwgData.header;
            Bit_Chain hdl_dat = new Bit_Chain(dat);
            assert dat._byte == 0xb;

            header_spec.header_spec_read(dat,hdl_dat,objDwgData);
        }

        return error;
    }

    private static int decode_R2004(Bit_Chain dat, Dwg_Data objDwgData)
    {
        return 0;
    }

    private static int decode_R2007(Bit_Chain dat, Dwg_Data objDwgData){
        return 0;
    }


}
