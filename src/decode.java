import java.lang.reflect.Array;
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
        int section_size = 0;
        int crc, crc2;
        Dwg_Object obj = null;
        long size;
        long endpos;
        long lastmap;
        long startpos;
        long object_begin;
        long object_end;
        long pvz = 0;
        int sentinel_size = 16;
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
        crc2 = 0;
        if(objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_HEADER_R13.value].size > 34
        && objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_HEADER_R13.value].size < 0xfff
        && pvz < dat._byte
        && pvz + objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_HEADER_R13.value].size < dat.size)
        {
            long crc_size = objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_HEADER_R13.value].size - 34;
            crc2 = bits.bit_calc_CRC(0xC0C1,(int)pvz, dat.chain, crc_size);
        }
        if(crc != crc2)
        {
            error |= DWG_ERROR.DWG_ERR_WRONGCRC.value;

        }

        /*-------------------------------------------------------------------------
         * Classes, section 1
         */
        dat._byte = objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_CLASSES_R13.value].address;
        if(dat._byte + 16 > dat.size || objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_CLASSES_R13.value].address
            + objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_CLASSES_R13.value].size > dat.size)
        {
            System.out.print("Invalid Classes section, skipped");
            return DWG_ERROR.DWG_ERR_SECTIONNOTFOUND.value;
        }

        if(commonvar.memcmp(commen.dwg_sentinel(commen.DWG_SENTINEL.DWG_SENTINEL_CLASS_BEGIN),dat.chain, dat._byte,16) == 0)
        {
            dat._byte += 16;
        }else{
            sentinel_size = 0;
        }
        dat.bit = 0;
        size = bits.bit_read_RL(dat);
        if(size != objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_CLASSES_R13.value].size
                - ((sentinel_size * 2) + 6))
        {
            endpos = objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_CLASSES_R13.value].address
                    + objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_CLASSES_R13.value].size - sentinel_size;
            System.out.print("Invalid size in classes section");
            return DWG_ERROR.DWG_ERR_SECTIONNOTFOUND.value;
        }
        else{
            endpos = dat._byte + size;
        }

        /* Read the classes
         */
        objDwgData.layout_type = 0;
        objDwgData.num_classes = 0;

        while (dat._byte < endpos - 1)
        {
            int i = 0;
            Dwg_Class klass = new Dwg_Class();
            i = objDwgData.num_classes;
            if( i == 0 )
            {
                objDwgData.dwg_class = new Dwg_Class[objDwgData.num_classes + 1];
                objDwgData.dwg_class[i] = new Dwg_Class();
            }
            else {
                objDwgData.dwg_class = Arrays.copyOf(objDwgData.dwg_class, i + 1);
                objDwgData.dwg_class[i] = new Dwg_Class();
            }
            klass = objDwgData.dwg_class[i];
            klass.number = bits.bit_read_BS(dat);
            klass.proxyflag = bits.bit_read_BS(dat);
            klass.appname = bits.bit_read_TV(dat).toCharArray();
            klass.cppname = bits.bit_read_TV(dat).toCharArray();
            klass.dxfname = bits.bit_read_TV(dat).toCharArray();
            klass.is_zombie = (char)bits.bit_read_B(dat);
            klass.item_class_id = bits.bit_read_BS(dat);

            if (new String(klass.dxfname).equals("LAYOUT" ) && klass.dxfname != null)
            {
                objDwgData.layout_type = klass.number;
            }

            objDwgData.num_classes++;
        }
        dat._byte = objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_CLASSES_R13.value].address
                + objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_CLASSES_R13.value].size - 18;
        dat.bit = 0;
        pvz = objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_CLASSES_R13.value].address + 16;
        if(bits.bit_check_CRC(dat,pvz,0xC0C1) == 0)
            error |= DWG_ERROR.DWG_ERR_WRONGCRC.value;
        dat._byte += 16;
        pvz = bits.bit_read_RL(dat);

        /*-------------------------------------------------------------------------
         * Object-map, section 2
         */
        dat._byte = objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_HANDLES_R13.value].address;
        dat.bit = 0;

        lastmap = dat._byte + objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_HANDLES_R13.value].size;
        objDwgData.num_objects = 0;
        object_begin = dat.size;
        object_end = 0;

        do{
            long last_handle = 0;
            long last_offset = 0;
            long oldpos = 0;
            long maxh = (long) objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_HANDLES_R13.value].size << 1;
            long max_handles = maxh < IntCommon.INT32_MAX ?
                    maxh :
                    objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_HANDLES_R13.value].size;
            int added = 0;
            pvz = dat._byte;

            startpos = dat._byte;
            section_size = bits.bit_read_RS_BE(dat);

            if(section_size > 2040)
            {
                System.out.print("Object-map section size greater than 2040!");
                return DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value;
            }

            while (dat._byte - startpos < section_size)
            {
                int prevsize = 0;
                long handleoff = 0;
                int offset = 0;

                oldpos = dat._byte;
                handleoff = bits.bit_read_UMC(dat);

                offset = bits.bit_read_MC(dat);

                prevsize = objDwgData.num_objects != 0 ?
                        objDwgData.object[objDwgData.num_objects - 1].size + 4 : 0;

                if((handleoff == 0) || (handleoff > (max_handles - last_handle))
                || (offset > -4 && offset < prevsize - 8))
                {
//                    if (offset == prevsize)
//                        LOG_WARN ("handleoff " FORMAT_UMC
//                                " looks wrong, max_handles %x - "
//                                "last_handle " FORMAT_RLLx " = " FORMAT_RLLx
//                                " (@%" PRIuSIZE ")",
//                                handleoff, (unsigned)max_handles, last_handle,
//                                max_handles - last_handle, oldpos);
                    if(offset == 1 || (offset > 0 && offset < prevsize && prevsize > 0) ||
                            (offset < 0 && commen.labs(offset) < prevsize && prevsize > 0))
                    {
//                        if (offset != prevsize)
//                            LOG_WARN ("offset " FORMAT_MC
//                                    " looks wrong, should be prevsize " FORMAT_MC
//                                    " + 4",
//                                    offset, prevsize - 4);
//                        // handleoff = 1;
//                        // offset = prevsize;
//                        // LOG_WARN ("Recover invalid handleoff to %" PRIuSIZE " and
//                        // offset to %ld",
//                        //           handleoff, offset);
                    }
                }
                last_offset += offset;
                if(dat._byte == oldpos)
                {
                    break;
                }
                if(object_end < last_offset)
                    object_end = last_offset;
                if(object_begin > last_offset)
                    object_begin = last_offset;

                added = dwg_decode_add_object(objDwgData,dat,dat,last_offset);

                if(added > 0)
                {
                    error |= added;
                    System.out.print("OBJECT OR ENTITY NOT ADDED!");
                }
                if(objDwgData.num_objects != 0)
                {
                    last_handle = objDwgData.object[objDwgData.num_objects - 1].handle.value;
                }
            }
            if(dat._byte == oldpos)
            {
                break;
            }

            //CRC on
            if(dat.bit > 0)
            {
                dat._byte += 1;
                dat.bit = 0;
            }

            if(dat._byte >= dat.size)
            {
                return DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value;
            }
            crc = bits.bit_read_RS_BE(dat);
            pvz = dat._byte;
            crc2 = bits.bit_calc_CRC(0xC0C1,0,dat.chain,section_size);
            if(crc != crc2)
            {
                if(dat.from_version.ordinal() != DWG_VERSION_TYPE.R_14.ordinal())
                {
                    error |= DWG_ERROR.DWG_ERR_WRONGCRC.value;
                }
            }
            if(dat._byte >= lastmap)
            {
                break;
            }
        }
        while (section_size > 2);

        if(object_end <= dat.size)
        {
            dat._byte = object_end;
        }
        object_begin = bits.bit_read_MC(dat);

        //this line is need to remove when objects are completed
        pvz = objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_OBJFREESPACE_R13.value].address;
        /*-------------------------------------------------------------------------
         * Section 2: ObjFreeSpace, r13c3-r2000
         */

        if(objDwgData.header.sections > 3 &&
                (objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_OBJFREESPACE_R13.value].address == pvz))
        {
            dat._byte = objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_OBJFREESPACE_R13.value].address;
            dat.bit = 0;

            error |= objfreespace_private(dat,objDwgData);
        }

        /*-------------------------------------------------------------------------
         * Second header, r13-r2000 only. With sentinels.
         */
        if(bits.bit_search_sentinel(dat,commen.dwg_sentinel(commen.DWG_SENTINEL.DWG_SENTINEL_2NDHEADER_BEGIN)) != 0)
        {
            Dwg_SecondHeader _obj = objDwgData.secondheader;
            String[] names = {
                    "HANDSEED",
                    "BLOCK_CONTROL_OBJECT",
                    "LAYER_CONTROL_OBJECT",
                    "STYLE_CONTROL_OBJECT",
                    "LTYPE_CONTROL_OBJECT",
                    "VIEW_CONTROL_OBJECT",
                    "UCS_CONTROL_OBJECT",
                    "VPORT_CONTROL_OBJECT",
                    "APPID_CONTROL_OBJECT",
                    "DIMSTYLE_CONTROL_OBJECT",
                    "VX_CONTROL_OBJECT",
                    "DICTIONARY_NAMED_OBJECT",
                    "DICTIONARY_ACAD_MLINESTYLE",
                    "DICTIONARY_ACAD_GROUP"
            };
            _obj.handles = new Dwg_SecondHeader_Handles[names.length];
            for(int i  = 0; i < names.length; i++)
            {
                _obj.handles[i] = new Dwg_SecondHeader_Handles();
                _obj.handles[i].name = names[i];
            }

            error = secondheader_private(dat,objDwgData,_obj);
            if(bits.bit_search_sentinel(dat,
                    commen.dwg_sentinel(commen.DWG_SENTINEL.DWG_SENTINEL_2NDHEADER_END)) != 0)
            {

            }

            /*-------------------------------------------------------------------------
             * Section 4: Template (with MEASUREMENT)
             * (Called PADDING section in the ODA)
             */

            if(objDwgData.header.sections > 4)
            {
                dat._byte = objDwgData.header.section[DWG_SECTION_TYPE_R13.SECTION_TEMPLATE_R13.value].address;
                dat.bit = 0;
                error |= template_private(dat,objDwgData);
            }
        }
       // error |= resolve_objectref_vector(dat,objDwgData);
        return error;
    }



    static int resolve_objectref_vector(Bit_Chain dat, Dwg_Data objDwgData) {
       for(int i = 0; i < objDwgData.num_object_refs; i++)
       {
           Dwg_Object_Ref ref = objDwgData.object_ref[i];

           assert ref.handleref.is_global == 1;
           Dwg_Object obj = dwg_resolve_handle(objDwgData,ref.absolute_ref);
           if(obj == null)
           {
           }
           ref.obj = obj;

           objDwgData.dirty_refs = 0;

       }
        return objDwgData.num_object_refs != 0 ? 0 : DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value;
    }

    static Dwg_Object dwg_resolve_handle(Dwg_Data objDwgData, long absref) {
        if(absref == 0)
        {
            return null;
        }
        loglevel = objDwgData.opts & dwg.DWG_OPTS_LOGLEVEL;
        objDwgData.object_map = new dwg_inthash();
        long i = hash.hash_get(objDwgData.object_map, absref);
        if(i != hash.HASH_NOT_FOUND)
        {
            //LOG_HANDLE ("[object_map{" FORMAT_RLLx "} => " FORMAT_BLL "] ", absref, i);
        }
        if(i != hash.HASH_NOT_FOUND || i >= objDwgData.num_objects)
        {
//            // ignore warning on invalid handles. These are warned earlier already
//            if (absref && dwg->header_vars.HANDSEED
//                    && absref < dwg->header_vars.HANDSEED->absolute_ref)
//            {
//                LOG_WARN ("Object handle not found " FORMAT_BLL "/" FORMAT_RLL
//                        " in " FORMAT_BL " objects of max " FORMAT_RLL " handles",
//                        absref, absref, dwg->num_objects,
//                        dwg->header_vars.HANDSEED->absolute_ref);
//            }
//      else
//            {
//                LOG_WARN ("Object handle not found " FORMAT_BLL "/" FORMAT_RLL,
//                        absref, absref);
//            }
            return null;
        }
        return objDwgData.object[(int)i];
    }

    static int template_private(Bit_Chain dat, Dwg_Data objDwgData) {
        Bit_Chain str_dat = new Bit_Chain(dat);
        Dwg_Template _obj = objDwgData.template;
        Dwg_Object obj = null;
        int error = 0;

        error = template_spec.template_spec_read(dat,obj,objDwgData,_obj);
        objDwgData.header_vars.MEASUREMENT = _obj.MEASUREMENT;

        return error;
    }

    static int secondheader_private(Bit_Chain dat, Dwg_Data objDwgData,Dwg_SecondHeader _obj) {
        Bit_Chain str_dat = dat;
        Dwg_Object obj = new Dwg_Object();

        int error = 0;
        if(dat.chain == null || dat.size == 0)
        {
            return 1;
        }

        second_header_spec.second_header_spec_read(dat, obj, objDwgData, _obj);


        if(bits.bit_check_CRC(dat,_obj.address + 16,0xC0C1 ) == 0)
        {
            error |= DWG_ERROR.DWG_ERR_WRONGCRC.value;
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_14,DWG_VERSION_TYPE.R_2000,dat))
        {
            _obj.junk_r14 = bits.bit_read_RLL1(dat);
        }

        return error;
    }

    static int objfreespace_private(Bit_Chain dat, Dwg_Data objDwgData) {
        Dwg_Object obj = null;
        int error = 0;

        if(dat.chain == null || dat.size == 0)
        {
            return 1;
        }

        error = objfreespace_spec.objfreespace_spec_read(dat, obj, objDwgData);

        return error;
    }


    static int decode_R2004(Bit_Chain dat, Dwg_Data objDwgData)
    {
        return 0;
    }

    static int decode_R2007(Bit_Chain dat, Dwg_Data objDwgData){
        return 0;
    }

    static int dwg_decode_header_variables(Bit_Chain dat, Bit_Chain hdl_dat, Bit_Chain str_dat, Dwg_Data objDwgData){
        int error = 0;
        Dwg_Header_Variables _obj = objDwgData.header_vars;
        Dwg_Object obj = new Dwg_Object();

        header_variables_spec.header_variables_spec_read(dat,hdl_dat,str_dat,obj,objDwgData);


        return error;
    }


    static int dwg_decode_add_object(Dwg_Data objDwgData, Bit_Chain dat,
                                     Bit_Chain hdl_dat, long address) {
        int error = 0;
        long objpos = 0, restartpos = 0;
        Bit_Chain abs_dat = null;
        Dwg_Object obj = new Dwg_Object();
        int num = objDwgData.num_objects;
        int realloced = 0;

        abs_dat = new Bit_Chain(dat);

        dat._byte = address;
        dat.bit = 0;

        realloced = dwg_add_object(objDwgData);

        obj = objDwgData.object[num];

        if(dat._byte >= dat.size)
        {
            objDwgData.num_objects--;
            dat = new Bit_Chain(abs_dat);
            return DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value;
        }

        obj.size = bits.bit_read_MS(dat);
        if(commen.SINCE(DWG_VERSION_TYPE.R_2010b,dat))
        {
            obj.handlestream_size = bits.bit_read_UMC(dat);
            obj.bitsize = (int)(obj.size * 8 - obj.handlestream_size);
        }
        objpos = bits.bit_position(dat);
        obj.address = dat._byte;

        bits.bit_reset_chain(dat);
        if (obj.size > dat.size || dat.size > abs_dat.size
                || (dat.size >= 0 && dat.size < dat.chain.length)
                || (abs_dat.size >= 0 && abs_dat.size < abs_dat.chain.length))
        {
            objDwgData.num_objects--;
            error |= DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value;

            if (true) {
                obj.size = (int)dat.size - 1;
                dat.chain = abs_dat.chain.clone();
                dat.size = abs_dat.size;
                return error;
            }
        }
        dat.size = obj.size;
        if(commen.SINCE(DWG_VERSION_TYPE.R_2010b,dat))
        {
            //obj.type = bits.bit_read_BOT(dat);
        }
        else {
            obj.type = bits.bit_read_BS(dat);
        }
        restartpos = bits.bit_position(dat);

        DWG_OBJECT_TYPE type = DWG_OBJECT_TYPE.fromValue(obj.type);
        switch (type)
        {
            case DWG_OBJECT_TYPE.DWG_TYPE_BLOCK_CONTROL:
                error = dwg_spec.dwg_decode_BLOCK_CONTROL ("BLOCK_CONTROL",obj,dat,objDwgData,DWG_OBJECT_TYPE.DWG_TYPE_BLOCK_CONTROL);
                if(error == 0 && obj.tio.object.tio.BLOCK_CONTROL != null)
                {
                    objDwgData.block_control = new Dwg_Object_BLOCK_CONTROL();
                    if(objDwgData.block_control.common.parent == null)
                    {
                        objDwgData.block_control = obj.tio.object.tio.BLOCK_CONTROL;
                    }
                    else{
                        System.out.print("Warning: Second BLOCK_CONTROL object ignored");
                    }
                }
                break;

        }
        if(obj.handle.value != 0)
        {
            //hash.hash_set(objDwgData.object_map,obj.handle.value,num);
        }
        if(dat._byte > 8 * dat.size)
        {
            dat = new Bit_Chain(abs_dat);
            return error |= DWG_ERROR.DWG_ERR_INVALIDDWG.value;
        }

        restartpos = bits.bit_position(dat);
        dat = new Bit_Chain(abs_dat);
        bits.bit_set_position(dat,objpos + restartpos);

        if(dat.bit != 0)
        {
            int r = 8 - dat.bit;
            bits.bit_advance_position(dat,r);
        }
        bits.bit_set_position(dat,(obj.address + obj.size) * 8 - 2);
        if(bits.bit_check_CRC(dat,address,0xC0C1) == 0)
        {
            return error |= DWG_ERROR.DWG_ERR_WRONGCRC.value;
        }
        dat = new Bit_Chain(abs_dat);
        return realloced != 0 ? -1 : error;
    }

    static int dwg_add_object(Dwg_Data objDwgData) {
        Dwg_Object obj = new Dwg_Object();
        int num = objDwgData.num_objects;
        int realloced = 0;
        loglevel = objDwgData.opts & dwg.DWG_OPTS_LOGLEVEL;
        if(num > 0 && objDwgData.num_alloced_objects == 0)
        {
            objDwgData.num_alloced_objects = num;
        }
        if(num == 0 && objDwgData.object == null)
        {
            objDwgData.object = new Dwg_Object[1024];
            objDwgData.num_alloced_objects = 1024;
            objDwgData.dirty_refs = 0;
        }
        else if (num >= objDwgData.num_alloced_objects) {
            Dwg_Object[] old = objDwgData.object;
            if (objDwgData.num_alloced_objects == 0) {
                objDwgData.num_alloced_objects = 1;
            }
            while (num >= objDwgData.num_alloced_objects) {
                objDwgData.num_alloced_objects *= 2;
            }

            // Reallocate the array
            Dwg_Object[] newArray = new Dwg_Object[objDwgData.num_alloced_objects];
            System.arraycopy(objDwgData.object, 0, newArray, 0, objDwgData.object.length);

            objDwgData.object = newArray;
            realloced = old != objDwgData.object ? 1 : 0;

            if (realloced != 0) {
                objDwgData.dirty_refs = 1;
            }
        }
        if (objDwgData.object == null) {
            return DWG_ERROR.DWG_ERR_OUTOFMEM.value;
        }
        obj = objDwgData.object[num];
        obj = new Dwg_Object();
        objDwgData.object[num] = obj;
        obj.index = num;
        objDwgData.num_objects++;
        obj.parent = objDwgData;
        return realloced != 0 ? -1 : 0;
    }

    static int dwg_decode_object(Bit_Chain dat, Bit_Chain hdl_dat,
                                 Bit_Chain str_dat, Dwg_Object_Object _obj)
    {
        int i = 0; int error = 0;
        Dwg_Data dwgData = _obj.dwg;
        Dwg_Object obj = dwgData.object[_obj.objid];
        long objectpos = bits.bit_position(dat);
        int has_wrong_bitsize = 0;
        int vcount = 0;

        obj.bitsize_pos = objectpos;
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_2000,DWG_VERSION_TYPE.R_2007,dat))
        {
            obj.bitsize = (int)bits.bit_read_RL(dat);
            if(obj.bitsize > (obj.size * 8))
            {
                obj.bitsize = obj.size * 8;
                has_wrong_bitsize = 1;
                error |= DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value;
            }
            else {
                error |= obj_handle_stream(dat,obj,hdl_dat);
            }
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007,dat))
        {
//            SINCE (R_2010b)
//            {
//                LOG_HANDLE (" bitsize: " FORMAT_RL ",", obj->bitsize);
//            }
            if(obj.bitsize > obj.size * 8)
            {
                obj.bitsize = obj.size * 8;
                has_wrong_bitsize = 1;
                error |= DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value;
            }
            if(commen.SINCE(DWG_VERSION_TYPE.R_2010b,dat))
            {
                error |= obj_handle_stream(dat,obj,hdl_dat);
            }

            DWG_OBJECT_TYPE mType = DWG_OBJECT_TYPE.fromValue(obj.type);
            if (mType != null && (obj.type >= 500 || obj_has_strings(mType) != 0)) {
                error |= obj_string_stream(dat, obj, str_dat);
            }
            else{
                str_dat.chain = Arrays.copyOfRange(str_dat.chain, (int) str_dat._byte, str_dat.chain.length);
                str_dat._byte = 0;
                str_dat.bit = 0;
                bits.bit_advance_position(str_dat,obj.bitsize - 1 -8);
                str_dat.size = 0;
            }
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            error |= bits.bit_read_H(dat,obj.handle);
            if((error & DWG_ERROR.DWG_ERR_INVALIDHANDLE.value) != 0 || obj.handle.value == 0
            || obj.handle.size == 0 || obj.handle.code != 0)
            {
                if(has_wrong_bitsize != 0)
                {
                    obj.bitsize = 0;
                }
                obj.tio.object.num_eed = 0;
                return error |= DWG_ERROR.DWG_ERR_INVALIDHANDLE.value;
            }
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            if(has_wrong_bitsize != 0)
            {
                String myname = "chetan";
            }
            else {
                error |= dwg_decode_eed(dat,_obj);
            }
            if((error & (DWG_ERROR.DWG_ERR_INVALIDEED.value | DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value)) != 0)
            {
                return  0;
            }
        }

        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            obj.bitsize = (int)bits.bit_read_RL(dat);
            if(obj.bitsize > obj.size * 8)
            {
                obj.bitsize = obj.size * 8;
                has_wrong_bitsize = 1;
                error |= DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value;
            }
            else
                error |= obj_handle_stream(dat,obj,hdl_dat);
        }

        common_object_handle_data_spec.common_object_handle_data_spec_read(dat,hdl_dat,obj,dwgData);

        obj.comman_size = bits.bit_position(dat) - objectpos;

        return error;
    }

    static int dwg_decode_eed(Bit_Chain dat, Dwg_Object obj)
    {
        int error = 0;
        Dwg_Handle last_handle = null;
        Bit_Chain dat1 = new Bit_Chain();
        int i = 0, num_eed = obj.tio.object.num_eed;
        int size = 0;
        int last_size = 0;
        int new_size = 0;
        int did_raw = 0;
        //int need_recalc = does_cross_unicode_datversion(dat);

        bits.bit_chain_init(dat1, 1024);
        dat1.from_version = dat.from_version;
        dat1.version = dat.version;
        dat1.opts = dat.opts;

        //if()
        return error;
    }
    //pending
    static int dwg_decode_eed(Bit_Chain dat, Dwg_Object_Object obj)
    {
        int error = 0;
        int size = 0;
        int idx = 0;
        Dwg_Data objDwgData = obj.dwg;
        Dwg_Object _obj;
        long sav_byte = dat._byte;

        if(objDwgData == null)
        {
            return DWG_ERROR.DWG_ERR_INVALIDEED.value;
        }
        _obj = objDwgData.object[obj.objid];
        obj.num_eed = 0;

        while (true)
        {
            int j = 0;
            long end = 0;
            if(dat.from_version.ordinal() >= DWG_VERSION_TYPE.R_13b1.ordinal())
            {
                size  = bits.bit_read_BS(dat);
                if(size == 0)
                    break;
            }
            else {
                if(idx != 0)
                {
                    break;
                }
                size = bits.bit_read_RS(dat);
            }
            if(size > _obj.size || dat._byte == sav_byte)
            {
                obj.num_eed = idx;
                return DWG_ERROR.DWG_ERR_INVALIDEED.value;
            }
            obj.num_eed = idx + 1;
            if (idx != 0)
            {
                obj.eed = Arrays.copyOf(obj.eed, obj.num_eed);
                obj.eed[idx] = new Dwg_Eed();
            }
            else{
                obj.eed = new Dwg_Eed[1];
                obj.eed[0] = new Dwg_Eed();
            }
            obj.eed[idx].size = size;
            if(dat.from_version.ordinal() >= DWG_VERSION_TYPE.R_13b1.ordinal())
            {
                error |= bits.bit_read_H(dat,obj.eed[idx].handle);
                end = dat._byte + size;
                if(error != 0)
                {
                    obj.eed[idx].size = 0;
                    obj.num_eed--;
                    if (obj.num_eed == 0)
                    {
                       // dwg_free_eed(_obj);
                    }
                    dat._byte = end;
                    continue;
                }
//                else {
//                    LOG_TRACE ("EED[%u] handle: " FORMAT_H, idx,
//                            ARGS_H (obj->eed[idx].handle));
//                    LOG_RPOS;
//                    if (dat->byte >= dat->size)
//                    end = dat->byte;
//                    if (_obj->fixedtype == DWG_TYPE_MLEADERSTYLE)
//                    { // check for is_new_format: has extended data for APPID
//                        // “ACAD_MLEADERVER”
//                        Dwg_Object_Ref ref;
//                        ref.obj = NULL;
//                        ref.handleref = obj->eed[idx].handle;
//                        ref.absolute_ref = 0L;
//                        if (dwg_resolve_handleref (&ref, _obj))
//                        {
//                            Dwg_Object *appid
//                                    = dwg_get_first_object (dwg, DWG_TYPE_APPID_CONTROL);
//                            if (appid)
//                            {
//                                Dwg_Object_APPID_CONTROL *_appid
//                                        = appid->tio.object->tio.APPID_CONTROL;
//                                // search absref in APPID_CONTROL apps[]
//                                for (j = 0; j < _appid->num_entries; j++)
//                                {
//                                    if (_appid->entries && _appid->entries[j]
//                                            && _appid->entries[j]->absolute_ref
//                                        == ref.absolute_ref)
//                                    {
//                                        Dwg_Object_MLEADERSTYLE *mstyle
//                                                = obj->tio.MLEADERSTYLE;
//                                        // real value with code 70 follows
//                                        mstyle->class_version = 2;
//                                        LOG_TRACE (
//                                                "EED found ACAD_MLEADERVER " FORMAT_RLLx
//                                                "\n",
//                                                ref.absolute_ref);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
            }
            else {
                end = dat._byte + size;
            }
            sav_byte = dat._byte;
           // obj.eed[idx].raw = bits.bit_read_TF(dat,size);
            dat._byte = sav_byte;

            while (dat._byte < end)
            {

            }
        }
        return error;
    }

    static int obj_string_stream(Bit_Chain dat, Dwg_Object obj, Bit_Chain str_dat)
    {
        long data_size = 0;
        long start = obj.bitsize - 1;
        long old_size = str_dat.size;
        long old_byte = str_dat._byte;

        str_dat.size = (obj.bitsize / 8) + ((obj.bitsize % 8) != 0 ? 1 :0);
        bits.bit_set_position(str_dat,start);

        if(str_dat._byte > old_size - old_byte)
        {
            str_dat._byte = old_byte;
            str_dat.size = old_size;
            obj.has_strings = 0;
            obj.bitsize = obj.size * 8;
            return DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value;
        }
        obj.has_strings = (char)bits.bit_read_B(dat);
        if(obj.has_strings == 0)
        {
            if(obj.fixedtype == DWG_OBJECT_TYPE.DWG_TYPE_SCALE)
            {
                obj.has_strings = 1;
            }
            return 0;
        }
        bits.bit_advance_position(str_dat, -1);
        str_dat._byte -= 2;
        data_size = bits.bit_read_RS(dat);

        if((data_size & 0x8000) != 0)
        {
            int hi_size = 0;
            str_dat._byte -= 4;
            data_size &= 0x7FFF;
            hi_size = bits.bit_read_RS(dat);
            data_size |= (hi_size << 15);
        }
        else{
            //LOG_HANDLE ("\n");
        }
        str_dat._byte -= 2;
        if(data_size > obj.bitsize)
        {
            if(dat.from_version.ordinal() == DWG_VERSION_TYPE.R_2007.ordinal())
            {
                return 0;
            }
            obj.has_strings = 0;
            bits.bit_reset_chain(str_dat);
            return DWG_ERROR.DWG_ERR_NOTYETSUPPORTED.value;
        }
        if(data_size < obj.bitsize)
        {
            obj.stringstream_size = (char)data_size;
            bits.bit_advance_position(str_dat,-(int)data_size);
        }
        else {
            bits.bit_set_position(str_dat,0);
        }

        return 0;
    }

    static int obj_handle_stream(Bit_Chain dat, Dwg_Object obj, Bit_Chain hdl_dat)
    {
        long bit8 = obj.bitsize / 8;
        assert dat != hdl_dat;
        obj.hdlpos = obj.bitsize;
        hdl_dat._byte = bit8;
        hdl_dat.bit = (char)(obj.bitsize % 8);
        if(obj.handlestream_size == 0)
        {
            obj.handlestream_size = (obj.bitsize % 8) - obj.bitsize;
        }
        hdl_dat.size = obj.size;
        if (loglevel >= logging.DWG_LOGLEVEL_HANDLE)
        {
            long end = obj.bitsize + obj.handlestream_size;
        }
        return 0;
    }

    static int obj_has_strings (DWG_OBJECT_TYPE type)
    {
        switch (type)
        {
            case DWG_OBJECT_TYPE.DWG_TYPE_TEXT:
            case DWG_OBJECT_TYPE.DWG_TYPE_ATTRIB:
            case DWG_OBJECT_TYPE.DWG_TYPE_ATTDEF:
            case DWG_OBJECT_TYPE.DWG_TYPE_BLOCK:
                return 1;
            case DWG_OBJECT_TYPE.DWG_TYPE_ENDBLK:
            case DWG_OBJECT_TYPE.DWG_TYPE_SEQEND:
            case DWG_OBJECT_TYPE.DWG_TYPE_INSERT:
            case DWG_OBJECT_TYPE.DWG_TYPE_MINSERT:
            case DWG_OBJECT_TYPE.DWG_TYPE_VERTEX_2D:
            case DWG_OBJECT_TYPE.DWG_TYPE_VERTEX_3D:
            case DWG_OBJECT_TYPE.DWG_TYPE_VERTEX_MESH:
            case DWG_OBJECT_TYPE.DWG_TYPE_VERTEX_PFACE:
            case DWG_OBJECT_TYPE.DWG_TYPE_VERTEX_PFACE_FACE:
            case DWG_OBJECT_TYPE.DWG_TYPE_POLYLINE_2D:
            case DWG_OBJECT_TYPE.DWG_TYPE_POLYLINE_3D:
            case DWG_OBJECT_TYPE.DWG_TYPE_ARC:
            case DWG_OBJECT_TYPE.DWG_TYPE_CIRCLE:
            case DWG_OBJECT_TYPE.DWG_TYPE_LINE:
                return 0;
            case DWG_OBJECT_TYPE.DWG_TYPE_DIMENSION_ORDINATE:
            case DWG_OBJECT_TYPE.DWG_TYPE_DIMENSION_LINEAR:
            case DWG_OBJECT_TYPE.DWG_TYPE_DIMENSION_ALIGNED:
            case DWG_OBJECT_TYPE.DWG_TYPE_DIMENSION_ANG3PT:
            case DWG_OBJECT_TYPE.DWG_TYPE_DIMENSION_ANG2LN:
            case DWG_OBJECT_TYPE.DWG_TYPE_DIMENSION_RADIUS:
            case DWG_OBJECT_TYPE.DWG_TYPE_DIMENSION_DIAMETER:
                return 1;
            case DWG_OBJECT_TYPE.DWG_TYPE_POINT:
            case DWG_OBJECT_TYPE.DWG_TYPE__3DFACE:
            case DWG_OBJECT_TYPE.DWG_TYPE_POLYLINE_PFACE:
            case DWG_OBJECT_TYPE.DWG_TYPE_POLYLINE_MESH:
            case DWG_OBJECT_TYPE.DWG_TYPE_SOLID:
            case DWG_OBJECT_TYPE.DWG_TYPE_TRACE:
            case DWG_OBJECT_TYPE.DWG_TYPE_SHAPE:
                return 0;
            case DWG_OBJECT_TYPE.DWG_TYPE_VIEWPORT:
                return 1;
            case DWG_OBJECT_TYPE.DWG_TYPE_ELLIPSE:
            case DWG_OBJECT_TYPE.DWG_TYPE_SPLINE:
                return 0;
            case DWG_OBJECT_TYPE.DWG_TYPE_REGION:
            case DWG_OBJECT_TYPE.DWG_TYPE__3DSOLID:
            case DWG_OBJECT_TYPE.DWG_TYPE_BODY:
                return 1;
            case DWG_OBJECT_TYPE.DWG_TYPE_RAY:
            case DWG_OBJECT_TYPE.DWG_TYPE_XLINE:
                return 0;
            case DWG_OBJECT_TYPE.DWG_TYPE_DICTIONARY:
            case DWG_OBJECT_TYPE.DWG_TYPE_OLEFRAME:
            case DWG_OBJECT_TYPE.DWG_TYPE_MTEXT:
            case DWG_OBJECT_TYPE.DWG_TYPE_LEADER:
                return 1;
            case DWG_OBJECT_TYPE.DWG_TYPE_TOLERANCE:
            case DWG_OBJECT_TYPE.DWG_TYPE_MLINE:
                return 0;
            case DWG_OBJECT_TYPE.DWG_TYPE_BLOCK_CONTROL:
            case DWG_OBJECT_TYPE.DWG_TYPE_LAYER_CONTROL:
            case DWG_OBJECT_TYPE.DWG_TYPE_STYLE_CONTROL:
            case DWG_OBJECT_TYPE.DWG_TYPE_LTYPE_CONTROL:
            case DWG_OBJECT_TYPE.DWG_TYPE_VIEW_CONTROL:
            case DWG_OBJECT_TYPE.DWG_TYPE_UCS_CONTROL:
            case DWG_OBJECT_TYPE.DWG_TYPE_VPORT_CONTROL:
            case DWG_OBJECT_TYPE.DWG_TYPE_APPID_CONTROL:
            case DWG_OBJECT_TYPE.DWG_TYPE_DIMSTYLE_CONTROL:
            case DWG_OBJECT_TYPE.DWG_TYPE_VX_CONTROL:
                return 0;
            case DWG_OBJECT_TYPE.DWG_TYPE_BLOCK_HEADER:
            case DWG_OBJECT_TYPE.DWG_TYPE_LAYER:
            case DWG_OBJECT_TYPE.DWG_TYPE_STYLE:
            case DWG_OBJECT_TYPE.DWG_TYPE_LTYPE:
            case DWG_OBJECT_TYPE.DWG_TYPE_VIEW:
            case DWG_OBJECT_TYPE.DWG_TYPE_UCS:
            case DWG_OBJECT_TYPE.DWG_TYPE_VPORT:
            case DWG_OBJECT_TYPE.DWG_TYPE_APPID:
            case DWG_OBJECT_TYPE.DWG_TYPE_DIMSTYLE:
            case DWG_OBJECT_TYPE.DWG_TYPE_VX_TABLE_RECORD:
                return 1;
            case DWG_OBJECT_TYPE.DWG_TYPE_GROUP:
            case DWG_OBJECT_TYPE.DWG_TYPE_MLINESTYLE:
            case DWG_OBJECT_TYPE.DWG_TYPE_OLE2FRAME:
                return 1;
            case DWG_OBJECT_TYPE.DWG_TYPE_DUMMY:
            case DWG_OBJECT_TYPE.DWG_TYPE_LONG_TRANSACTION:
            case DWG_OBJECT_TYPE.DWG_TYPE_LWPOLYLINE:
                return 0;
            case DWG_OBJECT_TYPE.DWG_TYPE_HATCH:
            case DWG_OBJECT_TYPE.DWG_TYPE_XRECORD:
                return 1;
            case DWG_OBJECT_TYPE.DWG_TYPE_PLACEHOLDER:
                return 0;
            case DWG_OBJECT_TYPE.DWG_TYPE_VBA_PROJECT:
            case DWG_OBJECT_TYPE.DWG_TYPE_LAYOUT:
            case DWG_OBJECT_TYPE.DWG_TYPE_PROXY_ENTITY:
            case DWG_OBJECT_TYPE.DWG_TYPE_PROXY_OBJECT:
            default:
                return 1;
        }
    }
}
