package unit_test.file;

import org.inspien.file.FileHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class FileTest {

    private static final String PATH = "/Users/0hyuncho/Desktop/inspien_code_temp_test";
    private static final String FILE_NAME = "/test.txt";
    private static final String TEST_DATA = "INSPIEN\nINSPIRES\nME";

    @Test
    public void readTest() throws IOException {
        System.out.println(FileHandler.read(PATH + FILE_NAME));
    }

    @Test
    public void writeTest() throws IOException {
        FileHandler.write(PATH + FILE_NAME, TEST_DATA);
    }
}
