public class config_write {
    /** Define to 1 if a shared library will be built */
    public static int ENABLE_SHARED = 1;

    /** Define if __attribute__((visibility("default"))) is supported. */
    public static int HAVE_ATTRIBUTE_VISIBILITY_DEFAULT = 1;

    /** Define to 1 if you have the `basename' function. */
    public static int HAVE_BASENAME = 1;

    /** Defined to 1 when the compiler supports c99, mostly (...) macros */
    public static int HAVE_C99 = 1;

    /** Define to 1 if you have the <ctype.h> header file. */
    public static int HAVE_CTYPE_H = 1;

    /** Define to 1 if you have the <dlfcn.h> header file. */
    public static int HAVE_DLFCN_H = 1;

    /** Define to 1 if you have the <float.h> header file. */
    public static int HAVE_FLOAT_H = 1;

    /** Define to 1 if you have the `floor' function. */
    public static int HAVE_FLOOR = 1;

    /* Define to 1 if the system has the `format' function attribute */
    public static int HAVE_FUNC_ATTRIBUTE_FORMAT = 1;

    /* Define to 1 if the system has the `gnu_format' function attribute */
    public static int HAVE_FUNC_ATTRIBUTE_GNU_FORMAT = 1;

    /* Define to 1 if the system has the `ms_format' function attribute */
    public static int HAVE_FUNC_ATTRIBUTE_MS_FORMAT = 1;

    /* Define to 1 if the system has the `returns_nonnull' function attribute */
//#define HAVE_FUNC_ATTRIBUTE_RETURNS_NONNULL 1

    /* Define to 1 if you have the <getopt.h> header file. */
    public static int HAVE_GETOPT_H = 1;

    /* Define to 1 if you have the `getopt_long' function. */
    public static int HAVE_GETOPT_LONG = 1;

    /* Define to 1 if you have the <inttypes.h> header file. */
    public static int HAVE_INTTYPES_H = 1;

    /* Define to 1 if you have the <libgen.h> header file. */
    public static int HAVE_LIBGEN_H = 1;

    /* Define to 1 if you have the `m' library (-lm). */
    public static int HAVE_LIBM = 1;

    /* Define to 1 if you have the <libps/pslib.h> header file. */
    public static int HAVE_LIBPS_PSLIB_H = 0;   /* #undef HAVE_LIBPS_PSLIB_H */

    /* Define to 1 if you have the <limits.h> header file. */
    public static int HAVE_LIMITS_H = 1;

    /* Define to 1 if your system has a GNU libc compatible `malloc' function, and
       to 0 otherwise. */
    public static int HAVE_MALLOC = 1;

    /* Define to 1 if you have the <malloc.h> header file. */
    public static int HAVE_MALLOC_H = 1;

    /* Define to 1 if you have the `memchr' function. */
    public static int HAVE_MEMCHR = 1;

    /* Define to 1 if you have the `memmove' function. */
    public static int HAVE_MEMMOVE = 1;

    /* Define to 1 if you have the <memory.h> header file. */
    public static int HAVE_MEMORY_H = 1;

    /* Define to disable DXF and other in/out modules. */
    public static int DISABLE_DXF = 0;  /* #undef DISABLE_DXF */

    /* Define to 1 to enable runtime tracing support. */
    public static int USE_TRACING = 0;  /* #undef USE_TRACING */

    /* Define to 1 if you have the `setenv' function. */
    public static int HAVE_SETENV = 1;

    /* Define to the version of this package. */
    public static String PACKAGE_VERSION = "UNKNOWN";

    /* Define to 1 if -lpcre2-16 is used also */
    public static int HAVE_PCRE2_16 = 0;    /* #undef HAVE_PCRE2_16 */

    /* Define to 1 if you have the <pcre2.h> header file. */
    public static int HAVE_PCRE2_H = 0; /* #undef HAVE_PCRE2_H */

    /* If available, contains the Python version number currently in use. */
    /* #undef HAVE_PYTHON */

    /* Define to 1 if your system has a GNU libc compatible `realloc' function,
       and to 0 otherwise. */
    public static int HAVE_REALLOC = 1;

    /* Define to 1 if you have the `scandir' function. */
    public static int HAVE_SCANDIR = 1;

