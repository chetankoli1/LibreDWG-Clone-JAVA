public class specs {
    static boolean DECODER = false;
    static boolean ENCODER = false;
    static final boolean PRINT = false;
    static final boolean DECODER_OR_ENCODER = false;
    static final boolean DXF_OR_PRINT = false;
    static final boolean DXF_OR_FREE = false;
    static final boolean DXF = false;
    static final boolean JSON = false;
    static final boolean FREE = false;
    static final boolean IF_FREE_OR_SINCE = false;  // Custom implementation needed
    static final boolean IF_FREE_OR_VERSIONS = false;  // Custom implementation needed
    static final boolean IF_ENCODE_FROM_EARLIER = false;
    static final boolean IF_ENCODE_FROM_EARLIER_OR_DXF = false;
    static final boolean IF_ENCODE_FROM_PRE_R13 = false;
    static final boolean IF_ENCODE_FROM_PRE_R2000 = false;
    static final boolean IF_IS_ENCODER = false;
    static final boolean IF_IS_DECODER = false;
    static final boolean IF_IS_DXF = false;
    static final boolean IF_IS_FREE = false;
    static final boolean IS_RELEASE = false;

    static boolean IF_FREE_OR_SINCE(DWG_VERSION_TYPE v, Bit_Chain dat)
    {
        return commen.SINCE(v,dat);
    }
    static boolean IF_FREE_OR_VERSIONS(DWG_VERSION_TYPE v1,DWG_VERSION_TYPE v2, Bit_Chain dat)
    {
        return commen.VERSIONS(v1,v2,dat);
    }
}
