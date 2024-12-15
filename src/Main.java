import javax.sound.sampled.AudioFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
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
               // NewFile(path);

                error = dwg.dwg_read_file(dwgFilePath,objDwgData);
                break;
        }
    }
}