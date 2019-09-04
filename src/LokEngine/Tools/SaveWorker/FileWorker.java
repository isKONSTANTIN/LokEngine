package LokEngine.Tools.SaveWorker;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileWorker {

    String filePath;
    File file;

    FileWriter fileWriter;
    FileReader fileReader;

    public static boolean fileExists(String filePath){
        return new File(filePath).exists();
    }

    public FileWorker(String filePath) throws IOException {
        file = new File(filePath);
        this.filePath = filePath;

        if (!file.exists())
            if (!file.createNewFile())
                throw new IOException("File does not exist and cannot be created!");
    }

    public void write(String data) throws IOException {
        if (fileWriter != null){
            fileWriter.write(data);
        }else{
            throw new IOException("File not writable");
        }
    }

    public void flush() throws IOException {
        if (fileWriter != null){
            fileWriter.flush();
        }else{
            throw new IOException("File not writable");
        }
    }

    public String read() throws IOException {
        if (fileReader != null){
            Scanner scan = new Scanner(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            while (scan.hasNextLine()) {
                stringBuilder.append(scan.nextLine()).append(scan.hasNextLine() ? "\n" : "");
            }
            return stringBuilder.toString();
        }else{
            throw new IOException("File unreadable");
        }
    }

    public void openWrite() throws IOException {
        if (fileReader != null){
            fileReader.close();
            fileReader = null;
        }

        if (file.canWrite()){
            fileWriter = new FileWriter(file);
        }else if (!file.canWrite()){
            throw new IOException("File not writable");
        }
    }

    public void openRead() throws IOException {
        if (fileWriter != null){
            fileWriter.close();
            fileWriter = null;
        }

        if (file.canRead()){
            fileReader = new FileReader(file);
        }else if (!file.canRead()){
            throw new IOException("File unreadable");
        }
    }

    public void close() throws IOException {
        if (fileWriter != null)
            fileWriter.close();

        if (fileReader != null)
            fileReader.close();
    }

}
