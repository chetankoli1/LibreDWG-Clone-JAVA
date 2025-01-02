import java.io.*;
import java.math.BigInteger;
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

    public final int value;

    DWG_ERROR(int value) {
        this.value = value;
    }
}

enum DWG_OBJECT_TYPE {
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
    DWG_TYPE_ACMECOMMANDHISTORY(502),
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
    DWG_OBJECT_TYPE(){}

    static DWG_OBJECT_TYPE fromValue(int value) {
        for (DWG_OBJECT_TYPE type : DWG_OBJECT_TYPE.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
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
enum DWG_SECTION_TYPE_R13
{
    SECTION_HEADER_R13 (0),
    SECTION_CLASSES_R13(1),
    SECTION_HANDLES_R13 (2),
    SECTION_OBJFREESPACE_R13(3), /* including the 2ndheader */
    SECTION_TEMPLATE_R13 (4),
    SECTION_AUXHEADER_R2000(5),
    SECTION_THUMBNAIL_R13(6);
    public int value;
    DWG_SECTION_TYPE_R13(int value)
    {
        this.value = value;
    }
}

class Dwg_Bitcode_TimeBLL{
    public long days;
    public long ms;
    public double value;
}
class Dwg_Bitcode_TimeRLL{
    public long days;
    public long ms;
    public double value;
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

    static int dwg_section_init(Dwg_Data objDwgData) {
        loglevel = objDwgData.opts & DWG_OPTS_LOGLEVEL;
        if(objDwgData.header.version.ordinal() < DWG_VERSION_TYPE.R_13b1.ordinal())
        {
            if(objDwgData.header.num_sections == 0)
            {
                objDwgData.header.num_sections = 5;
            }
            if(objDwgData.header.sections == 0)
                objDwgData.header.sections = 5;

            if(objDwgData.header.numheader_vars != 0)
            {
                if(objDwgData.header.numheader_vars > 129)
                    objDwgData.header.num_sections += 2;
                if(objDwgData.header.num_sections > 158)
                    objDwgData.header.num_sections += 1;
                if(objDwgData.header.numheader_vars > 160)
                    objDwgData.header.num_sections += 2;
            }else{
                if(objDwgData.header.version.ordinal() >= DWG_VERSION_TYPE.R_10.ordinal())
                    objDwgData.header.numheader_vars = 160;
                objDwgData.header.num_sections += 3;
                if(objDwgData.header.version.ordinal() >= DWG_VERSION_TYPE.R_11.ordinal())
                    objDwgData.header.numheader_vars = 205;
                objDwgData.header.num_sections += 2;
            }
            objDwgData.header.num_sections += 1;
        }else{
            if(objDwgData.header.num_sections == 0 ||
                    objDwgData.header.version.ordinal() <= DWG_VERSION_TYPE.R_2000.ordinal())
            {
                objDwgData.header.num_sections = (objDwgData.header.version.ordinal() < DWG_VERSION_TYPE.R_13c3.ordinal())
                        ? 3 : (objDwgData.header.version.ordinal() < DWG_VERSION_TYPE.R_2000b.ordinal())
                        ? 5 : 6;
                if(objDwgData.header.num_sections == 3 && objDwgData.objfreespace.numnums != '\0')
                    objDwgData.header.num_sections = 5;
            }

            if(objDwgData.header.sections == 0 ||
                    (objDwgData.header.from_version.ordinal() > DWG_VERSION_TYPE.R_2000.ordinal()
                    && objDwgData.header.version.ordinal() <= DWG_VERSION_TYPE.R_2000.ordinal()))
                objDwgData.header.sections = objDwgData.header.num_sections;

            if(objDwgData.header.num_sections != objDwgData.header.sections)
            {
                objDwgData.header.num_sections = objDwgData.header.sections;
            }
        }
        if(objDwgData.header.num_sections < 3){
            return DWG_ERROR.DWG_ERR_INVALIDDWG.value;
        }
        else if(objDwgData.header.num_sections > 28)
        {
            return DWG_ERROR.DWG_ERR_INVALIDDWG.value;
        }

        if (objDwgData.header.section != null) {
            // Resize the array by creating a new array with a larger size and copying the old elements
            Dwg_Section[] newSectionArray = new Dwg_Section[(int)objDwgData.header.num_sections + 2];
            System.arraycopy(objDwgData.header.section, 0, newSectionArray, 0, objDwgData.header.section.length);
            objDwgData.header.section = newSectionArray;
        } else {
            // Allocate a new array if section is null
            objDwgData.header.section = new Dwg_Section[(int)objDwgData.header.num_sections + 2];
        }
        if(objDwgData.header.section == null)
        {
            return DWG_ERROR.DWG_ERR_OUTOFMEM.value;
        }
        return 0;
    }

    static int dat_read_file(Bit_Chain dat, String filename) throws IOException {
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

    static short dwg_find_color_index(long rgb) {
        Dwg_RGB_Palette pal = new Dwg_RGB_Palette();
        rgb &= 0x00ffffff;
        pal.r = (int)((rgb >> 16) & 0xFF);
        pal.g = (int)((rgb >> 8) & 0xFF);
        pal.b = (int)(rgb & 0xFF);

        for(short i = 0; i < 256; i++)
        {
            if(dwg.rgb_palettes[i] == pal)
            {
                return i;
            }
        }
        return 256;
    }
    static int dwg_class_is_entity(Dwg_Class klass) {
        return (klass != null && klass.item_class_id == 0x1f2) ? 1 : 0;
    }

    static boolean dwg_obj_is_control(Dwg_Object obj)
    {
        DWG_OBJECT_TYPE type = obj.fixedtype;
        return (obj.supertype == DWG_OBJECT_SUPERTYPE.DWG_SUPERTYPE_OBJECT) &&
                (type == DWG_OBJECT_TYPE.DWG_TYPE_BLOCK_CONTROL || type == DWG_OBJECT_TYPE.DWG_TYPE_LAYER_CONTROL
                        || type == DWG_OBJECT_TYPE.DWG_TYPE_STYLE_CONTROL
                        || type == DWG_OBJECT_TYPE.DWG_TYPE_LTYPE_CONTROL || type == DWG_OBJECT_TYPE.DWG_TYPE_VIEW_CONTROL
                        || type == DWG_OBJECT_TYPE.DWG_TYPE_UCS_CONTROL || type == DWG_OBJECT_TYPE.DWG_TYPE_VPORT_CONTROL
                        || type == DWG_OBJECT_TYPE.DWG_TYPE_APPID_CONTROL
                        || type == DWG_OBJECT_TYPE.DWG_TYPE_DIMSTYLE_CONTROL
                        || type == DWG_OBJECT_TYPE.DWG_TYPE_VX_CONTROL);
    }
    static Dwg_RGB_Palette[] rgb_palettes = {
         new Dwg_RGB_Palette(0x00, 0x00, 0x00),//0
         new Dwg_RGB_Palette(0xFF, 0x00, 0x00),//1
         new Dwg_RGB_Palette(0xFF, 0xFF, 0x00),
         new Dwg_RGB_Palette(0x00, 0xFF, 0x00),
         new Dwg_RGB_Palette( 0x00, 0xFF, 0xFF),
         new Dwg_RGB_Palette( 0x00, 0x00, 0xFF),
         new Dwg_RGB_Palette(0xFF, 0x00, 0xFF),
         new Dwg_RGB_Palette(0xFF, 0xFF, 0xFF),
         new Dwg_RGB_Palette(0x41, 0x41, 0x41),
         new Dwg_RGB_Palette(0x80, 0x80, 0x80),
         new Dwg_RGB_Palette(0xFF, 0x00, 0x00), //10
         new Dwg_RGB_Palette(0xFF, 0xAA, 0xAA),
         new Dwg_RGB_Palette(0xBD, 0x00, 0x00),
         new Dwg_RGB_Palette(0xBD, 0x7E, 0x7E),
         new Dwg_RGB_Palette(0x81, 0x00, 0x00),
         new Dwg_RGB_Palette(0x81, 0x56, 0x56),
         new Dwg_RGB_Palette(0x68, 0x00, 0x00),
         new Dwg_RGB_Palette(0x68, 0x45, 0x45),
         new Dwg_RGB_Palette(0x4F, 0x00, 0x00),
         new Dwg_RGB_Palette(0x4F, 0x35, 0x35),
         new Dwg_RGB_Palette( 0xFF, 0x3F, 0x00),//20
         new Dwg_RGB_Palette(0xFF, 0xBF, 0xAA),
         new Dwg_RGB_Palette(0xBD, 0x2E, 0x00),
         new Dwg_RGB_Palette(0xBD, 0x8D, 0x7E),
         new Dwg_RGB_Palette(0x81, 0x1F, 0x00),
         new Dwg_RGB_Palette(0x81, 0x60, 0x56),
         new Dwg_RGB_Palette(0x68, 0x19, 0x00),
         new Dwg_RGB_Palette(0x68, 0x4E, 0x45),
         new Dwg_RGB_Palette(0x4F, 0x13, 0x00),
         new Dwg_RGB_Palette(0x4F, 0x3B, 0x35),
         new Dwg_RGB_Palette(0xFF, 0x7F, 0x00),//30
         new Dwg_RGB_Palette( 0xFF, 0xD4, 0xAA ),
         new Dwg_RGB_Palette(0xBD, 0x5E, 0x00),
         new Dwg_RGB_Palette(0xBD, 0x9D, 0x7E),
         new Dwg_RGB_Palette(0x81, 0x40, 0x00),
         new Dwg_RGB_Palette(0x81, 0x6B, 0x56),
         new Dwg_RGB_Palette(0x68, 0x34, 0x00),
         new Dwg_RGB_Palette(0x68, 0x56, 0x45),
         new Dwg_RGB_Palette(0x4F, 0x27, 0x00),
         new Dwg_RGB_Palette(0x4F, 0x42, 0x35),
         new Dwg_RGB_Palette(0xFF, 0xBF, 0x00),//40
         new Dwg_RGB_Palette(0xFF, 0xEA, 0xAA),
         new Dwg_RGB_Palette(0xBD, 0x8D, 0x00),
         new Dwg_RGB_Palette(0xBD, 0xAD, 0x7E),
         new Dwg_RGB_Palette(0x81, 0x60, 0x00),
         new Dwg_RGB_Palette(0x81, 0x76, 0x56),
         new Dwg_RGB_Palette(0x68, 0x4E, 0x00),
         new Dwg_RGB_Palette(0x68, 0x5F, 0x45),
         new Dwg_RGB_Palette(0x4F, 0x3B, 0x00),
         new Dwg_RGB_Palette(0x4F, 0x49, 0x35),
         new Dwg_RGB_Palette(0xFF, 0xFF, 0x00),//50
         new Dwg_RGB_Palette(0xFF, 0xFF, 0xAA),
         new Dwg_RGB_Palette(0xBD, 0xBD, 0x00),
         new Dwg_RGB_Palette(0xBD, 0xBD, 0x7E),
         new Dwg_RGB_Palette(0x81, 0x81, 0x00),
         new Dwg_RGB_Palette(0x81, 0x81, 0x56),
         new Dwg_RGB_Palette(0x68, 0x68, 0x00),
         new Dwg_RGB_Palette(0x68, 0x68, 0x45),
         new Dwg_RGB_Palette(0x4F, 0x4F, 0x00),
         new Dwg_RGB_Palette(0x4F, 0x4F, 0x35),
         new Dwg_RGB_Palette( 0xBF, 0xFF, 0x00),//60
         new Dwg_RGB_Palette(0xEA, 0xFF, 0xAA),
         new Dwg_RGB_Palette(0x8D, 0xBD, 0x00),
         new Dwg_RGB_Palette(0xAD, 0xBD, 0x7E),
         new Dwg_RGB_Palette(0x60, 0x81, 0x00),
         new Dwg_RGB_Palette(0x76, 0x81, 0x56),
         new Dwg_RGB_Palette(0x4E, 0x68, 0x00),
         new Dwg_RGB_Palette(0x5F, 0x68, 0x45),
         new Dwg_RGB_Palette(0x3B, 0x4F, 0x00),
         new Dwg_RGB_Palette(0x49, 0x4F, 0x35),
         new Dwg_RGB_Palette(0x7F, 0xFF, 0x00),//70
         new Dwg_RGB_Palette(0xD4, 0xFF, 0xAA),
         new Dwg_RGB_Palette(0x5E, 0xBD, 0x00),
         new Dwg_RGB_Palette(0x9D, 0xBD, 0x7E),
         new Dwg_RGB_Palette(0x40, 0x81, 0x00),
         new Dwg_RGB_Palette(0x6B, 0x81, 0x56),
         new Dwg_RGB_Palette(0x34, 0x68, 0x00),
         new Dwg_RGB_Palette(0x56, 0x68, 0x45),
         new Dwg_RGB_Palette(0x27, 0x4F, 0x00),
         new Dwg_RGB_Palette(0x42, 0x4F, 0x35), new Dwg_RGB_Palette(0x3F, 0xFF, 0x00),//80
         new Dwg_RGB_Palette(0xBF, 0xFF, 0xAA), new Dwg_RGB_Palette(0x2E, 0xBD, 0x00),
         new Dwg_RGB_Palette(0x8D, 0xBD, 0x7E), new Dwg_RGB_Palette(0x1F, 0x81, 0x00),
         new Dwg_RGB_Palette(0x60, 0x81, 0x56), new Dwg_RGB_Palette(0x19, 0x68, 0x00),
         new Dwg_RGB_Palette(0x4E, 0x68, 0x45), new Dwg_RGB_Palette(0x13, 0x4F, 0x00),
         new Dwg_RGB_Palette(0x3B, 0x4F, 0x35), new Dwg_RGB_Palette(0x00, 0xFF, 0x00),//90
         new Dwg_RGB_Palette(0xAA, 0xFF, 0xAA), new Dwg_RGB_Palette(0x00, 0xBD, 0x00),
         new Dwg_RGB_Palette(0x7E, 0xBD, 0x7E), new Dwg_RGB_Palette(0x00, 0x81, 0x00),
         new Dwg_RGB_Palette(0x56, 0x81, 0x56), new Dwg_RGB_Palette(0x00, 0x68, 0x00),
         new Dwg_RGB_Palette(0x45, 0x68, 0x45), new Dwg_RGB_Palette(0x00, 0x4F, 0x00),
         new Dwg_RGB_Palette(0x35, 0x4F, 0x35), new Dwg_RGB_Palette(0x00, 0xFF, 0x3F),//100
         new Dwg_RGB_Palette(0xAA, 0xFF, 0xBF), new Dwg_RGB_Palette(0x00, 0xBD, 0x2E),
         new Dwg_RGB_Palette(0x7E, 0xBD, 0x8D), new Dwg_RGB_Palette(0x00, 0x81, 0x1F),
         new Dwg_RGB_Palette(0x56, 0x81, 0x60), new Dwg_RGB_Palette(0x00, 0x68, 0x19),
         new Dwg_RGB_Palette(0x45, 0x68, 0x4E), new Dwg_RGB_Palette(0x00, 0x4F, 0x13),
         new Dwg_RGB_Palette(0x35, 0x4F, 0x3B), new Dwg_RGB_Palette(0x00, 0xFF, 0x7F),//110
         new Dwg_RGB_Palette(0xAA, 0xFF, 0xD4), new Dwg_RGB_Palette(0x00, 0xBD, 0x5E),
         new Dwg_RGB_Palette(0x7E, 0xBD, 0x9D), new Dwg_RGB_Palette(0x00, 0x81, 0x40),
         new Dwg_RGB_Palette(0x56, 0x81, 0x6B), new Dwg_RGB_Palette(0x00, 0x68, 0x34),
         new Dwg_RGB_Palette(0x45, 0x68, 0x56), new Dwg_RGB_Palette(0x00, 0x4F, 0x27),
         new Dwg_RGB_Palette(0x35, 0x4F, 0x42), new Dwg_RGB_Palette(0x00, 0xFF, 0xBF),//120
         new Dwg_RGB_Palette(0xAA, 0xFF, 0xEA), new Dwg_RGB_Palette(0x00, 0xBD, 0x8D),
         new Dwg_RGB_Palette(0x7E, 0xBD, 0xAD), new Dwg_RGB_Palette(0x00, 0x81, 0x60),
         new Dwg_RGB_Palette(0x56, 0x81, 0x76), new Dwg_RGB_Palette(0x00, 0x68, 0x4E),
         new Dwg_RGB_Palette(0x45, 0x68, 0x5F), new Dwg_RGB_Palette(0x00, 0x4F, 0x3B),
         new Dwg_RGB_Palette(0x35, 0x4F, 0x49), new Dwg_RGB_Palette(0x00, 0xFF, 0xFF),//130
         new Dwg_RGB_Palette(0xAA, 0xFF, 0xFF), new Dwg_RGB_Palette(0x00, 0xBD, 0xBD),
         new Dwg_RGB_Palette(0x00, 0x4F, 0x3B), new Dwg_RGB_Palette(0x35, 0x4F, 0x49),
         new Dwg_RGB_Palette(0x00, 0xFF, 0xFF), new Dwg_RGB_Palette(0xAA, 0xFF, 0xFF),
         new Dwg_RGB_Palette(0x00, 0xBD, 0xBD), new Dwg_RGB_Palette(0x7E, 0xBD, 0xBD),
         new Dwg_RGB_Palette(0x00, 0x81, 0x81), new Dwg_RGB_Palette(0x56, 0x81, 0x81),//140
         new Dwg_RGB_Palette(0xAA, 0xEA, 0xFF), new Dwg_RGB_Palette(0x00, 0x8D, 0xBD),
         new Dwg_RGB_Palette(0x7E, 0xAD, 0xBD), new Dwg_RGB_Palette(0x00, 0x60, 0x81),
         new Dwg_RGB_Palette(0x56, 0x76, 0x81), new Dwg_RGB_Palette(0x00, 0x4E, 0x68),
         new Dwg_RGB_Palette(0x45, 0x5F, 0x68), new Dwg_RGB_Palette(0x00, 0x3B, 0x4F),
         new Dwg_RGB_Palette(0x35, 0x49, 0x4F), new Dwg_RGB_Palette(0x00, 0x7F, 0xFF),//150
         new Dwg_RGB_Palette(0xAA, 0xD4, 0xFF), new Dwg_RGB_Palette(0x00, 0x5E, 0xBD),
         new Dwg_RGB_Palette(0x7E, 0x9D, 0xBD), new Dwg_RGB_Palette(0x00, 0x40, 0x81),
         new Dwg_RGB_Palette(0x56, 0x6B, 0x81), new Dwg_RGB_Palette(0x00, 0x34, 0x68 ),
         new Dwg_RGB_Palette(0x45, 0x56, 0x68), new Dwg_RGB_Palette(0x00, 0x27, 0x4F),
         new Dwg_RGB_Palette(0x35, 0x42, 0x4F), new Dwg_RGB_Palette(0x00, 0x3F, 0xFF),//160
         new Dwg_RGB_Palette(0xAA, 0xBF, 0xFF), new Dwg_RGB_Palette(0x00, 0x2E, 0xBD),
         new Dwg_RGB_Palette(0x7E, 0x8D, 0xBD), new Dwg_RGB_Palette(0x00, 0x1F, 0x81),
         new Dwg_RGB_Palette(0x56, 0x60, 0x81), new Dwg_RGB_Palette(0x00, 0x19, 0x68),
         new Dwg_RGB_Palette(0x45, 0x4E, 0x68), new Dwg_RGB_Palette(0x00, 0x13, 0x4F),
         new Dwg_RGB_Palette(0x35, 0x3B, 0x4F), new Dwg_RGB_Palette(0x00, 0x00, 0xFF),//170new Dwg_RGB_Palette( 0xAA, 0xAA, 0xFF), new Dwg_RGB_Palette(0x00, 0x00, 0xBD),
         new Dwg_RGB_Palette(0x7E, 0x7E, 0xBD), new Dwg_RGB_Palette(0x00, 0x00, 0x81),
         new Dwg_RGB_Palette(0x56, 0x56, 0x81), new Dwg_RGB_Palette(0x00, 0x00, 0x68),
         new Dwg_RGB_Palette(0x45, 0x45, 0x68), new Dwg_RGB_Palette(0x00, 0x00, 0x4F),
         new Dwg_RGB_Palette(0x35, 0x35, 0x4F), new Dwg_RGB_Palette(0x3F, 0x00, 0xF),//180
         new Dwg_RGB_Palette(0xBF, 0xAA, 0xFF), new Dwg_RGB_Palette(0x2E, 0x00, 0xBD),
         new Dwg_RGB_Palette(0x8D, 0x7E, 0xBD), new Dwg_RGB_Palette(0x1F, 0x00, 0x81),
         new Dwg_RGB_Palette(0x60, 0x56, 0x81), new Dwg_RGB_Palette(0x19, 0x00, 0x68),
         new Dwg_RGB_Palette(0x4E, 0x45, 0x68), new Dwg_RGB_Palette(0x13, 0x00, 0x4F),
         new Dwg_RGB_Palette(0x3B, 0x35, 0x4F), new Dwg_RGB_Palette(0x7F, 0x00, 0xFF),//190
         new Dwg_RGB_Palette(0xD4, 0xAA, 0xFF), new Dwg_RGB_Palette(0x5E, 0x00, 0xBD),
         new Dwg_RGB_Palette(0x9D, 0x7E, 0xBD), new Dwg_RGB_Palette(0x40, 0x00, 0x81),
         new Dwg_RGB_Palette(0x6B, 0x56, 0x81), new Dwg_RGB_Palette(0x34, 0x00, 0x68),
         new Dwg_RGB_Palette(0x56, 0x45, 0x68), new Dwg_RGB_Palette(0x27, 0x00, 0x4F),
         new Dwg_RGB_Palette(0x42, 0x35, 0x4F), new Dwg_RGB_Palette( 0xBF, 0x00, 0xFF), //200
         new Dwg_RGB_Palette(0xEA, 0xAA, 0xFF), new Dwg_RGB_Palette(0x8D, 0x00, 0xBD),
         new Dwg_RGB_Palette(0xAD, 0x7E, 0xBD), new Dwg_RGB_Palette(0x60, 0x00, 0x81),
         new Dwg_RGB_Palette(0x76, 0x56, 0x81), new Dwg_RGB_Palette(0x4E, 0x00, 0x68),
         new Dwg_RGB_Palette( 0x5F, 0x45, 0x68), new Dwg_RGB_Palette( 0x3B, 0x00, 0x4F),
         new Dwg_RGB_Palette(0x49, 0x35, 0x4F), new Dwg_RGB_Palette(0xFF, 0x00, 0xFF),//210
         new Dwg_RGB_Palette(0xFF, 0xAA, 0xFF), new Dwg_RGB_Palette(0xBD, 0x00, 0xBD),
         new Dwg_RGB_Palette(0xBD, 0x7E, 0xBD), new Dwg_RGB_Palette(0x81, 0x00, 0x81),
         new Dwg_RGB_Palette(0x81, 0x56, 0x81), new Dwg_RGB_Palette(0x68, 0x00, 0x68),
         new Dwg_RGB_Palette(0x68, 0x45, 0x68), new Dwg_RGB_Palette(0x4F, 0x00, 0x4F),
         new Dwg_RGB_Palette(0x4F, 0x35, 0x4F), new Dwg_RGB_Palette(0xFF, 0x00, 0xBF),//220
         new Dwg_RGB_Palette(0xFF, 0xAA, 0xEA), new Dwg_RGB_Palette(0xBD, 0x00, 0x8D),
         new Dwg_RGB_Palette(0xBD, 0x7E, 0xAD), new Dwg_RGB_Palette(0x81, 0x00, 0x60),
         new Dwg_RGB_Palette(0x81, 0x56, 0x76), new Dwg_RGB_Palette(0x68, 0x00, 0x4E),
         new Dwg_RGB_Palette(0x68, 0x45, 0x5F), new Dwg_RGB_Palette(0x4F, 0x00, 0x3B),
         new Dwg_RGB_Palette(0x4F, 0x35, 0x49), new Dwg_RGB_Palette(0xFF, 0x00, 0x7F),//230
         new Dwg_RGB_Palette(0xFF, 0xAA, 0xD4), new Dwg_RGB_Palette(0xBD, 0x00, 0x5E),
         new Dwg_RGB_Palette(0xBD, 0x7E, 0x9D), new Dwg_RGB_Palette(0x81, 0x00, 0x40),
         new Dwg_RGB_Palette(0x81, 0x56, 0x6B), new Dwg_RGB_Palette(0x68, 0x00, 0x34),
         new Dwg_RGB_Palette(0x68, 0x45, 0x56), new Dwg_RGB_Palette(0x4F, 0x00, 0x27),
         new Dwg_RGB_Palette(0x4F, 0x35, 0x42), new Dwg_RGB_Palette(0xFF, 0x00, 0x3F),//240
         new Dwg_RGB_Palette(0xFF, 0xAA, 0xBF), new Dwg_RGB_Palette(0xBD, 0x00, 0x2E),
         new Dwg_RGB_Palette(0xBD, 0x7E, 0x8D), new Dwg_RGB_Palette(0x81, 0x00, 0x1F),
         new Dwg_RGB_Palette(0x81, 0x56, 0x60), new Dwg_RGB_Palette(0x68, 0x00, 0x19),
         new Dwg_RGB_Palette(0x68, 0x45, 0x4E), new Dwg_RGB_Palette(0x4F, 0x00, 0x13),
         new Dwg_RGB_Palette(0x4F, 0x35, 0x3B), new Dwg_RGB_Palette(0x33, 0x33, 0x33),//250
         new Dwg_RGB_Palette(0x50, 0x50, 0x50), new Dwg_RGB_Palette(0x69, 0x69, 0x69),
         new Dwg_RGB_Palette(0x82, 0x82, 0x82), new Dwg_RGB_Palette(0xBE, 0xBE, 0xBE),
         new Dwg_RGB_Palette(0xFF, 0xFF, 0xFF) // 255
    };
    static Dwg_RGB_Palette[] dwg_rgb_palette()
    {
        return rgb_palettes;
    }

    static short dxf_cvt_lweight(short value) {
        return lweights[value % 32];
    }
    static short dxf_revcvt_lweight(short lw) {
        for(short i = 0; i < lweights.length; i++)
        {
            if(lweights[i] == lw)
            {
                return i;
            }
        }
        return 0;
    }
    static short lweights[] = { 0,
            5,
            9,
            13,
            15,
            18,
            20,
            25,
            30,
            35,
            40,
            50,
            53,
            60,
            70,
            80,
            90,
            100,
            106,
            120,
            /*20:*/ 140,
            158,
            200,
            211,
            /*illegal/reserved:*/ 0,
            0,
            0,
            0,
            /*28:*/ 0,  // 0x1c
            /*29:*/ -1, // 0x1d BYLAYER
            -2,         // BYBLOCK
            -3 };       // BYLWDEFAULT

    static class DwgBumpData{
        public char[] chain;
        public long size;
        public char typep;

        public DwgBumpData(){}
    }

     static DwgBumpData dwg_bmp(Dwg_Data objDwgData) {
        DwgBumpData data = new DwgBumpData();
        char i, num_headers,type = 0;
        int found = 0;
        long header_size,address = 0, osize;
        Bit_Chain dat = new Bit_Chain();
        loglevel = objDwgData.opts & DWG_OPTS_LOGLEVEL;
        data.size = 0;
        assert objDwgData != null;
        dat.chain = objDwgData.thumbnail.chain;
        dat.size = objDwgData.thumbnail.size;
        dat._byte = 0;
        dat.bit = '\0';
        osize = bits.bit_read_RL(dat);
        if(osize > dat.size)
        {
            return null;
        }
        num_headers = bits.bit_read_RC(dat);

        found = 0;
        header_size = 0;

        for(i = 0; i < num_headers; i++)
        {
            if(dat._byte > dat.size)
            {
                break;
            }
            type = bits.bit_read_RC(dat);
            data.typep = type;
            address = bits.bit_read_RL(dat);
            if(type == 1)
            {
                long h_size = bits.bit_read_RL(dat);
                header_size += h_size;
            }else if(type == 2 && found == 0){
                data.size = bits.bit_read_RL(dat);
                found = 1;
                if(data.size > dat.size - 4)
                {
                    return null;
                }
            }else if(type == 3)
            {
                osize = bits.bit_read_RL(dat);
                data.size = osize;
            }
            else if(type == 6)
            {
                osize = bits.bit_read_RL(dat);
                data.size = osize;
            }
            else{
                osize = bits.bit_read_RL(dat);
            }
        }

        dat._byte += header_size;
        if(data.size != 0)
        {
            //LOG_TRACE ("Image offset: %" PRIuSIZE "\n", dat.byte);
        }
        if(header_size + data.size > dat.size)
        {
            data.size = 0;
            return null;
        }
        if(data.size > 0)
        {
            data.chain = new char[(int) data.size];
            System.arraycopy(dat.chain, (int) dat._byte, data.chain, 0, (int) data.size);
            return data;
        }
        return  null;
    }
}
class Dwg_RGB_Palette {
    public int r;
    public int g;
    public int b;

    public Dwg_RGB_Palette(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Dwg_RGB_Palette() {
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
    public Dwg_Handle handleref = new Dwg_Handle();
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
class Dwg_Bitcode_3RD {
    public double x;
    public double y;
    public double z;
    Dwg_Bitcode_3RD(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    Dwg_Bitcode_3RD()
    {

    }
}
class Dwg_Bitcode_2BD {
    public double x;
    public double y;
    Dwg_Bitcode_2BD(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    Dwg_Bitcode_2BD()
    {

    }
}
class Dwg_Bitcode_2RD {
    public double x;
    public double y;
    Dwg_Bitcode_2RD(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    Dwg_Bitcode_2RD()
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
        public Dwg_Object_BLOCK_CONTROL BLOCK_CONTROL;
        public Dwg_Object_LAYER_CONTROL LAYER_CONTROL;
        public Dwg_Object_STYLE_CONTROL STYLE_CONTROL;
        public Dwg_Object_LTYPE_CONTROL LTYPE_CONTROL;
        public Dwg_Object_VIEW_CONTROL VIEW_CONTROL;
        public Dwg_Object_UCS_CONTROL UCS_CONTROL;
        public Dwg_Object_VPORT_CONTROL VPORT_CONTROL;
        public Dwg_Object_APPID_CONTROL APPID_CONTROL;
        public Dwg_Object_DIMSTYLE_CONTROL DIMSTYLE_CONTROL;
        public Dwg_Object_VX_CONTROL VX_CONTROL;
        public Dwg_Object_DICTIONARY DICTIONARY;
        public Dwg_Object_DICTIONARYWDFLT DICTIONARYWDFLT;
        public Dwg_Object_PLACEHOLDER PLACEHOLDER;
    }
    Tio tio = new Tio();
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
    public DWG_OBJECT_TYPE fixedtype = DWG_OBJECT_TYPE.DWG_TYPE_UNUSED;
    String name;
    String dxfname;
    public DWG_OBJECT_SUPERTYPE supertype;
    class Tio{
        Dwg_Object_Entity entity;
        Dwg_Object_Object object;
    }
    public Tio tio = new Tio();
    public Dwg_Handle handle = new Dwg_Handle();
    public Dwg_Data parent;
    public Dwg_Class klass;

    public int bitsize;
    public long bitsize_pos;
    public long hdlpos;
    public char was_bitsize_set;
    public char has_strings;
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
    dwg_inthash(){}

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
    public int number= 0;
    public int size = 0;
    public long address = 0;
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
    public Dwg_Class[] dwg_class;         /*!< array of classes */
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
    public Dwg_SecondHeader secondheader = new Dwg_SecondHeader();
    public Dwg_SummaryInfo summaryinfo;;
    public Dwg_AppInfo appinfo;
    public Dwg_FileDepList filedeplist;
    public Dwg_Security security;
    public Dwg_VBAProject vbaproject;
    public Dwg_AppInfoHistory appinfohistory;
    public Dwg_RevHistory revhistory;
    public Dwg_ObjFreeSpace objfreespace = new Dwg_ObjFreeSpace();
    public Dwg_Template template = new Dwg_Template();
    public Dwg_AcDs acds;

    public int layout_type;
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
    public long entities_start;
    public long entities_end;
    public long blocks_start;
    public long blocks_size;
    public long extras_start;
    public long extras_size;
    public int codepage;
    public char unknown_0;
    public char app_dwg_version;
    public char app_maint_version;
    public int security_type;
    public int r1_1c_address;
    public int summaryinfo_address;
    public int vbaproj_address;
    public int r2004_header_address;
    public long num_sections;
    public long sections;
    public Dwg_Section[] section;
    public Dwg_Section_InfoHdr section_infohdr = new Dwg_Section_InfoHdr();
    public Dwg_Section_Info[] section_info;
}

class Dwg_Header_Variables {
    public long size;
    public long bitsize_hi;
    public long bitsize;
    public char ACADMAINTVER;
    public long REQUIREDVERSIONS;
    public String DWGCODEPAGE;
    public int codepage;
    public double unit1_ratio;
    public double unit2_ratio;
    public double unit3_ratio;
    public double unit4_ratio;
    public String unit1_name;
    public String unit2_name;
    public String unit3_name;
    public String unit4_name;
    public long unknown_8;
    public long unknown_9;
    public int unknown_10;
    public Dwg_Object_Ref VX_TABLE_RECORD;
    public char DIMASO;
    public char DIMSHO;
    public char DIMSAV; /* undocumented */
    public char PLINEGEN;
    public char ORTHOMODE;
    public char REGENMODE;
    public char FILLMODE;
    public char QTEXTMODE;
    public char PSLTSCALE; /* r10- */
    public char LIMCHECK;
    public char[] MENUEXT = new char[46];
    public char BLIPMODE;
    public char unknown_11;
    public char USRTIMER;
    public char FASTZOOM;  /* -r11 */
    public char FLATLAND;
    public char VIEWMODE;
    public char SKPOLY;
    public int unknown_mon;  /* -r11 */
    public int unknown_day;  /* -r11 */
    public int unknown_year; /* -r11 */
    public int unknown_hour; /* -r11 */
    public int unknown_min;  /* -r11 */
    public int unknown_sec;  /* -r11 */
    public int unknown_ms;  /* -r11 */
    public char ANGDIR;
    public char SPLFRAME;
    public char ATTREQ;
    public char ATTDIA;
    public char MIRRTEXT;
    public char WORLDVIEW;
    public char WIREFRAME; /* Undocumented */
    public char TILEMODE;
    public char PLIMCHECK;
    public char VISRETAIN;
    public char DELOBJ;
    public char DISPSILH;
    public char PELLIPSE;
    public int SAVEIMAGES; //some r13 only
    public int PROXYGRAPHICS;
    public int MEASUREMENT; /* 0 English, 1 Metric. Stored as Section 4 */
    public int DRAGMODE;
    public short TREEDEPTH;
    public int LUNITS;
    public int LUPREC;
    public int AUNITS;
    public int AUPREC;
    public int ATTMODE;
    public int COORDS;
    public int PDMODE;
    public int PICKSTYLE;
    public int OSMODE;
    public long unknown_12;
    public long unknown_13;
    public long unknown_14;
    public short USERI1;
    public short USERI2;
    public short USERI3;
    public short USERI4;
    public short USERI5;
    public int SPLINESEGS;
    public int SURFU;
    public int SURFV;
    public int SURFTYPE;
    public int SURFTAB1;
    public int SURFTAB2;
    public int SPLINETYPE;
    public int SHADEDGE;
    public int SHADEDIF;
    public int UNITMODE;
    public int MAXACTVP;
    public int ISOLINES;
    public int CMLJUST;
    public int TEXTQLTY;
    public long unknown_14b;
    public double LTSCALE;
    public double TEXTSIZE;
    public double TRACEWID;
    public double SKETCHINC;
    public double FILLETRAD;
    public double THICKNESS;
    public double ANGBASE;
    public double PDSIZE;
    public double PLINEWID;
    public double USERR1;
    public double USERR2;
    public double USERR3;
    public double USERR4;
    public double USERR5;
    public double CHAMFERA;
    public double CHAMFERB;
    public double CHAMFERC;
    public double CHAMFERD;
    public double FACETRES;
    public double CMLSCALE;
    public double CELTSCALE;
    public double VIEWTWIST;
    public String MENU;
    public Dwg_Bitcode_TimeBLL TDCREATE;
    public Dwg_Bitcode_TimeBLL TDUPDATE;
    public Dwg_Bitcode_TimeBLL TDUCREATE;
    public Dwg_Bitcode_TimeBLL TDUUPDATE;
    public long unknown_15;
    public long unknown_16;
    public long unknown_17;
    public Dwg_Bitcode_TimeBLL TDINDWG;
    public Dwg_Bitcode_TimeBLL TDUSRTIMER;
    public Dwg_Color CECOLOR = new Dwg_Color();
    public int HANDLING; /* <r14: default 1 */
    public Dwg_Object_Ref HANDSEED;
    public long unknown_4f2; /* -r11 */
    public int unknown_5; /* r2-r11 */
    public int unknown_6; /* r10-r11 */
    public int unknown_6a; /* r2-r9 */
    public int unknown_6b; /* r2-r9 */
    public int unknown_6c; /* r2-r9 */
    public Dwg_Object_Ref CLAYER;	/*!< code 5, DXF 8 */
    public Dwg_Object_Ref TEXTSTYLE;	/*!< code 5, DXF 7 */
    public Dwg_Object_Ref CELTYPE;	/*!< code 5, DXF 6 */
    public Dwg_Object_Ref CMATERIAL;	/*!< r2007+ code 5, no DXF */
    public Dwg_Object_Ref DIMSTYLE;	/*!< code 5, DXF 2 */
    public Dwg_Object_Ref CMLSTYLE;	/*!< code 5, DXF 2 */
    public double PSVPSCALE;
    public Dwg_Bitcode_3BD PINSBASE;
    public Dwg_Bitcode_3BD PEXTMIN;
    public Dwg_Bitcode_3BD PEXTMAX;
    public Dwg_Bitcode_2RD PLIMMIN;
    public Dwg_Bitcode_2RD PLIMMAX;
    public double  PELEVATION;
    public Dwg_Bitcode_3BD PUCSORG;
    public Dwg_Bitcode_3BD PUCSXDIR;
    public Dwg_Bitcode_3BD PUCSYDIR;
    public Dwg_Object_Ref PUCSNAME;		/*!< r13+ code 5, DXF 2 */
    public Dwg_Object_Ref PUCSBASE;		/*!< r2000+ code 5, DXF 2 */
    public Dwg_Object_Ref PUCSORTHOREF;	/*!< r2000+ code 5, DXF 2 */
    public int PUCSORTHOVIEW;
    public Dwg_Bitcode_3BD PUCSORGTOP;
    public Dwg_Bitcode_3BD PUCSORGBOTTOM;
    public Dwg_Bitcode_3BD PUCSORGLEFT;
    public Dwg_Bitcode_3BD PUCSORGRIGHT;
    public Dwg_Bitcode_3BD PUCSORGFRONT;
    public Dwg_Bitcode_3BD PUCSORGBACK;
    public Dwg_Bitcode_3BD INSBASE;
    public Dwg_Bitcode_3BD EXTMIN;
    public Dwg_Bitcode_3BD EXTMAX;
    public Dwg_Bitcode_3BD VIEWDIR;
    public Dwg_Bitcode_3BD TARGET;
    public Dwg_Bitcode_2RD LIMMIN;
    public Dwg_Bitcode_2RD LIMMAX;
    public Dwg_Bitcode_3RD VIEWCTR;  /* -r11 */
    public double ELEVATION;
    public double VIEWSIZE;  /* -r11 */
    public int SNAPMODE;  /* -r11 */
    public Dwg_Bitcode_2RD SNAPUNIT; /* -r11 */
    public Dwg_Bitcode_2RD SNAPBASE; /* -r11 */
    public double SNAPANG;   /* -r11 */
    public int SNAPSTYLE;  /* -r11 */
    public int SNAPISOPAIR; /* -r11 */
    public int GRIDMODE;  /* -r11 */
    public Dwg_Bitcode_2RD GRIDUNIT; /* -r11 */
    public int AXISMODE;  /* -r11 */
    public Dwg_Bitcode_2RD AXISUNIT; /* -r11 */
    public Dwg_Bitcode_3RD VPOINTX; /* -r11 */
    public Dwg_Bitcode_3RD VPOINTY; /* -r11 */
    public Dwg_Bitcode_3RD VPOINTZ; /* -r11 */
    public Dwg_Bitcode_3RD VPOINTXALT; /* -r11 */
    public Dwg_Bitcode_3RD VPOINTYALT; /* -r11 */
    public Dwg_Bitcode_3RD VPOINTZALT; /* -r11 */
    public int flag_3d; /* -r11 */
    public Dwg_Bitcode_3BD UCSORG;
    public Dwg_Bitcode_3BD UCSXDIR;
    public Dwg_Bitcode_3BD UCSYDIR;
    public Dwg_Object_Ref UCSNAME;		/*!< code 5, DXF 2 */
    public Dwg_Object_Ref UCSBASE;		/*!< code 5, DXF 2 */
    public int UCSORTHOVIEW;
    public Dwg_Object_Ref UCSORTHOREF;	/*!< code 5, DXF 2 */
    public Dwg_Bitcode_3BD UCSORGTOP;
    public Dwg_Bitcode_3BD UCSORGBOTTOM;
    public Dwg_Bitcode_3BD UCSORGLEFT;
    public Dwg_Bitcode_3BD UCSORGRIGHT;
    public Dwg_Bitcode_3BD UCSORGFRONT;
    public Dwg_Bitcode_3BD UCSORGBACK;
    public String DIMPOST;
    public String DIMAPOST;
    public char DIMTOL;
    public char DIMLIM;
    public char DIMTIH;
    public char DIMTOH;
    public char DIMSE1;
    public char DIMSE2;
    public char DIMALT;
    public char DIMTOFL;
    public char DIMSAH;
    public char DIMTIX;
    public char DIMSOXD;
    public int DIMALTD;   /*!< r13-r14 only RC */
    public char DIMZIN;    /*!< r13-r14 only RC */
    public char DIMSD1;
    public char DIMSD2;
    public int DIMTOLJ;   /*!< r13-r14 only RC */
    public int DIMJUST;   /*!< r13-r14 only RC */
    public int DIMFIT;    /*!< r13-r14 only RC */
    public char DIMUPT;
    public int DIMTZIN;   /*!< r13-r14 only RC */
    public int DIMTAD;    /*!< r13-r14 only RC */
    public int DIMUNIT;
    public int DIMAUNIT;
    public int DIMDEC;
    public int DIMTDEC;
    public int DIMALTU;
    public int DIMALTTD;
    public Dwg_Object_Ref DIMTXSTY;	/*!< code 5, DXF 7 */
    public double DIMSCALE;
    public double DIMARROW; /* r1.2-r1.4 */
    public double DIMASZ;
    public double DIMEXO;
    public double DIMDLI;
    public double DIMEXE;
    public double DIMRND;
    public double DIMDLE;
    public double DIMTP;
    public double DIMTM;
    public double DIMFXL;
    public double DIMJOGANG;
    public int DIMTFILL;
    public Dwg_Color DIMTFILLCLR = new Dwg_Color();
    public int DIMAZIN;
    public int DIMARCSYM;
    public double DIMTXT;
    public double DIMCEN;
    public double DIMTSZ;
    public double DIMALTF;
    public double DIMLFAC;
    public double DIMTVP;
    public double DIMTFAC;
    public double DIMGAP;
    public String DIMPOST_T; /* preR13 => handle */
    public String DIMAPOST_T;
    public String DIMBLK_T;
    public String DIMBLK1_T;
    public String DIMBLK2_T;
    public String unknown_string; /* r10-r11 */
    public double DIMALTRND;
    public int DIMCLRD_C; /* preR13 => CMC */
    public int DIMCLRE_C;
    public int DIMCLRT_C;
    public Dwg_Color DIMCLRD = new Dwg_Color();
    public Dwg_Color DIMCLRE = new Dwg_Color();;
    public Dwg_Color DIMCLRT = new Dwg_Color();;
    public int DIMADEC;   /*!< r2000+ ... */
    public int DIMFRAC;
    public int DIMLUNIT;
    public int DIMDSEP;
    public int DIMTMOVE;
    public int DIMALTZ;  /*!< r13-r14 only RC */
    public int DIMALTTZ; /*!< r13-r14 only RC */
    public int DIMATFIT;
    public char  DIMFXLON;  /*!< r2007+ */
    public char  DIMTXTDIRECTION; /*!< r2010+ */
    public double DIMALTMZF; /*!< r2010+ */
    public String  DIMALTMZS; /*!< r2010+ */
    public double DIMMZF;    /*!< r2010+ */
    public String  DIMMZS;    /*!< r2010+ */
    /*BITCODE_H DIMTXSTY;*/  /*!< r2000+ */
    public Dwg_Object_Ref DIMLDRBLK;  /*!< r2000+ code 5, DXF 1 */
    public Dwg_Object_Ref DIMBLK;     /*!< r2000+ code 5, DXF 1 */
    public Dwg_Object_Ref DIMBLK1;    /*!< r2000+ code 5, DXF 1 */
    public Dwg_Object_Ref DIMBLK2;    /*!< r2000+ code 5, DXF 1 */
    public Dwg_Object_Ref DIMLTYPE; /*!< r2007+ code 5, DXF 6 */
    public Dwg_Object_Ref DIMLTEX1; /*!< r2007+ code 5, DXF 6 */
    public Dwg_Object_Ref DIMLTEX2; /*!< r2007+ code 5, DXF 6 */
    public short DIMLWD;  /*!< r2000+ */
    public short DIMLWE;  /*!< r2000+ */
    public Dwg_Object_Ref BLOCK_CONTROL_OBJECT; /*!< code 3 */
    public Dwg_Object_Ref LAYER_CONTROL_OBJECT; /*!< code 3 */
    public Dwg_Object_Ref STYLE_CONTROL_OBJECT; /*!< code 3 */
    public Dwg_Object_Ref LTYPE_CONTROL_OBJECT; /*!< code 3 */
    public Dwg_Object_Ref VIEW_CONTROL_OBJECT; /*!< code 3 */
    public Dwg_Object_Ref UCS_CONTROL_OBJECT; /*!< code 3 */
    public Dwg_Object_Ref VPORT_CONTROL_OBJECT; /*!< code 3 */
    public Dwg_Object_Ref APPID_CONTROL_OBJECT; /*!< code 3 */
    public Dwg_Object_Ref DIMSTYLE_CONTROL_OBJECT; /*!< code 3 */
    public Dwg_Object_Ref VX_CONTROL_OBJECT; /*!< r11-r2000 code 3 */
    public Dwg_Object_Ref DICTIONARY_ACAD_GROUP;	/*!< code 5 */
    public Dwg_Object_Ref DICTIONARY_ACAD_MLINESTYLE;	/*!< code 5 */
    public Dwg_Object_Ref DICTIONARY_NAMED_OBJECT;	/*!< code 5, the "NOD" */
    public int TSTACKALIGN;           /*!< r2000+ */
    public int TSTACKSIZE;            /*!< r2000+ */
    public String  HYPERLINKBASE;         /*!< r2000+ */
    public String STYLESHEET;            /*!< r2000+ */
    public Dwg_Object_Ref DICTIONARY_LAYOUT;      /*!< r2000+ code 5 */
    public Dwg_Object_Ref DICTIONARY_PLOTSETTINGS;   /*!< r2000+ code 5 */
    public Dwg_Object_Ref DICTIONARY_PLOTSTYLENAME;  /*!< r2000+ code 5 */
    public Dwg_Object_Ref DICTIONARY_MATERIAL;    /*!< r2004+ code 5 */
    public Dwg_Object_Ref DICTIONARY_COLOR;       /*!< r2004+ code 5 */
    public Dwg_Object_Ref DICTIONARY_VISUALSTYLE; /*!< r2007+ code 5 */
    public Dwg_Object_Ref DICTIONARY_LIGHTLIST;   /*!< r2010+ code 5 ?? */
    public Dwg_Object_Ref unknown_20;             /*!< r2013+ code 5 LIGHTLIST? */
    public long FLAGS;
    public short CELWEIGHT; /*!< = FLAGS & 0x1f, see dxf_cvt_lweight() DXF 370 (int16) */
    public char  ENDCAPS;    /*!< = FLAGS & 0x60 */
    public char  JOINSTYLE;  /*!< = FLAGS & 0x180 */
    public char  LWDISPLAY;  /*!< = !(FLAGS & 0x200) */
    public char  XEDIT;      /*!< = !(FLAGS & 0x400) */
    public char  EXTNAMES;   /*!< = FLAGS & 0x800 */
    public char  PSTYLEMODE; /*!< = FLAGS & 0x2000 */
    public char  OLESTARTUP; /*!< = FLAGS & 0x4000 */
    public int INSUNITS;
    public int CEPSNTYPE;
    public Dwg_Object_Ref CPSNID;      /*!< when CEPSNTYPE = 3, code 5 */
    public String FINGERPRINTGUID;
    public String VERSIONGUID;
    public char SORTENTS;
    public char INDEXCTL;
    public char HIDETEXT;
    public char XCLIPFRAME;
    public char DIMASSOC;
    public char HALOGAP;
    public int OBSCOLOR;
    public int INTERSECTIONCOLOR;
    public char OBSLTYPE;
    public char INTERSECTIONDISPLAY;
    public String PROJECTNAME;
    public Dwg_Object_Ref BLOCK_RECORD_PSPACE;	/*!< code 5 */
    public Dwg_Object_Ref BLOCK_RECORD_MSPACE;	/*!< code 5 */
    public Dwg_Object_Ref LTYPE_BYLAYER;	/*!< code 5 */
    public Dwg_Object_Ref LTYPE_BYBLOCK;	/*!< code 5 */
    public Dwg_Object_Ref LTYPE_CONTINUOUS;	/*!< code 5 */
    public char CAMERADISPLAY; /*!< r2007+ ... */
    public long unknown_21;
    public long unknown_22;
    public double unknown_23;
    public double STEPSPERSEC;
    public double STEPSIZE;
    public double _3DDWFPREC;
    public double LENSLENGTH;
    public double CAMERAHEIGHT;
    public char SOLIDHIST;
    public char SHOWHIST;
    public double PSOLWIDTH;
    public double PSOLHEIGHT;
    public double LOFTANG1;
    public double LOFTANG2;
    public double LOFTMAG1;
    public double LOFTMAG2;
    public int LOFTPARAM;
    public char LOFTNORMALS;
    public double LATITUDE;
    public double LONGITUDE;
    public double NORTHDIRECTION;
    public long TIMEZONE;
    public char LIGHTGLYPHDISPLAY;
    public char TILEMODELIGHTSYNCH;
    public char DWFFRAME;
    public char DGNFRAME;
    public char REALWORLDSCALE;
    public Dwg_Color INTERFERECOLOR = new Dwg_Color();
    public Dwg_Object_Ref INTERFEREOBJVS;	/*!< r2007+ code 5, DXF 345 VISUALSTYLE */
    public Dwg_Object_Ref INTERFEREVPVS;	/*!< r2007+ code 5, DXF 346 VISUALSTYLE */
    public Dwg_Object_Ref DRAGVS;		/*!< r2007+ code 5, DXF 349 VISUALSTYLE */
    public char CSHADOW;
    public double SHADOWPLANELOCATION;
    public int unknown_54; /*!< r14+ ... optional */
    public int unknown_55;
    public int unknown_56;
    public int unknown_57;
    public long dwg_size;     /* -r1.40 */
    public int numentities;  /* r2.0 - r10 */
    public int circle_zoom_percent;
    public double FRONTZ;
    public double BACKZ;
    public char UCSICON;
    public long oldCECOLOR_hi; /* r11, or RD */
    public long oldCECOLOR_lo; /* r11 */
    public int[] layer_colors = new int[128]; /* r1.1 - r1.4 */
    public int unknown_51e;  /* r11 */
    public int unknown_520;  /* r11 */
    public short unknown_52c;  /* r11 */
    public int unknown_52e;  /* r11 */
    public char unknown_530;  /* r11 */
    public int unknown_59;   /* r11 */
    public double aspect_ratio; /* r11 */
}

class Dwg_Chain
{
    public char[] chain;
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

class Dwg_Object_BLOCK_CONTROL extends Dwg_Object_With_COMMON_TABLE_CONTROL_FIELDS
{
    public Dwg_Object_Ref model_space;
    public Dwg_Object_Ref paper_space;
}
class Dwg_Object_LAYER_CONTROL extends Dwg_Object_With_COMMON_TABLE_CONTROL_FIELDS
{

}
class Dwg_Object_STYLE_CONTROL extends Dwg_Object_With_COMMON_TABLE_CONTROL_FIELDS
{

}
class Dwg_Object_LTYPE_CONTROL extends Dwg_Object_With_COMMON_TABLE_CONTROL_FIELDS
{
    public Dwg_Object_Ref bylayer;
    public Dwg_Object_Ref byblock;
}
class Dwg_Object_VIEW_CONTROL extends Dwg_Object_With_COMMON_TABLE_CONTROL_FIELDS
{

}
class Dwg_Object_UCS_CONTROL extends Dwg_Object_With_COMMON_TABLE_CONTROL_FIELDS
{

}
class Dwg_Object_VPORT_CONTROL extends Dwg_Object_With_COMMON_TABLE_CONTROL_FIELDS
{

}
class Dwg_Object_APPID_CONTROL extends Dwg_Object_With_COMMON_TABLE_CONTROL_FIELDS
{

}
class Dwg_Object_DIMSTYLE_CONTROL extends Dwg_Object_With_COMMON_TABLE_CONTROL_FIELDS
{
    public int num_morehandles;
    public Dwg_Object_Ref[] morehandles;
}
class Dwg_Object_VX_CONTROL extends Dwg_Object_With_COMMON_TABLE_CONTROL_FIELDS
{

}

class Dwg_Object_DICTIONARY implements IParent {
    public Dwg_Object_Object parent;
    public long numitems;
    public char is_hardowner;
    public int cloning;
    public String[] texts;
    public Dwg_Object_Ref[] itemhandles;

    @Override
    public Dwg_Object_Object getParent() {
        return parent;
    }

    @Override
    public void setParent(Dwg_Object_Object parent) {
        this.parent = parent;
    }
}

class Dwg_Object_DICTIONARYWDFLT implements IParent{
    public Dwg_Object_Object parent;
    public long numitems;
    public char is_hardowner;
    public int cloning;
    public String[] texts;
    public Dwg_Object_Ref[] itemhandles;
    public Dwg_Object_Ref defaultid;
    @Override
    public Dwg_Object_Object getParent() {
        return parent;
    }

    @Override
    public void setParent(Dwg_Object_Object parent) {
        this.parent = parent;
    }
}
class Dwg_Object_PLACEHOLDER implements IParent {
    public Dwg_Object_Object parent;

    @Override
    public Dwg_Object_Object getParent() {
        return parent;
    }

    @Override
    public void setParent(Dwg_Object_Object parent) {
        this.parent = parent;
    }
}

class Dwg_AuxHeader
{
    public char[] aux_intro;
    public int dwg_version;
    public long maint_version;
    public int numsaves;
    public long minus_1;
    public int numsaves_1;
    public int numsaves_2;
    public long zero;
    public int dwg_version_1;
    public long maint_version_1;
    public int dwg_version_2;
    public long maint_version_2;
    public long[] unknown_6rs;
    public long[] unknown_5rl;
    public Dwg_Bitcode_TimeRLL TDCREATE;
    public Dwg_Bitcode_TimeRLL TDUPDATE;
    public long HANDSEED;
    public long plot_stamp;
    public int zero_1;
    public int numsaves_3;
    public long zero_2;
    public long zero_3;
    public long zero_4;
    public long numsaves_4;
    public long zero_5;
    public long zero_6;
    public long zero_7;
    public long zero_8;     /* ?? */
    public long[] zero_18; /* R2018+ */
    public int num_auxheader_variables; /* < R13 */
    public int auxheader_size;          /* < R13 */
    public long entities_start;          /* < R13 */
    public long entities_end;            /* < R13 */
    public long blocks_start;            /* < R13 */
    public long extras_start;            /* < R13 */
    public long auxheader_address;      /* < R13 */
    public int num_aux_tables;          /* < R13 */
    public int R11_HANDLING;  /* TODO Merge with HANDSEED */
    public Dwg_Object_Ref R11_HANDSEED;   /* TODO Merge with HANDSEED */
}

class Dwg_SecondHeader
{
    public long size;
    public long address;
    public char[] version = new char[11];
    public char is_maint;
    public char zero_one_or_three;
    public int dwg_version;
    public int codepage;
    public int num_sections;
    public Dwg_SecondHeader_Sections[] sections = new Dwg_SecondHeader_Sections[7];
    public int num_handles;
    public Dwg_SecondHeader_Handles[] handles = new Dwg_SecondHeader_Handles[14];
    public int crc;
    public BigInteger junk_r14;

}

class Dwg_SecondHeader_Sections {
    public char nr;
    public long address;
    public long size;
}

class Dwg_SecondHeader_Handles{
    public char num_hdl;
    public char nr;
    public char[] hdl = new char[8];
    public String name;
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
    public long zero;
    public long numhandles;
    public Dwg_Bitcode_TimeRLL TDUPDATE;
    public long objects_address;
    public char numnums;
    public long max32;
    public long max64;
    public long maxtbl;
    public long maxrl;
    public long max32_hi;
    public long max64_hi;
    public long maxtbl_hi;
    public long maxrl_hi;

}

class Dwg_Template
{
    public String description;
    public int MEASUREMENT;
}

class Dwg_AcDs
{

}

class Dwg_Object_With_COMMON_TABLE_CONTROL_FIELDS implements ICommon {
    public COMMON_TABLE_CONTROL_FIELDS common = new COMMON_TABLE_CONTROL_FIELDS();
    @Override
    public IParent getCommon() {
        return common;
    }
}

class COMMON_TABLE_CONTROL_FIELDS implements IParent
{
    public Dwg_Object_Object parent;
    public int num_entries;
    public Dwg_Object_Ref[] entres;
    public int objid;
    public int flags_r11;

    @Override
    public Dwg_Object_Object getParent() {
        return parent;
    }

    @Override
    public void setParent(Dwg_Object_Object parent) {
        this.parent = parent;
    }
}

interface ICommon {
    public IParent getCommon();
}

interface IParent {
    public Dwg_Object_Object getParent();
    public void setParent(Dwg_Object_Object parent);
}