    /* Define to 1 if you have the `sqrt' function. */
    public static int HAVE_SQRT = 1;

    /* Define to 1 if `stat' has the bug that it succeeds when given the
       zero-length file name argument. */
    public static int HAVE_STAT_EMPTY_STRING_BUG = 0;   /* #undef HAVE_STAT_EMPTY_STRING_BUG*/

    /* Define to 1 if you have the <stddef.h> header file. */
    public static int HAVE_STDDEF_H = 1;

    /* Define to 1 if you have the <stdint.h> header file. */
    public static int HAVE_STDINT_H = 1;

    /* Define to 1 if you have the <stdlib.h> header file. */
    public static int HAVE_STDLIB_H = 1;

    /* Define to 1 if you have the `strcmp' function. */
    public static int HAVE_STRCASECMP = 1;

    /* Define to 1 if you have the `strcasestr' function. */
    public static int HAVE_STRCASESTR = 1;

    /* Define to 1 if you have the `strchr' function. */
    public static int HAVE_STRCHR = 1;

    /* Define to 1 if you have the <strings.h> header file. */
    public static int HAVE_STRINGS_H = 1;

    /* Define to 1 if you have the <string.h> header file. */
    public static int HAVE_STRING_H = 1;

    /* Define to 1 if you have the `strrchr' function. */
    public static int HAVE_STRRCHR = 1;

    /* Define to 1 if you have the `strtol' function. */
    public static int HAVE_STRTOL = 1;

    /* Define to 1 if you have the `strtoul' function. */
    public static int HAVE_STRTOUL = 1;

    /* Define to 1 if you have the `strtoull' function. */
    public static int HAVE_STRTOULL = 1;

    /* Define to 1 if you have the <sys/stat.h> header file. */
    public static int HAVE_SYS_STAT_H = 1;

    /* Define to 1 if you have the <sys/types.h> header file. */
    public static int HAVE_SYS_TYPES_H = 1;

    /* Define to 1 if you have the <unistd.h> header file. */
    public static int HAVE_UNISTD_H = 1;

    /* Define to 1 if you have the <valgrind/valgrind.h> header file. */
    public static int HAVE_VALGRIND_VALGRIND_H = 0; /* #undef HAVE_VALGRIND_VALGRIND_H */

    /* Define to 1 if you have the <wchar.h> header file. */
    public static int HAVE_WCHAR_H = 1;

    /* Define to 1 if the system has the type `_Bool'. */
    public static int HAVE__BOOL = 1;

    /* Define to 1 if this is a release, skipping unstable DWG features, unknown
       DWG versions and objects. */
    public static int IS_RELEASE = 1;

    /* Define to 1 if `lstat' dereferences a symlink specified with a trailing
       slash. */
    public static int LSTAT_FOLLOWS_SLASHED_SYMLINK = 1;

    /* Define to the sub-directory where libtool stores uninstalled libraries. */
    public static String LT_OBJDIR = ".libs/";

    /* Define to the address where bug reports for this package should be sent. */
    public static String PACKAGE_BUGREPORT = "libredwg@gnu.org";

    /* Define to the full name of this package. */
    public static String PACKAGE_NAME = "LibreDWG";

    /* Define to the full name and version of this package. */
    public static String PACKAGE_STRING = "LibreDWG UNKNOWN";

    /* Define to the one symbol short name of this package. */
    public static String PACKAGE_TARNAME = "libredwg";

    /* Define to the home page for this package. */
    public static String PACKAGE_URL = "https://savannah.gnu.org/projects/libredwg/";

    /* The size of `size_t', as computed by sizeof. */
    public static int SIZEOF_SIZE_T = 8;

    /* The number of bytes in type wchar_t */
    public static int SIZEOF_WCHAR_T = 2;

/* If using the C implementation of alloca, define if you know the
direction of stack growth for your system; otherwise it will be
automatically deduced at runtime.
STACK_DIRECTION > 0 => grows toward higher addresses
STACK_DIRECTION < 0 => grows toward lower addresses
STACK_DIRECTION = 0 => direction of growth unknown */
    /* #undef STACK_DIRECTION */

    /* Define to 1 if you have the ANSI C header files. */
    public static int STDC_HEADERS = 1;

    /* Undefine to disable write support. */
    public static int USE_WRITE = 1;
}
