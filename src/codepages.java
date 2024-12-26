
enum Dwg_Codepages
{
    CP_UTF8(0),
    CP_US_ASCII(1),
    CP_ISO_8859_1(2),
    CP_ISO_8859_2(3),
    CP_ISO_8859_3(4),
    CP_ISO_8859_4(5),
    CP_ISO_8859_5(6),
    CP_ISO_8859_6(7),
    CP_ISO_8859_7(8),
    CP_ISO_8859_8(9),
    CP_ISO_8859_9(10),
    CP_CP437(11),     // DOS English
    CP_CP850(12),     // DOS Latin-1
    CP_CP852(13),     // DOS Central European
    CP_CP855(14),     // DOS Cyrillic
    CP_CP857(15),     // DOS Turkish
    CP_CP860(16),     // DOS Portuguese
    CP_CP861(17),     // DOS Icelandic
    CP_CP863(18),     // DOS Hebrew
    CP_CP864(19),     // DOS Arabic (IBM)
    CP_CP865(20),     // DOS Nordic
    CP_CP869(21),     // DOS Greek
    CP_CP932(22),     // DOS Japanese (shiftjis)
    CP_MACINTOSH(23), // Macintosh
    CP_BIG5(24),
    CP_CP949(25),     // Korean (Wansung + Johab)
    CP_JOHAB(26),     // Johab
    CP_CP866(27),     // Russian
    CP_ANSI_1250(28), // Central + Eastern European
    CP_ANSI_1251(29), // Cyrillic
    CP_ANSI_1252(30), // Western European
    CP_GB2312(31),    // EUC-CN Chinese
    CP_ANSI_1253(32), // Greek
    CP_ANSI_1254(33), // Turkish
    CP_ANSI_1255(34), // Hebrew
    CP_ANSI_1256(35), // Arabic
    CP_ANSI_1257(36), // Baltic
    CP_ANSI_874(37),  // Thai
    CP_ANSI_932(38),  // Japanese (extended shiftjis, windows-31j)
    CP_ANSI_936(39),  // Simplified Chinese
    CP_ANSI_949(40),  // Korean Wansung
    CP_ANSI_950(41),  // Traditional Chinese
    CP_ANSI_1361(42), // Korean Wansung
    CP_UTF16(43),
    CP_ANSI_1258(44), // Vietnamese
    CP_UNDEFINED(255); // mostly R11

    public int value;
    Dwg_Codepages(int val)
    {
        this.value = val;
    }
    Dwg_Codepages(){}

    static final Dwg_Codepages fromValue(int value)
    {
        for(Dwg_Codepages page : Dwg_Codepages.values())
        {
            if(page.value == value)
            {
                return page;
            }
        }
        return null;
    }
}

public class codepages {
    static char dwg_codepage_uc(Dwg_Codepages cp, int c) {
        if(c < 128)
        {
            return (char)c;
        }else if(cp == Dwg_Codepages.CP_US_ASCII)
        {
            return '\0';
        }
        if(cp == Dwg_Codepages.CP_ISO_8859_1 || cp == Dwg_Codepages.CP_UTF8 || cp == Dwg_Codepages.CP_UTF16)
        {
            return (char) c;
        }
        return codepage_helper(cp,(char)c,0,0);
    }

    static char dwg_codepage_uwc(Dwg_Codepages cp, int c) {
        if(cp == Dwg_Codepages.CP_CP864 && c == 0x25)
        {
            return 0x066a;
        }else if(cp == Dwg_Codepages.CP_CP932 && c == 0x5c)
        {
            return 0x00A5;
        }
        else if(cp == Dwg_Codepages.CP_CP932 && c == 0x7e)
        {
            return 0x203E;
        }
        else if(cp == Dwg_Codepages.CP_JOHAB && c == 0x5c)
        {
            return 0x20A9;
        }
        if(c < 128 || cp == Dwg_Codepages.CP_UTF8 || cp == Dwg_Codepages.CP_UTF16)
        {
            return (char) c;
        }
        return codepage_helper(cp, (char)c,0,1);
    }

    static char codepage_helper(Dwg_Codepages codepage, char wc, int dir, int asian) {
        return '\0';
    }

    static boolean dwg_codepage_isasian(Dwg_Codepages cp) {
        if(cp == Dwg_Codepages.CP_BIG5 &&  cp.value <= Dwg_Codepages.CP_JOHAB.value)
        {
            return true;
        }
        else if(cp.value>= Dwg_Codepages.CP_ANSI_932.value &&
                cp.value <= Dwg_Codepages.CP_ANSI_1258.value)
        {
            return true;
        }
        else if(cp == Dwg_Codepages.CP_GB2312)
        {
            return true;
        }
        else{
            return false;
        }
    }

    static boolean dwg_codepage_is_twobyte(Dwg_Codepages cp, int c) {
        if(cp == Dwg_Codepages.CP_CP932 || cp == Dwg_Codepages.CP_ANSI_932)
        {
            return (cp.value >= 0x80 && c <= 0x9F) || (c >= 0xE0);
        }
        else if(cp == Dwg_Codepages.CP_CP949 ||
                cp == Dwg_Codepages.CP_ANSI_949 || cp == Dwg_Codepages.CP_ANSI_936
                || cp == Dwg_Codepages.CP_ANSI_950)
        {
            return (c & 0x80) != 0;
        }
        else if(cp == Dwg_Codepages.CP_ANSI_1361)
        {
            return (c >= 0x80 && c <= 0x83) || (c >= 0xD4 && c <= 0xD7) || (c == 0xDF) || (c>= 0xFA);
        }
        else if(cp == Dwg_Codepages.CP_GB2312 || cp == Dwg_Codepages.CP_BIG5)
        {
            return true;
        }
        else{
            return false;
        }
    }
}
