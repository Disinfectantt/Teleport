package xyz.cringee.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Utils {

    public static File createFileIfNotExists(String path, String filename, Logger logger) throws IOException {
        Files.createDirectories(Paths.get(path));
        File file = new File(path + filename);
        if (!file.exists() && !file.isDirectory()) {
            boolean created = file.createNewFile();
            if (created) {
                logger.info("Created " + path + filename);
            }
        }
        return file;
    }

}
