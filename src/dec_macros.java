import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class dec_macros {
    static final int REFS_PER_REALLOC = 16384;
    static final long MAX_MEM_ALLOC =  0x10000000000L;
    static int loglevel =  0;

    static char FIELD_RC(Bit_Chain dat, String type, int dxf)
    {
        return bits.bit_read_RC(dat);
        //FIELD_G_TRACE (nam, type, dxf);
    }
    static char FIELD_RC0(Bit_Chain dat, String type, int dxf)
    {
        return bits.bit_read_RC(dat);
        //FIELD_G_TRACE (nam, type, dxf);
    }

    static int FIELD_RS(Bit_Chain dat, String type, int dxf) {
        return bits.bit_read_RS(dat);
    }
    static int FIELD_RL(Bit_Chain dat, String type, int dxf) {
        return (int) (bits.bit_read_RL(dat) & 0xFFF);
    }
    static int FIELD_RLd(Bit_Chain dat, String type, int dxf) {
        return (int)bits.bit_read_RL(dat);
    }
    static long FIELD_RLx(Bit_Chain dat, String type, int dxf)
    {
        return (long)FIELD_CAST(dat,"RL",dxf);
    }
    static Object FIELD_CAST(Bit_Chain dat, String castType, int dxf) {
        switch (castType)
        {
            case "RC":
                return bits.bit_read_RC(dat);
            case "RS":
                return bits.bit_read_RS(dat);
            case "RL":
                return bits.bit_read_RL(dat);
            case "BS":
                return bits.bit_read_BS(dat);
            case "BL":
                return bits.bit_read_BL(dat);
            default:
                return 101;
        }
    }

    static char[] FIELD_VECTOR_INL_CHAR(Bit_Chain dat, String type, int size, int dxf)
    {
        char[] arr = new char[size];
        if(size > 0)
        {
            if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
            {
                if(_VECTOR_CHKCOUNT_STATIC(arr,size,TYPE_MAXELEMSIZE(type),dat) != 0)
                {
                    return null;
                }
                for(int vcount = 0; vcount < size; vcount++)
                {
                    arr[vcount] = bits.bit_read_RC(dat);
                }
            }
        }
        return arr;
    }

    static long[] FIELD_VECTOR_INL_LONG(Bit_Chain dat, String type, int size, int dxf)
    {
        long[] arr = new long[size];
        if(size > 0)
        {
            if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
            {
                if(_VECTOR_CHKCOUNT_STATIC(arr,size,TYPE_MAXELEMSIZE(type),dat) != 0)
                {
                    return null;
                }
                for(int vcount = 0; vcount < size; vcount++)
                {
                    if(type == "RS")
                    {
                        arr[vcount] = bits.bit_read_RS(dat);
                    }
                    if(type == "RL")
                    {
                        arr[vcount] = bits.bit_read_RL(dat);
                    }
                }
            }
        }
        return arr;
    }

    static Object[] FIELD_VECTOR_INL(Bit_Chain dat, String type, int size, int dxf)
    {
        Object[] arr = new Object[size];
        if(size > 0)
        {
            if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
            {
                for(int vcount = 0; vcount < size; vcount++)
                {
                    switch (type)
                    {
                        case "RD":
                            arr[vcount] = bits.bit_read_RD(dat);
                            break;
                        default:
                            arr[vcount] = null;

                    }
                }
            }
        }
        return arr;
    }

    static Object[] FIELD_VECTOR_N(Bit_Chain dat, String type, int size, int dxf)
    {
        Object[] arr = new Object[size];
        if(size > 0)
        {
            if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
            {
                for(int vcount = 0; vcount < size; vcount++)
                {
                    switch (type)
                    {
                        case "RD":
                            arr[vcount] = bits.bit_read_RD(dat);
                            break;
                        default:
                            arr[vcount] = null;

                    }
                }
            }
        }
        return arr;
    }

    static Dwg_Bitcode_TimeRLL FIELD_TIMERLL(Bit_Chain dat, int dxf) {
        Dwg_Bitcode_TimeRLL time = new Dwg_Bitcode_TimeRLL();
        time = bits.bit_read_TIMERLL(dat);
        return time;
    }

    static Dwg_Bitcode_TimeBLL FIELD_TIMEBLL(Bit_Chain dat, int dxf) {
        Dwg_Bitcode_TimeBLL time = new Dwg_Bitcode_TimeBLL();
        time = bits.bit_read_TIMEBLL(dat);
        return time;
    }

    private static int TYPE_MAXELEMSIZE(String type) {
        Map<String, Integer> dwgBitsSizeMap = new HashMap<>();
        dwgBitsSizeMap.put("UNKNOWN", 0);
        dwgBitsSizeMap.put("RC", 8);
        dwgBitsSizeMap.put("RS", 16);
        dwgBitsSizeMap.put("RL", 32);
        dwgBitsSizeMap.put("B", 1);
        dwgBitsSizeMap.put("BB", 2);
        dwgBitsSizeMap.put("3B", 3);
        dwgBitsSizeMap.put("4BITS", 4);
        dwgBitsSizeMap.put("BS", 2);
        dwgBitsSizeMap.put("BL", 2);
        dwgBitsSizeMap.put("BLd", 2);
        dwgBitsSizeMap.put("RLL", 64);
        dwgBitsSizeMap.put("RD", 64);
        dwgBitsSizeMap.put("BD", 2);
        dwgBitsSizeMap.put("MC", 1);
        dwgBitsSizeMap.put("UMC", 1);
        dwgBitsSizeMap.put("MS", 16);
        dwgBitsSizeMap.put("TV", 2);
        dwgBitsSizeMap.put("TU", 18);
        dwgBitsSizeMap.put("T", 2);
        dwgBitsSizeMap.put("TF", 1);
        dwgBitsSizeMap.put("T32", 2);
        dwgBitsSizeMap.put("TU32", 4);
        dwgBitsSizeMap.put("HANDLE", 8);
        dwgBitsSizeMap.put("BE", 1);
        dwgBitsSizeMap.put("DD", 2);
        dwgBitsSizeMap.put("BT", 1);
        dwgBitsSizeMap.put("BOT", 10);
        dwgBitsSizeMap.put("BLL", 3);
        dwgBitsSizeMap.put("TIMEBLL", 4);
        dwgBitsSizeMap.put("CMC", 2);
        dwgBitsSizeMap.put("ENC", 4);
        dwgBitsSizeMap.put("2RD", 128);
        dwgBitsSizeMap.put("3RD", 196);
        dwgBitsSizeMap.put("2BD", 4);
        dwgBitsSizeMap.put("3BD", 6);
        dwgBitsSizeMap.put("2DD", 4);
        dwgBitsSizeMap.put("3DD", 6);
        dwgBitsSizeMap.put("CRC", 8);
        dwgBitsSizeMap.put("CRC64", 64);
        dwgBitsSizeMap.put("RLLd", 64);

        return dwgBitsSizeMap.get(type);
    }

    static int _VECTOR_CHKCOUNT_STATIC(char[] nam, int siz, int maxelemsize, Bit_Chain dat)
    {
        if((8L * siz) > AVAIL_BITS(dat) || ((long)(siz) * (maxelemsize)) > AVAIL_BITS(dat)
        || dat._byte + (siz) > dat.size)
        {
            return DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value;
        }
        return 0;
    }
    static int _VECTOR_CHKCOUNT_STATIC(long[] nam, int siz, int maxelemsize, Bit_Chain dat)
    {
        if((8L * siz) > AVAIL_BITS(dat) || ((long)(siz) * (maxelemsize)) > AVAIL_BITS(dat)
                || dat._byte + (siz) > dat.size)
        {
            return DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value;
        }
        return 0;
    }

    static long AVAIL_BITS(Bit_Chain dat) {
        return ((dat.size * 8) - bits.bit_position(dat));
    }

    static long FIELD_BLL(Bit_Chain dat, String type, int dxf) {
        return (long)FIELDG(dat,type,dxf);
    }

    private static Object FIELDG(Bit_Chain dat, String type, int dxf) {
        return switch (type) {
            case "BLL" -> bits.bit_read_BLL(dat);
            case "RLL" -> bits.bit_read_RLL(dat);
            case "RL" -> bits.bit_read_RL(dat);
            case "BL" -> bits.bit_read_BL(dat);
            case "BS" -> bits.bit_read_BS(dat);
            case "B" -> bits.bit_read_B(dat);
            case "TV" -> bits.bit_read_TV(dat);
            default -> null;
        };
    }

    static double FIELD_BD(Bit_Chain dat, String type, int dxf) {
        double val = bits.bit_read_BD(dat);
        if(bits.bit_isnan(val))
        {
            return DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value;
        }
        return val;
    }

    static String FIELD_TV(Bit_Chain dat, String type, int dxf) {
        String value = "";
        value = bits.bit_read_TV(dat);
        return value;
    }

    static long FIELD_BL(Bit_Chain dat, String type, int dxf) {
        return (long)((long)FIELDG(dat,type,dxf) & IntMask.UINT32_MASK);
    }

    static int FIELD_BS(Bit_Chain dat, String type, int dxf) {
        return (int)((int)FIELDG(dat,type,dxf) & IntMask.UINT32_MASK);
    }

    static Dwg_Object_Ref FIELD_HANDLE(Bit_Chain dat, Dwg_Object obj, Dwg_Data objDwgData, int code, int dxf) {
        Dwg_Object_Ref ref = new Dwg_Object_Ref();
        ref = VALUE_HANDLE(dat,ref,code,obj,objDwgData,dxf);
        return ref;
    }
    static Dwg_Object_Ref FIELD_DATAHANDLE(Bit_Chain hdl_dat, Dwg_Object obj, Dwg_Data objDwgData, int code, int dxf) {
        Dwg_Object_Ref ref = new Dwg_Object_Ref();
        ref = dwg_decode_handleref(hdl_dat,obj,objDwgData);
        return ref;
    }

     static Dwg_Object_Ref VALUE_HANDLE(Bit_Chain hdl_dat,Dwg_Object_Ref ref, int code, Dwg_Object obj, Dwg_Data dwgObj,int dxf) {
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,hdl_dat))
        {
            long _pos = bits.bit_position(hdl_dat);
            if(ref != null)
                ref = null;

           // ref = dwg_decode_preR13_handleref(dat,code,dwgObj);
        }
        else {
            long _pos = bits.bit_position(hdl_dat);
            if(code >= 0)
            {
                ref = dwg_decode_handleref_with_code(hdl_dat,obj,dwgObj,code);
            }else{
                ref = dwg_decode_handleref(hdl_dat,obj,dwgObj);
            }
        }
        return ref;
    }

    private static Dwg_Object_Ref dwg_decode_handleref(Bit_Chain hdl_dat, Dwg_Object obj, Dwg_Data dwgObj) {
        Dwg_Object_Ref ref = new Dwg_Object_Ref();
        if(ref == null)
        {
            return null;
        }
        if(bits.bit_read_H(hdl_dat,ref.handleref) != 0)
        {
            return null;
        }
        if(ref.handleref.size != 0 || (obj != null && ref.handleref.code > 5))
        {
            if (dwg_decode_add_object_ref (dwgObj, ref) != 0)
            {
                return null;
            }
        }
        else if(ref.handleref.value == 0) {
            ref.absolute_ref = 0;
            ref.obj = null;
            return ref;
        }
        if(obj == null)
        {
            if(ref.handleref.value != 0)
            {
                ref.absolute_ref = ref.handleref.value;
                ref.obj = null;
                return ref;
            }
            if(ref.handleref.code >= 6)
            {
                ref.obj = null;
                return null;
            }
        }

        switch (ref.handleref.code)
        {
            case 6:
                ref.absolute_ref = (obj.handle.value + 1);
                break;
            case 8:
                ref.absolute_ref = (obj.handle.value - 1);
                break;
            case 10:
                ref.absolute_ref = (obj.handle.value + ref.handleref.value);
                break;
            case 12:
                ref.absolute_ref = (obj.handle.value - ref.handleref.value);
                break;
            case 14:
                ref.absolute_ref = obj.handle.value;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                ref.absolute_ref = ref.handleref.value;
            case 0:
                ref.absolute_ref = ref.handleref.value;
                break;
            default:
                ref.absolute_ref = 0;
                ref.obj = null;
                System.out.println("Invalid handle pointer code %d "+ ref.handleref.code);
                break;
        }
        return ref;
    }

    static int dwg_decode_add_object_ref(Dwg_Data dwgObj, Dwg_Object_Ref ref) {
        Dwg_Object_Ref[] object_ref_old = dwgObj.object_ref;
        if (dwgObj.num_object_refs == 0) {
            dwgObj.object_ref = new Dwg_Object_Ref[REFS_PER_REALLOC];
        } else if (dwgObj.num_object_refs % REFS_PER_REALLOC == 0) {
            Dwg_Object_Ref[] newObjectRef = new Dwg_Object_Ref[dwgObj.num_object_refs + REFS_PER_REALLOC];
            if (dwgObj.object_ref != null) {
                System.arraycopy(dwgObj.object_ref, 0, newObjectRef, 0, dwgObj.num_object_refs);
            }
            dwgObj.object_ref = newObjectRef;
            dwgObj.dirty_refs = 1;
        }
        if (dwgObj.object_ref == null) {
            dwgObj.object_ref = object_ref_old;
            return DWG_ERROR.DWG_ERR_OUTOFMEM.value;
        }
        dwgObj.object_ref[dwgObj.num_object_refs++] = ref;
        ref.handleref.is_global = 1;
        return 0;
    }


    private static Dwg_Object_Ref dwg_decode_handleref_with_code(Bit_Chain dat, Dwg_Object obj, Dwg_Data dwgObj, int code) {
        Dwg_Object_Ref ref = new Dwg_Object_Ref();
        if(ref == null)
        {
            return null;
        }
        if(bits.bit_read_H(dat,ref.handleref) != 0)
        {
            return null;
        }

        if(ref.handleref.size != 0 || (obj != null && ref.handleref.code > 5))
        {
//            if (dwg_decode_add_object_ref (dwg, ref))
//            {
//                free (ref);
//                return NULL;
//            }
        }
        else if(ref.handleref.value == 0) {
            ref.absolute_ref = 0;
            ref.obj = null;
            return ref;
        }
        if(obj == null)
        {
            ref.absolute_ref = ref.handleref.value;
            ref.obj = null;
            return ref;
        }

        switch (ref.handleref.code) {
            case 0x06:
                ref.absolute_ref = obj.handle.value + 1;
                break;
            case 0x08:
                ref.absolute_ref = obj.handle.value - 1;
                break;
            case 0x0A:
                ref.absolute_ref = obj.handle.value + ref.handleref.value;
                break;
            case 0x0C:
                ref.absolute_ref = obj.handle.value - ref.handleref.value;
                break;
            case 0x0E: // e.g., 2007 REGION.history_id (some very high number)
                ref.absolute_ref = obj.handle.value;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                ref.absolute_ref = ref.handleref.value;
                break;
            case 0: // ignore?
                ref.absolute_ref = ref.handleref.value;
                break;
            default:
                // Equivalent handling for logging and cleanup in Java
                ref.absolute_ref = 0;
                ref.obj = null;
                System.err.printf("Invalid handle pointer code %d%n", ref.handleref.code);
                break;
        }
        return ref;
    }

    static char FIELD_B(Bit_Chain dat, String type, int dxf) {
        return (char) (int)FIELDG(dat, type, dxf);
    }

    static short FIELD_BSd(Bit_Chain dat, String type, String cast, int dxf) {
        return (short)(int)FIELD_CAST(dat,cast,dxf);
    }

    static Dwg_Color FIELD_CMC(Bit_Chain dat, Bit_Chain str_dat, int dxf) {
        Dwg_Color color = new Dwg_Color();
        color = bits.bit_read_CMC(dat, str_dat);
        return color;
    }
    static Dwg_Color SUB_FIELD_CMC(Bit_Chain dat, Bit_Chain str_dat, int dxf) {
        Dwg_Color color = new Dwg_Color();
        color = bits.bit_read_CMC(dat, str_dat);
        return color;
    }


    static Dwg_Bitcode_3BD FIELD_3BD(Bit_Chain dat, int dxf) {
        Dwg_Bitcode_3BD val = new Dwg_Bitcode_3BD();
        val.x = bits.bit_read_BD(dat);
        val.y = bits.bit_read_BD(dat);
        val.z = bits.bit_read_BD(dat);
        if(bits.bit_isnan(val.x) || bits.bit_isnan(val.y) || bits.bit_isnan(val.z))
        {
            System.out.print("DWG_ERR_VALUEOUTOFBOUNDS"+dxf);
            return null;
        }
        return val;
    }
    static Dwg_Bitcode_3BD FIELD_3BD_BIG(Bit_Chain dat, int dxf) {
        Dwg_Bitcode_3BD val = new Dwg_Bitcode_3BD();
        val.x = bits.bit_read_BD(dat);
        val.y = bits.bit_read_BD(dat);
        val.z = bits.bit_read_BD(dat);
        if(bits.bit_isnan(val.x) || bits.bit_isnan(val.y) || bits.bit_isnan(val.z))
        {
            System.out.print("DWG_ERR_VALUEOUTOFBOUNDS"+dxf);
            return null;
        }
        return val;
    }

    static Dwg_Bitcode_3RD FIELD_3RD(Bit_Chain dat, int dxf) {
        Dwg_Bitcode_3RD val = new Dwg_Bitcode_3RD();
        val.x = bits.bit_read_RD(dat);
        val.y = bits.bit_read_RD(dat);
        val.z = bits.bit_read_RD(dat);
        if(bits.bit_isnan(val.x) || bits.bit_isnan(val.y) || bits.bit_isnan(val.z))
        {
            System.out.print("DWG_ERR_VALUEOUTOFBOUNDS"+dxf);
            return null;
        }
        return val;
    }

    static Dwg_Bitcode_2BD FIELD_2BD(Bit_Chain dat, int dxf) {
        Dwg_Bitcode_2BD val = new Dwg_Bitcode_2BD();
        val.x = bits.bit_read_BD(dat);
        val.y = bits.bit_read_BD(dat);
        if(bits.bit_isnan(val.x) || bits.bit_isnan(val.y))
        {
            System.out.print("DWG_ERR_VALUEOUTOFBOUNDS"+dxf);
            return null;
        }
        return val;
    }

    static Dwg_Bitcode_2RD FIELD_2RD(Bit_Chain dat, int dxf) {
        Dwg_Bitcode_2RD val = new Dwg_Bitcode_2RD();
        val.x = bits.bit_read_RD(dat);
        val.y = bits.bit_read_RD(dat);
        if(bits.bit_isnan(val.x) || bits.bit_isnan(val.y))
        {
            System.out.print("DWG_ERR_VALUEOUTOFBOUNDS"+dxf);
            return null;
        }
        return val;
    }

    static long FIELD_BLx(Bit_Chain dat, String type, String cast, int dxf) {
        return (long)FIELD_CAST(dat,cast,dxf);
    }

    static long FIELD_BLd(Bit_Chain dat, String type, String cast, int dxf) {
        return (long)FIELD_CAST(dat,cast,dxf);
    }

    static long FIELD_RLL(Bit_Chain dat, String type, int dxf) {
        return bits.bit_read_RLL(dat);
    }

    static String FIELD_TFF(Bit_Chain dat, long len, String type, int dxf) {
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
          //  _VECTOR_CHKCOUNT_STATIC()
        }
        String nam = "";
        nam = bits.bit_read_fixed(dat,len);
        return nam;
    }


    static int FIELD_BSx(Bit_Chain dat, String type, int dxf) {
        return (int)FIELD_CAST(dat,"BS",dxf);
    }

    static char SUB_FIELD_RCd(Bit_Chain dat, String type, int dxf) {
        return (char)SUB_FIELD_CAST(dat,"RC",dxf);
    }

    static Object SUB_FIELD_CAST(Bit_Chain dat, String type, int dxf) {
        switch (type)
        {
            case "RC":
                return bits.bit_read_RC(dat);
            case "BL":
                return bits.bit_read_BL(dat);
            default:
                return null;
        }
    }


    static long SUB_FIELD_BL(Bit_Chain dat, String type, int dxf) {
        return (long)SUB_FIELD_CAST(dat,type,dxf);
    }

    static char[] SUB_FIELD_VECTOR_INL(Bit_Chain dat, String type, int size, int dxf) {
        char[] arr = new char[size];
       int err =  _VECTOR_CHKCOUNT_STATIC(arr,size,TYPE_MAXELEMSIZE(type),dat);
       if(err == 0)
       {
           for (int i = 0; i < size; i++)
           {
               arr[i] = bits.bit_read_RC(dat);
           }
       }
        return arr;
    }

    static String FIELD_T16(Bit_Chain dat, String type, int i) {
        String value = "";
        if(dat.from_version.ordinal() < DWG_VERSION_TYPE.R_2007.ordinal())
        {
            value = bits.bit_read_T16(dat);
        }
        else {
            value = bits.bit_read_TU16(dat);
        }
        return value;
    }

    static int dwg_decode_token(Bit_Chain dat, Dwg_Object obj, String name,
                                DWG_OBJECT_TYPE type, Bit_Chain hdl_dat, Bit_Chain str_dat)
    {
        int error = dwg_setup_token(obj,name,type);
        if(error != 0)
        {
            return error;
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            Bit_Chain obj_dat = dat;
            str_dat = new Bit_Chain(dat);
            error = dwg_decode_token_private(obj_dat,hdl_dat,str_dat,obj,name);
        }
        else {
            error = dwg_decode_token_private(dat,hdl_dat,dat,obj,name);
        }
        return error;
    }

    static int dwg_decode_token_private(Bit_Chain dat, Bit_Chain hdl_dat,
                                        Bit_Chain str_dat, Dwg_Object obj, String name)
    {
        int error = 0;
        Object _obj = getObject(name,obj.tio.object);
        error = decode.dwg_decode_object(dat,hdl_dat,str_dat,obj.tio.object);
        if(error >= DWG_ERROR.DWG_ERR_CRITICAL || dat._byte > dat.size)
        {
            return error;
        }

        return error;
    }

    static int dwg_setup_token(Dwg_Object obj, String name, DWG_OBJECT_TYPE type)
    {
        obj.tio.object = new Dwg_Object_Object();
        Object _obj = getObject(name,obj.tio.object);

        obj.supertype = DWG_OBJECT_SUPERTYPE.DWG_SUPERTYPE_OBJECT;
        if(obj.tio.object == null)
        {
            return DWG_ERROR.DWG_ERR_OUTOFMEM.value;
        }
        if(_obj == null)
        {
            obj.tio.object = null;
            obj.fixedtype = DWG_OBJECT_TYPE.DWG_TYPE_FREED;
            return DWG_ERROR.DWG_ERR_OUTOFMEM.value;
        }
        if(obj.fixedtype.value == 0)
        {
            obj.fixedtype = type;
            obj.name = name;
        }
        if(obj.type == 0 && obj.fixedtype.value <= DWG_OBJECT_TYPE.DWG_TYPE_LAYOUT.value)
        {
            obj.type = type.value;
        }
        if(obj.dxfname == null)
        {
            obj.dxfname = name;
        }
        if((obj.parent.opts & dwg.DWG_OPTS_IN) != 0)
        {
            obj.name = obj.dxfname;
            if((obj.parent.opts & dwg.DWG_OPTS_INJSON) != 0)
            {
                obj.name = obj.name;
            }
        }
        if(_obj instanceof ICommon common)
        {
            common.getCommon().setParent(obj.tio.object);
        }
        else if(_obj instanceof IParent parent)
        {
            parent.setParent(obj.tio.object);
        }
        else {
            throw new UnsupportedOperationException("Not implemented for this type");
        }

        obj.tio.object.dwg = obj.parent;
        obj.tio.object.objid = obj.index;

        return 0;
    }

    static Object getObject(String name, Dwg_Object_Object objDwgObject) {
        switch (name)
        {
            case "BLOCK_CONTROL":
                if (objDwgObject.tio.BLOCK_CONTROL == null)
                {
                    objDwgObject.tio.BLOCK_CONTROL = new Dwg_Object_BLOCK_CONTROL();
                    objDwgObject.tio.BLOCK_CONTROL.common.parent = objDwgObject;
                    return objDwgObject.tio.BLOCK_CONTROL;
                }
                else
                    return objDwgObject.tio.BLOCK_CONTROL;
            case "LAYER_CONTROL":
                if (objDwgObject.tio.LAYER_CONTROL == null)
                {
                    objDwgObject.tio.LAYER_CONTROL = new Dwg_Object_LAYER_CONTROL();
                    objDwgObject.tio.LAYER_CONTROL.common.parent = objDwgObject;
                    return objDwgObject.tio.LAYER_CONTROL;
                }
                else
                    return objDwgObject.tio.LAYER_CONTROL;

            case "STYLE_CONTROL":
                if (objDwgObject.tio.STYLE_CONTROL == null)
                {
                    objDwgObject.tio.STYLE_CONTROL = new Dwg_Object_STYLE_CONTROL();
                    objDwgObject.tio.STYLE_CONTROL.common.parent = objDwgObject;
                    return objDwgObject.tio.STYLE_CONTROL;
                }
                else
                    return objDwgObject.tio.STYLE_CONTROL;

            case "LTYPE_CONTROL":
                if (objDwgObject.tio.LTYPE_CONTROL == null)
                {
                    objDwgObject.tio.LTYPE_CONTROL = new Dwg_Object_LTYPE_CONTROL();
                    objDwgObject.tio.LTYPE_CONTROL.common.parent = objDwgObject;
                    return objDwgObject.tio.LTYPE_CONTROL;
                }
                else
                    return objDwgObject.tio.LTYPE_CONTROL;

            case "VIEW_CONTROL":
                if (objDwgObject.tio.VIEW_CONTROL == null)
                {
                    objDwgObject.tio.VIEW_CONTROL = new Dwg_Object_VIEW_CONTROL();
                    objDwgObject.tio.VIEW_CONTROL.common.parent = objDwgObject;
                    return objDwgObject.tio.VIEW_CONTROL;
                }
                else
                    return objDwgObject.tio.VIEW_CONTROL;
            case "UCS_CONTROL":
                if (objDwgObject.tio.UCS_CONTROL == null)
                {
                    objDwgObject.tio.UCS_CONTROL = new Dwg_Object_UCS_CONTROL();
                    objDwgObject.tio.UCS_CONTROL.common.parent = objDwgObject;
                    return objDwgObject.tio.UCS_CONTROL;
                }
                else
                    return objDwgObject.tio.UCS_CONTROL;
            case "VPORT_CONTROL":
                if (objDwgObject.tio.VPORT_CONTROL == null)
                {
                    objDwgObject.tio.VPORT_CONTROL = new Dwg_Object_VPORT_CONTROL();
                    objDwgObject.tio.VPORT_CONTROL.common.parent = objDwgObject;
                    return objDwgObject.tio.VPORT_CONTROL;
                }
                else
                    return objDwgObject.tio.VPORT_CONTROL;
            case "APPID_CONTROL":
                if (objDwgObject.tio.APPID_CONTROL == null)
                {
                    objDwgObject.tio.APPID_CONTROL = new Dwg_Object_APPID_CONTROL();
                    objDwgObject.tio.APPID_CONTROL.common.parent = objDwgObject;
                    return objDwgObject.tio.APPID_CONTROL;
                }
                else
                    return objDwgObject.tio.APPID_CONTROL;
            case "DIMSTYLE_CONTROL":
                if (objDwgObject.tio.DIMSTYLE_CONTROL == null)
                {
                    objDwgObject.tio.DIMSTYLE_CONTROL = new Dwg_Object_DIMSTYLE_CONTROL();
                    objDwgObject.tio.DIMSTYLE_CONTROL.common.parent = objDwgObject;
                    return objDwgObject.tio.DIMSTYLE_CONTROL;
                }
                else
                    return objDwgObject.tio.DIMSTYLE_CONTROL;
            case "VX_CONTROL":
                if (objDwgObject.tio.VX_CONTROL == null)
                {
                    objDwgObject.tio.VX_CONTROL = new Dwg_Object_VX_CONTROL();
                    objDwgObject.tio.VX_CONTROL.common.parent = objDwgObject;
                    return objDwgObject.tio.VX_CONTROL;
                }
                else
                    return objDwgObject.tio.VX_CONTROL;
            case "DICTIONARY":
                if (objDwgObject.tio.DICTIONARY == null)
                {
                    objDwgObject.tio.DICTIONARY = new Dwg_Object_DICTIONARY();
                    //objDwgObject.tio.DICTIONARY.parent = objDwgObject;
                    objDwgObject.tio.DICTIONARY.setParent(objDwgObject);
                    return objDwgObject.tio.DICTIONARY;
                }
                else
                    return objDwgObject.tio.DICTIONARY;
            case "DICTIONARYWDFLT":
                if (objDwgObject.tio.DICTIONARYWDFLT == null)
                {
                    objDwgObject.tio.DICTIONARYWDFLT = new Dwg_Object_DICTIONARYWDFLT();
                    objDwgObject.tio.DICTIONARYWDFLT.setParent(objDwgObject);
                    return objDwgObject.tio.DICTIONARYWDFLT;
                }
                else
                    return objDwgObject.tio.DICTIONARYWDFLT;
            case "PLACEHOLDER":
                if (objDwgObject.tio.PLACEHOLDER == null)
                {
                    objDwgObject.tio.PLACEHOLDER = new Dwg_Object_PLACEHOLDER();
                    objDwgObject.tio.PLACEHOLDER.setParent(objDwgObject);
                    return objDwgObject.tio.PLACEHOLDER;
                }
                else
                    return objDwgObject.tio.PLACEHOLDER;
            case "LAYER":
                if (objDwgObject.tio.LAYER == null)
                {
                    objDwgObject.tio.LAYER = new Dwg_Object_LAYER();
                    objDwgObject.tio.LAYER.common.setParent(objDwgObject);
                    return objDwgObject.tio.LAYER;
                }
                else
                    return objDwgObject.tio.LAYER;
            case "STYLE":
                if (objDwgObject.tio.STYLE == null)
                {
                    objDwgObject.tio.STYLE = new Dwg_Object_STYLE();
                    objDwgObject.tio.STYLE.common.setParent(objDwgObject);
                    return objDwgObject.tio.STYLE;
                }
                else
                    return objDwgObject.tio.STYLE;
            case "APPID":
                if (objDwgObject.tio.APPID == null)
                {
                    objDwgObject.tio.APPID = new Dwg_Object_APPID();
                    objDwgObject.tio.APPID.common.setParent(objDwgObject);
                    return objDwgObject.tio.APPID;
                }
                else
                    return objDwgObject.tio.APPID;
            case "LTYPE":
                if (objDwgObject.tio.LTYPE == null)
                {
                    objDwgObject.tio.LTYPE = new Dwg_Object_LTYPE();
                    objDwgObject.tio.LTYPE.common.setParent(objDwgObject);
                    return objDwgObject.tio.LTYPE;
                }
                else
                    return objDwgObject.tio.LTYPE;
            case "MLINESTYLE":
                if (objDwgObject.tio.MLINESTYLE == null)
                {
                    objDwgObject.tio.MLINESTYLE = new Dwg_Object_MLINESTYLE();
                    objDwgObject.tio.MLINESTYLE.setParent(objDwgObject);
                    return objDwgObject.tio.MLINESTYLE;
                }
                else
                    return objDwgObject.tio.MLINESTYLE;
            case "UNKNOWN_OBJ":
                if (objDwgObject.tio.UNKNOWN_OBJ == null)
                {
                    objDwgObject.tio.UNKNOWN_OBJ = new Dwg_Object_UNKNOWN_OBJ();
                    objDwgObject.tio.UNKNOWN_OBJ.setParent(objDwgObject);
                    return objDwgObject.tio.UNKNOWN_OBJ;
                }
                else
                    return objDwgObject.tio.UNKNOWN_OBJ;
            default:
                throw new IllegalArgumentException("Invalid Type");
        }
    }

    static void CONTROL_HANDLE_STREAM(Dwg_Object obj, Bit_Chain hdl_dat, Bit_Chain dat,
                                      Dwg_Data objDwgData, Dwg_Object_Ref ref)

    {
        assert obj.supertype == DWG_OBJECT_SUPERTYPE.DWG_SUPERTYPE_OBJECT;
        if(commen.PRE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            hdl_dat._byte = dat._byte;
            hdl_dat.bit = dat.bit;
        }
        if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            ref = dec_macros.VALUE_HANDLE(hdl_dat,ref,4,obj,objDwgData,0);
            obj.tio.object.ownerhandle = ref;
            REACTORS(4,obj,hdl_dat,objDwgData);
            XDICOBJHANDLE(hdl_dat,3,obj,objDwgData);
        }
    }

    static void XDICOBJHANDLE(Bit_Chain dat,int code, Dwg_Object obj, Dwg_Data objDwgData) {
        if(commen.SINCE(DWG_VERSION_TYPE.R_2004a,dat))
        {
            if(obj.tio.object.is_xdic_missing == 0)
            {
                obj.tio.object.xdicobjhandle = new Dwg_Object_Ref();
                obj.tio.object.xdicobjhandle = VALUE_HANDLE(dat,obj.tio.object.xdicobjhandle,code,obj,objDwgData,360);
                if(obj.tio.object.xdicobjhandle == null)
                {
                    obj.tio.object.is_xdic_missing = 1;
                }
            }
        }else {
            if(commen.SINCE(DWG_VERSION_TYPE.R_13b1,dat))
            {
                obj.tio.object.xdicobjhandle = new Dwg_Object_Ref();
                obj.tio.object.xdicobjhandle = VALUE_HANDLE(dat,obj.tio.object.xdicobjhandle,code,obj,objDwgData,360);
            }
        }
    }

    static void REACTORS(int code, Dwg_Object obj, Bit_Chain dat, Dwg_Data objDwgData) {
        if (obj.tio.object.num_reactors > 0) {
            _VECTOR_CHKCOUNT(obj.tio.object.num_reactors, TYPE_MAXELEMSIZE("RS"), dat, obj, obj.tio.object.reactors);
            //obj.tio.object.reactors = Arrays.copyOf(obj.tio.object.reactors, (int) obj.tio.object.num_reactors);
            obj.tio.object.reactors = new Dwg_Object_Ref[(int)obj.tio.object.num_reactors];
            for (int vcount = 0; vcount < obj.tio.object.num_reactors; vcount++) {
                obj.tio.object.reactors[vcount] = new Dwg_Object_Ref();
                obj.tio.object.reactors[vcount] = VALUE_HANDLE_N_SPEC(dat,code,objDwgData,obj,330);
            }
        }
    }

    static void VALUE_HANDLE_N(Bit_Chain dat, int code, Dwg_Data objDwgData,
                               Dwg_Object obj, Dwg_Object_Ref ref, int dxf)
    {

        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            long size = bits.bit_position(dat);
           // ref = dwg_decode_preR13_handleref (dat, code, dwg);
        }else{
            long size = bits.bit_position(dat);
            if(code >= 0)
            {
                ref = dwg_decode_handleref_with_code(dat,obj,objDwgData,code);
            }
            else{
                ref = dwg_decode_handleref(dat,obj,objDwgData);
            }
        }
    }
    static Dwg_Object_Ref VALUE_HANDLE_N_SPEC(Bit_Chain dat, int code, Dwg_Data objDwgData,
                               Dwg_Object obj, int dxf)
    {
        Dwg_Object_Ref ref = new Dwg_Object_Ref();
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            long size = bits.bit_position(dat);
           // ref = dwg_decode_preR13_handleref (dat, code, dwg);
        }else{
            long size = bits.bit_position(dat);
            if(code >= 0)
            {
                ref = dwg_decode_handleref_with_code(dat,obj,objDwgData,code);
            }
            else{
                ref = dwg_decode_handleref(dat,obj,objDwgData);
            }
        }
        return ref;
    }

    static int _VECTOR_CHKCOUNT(long size, int maxelemsize, Bit_Chain dat, Dwg_Object obj,
                                Dwg_Object_Ref[] nam) {
        if (size > AVAIL_BITS(dat) || (size * maxelemsize) > AVAIL_BITS(dat)) {
            // LOG_ERROR("Invalid " + nam + " size " + size + ". Need min. " + (size * maxelemsize) + " bits, have " + AVAIL_BITS(dat, obj) + " for " + SAFEDXFNAME);
            size = 0;
            return DWG_ERROR.DWG_ERR_VALUEOUTOFBOUNDS.value;
        } else {
            return 0; // Check here
        }
    }

    static Dwg_Object_Ref[] HANDLE_VECTOR(Bit_Chain dat, int sizefield,
                                          int code, Dwg_Object obj, Dwg_Data objDwgData, int dxf)
    {
        VECTOR_CHKCOUNT_LV(dat,sizefield,"BS");
        Dwg_Object_Ref[] refs = new Dwg_Object_Ref[sizefield];
        if(sizefield > 0)
        {
            for(int vcount = 0; vcount < sizefield; vcount++)
            {
               // refs[vcount] = new Dwg_Object_Ref();
                refs[vcount] = VALUE_HANDLE_N_SPEC(dat,code,objDwgData,obj,0);
            }
        }
        return refs;
    }

    static void FIELD_HANDLE_N(Bit_Chain dat, int code, Dwg_Data objDwgData, Dwg_Object obj,
                               Dwg_Object_Ref ref,
                                int dxf) {
        ref = new Dwg_Object_Ref();
        VALUE_HANDLE_N(dat,code,objDwgData,obj,ref,0);
    }

    static void VECTOR_CHKCOUNT_LV(Bit_Chain dat, int size, String type) {
        if ((long)(size) > AVAIL_BITS(dat) || ((long) (size) * TYPE_MAXELEMSIZE(type)) > AVAIL_BITS(dat))
        {
            size = 0;
        }
    }


    /**
     * Ending Then OBJECT, ENTITY AND TABLE when read are completed.
    */
    static int DWG_OBJECT_END(Bit_Chain dat, Bit_Chain hdl_dat, Bit_Chain str_dat,
                              Dwg_Object obj, int error) {
        return DWG_ENTITY_END(dat,hdl_dat,str_dat,obj,error);
    }

    static int DWG_ENTITY_END(Bit_Chain dat, Bit_Chain hdl_dat, Bit_Chain str_dat,
                              Dwg_Object obj, int error)
    {
        long pos = obj_stream_position(dat,hdl_dat,str_dat);
        long padding = (obj.size * 8L) - pos;
        bits.bit_set_position(dat,pos);

        return error & ~DWG_ERROR.DWG_ERR_UNHANDLEDCLASS.value;
    }

    private static long obj_stream_position(Bit_Chain dat, Bit_Chain hdl_dat, Bit_Chain str_dat)
    {
        long p1 = bits.bit_position(dat);
        long p2 = bits.bit_position(hdl_dat);

        if(commen.SINCE(DWG_VERSION_TYPE.R_2007a,dat))
        {
            long p3 = bits.bit_position(str_dat);
            if (p2 > p1)
                return Math.max(p3, p2);
            else
                return Math.max(p3, p1);
        }
        else
        {
            return Math.max(p2, p1);
        }
    }

    static long VALUE_RLx(Bit_Chain dat, String type, int dxf) {
        return (long)VALUE(dat,"RL",dxf);
    }

    static Object VALUE(Bit_Chain dat, String type, int dxf) {
        switch (type)
        {
            case "RL":
                return bits.bit_read_RL(dat);
            default: return null;
        }
    }

    static int FIELD_RCu(Bit_Chain dat, int dxf) {
        return (int) bits.bit_read_RC(dat);
    }

    public static String[] FIELD_VECTOR_T(Bit_Chain dat, String type, long size, int dxf) {
        String[] str = new String[(int)size];
        if(size > 0)
        {
            for(int i = 0 ; i < size; i++)
            {
                if(commen.PRE(DWG_VERSION_TYPE.R_2007a,dat))
                {
                    str[i] = bits.bit_read_TV(dat);
                }
                else {
                    str[i] = bits.bit_read_T(dat);
                }
            }
        }
        return str;
    }

    static void START_OBJECT_HANDLE_STREAM(Bit_Chain dat, Dwg_Object obj) {
        START_HANDLE_STREAM(dat,obj);
        assert obj.supertype == DWG_OBJECT_SUPERTYPE.DWG_SUPERTYPE_OBJECT;
    }

    static void START_HANDLE_STREAM(Bit_Chain dat, Dwg_Object obj) {
        long vcount = bits.bit_position(dat);
        if(dat.from_version.ordinal() >= DWG_VERSION_TYPE.R_2007.ordinal())
        {
            vcount++;
        }
        if(obj.hdlpos != vcount)
        {
            bits.bit_set_position(dat,obj.hdlpos);
        }
    }

    static COMMON_TABLE_FIELDS COMMON_TABLE_FLAGS_READ(String name,Bit_Chain dat, Bit_Chain hdlDat, Bit_Chain strDat,
                                                               Dwg_Object obj, Dwg_Data objDwgData)
    {
        COMMON_TABLE_FIELDS acdbname = new COMMON_TABLE_FIELDS();
        if(commen.PRE(DWG_VERSION_TYPE.R_13b1,dat))
        {
            if(name.trim().equals("LAYER"))
            {
                acdbname.flag = (int)dec_macros.FIELD_CAST(dat,"RS",70);
            }
            else {
                acdbname.flag = (int)dec_macros.FIELD_CAST(dat,"RC",70);
            }

            acdbname.name = dec_macros.FIELD_TFv(dat,32,2);
            if(commen.VERSION(DWG_VERSION_TYPE.R_11,dat))
                acdbname.used = (short) dec_macros.FIELD_RS(dat,"RS",0);
        }
        else {
            acdbname.name = dec_macros.FIELD_T(dat, obj,"T",2);
            if(commen.UNTIL(DWG_VERSION_TYPE.R_2004,dat))
            {
                acdbname.is_xref_ref = dec_macros.FIELD_B(dat,"B",0);
                acdbname.is_xref_resolved = dec_macros.FIELD_BS(dat,"BS",0);
                acdbname.is_xref_dep = dec_macros.FIELD_B(dat,"B",0);
            }
            else{
                acdbname.is_xref_ref = 1;
                acdbname.is_xref_resolved = dec_macros.FIELD_BS(dat,"BS",0);
                if(acdbname.is_xref_resolved == 256)
                    acdbname.is_xref_dep = 1;
            }
            acdbname.xref = new Dwg_Object_Ref();
            acdbname.xref = dec_macros.FIELD_HANDLE(hdlDat,obj,objDwgData,5,0);
            acdbname.flag = acdbname.is_xref_dep << 4 | acdbname.is_xref_ref << 6;
        }
        commen.RESET_VER(dat);
        return acdbname;
    }



    static String FIELD_T(Bit_Chain dat, Dwg_Object obj, String type, int dxf) {
        if(dat.from_version.ordinal() < DWG_VERSION_TYPE.R_2007.ordinal())
        {
            return bits.bit_read_TV(dat);
        }
        else {
            if(obj == null || obj.has_strings != 0)
            {
                return bits.bit_read_TU(dat);
            }
        }
        return null;
    }

    static String FIELD_TFv(Bit_Chain dat, int size, int dxf) {
        return bits.bit_read_TF(dat,size);
    }

    public static double FIELD_RD(Bit_Chain dat, String type, int dxf) {
        return bits.bit_read_RD(dat);
    }

    public static double SUB_FIELD_BD(Bit_Chain dat, String type, int dxf)
    {
        return FIELD_BD(dat,type,dxf);
    }

    public static int SUB_FIELD_BS(Bit_Chain dat, String type, int dxf) {
        return (int)FIELDG(dat,type,dxf);
    }
    public static int SUB_FIELD_BSd(Bit_Chain dat, String type, int dxf) {
        return (int)FIELDG(dat,type,dxf);
    }

    static Dwg_Object_Ref SUB_FIELD_HANDLE(Bit_Chain hdlDat, Dwg_Object_Ref ref,
                                                  int code, Dwg_Object obj, Dwg_Data objDwgData, int dxf)
    {
        return VALUE_HANDLE(hdlDat,ref,code,obj,objDwgData,dxf);
    }

    static double SUB_FIELD_RD(Bit_Chain dat, String type, int dxf)
    {
        return FIELD_RD(dat,type,dxf);
    }

    static String SUB_FIELD_T(Bit_Chain dat, Dwg_Object obj, String type, int dxf) {
        return FIELD_T(dat,obj,type,dxf);
    }

    static String FIELD_BINARY(Bit_Chain dat, int len, int dxf) {
        return bits.bit_read_TF(dat,len);
    }
}
