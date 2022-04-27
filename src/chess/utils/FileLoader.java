package chess.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLoader {

    public String load(String path) {
        String content = "";
        try {
            byte[] data = Files.readAllBytes(Paths.get(path));
            content = new String(data, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
