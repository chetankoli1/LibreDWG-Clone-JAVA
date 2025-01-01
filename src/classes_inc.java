public class classes_inc {
    public static String[] ACTION = {
            "decode","encode","json"
    };

    static int classes(Dwg_Class klass, String action, Dwg_Object obj, String name, Bit_Chain dat,
                       Dwg_Data objDwgData, String dxfname)
    {
        int error = 0;

        return error;
    }

    static void STABLE_CLASS(Dwg_Class klass, String action, Dwg_Object obj, String name,
                             Bit_Chain dat, Dwg_Data objDwgData, String dxfname)
    {
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
    }

    private static DWG_OBJECT_TYPE getObjectType(String dxfname)
    {
        return DWG_OBJECT_TYPE.DWG_TYPE_UNUSED;
    }
}
