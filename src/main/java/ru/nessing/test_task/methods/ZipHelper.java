package ru.nessing.test_task.methods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipHelper {

    private final String FILE = "result/out.zip";

    public File zip(File path) throws IOException, IllegalAccessException {
        if (!path.exists()) {
            return null;
        }
        File[] files = path.listFiles();
        if (files.length == 0)
            throw new IllegalAccessException("No files in path" + path.getAbsolutePath());

        FileOutputStream fos = new FileOutputStream(FILE);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        System.out.println("loading...");

        createZipDir(zipOut, files, "");

        zipOut.close();
        System.out.println("okay");
        return new File(FILE);
    }

    private void createZipDir(ZipOutputStream zipOut, File[] files, String path) throws IOException {
        for (File file : files) {
            if (file.isDirectory()) {
                createZipDir(zipOut, file.listFiles(), path + file.getName() + "/");
            } else if (!file.getName().equals(FILE)) {
                ZipEntry zipEntry = new ZipEntry(path + file.getName());
                zipOut.putNextEntry(zipEntry);

                FileInputStream fis = new FileInputStream(file);
                byte[] bytes = new byte[2048];
                int length;
                while ((length = fis.read(bytes)) != -1) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }
        }
    }
}
