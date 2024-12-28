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

        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            headerVars.UCSORTHOREF = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,2);
            headerVars.UCSORTHOVIEW = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.UCSBASE = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,2);
            headerVars.UCSORGTOP = dec_macros.FIELD_3BD(dat,10);
            headerVars.UCSORGBOTTOM = dec_macros.FIELD_3BD(dat,10);
            headerVars.UCSORGLEFT = dec_macros.FIELD_3BD(dat,10);
            headerVars.UCSORGRIGHT = dec_macros.FIELD_3BD(dat,10);
            headerVars.UCSORGFRONT = dec_macros.FIELD_3BD(dat,10);
            headerVars.UCSORGBACK = dec_macros.FIELD_3BD(dat,10);
            if(commen.PRE(DWG_VERSION_TYPE.R_2007a,dat))
            {
                headerVars.DIMPOST = dec_macros.FIELD_TV(dat,"TV",1);
                headerVars.DIMAPOST = dec_macros.FIELD_TV(dat,"TV",1);
            }
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            headerVars.DIMTOL = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMLIM = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMTIH = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMTOH = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMSE1 = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMSE2 = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMALT = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMTOFL = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMSAH = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMTIX = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMSOXD = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMALTD = (char) dec_macros.FIELD_CAST(dat,"BS",70);
            headerVars.DIMZIN = (char) dec_macros.FIELD_CAST(dat,"BS",70);
            headerVars.DIMSD1 = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMSD2 = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMTOLJ = (char) dec_macros.FIELD_CAST(dat,"BS",70);
            headerVars.DIMJUST = (char) dec_macros.FIELD_CAST(dat,"BS",70);
            headerVars.DIMFIT = (char) dec_macros.FIELD_CAST(dat,"BS",70);
            headerVars.DIMUPT = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMTZIN = (char) dec_macros.FIELD_CAST(dat,"BS",70);
            headerVars.DIMALTZ = (char) dec_macros.FIELD_CAST(dat,"BS",70);
            headerVars.DIMALTTZ = (char) dec_macros.FIELD_CAST(dat,"BS",70);
            headerVars.DIMTAD = (char) dec_macros.FIELD_CAST(dat,"BS",70);
            headerVars.DIMUNIT = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMAUNIT = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMDEC = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMTDEC = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMALTU = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMALTTD = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMTXSTY = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,7);
        }
        headerVars.DIMSCALE = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.DIMASZ = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.DIMEXO = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.DIMDLI = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.DIMEXE = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.DIMRND = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.DIMDLE = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.DIMTP = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.DIMTM = dec_macros.FIELD_BD(dat,"BD",40);
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            headerVars.DIMFXL = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.DIMJOGANG = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.DIMTFILL = dec_macros.FIELD_BS(dat,"BS",40);
            headerVars.DIMTFILLCLR = dec_macros.FIELD_CMC(dat,str_dat,70);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            headerVars.DIMTOL = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMLIM = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMTIH = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMTOH = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMSE1 = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMSE2 = dec_macros.FIELD_B(dat,"B",70);

            headerVars.DIMTAD = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMZIN = (char) dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMAZIN = dec_macros.FIELD_BS(dat,"BS",70);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            headerVars.DIMARCSYM = dec_macros.FIELD_BS(dat,"BS",70);
        }
        headerVars.DIMTXT = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.DIMCEN = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.DIMTSZ = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.DIMALTF = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.DIMLFAC = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.DIMTVP = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.DIMTFAC = dec_macros.FIELD_BD(dat,"BD",40);
        headerVars.DIMGAP = dec_macros.FIELD_BD(dat,"BD",40);

        if(specs.IF_FREE_OR_VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            headerVars.DIMPOST = dec_macros.FIELD_TV(dat,"TV",1);
            headerVars.DIMAPOST = dec_macros.FIELD_TV(dat,"TV",1);
            headerVars.DIMBLK_T = dec_macros.FIELD_TV(dat,"TV",1);
            headerVars.DIMBLK1_T = dec_macros.FIELD_TV(dat,"TV",1);
            headerVars.DIMBLK2_T = dec_macros.FIELD_TV(dat,"TV",1);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            headerVars.DIMALTRND = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.DIMALT = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMALTD = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMTOFL = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMSAH = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMTIX = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMSOXD = dec_macros.FIELD_B(dat,"B",70);

        }
        headerVars.DIMCLRD = dec_macros.FIELD_CMC(dat,str_dat,70);
        headerVars.DIMCLRE = dec_macros.FIELD_CMC(dat,str_dat,70);
        headerVars.DIMCLRT = dec_macros.FIELD_CMC(dat,str_dat,70);
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat)){
            headerVars.DIMADEC = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMDEC = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMTDEC = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMALTU = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMALTTD = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMAUNIT = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMFRAC = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMLUNIT = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMDSEP = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMTMOVE = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMJUST = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMSD1 = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMSD2 = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMTOLJ = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMTZIN = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMALTZ = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMALTTZ = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.DIMUPT = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMATFIT = dec_macros.FIELD_BS(dat,"BS",70);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            headerVars.DIMFXLON = dec_macros.FIELD_B(dat,"B",70);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2010b,dat))
        {
            headerVars.DIMTXTDIRECTION = dec_macros.FIELD_B(dat,"B",70);
            headerVars.DIMALTMZF = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.DIMMZF = dec_macros.FIELD_BD(dat,"BD",40);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            headerVars.DIMTXSTY = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,7);
            headerVars.DIMLDRBLK = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,1);
            headerVars.DIMBLK = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,1);
            headerVars.DIMBLK1 = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,1);
            headerVars.DIMBLK2 = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,1);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            headerVars.DIMLTYPE = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,6);
            headerVars.DIMLTEX1 = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,6);
            headerVars.DIMLTEX2 = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,6);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            headerVars.DIMLWD = dec_macros.FIELD_BSd(dat,"BSd","BS",70);
            headerVars.DIMLWE = dec_macros.FIELD_BSd(dat,"BSd","BS",70);
        }
        headerVars.BLOCK_CONTROL_OBJECT = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        headerVars.LAYER_CONTROL_OBJECT = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        headerVars.STYLE_CONTROL_OBJECT = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        headerVars.LTYPE_CONTROL_OBJECT = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        headerVars.VIEW_CONTROL_OBJECT = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        headerVars.UCS_CONTROL_OBJECT = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        headerVars.VPORT_CONTROL_OBJECT = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        headerVars.APPID_CONTROL_OBJECT = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        headerVars.DIMSTYLE_CONTROL_OBJECT = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_2000,dat))
        {
            headerVars.VX_CONTROL_OBJECT = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        }
        headerVars.DICTIONARY_ACAD_GROUP = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        headerVars.DICTIONARY_ACAD_MLINESTYLE = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        headerVars.DICTIONARY_NAMED_OBJECT = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            if(specs.IF_ENCODE_FROM_EARLIER_OR_DXF)
            {
                headerVars.TSTACKALIGN = 1;
                headerVars.TSTACKSIZE = 70;
            }
            headerVars.TSTACKALIGN = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.TSTACKSIZE = dec_macros.FIELD_BS(dat,"BS",70);
            if(commen.PRE(DWG_VERSION_TYPE.R_2007a,dat))
            {
                headerVars.HYPERLINKBASE = dec_macros.FIELD_TV(dat,"TV",1);
                headerVars.STYLESHEET = dec_macros.FIELD_TV(dat,"TV",1);
            }
            headerVars.DICTIONARY_LAYOUT = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
            headerVars.DICTIONARY_PLOTSETTINGS = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
            headerVars.DICTIONARY_PLOTSTYLENAME = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
        }
        if (commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {
            headerVars.DICTIONARY_MATERIAL = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
            headerVars.DICTIONARY_COLOR = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
        }
        if (commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            headerVars.DICTIONARY_VISUALSTYLE = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
        }
        if (commen.SINCE(DWG_VERSION_TYPE.R_2013b,dat))
        {
            headerVars.unknown_20 = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            if(specs.ENCODER)
            {
                // unneeded here. done in in_dxf.c:1189
                //FIELD_VALUE (FLAGS) |= dxf_revcvt_lweight (FIELD_VALUE (CELWEIGHT));
                //if (FIELD_VALUE (ENDCAPS)) FIELD_VALUE (FLAGS) |= 0x60;
                // ...
            }
            headerVars.FLAGS = dec_macros.FIELD_BLx(dat,"BLx","BL",70);
            if(specs.DECODER)
            {
                headerVars.CELWEIGHT = dwg.dxf_cvt_lweight((short)(headerVars.FLAGS & 0x1f));
                //FIELD_G_TRACE (CELWEIGHT, BSd, 370) // default: -1 ByLayer
                headerVars.ENDCAPS = (char)((headerVars.FLAGS & 0x60) != 0 ? 1 : 0);
                //FIELD_G_TRACE (ENDCAPS, RC, 280)
                headerVars.JOINSTYLE = (char)((headerVars.FLAGS & 0x180) != 0 ? 1 : 0);
                headerVars.LWDISPLAY = (char)((headerVars.FLAGS & 0x200) != 0 ? 1 : 0);
                headerVars.XEDIT = (char)((headerVars.FLAGS & 0x400) != 0 ? 0 : 1);
                headerVars.EXTNAMES = (char)((headerVars.FLAGS & 0x800) != 0 ? 1 : 0);
                headerVars.PSTYLEMODE = (char)((headerVars.FLAGS & 0x2000) != 0 ? 1 : 0);
                headerVars.OLESTARTUP = (char)((headerVars.FLAGS & 0x4000) != 0 ? 1 : 0);
            }
            headerVars.INSUNITS = dec_macros.FIELD_BS(dat,"BS",70);
            headerVars.CEPSNTYPE = dec_macros.FIELD_BS(dat,"BS",70);
            if(headerVars.CEPSNTYPE == 3)
            {
                headerVars.CPSNID = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
            }
            if(commen.PRE(DWG_VERSION_TYPE.R_2007a,dat))
            {
                headerVars.FINGERPRINTGUID = dec_macros.FIELD_TV(dat,"TV",0);
                headerVars.VERSIONGUID = dec_macros.FIELD_TV(dat,"TV",0);
            }
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {
            headerVars.SORTENTS = dec_macros.FIELD_RC(dat,"RC",280);
            headerVars.INDEXCTL = dec_macros.FIELD_RC(dat,"RC",280);
            headerVars.HIDETEXT = dec_macros.FIELD_RC(dat,"RC",280);
            headerVars.XCLIPFRAME = dec_macros.FIELD_RC(dat,"RC",290);
            headerVars.DIMASSOC = dec_macros.FIELD_RC(dat,"RC",280);
            headerVars.HALOGAP = dec_macros.FIELD_RC(dat,"RC",280);
            headerVars.OBSCOLOR = dec_macros.FIELD_BS(dat,"BS",280);
            headerVars.INTERSECTIONCOLOR = dec_macros.FIELD_BS(dat,"BS",280);
            headerVars.OBSLTYPE = dec_macros.FIELD_RC(dat,"RC",280);
            headerVars.INTERSECTIONDISPLAY = dec_macros.FIELD_RC(dat,"RC",290);
            if(commen.PRE(DWG_VERSION_TYPE.R_2007a,dat))
            {
                headerVars.PROJECTNAME = dec_macros.FIELD_TV(dat,"TV",1);
            }
        }
        headerVars.BLOCK_RECORD_PSPACE = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
        headerVars.BLOCK_RECORD_MSPACE = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
        headerVars.LTYPE_BYLAYER = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
        headerVars.LTYPE_BYBLOCK = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
        headerVars.LTYPE_CONTINUOUS = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);

        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            if(specs.IF_ENCODE_FROM_EARLIER)
            {
                headerVars.STEPSPERSEC = 2.0;
                headerVars.STEPSIZE = 50.0;
                headerVars.LENSLENGTH = 50.0;
                headerVars._3DDWFPREC = 2.0;
                headerVars.PSOLWIDTH = 5.0;
                headerVars.PSOLHEIGHT = 80;
                headerVars.LOFTANG1 = commen.M_PI_2;
                headerVars.LOFTANG2 = commen.M_PI_2;
                headerVars.LOFTPARAM = 7;
                headerVars.LOFTNORMALS = 1;
                headerVars.LATITUDE = 1.0;
                headerVars.LONGITUDE = 1.0;
                headerVars.TIMEZONE = -8000;
                headerVars.LIGHTGLYPHDISPLAY = 1;
                headerVars.TILEMODELIGHTSYNCH = 1;
                headerVars.SOLIDHIST = 1;
                headerVars.SHOWHIST = 1;
                headerVars.DWFFRAME = 2;
                headerVars.REALWORLDSCALE = 1;
            }
            headerVars.CAMERADISPLAY = dec_macros.FIELD_B(dat,"B",290);
            headerVars.unknown_21 = dec_macros.FIELD_BL(dat,"BL",0);
            headerVars.unknown_22 = dec_macros.FIELD_BL(dat,"BL",0);
            headerVars.unknown_23 = dec_macros.FIELD_BD(dat,"BD",0);
            headerVars.STEPSPERSEC = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.STEPSIZE = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars._3DDWFPREC = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.LENSLENGTH = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.CAMERAHEIGHT = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.SOLIDHIST = dec_macros.FIELD_RC(dat,"RC",40);
            headerVars.SHOWHIST = dec_macros.FIELD_RC(dat,"RC",40);
            headerVars.PSOLWIDTH = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.PSOLHEIGHT = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.LOFTANG1 = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.LOFTANG2 = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.LOFTMAG1 = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.LOFTMAG2 = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.LOFTPARAM = dec_macros.FIELD_BS(dat,"BS",40);
            headerVars.LOFTNORMALS = dec_macros.FIELD_RC(dat,"RC",40);
            headerVars.LATITUDE = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.LONGITUDE = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.NORTHDIRECTION = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.TIMEZONE = dec_macros.FIELD_BLd(dat,"BLd","BL",40);
            headerVars.LIGHTGLYPHDISPLAY = dec_macros.FIELD_RC(dat,"RC",40);
            headerVars.TILEMODELIGHTSYNCH = dec_macros.FIELD_RC(dat,"RC",40);
            headerVars.DWFFRAME = dec_macros.FIELD_RC(dat,"RC",40);
            headerVars.DGNFRAME = dec_macros.FIELD_RC(dat,"RC",40);
            headerVars.REALWORLDSCALE = dec_macros.FIELD_B(dat,"B",40);
            headerVars.INTERFERECOLOR = dec_macros.FIELD_CMC(dat,str_dat,40);
            headerVars.INTERFEREOBJVS = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,345);
            headerVars.INTERFEREVPVS = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,346);
            headerVars.DRAGVS = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,349);
            headerVars.CSHADOW = dec_macros.FIELD_RC(dat,"RC",280);
            headerVars.SHADOWPLANELOCATION = dec_macros.FIELD_BD(dat,"BD",40);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_14,dat))
        {
            headerVars.unknown_54 = dec_macros.FIELD_BS(dat,"BS",0);
            headerVars.unknown_55 = dec_macros.FIELD_BS(dat,"BS",0);
            headerVars.unknown_56 = dec_macros.FIELD_BS(dat,"BS",0);
            headerVars.unknown_57 = dec_macros.FIELD_BS(dat,"BS",0);
        }

        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            /**
             * // TODO split str_dat stream and get rid of this block
             *     SECTION_STRING_STREAM
             *     FIELD_T (unit1_name, 0);
             *     FIELD_T (unit2_name, 0);
             *     FIELD_T (unit3_name, 0);
             *     FIELD_T (unit4_name, 0);
             *     FIELD_T (MENU, 1);
             *     FIELD_T (DIMPOST, 1);
             *     FIELD_T (DIMAPOST, 1);
             *     SINCE (R_2010b) {
             *       FIELD_T (DIMALTMZS, 1);
             *       FIELD_T (DIMMZS, 1);
             *     }
             *     FIELD_T (HYPERLINKBASE, 1); // see SummaryInfo
             *     FIELD_T (STYLESHEET, 1);
             *     FIELD_T (FINGERPRINTGUID, 1);
             *     FIELD_T (VERSIONGUID, 1);
             *     FIELD_T (PROJECTNAME, 1);
             *     END_STRING_STREAM
             * */
        }
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

        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            out_json.FIELD_HANDLE(hdl_dat,"UCSORTHOREF",headerVars.UCSORTHOREF,5,2);
            out_json.FIELD_BS(dat,"UCSORTHOVIEW",headerVars.UCSORTHOVIEW,70);
            out_json.FIELD_HANDLE(hdl_dat,"UCSBASE",headerVars.UCSBASE,5,2);
            out_json.FIELD_3BD(dat,"UCSORGTOP",headerVars.UCSORGTOP,10);
            out_json.FIELD_3BD(dat,"UCSORGBOTTOM",headerVars.UCSORGBOTTOM,10);
            out_json.FIELD_3BD(dat,"UCSORGLEFT",headerVars.UCSORGLEFT,10);
            out_json.FIELD_3BD(dat,"UCSORGRIGHT",headerVars.UCSORGRIGHT,10);
            out_json.FIELD_3BD(dat,"UCSORGFRONT",headerVars.UCSORGFRONT,10);
            out_json.FIELD_3BD(dat,"UCSORGBACK",headerVars.UCSORGBACK,10);

            if(commen.PRE(DWG_VERSION_TYPE.R_2007a,dat))
            {
                out_json.FIELD_TV(dat,"DIMPOST",headerVars.DIMPOST,1);
                out_json.FIELD_TV(dat,"DIMAPOST",headerVars.DIMAPOST,1);
            }
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            out_json.FIELD_B(dat,"DIMTOL",headerVars.DIMTOL,70);
            out_json.FIELD_B(dat,"DIMLIM",headerVars.DIMLIM,70);
            out_json.FIELD_B(dat,"DIMTIH",headerVars.DIMTIH,70);
            out_json.FIELD_B(dat,"DIMTOH",headerVars.DIMTOH,70);
            out_json.FIELD_B(dat,"DIMSE1",headerVars.DIMSE1,70);
            out_json.FIELD_B(dat,"DIMSE2",headerVars.DIMSE2,70);
            out_json.FIELD_B(dat,"DIMALT",headerVars.DIMALT,70);
            out_json.FIELD_B(dat,"DIMTOFL",headerVars.DIMTOFL,70);
            out_json.FIELD_B(dat,"DIMSAH",headerVars.DIMSAH,70);
            out_json.FIELD_B(dat,"DIMTIX",headerVars.DIMTIX,70);
            out_json.FIELD_B(dat,"DIMSOXD",headerVars.DIMSOXD,70);
            out_json.FIELD_CAST(dat,headerVars.DIMALTD,"DIMALTD","BS",70);
            out_json.FIELD_CAST(dat,headerVars.DIMZIN,"DIMZIN","BS",70);
            out_json.FIELD_B(dat,"DIMSD1",headerVars.DIMSD1,70);
            out_json.FIELD_B(dat,"DIMSD2",headerVars.DIMSD2,70);
            out_json.FIELD_CAST(dat,headerVars.DIMTOLJ,"DIMTOLJ","BS",70);
            out_json.FIELD_CAST(dat,headerVars.DIMJUST,"DIMJUST","BS",70);
            out_json.FIELD_CAST(dat,headerVars.DIMFIT,"DIMFIT","BS",70);
            out_json.FIELD_B(dat,"DIMUPT",headerVars.DIMUPT,70);
            out_json.FIELD_CAST(dat,headerVars.DIMTZIN,"DIMTZIN","BS",70);
            out_json.FIELD_CAST(dat,headerVars.DIMALTZ,"DIMALTZ","BS",70);
            out_json.FIELD_CAST(dat,headerVars.DIMALTTZ,"DIMALTTZ","BS",70);
            out_json.FIELD_CAST(dat,headerVars.DIMTAD,"DIMTAD","BS",70);

            out_json.FIELD_BS(dat,"DIMUNIT",headerVars.DIMUNIT,70);
            out_json.FIELD_BS(dat,"DIMAUNIT",headerVars.DIMAUNIT,70);
            out_json.FIELD_BS(dat,"DIMDEC",headerVars.DIMDEC,70);
            out_json.FIELD_BS(dat,"DIMTDEC",headerVars.DIMTDEC,70);
            out_json.FIELD_BS(dat,"DIMALTU",headerVars.DIMALTU,70);
            out_json.FIELD_BS(dat,"DIMALTTD",headerVars.DIMALTTD,70);
            out_json.FIELD_HANDLE(hdl_dat,"DIMTXSTY",headerVars.DIMTXSTY,5,7);
        }
        out_json.FIELD_BD(dat,"DIMSCALE",headerVars.DIMSCALE,40);
        out_json.FIELD_BD(dat,"DIMASZ",headerVars.DIMASZ,40);
        out_json.FIELD_BD(dat,"DIMEXO",headerVars.DIMEXO,40);
        out_json.FIELD_BD(dat,"DIMDLI",headerVars.DIMDLI,40);
        out_json.FIELD_BD(dat,"DIMEXE",headerVars.DIMEXE,40);
        out_json.FIELD_BD(dat,"DIMRND",headerVars.DIMRND,40);
        out_json.FIELD_BD(dat,"DIMDLE",headerVars.DIMDLE,40);
        out_json.FIELD_BD(dat,"DIMTP",headerVars.DIMTP,40);
        out_json.FIELD_BD(dat,"DIMTM",headerVars.DIMTM,40);
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            out_json.FIELD_BD(dat,"DIMFXL",headerVars.DIMFXL,40);
            out_json.FIELD_BD(dat,"DIMJOGANG",headerVars.DIMJOGANG,40);
            out_json.FIELD_BD(dat,"DIMTFILL",headerVars.DIMTFILL,40);
            out_json.FIELD_CMC(dat,"",headerVars.DIMTFILLCLR,70);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            out_json.FIELD_B(dat,"DIMTOL",headerVars.DIMTOL,40);
            out_json.FIELD_B(dat,"DIMLIM",headerVars.DIMLIM,40);
            out_json.FIELD_B(dat,"DIMTIH",headerVars.DIMTIH,40);
            out_json.FIELD_B(dat,"DIMTOH",headerVars.DIMTOH,40);
            out_json.FIELD_B(dat,"DIMSE1",headerVars.DIMSE1,40);
            out_json.FIELD_B(dat,"DIMSE2",headerVars.DIMSE2,40);

            out_json.FIELD_BS(dat,"DIMTAD",headerVars.DIMTAD,40);
            out_json.FIELD_BS(dat,"DIMZIN",headerVars.DIMZIN,40);
            out_json.FIELD_BS(dat,"DIMAZIN",headerVars.DIMAZIN,40);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            out_json.FIELD_BS(dat,"DIMARCSYM",headerVars.DIMARCSYM,70);
        }
        out_json.FIELD_BD(dat,"DIMTXT",headerVars.DIMTXT,40);
        out_json.FIELD_BD(dat,"DIMCEN",headerVars.DIMCEN,40);
        out_json.FIELD_BD(dat,"DIMTSZ",headerVars.DIMTSZ,40);
        out_json.FIELD_BD(dat,"DIMALTF",headerVars.DIMALTF,40);
        out_json.FIELD_BD(dat,"DIMLFAC",headerVars.DIMLFAC,40);
        out_json.FIELD_BD(dat,"DIMTVP",headerVars.DIMTVP,40);
        out_json.FIELD_BD(dat,"DIMTFAC",headerVars.DIMTFAC,40);
        out_json.FIELD_BD(dat,"DIMGAP",headerVars.DIMGAP,40);

        if(specs.IF_FREE_OR_VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            out_json.FIELD_TV(dat,"DIMPOST",headerVars.DIMPOST,1);
            out_json.FIELD_TV(dat,"DIMAPOST",headerVars.DIMAPOST,1);
            out_json.FIELD_TV(dat,"DIMBLK_T",headerVars.DIMBLK_T,1);
            out_json.FIELD_TV(dat,"DIMBLK1_T",headerVars.DIMBLK1_T,1);
            out_json.FIELD_TV(dat,"DIMBLK2_T",headerVars.DIMBLK2_T,1);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            out_json.FIELD_BD(dat,"DIMALTRND",headerVars.DIMALTRND,40);
            out_json.FIELD_B(dat,"DIMALT",headerVars.DIMALT,70);
            out_json.FIELD_BS(dat,"DIMALTD",headerVars.DIMALTD,70);
            out_json.FIELD_B(dat,"DIMTOFL",headerVars.DIMTOFL,70);
            out_json.FIELD_B(dat,"DIMSAH",headerVars.DIMSAH,70);
            out_json.FIELD_B(dat,"DIMTIX",headerVars.DIMTIX,70);
            out_json.FIELD_B(dat,"DIMSOXD",headerVars.DIMSOXD,70);
        }
        out_json.FIELD_CMC(dat,"DIMCLRD",headerVars.DIMCLRD,70);
        out_json.FIELD_CMC(dat,"DIMCLRE",headerVars.DIMCLRE,70);
        out_json.FIELD_CMC(dat,"DIMCLRT",headerVars.DIMCLRT,70);

        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat)){
            out_json.FIELD_BS(dat,"DIMADEC",headerVars.DIMADEC,70);
            out_json.FIELD_BS(dat,"DIMDEC",headerVars.DIMDEC,70);
            out_json.FIELD_BS(dat,"DIMTDEC",headerVars.DIMTDEC,70);
            out_json.FIELD_BS(dat,"DIMALTU",headerVars.DIMALTU,70);
            out_json.FIELD_BS(dat,"DIMALTTD",headerVars.DIMALTTD,70);
            out_json.FIELD_BS(dat,"DIMAUNIT",headerVars.DIMAUNIT,70);
            out_json.FIELD_BS(dat,"DIMFRAC",headerVars.DIMFRAC,70);
            out_json.FIELD_BS(dat,"DIMLUNIT",headerVars.DIMLUNIT,70);
            out_json.FIELD_BS(dat,"DIMDSEP",headerVars.DIMDSEP,70);
            out_json.FIELD_BS(dat,"DIMTMOVE",headerVars.DIMTMOVE,70);
            out_json.FIELD_BS(dat,"DIMJUST",headerVars.DIMJUST,70);
            out_json.FIELD_B(dat,"DIMSD1",headerVars.DIMSD1,70);
            out_json.FIELD_B(dat,"DIMSD2",headerVars.DIMSD2,70);
            out_json.FIELD_BS(dat,"DIMTOLJ",headerVars.DIMTOLJ,70);
            out_json.FIELD_BS(dat,"DIMTZIN",headerVars.DIMTZIN,70);
            out_json.FIELD_BS(dat,"DIMALTZ",headerVars.DIMALTZ,70);
            out_json.FIELD_BS(dat,"DIMALTTZ",headerVars.DIMALTTZ,70);
            out_json.FIELD_B(dat,"DIMUPT",headerVars.DIMUPT,70);
            out_json.FIELD_BS(dat,"DIMATFIT",headerVars.DIMATFIT,70);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            out_json.FIELD_B(dat,"DIMFXLON",headerVars.DIMFXLON,70);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2010b,dat))
        {
            out_json.FIELD_B(dat,"DIMTXTDIRECTION",headerVars.DIMTXTDIRECTION,70);
            out_json.FIELD_BD(dat,"DIMALTMZF",headerVars.DIMALTMZF,40);
            out_json.FIELD_BD(dat,"DIMMZF",headerVars.DIMMZF,40);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {;
            out_json.FIELD_HANDLE(hdl_dat,"DIMTXSTY",headerVars.DIMTXSTY,5,7);
            out_json.FIELD_HANDLE(hdl_dat,"DIMLDRBLK",headerVars.DIMLDRBLK,5,1);
            out_json.FIELD_HANDLE(hdl_dat,"DIMBLK",headerVars.DIMBLK,5,1);
            out_json.FIELD_HANDLE(hdl_dat,"DIMBLK1",headerVars.DIMBLK1,5,1);
            out_json.FIELD_HANDLE(hdl_dat,"DIMBLK2",headerVars.DIMBLK2,5,1);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            out_json.FIELD_HANDLE(hdl_dat,"DIMLTYPE",headerVars.DIMLTYPE,5,6);
            out_json.FIELD_HANDLE(hdl_dat,"DIMLTEX1",headerVars.DIMLTEX1,5,6);
            out_json.FIELD_HANDLE(hdl_dat,"DIMLTEX2",headerVars.DIMLTEX2,5,6);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            out_json.FIELD_BSd(dat,"DIMLWD",headerVars.DIMLWD,70);
            out_json.FIELD_BSd(dat,"DIMLWE",headerVars.DIMLWE,70);
        }

        out_json.FIELD_HANDLE(hdl_dat,"BLOCK_CONTROL_OBJECT",headerVars.BLOCK_CONTROL_OBJECT,3,0);
        out_json.FIELD_HANDLE(hdl_dat,"LAYER_CONTROL_OBJECT",headerVars.LAYER_CONTROL_OBJECT,3,0);
        out_json.FIELD_HANDLE(hdl_dat,"STYLE_CONTROL_OBJECT",headerVars.STYLE_CONTROL_OBJECT,3,0);
        out_json.FIELD_HANDLE(hdl_dat,"LTYPE_CONTROL_OBJECT",headerVars.LTYPE_CONTROL_OBJECT,3,0);
        out_json.FIELD_HANDLE(hdl_dat,"VIEW_CONTROL_OBJECT",headerVars.VIEW_CONTROL_OBJECT,3,0);
        out_json.FIELD_HANDLE(hdl_dat,"UCS_CONTROL_OBJECT",headerVars.UCS_CONTROL_OBJECT,3,0);
        out_json.FIELD_HANDLE(hdl_dat,"VPORT_CONTROL_OBJECT",headerVars.VPORT_CONTROL_OBJECT,3,0);
        out_json.FIELD_HANDLE(hdl_dat,"APPID_CONTROL_OBJECT",headerVars.APPID_CONTROL_OBJECT,3,0);
        out_json.FIELD_HANDLE(hdl_dat,"DIMSTYLE_CONTROL_OBJECT",headerVars.DIMSTYLE_CONTROL_OBJECT,3,0);

        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_2000,dat))
        {
            out_json.FIELD_HANDLE(hdl_dat,"VX_CONTROL_OBJECT",headerVars.VX_CONTROL_OBJECT,3,0);
        }
        out_json.FIELD_HANDLE(hdl_dat,"DICTIONARY_ACAD_GROUP",headerVars.DICTIONARY_ACAD_GROUP,5,0);
        out_json.FIELD_HANDLE(hdl_dat,"DICTIONARY_ACAD_MLINESTYLE",headerVars.DICTIONARY_ACAD_MLINESTYLE,5,0);
        out_json.FIELD_HANDLE(hdl_dat,"DICTIONARY_NAMED_OBJECT",headerVars.DICTIONARY_NAMED_OBJECT,5,0);
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            if(specs.IF_ENCODE_FROM_EARLIER_OR_DXF)
            {
                headerVars.TSTACKALIGN = 1;
                headerVars.TSTACKSIZE = 70;
            }
            out_json.FIELD_BS(dat,"TSTACKALIGN",headerVars.TSTACKALIGN,70);
            out_json.FIELD_BS(dat,"TSTACKSIZE",headerVars.TSTACKSIZE,70);
            if(commen.PRE(DWG_VERSION_TYPE.R_2007a,dat))
            {
                out_json.FIELD_TV(dat,"HYPERLINKBASE",headerVars.HYPERLINKBASE,1);
                out_json.FIELD_TV(dat,"STYLESHEET",headerVars.STYLESHEET,1);
            }
            out_json.FIELD_HANDLE(hdl_dat,"DICTIONARY_LAYOUT",headerVars.DICTIONARY_LAYOUT,5,0);
            out_json.FIELD_HANDLE(hdl_dat,"DICTIONARY_PLOTSETTINGS",headerVars.DICTIONARY_PLOTSETTINGS,5,0);
            out_json.FIELD_HANDLE(hdl_dat,"DICTIONARY_PLOTSTYLENAME",headerVars.DICTIONARY_PLOTSTYLENAME,5,0);
        }
        if (commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {

            out_json.FIELD_HANDLE(hdl_dat,"DICTIONARY_MATERIAL",headerVars.DICTIONARY_MATERIAL,5,0);
            out_json.FIELD_HANDLE(hdl_dat,"DICTIONARY_COLOR",headerVars.DICTIONARY_COLOR,5,0);
        }
        if (commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            out_json.FIELD_HANDLE(hdl_dat,"DICTIONARY_VISUALSTYLE",headerVars.DICTIONARY_VISUALSTYLE,5,0);
        }
        if (commen.SINCE(DWG_VERSION_TYPE.R_2013b,dat))
        {
            out_json.FIELD_HANDLE(hdl_dat,"unknown_20",headerVars.unknown_20,5,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            if(specs.ENCODER)
            {
                // unneeded here. done in in_dxf.c:1189
                //FIELD_VALUE (FLAGS) |= dxf_revcvt_lweight (FIELD_VALUE (CELWEIGHT));
                //if (FIELD_VALUE (ENDCAPS)) FIELD_VALUE (FLAGS) |= 0x60;
                // ...
            }
            out_json.FIELD_BLx(dat,"FLAGS",headerVars.FLAGS,70);
            if(specs.DECODER)
            {
                headerVars.CELWEIGHT = dwg.dxf_cvt_lweight((short)(headerVars.FLAGS & 0x1f));
                //FIELD_G_TRACE (CELWEIGHT, BSd, 370) // default: -1 ByLayer
                headerVars.ENDCAPS = (char)((headerVars.FLAGS & 0x60) != 0 ? 1 : 0);
                //FIELD_G_TRACE (ENDCAPS, RC, 280)
                headerVars.JOINSTYLE = (char)((headerVars.FLAGS & 0x180) != 0 ? 1 : 0);
                headerVars.LWDISPLAY = (char)((headerVars.FLAGS & 0x200) != 0 ? 1 : 0);
                headerVars.XEDIT = (char)((headerVars.FLAGS & 0x400) != 0 ? 0 : 1);
                headerVars.EXTNAMES = (char)((headerVars.FLAGS & 0x800) != 0 ? 1 : 0);
                headerVars.PSTYLEMODE = (char)((headerVars.FLAGS & 0x2000) != 0 ? 1 : 0);
                headerVars.OLESTARTUP = (char)((headerVars.FLAGS & 0x4000) != 0 ? 1 : 0);
            }
            out_json.FIELD_BS(dat,"INSUNITS",headerVars.INSUNITS,70);
            out_json.FIELD_BS(dat,"CEPSNTYPE",headerVars.CEPSNTYPE,70);
            if(headerVars.CEPSNTYPE == 3)
            {
                out_json.FIELD_HANDLE(hdl_dat,"CPSNID",headerVars.CPSNID,5,0);
            }
            if(commen.PRE(DWG_VERSION_TYPE.R_2007a,dat))
            {
                out_json.FIELD_TV(dat,"FINGERPRINTGUID",headerVars.FINGERPRINTGUID,0);
                out_json.FIELD_TV(dat,"VERSIONGUID",headerVars.VERSIONGUID,0);
            }
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {
            out_json.FIELD_RC("SORTENTS",headerVars.SORTENTS,dat,280);
            out_json.FIELD_RC("INDEXCTL",headerVars.INDEXCTL,dat,280);
            out_json.FIELD_RC("HIDETEXT",headerVars.HIDETEXT,dat,280);
            out_json.FIELD_RC("XCLIPFRAME",headerVars.XCLIPFRAME,dat,290);
            out_json.FIELD_RC("DIMASSOC",headerVars.DIMASSOC,dat,280);
            out_json.FIELD_RC("HALOGAP",headerVars.HALOGAP,dat,280);
            headerVars.OBSCOLOR = dec_macros.FIELD_BS(dat,"BS",280);
            headerVars.INTERSECTIONCOLOR = dec_macros.FIELD_BS(dat,"BS",280);
            out_json.FIELD_RC("OBSLTYPE",headerVars.OBSLTYPE,dat,280);
            out_json.FIELD_BS(dat,"",headerVars.INTERSECTIONDISPLAY,290);
            out_json.FIELD_RC("INTERSECTIONDISPLAY",headerVars.INTERSECTIONDISPLAY,dat,290);
            if(commen.PRE(DWG_VERSION_TYPE.R_2007a,dat))
            {
                out_json.FIELD_TV(dat,"PROJECTNAME", headerVars.PROJECTNAME,1);
            }
        }
        out_json.FIELD_HANDLE(hdl_dat,"BLOCK_RECORD_PSPACE",headerVars.BLOCK_RECORD_PSPACE,5,0);
        out_json.FIELD_HANDLE(hdl_dat,"BLOCK_RECORD_MSPACE",headerVars.BLOCK_RECORD_MSPACE,5,0);
        out_json.FIELD_HANDLE(hdl_dat,"LTYPE_BYLAYER",headerVars.LTYPE_BYLAYER,5,0);
        out_json.FIELD_HANDLE(hdl_dat,"LTYPE_BYBLOCK",headerVars.LTYPE_BYBLOCK,5,0);
        out_json.FIELD_HANDLE(hdl_dat,"LTYPE_CONTINUOUS",headerVars.LTYPE_CONTINUOUS,5,0);

        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            if(specs.IF_ENCODE_FROM_EARLIER)
            {
                headerVars.STEPSPERSEC = 2.0;
                headerVars.STEPSIZE = 50.0;
                headerVars.LENSLENGTH = 50.0;
                headerVars._3DDWFPREC = 2.0;
                headerVars.PSOLWIDTH = 5.0;
                headerVars.PSOLHEIGHT = 80;
                headerVars.LOFTANG1 = commen.M_PI_2;
                headerVars.LOFTANG2 = commen.M_PI_2;
                headerVars.LOFTPARAM = 7;
                headerVars.LOFTNORMALS = 1;
                headerVars.LATITUDE = 1.0;
                headerVars.LONGITUDE = 1.0;
                headerVars.TIMEZONE = -8000;
                headerVars.LIGHTGLYPHDISPLAY = 1;
                headerVars.TILEMODELIGHTSYNCH = 1;
                headerVars.SOLIDHIST = 1;
                headerVars.SHOWHIST = 1;
                headerVars.DWFFRAME = 2;
                headerVars.REALWORLDSCALE = 1;
            }
            headerVars.CAMERADISPLAY = dec_macros.FIELD_B(dat,"B",290);
            headerVars.unknown_21 = dec_macros.FIELD_BL(dat,"BL",0);
            headerVars.unknown_22 = dec_macros.FIELD_BL(dat,"BL",0);
            headerVars.unknown_23 = dec_macros.FIELD_BD(dat,"BD",0);
            headerVars.STEPSPERSEC = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.STEPSIZE = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars._3DDWFPREC = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.LENSLENGTH = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.CAMERAHEIGHT = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.SOLIDHIST = dec_macros.FIELD_RC(dat,"RC",40);
            headerVars.SHOWHIST = dec_macros.FIELD_RC(dat,"RC",40);
            headerVars.PSOLWIDTH = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.PSOLHEIGHT = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.LOFTANG1 = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.LOFTANG2 = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.LOFTMAG1 = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.LOFTMAG2 = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.LOFTPARAM = dec_macros.FIELD_BS(dat,"BS",40);
            headerVars.LOFTNORMALS = dec_macros.FIELD_RC(dat,"RC",40);
            headerVars.LATITUDE = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.LONGITUDE = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.NORTHDIRECTION = dec_macros.FIELD_BD(dat,"BD",40);
            headerVars.TIMEZONE = dec_macros.FIELD_BLd(dat,"BLd","BL",40);
            headerVars.LIGHTGLYPHDISPLAY = dec_macros.FIELD_RC(dat,"RC",40);
            headerVars.TILEMODELIGHTSYNCH = dec_macros.FIELD_RC(dat,"RC",40);
            headerVars.DWFFRAME = dec_macros.FIELD_RC(dat,"RC",40);
            headerVars.DGNFRAME = dec_macros.FIELD_RC(dat,"RC",40);
            headerVars.REALWORLDSCALE = dec_macros.FIELD_B(dat,"B",40);
            headerVars.INTERFERECOLOR = dec_macros.FIELD_CMC(dat,str_dat,40);
            headerVars.INTERFEREOBJVS = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,345);
            headerVars.INTERFEREVPVS = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,346);
            headerVars.DRAGVS = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,349);
            headerVars.CSHADOW = dec_macros.FIELD_RC(dat,"RC",280);
            headerVars.SHADOWPLANELOCATION = dec_macros.FIELD_BD(dat,"BD",40);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_14,dat))
        {
            out_json.FIELD_BS(dat,"unknown_54",headerVars.unknown_54,0);
            out_json.FIELD_BS(dat,"unknown_55",headerVars.unknown_55,0);
            out_json.FIELD_BS(dat,"unknown_56",headerVars.unknown_56,0);
            out_json.FIELD_BS(dat,"unknown_57",headerVars.unknown_57,0);
        }

        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            /**
             * // TODO split str_dat stream and get rid of this block
             *     SECTION_STRING_STREAM
             *     FIELD_T (unit1_name, 0);
             *     FIELD_T (unit2_name, 0);
             *     FIELD_T (unit3_name, 0);
             *     FIELD_T (unit4_name, 0);
             *     FIELD_T (MENU, 1);
             *     FIELD_T (DIMPOST, 1);
             *     FIELD_T (DIMAPOST, 1);
             *     SINCE (R_2010b) {
             *       FIELD_T (DIMALTMZS, 1);
             *       FIELD_T (DIMMZS, 1);
             *     }
             *     FIELD_T (HYPERLINKBASE, 1); // see SummaryInfo
             *     FIELD_T (STYLESHEET, 1);
             *     FIELD_T (FINGERPRINTGUID, 1);
             *     FIELD_T (VERSIONGUID, 1);
             *     FIELD_T (PROJECTNAME, 1);
             *     END_STRING_STREAM
             * */
        }
        return error;
    }
}
