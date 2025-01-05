import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;


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

        if( blcckcontroll.common.entries == null){
            blcckcontroll.common.entries = new Dwg_Object_Ref[ blcckcontroll.common.num_entries];
        }
        blcckcontroll.common.entries = dec_macros.HANDLE_VECTOR(hdl_dat,blcckcontroll.common.num_entries,2,obj,objDwgData,0);
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

        out_json.HANDLE_VECTOR(dat,blcckcontroll.common.entries,"entries",blcckcontroll.common.num_entries,2,0);
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

        if( layerControl.common.entries == null){
            layerControl.common.entries = new Dwg_Object_Ref[ layerControl.common.num_entries];
        }
        layerControl.common.entries = dec_macros.HANDLE_VECTOR(hdl_dat,layerControl.common.num_entries,2,obj,objDwgData,0);
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

        out_json.HANDLE_VECTOR(dat,layerControl.common.entries,"entries",layerControl.common.num_entries,2,0);
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

        if( styleControl.common.entries == null){
            styleControl.common.entries = new Dwg_Object_Ref[ styleControl.common.num_entries];
        }
        styleControl.common.entries = dec_macros.HANDLE_VECTOR(hdl_dat,styleControl.common.num_entries,2,obj,objDwgData,0);
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

        out_json.HANDLE_VECTOR(dat,styleControl.common.entries,"entries",styleControl.common.num_entries,2,0);
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

        if( ltypeControl.common.entries == null){
            ltypeControl.common.entries = new Dwg_Object_Ref[ ltypeControl.common.num_entries];
        }
        ltypeControl.common.entries = dec_macros.HANDLE_VECTOR(hdl_dat,ltypeControl.common.num_entries,2,obj,objDwgData,0);

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

        out_json.HANDLE_VECTOR(dat,ltypeControl.common.entries,"entries",ltypeControl.common.num_entries,2,0);

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

        if( viewControl.common.entries == null){
            viewControl.common.entries = new Dwg_Object_Ref[ viewControl.common.num_entries];
        }
        viewControl.common.entries = dec_macros.HANDLE_VECTOR(hdl_dat,viewControl.common.num_entries,2,obj,objDwgData,0);
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

        out_json.HANDLE_VECTOR(dat,viewControl.common.entries,"entries",viewControl.common.num_entries,2,0);
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

        if( ucsControl.common.entries == null){
            ucsControl.common.entries = new Dwg_Object_Ref[ ucsControl.common.num_entries];
        }
        ucsControl.common.entries = dec_macros.HANDLE_VECTOR(hdl_dat,ucsControl.common.num_entries,2,obj,objDwgData,0);
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

        out_json.HANDLE_VECTOR(dat,ucsControl.common.entries,"entries",ucsControl.common.num_entries,2,0);
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

        if( vportControl.common.entries == null){
            vportControl.common.entries = new Dwg_Object_Ref[ vportControl.common.num_entries];
        }
        vportControl.common.entries = dec_macros.HANDLE_VECTOR(hdl_dat,vportControl.common.num_entries,2,obj,objDwgData,0);
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

        out_json.HANDLE_VECTOR(dat,vportControl.common.entries,"entries",vportControl.common.num_entries,2,0);
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

        if( appidControl.common.entries == null){
            appidControl.common.entries = new Dwg_Object_Ref[ appidControl.common.num_entries];
        }
        appidControl.common.entries = dec_macros.HANDLE_VECTOR(hdl_dat,appidControl.common.num_entries,2,obj,objDwgData,0);
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

        out_json.HANDLE_VECTOR(dat,appidControl.common.entries,"entries",appidControl.common.num_entries,2,0);
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

        if( dimstyleControl.common.entries == null){
            dimstyleControl.common.entries = new Dwg_Object_Ref[ dimstyleControl.common.num_entries];
        }
        dimstyleControl.common.entries = dec_macros.HANDLE_VECTOR(hdl_dat,dimstyleControl.common.num_entries,2,obj,objDwgData,0);
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

        out_json.HANDLE_VECTOR(dat,dimstyleControl.common.entries,"entries",dimstyleControl.common.num_entries,2,0);
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

        if( vxControl.common.entries == null){
            vxControl.common.entries = new Dwg_Object_Ref[ vxControl.common.num_entries];
        }
        vxControl.common.entries = dec_macros.HANDLE_VECTOR(hdl_dat,vxControl.common.num_entries,2,obj,objDwgData,0);

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

        out_json.HANDLE_VECTOR(dat,vxControl.common.entries,"entries",vxControl.common.num_entries,2,0);
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

    static int dwg_decode_LAYOUT(String name, Dwg_Object obj, Bit_Chain dat,
                                 Dwg_Data objDwgData, DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);

        return dec_macros.DWG_OBJECT_END(dat,hdl_dat,str_dat,obj,error);
    }

    static int dwg_json_LAYOUT(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                               DWG_OBJECT_TYPE type) throws IOException {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain();
        Bit_Chain str_dat = new Bit_Chain(dat);
        error = out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_LAYER layer = obj.tio.object.tio.LAYER;

        return error;
    }

    static int dwg_decode_LAYER(String name, Dwg_Object obj, Bit_Chain dat,
                                 Dwg_Data objDwgData, DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_LAYER layer = obj.tio.object.tio.LAYER;

        layer.common = dec_macros.COMMON_TABLE_FLAGS_READ(name,dat,hdl_dat,str_dat,obj,objDwgData);

        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            layer.color = dec_macros.FIELD_CMC(dat,str_dat,62);

            layer.ltype = new Dwg_Object_Ref();
            layer.ltype = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,2,6);

            if(obj.size == 38){
                layer.flag0 = (char)dec_macros.FIELD_CAST(dat,"BS",0);
            }
            if(specs.DECODER)
            {
                layer.on = layer.color.index >= 0 ? '1' : 0;
                layer.frozen = (char)(layer.common.flag & 1);
                layer.frozen_in_new = (char)(layer.common.flag & 2);
                layer.locked = (char)(layer.common.flag & 4);
            }
        }

        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            layer.frozen = dec_macros.FIELD_B(dat,"B",0);
            layer.on = dec_macros.FIELD_B(dat,"B",0);
            layer.frozen_in_new = dec_macros.FIELD_B(dat,"B",0);
            layer.locked = dec_macros.FIELD_B(dat,"B",0);
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            int flag0 = layer.flag0;
            layer.flag0 = dec_macros.FIELD_BSx(dat,"BS",0);
            flag0 = layer.flag0;

            layer.frozen = (char) (flag0 & 1);
            layer.on = (char) ((flag0 & 2) == 0 ? 1 : 0);
            layer.frozen_in_new = (char) ((flag0 & 4) != 0 ? 1 : 0);
            layer.locked = (char) ((flag0 & 8) != 0 ? 1 : 0);
            layer.plotflag = (char) ((flag0 & 16) != 0 ? 1 : 0);
            layer.linewt = (char) ((flag0 & 0x03E0) >> 5);

            layer.common.flag |= (layer.frozen) |
                    (layer.frozen_in_new << 1) |
                    (layer.locked << 2) |
                    (layer.common.is_xref_ref << 3) |
                    ((layer.common.is_xref_resolved != 0 ? 1 : 0) << 4) |
                    (layer.common.is_xref_dep << 5);

            if(specs.JSON)
            {
                layer.linewt = dec_macros.FIELD_RC(dat,"RC", 370);
            }
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            layer.color = dec_macros.FIELD_CMC(dat,str_dat,0);
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            // For DWG
            layer.flag0 |= layer.frozen |
                    (layer.frozen_in_new << 1) |
                    (layer.locked << 2) |
                    ((layer.color.index < 0 ? 32 : 0));
            // For DXF
            layer.common.flag |= layer.frozen |
                    (layer.frozen_in_new << 1) |
                    (layer.locked << 2) |
                    ((layer.color.index < 0 ? 32 : 0));
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            dec_macros.START_OBJECT_HANDLE_STREAM(dat,obj);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            layer.plotstyle = new Dwg_Object_Ref();
            layer.plotstyle = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            layer.material = new Dwg_Object_Ref();
            layer.material = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            layer.ltype = new Dwg_Object_Ref();
            layer.ltype = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,6);
        }
        if(specs.DXF)
        {
            /*
            * SINCE (R_2000b) {
      if (_obj->name &&
          (bit_eq_T (dat, _obj->name, "Defpoints") ||
           bit_eq_T (dat, _obj->name, "DEFPOINTS")))
      {
        _obj->plotflag = 0;
        FIELD_B (plotflag, 290);
      } else {
        FIELD_B0 (plotflag, 290);
      }
    }
    SINCE (R_13b1) {
      int lw = dxf_cvt_lweight (FIELD_VALUE (linewt));
      KEY (linewt); VALUE_BSd (lw, 370);
    }
    SINCE (R_2000b) {
      FIELD_HANDLE (plotstyle, 5, 390);
    }
    SINCE (R_2007a) {
      DXF { FIELD_HANDLE (material, 5, 0); } // yet unstable class
      else {
        FIELD_HANDLE (material, 5, 347);
      }
    }
  }
  SINCE (R_2013b) {
    FIELD_HANDLE (visualstyle, 5, 348);
  }
            * */
        }
        return dec_macros.DWG_OBJECT_END(dat,hdl_dat,str_dat,obj,error);
    }

    static int dwg_json_LAYER(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                               DWG_OBJECT_TYPE type) throws IOException {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = new Bit_Chain(dat);
        error = out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_LAYER layer = obj.tio.object.tio.LAYER;

        out_json.COMMON_TABLE_FLAGS_WRITE(name,dat,hdl_dat,str_dat,obj,objDwgData,layer.common);

        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_CMC(dat,"color",layer.color,62);
            out_json.FIELD_HANDLE(hdl_dat,"ltype",layer.ltype,2,6);

            if(obj.size == 38){
                out_json.FIELD_CAST(dat,layer.flag0,"flag0","BS",0);

            }
            if(specs.DECODER)
            {
                layer.on = layer.color.index >= 0 ? '1' : 0;
                layer.frozen = (char)(layer.common.flag & 1);
                layer.frozen_in_new = (char)(layer.common.flag & 2);
                layer.locked = (char)(layer.common.flag & 4);
            }
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            out_json.FIELD_B(dat,"frozen",layer.frozen,0);
            out_json.FIELD_B(dat,"on",layer.on,0);
            out_json.FIELD_B(dat,"frozen_in_new",layer.frozen_in_new,0);
            out_json.FIELD_B(dat,"locked",layer.locked,0);
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            int flag0 = layer.flag0;
            out_json.FIELD_BSx(dat,"flag0",(short)flag0,0);

            layer.frozen = (char) (flag0 & 1);
            layer.on = (char) ((flag0 & 2) == 0 ? 1 : 0);
            layer.frozen_in_new = (char) ((flag0 & 4) != 0 ? 1 : 0);
            layer.locked = (char) ((flag0 & 8) != 0 ? 1 : 0);
            layer.plotflag = (char) ((flag0 & 16) != 0 ? 1 : 0);
            layer.linewt = (char) ((flag0 & 0x03E0) >> 5);

            layer.common.flag |= (layer.frozen) |
                    (layer.frozen_in_new << 1) |
                    (layer.locked << 2) |
                    (layer.common.is_xref_ref << 3) |
                    ((layer.common.is_xref_resolved != 0 ? 1 : 0) << 4) |
                    (layer.common.is_xref_dep << 5);

            if(specs.JSON)
            {
                out_json.FIELD_RC("linewt",layer.linewt,dat,370);
            }
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_CMC(dat,"color",layer.color,62);
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_14,dat))
        {
            // For DWG
            layer.flag0 |= layer.frozen |
                    (layer.frozen_in_new << 1) |
                    (layer.locked << 2) |
                    ((layer.color.index < 0 ? 32 : 0));
            // For DXF
            layer.common.flag |= layer.frozen |
                    (layer.frozen_in_new << 1) |
                    (layer.locked << 2) |
                    ((layer.color.index < 0 ? 32 : 0));
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            //dec_macros.START_OBJECT_HANDLE_STREAM(dat,obj);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            out_json.FIELD_HANDLE(hdl_dat,"plotstyle",layer.plotstyle,5,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            out_json.FIELD_HANDLE(hdl_dat,"material",layer.material,5,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_HANDLE(hdl_dat,"ltype",layer.ltype,5,6);
        }
        if(specs.DXF)
        {
            /*
            * SINCE (R_2000b) {
      if (_obj->name &&
          (bit_eq_T (dat, _obj->name, "Defpoints") ||
           bit_eq_T (dat, _obj->name, "DEFPOINTS")))
      {
        _obj->plotflag = 0;
        FIELD_B (plotflag, 290);
      } else {
        FIELD_B0 (plotflag, 290);
      }
    }
    SINCE (R_13b1) {
      int lw = dxf_cvt_lweight (FIELD_VALUE (linewt));
      KEY (linewt); VALUE_BSd (lw, 370);
    }
    SINCE (R_2000b) {
      FIELD_HANDLE (plotstyle, 5, 390);
    }
    SINCE (R_2007a) {
      DXF { FIELD_HANDLE (material, 5, 0); } // yet unstable class
      else {
        FIELD_HANDLE (material, 5, 347);
      }
    }
  }
  SINCE (R_2013b) {
    FIELD_HANDLE (visualstyle, 5, 348);
  }
            * */
        }
        return error;
    }

    static int dwg_decode_UNKNOWN_OBJ(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                                    DWG_OBJECT_TYPE type) throws IOException {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);
        specs.HANDLE_UNKNOWN_BITS(dat,obj,objDwgData);
        return dec_macros.DWG_OBJECT_END(dat,hdl_dat,str_dat,obj,error);
    }

    static int dwg_json_UNKNOWN_OBJ(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                                    DWG_OBJECT_TYPE type) throws IOException {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = new Bit_Chain(dat);
        error = out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);
        specs.HANDLE_UNKNOWN_BITS(dat,obj,objDwgData);
        return error;
    }

    static int dwg_json_UNKNOWN_ENT(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                                    DWG_OBJECT_TYPE type) throws IOException {
        return 0;
    }

    public static int dwg_decode_UNKNOWN_ENT(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                                             DWG_OBJECT_TYPE type)
    {
        return 0;
    }

    static int dwg_decode_STYLE(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                                DWG_OBJECT_TYPE type) {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_STYLE style = obj.tio.object.tio.STYLE;

        style.common = dec_macros.COMMON_TABLE_FLAGS_READ(name,dat,hdl_dat,str_dat,obj,objDwgData);

        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            style.is_shape = dec_macros.FIELD_B(dat,"B",0);
            style.is_vertical = dec_macros.FIELD_B(dat,"B",0);
            if(specs.DECODER_OR_ENCODER)
            {
                style.common.flag |= (style.is_vertical != 0 ? 4 : 0) +
                        (style.is_shape != 0 ? 1 : 0);
            }
        }
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            style.text_size = dec_macros.FIELD_RD(dat,"RD",40);
            style.width_factor = dec_macros.FIELD_RD(dat,"RD",41);
            style.oblique_angle = dec_macros.FIELD_RD(dat,"RD",50);
            style.generation = dec_macros.FIELD_RC(dat,"RC",71);
            style.last_height = dec_macros.FIELD_RD(dat,"RD",42);
            style.font_file = dec_macros.FIELD_TFv(dat,64, 3);
            if(commen.SINCE(DWG_VERSION_TYPE.R_2_4,dat))
                style.bigfont_file = dec_macros.FIELD_TFv(dat,64,4);
            if(specs.DECODER)
            {
                style.is_shape = (char)(style.common.flag & 4);
                style.is_vertical = (char)(style.common.flag & 1);
            }
        }
        else {
            style.text_size = dec_macros.FIELD_BD(dat,"BD",40);
            style.width_factor = dec_macros.FIELD_BD(dat,"BD",41);
            style.oblique_angle = dec_macros.FIELD_BD(dat,"BD",50);
            style.generation = dec_macros.FIELD_RC(dat,"RC",71);
            style.last_height = dec_macros.FIELD_BD(dat,"BD",42);
            style.font_file = dec_macros.FIELD_T(dat,obj,"T",3);
            style.bigfont_file = dec_macros.FIELD_T(dat,obj,"T",4);
        }
        dec_macros.START_OBJECT_HANDLE_STREAM(dat,obj);
        return dec_macros.DWG_OBJECT_END(dat,hdl_dat,str_dat,obj,error);
    }

    static int dwg_json_STYLE(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                              DWG_OBJECT_TYPE type) throws IOException {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = new Bit_Chain(dat);
        error = out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);

        Dwg_Object_STYLE style = obj.tio.object.tio.STYLE;

        out_json.COMMON_TABLE_FLAGS_WRITE(name,dat,hdl_dat,str_dat,obj,objDwgData,style.common);
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_B(dat,"is_shape",style.is_shape,0);
            out_json.FIELD_B(dat,"is_vertical",style.is_vertical,0);
            if(specs.DECODER_OR_ENCODER)
            {
                style.common.flag |= (style.is_vertical != 0 ? 4 : 0) +
                        (style.is_shape != 0 ? 1 : 0);
            }
        }

        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_RD(dat,"text_size",style.text_size,40);
            out_json.FIELD_RD(dat,"width_factor",style.width_factor,41);
            out_json.FIELD_RD(dat,"oblique_angle",style.oblique_angle,50);
            out_json.FIELD_RC("generation",style.generation,dat,71);
            out_json.FIELD_RD(dat,"last_height",style.last_height,50);
            out_json.FIELD_TFv(dat,"font_file",style.font_file,3);
            if(commen.SINCE(DWG_VERSION_TYPE.R_2_4,dat))
                out_json.FIELD_TFv(dat,"bigfont_file",style.bigfont_file,3);
            if(specs.DECODER)
            {
                style.is_shape = (char)(style.common.flag & 4);
                style.is_vertical = (char)(style.common.flag & 1);
            }
        }
        else {
            out_json.FIELD_BD(dat,"text_size",style.text_size,40);
            out_json.FIELD_BD(dat,"width_factor",style.width_factor,41);
            out_json.FIELD_BD(dat,"oblique_angle",style.oblique_angle,50);
            out_json.FIELD_RC("generation",style.generation,dat,71);
            out_json.FIELD_BD(dat,"last_height",style.last_height,50);
            out_json.FIELD_T(dat,"font_file",style.font_file,3);
            out_json.FIELD_T(dat,"bigfont_file",style.bigfont_file,4);
        }
        return error;
    }
    static int dwg_decode_APPID(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                                DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_APPID appid = obj.tio.object.tio.APPID;

        appid.common = dec_macros.COMMON_TABLE_FLAGS_READ(name,dat,hdl_dat,str_dat,obj,objDwgData);
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            if(specs.DXF)
            {
                appid.unknown = (char)dec_macros.FIELD_RS(dat,"RS",71);
            }
            else {
                appid.unknown = dec_macros.FIELD_RC(dat,"RC",71);
            }
            dec_macros.START_OBJECT_HANDLE_STREAM(dat,obj);
        }

        return dec_macros.DWG_OBJECT_END(dat,hdl_dat,str_dat,obj,error);
    }

    static int dwg_json_APPID(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                                DWG_OBJECT_TYPE type) throws IOException {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = new Bit_Chain(dat);
        error = out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_APPID appid = obj.tio.object.tio.APPID;

        out_json.COMMON_TABLE_FLAGS_WRITE(name,dat,hdl_dat,str_dat,obj,objDwgData,appid.common);

        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            if(specs.DXF)
            {
                out_json.FIELD_RS(dat,appid.unknown,"unknown",71);
            }
            else {
                out_json.FIELD_RC("unknown",appid.unknown,dat,71);
            }
        }

        return error;
    }

    static int dwg_decode_LTYPE(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                                DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_LTYPE ltype = obj.tio.object.tio.LTYPE;

        ltype.common = dec_macros.COMMON_TABLE_FLAGS_READ(name,dat,hdl_dat,str_dat,obj,objDwgData);
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            ltype.description = dec_macros.FIELD_TFv(dat,48,3);
        }
        else {
            ltype.description = dec_macros.FIELD_T(dat,obj,"T",3);
            ltype.pattern_len = dec_macros.FIELD_BD(dat,"BD",0);
        }
        ltype.alignment = dec_macros.FIELD_RC(dat,"RC",72);
        ltype.numdashes = (char)dec_macros.FIELD_RCu(dat,73);
        if(specs.DXF)
        {
            ltype.pattern_len = dec_macros.FIELD_BD(dat,"BD",40);
        }
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            ltype.pattern_len = dec_macros.FIELD_RD(dat,"RD",40);
            if(macros.IS_JSON){
                ltype.dashes_r11 = Arrays.stream(dec_macros.FIELD_VECTOR_INL(dat,"RD",12,49))
                        .mapToDouble(_obj -> (Double) _obj).toArray();
            }else{
                ltype.dashes_r11 = Arrays.stream(dec_macros.FIELD_VECTOR_N(dat,"RD",12,49))
                        .mapToDouble(_obj -> (Double) _obj).toArray();
            }
            if(commen.PRE(DWG_VERSION_TYPE.R_14,dat))
            {
                if(obj.size > 187)
                {
                    ltype.unknown_r11 = dec_macros.FIELD_RC(dat,"RC",0);
                }
            }
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            if(ltype.dashes == null){
                ltype.dashes = new Dwg_LTYPE_dash[(int)ltype.numdashes];
            }

            for(int i = 0; i < (int)ltype.numdashes; i++)
            {
                Dwg_LTYPE_dash dash = ltype.dashes[i];
                dash.length = dec_macros.SUB_FIELD_BD(dat,"BD",49);
                if(specs.DXF)
                {
                    dash.shape_flag = dec_macros.SUB_FIELD_BS(dat,"BS",74);
                    if(dash.shape_flag != 0)
                    {
                        dash.complex_shapecode = dec_macros.SUB_FIELD_BS(dat,"BS",75);
                        dash.style = new Dwg_Object_Ref();
                        dash.style = dec_macros.SUB_FIELD_HANDLE(hdl_dat,dash.style,5,obj,objDwgData,0);
                        dash.scale = dec_macros.SUB_FIELD_BD(dat,"BD",46);
                        dash.rotation = dec_macros.SUB_FIELD_BD(dat,"BD",50);
                        dash.x_offset = dec_macros.SUB_FIELD_RD(dat,"RD",44);
                        dash.y_offset = dec_macros.SUB_FIELD_RD(dat,"RD",45);
                    }
                    if((dash.shape_flag & 2) != 0)
                    {
                        dash.text = dec_macros.SUB_FIELD_T(dat,obj,"T",9);
                    }
                }
                else {
                    dash.complex_shapecode = dec_macros.SUB_FIELD_BS(dat,"BS",75);
                    dash.style = new Dwg_Object_Ref();
                    dash.style = dec_macros.SUB_FIELD_HANDLE(hdl_dat,dash.style,5,obj,objDwgData,0);
                    dash.x_offset = dec_macros.SUB_FIELD_RD(dat,"RD",44);
                    dash.y_offset = dec_macros.SUB_FIELD_RD(dat,"RD",45);
                    dash.scale = dec_macros.SUB_FIELD_BD(dat,"BD",46);
                    dash.rotation = dec_macros.SUB_FIELD_BD(dat,"BD",50);
                    dash.shape_flag = dec_macros.SUB_FIELD_BS(dat,"BS",74);
                }
                if(specs.DECODER)
                {
                    if((dash.shape_flag & 2) != 0)
                    {
                        ltype.has_string_area = 1;
                    }
                    if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat)){
                        ltype.pattern_len += dash.length;
                    }
                }
                specs.SET_PARENT_OBJ(dash,ltype);
            }

            if(commen.UNTIL(DWG_VERSION_TYPE.R_2004,dat))
            {
                if(!macros.IS_DECODER)
                {

                }
                else {
                    if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_13c3,dat))
                    {
                        ltype.unknown_r13 = (char)dec_macros.FIELD_RC(dat,"RC",0);
                    }
                    ltype.string_area = dec_macros.FIELD_BINARY(dat,256,0);

                    if(specs.DECODER)
                    {
                        int dash_i = 0;
                        for (int ki = 0; ltype.string_area != null && ki < (int)ltype.numdashes; ki++)
                        {
                            if((ltype.dashes[ki].shape_flag & 2) != 0)
                            {
                                if(dash_i >= 256)
                                {
                                    break;
                                }
                                ltype.dashes[ki].text = ltype.string_area;
                                dash_i += (ltype.dashes[ki].text.length() + 256 - dash_i) + 1;
                            }
                        }
                    }
                }
            }
            else {
                if(ltype.has_string_area != 0)
                {
                    int dash_i = 0;
                    ltype.string_area = dec_macros.FIELD_BINARY(dat,512,0);
                    for(int ji = 0;  ltype.string_area.isEmpty() && ji < (int)ltype.numdashes; ji++)
                    {
                        if((ltype.dashes[ji].shape_flag & 2) !=0 )
                        {
                            if(dash_i >= 512)
                            {
                                break;
                            }
                            ltype.dashes[ji].text = ltype.string_area;
//                            dash_i += ((2 * bit_wcs2nlen ((BITCODE_TU)_obj->dashes[rcount1].text,
//                                    256 - (dash_i / 2))) + 2 ) & 0xFFFF;
                        }
                    }
                }
            }
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            dec_macros.START_OBJECT_HANDLE_STREAM(dat,obj);
        }
        return dec_macros.DWG_OBJECT_END(dat,hdl_dat,str_dat,obj,error);
    }

    static int dwg_json_LTYPE(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                                DWG_OBJECT_TYPE type) throws IOException {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = new Bit_Chain(dat);
        error = out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_LTYPE ltype = obj.tio.object.tio.LTYPE;

        out_json.COMMON_TABLE_FLAGS_WRITE(name,dat,hdl_dat,str_dat,obj,objDwgData,ltype.common);
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_TFv(dat,"description",ltype.description,3);
        }
        else{
            out_json.FIELD_T(dat,"description",ltype.description,3);
            out_json.FIELD_BD(dat,"pattern_len",ltype.pattern_len,0);
        }
        out_json.FIELD_RC("alignment",ltype.alignment,dat,72);
        out_json.FIELD_RCu(dat,"numdashes",ltype.numdashes,73);

        if(specs.DXF)
        {

        }

        if(ltype.numdashes > 0)
        {

        }

        if(commen.UNTIL(DWG_VERSION_TYPE.R_2004,dat))
        {
            if(specs.JSON)
            {
                if(ltype.has_string_area == 0)
                {
                    out_json.FIELD_BINARY(dat,"strings_area",ltype.string_area,256,0);
                }
            }
        }
        return error;
    }

    static int dwg_decode_MLINESTYLE(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                                DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_MLINESTYLE mlinestyle = obj.tio.object.tio.MLINESTYLE;

        //SUBCLASS (AcDbMlineStyle)
        mlinestyle.name = dec_macros.FIELD_T(dat,obj,"T",2);
        mlinestyle.description = dec_macros.FIELD_T(dat,obj,"T",0);
        mlinestyle.flag = dec_macros.FIELD_BS(dat,"BS",70);
        //        LOG_MLINESTYLE_FLAG
//        DXF { FIELD_T (description, 3); }
        mlinestyle.fill_color = dec_macros.FIELD_CMC(dat,str_dat,62);
        if(macros.IS_DXF)
        {

        }
        else{
            mlinestyle.start_angle = dec_macros.FIELD_BD(dat,"BD",51);
            mlinestyle.end_angle = dec_macros.FIELD_BD(dat,"BD",52);
        }
        mlinestyle.num_lines = dec_macros.FIELD_RCu(dat,72);
        if(mlinestyle.num_lines > 0)
        {
            if(mlinestyle.lines == null)
            {
                mlinestyle.lines = new Dwg_MLINESTYLE_line[mlinestyle.num_lines];
            }
            for(int i = 0; i < mlinestyle.num_lines; i++)
            {
                mlinestyle.lines[i] = new Dwg_MLINESTYLE_line();
                mlinestyle.lines[i].offset = dec_macros.SUB_FIELD_BD(dat,"BD",49);
                mlinestyle.lines[i].color = dec_macros.SUB_FIELD_CMC(dat,str_dat,62);
                if(commen.PRE(DWG_VERSION_TYPE.R_2018,dat))
                {
                    if(macros.IS_DXF && !macros.IS_ENCODER)
                    {

                    }
                    else {
                        mlinestyle.lines[i].lt.index = dec_macros.SUB_FIELD_BSd(dat,"BS",6);
                    }
                }
                else {
                    mlinestyle.lines[i].lt.ltype = dec_macros.SUB_FIELD_HANDLE(hdl_dat,mlinestyle.lines[i].lt.ltype,5,obj,objDwgData,6);
                }
                specs.SET_PARENT_OBJ(mlinestyle.lines[i],mlinestyle);
            }
        }
        dec_macros.START_OBJECT_HANDLE_STREAM(dat,obj);
        return dec_macros.DWG_OBJECT_END(dat,hdl_dat,str_dat,obj,error);
    }

    static int dwg_json_MLINESTYLE(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                              DWG_OBJECT_TYPE type) throws IOException {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = new Bit_Chain(dat);
        error = out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_MLINESTYLE mlinestyle = obj.tio.object.tio.MLINESTYLE;

        out_json.SUBCLASS(dat,"AcDbMlineStyle");
        out_json.FIELD_T(dat,"name",mlinestyle.name,2);
        out_json.FIELD_T(dat,"description",mlinestyle.description,0);
        out_json.FIELD_BS(dat,"flag",mlinestyle.flag,70);

//        LOG_MLINESTYLE_FLAG
//        DXF { FIELD_T (description, 3); }
        out_json.FIELD_CMC(dat,"fill_color",mlinestyle.fill_color,62);
        if(macros.IS_DXF)
        {

        }
        else{
            out_json.FIELD_BD(dat,"start_angle",mlinestyle.start_angle,51);
            out_json.FIELD_BD(dat,"end_angle",mlinestyle.end_angle,52);
        }
        out_json.FIELD_RCu(dat,"num_lines",mlinestyle.num_lines,71);
        out_json.REPEAT(dat,"lines");
        if(mlinestyle.num_lines > 0)
        {
            for(int i = 0; i < mlinestyle.num_lines; i++)
            {
                out_json.REPEAT_BLOCK(dat);
                out_json.SUB_FIELD_BD(dat,"offset",mlinestyle.lines[i].offset,49);
                out_json.SUB_FIELD_CMC(dat,"color",mlinestyle.lines[i].color,62);
                if(commen.PRE(DWG_VERSION_TYPE.R_2018,dat))
                {
                    if(macros.IS_DXF && !macros.IS_ENCODER)
                    {

                    }
                    else {
                        out_json.SUB_FIELD_BSd(dat,"lt.index",(short)mlinestyle.lines[i].lt.index,6);
                    }
                }
                else {
                    out_json.SUB_FIELD_HANDLE(dat,"lt.ltype",mlinestyle.lines[i].lt.ltype,5,6);
                }
//                specs.SET_PARENT_OBJ(mlinestyle.lines[i],mlinestyle);
                out_json.END_REPEAT_BLOCK(dat);
            }
            out_json.ENDREPEAT(dat);
        }

        return error;
    }

    static int dwg_decode_BLOCK_HEADER(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                                     DWG_OBJECT_TYPE type)
    {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = dec_macros.dwg_decode_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_BLOCK_HEADER blockHeader = obj.tio.object.tio.BLOCK_HEADER;

        blockHeader.common = dec_macros.COMMON_TABLE_FLAGS_READ(name,dat,hdl_dat,str_dat,obj,objDwgData);
/*
* DXF {
    // not allowed to be skipped, can be 0
    VALUE_HANDLE (_obj->layout, layout, 5, 340);
    if (FIELD_VALUE (preview_size))
      {
        FIELD_BINARY (preview, FIELD_VALUE (preview_size), 310);
      }
    if (FIELD_VALUE (num_inserts))
      {
        VALUE_TFF ("{BLKREFS", 102);
        HANDLE_VECTOR (inserts, num_inserts, 4, 331);
        VALUE_TFF ("}", 102);
      }
    // The DXF TABLE.BLOCK_RECORD only has this. More later in the BLOCKS section.
    return 0;
  }
* */
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            blockHeader.block_offset_r11 = dec_macros.FIELD_RLx(dat,"RL",0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            blockHeader.anonymous = dec_macros.FIELD_B(dat,"B",0);
            blockHeader.hasattrs = dec_macros.FIELD_B(dat,"B",0);
            blockHeader.blkisxref = dec_macros.FIELD_B(dat,"B",0);
            blockHeader.xrefoverlaid = dec_macros.FIELD_B(dat,"B",0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            blockHeader.loaded_bit = dec_macros.FIELD_B(dat,"B",0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {
            blockHeader.common.flag |= blockHeader.anonymous | blockHeader.hasattrs << 1 |
                    blockHeader.blkisxref << 2 | blockHeader.xrefoverlaid << 3;
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {
           blockHeader.num_owned = dec_macros.FIELD_BL(dat,"BL",0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            blockHeader.base_pt = dec_macros.FIELD_3DPOINT(dat,10);
            blockHeader.xref_pname = dec_macros.FIELD_T(dat,obj,"T",1);
        }
        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            blockHeader.num_inserts = dec_macros.FIELD_NUM_INSERTS(dat,"RL",0);
            blockHeader.description = dec_macros.FIELD_T(dat,obj,"T",4);
            if(specs.IS_JSON)
            {
                blockHeader.preview_size = dec_macros.FIELD_BL(dat,"BL",0);
            } else {
                if(blockHeader.preview_size < 0xa00000)
                {
                    blockHeader.preview = dec_macros.FIELD_BINARY(dat,(int)blockHeader.preview_size,310);
                }
            }
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            blockHeader.insert_units = dec_macros.FIELD_BS(dat,"BS",70);
            blockHeader.explodable = dec_macros.FIELD_B(dat,"B",280);
            blockHeader.block_scaling = dec_macros.FIELD_RC(dat,"RC",281);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            dec_macros.START_OBJECT_HANDLE_STREAM(dat,obj);
            blockHeader.block_entity = new Dwg_Object_Ref();
            blockHeader.block_entity = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_2000,dat))
        {
            if(blockHeader.blkisxref == 0 && blockHeader.xrefoverlaid == 0)
            {
                blockHeader.first_entity = new Dwg_Object_Ref();
                blockHeader.first_entity = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,4,0);

                blockHeader.last_entity = new Dwg_Object_Ref();
                blockHeader.last_entity = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,4,0);
            }
        }
        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {
            if(blockHeader.num_owned < 0xf00000)
            {
                if(blockHeader.entities == null)
                    blockHeader.entities = new Dwg_Object_Ref[(int)blockHeader.num_owned];

                blockHeader.entities = dec_macros.HANDLE_VECTOR(hdl_dat,(int)blockHeader.num_owned,4,obj,objDwgData,0);
            }
        }
        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_13b1,dat)){
            blockHeader.endblk_entity = new Dwg_Object_Ref();
            blockHeader.endblk_entity = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,3,0);
        }
        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_2000b,dat)){
            if(blockHeader.num_inserts != 0 && blockHeader.num_inserts < 0xf00000)
            {
                if(blockHeader.inserts == null)
                    blockHeader.inserts = new Dwg_Object_Ref[(int)blockHeader.num_inserts];

                blockHeader.inserts = dec_macros.HANDLE_VECTOR(hdl_dat,(int)blockHeader.num_inserts,
                        4,obj,objDwgData,0);
            }
            blockHeader.layout = new Dwg_Object_Ref();
            blockHeader.layout = dec_macros.FIELD_HANDLE(hdl_dat,obj,objDwgData,5,0);
        }
        return dec_macros.DWG_OBJECT_END(dat,hdl_dat,str_dat,obj,error);
    }

    static int dwg_json_BLOCK_HEADER(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                                   DWG_OBJECT_TYPE type) throws IOException {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = new Bit_Chain(dat);
        error = out_json.dwg_json_token(dat,obj,name,type,hdl_dat,str_dat);
        Dwg_Object_BLOCK_HEADER blockHeader = obj.tio.object.tio.BLOCK_HEADER;

        out_json.COMMON_TABLE_FLAGS_WRITE(name,dat,hdl_dat,str_dat,obj,objDwgData,blockHeader.common);
        /*
* DXF {
    // not allowed to be skipped, can be 0
    VALUE_HANDLE (_obj->layout, layout, 5, 340);
    if (FIELD_VALUE (preview_size))
      {
        FIELD_BINARY (preview, FIELD_VALUE (preview_size), 310);
      }
    if (FIELD_VALUE (num_inserts))
      {
        VALUE_TFF ("{BLKREFS", 102);
        HANDLE_VECTOR (inserts, num_inserts, 4, 331);
        VALUE_TFF ("}", 102);
      }
    // The DXF TABLE.BLOCK_RECORD only has this. More later in the BLOCKS section.
    return 0;
  }
* */
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_RLx(dat,blockHeader.block_offset_r11,"block_offset_r11",0);
/*
* DECODER_OR_ENCODER {
      if (_obj->block_offset_r11 >= 0x40000000)
        {
          BITCODE_RL off = _obj->block_offset_r11 & 0x3fffffff;
          LOG_TRACE ("abs. offset => " FORMAT_RLx "\n",
                     off + dwg->header.blocks_start);
        }
      else
        {
          LOG_TRACE ("abs. offset => " FORMAT_RLx "\n",
                     _obj->block_offset_r11 + dwg->header.blocks_start);
        }
    }
    if (!obj->size || obj->size == 38)
      FIELD_RC (unknown_r11, 0);
    SINCE (R_11)
    {
      FIELD_HANDLE (block_entity, 2, 0);
      FIELD_RSd (flag2, 0);
    }
    FIELD_VALUE (anonymous)    = FIELD_VALUE (flag) & 1;
    FIELD_VALUE (hasattrs)     = FIELD_VALUE (flag) & 2;
    FIELD_VALUE (blkisxref)    = FIELD_VALUE (flag) & 4;
    FIELD_VALUE (xrefoverlaid) = FIELD_VALUE (flag) & 8;
* */
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_B(dat,"anonymous",blockHeader.anonymous,0);
            out_json.FIELD_B(dat,"hasattrs",blockHeader.hasattrs,0);
            out_json.FIELD_B(dat,"blkisxref",blockHeader.blkisxref,0);
            out_json.FIELD_B(dat,"xrefoverlaid",blockHeader.xrefoverlaid,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            out_json.FIELD_B(dat,"loaded_bit",blockHeader.loaded_bit,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {
            blockHeader.common.flag |= blockHeader.anonymous | blockHeader.hasattrs << 1 |
                    blockHeader.blkisxref << 2 | blockHeader.xrefoverlaid << 3;
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {
            out_json.FIELD_BL(dat,"num_owned",blockHeader.num_owned,0);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            out_json.FIELD_3DPOINT(dat,"base_pt",blockHeader.base_pt,10);
            out_json.FIELD_T(dat,"xref_pname",blockHeader.xref_pname,1);
        }
        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_2000b,dat))
        {
            out_json.FIELD_NUM_INSERTS(dat,"num_inserts",blockHeader.num_inserts,0);
            out_json.FIELD_T(dat,"description",blockHeader.description,4);
            if(!specs.IS_JSON)
            {
                out_json.FIELD_BL(dat,"preview_size",blockHeader.preview_size,0);
            } else {
                out_json.FIELD_BINARY(dat,"preview",blockHeader.preview,(int)blockHeader.preview_size,310);
            }
        }

        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            out_json.FIELD_BS(dat,"insert_units",blockHeader.insert_units,70);
            out_json.FIELD_B(dat,"explodable",blockHeader.explodable,280);
            out_json.FIELD_RC("block_scaling",blockHeader.block_scaling,dat,281);
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            //dec_macros.START_OBJECT_HANDLE_STREAM(dat,obj);
            out_json.FIELD_HANDLE(dat,"block_entity",blockHeader.block_entity,3,0);
        }
        if(commen.VERSIONS(DWG_VERSION_TYPE.R_13b1,DWG_VERSION_TYPE.R_2000,dat))
        {
            if(blockHeader.blkisxref == 0 && blockHeader.xrefoverlaid == 0)
            {
                out_json.FIELD_HANDLE(dat,"first_entity",blockHeader.first_entity,4,0);
                out_json.FIELD_HANDLE(dat,"last_entity",blockHeader.last_entity,4,0);
            }
        }
        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {
            if(blockHeader.num_owned < 0xf00000)
            {
                out_json.HANDLE_VECTOR(dat,blockHeader.entities,"entities",
                        (int)blockHeader.num_owned,4,0);
            }
        }
        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_13b1,dat)){
            out_json.FIELD_HANDLE(dat,"endblk_entity",blockHeader.endblk_entity,3,0);
        }
        if(specs.IF_FREE_OR_SINCE(DWG_VERSION_TYPE.R_2000b,dat)){
            if(blockHeader.num_inserts != 0 && blockHeader.num_inserts < 0xf00000)
            {
                out_json.HANDLE_VECTOR(dat,blockHeader.inserts,"entities",
                        (int)blockHeader.num_inserts,4,0);
            }
            out_json.FIELD_HANDLE(dat,"layout",blockHeader.layout,5,0);
        }

        return error;

    }

    static int dwg_decode_BLOCK(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                                       DWG_OBJECT_TYPE type){
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        dec_macros.dwg_decode_entity_token(dat, obj, name, type, hdl_dat, str_dat);

        Dwg_Entity_BLOCK block = obj.tio.entity.tio.BLOCK;
        block.name = dec_macros.BLOCK_NAME(dat,str_dat,obj,2);
        dec_macros.COMMON_ENTITY_HANDLE_DATA(dat,obj);

        return dec_macros.DWG_ENTITY_END(dat,hdl_dat,str_dat,obj,error);
    }
    static int dwg_json_BLOCK(String name, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData,
                                     DWG_OBJECT_TYPE type) throws IOException {
        int error = 0;
        Bit_Chain hdl_dat = new Bit_Chain(dat);
        Bit_Chain str_dat = dat;
        error = out_json.dwg_json_entity_token(dat, obj, name, type, hdl_dat, str_dat);
        return error;
    }
}
