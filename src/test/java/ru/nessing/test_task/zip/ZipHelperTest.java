package ru.nessing.test_task.zip;

import org.junit.jupiter.api.Test;
import ru.nessing.test_task.methods.ZipHelper;

import java.io.File;
import java.io.IOException;

public class ZipHelperTest {

    @Test
    public void zip() throws IOException, IllegalAccessException {
        new ZipHelper().zip(new File("filestozip"));
    }
}
