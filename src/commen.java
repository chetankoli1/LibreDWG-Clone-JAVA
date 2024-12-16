class dwg_versions{
    public DWG_VERSION_TYPE r;
    public String type;
    public String hdr;
    public String desc;
    public int dwg_version;

    public dwg_versions(DWG_VERSION_TYPE r, String type, String hdr, String desc, int dwg_version) {
        this.r = r;
        this.type = type;
        this.hdr = hdr;
        this.desc = desc;
        this.dwg_version = dwg_version;
    }

    dwg_versions(){

    }
}

class commonvar{
    public static final String CreatedBy = "LibreDWG UNKNOWN";
}

public class commen {
    static DWG_VERSION_TYPE dwg_version_hdr_type(String hdr) {
        for(int i = DWG_VERSION_TYPE.R_AFTER.ordinal() - 1; i > 0; i--)
            if (strEQ(dwg_versions[i].hdr, hdr)) {
                return dwg_versions[i].r;
            }
        return DWG_VERSION_TYPE.R_INVALID;
    }

    static DWG_VERSION_TYPE dwg_version_hdr_type2(String hdr, int dwg_version)
    {
        for(int i = DWG_VERSION_TYPE.R_AFTER.ordinal() - 1; i > 0; i--)
            if (strEQ(dwg_versions[i].hdr, hdr)) {
                if(dwg_version == 0 || dwg_versions[i].dwg_version == dwg_version)
                    return dwg_versions[i].r;
            }
        return DWG_VERSION_TYPE.R_INVALID;
    }




    public static void strcpy(char[] src, char[] dest, int length)
    {
        for(int i = 0; i < length; i++)
        {
            src[i] = dest[i];
        }
    }

    public static void strcpy(char[] src, byte[] dest, int length)
    {
        for(int i = 0; i < length; i++)
        {
            src[i] = (char)dest[i];
        }
    }

    public static boolean strEQ(String src, String dest)
    {
        if(src != null && src.equals(dest))
        {
            return true;
        }
        return false;
    }

    public static boolean strcmp(String str1, String str2, int n) {
        if (str1 == null || str2 == null) {
            throw new IllegalArgumentException("Null strings are not allowed");
        }

        // Ensure n doesn't exceed the length of either string
        int minLength = Math.min(Math.min(str1.length(), str2.length()), n);

        for (int i = 0; i < minLength; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return false;
            }
        }

