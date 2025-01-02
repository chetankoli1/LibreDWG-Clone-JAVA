import java.io.IOException;

public class dwg_spec {
    static int dwg_decode_BLOCK_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                        Dwg_Data objDwgData,DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_BLOCK_CONTROL blcckcontroll = obj.tio.object.tio.BLOCK_CONTROL;
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            blcckcontroll.common.num_entries = dec_macros.FIELD_RS(dat,"RS",70);
            blcckcontroll.common.flags_r11 = dec_macros.FIELD_RS(dat,"RS",70);
            //JSON { KEY (address); }
            //VALUE_RLx (obj->address & 0xFFFFFFFF, 0);
        }
        else //LATER_VERSIONS
        {
            blcckcontroll.common.num_entries = (int)dec_macros.FIELD_BL(dat,"BL",70);
        }
        obj.tio.object.ownerhandle = new Dwg_Object_Ref();
        dec_macros.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        if( blcckcontroll.common.entres == null){
            blcckcontroll.common.entres = new Dwg_Object_Ref[ blcckcontroll.common.num_entries];
        }
        blcckcontroll.common.entres = dec_macros.HANDLE_VECTOR(hdl_dat,blcckcontroll.common.num_entries,2,obj,objDwgData,0);
        blcckcontroll.model_space = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            blcckcontroll.paper_space = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        }
        return dec_macros.DWG_OBJECT_END(dat, hdl_dat, str_dat, obj, error);
    }

    static int dwg_json_BLOCK_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                       Dwg_Data objDwgData, DWG_OBJECT_TYPE type) throws IOException
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain();
        Bit_Chain str_dat = new Bit_Chain(dat);
        out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_BLOCK_CONTROL blcckcontroll = obj.tio.object.tio.BLOCK_CONTROL;
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_RS(dat,blcckcontroll.common.num_entries,"num_entries",70);
            out_json.FIELD_RS(dat,blcckcontroll.common.flags_r11,"flags_r11",70);
        }
        else //LATER_VERSIONS
        {
            out_json.FIELD_BL(dat,"num_entries",blcckcontroll.common.num_entries,70);
        }
        out_json.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        out_json.HANDLE_VECTOR(dat,blcckcontroll.common.entres,"entries",blcckcontroll.common.num_entries,2,0);
        out_json.FIELD_HANDLE(dat,"model_space",blcckcontroll.model_space,3,0);
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_HANDLE(dat,"paper_space",blcckcontroll.paper_space,3,0);
        }
        return error;
    }

    static int dwg_decode_LAYER_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                        Dwg_Data objDwgData,DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_LAYER_CONTROL layerControl = obj.tio.object.tio.LAYER_CONTROL;

        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            layerControl.common.num_entries = dec_macros.FIELD_RS(dat,"RS",70);
            layerControl.common.flags_r11 = dec_macros.FIELD_RS(dat,"RS",70);
            //JSON { KEY (address); }
            //VALUE_RLx (obj->address & 0xFFFFFFFF, 0);
        }
        else //LATER_VERSIONS
        {
            layerControl.common.num_entries = (int)dec_macros.FIELD_BL(dat,"BL",70);
        }
        obj.tio.object.ownerhandle = new Dwg_Object_Ref();
        dec_macros.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        if( layerControl.common.entres == null){
            layerControl.common.entres = new Dwg_Object_Ref[ layerControl.common.num_entries];
        }
        layerControl.common.entres = dec_macros.HANDLE_VECTOR(hdl_dat,layerControl.common.num_entries,2,obj,objDwgData,0);
        return error;
    }

    static int dwg_json_LAYER_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                      Dwg_Data objDwgData, DWG_OBJECT_TYPE type) throws IOException
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain();
        Bit_Chain str_dat = new Bit_Chain(dat);
        out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_LAYER_CONTROL layerControl = obj.tio.object.tio.LAYER_CONTROL;
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_RS(dat,layerControl.common.num_entries,"num_entries",70);
            out_json.FIELD_RS(dat,layerControl.common.flags_r11,"flags_r11",70);
        }
        else //LATER_VERSIONS
        {
            out_json.FIELD_BL(dat,"num_entries",layerControl.common.num_entries,70);
        }
        out_json.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        out_json.HANDLE_VECTOR(dat,layerControl.common.entres,"entries",layerControl.common.num_entries,2,0);
        return error;
    }

    static int dwg_decode_STYLE_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                        Dwg_Data objDwgData,DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_STYLE_CONTROL styleControl = obj.tio.object.tio.STYLE_CONTROL;

        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            styleControl.common.num_entries = dec_macros.FIELD_RS(dat,"RS",70);
            styleControl.common.flags_r11 = dec_macros.FIELD_RS(dat,"RS",70);
            //JSON { KEY (address); }
            //VALUE_RLx (obj->address & 0xFFFFFFFF, 0);
        }
        else //LATER_VERSIONS
        {
            styleControl.common.num_entries = (int)dec_macros.FIELD_BL(dat,"BL",70);
        }
        obj.tio.object.ownerhandle = new Dwg_Object_Ref();
        dec_macros.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        if( styleControl.common.entres == null){
            styleControl.common.entres = new Dwg_Object_Ref[ styleControl.common.num_entries];
        }
        styleControl.common.entres = dec_macros.HANDLE_VECTOR(hdl_dat,styleControl.common.num_entries,2,obj,objDwgData,0);
        return error;
    }

    static int dwg_json_STYLE_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                      Dwg_Data objDwgData, DWG_OBJECT_TYPE type) throws IOException
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain();
        Bit_Chain str_dat = new Bit_Chain(dat);
        out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_STYLE_CONTROL styleControl = obj.tio.object.tio.STYLE_CONTROL;
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_RS(dat,styleControl.common.num_entries,"num_entries",70);
            out_json.FIELD_RS(dat,styleControl.common.flags_r11,"flags_r11",70);
        }
        else //LATER_VERSIONS
        {
            out_json.FIELD_BL(dat,"num_entries",styleControl.common.num_entries,70);
        }
        out_json.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        out_json.HANDLE_VECTOR(dat,styleControl.common.entres,"entries",styleControl.common.num_entries,2,0);
        return error;
    }

    static int dwg_decode_LTYPE_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                        Dwg_Data objDwgData,DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_LTYPE_CONTROL ltypeControl = obj.tio.object.tio.LTYPE_CONTROL;
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            ltypeControl.common.num_entries = dec_macros.FIELD_RS(dat,"RS",70);
            ltypeControl.common.flags_r11 = dec_macros.FIELD_RS(dat,"RS",70);
            //JSON { KEY (address); }
            obj.address = dec_macros.VALUE_RLx(dat,"RLx",0) & 0xFFFFFFFFL;
        }
        else //LATER_VERSIONS
        {
            ltypeControl.common.num_entries = (int)dec_macros.FIELD_BL(dat,"BL",70);
        }
        obj.tio.object.ownerhandle = new Dwg_Object_Ref();
        dec_macros.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        if( ltypeControl.common.entres == null){
            ltypeControl.common.entres = new Dwg_Object_Ref[ ltypeControl.common.num_entries];
        }
        ltypeControl.common.entres = dec_macros.HANDLE_VECTOR(hdl_dat,ltypeControl.common.num_entries,2,obj,objDwgData,0);

        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            ltypeControl.byblock = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
            ltypeControl.bylayer = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        }
        return dec_macros.DWG_OBJECT_END(dat, hdl_dat, str_dat, obj, error);
    }

    static int dwg_json_LTYPE_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                      Dwg_Data objDwgData, DWG_OBJECT_TYPE type) throws IOException
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain();
        Bit_Chain str_dat = new Bit_Chain(dat);
        out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_LTYPE_CONTROL ltypeControl = obj.tio.object.tio.LTYPE_CONTROL;
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_RS(dat,ltypeControl.common.num_entries,"num_entries",70);
            out_json.FIELD_RS(dat,ltypeControl.common.flags_r11,"flags_r11",70);
        }
        else //LATER_VERSIONS
        {
            out_json.FIELD_BL(dat,"num_entries",ltypeControl.common.num_entries,70);
        }
        out_json.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        out_json.HANDLE_VECTOR(dat,ltypeControl.common.entres,"entries",ltypeControl.common.num_entries,2,0);

        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_HANDLE(dat,"byblock",ltypeControl.byblock,3,0);
            out_json.FIELD_HANDLE(dat,"bylayer",ltypeControl.bylayer,3,0);
        }
        return error;
    }

    static int dwg_decode_VIEW_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                        Dwg_Data objDwgData,DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_VIEW_CONTROL viewControl = obj.tio.object.tio.VIEW_CONTROL;

        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            viewControl.common.num_entries = dec_macros.FIELD_RS(dat,"RS",70);
            viewControl.common.flags_r11 = dec_macros.FIELD_RS(dat,"RS",70);
            //JSON { KEY (address); }
            obj.address = dec_macros.VALUE_RLx(dat,"RLx",0) & 0xFFFFFFFFL;
        }
        else //LATER_VERSIONS
        {
            viewControl.common.num_entries = (int)dec_macros.FIELD_BL(dat,"BL",70);
        }
        obj.tio.object.ownerhandle = new Dwg_Object_Ref();
        dec_macros.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        if( viewControl.common.entres == null){
            viewControl.common.entres = new Dwg_Object_Ref[ viewControl.common.num_entries];
        }
        viewControl.common.entres = dec_macros.HANDLE_VECTOR(hdl_dat,viewControl.common.num_entries,2,obj,objDwgData,0);
        return error;
    }

    static int dwg_json_VIEW_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                      Dwg_Data objDwgData, DWG_OBJECT_TYPE type) throws IOException
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain();
        Bit_Chain str_dat = new Bit_Chain(dat);
        out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_VIEW_CONTROL viewControl = obj.tio.object.tio.VIEW_CONTROL;
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_RS(dat,viewControl.common.num_entries,"num_entries",70);
            out_json.FIELD_RS(dat,viewControl.common.flags_r11,"flags_r11",70);
        }
        else //LATER_VERSIONS
        {
            out_json.FIELD_BL(dat,"num_entries",viewControl.common.num_entries,70);
        }
        out_json.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        out_json.HANDLE_VECTOR(dat,viewControl.common.entres,"entries",viewControl.common.num_entries,2,0);
        return error;
    }
    static int dwg_decode_UCS_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                        Dwg_Data objDwgData,DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_UCS_CONTROL ucsControl = obj.tio.object.tio.UCS_CONTROL;

        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            ucsControl.common.num_entries = dec_macros.FIELD_RS(dat,"RS",70);
            ucsControl.common.flags_r11 = dec_macros.FIELD_RS(dat,"RS",70);
            //JSON { KEY (address); }
            obj.address = dec_macros.VALUE_RLx(dat,"RLx",0) & 0xFFFFFFFFL;
        }
        else //LATER_VERSIONS
        {
            ucsControl.common.num_entries = (int)dec_macros.FIELD_BL(dat,"BL",70);
        }
        obj.tio.object.ownerhandle = new Dwg_Object_Ref();
        dec_macros.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        if( ucsControl.common.entres == null){
            ucsControl.common.entres = new Dwg_Object_Ref[ ucsControl.common.num_entries];
        }
        ucsControl.common.entres = dec_macros.HANDLE_VECTOR(hdl_dat,ucsControl.common.num_entries,2,obj,objDwgData,0);
        return error;
    }

    static int dwg_json_UCS_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                      Dwg_Data objDwgData, DWG_OBJECT_TYPE type) throws IOException
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain();
        Bit_Chain str_dat = new Bit_Chain(dat);
        out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_UCS_CONTROL ucsControl = obj.tio.object.tio.UCS_CONTROL;
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_RS(dat,ucsControl.common.num_entries,"num_entries",70);
            out_json.FIELD_RS(dat,ucsControl.common.flags_r11,"flags_r11",70);
        }
        else //LATER_VERSIONS
        {
            out_json.FIELD_BL(dat,"num_entries",ucsControl.common.num_entries,70);
        }
        out_json.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        out_json.HANDLE_VECTOR(dat,ucsControl.common.entres,"entries",ucsControl.common.num_entries,2,0);
        return error;
    }

    static int dwg_decode_VPORT_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                        Dwg_Data objDwgData,DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_VPORT_CONTROL vportControl = obj.tio.object.tio.VPORT_CONTROL;
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            vportControl.common.num_entries = dec_macros.FIELD_RS(dat,"RS",70);
            vportControl.common.flags_r11 = dec_macros.FIELD_RS(dat,"RS",70);
            //JSON { KEY (address); }
            obj.address = dec_macros.VALUE_RLx(dat,"RLx",0) & 0xFFFFFFFFL;
        }
        else //LATER_VERSIONS
        {
            vportControl.common.num_entries = (int)dec_macros.FIELD_BL(dat,"BL",70);
        }
        obj.tio.object.ownerhandle = new Dwg_Object_Ref();
        dec_macros.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        if( vportControl.common.entres == null){
            vportControl.common.entres = new Dwg_Object_Ref[ vportControl.common.num_entries];
        }
        vportControl.common.entres = dec_macros.HANDLE_VECTOR(hdl_dat,vportControl.common.num_entries,2,obj,objDwgData,0);
        return dec_macros.DWG_OBJECT_END(dat, hdl_dat, str_dat, obj, error);
    }

    static int dwg_json_VPORT_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                      Dwg_Data objDwgData, DWG_OBJECT_TYPE type) throws IOException
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain();
        Bit_Chain str_dat = new Bit_Chain(dat);
        out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_VPORT_CONTROL vportControl = obj.tio.object.tio.VPORT_CONTROL;
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_RS(dat,vportControl.common.num_entries,"num_entries",70);
            out_json.FIELD_RS(dat,vportControl.common.flags_r11,"flags_r11",70);
        }
        else //LATER_VERSIONS
        {
            out_json.FIELD_BL(dat,"num_entries",vportControl.common.num_entries,70);
        }
        out_json.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        out_json.HANDLE_VECTOR(dat,vportControl.common.entres,"entries",vportControl.common.num_entries,2,0);
        return error;
    }

    static int dwg_decode_APPID_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                        Dwg_Data objDwgData,DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_APPID_CONTROL appidControl = obj.tio.object.tio.APPID_CONTROL;
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            appidControl.common.num_entries = dec_macros.FIELD_RS(dat,"RS",70);
            appidControl.common.flags_r11 = dec_macros.FIELD_RS(dat,"RS",70);
            //JSON { KEY (address); }
            obj.address = dec_macros.VALUE_RLx(dat,"RLx",0) & 0xFFFFFFFFL;
        }
        else //LATER_VERSIONS
        {
            appidControl.common.num_entries = (int)dec_macros.FIELD_BL(dat,"BL",70);
        }
        obj.tio.object.ownerhandle = new Dwg_Object_Ref();
        dec_macros.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        if( appidControl.common.entres == null){
            appidControl.common.entres = new Dwg_Object_Ref[ appidControl.common.num_entries];
        }
        appidControl.common.entres = dec_macros.HANDLE_VECTOR(hdl_dat,appidControl.common.num_entries,2,obj,objDwgData,0);
        return dec_macros.DWG_OBJECT_END(dat, hdl_dat, str_dat, obj, error);
    }

    static int dwg_json_APPID_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                      Dwg_Data objDwgData, DWG_OBJECT_TYPE type) throws IOException
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain();
        Bit_Chain str_dat = new Bit_Chain(dat);
        out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_APPID_CONTROL appidControl = obj.tio.object.tio.APPID_CONTROL;
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_RS(dat,appidControl.common.num_entries,"num_entries",70);
            out_json.FIELD_RS(dat,appidControl.common.flags_r11,"flags_r11",70);
        }
        else //LATER_VERSIONS
        {
            out_json.FIELD_BL(dat,"num_entries",appidControl.common.num_entries,70);
        }
        out_json.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        out_json.HANDLE_VECTOR(dat,appidControl.common.entres,"entries",appidControl.common.num_entries,2,0);
        return error;
    }

    static int dwg_decode_DIMSTYLE_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                        Dwg_Data objDwgData,DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_DIMSTYLE_CONTROL dimstyleControl = obj.tio.object.tio.DIMSTYLE_CONTROL;
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            dimstyleControl.common.num_entries = dec_macros.FIELD_RS(dat,"RS",70);
            dimstyleControl.common.flags_r11 = dec_macros.FIELD_RS(dat,"RS",70);
            //JSON { KEY (address); }
            obj.address = dec_macros.VALUE_RLx(dat,"RLx",0) & 0xFFFFFFFFL;
        }
        else //LATER_VERSIONS
        {
            dimstyleControl.common.num_entries = (int)dec_macros.FIELD_BL(dat,"BL",70);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
           // SUBCLASS (AcDbDimStyleTable)
            dimstyleControl.num_morehandles = dec_macros.FIELD_RCu(dat,71);
        }
        obj.tio.object.ownerhandle = new Dwg_Object_Ref();
        dec_macros.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        if( dimstyleControl.common.entres == null){
            dimstyleControl.common.entres = new Dwg_Object_Ref[ dimstyleControl.common.num_entries];
        }
        dimstyleControl.common.entres = dec_macros.HANDLE_VECTOR(hdl_dat,dimstyleControl.common.num_entries,2,obj,objDwgData,0);
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            dimstyleControl.morehandles = dec_macros.HANDLE_VECTOR(hdl_dat,dimstyleControl.num_morehandles,5,obj,objDwgData,71);
        }
        return dec_macros.DWG_OBJECT_END(dat, hdl_dat, str_dat, obj, error);
    }

    static int dwg_json_DIMSTYLE_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                      Dwg_Data objDwgData, DWG_OBJECT_TYPE type) throws IOException
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain();
        Bit_Chain str_dat = new Bit_Chain(dat);
        out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_DIMSTYLE_CONTROL dimstyleControl = obj.tio.object.tio.DIMSTYLE_CONTROL;

        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_RS(dat,dimstyleControl.common.num_entries,"num_entries",70);
            out_json.FIELD_RS(dat,dimstyleControl.common.flags_r11,"flags_r11",70);
        }
        else //LATER_VERSIONS
        {
            out_json.FIELD_BL(dat,"num_entries",dimstyleControl.common.num_entries,70);
        }
        out_json.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            out_json.SUBCLASS(dat,"AcDbDimStyleTable");
            out_json.FIELD_RCu(dat,"num_morehandles",dimstyleControl.num_morehandles,0);
        }

        out_json.HANDLE_VECTOR(dat,dimstyleControl.common.entres,"entries",dimstyleControl.common.num_entries,2,0);
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.HANDLE_VECTOR(dat,dimstyleControl.morehandles,"morehandles",dimstyleControl.num_morehandles,5,0);
        }
        return error;
    }
    static int dwg_decode_VX_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                        Dwg_Data objDwgData,DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_VX_CONTROL vxControl = obj.tio.object.tio.VX_CONTROL;
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            vxControl.common.num_entries = dec_macros.FIELD_RS(dat,"RS",70);
            vxControl.common.flags_r11 = dec_macros.FIELD_RS(dat,"RS",70);
            //JSON { KEY (address); }
            obj.address = dec_macros.VALUE_RLx(dat,"RLx",0) & 0xFFFFFFFFL;
        }
        else //LATER_VERSIONS
        {
            vxControl.common.num_entries = (int)dec_macros.FIELD_BL(dat,"BL",70);
        }
        obj.tio.object.ownerhandle = new Dwg_Object_Ref();
        dec_macros.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);

        if( vxControl.common.entres == null){
            vxControl.common.entres = new Dwg_Object_Ref[ vxControl.common.num_entries];
        }
        vxControl.common.entres = dec_macros.HANDLE_VECTOR(hdl_dat,vxControl.common.num_entries,2,obj,objDwgData,0);

        return dec_macros.DWG_OBJECT_END(dat, hdl_dat, str_dat, obj, error);
    }

    static int dwg_json_VX_CONTROL(String name, Dwg_Object obj, Bit_Chain dat,
                                      Dwg_Data objDwgData, DWG_OBJECT_TYPE type) throws IOException
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain();
        Bit_Chain str_dat = new Bit_Chain(dat);
        out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_VX_CONTROL vxControl = obj.tio.object.tio.VX_CONTROL;

        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_RS(dat,vxControl.common.num_entries,"num_entries",70);
            out_json.FIELD_RS(dat,vxControl.common.flags_r11,"flags_r11",70);
        }
        else //LATER_VERSIONS
        {
            out_json.FIELD_BL(dat,"num_entries",vxControl.common.num_entries,70);
        }
        out_json.CONTROL_HANDLE_STREAM(obj, hdl_dat, dat, objDwgData,obj.tio.object.ownerhandle);
        out_json.SUBCLASS(dat,"AcDbVXTable");

        out_json.HANDLE_VECTOR(dat,vxControl.common.entres,"entries",vxControl.common.num_entries,2,0);
        return error;
    }

    static int dwg_decode_DICTIONARY(String name, Dwg_Object obj, Bit_Chain dat,
                                     Dwg_Data objDwgData, DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_DICTIONARY dictionary = obj.tio.object.tio.DICTIONARY;

        if(macros.IS_DXF)
        {
            if(commen.SINCE(DWG_VERSION_TYPE.R_13c3,dat))
            {
                dictionary.is_hardowner = dec_macros.FIELD_RC0(dat,"RC",280);
            }
            if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
            {
                dictionary.cloning = dec_macros.FIELD_RC0(dat,"RC",281);
            }
        }
        dictionary.numitems = dec_macros.FIELD_BL(dat,"BL",0);
        if(commen.SINCE(DWG_VERSION_TYPE.R_13c3,dat))
        {
            if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
            {
                dictionary.cloning = dec_macros.FIELD_BS(dat,"BS",281);
            }
            if(dat.version.ordinal() != DWG_VERSION_TYPE.R_13c3.ordinal() || objDwgData.header.is_maint > 4)
            {
                dictionary.is_hardowner = dec_macros.FIELD_RC(dat,"RC",280);
            }
        }
        if(dictionary.texts == null)
        {
            dictionary.texts = new String[(int)dictionary.numitems];
        }
        dictionary.texts = dec_macros.FIELD_VECTOR_T(dat,"T",dictionary.numitems,3);
        dec_macros.START_OBJECT_HANDLE_STREAM(dat,obj);

        if(dictionary.itemhandles == null)
        {
            dictionary.itemhandles = new Dwg_Object_Ref[(int)dictionary.numitems];
        }
        dictionary.itemhandles = dec_macros.HANDLE_VECTOR(hdl_dat,(int)dictionary.numitems,2,obj,objDwgData,350);
        return dec_macros.DWG_OBJECT_END(dat, hdl_dat, str_dat, obj, error);
    }

    static int dwg_json_DICTIONARY(String name, Dwg_Object obj, Bit_Chain dat,
                                     Dwg_Data objDwgData, DWG_OBJECT_TYPE type) throws IOException {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain();
        Bit_Chain str_dat = new Bit_Chain(dat);
        out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_DICTIONARY dictionary = obj.tio.object.tio.DICTIONARY;

       // out_json.SUBCLASS(dat,"AcDbDictionary");
        if(macros.IS_DXF)
        {
            if(commen.SINCE(DWG_VERSION_TYPE.R_13c3,dat))
            {
                out_json.FIELD_RC0("is_hardowner",dictionary.is_hardowner,dat,280);
            }
            if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
            {
                out_json.FIELD_RC0("cloning",(char)dictionary.cloning,dat,281);
            }
        }

        out_json.FIELD_BL(dat,"numitems",dictionary.numitems,0);
        if(commen.SINCE(DWG_VERSION_TYPE.R_13c3,dat))
        {
            if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
            {
                out_json.FIELD_BS(dat,"cloning",dictionary.cloning,281);
            }
            if(dat.version.ordinal() != DWG_VERSION_TYPE.R_13c3.ordinal() || objDwgData.header.is_maint > 4)
            {
                dictionary.is_hardowner = dec_macros.FIELD_RC(dat,"RC",280);
                out_json.FIELD_RC("is_hardowner",dictionary.is_hardowner, dat,280);
            }
        }

       if(macros.IS_JSON)
       {
           out_json.RECORD(dat,"items");
           for(int i = 0; i < dictionary.numitems; i++)
           {
               out_json.FIRSTPREFIX(dat);
               out_json.VALUE_TEXT(dat,dictionary.texts[i]);
               config.streamWriter.write(": ");
               out_json.VALUE_HANDLE(dat,dictionary.itemhandles[i]);
           }
           out_json.ENDRECORD(dat);
       }
//       else {
//           out_json.FIELD_V
//       }

        return error;
    }

    static int dwg_decode_DICTIONARYWDFLTL(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData, DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_DICTIONARYWDFLT dictionarywdflt = obj.tio.object.tio.DICTIONARYWDFLT;
        if(macros.IS_DXF)
        {
            if(dictionarywdflt.is_hardowner != 0)
            {
                dictionarywdflt.is_hardowner = bits.bit_read_RC(dat);
            }
            dictionarywdflt.cloning = bits.bit_read_BS(dat);
        }
        else {
            dictionarywdflt.numitems = bits.bit_read_BL(dat);
            dictionarywdflt.cloning = bits.bit_read_BS(dat);
            dictionarywdflt.is_hardowner = bits.bit_read_RC(dat);
        }

       if(macros.IS_DXF)
       {
           if(dictionarywdflt.numitems > 0 && dictionarywdflt.numitems < 10000)
           {

           }
       }

       if(macros.IS_JSON)
       {

       }
       else{
           if(dictionarywdflt.texts == null)
           {
               dictionarywdflt.texts = new String[(int)dictionarywdflt.numitems];
           }
           dictionarywdflt.texts = dec_macros.FIELD_VECTOR_T(dat,"T",dictionarywdflt.numitems,3);
       }
       dec_macros.START_OBJECT_HANDLE_STREAM(dat,obj);

       if(!macros.IS_DXF && !macros.IS_JSON)
       {
           if(dictionarywdflt.itemhandles == null)
           {
               dictionarywdflt.itemhandles = new Dwg_Object_Ref[(int)dictionarywdflt.numitems];
           }
           dictionarywdflt.itemhandles = dec_macros.HANDLE_VECTOR(hdl_dat,(int)dictionarywdflt.numitems,2,obj,objDwgData,0);
       }

       dictionarywdflt.defaultid = new Dwg_Object_Ref();
       dictionarywdflt.defaultid = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,340);

        return dec_macros.DWG_OBJECT_END(dat, hdl_dat, str_dat, obj, error);
    }
    static int dwg_json_DICTIONARYWDFLTL(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData, DWG_OBJECT_TYPE type)
            throws IOException {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain();
        Bit_Chain str_dat = new Bit_Chain(dat);
        error = out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_DICTIONARYWDFLT dictionarywdflt = obj.tio.object.tio.DICTIONARYWDFLT;
        if(macros.IS_DXF)
        {
            if(dictionarywdflt.is_hardowner != 0)
            {
                out_json.FIELD_RC("is_hardowner",dictionarywdflt.is_hardowner,dat,0);
            }
            out_json.FIELD_BS(dat,"cloning",dictionarywdflt.cloning,0);
        }
        else {
            out_json.FIELD_BL(dat,"numitems",dictionarywdflt.numitems,0);
            out_json.FIELD_BS(dat,"cloning",dictionarywdflt.cloning,0);
            out_json.FIELD_RC("is_hardowner",dictionarywdflt.is_hardowner,dat,0);
        }

        if(macros.IS_DXF)
        {
            if(dictionarywdflt.numitems > 0 && dictionarywdflt.numitems < 10000)
            {

            }
        }

        if(macros.IS_JSON)
        {
            out_json.RECORD(dat,"items");
            for(int i = 0; i < dictionarywdflt.numitems; i++)
            {
                out_json.FIRSTPREFIX(dat);
                out_json.VALUE_TEXT(dat,dictionarywdflt.texts[i]);
                config.streamWriter.write(": ");
                out_json.VALUE_HANDLE(dat,dictionarywdflt.itemhandles[i]);
            }
            out_json.ENDRECORD(dat);
        }
        else{
          //  dictionarywdflt.texts = dec_macros.FIELD_VECTOR_T(dat,"T",dictionarywdflt.numitems,3);
        }
       // dec_macros.START_OBJECT_HANDLE_STREAM(dat,obj);

        if(!macros.IS_DXF && !macros.IS_JSON)
        {

            out_json.HANDLE_VECTOR(dat,dictionarywdflt.itemhandles,"itemhandles",(int)dictionarywdflt.numitems,5,340);
        }
        out_json.SUBCLASS(dat,"AcDbDictionaryWithDefault");
        out_json.FIELD_HANDLE(dat,"defaultid",dictionarywdflt.defaultid,5,340);
        return error;
    }

    static int dwg_decode_PLACEHOLDER(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData, DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);

        return dec_macros.DWG_OBJECT_END(dat,hdl_dat,str_dat,obj,error);
    }
    static int dwg_json_PLACEHOLDER(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                                    DWG_OBJECT_TYPE type) throws IOException
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain();
        Bit_Chain str_dat = new Bit_Chain(dat);
        error = out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);

        return error;
    }
}
