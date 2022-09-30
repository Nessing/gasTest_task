package ru.nessing.test_task.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nessing.test_task.DTO.IntersectionDto;
import ru.nessing.test_task.DTO.NameDto;
import ru.nessing.test_task.DTO.PathDto;
import ru.nessing.test_task.DTO.ResultDto;
import ru.nessing.test_task.Entities.Result;
import ru.nessing.test_task.enums.States;
import ru.nessing.test_task.methods.DtoMethods;
import ru.nessing.test_task.methods.ZipHelperServer;
import ru.nessing.test_task.repositories.IntersectionRepository;
import ru.nessing.test_task.repositories.NamesRepository;
import ru.nessing.test_task.repositories.PathsRepository;
import ru.nessing.test_task.repositories.ResultRepository;
import ru.nessing.test_task.services.methods.serverMethods;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServerService {
    private final IntersectionRepository intersectionRepository;
    private final NamesRepository namesRepository;
    private final PathsRepository pathsRepository;
    private final ResultRepository resultRepository;

    private Long id;
    private ZipHelperServer zipHelper = new ZipHelperServer();
    private DtoMethods dtoMethods = new DtoMethods();

    public String result(Long id) {
        Result result = resultRepository.findById(id).orElse(null);
        if (result == null) return States.NO_FOUND.toString();
        ResultDto resultDto = dtoMethods.resultToDto(result);

        if (resultDto.getStates() == States.READY) {
            StringBuilder builder = new StringBuilder();
            for (IntersectionDto intersection : resultDto.getIntersectionList()) {
                builder.append("-- a couple of files --\n");
                intersection.getPaths().forEach(path -> builder.append(path.getPath() + "\n"));
                builder.append("-- names ---\n");
                intersection.getNames().forEach(name -> builder.append(name.getFullName() + "\n"));
            }
            return builder.toString();
        }
        return result.getStates().toString();
    }

    public Long getId() {
        Result result = new Result();
        result.setId(null);
        resultRepository.save(result);
        id = result.getId();
        return id;
    }

    public void load(MultipartFile file, Long id) throws IOException {
        this.id = id;
        if (file.isEmpty()) {
            Result result = resultRepository.findById(id).orElse(null);
            ResultDto resultDto = dtoMethods.resultToDto(result);
            resultDto.setStates(States.ERROR);
            resultRepository.save(dtoMethods.getResult(resultDto));
            System.out.println();
            return;
        }
        ArrayList<IntersectionDto> intersectionDtoList = new ArrayList<>();
        Result result = resultRepository.findAllById(id);
        ResultDto resultDto = dtoMethods.resultToDto(result);
        resultDto.setStates(States.LOADING);
        resultRepository.save(dtoMethods.getResult(resultDto));

        String path = serverMethods.createFolder(file, id);

        List<List<String>> list = zipHelper.parseFolder(new File(path));

        collect(intersectionDtoList, list);

        resultDto.setIntersectionList(intersectionDtoList);
        resultDto.setStates(States.READY);
        resultRepository.save(dtoMethods.getResult(resultDto));
    }

    private void collect(ArrayList<IntersectionDto> intersectionDtoList, List<List<String>> list) {
        for (List<String> arr : list) {
            List<PathDto> paths = new ArrayList<>();
            PathDto pathDto1 = PathDto.builder()
                    .path(arr.get(0))
                    .build();
            PathDto pathDto2 = PathDto.builder()
                    .path(arr.get(1))
                    .build();
            paths.add(dtoMethods.getPathDto(
                    pathsRepository.save(dtoMethods.getPath(pathDto1)))
            );
            paths.add(dtoMethods.getPathDto(
                    pathsRepository.save(dtoMethods.getPath(pathDto2)))
            );

            IntersectionDto intersectionDto = new IntersectionDto();
            List<NameDto> names = new ArrayList<>();
            for (int i = 2; i < arr.size(); i++) {

                String[] parse = arr.get(i).split(" ");
                NameDto nameDto = NameDto.builder()
                        .firstName(parse[0])
                        .middleName(parse[1])
                        .lastName(parse[1])
                        .build();
                names.add(dtoMethods.getNameDto(
                        namesRepository.save(dtoMethods.getName(nameDto)))
                );
            }
            intersectionDto.setNames(names);
            intersectionDto.setPaths(paths);
            intersectionDtoList.add(dtoMethods.getIntersectionDto(
                    intersectionRepository.save(dtoMethods.getIntersection(intersectionDto))
            ));
        }
    }
}
