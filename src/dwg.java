import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


public class dwg {
    public static int loglevel;
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

            fp.close();

            error = decode.dwg_decode(bit_chain,objDwgData);

        } catch (Exception e) {
            System.err.printf("Error handling file: %s%n", filename);
            e.printStackTrace();
        }

        return  error;
    }

    private static int dat_read_file(Bit_Chain dat, String filename) throws IOException {
        byte[] fileBytes = Files.readAllBytes(Path.of(filename));
        dat.chain = fileBytes;
        if(fileBytes.length < 0)
        {
            return 1;
        }

        return 0;
    }
}

class Dwg_Data {
    public Dwg_Header header = new Dwg_Header();
    public int opts;
}

class Dwg_Header{
    public DWG_VERSION_TYPE version = DWG_VERSION_TYPE.R_INVALID;
    public DWG_VERSION_TYPE from_version = DWG_VERSION_TYPE.R_INVALID;

    public char[] zero_5;
    public char is_maint;
    public  char zero_one_or_three;
    public int numentity_section;
    public int numheader_vars;
    public int[] unknown_s;
    public int thumbnail_address;
    public char dwg_version;
    public char maint_version;
    public int entities_start;
    public int entities_end;
    public int block_start;
    public int block_size;
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
    public int sections;

}