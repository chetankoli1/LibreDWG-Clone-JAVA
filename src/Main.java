import javax.sound.sampled.AudioFormat;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        int error = 0;
        System.out.println("1. Read DWG to Write into JSON");
        System.out.println("2. Read JSON to write into new DWG");
        System.out.println("Enter your Option");
        String commandInput = new Scanner(System.in).nextLine();
       // String commandInput = "1";

        switch (commandInput)
        {
            case "1":
                Bit_Chain dat = new Bit_Chain();
                Dwg_Data objDwgData = new Dwg_Data();
                String dwgFilePath = args[0];
                config.outFilePath = args[1];

                String dirName = new File(config.outFilePath).getParent();
                String fileNameWithExtension = new File(config.outFilePath).getName().replace("[.][^.]+$","");
                String path = dirName + File.separator + fileNameWithExtension;
                NewFile(path);

                specs.DECODER = true;
                macros.IS_DECODER = true;
                macros.IS_JSON = false;
                macros.IS_ENCODER = false;
                specs.DECODER_OR_ENCODER = true;
                error = dwg.dwg_read_file(dwgFilePath,objDwgData);

                if(!config.outFilePath.isEmpty())
                {
                    if(new File(config.outFilePath).exists())
                    {
                        new File(config.outFilePath).delete();
                    }else{
                        File newfile = new File(config.outFilePath);
                    }
                    dat.fh = new RandomAccessFile(config.outFilePath,"rw");
                    dat.fh.close();
                }

                if(!(error >= DWG_ERROR.DWG_ERR_CLASSESNOTFOUND.value))
                {
                    dat.version = dat.from_version = objDwgData.header.version;
                    dat.codepages = objDwgData.header.codepage;
                    BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
                    config.streamWriter = writer;
                    specs.DECODER = false;
                    macros.IS_DECODER = false;
                    macros.IS_ENCODER = false;
                    specs.DECODER_OR_ENCODER = false;
                    specs.DXF_OR_PRINT = true;
                    specs.JSON = true;
                    macros.IS_JSON = true;
                    error = out_json.dwg_write_json(dat, objDwgData);
                    config.streamWriter.close();
                    System.out.println("JSON file is Genarated Succesfully");
                }
                break;
            case "2":
                dirName = new File(args[0]).getParent();
                fileNameWithExtension = new File(args[0]).getName().replace("[.][^.]+$","");
                path = dirName + File.separator + fileNameWithExtension;
                NewFile(path);

                dwg_write.dwg_write_main(8, args);
                System.out.println("New DWG file is Genarated Succesfully");
                break;

        }
    }

    private static void NewFile(String path) {

    }
}