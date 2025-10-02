package shaman.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TextReader {

    //read all file and return the text read in string
    public static String readFile(File file) throws IOException {
        return Files.readString(file.toPath());
    }

    //Same as readFile but now we read all file in the folder
    public static String readAllTextFilesInFolder(File folder) throws IOException {
        StringBuilder sb = new StringBuilder();
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (files != null) {
            for (File f : files) {
                sb.append("=== ").append(f.getName()).append(" ===\n");
                sb.append(readFile(f)).append("\n\n");
            }
        }
        return sb.toString();
    }
}
