import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

enum DWG_ERROR {
    DWG_NOERR(0),
    // Sorted by severity
    DWG_ERR_WRONGCRC(1),
    DWG_ERR_NOTYETSUPPORTED(1 << 1),   // 2
    DWG_ERR_UNHANDLEDCLASS(1 << 2),    // 4
    DWG_ERR_INVALIDTYPE(1 << 3),       // 8
    DWG_ERR_INVALIDHANDLE(1 << 4),     // 16
    DWG_ERR_INVALIDEED(1 << 5),        // 32
    DWG_ERR_VALUEOUTOFBOUNDS(1 << 6),  // 64
    // Critical errors
    DWG_ERR_CLASSESNOTFOUND(1 << 7),   // 128
    DWG_ERR_SECTIONNOTFOUND(1 << 8),   // 256
    DWG_ERR_PAGENOTFOUND(1 << 9),      // 512
    DWG_ERR_INTERNALERROR(1 << 10),    // 1024
    DWG_ERR_INVALIDDWG(1 << 11),       // 2048
    DWG_ERR_IOERROR(1 << 12),          // 4096
    DWG_ERR_OUTOFMEM(1 << 13);         // 8192

    public static final int DWG_ERR_CRITICAL = DWG_ERR_CLASSESNOTFOUND.value;

    private final int value;

