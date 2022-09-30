package ru.nessing.test_task.services.methods;

import org.springframework.web.multipart.MultipartFile;
import ru.nessing.test_task.methods.ZipHelperServer;

import java.io.File;
import java.io.IOException;

public class serverMethods {
    public static String createFolder(MultipartFile file, Long id) throws IOException {
        String pathToFolderOfServer = "serverFiles/" + id;
        File dir = new File(pathToFolderOfServer);
        dir.mkdirs();
        ZipHelperServer zipHelper = new ZipHelperServer();
        File tempFile = new File(pathToFolderOfServer + "/" + file.getOriginalFilename());
        tempFile.createNewFile();

        file.transferTo(tempFile.getAbsoluteFile());
        zipHelper.unzip(pathToFolderOfServer + "/" + file.getOriginalFilename(), pathToFolderOfServer);
        tempFile.delete();
        return pathToFolderOfServer;
    }
}
