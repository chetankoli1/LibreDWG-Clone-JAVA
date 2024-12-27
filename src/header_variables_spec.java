import java.io.IOException;

public class header_variables_spec {

    public static void header_variables_spec_read(Bit_Chain dat, Bit_Chain hdl_dat, Bit_Chain str_dat,Dwg_Object obj, Dwg_Data objDwgData) {

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

            headerVars.unit1_name = dec_macros.FIELD_TV(dat,"TV",0);
            headerVars.unit2_name = dec_macros.FIELD_TV(dat,"TV",0);
            headerVars.unit3_name = dec_macros.FIELD_TV(dat,"TV",0);
            headerVars.unit4_name = dec_macros.FIELD_TV(dat,"TV",0);
        }
        if(specs.FREE)
        {
            headerVars.unit1_name = dec_macros.FIELD_TV(dat,"TV",0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            if(specs.IF_ENCODE_FROM_EARLIER_OR_DXF)
            {
                headerVars.unknown_8 = 24;
            }
            headerVars.unknown_8 = dec_macros.FIELD_BL(dat,"BL",0);
            headerVars.unknown_9 = dec_macros.FIELD_BL(dat,"BL",0);
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            headerVars.unknown_10 = dec_macros.FIELD_BS(dat,"BS",0);
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_2000,dat))
        {
            headerVars.VX_TABLE_RECORD = dec_macros.FIELD_HANDLE(dat,obj,objDwgData,5,0);
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            headerVars.DIMASO = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMSHO = dec_macros.FIELD_B(dat,"B",70);
        }

        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            headerVars.DIMSAV = dec_macros.FIELD_B(dat,"B",70);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            headerVars.PLINEGEN = dec_macros.FIELD_B(dat,"B",70);
            headerVars.ORTHOMODE = dec_macros.FIELD_B(dat,"B",70);
            headerVars.REGENMODE = dec_macros.FIELD_B(dat,"B",70);
            headerVars.FILLMODE = dec_macros.FIELD_B(dat,"B",70);
            headerVars.QTEXTMODE = dec_macros.FIELD_B(dat,"B",70);
            headerVars.PSLTSCALE = dec_macros.FIELD_B(dat,"B",70);
            headerVars.LIMCHECK = dec_macros.FIELD_B(dat,"B",70);
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            headerVars.BLIPMODE = dec_macros.FIELD_B(dat,"B",70);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat)){
            headerVars.unknown_11 = dec_macros.FIELD_B(dat,"B",0);
        }
        headerVars.USRTIMER = dec_macros.FIELD_B(dat,"B",70);
        headerVars.SKPOLY = dec_macros.FIELD_B(dat,"B",70);
        headerVars.ANGDIR = dec_macros.FIELD_B(dat,"B",70);
        headerVars.SPLFRAME = dec_macros.FIELD_B(dat,"B",70);

        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            if(specs.IF_ENCODE_FROM_EARLIER_OR_DXF)
            {
                headerVars.ATTREQ = 1;
                headerVars.ATTDIA = 1;
                headerVars.HANDLING = 1;
            }
            headerVars.ATTREQ = dec_macros.FIELD_B(dat,"B",70);
            headerVars.ATTDIA = dec_macros.FIELD_B(dat,"B",70);
        }
        headerVars.MIRRTEXT = dec_macros.FIELD_B(dat,"B",70);
        headerVars.WORLDVIEW = dec_macros.FIELD_B(dat,"B",70);
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            headerVars.WIREFRAME = dec_macros.FIELD_B(dat,"B",0);
        }
        headerVars.TILEMODE = dec_macros.FIELD_B(dat,"B",70);
        headerVars.PLIMCHECK = dec_macros.FIELD_B(dat,"B",70);
        headerVars.VISRETAIN = dec_macros.FIELD_B(dat,"B",70);
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            headerVars.DELOBJ = dec_macros.FIELD_B(dat,"B",0);
        }
        headerVars.DISPSILH = dec_macros.FIELD_B(dat,"B",70);
        headerVars.PELLIPSE = dec_macros.FIELD_B(dat,"B",70);