    DWG_ERROR(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

enum DWG_OBJECT_TYPE{
    DWG_TYPE_UNUSED(0x00),
    DWG_TYPE_TEXT(0x01),
    DWG_TYPE_ATTRIB(0x02),
    DWG_TYPE_ATTDEF(0x03),
    DWG_TYPE_BLOCK(0x04),
    DWG_TYPE_ENDBLK(0x05),
    DWG_TYPE_SEQEND(0x06),
    DWG_TYPE_INSERT(0x07),
    DWG_TYPE_MINSERT(0x08),
    // DWG_TYPE_TRACE_old = 0x09, /* old TRACE r10-r11 only */
    DWG_TYPE_VERTEX_2D(0x0a),
    DWG_TYPE_VERTEX_3D(0x0b),
    DWG_TYPE_VERTEX_MESH(0x0c),
    DWG_TYPE_VERTEX_PFACE(0x0d),
    DWG_TYPE_VERTEX_PFACE_FACE(0x0e),
    DWG_TYPE_POLYLINE_2D(0x0f),
    DWG_TYPE_POLYLINE_3D(0x10),
    DWG_TYPE_ARC(0x11),
    DWG_TYPE_CIRCLE(0x12),
    DWG_TYPE_LINE(0x13),
    DWG_TYPE_DIMENSION_ORDINATE(0x14),
    DWG_TYPE_DIMENSION_LINEAR(0x15),
    DWG_TYPE_DIMENSION_ALIGNED(0x16),
    DWG_TYPE_DIMENSION_ANG3PT(0x17),
    DWG_TYPE_DIMENSION_ANG2LN(0x18),
    DWG_TYPE_DIMENSION_RADIUS(0x19),
    DWG_TYPE_DIMENSION_DIAMETER(0x1A),
    DWG_TYPE_POINT(0x1b),
    DWG_TYPE__3DFACE(0x1c),
    DWG_TYPE_POLYLINE_PFACE(0x1d),
    DWG_TYPE_POLYLINE_MESH(0x1e),
    DWG_TYPE_SOLID(0x1f),
    DWG_TYPE_TRACE(0x20),
    DWG_TYPE_SHAPE(0x21),
    DWG_TYPE_VIEWPORT(0x22),
    DWG_TYPE_ELLIPSE(0x23),
    DWG_TYPE_SPLINE(0x24),
    DWG_TYPE_REGION(0x25),
    DWG_TYPE__3DSOLID(0x26),
    DWG_TYPE_BODY(0x27),
    DWG_TYPE_RAY(0x28),
    DWG_TYPE_XLINE(0x29),
    DWG_TYPE_DICTIONARY(0x2a),
    DWG_TYPE_OLEFRAME(0x2b),
    DWG_TYPE_MTEXT(0x2c),
    DWG_TYPE_LEADER(0x2d),
    DWG_TYPE_TOLERANCE(0x2e),
    DWG_TYPE_MLINE(0x2f),
    DWG_TYPE_BLOCK_CONTROL(0x30),
    DWG_TYPE_BLOCK_HEADER(0x31),
    DWG_TYPE_LAYER_CONTROL(0x32),
    DWG_TYPE_LAYER(0x33),
    DWG_TYPE_STYLE_CONTROL(0x34), /* 52 SHAPEFILE_CONTROL */
    DWG_TYPE_STYLE(0x35),
    /* DWG_TYPE_<UNKNOWN> = 0x36, */
    /* DWG_TYPE_<UNKNOWN> = 0x37, */
    DWG_TYPE_LTYPE_CONTROL(0x38),
    DWG_TYPE_LTYPE(0x39),
    /* DWG_TYPE_<UNKNOWN> = 0x3a, */
    /* DWG_TYPE_<UNKNOWN> = 0x3b, */
    DWG_TYPE_VIEW_CONTROL(0x3c),
    DWG_TYPE_VIEW(0x3d),
    DWG_TYPE_UCS_CONTROL(0x3e),
    DWG_TYPE_UCS(0x3f),
    DWG_TYPE_VPORT_CONTROL(0x40),
    DWG_TYPE_VPORT(0x41),
    DWG_TYPE_APPID_CONTROL(0x42),
    DWG_TYPE_APPID(0x43),
    DWG_TYPE_DIMSTYLE_CONTROL(0x44),
    DWG_TYPE_DIMSTYLE(0x45),
    DWG_TYPE_VX_CONTROL(0x46),
    DWG_TYPE_VX_TABLE_RECORD(0x47),
    DWG_TYPE_GROUP(0x48),
    DWG_TYPE_MLINESTYLE(0x49),
    DWG_TYPE_OLE2FRAME(0x4a),
    DWG_TYPE_DUMMY(0x4b),
    DWG_TYPE_LONG_TRANSACTION(0x4c),
    DWG_TYPE_LWPOLYLINE(0x4d), /* ?? */
    DWG_TYPE_HATCH(0x4e),
    DWG_TYPE_XRECORD(0x4f),
    DWG_TYPE_PLACEHOLDER(0x50),
    DWG_TYPE_VBA_PROJECT(0x51),
    DWG_TYPE_LAYOUT(0x52),

    DWG_TYPE_PROXY_ENTITY(0x1f2), /* 498 */
    DWG_TYPE_PROXY_OBJECT(0x1f3), /* 499 */

    /* non-fixed types > 500. not stored as type, but as fixedtype */
    DWG_TYPE_ACDSRECORD(500),

    DWG_TYPE_ACDSSCHEMA(501),
    DWG_TYPE_ACMECOMMANDHISTORY(501),
    DWG_TYPE_ACMESCOPE(501),
    DWG_TYPE_ACMESTATEMGR(501),
    DWG_TYPE_ACSH_BOOLEAN_CLASS(501),
    DWG_TYPE_ACSH_BOX_CLASS(501),
    DWG_TYPE_ACSH_BREP_CLASS(501),
    DWG_TYPE_ACSH_CHAMFER_CLASS(501),
    DWG_TYPE_ACSH_CONE_CLASS(501),
    DWG_TYPE_ACSH_CYLINDER_CLASS(501),
    DWG_TYPE_ACSH_EXTRUSION_CLASS(501),
    DWG_TYPE_ACSH_FILLET_CLASS(501),
    DWG_TYPE_ACSH_HISTORY_CLASS(501),
    DWG_TYPE_ACSH_LOFT_CLASS(501),
    DWG_TYPE_ACSH_PYRAMID_CLASS(501),
    DWG_TYPE_ACSH_REVOLVE_CLASS(501),
    DWG_TYPE_ACSH_SPHERE_CLASS(501),
    DWG_TYPE_ACSH_SWEEP_CLASS(501),
    DWG_TYPE_ACSH_TORUS_CLASS(501),
    DWG_TYPE_ACSH_WEDGE_CLASS(501),
    DWG_TYPE_ALDIMOBJECTCONTEXTDATA(501),
    DWG_TYPE_ALIGNMENTPARAMETERENTITY(501),
    DWG_TYPE_ANGDIMOBJECTCONTEXTDATA(501),
    DWG_TYPE_ANNOTSCALEOBJECTCONTEXTDATA(501),
    DWG_TYPE_ARCALIGNEDTEXT(501),
    DWG_TYPE_ARC_DIMENSION(501),
    DWG_TYPE_ASSOC2DCONSTRAINTGROUP(501),
    DWG_TYPE_ASSOC3POINTANGULARDIMACTIONBODY(501),
    DWG_TYPE_ASSOCACTION(501),
    DWG_TYPE_ASSOCACTIONPARAM(501),
    DWG_TYPE_ASSOCALIGNEDDIMACTIONBODY(501),
    DWG_TYPE_ASSOCARRAYACTIONBODY(501),
    DWG_TYPE_ASSOCARRAYMODIFYACTIONBODY(501),
    DWG_TYPE_ASSOCARRAYMODIFYPARAMETERS(501),
    DWG_TYPE_ASSOCARRAYPATHPARAMETERS(501),
    DWG_TYPE_ASSOCARRAYPOLARPARAMETERS(501),
    DWG_TYPE_ASSOCARRAYRECTANGULARPARAMETERS(501),
    DWG_TYPE_ASSOCASMBODYACTIONPARAM(501),
    DWG_TYPE_ASSOCBLENDSURFACEACTIONBODY(501),
    DWG_TYPE_ASSOCCOMPOUNDACTIONPARAM(501),
    DWG_TYPE_ASSOCDEPENDENCY(501),
    DWG_TYPE_ASSOCDIMDEPENDENCYBODY(501),
    DWG_TYPE_ASSOCEDGEACTIONPARAM(501),
    DWG_TYPE_ASSOCEDGECHAMFERACTIONBODY(501),
    DWG_TYPE_ASSOCEDGEFILLETACTIONBODY(501),
    DWG_TYPE_ASSOCEXTENDSURFACEACTIONBODY(501),
    DWG_TYPE_ASSOCEXTRUDEDSURFACEACTIONBODY(501),
    DWG_TYPE_ASSOCFACEACTIONPARAM(501),
    DWG_TYPE_ASSOCFILLETSURFACEACTIONBODY(501),
    DWG_TYPE_ASSOCGEOMDEPENDENCY(501),
    DWG_TYPE_ASSOCLOFTEDSURFACEACTIONBODY(501),
    DWG_TYPE_ASSOCMLEADERACTIONBODY(501),
    DWG_TYPE_ASSOCNETWORK(501),
    DWG_TYPE_ASSOCNETWORKSURFACEACTIONBODY(501),
    DWG_TYPE_ASSOCOBJECTACTIONPARAM(501),
    DWG_TYPE_ASSOCOFFSETSURFACEACTIONBODY(501),
    DWG_TYPE_ASSOCORDINATEDIMACTIONBODY(501),
    DWG_TYPE_ASSOCOSNAPPOINTREFACTIONPARAM(501),
    DWG_TYPE_ASSOCPATCHSURFACEACTIONBODY(501),
    DWG_TYPE_ASSOCPATHACTIONPARAM(501),
    DWG_TYPE_ASSOCPERSSUBENTMANAGER(501),
    DWG_TYPE_ASSOCPLANESURFACEACTIONBODY(501),
    DWG_TYPE_ASSOCPOINTREFACTIONPARAM(501),
    DWG_TYPE_ASSOCRESTOREENTITYSTATEACTIONBODY(501),
    DWG_TYPE_ASSOCREVOLVEDSURFACEACTIONBODY(501),
    DWG_TYPE_ASSOCROTATEDDIMACTIONBODY(501),
    DWG_TYPE_ASSOCSWEPTSURFACEACTIONBODY(501),
    DWG_TYPE_ASSOCTRIMSURFACEACTIONBODY(501),
    DWG_TYPE_ASSOCVALUEDEPENDENCY(501),
    DWG_TYPE_ASSOCVARIABLE(501),
    DWG_TYPE_ASSOCVERTEXACTIONPARAM(501),
    DWG_TYPE_BASEPOINTPARAMETERENTITY(501),
    DWG_TYPE_BLKREFOBJECTCONTEXTDATA(501),
    DWG_TYPE_BLOCKALIGNEDCONSTRAINTPARAMETER(501),
    DWG_TYPE_BLOCKALIGNMENTGRIP(501),
    DWG_TYPE_BLOCKALIGNMENTPARAMETER(501),
    DWG_TYPE_BLOCKANGULARCONSTRAINTPARAMETER(501),
    DWG_TYPE_BLOCKARRAYACTION(501),
    DWG_TYPE_BLOCKBASEPOINTPARAMETER(501),
    DWG_TYPE_BLOCKDIAMETRICCONSTRAINTPARAMETER(501),
    DWG_TYPE_BLOCKFLIPACTION(501),
    DWG_TYPE_BLOCKFLIPGRIP(501),
    DWG_TYPE_BLOCKFLIPPARAMETER(501),
    DWG_TYPE_BLOCKGRIPLOCATIONCOMPONENT(501),
    DWG_TYPE_BLOCKHORIZONTALCONSTRAINTPARAMETER(501),
    DWG_TYPE_BLOCKLINEARCONSTRAINTPARAMETER(501),
    DWG_TYPE_BLOCKLINEARGRIP(501),
    DWG_TYPE_BLOCKLINEARPARAMETER(501),
    DWG_TYPE_BLOCKLOOKUPACTION(501),
    DWG_TYPE_BLOCKLOOKUPGRIP(501),
    DWG_TYPE_BLOCKLOOKUPPARAMETER(501),
    DWG_TYPE_BLOCKMOVEACTION(501),
    DWG_TYPE_BLOCKPARAMDEPENDENCYBODY(501),
    DWG_TYPE_BLOCKPOINTPARAMETER(501),
    DWG_TYPE_BLOCKPOLARGRIP(501),
    DWG_TYPE_BLOCKPOLARPARAMETER(501),
    DWG_TYPE_BLOCKPOLARSTRETCHACTION(501),
    DWG_TYPE_BLOCKPROPERTIESTABLE(501),
    DWG_TYPE_BLOCKPROPERTIESTABLEGRIP(501),
    DWG_TYPE_BLOCKRADIALCONSTRAINTPARAMETER(501),
    DWG_TYPE_BLOCKREPRESENTATION(501),
    DWG_TYPE_BLOCKROTATEACTION(501),
    DWG_TYPE_BLOCKROTATIONGRIP(501),
    DWG_TYPE_BLOCKROTATIONPARAMETER(501),
    DWG_TYPE_BLOCKSCALEACTION(501),
    DWG_TYPE_BLOCKSTRETCHACTION(501),
    DWG_TYPE_BLOCKUSERPARAMETER(501),
    DWG_TYPE_BLOCKVERTICALCONSTRAINTPARAMETER(501),
    DWG_TYPE_BLOCKVISIBILITYGRIP(501),
    DWG_TYPE_BLOCKVISIBILITYPARAMETER(501),
    DWG_TYPE_BLOCKXYGRIP(501),
    DWG_TYPE_BLOCKXYPARAMETER(501),
    DWG_TYPE_CAMERA(501),
    DWG_TYPE_CELLSTYLEMAP(501),
    DWG_TYPE_CONTEXTDATAMANAGER(501),
    DWG_TYPE_CSACDOCUMENTOPTIONS(501),
    DWG_TYPE_CURVEPATH(501),
    DWG_TYPE_DATALINK(501),
    DWG_TYPE_DATATABLE(501),
    DWG_TYPE_DBCOLOR(501),
    DWG_TYPE_DETAILVIEWSTYLE(501),
    DWG_TYPE_DGNDEFINITION(501),
    DWG_TYPE_DGNUNDERLAY(501),
    DWG_TYPE_DICTIONARYVAR(501),
    DWG_TYPE_DICTIONARYWDFLT(501),
    DWG_TYPE_DIMASSOC(501),
    DWG_TYPE_DMDIMOBJECTCONTEXTDATA(501),
    DWG_TYPE_DWFDEFINITION(501),
    DWG_TYPE_DWFUNDERLAY(501),
    DWG_TYPE_DYNAMICBLOCKPROXYNODE(501),
    DWG_TYPE_DYNAMICBLOCKPURGEPREVENTER(501),
    DWG_TYPE_EVALUATION_GRAPH(501),
    DWG_TYPE_EXTRUDEDSURFACE(501),
    DWG_TYPE_FCFOBJECTCONTEXTDATA(501),
    DWG_TYPE_FIELD(501),
    DWG_TYPE_FIELDLIST(501),
    DWG_TYPE_FLIPPARAMETERENTITY(501),
    DWG_TYPE_GEODATA(501),
    DWG_TYPE_GEOMAPIMAGE(501),
    DWG_TYPE_GEOPOSITIONMARKER(501),
    DWG_TYPE_GRADIENT_BACKGROUND(501),
    DWG_TYPE_GROUND_PLANE_BACKGROUND(501),
    DWG_TYPE_HELIX(501),
    DWG_TYPE_IBL_BACKGROUND(501),
    DWG_TYPE_IDBUFFER(501),
    DWG_TYPE_IMAGE(501),
    DWG_TYPE_IMAGEDEF(501),
    DWG_TYPE_IMAGEDEF_REACTOR(501),
    DWG_TYPE_IMAGE_BACKGROUND(501),
    DWG_TYPE_INDEX(501),
    DWG_TYPE_LARGE_RADIAL_DIMENSION(501),
    DWG_TYPE_LAYERFILTER(501),
    DWG_TYPE_LAYER_INDEX(501),
    DWG_TYPE_LAYOUTPRINTCONFIG(501),
    DWG_TYPE_LEADEROBJECTCONTEXTDATA(501),
    DWG_TYPE_LIGHT(501),
    DWG_TYPE_LIGHTLIST(501),
    DWG_TYPE_LINEARPARAMETERENTITY(501),
    DWG_TYPE_LOFTEDSURFACE(501),
    DWG_TYPE_MATERIAL(501),
    DWG_TYPE_MENTALRAYRENDERSETTINGS(501),
    DWG_TYPE_MESH(501),
    DWG_TYPE_MLEADEROBJECTCONTEXTDATA(501),
    DWG_TYPE_MLEADERSTYLE(501),
    DWG_TYPE_MOTIONPATH(501),
    DWG_TYPE_MPOLYGON(501),
    DWG_TYPE_MTEXTATTRIBUTEOBJECTCONTEXTDATA(501),
    DWG_TYPE_MTEXTOBJECTCONTEXTDATA(501),
    DWG_TYPE_MULTILEADER(501),
    DWG_TYPE_NAVISWORKSMODEL(501),
    DWG_TYPE_NAVISWORKSMODELDEF(501),
    DWG_TYPE_NPOCOLLECTION(501),
    DWG_TYPE_NURBSURFACE(501),
    DWG_TYPE_OBJECT_PTR(501),
    DWG_TYPE_ORDDIMOBJECTCONTEXTDATA(501),
    DWG_TYPE_PARTIAL_VIEWING_INDEX(501),
    //DWG_TYPE_PARTIAL_VIEWING_FILTER,
    DWG_TYPE_PDFDEFINITION(501),
    DWG_TYPE_PDFUNDERLAY(501),
    DWG_TYPE_PERSUBENTMGR(501),
    DWG_TYPE_PLANESURFACE(501),
    DWG_TYPE_PLOTSETTINGS(501),
    DWG_TYPE_POINTCLOUD(501),
    DWG_TYPE_POINTCLOUDCOLORMAP(501),
    DWG_TYPE_POINTCLOUDDEF(501),
    DWG_TYPE_POINTCLOUDDEFEX(501),
    DWG_TYPE_POINTCLOUDDEF_REACTOR(501),
    DWG_TYPE_POINTCLOUDDEF_REACTOR_EX(501),
    DWG_TYPE_POINTCLOUDEX(501),
    DWG_TYPE_POINTPARAMETERENTITY(501),
    DWG_TYPE_POINTPATH(501),
    DWG_TYPE_POLARGRIPENTITY(501),
    DWG_TYPE_RADIMLGOBJECTCONTEXTDATA(501),
    DWG_TYPE_RADIMOBJECTCONTEXTDATA(501),
    DWG_TYPE_RAPIDRTRENDERSETTINGS(501),
    DWG_TYPE_RASTERVARIABLES(501),
    DWG_TYPE_RENDERENTRY(501),
    DWG_TYPE_RENDERENVIRONMENT(501),
    DWG_TYPE_RENDERGLOBAL(501),
    DWG_TYPE_RENDERSETTINGS(501),
    DWG_TYPE_REVOLVEDSURFACE(501),
    DWG_TYPE_ROTATIONPARAMETERENTITY(501),
    DWG_TYPE_RTEXT(501),
    DWG_TYPE_SCALE(501),
    DWG_TYPE_SECTIONOBJECT(501),
    DWG_TYPE_SECTIONVIEWSTYLE(501),
    DWG_TYPE_SECTION_MANAGER(501),
    DWG_TYPE_SECTION_SETTINGS(501),
    DWG_TYPE_SKYLIGHT_BACKGROUND(501),
    DWG_TYPE_SOLID_BACKGROUND(501),
    DWG_TYPE_SORTENTSTABLE(501),
    DWG_TYPE_SPATIAL_FILTER(501),
    DWG_TYPE_SPATIAL_INDEX(501),
    DWG_TYPE_SUN(501),
    DWG_TYPE_SUNSTUDY(501),
    DWG_TYPE_SWEPTSURFACE(501),
    DWG_TYPE_TABLE(501),
    DWG_TYPE_TABLECONTENT(501),
    DWG_TYPE_TABLEGEOMETRY(501),
    DWG_TYPE_TABLESTYLE(501),
    DWG_TYPE_TEXTOBJECTCONTEXTDATA(501),
    DWG_TYPE_TVDEVICEPROPERTIES(501),
    DWG_TYPE_VISIBILITYGRIPENTITY(501),
    DWG_TYPE_VISIBILITYPARAMETERENTITY(501),
    DWG_TYPE_VISUALSTYLE(501),
    DWG_TYPE_WIPEOUT(501),
    DWG_TYPE_WIPEOUTVARIABLES(501),
    DWG_TYPE_XREFPANELOBJECT(501),
    DWG_TYPE_XYPARAMETERENTITY(501),
    DWG_TYPE_BREAKDATA(501),
    DWG_TYPE_BREAKPOINTREF(501),
    DWG_TYPE_FLIPGRIPENTITY(501),
    DWG_TYPE_LINEARGRIPENTITY(501),
    DWG_TYPE_ROTATIONGRIPENTITY(501),
    DWG_TYPE_XYGRIPENTITY(501),

    /* preR13 entities */
    DWG_TYPE__3DLINE(501),
    DWG_TYPE_REPEAT(501),
    DWG_TYPE_ENDREP(501),
    DWG_TYPE_JUMP(501),
    /* pre2.0 entities */
    DWG_TYPE_LOAD(501),
// after 1.0 add new types here for binary compat
    DWG_TYPE_FREED(0xfffd),
    DWG_TYPE_UNKNOWN_ENT(0xfffe),
    DWG_TYPE_UNKNOWN_OBJ(0xffff);
    public int value;
    DWG_OBJECT_TYPE(int value)
    {
        this.value = value;
    }

}

enum DWG_OBJECT_SUPERTYPE {
    DWG_SUPERTYPE_ENTITY, DWG_SUPERTYPE_OBJECT
}

enum DWG_SECTION_TYPE{
    SECTION_UNKNOWN (0),                  /* FILEHEADER, the very first 160 byte? */
    SECTION_HEADER (1),                   /* AcDb:Header */
    SECTION_AUXHEADER (2),                /* AcDb:AuxHeader */
    SECTION_CLASSES (3),                  /* AcDb:Classes */
    SECTION_HANDLES(4),                  /* AcDb:Handles */
    SECTION_TEMPLATE(5),                 /* AcDb:Template */
    SECTION_OBJFREESPACE( 6),             /* AcDb:ObjFreeSpace */
    SECTION_OBJECTS (7),                  /* AcDb:AcDbObjects */
    SECTION_REVHISTORY ( 8),               /* AcDb:RevHistory */
    SECTION_SUMMARYINFO ( 9),              /* AcDb:SummaryInfo */
    SECTION_PREVIEW (10),                 /* AcDb:Preview */
    SECTION_APPINFO (10),                 /* AcDb:AppInfo */
    SECTION_APPINFOHISTORY (12),          /* AcDb:AppInfoHistory */
    SECTION_FILEDEPLIST (13),             /* AcDb:FileDepList */
    SECTION_SECURITY(14),                     /* AcDb:Security, if stored with a password */
    SECTION_VBAPROJECT(15),                   /* AcDb:VBAProject */
    SECTION_SIGNATURE(16),                    /* AcDb:Signature */
    SECTION_ACDS(17),                         /* AcDb:AcDsPrototype_1b = 12 (ACIS datastorage) */
    SECTION_INFO(18),                         /* also called Data Section, or Section Page Map (ODA) */
    SECTION_SYSTEM_MAP(19);

    public int value;
    DWG_SECTION_TYPE(int value)
    {
        this.value = value;
    }
}

public class dwg {
    public static int loglevel;
    public static final int DWG_OPTS_LOGLEVEL = 0x0F;
    public static final int DWG_OPTS_MINIMAL = 0x10;
    public static final int DWG_OPTS_DXFB = 0x20;
    public static final int DWG_OPTS_JSONFIRST = 0x20;
    public static final int DWG_OPTS_INDXF = 0x40;
    public static final int DWG_OPTS_INJSON = 0x80;
    public static final int DWG_OPTS_IN = DWG_OPTS_INDXF | DWG_OPTS_INJSON;
    public static int dwg_read_file(String filename, Dwg_Data objDwgData) {
        int error = 0;
        RandomAccessFile fp = null;
        Bit_Chain bit_chain = new Bit_Chain();

        try {
            if ("-".equals(filename)) {
                System.out.println("Enter input for stdin (file will be created):");
                String input = new Scanner(System.in).nextLine();
                File file = new File(input);
                if (!file.exists()) {
                    file.createNewFile();
                }
                fp = new RandomAccessFile(file, "rw"); // Read-write mode for created file
            } else {
                File file = new File(filename);
                if (!file.exists()) {
                    System.err.printf("File not found: %s%n", filename);
                    return 1; // Indicates an error
                }
                fp = new RandomAccessFile(file, "r"); // Open in read-only mode
            }

            bit_chain.size = fp.length();
            error = dat_read_file(bit_chain, filename);
            if(error >= DWG_ERROR.DWG_ERR_CRITICAL)
            {
                return error;
            }

            fp.close();

            /* Decode the dwg structure */
            error = decode.dwg_decode(bit_chain,objDwgData);
            if(error >= DWG_ERROR.DWG_ERR_CRITICAL)
            {
                bit_chain.chain = null;
                bit_chain.size = 0;
                return  error;
            }

        } catch (Exception e) {
            System.err.printf("Error handling file: %s%n", filename);
            e.printStackTrace();
        }
        bit_chain.chain = null;
        bit_chain.size = 0;
        return  error;
    }

    private static int dat_read_file(Bit_Chain dat, String filename) throws IOException {
        byte[] fileBytes = Files.readAllBytes(Path.of(filename));
        int[] arr = new int[fileBytes.length];

        for (int i = 0; i < fileBytes.length; i++) {
            arr[i] = fileBytes[i] & 0xFF;
        }
        dat.chain = new char[arr.length];
        for (int i = 0; i < arr.length; i++) {
            dat.chain[i] = (char)(arr[i] & 0xFF);
        }
        if (fileBytes.length <= 0) {
            return 1;
        }
        return 0;
    }
}

class Dwg_Class{
    public int number;
    public int proxyflag;
    public char[] appname;
    public char[] cppname;
    public char[] dxfname;
    public char dxfname_u;
    public char is_zombie;
    public int item_class_id;
    public int num_instances;
    public int dwg_version;
    public int maint_version;
    public int unknown_1;
    public int unknown_2;
}

class Dwg_Eed{
    public int size;
    public Dwg_Handle handle;
    public Dwg_Eed_Data[] data;
    public String raw;
}

class Dwg_Handle{
    public char code;
    public char size;
    public long value;
    public char is_global;
}

class Dwg_Eed_Data
{
    public char code;

    class Eed_data_t{
        class Eed_0 {
            public int length;
            public short codepage;
            public short is_tu;
            public char[] string = new char[1];
        }
        Eed_0 eed_0 = new Eed_0();

        class Eed_0_r2007
        {
            public int length;
            public short codepage;
            public short is_tu;
            public char[] string = new char[1];
        }
        Eed_0_r2007 eed_0_r2007 = new Eed_0_r2007();
    }
    Eed_data_t u = new Eed_data_t();
}

class Dwg_Object_Ref{
    public Dwg_Object obj;
    public Dwg_Handle handleref;
    public long absolute_ref;
    public short r11_idx;
}

class Dwg_Color
{
    public short index;
    public int flag;
    public int raw;
    public long rgb;
    public int method;

    public String name;
    public String book_name;
    public Dwg_Object_Ref handle;
    public int alpha_raw;
    public char alpha_type;
    public char alpha;
}
class Dwg_Bitcode_3BD {
    public double x;
    public double y;
    public double z;
    Dwg_Bitcode_3BD(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    Dwg_Bitcode_3BD()
    {

    }
}
class Dwg_Entity_POINT{
    public double x;
    public double y;
    public double z;
    public double thickness;
    public Dwg_Bitcode_3BD extrusion;
    public double x_ang;

}
class Dwg_Object_Entity
{
    public int objid;

    class Tio{
        Dwg_Entity_POINT POINT;
    }
    Tio tio;
    public Dwg_Data dwg;
    public int num_eed;
    public Dwg_Eed eed;

    public char preview_exists;
    public char preview_is_proxy;
    public long preview_size;

    public String preview;
    public char entmode;

    public int num_reactor;
    public char is_xdic_missing;
    public char isbylayerlt;
    public char nolinks;
    public char has_ds_data;
    public Dwg_Color color;
    public double ltype_scale;
    public char ltype_flags;
    public char plotstyle_flags;
    public char material_flags;
    public char shadow_flags;

    public char has_full_visualstyle;
    public char has_face_visualstyle;
    public char has_edge_visualstyle;
    public int linewt;

    public char flag_r11;
    public int opts_r11;
    public int extra_r11;
    public char color_r11;
    public double elevation_r11;
    public double thickness_r11;
    public Dwg_Object_Ref viewport;

    /* Common Entity Handle Data */
    public int __iterator;
    public Dwg_Object_Ref ownerhandle;
    public Dwg_Object_Ref[] reactors;
    public Dwg_Object_Ref xdicobjhandle;
    public Dwg_Object_Ref prev_entity;
    public Dwg_Object_Ref next_entity;
    public Dwg_Object_Ref layer;
    public Dwg_Object_Ref ltype;
    public Dwg_Object_Ref material;
    public Dwg_Object_Ref shadow;
    public Dwg_Object_Ref plotstyle;
    public Dwg_Object_Ref full_visualstyle;
    public Dwg_Object_Ref face_visualstyle;
    public Dwg_Object_Ref edge_visualstyle;
}
class Dwg_Object_Object
{
    public int objid;
    class Tio{

    }
    public Dwg_Data dwg;
    public int num_eed;
    public Dwg_Eed[] eed;

    /* Common Object Data */
    public Dwg_Object_Ref ownerhandle;
    public int num_reactors;
    public Dwg_Object_Ref[] reactors;
    public Dwg_Object_Ref xdicobjhandle;
    public char is_xdic_missing;
    public char has_ds_data;

    /*unsigned int num_handles;*/
    Dwg_Handle[] handleref;
}
class Dwg_Object{
    public int size;
    public long address;
    public int type;
    public int index;
    public DWG_OBJECT_TYPE fixedtype;
    char[] name;
    char[] dxfname;
    public DWG_OBJECT_SUPERTYPE supertype;
    class Tio{
        Dwg_Object_Entity entity;
        Dwg_Object_Object object;
    }
    public Tio tio;
    public Dwg_Handle handle;
    public Dwg_Data parent;
    public Dwg_Class klass;

    public int bitsize;
    public long bitsize_pos;
    public long hdlpos;
    public char was_bitsize_set;
    public char stringstream_size;
    public long handlestream_size;
    public long comman_size;

    public int num_unknown_bits;
    public String unknown_bits;
    public int num_unknown_rest;
    public String unknown_rest;
}
class dwg_inthash{
    public _hashbucket[] array;
    public long size;
    public int elems;
}
class _hashbucket{
    public long key = 0;
    public long value = 0;
}
class Dwg_Section_InfoHdr
{
    public int num_desc;
    public int compressed;
    public int max_size;
    public int encrypted;
    public int num_desc2;
}

class Dwg_Section{
    public int number;
    public int size;
    public long address;
    public int objid_r11;

    public int parent;
    public int left;
    public int right;
    public int x00;
    public DWG_SECTION_TYPE type;

    char[] name = new char[64];
    public int section_type;
    public int decomp_data_size;
    public int comp_data_size;
    public int compression_type;
    public int checksum;
    public int flags_r11;
}

class Dwg_Section_Info
{
    public long size;
    public int num_sections;
    public int max_decomp_size;
    public int unknown;
    public int compressed;
    public int type;
    public int encrypted;
    public char[] name = new char[64];
    public DWG_SECTION_TYPE fixedtype;
    public Dwg_Section[] sections;
}
class Dwg_Data {
    public Dwg_Header header = new Dwg_Header();
    public int num_classes;        /*!< number of classes */
    public Dwg_Class dwg_class;         /*!< array of classes */
    public int num_objects;        /*!< number of objects */
    public int num_alloced_objects;/*!< room for objects */
    public Dwg_Object[] object;           /*!< list of all objects and entities */
    public int num_entities;       /*!< number of entities in object */
    public int num_object_refs;    /*!< number of object_ref's (resolved handles) */
    public int cur_index;          /*!< how many we have written currently */
    Dwg_Object_Ref[] object_ref;   /*!< array of most handles */
    public dwg_inthash object_map;   /*!< map of all handles */
    int dirty_refs;
    public int opts;
    public Dwg_Header_Variables header_vars;
    public Dwg_Chain thumbnail = new Dwg_Chain();

    public Dwg_R2004_Header r2004_header;
    public Dwg_R2007_Header r2007_file_header;

    public Dwg_Object[] mspace_block;
    public Dwg_Object[] pspace_block;

    public Dwg_Object_BLOCK_CONTROL block_control;
    public Dwg_AuxHeader auxheader;
    public Dwg_SecondHeader secondheader;
    public Dwg_SummaryInfo summaryinfo;;
    public Dwg_AppInfo appinfo;
    public Dwg_FileDepList filedeplist;
    public Dwg_Security security;
    public Dwg_VBAProject vbaproject;
    public Dwg_AppInfoHistory appinfohistory;
    public Dwg_RevHistory revhistory;
    public Dwg_ObjFreeSpace objfreespace;
    public Dwg_Template Template;
    public Dwg_AcDs acds;

    public int layout_tupe;
    public int num_acid_sab_hdl;
    public Dwg_Object_Ref[] acis_sab_hdl;
    public long next_hdl;

    public long prev_entity_index;

    public Dwg_Object_Ref[] object_ordered_ref;
    public int num_object_ordered_refs;

}
class Dwg_Header{
    public DWG_VERSION_TYPE version;
    public DWG_VERSION_TYPE from_version;

    public char is_maint;
    public  char zero_one_or_three;
    public int numentity_sections;
    public int numheader_vars;
    public int[] unknown_s;
    public long thumbnail_address;
    public char dwg_version;
    public char maint_version;
    public int entities_start;
    public int entities_end;
    public int blocks_start;
    public int blocks_size;
    public int extras_start;
    public int extras_size;
    public int codepage;
    public char unknown_0;
    public char app_dwg_version;
    public char app_maint_version;
    public int security_type;
    public int r1_1c_address;
    public int summaryinfo_address;
    public int vbaproj_address;
    public int r2004_header_address;
    public int num_sections;
    public long sections;
    public Dwg_Section_InfoHdr section_infohdr = new Dwg_Section_InfoHdr();
    public Dwg_Section_Info[] section_info;
}

class Dwg_Header_Variables
{

}

class Dwg_Chain
{
    public String chain;
    public long size;
    public long _byte;
    public char bit;
}

class Dwg_R2004_Header
{

}

class Dwg_R2007_Header
{

}

class Dwg_Object_BLOCK_CONTROL
{

}

class Dwg_AuxHeader
{

}

class Dwg_SecondHeader
{

}

class Dwg_SummaryInfo
{

}

class Dwg_AppInfo
{

}

class Dwg_FileDepList
{

}

class Dwg_Security
{

}

class Dwg_VBAProject
{

}

class Dwg_AppInfoHistory
{

}

class Dwg_RevHistory
{

}

class Dwg_ObjFreeSpace
{

}

class Dwg_Template
{

}

class Dwg_AcDs
{

}