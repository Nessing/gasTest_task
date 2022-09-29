package ru.nessing.test_task.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nessing.test_task.Entities.*;
import ru.nessing.test_task.enums.States;
import ru.nessing.test_task.methods.ZipHelperServer;
import ru.nessing.test_task.repositories.NamesRepository;
import ru.nessing.test_task.repositories.PathsRepository;
import ru.nessing.test_task.repositories.IntersectionRepository;
import ru.nessing.test_task.repositories.ResultRepository;

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

    public String result(Long id) {
        Result result = resultRepository.findAllById(id);
        if (result == null) return States.NO_FOUND.toString();
        if (result.getStates() == States.READY) {
            StringBuilder builder = new StringBuilder();
            for (Intersection intersection : result.getIntersectionList()) {
                builder.append("-- a couple of files --\n");
                intersection.getPaths().stream().forEach(path -> builder.append(path.getPath() + "\n"));
                builder.append("-- names ---\n");
                intersection.getNames().stream().forEach(name -> builder.append(name.getFullName() + "\n"));
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
        if(file.isEmpty()) {
            Result result = resultRepository.findAllById(id);
            result.setStates(States.ERROR);
            resultRepository.save(result);
            return;
        }
        ArrayList<Intersection> intersections = new ArrayList<>();
        Result result = resultRepository.findAllById(id);
        result.setStates(States.LOADING);
        resultRepository.save(result);
        String path = "serverFiles/" + id;
        File dir = new File(path);
        dir.mkdirs();
        File tempFile = new File(path + "/" + file.getOriginalFilename());
        tempFile.createNewFile();
        List<List<String>> list;

        file.transferTo(tempFile.getAbsoluteFile());
        zipHelper.unzip(path + "/" + file.getOriginalFilename(), path);
        tempFile.delete();
        list = zipHelper.parseFolder(new File(path));

        for (List<String> arr : list) {
            List<Path> paths = new ArrayList<>();
            Path path1 = new Path();
            path1.setId(null);
            path1.setPath(arr.get(0));
            Path path2 = new Path();
            path2.setId(null);
            path2.setPath(arr.get(1));
            paths.add(path1);
            paths.add(path2);
            pathsRepository.save(path1);
            pathsRepository.save(path2);

            Intersection intersection = new Intersection();
            intersection.setId(null);
            List<Name> names = new ArrayList<>();
            for (int i = 2; i < arr.size(); i++) {

                String[] parse = arr.get(i).split(" ");
                Name name = new Name();
                name.setId(null);
                name.setFirstName(parse[0]);
                name.setMiddleName(parse[1]);
                name.setLastName(parse[2]);
                namesRepository.save(name);
                names.add(name);
            }
            intersection.setNames(names);
            intersection.setPaths(paths);
            intersectionRepository.save(intersection);
            intersections.add(intersection);
        }
        result.setIntersectionList(intersections);
        result.setStates(States.READY);
        resultRepository.save(result);
    }
}