//#IF 0
        if(commen.VERSION(DWG_VERSION_TYPE.R_13b1,dat))
        {
            headerVars.SAVEIMAGES = dec_macros.FIELD_BS(dat,"BS",70);
        }

//#ENDIF
        headerVars.PROXYGRAPHICS = dec_macros.FIELD_BS(dat,"BS",70);
        if(commen.UNTIL(DWG_VERSION_TYPE.R_14,dat))
        {
            if(specs.IF_ENCODE_FROM_EARLIER_OR_DXF)
            {
                headerVars.DRAGMODE = 2;
            }
            headerVars.DRAGMODE = dec_macros.FIELD_BS(dat,"BS",70);
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            if(specs.IF_ENCODE_FROM_EARLIER_OR_DXF)
            {
                headerVars.TREEDEPTH = 3020;
            }
            headerVars.TREEDEPTH = dec_macros.FIELD_BSd(dat,"BSd","BS", 70);
        }
        headerVars.LUNITS = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.LUPREC = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.AUNITS = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.AUPREC = dec_macros.FIELD_BS(dat,"BS",70);

        if(commen.UNTIL(DWG_VERSION_TYPE.R_14,dat))
        {
            headerVars.OSMODE = dec_macros.FIELD_BS(dat,"BS",70);
        }
        headerVars.ATTMODE = dec_macros.FIELD_BS(dat,"BS",70);
        if(commen.UNTIL(DWG_VERSION_TYPE.R_14,dat))
        {
            headerVars.COORDS = dec_macros.FIELD_BS(dat,"BS",70);
        }
        headerVars.PDMODE = dec_macros.FIELD_BS(dat,"BS",70);
        if(commen.UNTIL(DWG_VERSION_TYPE.R_14,dat))
        {
            headerVars.PICKSTYLE = dec_macros.FIELD_BS(dat,"BS",70);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {
            headerVars.unknown_12 = dec_macros.FIELD_BL(dat,"BL",0);
            headerVars.unknown_13 = dec_macros.FIELD_BL(dat,"BL",0);
            headerVars.unknown_14 = dec_macros.FIELD_BL(dat,"BL",0);
        }

        if(specs.IF_ENCODE_FROM_PRE_R13)
        {
            headerVars.SHADEDGE = 3;
            headerVars.SHADEDIF = 70;
            headerVars.MAXACTVP = 64;
        }

        headerVars.USERI1 = dec_macros.FIELD_BSd(dat,"BSd","BS",70);
        headerVars.USERI2 = dec_macros.FIELD_BSd(dat,"BSd","BS",70);
        headerVars.USERI3 = dec_macros.FIELD_BSd(dat,"BSd","BS",70);
        headerVars.USERI4 = dec_macros.FIELD_BSd(dat,"BSd","BS",70);
        headerVars.USERI5 = dec_macros.FIELD_BSd(dat,"BSd","BS",70);

        headerVars.SPLINESEGS = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.SURFU = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.SURFV = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.SURFTYPE = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.SURFTAB1 = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.SURFTAB2 = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.SPLINETYPE = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.SHADEDGE = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.SHADEDIF = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.UNITMODE = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.MAXACTVP = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.ISOLINES = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.CMLJUST = dec_macros.FIELD_BS(dat,"BS",70);
        headerVars.TEXTQLTY = dec_macros.FIELD_BS(dat,"BS",70);

        headerVars.LTSCALE = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.TEXTSIZE = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.TRACEWID = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.SKETCHINC = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.FILLETRAD = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.THICKNESS = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.ANGBASE = dec_macros.FIELD_BD(dat,"BD",50);
        headerVars.PDSIZE = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.PLINEWID = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.USERR1 = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.USERR2 = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.USERR3 = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.USERR4 = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.USERR5 = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.CHAMFERA = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.CHAMFERB = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.CHAMFERC = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.CHAMFERD = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.FACETRES = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.CMLSCALE = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.CELTSCALE = dec_macros.FIELD_BD(dat,"BD",40);
        if(commen.PRE(DWG_VERSION_TYPE.R_2004a,dat))
        {
            headerVars.MENU = dec_macros.FIELD_TV(dat,"TV",1);
        }
        headerVars.TDUCREATE = dec_macros.FIELD_TIMEBLL(dat,40);
        headerVars.TDUUPDATE = dec_macros.FIELD_TIMEBLL(dat,40);
        /**
         * DECODER {
         *     if (!_obj->TDCREATE.days) {
         *       struct tm tm;
         *       long off = tm_offset() * 1000;
         *       char _buf[60] = "";
         *       _obj->TDCREATE.days  = _obj->TDUCREATE.days;
         *       // adjust for timezone offset
         *       _obj->TDCREATE.ms    = _obj->TDUCREATE.ms + off;
         *       _obj->TDCREATE.value = _obj->TDCREATE.days + (_obj->TDCREATE.ms * 1e-8);
         *       strftime (_buf, 60, STRFTIME_DATE, cvt_TIMEBLL (&tm, _obj->TDCREATE));
         *       LOG_TRACE ("=> TDCREATE: [" FORMAT_BL ", " FORMAT_BL "] %s [TIMEBLL 40]\n",
         *                    _obj->TDCREATE.days, _obj->TDCREATE.ms, _buf);
         *       _obj->TDUPDATE.days  = _obj->TDUUPDATE.days;
         *       _obj->TDUPDATE.ms    = _obj->TDUUPDATE.ms + off;
         *       _obj->TDUPDATE.value = _obj->TDUPDATE.days + (_obj->TDUPDATE.ms * 1e-8);
         *       strftime (_buf, 60, STRFTIME_DATE, cvt_TIMEBLL (&tm, _obj->TDUPDATE));
         *       LOG_TRACE ("=> TDUPDATE: [" FORMAT_BL ", " FORMAT_BL "] %s [TIMEBLL 40]\n",
         *                    _obj->TDUPDATE.days, _obj->TDUPDATE.ms, _buf);
         *     }
         *   }
         */
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {
            headerVars.unknown_15 = dec_macros.FIELD_BL(dat,"BL",0);
            headerVars.unknown_16 = dec_macros.FIELD_BL(dat,"BL",0);
            headerVars.unknown_17 = dec_macros.FIELD_BL(dat,"BL",0);
        }
        headerVars.TDINDWG = dec_macros.FIELD_TIMEBLL(dat,40);
        headerVars.TDUSRTIMER = dec_macros.FIELD_TIMEBLL(dat,40);
        headerVars.CECOLOR = dec_macros.FIELD_CMC(dat,str_dat,0);
        headerVars.HANDSEED = dec_macros.FIELD_DATAHANDLE(hdl_dat,obj,objDwgData,-1,0);
        headerVars.CLAYER = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,8);
        headerVars.TEXTSTYLE = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,8);
        headerVars.CELTYPE = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,8);
        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            headerVars.CMATERIAL = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
        }
        headerVars.DIMSTYLE = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,2);
        headerVars.CMLSTYLE = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,2);
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            headerVars.PSVPSCALE = dec_macros.FIELD_BD(dat,"BD",40);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            headerVars.PINSBASE = dec_macros.FIELD_3BD(dat,10);
            headerVars.PEXTMIN = dec_macros.FIELD_3BD(dat,10);
            headerVars.PEXTMAX = dec_macros.FIELD_3BD(dat,10);
            headerVars.PLIMMIN = dec_macros.FIELD_2RD(dat,10);
            headerVars.PLIMMAX = dec_macros.FIELD_2RD(dat,10);
            headerVars.PELEVATION = dec_macros.FIELD_BD(dat,"BD",10);
            headerVars.PUCSORG = dec_macros.FIELD_3BD(dat,10);
            headerVars.PUCSXDIR = dec_macros.FIELD_3BD(dat,10);
            headerVars.PUCSYDIR = dec_macros.FIELD_3BD(dat,10);
            headerVars.PUCSNAME = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,2);
        }
        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            headerVars.PUCSORTHOREF = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,2);
            headerVars.PUCSORTHOVIEW = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.PUCSBASE = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,2);
            headerVars.PUCSORGTOP = dec_macros.FIELD_3BD(dat,10);
            headerVars.PUCSORGBOTTOM = dec_macros.FIELD_3BD(dat,10);
            headerVars.PUCSORGLEFT = dec_macros.FIELD_3BD(dat,10);
            headerVars.PUCSORGRIGHT = dec_macros.FIELD_3BD(dat,10);
            headerVars.PUCSORGFRONT = dec_macros.FIELD_3BD(dat,10);
            headerVars.PUCSORGBACK = dec_macros.FIELD_3BD(dat,10);
        }

        headerVars.INSBASE = dec_macros.FIELD_3BD(dat,10);
        headerVars.EXTMIN = dec_macros.FIELD_3BD(dat,10);
        headerVars.EXTMAX = dec_macros.FIELD_3BD(dat,10);
        headerVars.LIMMIN = dec_macros.FIELD_2RD(dat,10);
        headerVars.LIMMAX = dec_macros.FIELD_2RD(dat,10);
        headerVars.ELEVATION = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.UCSORG = dec_macros.FIELD_3BD(dat,10);
        headerVars.UCSXDIR = dec_macros.FIELD_3BD(dat,10);
        headerVars.UCSYDIR = dec_macros.FIELD_3BD(dat,10);
        headerVars.UCSNAME = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,2);


    }

    static int header_variables_spec_write(Bit_Chain dat, Bit_Chain hdl_dat, Bit_Chain str_dat, Dwg_Data objDwgData) throws IOException {
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

        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            if(specs.IF_ENCODE_FROM_EARLIER_OR_DXF)
            {
                headerVars.unit1_ratio = 412148564080.0;
                headerVars.unit2_ratio = 1.0;
                headerVars.unit3_ratio = 1.0;
                headerVars.unit4_ratio = 1.0;
            }

        }

        out_json.FIELD_BD(dat,"unit1_ratio",headerVars.unit1_ratio,0);
        out_json.FIELD_BD(dat,"unit2_ratio",headerVars.unit2_ratio,0);
        out_json.FIELD_BD(dat,"unit3_ratio",headerVars.unit3_ratio,0);
        out_json.FIELD_BD(dat,"unit4_ratio",headerVars.unit4_ratio,0);

        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_2004,dat))
        {
            if(specs.IF_ENCODE_FROM_EARLIER_OR_DXF)
            {
                headerVars.unit1_name = "";
                headerVars.unit1_name = "m";
            }
            out_json.FIELD_TV(dat,"unit1_name",headerVars.unit1_name,0);
            out_json.FIELD_TV(dat,"unit2_name",headerVars.unit2_name,0);
            out_json.FIELD_TV(dat,"unit3_name",headerVars.unit3_name,0);
            out_json.FIELD_TV(dat,"unit4_name",headerVars.unit4_name,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            if(specs.IF_ENCODE_FROM_EARLIER_OR_DXF)
            {
                headerVars.unknown_8 = 24;
            }
           out_json.FIELD_BL(dat,"unknown_8",headerVars.unknown_8,0);
           out_json.FIELD_BL(dat,"unknown_9",headerVars.unknown_9,0);
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            out_json.FIELD_BS(dat,"unknown_10",headerVars.unknown_10,0);
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_2000,dat))
        {
            out_json.FIELD_HANDLE(dat,"VX_TABLE_RECORD",headerVars.VX_TABLE_RECORD,5,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_B(dat,"DIMASO",headerVars.DIMASO,70);
            out_json.FIELD_B(dat,"DIMSHO",headerVars.DIMSHO,70);
        }

        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            out_json.FIELD_B(dat,"DIMSAV",headerVars.DIMSAV,70);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_B(dat,"PLINEGEN",headerVars.PLINEGEN,70);
            out_json.FIELD_B(dat,"ORTHOMODE",headerVars.ORTHOMODE,70);
            out_json.FIELD_B(dat,"REGENMODE",headerVars.REGENMODE,70);
            out_json.FIELD_B(dat,"FILLMODE",headerVars.FILLMODE,70);
            out_json.FIELD_B(dat,"QTEXTMODE",headerVars.QTEXTMODE,70);
            out_json.FIELD_B(dat,"PSLTSCALE",headerVars.PSLTSCALE,70);
            out_json.FIELD_B(dat,"LIMCHECK",headerVars.LIMCHECK,70);
        }

        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            out_json.FIELD_B(dat,"BLIPMODE",headerVars.BLIPMODE,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat)){
            out_json.FIELD_B(dat,"unknown_11",headerVars.unknown_11,0);
        }
        out_json.FIELD_B(dat,"USRTIMER",headerVars.USRTIMER,70);
        out_json.FIELD_B(dat,"SKPOLY",headerVars.SKPOLY,70);
        out_json.FIELD_B(dat,"ANGDIR",headerVars.ANGDIR,70);
        out_json.FIELD_B(dat,"SPLFRAME",headerVars.SPLFRAME,70);

        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            if(specs.IF_ENCODE_FROM_EARLIER_OR_DXF)
            {
                headerVars.ATTREQ = 1;
                headerVars.ATTDIA = 1;
                headerVars.HANDLING = 1;
            }
            out_json.FIELD_B(dat,"ATTREQ",headerVars.ATTREQ,70);
            out_json.FIELD_B(dat,"ATTDIA",headerVars.ATTDIA,70);
        }
        out_json.FIELD_B(dat,"MIRRTEXT",headerVars.MIRRTEXT,70);
        out_json.FIELD_B(dat,"WORLDVIEW",headerVars.WORLDVIEW,70);

        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            out_json.FIELD_B(dat,"WIREFRAME",headerVars.WIREFRAME,0);
        }
        out_json.FIELD_B(dat,"TILEMODE",headerVars.TILEMODE,70);
        out_json.FIELD_B(dat,"PLIMCHECK",headerVars.PLIMCHECK,70);
        out_json.FIELD_B(dat,"VISRETAIN",headerVars.VISRETAIN,70);

        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            out_json.FIELD_B(dat,"DELOBJ",headerVars.DELOBJ,70);
        }
        out_json.FIELD_B(dat,"DISPSILH",headerVars.DISPSILH,70);
        out_json.FIELD_B(dat,"PELLIPSE",headerVars.PELLIPSE,70);

        //#IF 0
        if(commen.VERSION(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_BS(dat,"SAVEIMAGES",headerVars.SAVEIMAGES,0);
        }

//#ENDIF
        out_json.FIELD_BS(dat,"PROXYGRAPHICS",headerVars.PROXYGRAPHICS,70);
        if(commen.UNTIL(DWG_VERSION_TYPE.R_14,dat))
        {
            if(specs.IF_ENCODE_FROM_EARLIER_OR_DXF)
            {
                headerVars.DRAGMODE = 2;
            }
            out_json.FIELD_BS(dat,"DRAGMODE",headerVars.DRAGMODE,0);
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            if(specs.IF_ENCODE_FROM_EARLIER_OR_DXF)
            {
                headerVars.TREEDEPTH = 3020;
            }
            out_json.FIELD_BSd(dat,"TREEDEPTH",headerVars.TREEDEPTH,70);
        }

        out_json.FIELD_BS(dat,"LUNITS",headerVars.LUNITS,70);
        out_json.FIELD_BS(dat,"LUPREC",headerVars.LUPREC,70);
        out_json.FIELD_BS(dat,"AUNITS",headerVars.AUNITS,70);
        out_json.FIELD_BS(dat,"AUPREC",headerVars.AUPREC,70);
        if(commen.UNTIL(DWG_VERSION_TYPE.R_14,dat))
        {
            out_json.FIELD_BS(dat,"OSMODE",headerVars.OSMODE,70);
        }
        out_json.FIELD_BS(dat,"ATTMODE",headerVars.ATTMODE,70);
        if(commen.UNTIL(DWG_VERSION_TYPE.R_14,dat))
        {
            out_json.FIELD_BS(dat,"COORDS",headerVars.COORDS,70);
        }
        out_json.FIELD_BS(dat,"PDMODE",headerVars.PDMODE,70);
        if(commen.UNTIL(DWG_VERSION_TYPE.R_14,dat))
        {
            out_json.FIELD_BS(dat,"PICKSTYLE",headerVars.PICKSTYLE,70);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {
            out_json.FIELD_BL(dat,"unknown_12",headerVars.unknown_12,0);
            out_json.FIELD_BL(dat,"unknown_13",headerVars.unknown_13,0);
            out_json.FIELD_BL(dat,"unknown_14",headerVars.unknown_14,0);
        }

        if(specs.IF_ENCODE_FROM_PRE_R13)
        {
            headerVars.SHADEDGE = 3;
            headerVars.SHADEDIF = 70;
            headerVars.MAXACTVP = 64;
        }

        out_json.FIELD_BSd(dat,"USERI1",headerVars.USERI1,70);
        out_json.FIELD_BSd(dat,"USERI2",headerVars.USERI2,70);
        out_json.FIELD_BSd(dat,"USERI3",headerVars.USERI3,70);
        out_json.FIELD_BSd(dat,"USERI4",headerVars.USERI4,70);
        out_json.FIELD_BSd(dat,"USERI5",headerVars.USERI5,70);

        out_json.FIELD_BS(dat,"SPLINESEGS",headerVars.SPLINESEGS,70);
        out_json.FIELD_BS(dat,"SURFU",headerVars.SURFU,70);
        out_json.FIELD_BS(dat,"SURFV",headerVars.SURFV,70);
        out_json.FIELD_BS(dat,"SURFTYPE",headerVars.SURFTYPE,70);
        out_json.FIELD_BS(dat,"SURFTAB1",headerVars.SURFTAB1,70);
        out_json.FIELD_BS(dat,"SURFTAB2",headerVars.SURFTAB2,70);
        out_json.FIELD_BS(dat,"SPLINETYPE",headerVars.SPLINETYPE,70);
        out_json.FIELD_BS(dat,"SHADEDGE",headerVars.SHADEDGE,70);
        out_json.FIELD_BS(dat,"SHADEDIF",headerVars.SHADEDIF,70);
        out_json.FIELD_BS(dat,"UNITMODE",headerVars.UNITMODE,70);
        out_json.FIELD_BS(dat,"MAXACTVP",headerVars.MAXACTVP,70);
        out_json.FIELD_BS(dat,"ISOLINES",headerVars.ISOLINES,70);
        out_json.FIELD_BS(dat,"CMLJUST",headerVars.CMLJUST,70);
        out_json.FIELD_BS(dat,"TEXTQLTY",headerVars.TEXTQLTY,70);

        out_json.FIELD_BD(dat,"LTSCALE",headerVars.LTSCALE,40);
        out_json.FIELD_BD(dat,"TEXTSIZE",headerVars.TEXTSIZE,40);
        out_json.FIELD_BD(dat,"TRACEWID",headerVars.TRACEWID,40);
        out_json.FIELD_BD(dat,"SKETCHINC",headerVars.SKETCHINC,40);
        out_json.FIELD_BD(dat,"FILLETRAD",headerVars.FILLETRAD,40);
        out_json.FIELD_BD(dat,"THICKNESS",headerVars.THICKNESS,40);
        out_json.FIELD_BD(dat,"ANGBASE",headerVars.ANGBASE,40);
        out_json.FIELD_BD(dat,"PDSIZE",headerVars.PDSIZE,40);
        out_json.FIELD_BD(dat,"PLINEWID",headerVars.PLINEWID,40);
        out_json.FIELD_BD(dat,"USERR1",headerVars.USERR1,40);
        out_json.FIELD_BD(dat,"USERR2",headerVars.USERR2,40);
        out_json.FIELD_BD(dat,"USERR3",headerVars.USERR3,40);
        out_json.FIELD_BD(dat,"USERR4",headerVars.USERR4,40);
        out_json.FIELD_BD(dat,"USERR5",headerVars.USERR5,40);
        out_json.FIELD_BD(dat,"CHAMFERA",headerVars.CHAMFERA,40);
        out_json.FIELD_BD(dat,"CHAMFERB",headerVars.CHAMFERB,40);
        out_json.FIELD_BD(dat,"CHAMFERC",headerVars.CHAMFERC,40);
        out_json.FIELD_BD(dat,"CHAMFERD",headerVars.CHAMFERD,40);
        out_json.FIELD_BD(dat,"FACETRES",headerVars.FACETRES,40);
        out_json.FIELD_BD(dat,"CMLSCALE",headerVars.CMLSCALE,40);
        out_json.FIELD_BD(dat,"CELTSCALE",headerVars.CELTSCALE,40);
        if(commen.PRE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            out_json.FIELD_TV(dat,"MENU",headerVars.MENU,1);
        }
        out_json.FIELD_TIMEBLL(dat,"TDUCREATE",headerVars.TDUCREATE,40);
        out_json.FIELD_TIMEBLL(dat,"TDUUPDATE",headerVars.TDUUPDATE,40);
        /**
         * DECODER {
         *     if (!_obj->TDCREATE.days) {
         *       struct tm tm;
         *       long off = tm_offset() * 1000;
         *       char _buf[60] = "";
         *       _obj->TDCREATE.days  = _obj->TDUCREATE.days;
         *       // adjust for timezone offset
         *       _obj->TDCREATE.ms    = _obj->TDUCREATE.ms + off;
         *       _obj->TDCREATE.value = _obj->TDCREATE.days + (_obj->TDCREATE.ms * 1e-8);
         *       strftime (_buf, 60, STRFTIME_DATE, cvt_TIMEBLL (&tm, _obj->TDCREATE));
         *       LOG_TRACE ("=> TDCREATE: [" FORMAT_BL ", " FORMAT_BL "] %s [TIMEBLL 40]\n",
         *                    _obj->TDCREATE.days, _obj->TDCREATE.ms, _buf);
         *       _obj->TDUPDATE.days  = _obj->TDUUPDATE.days;
         *       _obj->TDUPDATE.ms    = _obj->TDUUPDATE.ms + off;
         *       _obj->TDUPDATE.value = _obj->TDUPDATE.days + (_obj->TDUPDATE.ms * 1e-8);
         *       strftime (_buf, 60, STRFTIME_DATE, cvt_TIMEBLL (&tm, _obj->TDUPDATE));
         *       LOG_TRACE ("=> TDUPDATE: [" FORMAT_BL ", " FORMAT_BL "] %s [TIMEBLL 40]\n",
         *                    _obj->TDUPDATE.days, _obj->TDUPDATE.ms, _buf);
         *     }
         *   }
         */
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {
            out_json.FIELD_BL(dat,"unknown_15",headerVars.unknown_15,0);
            out_json.FIELD_BL(dat,"unknown_16",headerVars.unknown_16,0);
            out_json.FIELD_BL(dat,"unknown_17",headerVars.unknown_17,0);
        }
        out_json.FIELD_TIMEBLL(dat,"TDINDWG",headerVars.TDINDWG,40);
        out_json.FIELD_TIMEBLL(dat,"TDUSRTIMER",headerVars.TDUSRTIMER,40);
        out_json.FIELD_CMC(dat,"CECOLOR",headerVars.CECOLOR,0);
        out_json.FIELD_DATAHANDLE(hdl_dat,"HANDSEED",headerVars.HANDSEED,-1,0);
        out_json.FIELD_HANDLE(hdl_dat,"CLAYER",headerVars.CLAYER,5,8);
        out_json.FIELD_HANDLE(hdl_dat,"TEXTSTYLE",headerVars.TEXTSTYLE,5,7);
        out_json.FIELD_HANDLE(hdl_dat,"CELTYPE",headerVars.CELTYPE,5,6);
        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            out_json.FIELD_HANDLE(hdl_dat,"CMATERIAL",headerVars.CMATERIAL,5,0);
        }
        out_json.FIELD_HANDLE(hdl_dat,"DIMSTYLE",headerVars.DIMSTYLE,5,2);
        out_json.FIELD_HANDLE(hdl_dat,"CMLSTYLE",headerVars.CMLSTYLE,5,2);
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            out_json.FIELD_BD(dat,"PSVPSCALE",headerVars.PSVPSCALE,10);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_3BD(dat,"PINSBASE",headerVars.PINSBASE,10);
            out_json.FIELD_3BD(dat,"PEXTMIN",headerVars.PEXTMIN,10);
            out_json.FIELD_3BD(dat,"PEXTMAX",headerVars.PEXTMAX,10);
            out_json.FIELD_2RD(dat,"PLIMMIN",headerVars.PLIMMIN,10);
            out_json.FIELD_2RD(dat,"PLIMMAX",headerVars.PLIMMAX,10);
            out_json.FIELD_BD(dat,"PELEVATION",headerVars.PELEVATION,10);
            out_json.FIELD_3BD(dat,"PUCSORG",headerVars.PUCSORG,10);
            out_json.FIELD_3BD(dat,"PUCSXDIR",headerVars.PUCSXDIR,10);
            out_json.FIELD_3BD(dat,"PUCSYDIR",headerVars.PUCSYDIR,10);
            out_json.FIELD_HANDLE(hdl_dat,"PUCSNAME",headerVars.PUCSNAME,5,2);

        }
        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            out_json.FIELD_HANDLE(hdl_dat,"PUCSORTHOREF",headerVars.PUCSORTHOREF,5,2);
            out_json.FIELD_BS(dat,"PUCSORTHOVIEW",headerVars.PUCSORTHOVIEW,70);
            out_json.FIELD_HANDLE(hdl_dat,"PUCSBASE",headerVars.PUCSBASE,5,2);
            out_json.FIELD_3BD(dat,"PUCSORGTOP",headerVars.PUCSORGTOP,10);
            out_json.FIELD_3BD(dat,"PUCSORGBOTTOM",headerVars.PUCSORGBOTTOM,10);
            out_json.FIELD_3BD(dat,"PUCSORGLEFT",headerVars.PUCSORGLEFT,10);
            out_json.FIELD_3BD(dat,"PUCSORGRIGHT",headerVars.PUCSORGRIGHT,10);
            out_json.FIELD_3BD(dat,"PUCSORGFRONT",headerVars.PUCSORGFRONT,10);
            out_json.FIELD_3BD(dat,"PUCSORGBACK",headerVars.PUCSORGBACK,10);
        }

        out_json.FIELD_3BD(dat,"INSBASE",headerVars.INSBASE,10);
        out_json.FIELD_3BD(dat,"EXTMIN",headerVars.EXTMIN,10);
        out_json.FIELD_3BD(dat,"EXTMAX",headerVars.EXTMAX,10);
        out_json.FIELD_2RD(dat,"LIMMIN",headerVars.LIMMIN,10);
        out_json.FIELD_2RD(dat,"LIMMAX",headerVars.LIMMAX,10);
        out_json.FIELD_BD(dat,"ELEVATION",headerVars.ELEVATION,40);
        out_json.FIELD_3BD(dat,"UCSORG",headerVars.UCSORG,10);
        out_json.FIELD_3BD(dat,"UCSXDIR",headerVars.UCSXDIR,10);
        out_json.FIELD_3BD(dat,"UCSYDIR",headerVars.UCSYDIR,10);
        out_json.FIELD_HANDLE(hdl_dat,"UCSNAME",headerVars.UCSNAME,5,2);


        return error;
    }
}
