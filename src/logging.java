public class logging {
    public static final int DWG_LOGLEVEL_NONE  = 0;   // no log
    public static final int DWG_LOGLEVEL_ERROR = 1;  // only error and warning messages
    public static final int DWG_LOGLEVEL_INFO  = 2;  // only general info and object codes/names
    public static final int DWG_LOGLEVEL_TRACE = 3;  // eg for each field value parsed
    public static final int DWG_LOGLEVEL_HANDLE = 4; // print all referenced objects (handles)
    public static final int DWG_LOGLEVEL_INSANE = 5; // print all vector data (string content)
}
