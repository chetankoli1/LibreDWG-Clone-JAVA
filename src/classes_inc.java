import java.io.IOException;

public class classes_inc {
    public static String[] ACTION = {
            "decode","encode","json"
    };

    static int STABLE_CLASS(Dwg_Class klass, String action, Dwg_Object obj, String name,
                            Bit_Chain dat, Dwg_Data objDwgData, String dxfname) throws IOException {
        if(klass.dxfname != null && new String(klass.dxfname).equals(name))
        {
            if(action.equals("decode") || commen.memcmp(action,"in",2))
            {
                obj.name = name;
                obj.dxfname = dxfname;
                obj.fixedtype = getObjectType(dxfname);
                obj.klass = klass;
            }
        }
        switch (action.trim())
        {
            case "decode":
                return callback_read(dat,obj,objDwgData,name);
            case "json":
                return callback_write(dat,obj,objDwgData,name);
            default:
                return -1;
        }
    }

    static int STABLE_CLASS_DXF(Dwg_Class klass, String action, Dwg_Object obj, String name,
                                Bit_Chain dat, Dwg_Data objDwgData, String dxfname) throws IOException {
        int error = 0;
        if(klass.dxfname != null && new String(klass.dxfname).trim().equals(dxfname.trim()))
        {
            if(action.equals("decode") || commen.memcmp(action,"in",2))
            {
                obj.name = name;
                obj.dxfname = dxfname;
                obj.fixedtype = getObjectType(name.trim());
                obj.klass = klass;
            }
        }
        switch (action.trim())
        {
            case "decode":
                return callback_read(dat,obj,objDwgData,name);
            case "json":
                return callback_write(dat,obj,objDwgData,name);
            default:
                return -1;
        }
    }

    static int classes(Dwg_Class klass, String action, Dwg_Object obj, String name, Bit_Chain dat,
                       Dwg_Data objDwgData, String dxfname) throws IOException {
        int error = 0;

        switch (dxfname.trim()){
            case "ACDBDICTIONARYWDFLT":
                return STABLE_CLASS_DXF(klass,action,obj,name,dat,objDwgData,dxfname);
            case "ACDBPLACEHOLDER":
                return STABLE_CLASS_DXF(klass,action,obj,name,dat,objDwgData,dxfname);
            default:
                return DWG_ERROR.DWG_ERR_CLASSESNOTFOUND.value;
        }
    }



    static int callback_read(Bit_Chain dat, Dwg_Object obj, Dwg_Data objDwgData, String name)
    {
        String strName = name.trim();
        int error = 0;
        switch (strName)
        {
            case "DICTIONARYWDFLT":
                error = dwg_spec.dwg_decode_DICTIONARYWDFLTL(name,obj,dat,objDwgData,getObjectType(name));
                return 0;
            case "PLACEHOLDER":
                error = dwg_spec.dwg_decode_PLACEHOLDER(name,obj,dat,objDwgData,getObjectType(name));
                return error;
            default: return -1;
        }
    }
    static int callback_write(Bit_Chain dat, Dwg_Object obj, Dwg_Data objDwgData, String name) throws IOException {
        int error = -1;
        String strName = name.trim();
        switch (strName)
        {
            case "DICTIONARYWDFLT":
                error = dwg_spec.dwg_json_DICTIONARYWDFLTL(name,obj,dat,objDwgData,getObjectType(name));
                return error;
            case "PLACEHOLDER":
                error = dwg_spec.dwg_json_PLACEHOLDER(name,obj,dat,objDwgData,getObjectType(name));
                return error;
            default: return error;
        }
    }

    private static DWG_OBJECT_TYPE getObjectType(String name)
    {
        switch (name){
            case "DICTIONARYWDFLT":
                return DWG_OBJECT_TYPE.DWG_TYPE_DICTIONARYWDFLT;
            case "PLACEHOLDER":
                return DWG_OBJECT_TYPE.DWG_TYPE_PLACEHOLDER;
            default:
                return DWG_OBJECT_TYPE.DWG_TYPE_UNUSED;
        }
    }
}
