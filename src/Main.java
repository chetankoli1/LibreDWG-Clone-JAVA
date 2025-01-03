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
//        System.out.println("");
//        System.out.println("");
//        System.out.println("");
//        String commandInput = new Scanner(System.in).nextLine();
        String commandInput = "1";

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
                    specs.JSON = true;
                    macros.IS_JSON = true;
                    error = out_json.dwg_write_json(dat, objDwgData);
                    config.streamWriter.close();
                    System.out.println("JSON file is Genarated Succesfully");
                }
                break;
        }
    }

    private static void NewFile(String path) {

    }
}