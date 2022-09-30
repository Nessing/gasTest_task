package ru.nessing.server.services.methods;

import org.springframework.web.multipart.MultipartFile;
import ru.nessing.server.DTO.ResultDto;
import ru.nessing.server.Entities.Result;
import ru.nessing.server.enums.States;
import ru.nessing.server.methods.DtoMethods;
import ru.nessing.server.methods.ZipHelperServer;
import ru.nessing.server.repositories.ResultRepository;

import java.io.File;
import java.io.IOException;

public class ServerMethods {
    private static DtoMethods dtoMethods = new DtoMethods();

    public static String createFolder(MultipartFile file, Long id) throws IOException {
        String pathToFolderOfServer = "server/serverFiles/" + id;
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

    public static ResultDto createResultDto(ResultRepository resultRepository, Long id, States state) {
        Result result = resultRepository.findById(id).orElse(null);
        if (result == null) return null;
        ResultDto resultDto = dtoMethods.resultToDto(result);
        resultDto.setStates(state);
        resultRepository.save(dtoMethods.getResult(resultDto));
        return resultDto;
    }
}
