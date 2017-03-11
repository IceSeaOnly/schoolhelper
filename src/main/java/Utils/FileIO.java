package Utils;

/**
 * Created by Administrator on 2017/1/16.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileIO{
    public static String ReadCH(String name){
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(name);
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),"gbk");
                BufferedReader reader = new BufferedReader(read);
                String line;
                while((line = reader.readLine()) != null){
                    sb.append(line);
                }
                reader.close();
                read.close();
            }
        } catch (Exception e) {
        }
        return sb.toString();
    }
    public static void WriteCH(String filename,String stringContent) {
        File file = new File(filename);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            BufferedWriter writer  = new BufferedWriter(write);
            writer.write(stringContent);
            writer.close();
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