        return minLength == n; // True if exactly n characters were compared
    }

    static boolean VERSIONS(DWG_VERSION_TYPE v1,DWG_VERSION_TYPE v2,Bit_Chain dat)
    {
        return dat.version.ordinal() >= v1.ordinal() && dat.version.ordinal() <= v2.ordinal();
    }

    static boolean SINCE(DWG_VERSION_TYPE version, Bit_Chain dat)
    {
        return dat.version.ordinal() >= version.ordinal();
    }
    static boolean PRE(DWG_VERSION_TYPE version, Bit_Chain dat)
    {
        return dat.version.ordinal() < version.ordinal();
    }

    static dwg_versions[] dwg_versions =
            {
                    new dwg_versions(DWG_VERSION_TYPE.R_INVALID, "invalid", "INVALI", "No DWG", 0),
                    new dwg_versions(DWG_VERSION_TYPE.R_1_1, "r1.1", "MC0.0", "MicroCAD Release 1.1", 0),
                    new dwg_versions(DWG_VERSION_TYPE.R_1_2, "r1.2", "AC1.2", "AutoCAD Release 1.2", 0),
                    new dwg_versions(DWG_VERSION_TYPE.R_1_3, "r1.3", "AC1.3", "AutoCAD Release 1.3", 1),
                    new dwg_versions(DWG_VERSION_TYPE.R_1_4, "r1.4", "AC1.40", "AutoCAD Release 1.4", 2),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_0b, "r2.0b", "AC1.50", "AutoCAD 2.0 beta", 3),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_0, "r2.0", "AC1.50", "AutoCAD Release 2.0", 4),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_10, "r2.10", "AC2.10", "AutoCAD Release 2.10", 5),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_21, "r2.21", "AC2.21", "AutoCAD Release 2.21", 6),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_22, "r2.22", "AC2.22", "AutoCAD Release 2.22", 7),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_4, "r2.4", "AC1001", "AutoCAD Release 2.4", 8),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_5, "r2.5", "AC1002", "AutoCAD Release 2.5", 9),
                    new dwg_versions(DWG_VERSION_TYPE.R_2_6, "r2.6", "AC1003", "AutoCAD Release 2.6", 10),
                    new dwg_versions(DWG_VERSION_TYPE.R_9, "r9", "AC1004", "AutoCAD Release 9", 0xb),
                    new dwg_versions(DWG_VERSION_TYPE.R_9c1, "r9c1", "AC1005", "AutoCAD Release 9c1", 0xc),
                    new dwg_versions(DWG_VERSION_TYPE.R_10, "r10", "AC1006", "AutoCAD Release 10", 0xd),
                    new dwg_versions(DWG_VERSION_TYPE.R_11b1, "r11b1", "AC1007", "AutoCAD 11 beta 1", 0xe),
                    new dwg_versions(DWG_VERSION_TYPE.R_11b2, "r11b2", "AC1008", "AutoCAD 11 beta 2", 0xf),
                    new dwg_versions(DWG_VERSION_TYPE.R_11, "r11", "AC1009", "AutoCAD Release 11/12 (LT R1/R2)", 0x10),
                    new dwg_versions(DWG_VERSION_TYPE.R_13b1, "r13b1", "AC1010", "AutoCAD pre-R13 a", 0x11),
                    new dwg_versions(DWG_VERSION_TYPE.R_13b2, "r13b2", "AC1011", "AutoCAD pre-R13 b", 0x12),
                    new dwg_versions(DWG_VERSION_TYPE.R_13, "r13", "AC1012", "AutoCAD Release 13", 0x13),
                    new dwg_versions(DWG_VERSION_TYPE.R_13c3, "r13c3", "AC1013", "AutoCAD Release 13c3", 0x14),
                    new dwg_versions(DWG_VERSION_TYPE.R_14, "r14", "AC1014", "AutoCAD Release 14", 0x15),
                    new dwg_versions(DWG_VERSION_TYPE.R_2000b, "r2000b", "AC1500", "AutoCAD 2000 beta", 0x16),
                    new dwg_versions(DWG_VERSION_TYPE.R_2000, "r2000", "AC1015", "AutoCAD Release 2000", 0x17),
                    new dwg_versions(DWG_VERSION_TYPE.R_2000i, "r2000i", "AC1016", "AutoCAD Release 2000i", 0x17),
                    new dwg_versions(DWG_VERSION_TYPE.R_2002, "r2002", "AC1017", "AutoCAD Release 2002", 0x17),
                    new dwg_versions(DWG_VERSION_TYPE.R_2004a, "r2004a", "AC402a", "AutoCAD 2004 alpha a", 0x18),
                    new dwg_versions(DWG_VERSION_TYPE.R_2004b, "r2004b", "AC402b", "AutoCAD 2004 alpha b", 0x18),
                    new dwg_versions(DWG_VERSION_TYPE.R_2004c, "r2004c", "AC1018", "AutoCAD 2004 beta", 0x18),
                    new dwg_versions(DWG_VERSION_TYPE.R_2004, "r2004", "AC1018", "AutoCAD Release 2004", 0x19),
                    new dwg_versions(DWG_VERSION_TYPE.R_2007a, "r2007a", "AC701a", "AutoCAD 2007 alpha", 0x1a),
                    new dwg_versions(DWG_VERSION_TYPE.R_2007b, "r2007b", "AC1021", "AutoCAD 2007 beta", 0x1a),
                    new dwg_versions(DWG_VERSION_TYPE.R_2007, "r2007", "AC1021", "AutoCAD Release 2007", 0x1b),
                    new dwg_versions(DWG_VERSION_TYPE.R_2010b, "r2010b", "AC1024", "AutoCAD 2010 beta", 0x1c),
                    new dwg_versions(DWG_VERSION_TYPE.R_2010, "r2010", "AC1024", "AutoCAD Release 2010", 0x1d),
                    new dwg_versions(DWG_VERSION_TYPE.R_2013b, "r2013b", "AC1027", "AutoCAD 2013 beta", 0x1e),
                    new dwg_versions(DWG_VERSION_TYPE.R_2013, "r2013", "AC1027", "AutoCAD Release 2013", 0x1f),
                    new dwg_versions(DWG_VERSION_TYPE.R_2018b, "r2018b", "AC1032", "AutoCAD 2018 beta", 0x20),
                    new dwg_versions(DWG_VERSION_TYPE.R_2018, "r2018", "AC1032", "AutoCAD Release 2018", 0x21),
                    new dwg_versions(DWG_VERSION_TYPE.R_2022b, "r2022b", "AC103-4", "AutoCAD 2022 beta", 0x24),
                    new dwg_versions(DWG_VERSION_TYPE.R_AFTER, "r>2022", "", "AutoCAD Release >2022", 0),
            };

    public static boolean memBEGINc(String s1, String s2) {
        return (s1.length() >= s2.length() && commen.strcmp(s1,s2,s2.length()));
    }
}
