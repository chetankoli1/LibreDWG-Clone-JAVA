import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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
            return DWG_ERROR.DWG_ERR_OUTOFMEM.value;
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
            return DWG_ERROR.DWG_ERR_INVALIDDWG.value;
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
            return DWG_ERROR.DWG_ERR_INVALIDDWG.value;
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
    public static int MAX_HEADER_SIZE = 2048;

    private static int decode_R13_R2000(Bit_Chain dat, Dwg_Data objDwgData) {
        int error = 0;
        int crc, crc2;
        Dwg_Object obj = null;
        long size;
        long endpos;
        long lastmap;
        long startpos;
        long object_begin;
        long object_end;
        long pvz = 0;
        String[] section_names =
        {
                "AcDb:Header","AcDb:Classes","AcDb:Handles","AcDb:ObjFreeSpace",
                "AcDb:Template","AcDb:AuxHeader"
        };

        {
            Dwg_Header _obj = objDwgData.header;
            Bit_Chain hdl_dat = new Bit_Chain(dat);
            assert dat._byte == 0xb;

            fileheader_spec.fileheader_spec_read(dat,hdl_dat,objDwgData);
        }

        if((error = dwg.dwg_section_init(objDwgData)) != 0)
        {
            return error;
        }
        if(dat._byte != 0x19)
        {
            return DWG_ERROR.DWG_ERR_INVALIDDWG.value;
        }

        assert dat._byte == 0x19;

        for(int j = 0; j < objDwgData.header.sections; j++)
        {
            objDwgData.header.section[j] = new Dwg_Section();
            objDwgData.header.section[j].number = (int)bits.bit_read_RC(dat);
            objDwgData.header.section[j].address = (long)bits.bit_read_RL(dat);
            objDwgData.header.section[j].size = (int)bits.bit_read_RL(dat);

            if(j < 6)
                objDwgData.header.section[j].name = section_names[j].toCharArray();

            if((objDwgData.header.section[j].address +
                    objDwgData.header.section[j].size) > dat.size)
            {
                return DWG_ERROR.DWG_ERR_INVALIDDWG.value;
            }
        }

        crc2 = bits.bit_calc_CRC(0xC0C1, 0, dat.chain, dat._byte);
        crc = bits.bit_read_RS (dat);

        if(crc != crc2)
        {
            return DWG_ERROR.DWG_ERR_WRONGCRC.value;
        }

        if(bits.bit_search_sentinel(dat, commen.dwg_sentinel(commen.DWG_SENTINEL.DWG_SENTINEL_HEADER_END)) == 0)
        {

        }
        /*-------------------------------------------------------------------------
         * Section 5 AuxHeader
         * R13c3+, mostly redundant file header information. no sentinels
         */

        if(objDwgData.header.num_sections == 6 && objDwgData.header.version.ordinal() >= DWG_VERSION_TYPE.R_13c3.ordinal())
        {
            objDwgData.auxheader = new Dwg_AuxHeader();
            Dwg_AuxHeader _obj = objDwgData.auxheader;
            Bit_Chain hdl_dat = new Bit_Chain(dat);
            long end_address = objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_AUXHEADER_R2000.value].address
                    + objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_AUXHEADER_R2000.value].size;

            obj = null;
            dat._byte = objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_AUXHEADER_R2000.value].address;

            if(dat.size < end_address)
            {
                return DWG_ERROR.DWG_ERR_SECTIONNOTFOUND.value;
            }
            else{
                long old_size = dat.size;
                dat.size = end_address;

                //
                error = auxheader_spec.auxheader_spec_read(dat,objDwgData);
                //

                dat.size = old_size;
            }
        }
        /*-------------------------------------------------------------------------
         * Thumbnail Image (pre-r13c3 before, since r13c3 at the end)
         */
        if(bits.bit_search_sentinel(dat,commen.dwg_sentinel(commen.DWG_SENTINEL.DWG_SENTINEL_THUMBNAIL_BEGIN)) != 0)
        {
            long start_address;
            dat.bit = 0;
            start_address = dat._byte;
            if(objDwgData.header.thumbnail_address == 0
                    && objDwgData.header.thumbnail_address != (dat._byte - 16))
            {
//                LOG_WARN ("Illegal header.thumbnail_address: %i != %" PRIuSIZE,
//                        dwg->header.thumbnail_address, dat->byte - 16)
            }
            objDwgData.header.thumbnail_address = (dat._byte - 16) & 0xFFFFFFFFL;
            if(bits.bit_search_sentinel(dat,commen.dwg_sentinel(commen.DWG_SENTINEL.DWG_SENTINEL_THUMBNAIL_END)) != 0)
            {
                long bmpsize = 0;
                if(dat._byte - 16 < start_address)
                {
//                    LOG_ERROR ("Illegal header.thumbnail_size: %" PRIuSIZE
//                            " < %" PRIuSIZE,
//                            dat->byte - 16, start_address);
                }
                else if((dat._byte - 16) - start_address < 10) {
//                    LOG_TRACE ("No header.thumbnail: %" PRIuSIZE " < 10",
//                            dat->byte - 16 - start_address);
                }
                else{
                    char type = '0';
                    assert  (dat._byte - 16) >= start_address;
                    objDwgData.thumbnail.size = (dat._byte - 16) - start_address;
                    objDwgData.thumbnail.chain = new char[(int)objDwgData.thumbnail.size];
                    objDwgData.thumbnail._byte = 0;
                    if(objDwgData.thumbnail.chain == null)
                    {
                        System.out.println("Out of memory");
                        return DWG_ERROR.DWG_ERR_OUTOFMEM.value;
                    }
//                    byte[] temp = new byte[dat.chain.length];
//                    String tempStr = new String(dat.chain);
//                    temp = tempStr.getBytes(StandardCharsets.UTF_8);
//                    objDwgData.thumbnail.chain = commen.memcpy(objDwgData.thumbnail.chain,start_address,dat.chain);
                    System.arraycopy(dat.chain, (int)start_address, objDwgData.thumbnail.chain, 0, (int)objDwgData.thumbnail.size);
                    dat._byte += (int)objDwgData.thumbnail.size;
                    dwg.DwgBumpData data = new dwg.DwgBumpData();
                    data = dwg.dwg_bmp(objDwgData);
                    bmpsize = data.size;
                    type = data.typep;
                    if(bmpsize > objDwgData.thumbnail.size)
                    {
                        /*LOG_ERROR ("thumbnail size overflow: %i > %" PRIuSIZE "\n",
                           bmpsize, dwg->thumbnail.size)*/
                    }
                }
            }
        }
        /*-------------------------------------------------------------------------
         * Header Variables, section 0
         */



        if(objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_HEADER_R13.value].address < 58
        || objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_HEADER_R13.value].address +
            objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_HEADER_R13.value].size > dat.size)
        {
            //LOG_ERROR ("Invalid Header section, skipped")
            return error = DWG_ERROR.DWG_ERR_SECTIONNOTFOUND.value;
        }
        dat._byte = pvz = objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_HEADER_R13.value].address + 16;
        objDwgData.header_vars = new Dwg_Header_Variables();
        objDwgData.header_vars.size = bits.bit_read_RL(dat);
        if(objDwgData.header_vars.size > MAX_HEADER_SIZE)
        {
            objDwgData.header_vars.size =
                    objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_HEADER_R13.value].size;
            if(objDwgData.header_vars.size > 20)
            {
                //LOG_WARN ("Fixup illegal Header Length");
                objDwgData.header_vars.size -= 16 + 4;
            }
        }
        dat.bit = '\0';

        error |= dwg_decode_header_variables(dat,dat,dat,objDwgData);

        if(objDwgData.header_vars.size < MAX_HEADER_SIZE)
        {
            long crcpos = pvz + objDwgData.header_vars.size + 4;
            if(dat.bit != '\0' || dat._byte != crcpos)
            {
                //unsigned char r = 8 - dat->bit;
                //LOG_HANDLE (" padding: %zd byte, %d bits\n", crcpos - dat->byte, r);
            }
            //LOG_HANDLE (" crc pos: %" PRIuSIZE "\n", crcpos);
            bits.bit_set_position(dat,crcpos * 8);
            crc = bits.bit_read_RS(dat);
        }
        else{
            return error |= DWG_ERROR.DWG_ERR_SECTIONNOTFOUND.value;
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

    private static int dwg_decode_header_variables(Bit_Chain dat, Bit_Chain hdl_dat, Bit_Chain str_dat, Dwg_Data objDwgData){
        int error = 0;
        Dwg_Header_Variables _obj = objDwgData.header_vars;
        Dwg_Object obj = new Dwg_Object();

        header_variables_spec.header_variables_spec_read(dat,hdl_dat,str_dat,objDwgData);


        return error;
    }


}
