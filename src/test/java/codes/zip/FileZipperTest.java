package codes.zip;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class FileZipperTest {

    @Test
    void zip_테스트(){

        String property = System.getProperty("user.dir");
        System.out.println("property = " + property);
        String dirpath = property + "/file";

        File file = new File(dirpath);
        FileZipper.zipFileList(Objects.requireNonNull(file.listFiles()), "csv", true);



    }

}