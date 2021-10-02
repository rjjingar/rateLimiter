package rate.retriever;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {

    public static String readEntireFile(final String filePath) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Could not read file " + filePath);
            e.printStackTrace();
        }
        return new String(encoded, Charset.defaultCharset());

    }
}
