import java.util.HashMap;
import java.util.Map;

public class dec_macros {
    static char FIELD_RC(Bit_Chain dat, String type, int dxf)
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

    static Dwg_Bitcode_TimeRLL FIELD_TIMERLL(Bit_Chain dat, int i) {
        Dwg_Bitcode_TimeRLL time = new Dwg_Bitcode_TimeRLL();
        time = bits.bit_read_TIMERLL(dat);
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
            case "RL" -> bits.bit_read_RL(dat);
            case "BL" -> bits.bit_read_BL(dat);
            case "BS" -> bits.bit_read_BS(dat);
            case "B" -> bits.bit_read_B(dat);
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
               // ref = dwg_decode_handleref(hdl_dat,obj,dwgObj);
            }
        }
        return ref;
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
}
