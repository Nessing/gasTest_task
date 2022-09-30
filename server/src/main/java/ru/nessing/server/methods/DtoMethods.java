package ru.nessing.server.methods;

import ru.nessing.server.DTO.IntersectionDto;
import ru.nessing.server.DTO.NameDto;
import ru.nessing.server.DTO.PathDto;
import ru.nessing.server.DTO.ResultDto;
import ru.nessing.server.Entities.Intersection;
import ru.nessing.server.Entities.Name;
import ru.nessing.server.Entities.Path;
import ru.nessing.server.Entities.Result;

import java.util.ArrayList;
import java.util.List;

public class DtoMethods {
    public ResultDto resultToDto(Result result) {
        List<IntersectionDto> intersectionDtoList = new ArrayList<>();
        result.getIntersectionList().forEach(i -> intersectionDtoList.add(getIntersectionDto(i)));
        return ResultDto.builder()
                .id(result.getId())
                .intersectionList(intersectionDtoList)
                .states(result.getStates())
                .build();
    }

    public IntersectionDto getIntersectionDto(Intersection intersection) {
        List<NameDto> nameDtoList = new ArrayList<>();
        List<PathDto> pathDtoList = new ArrayList<>();
        intersection.getNames().forEach(name -> nameDtoList.add(getNameDto(name)));
        intersection.getPaths().forEach(path -> pathDtoList.add(getPathDto(path)));
        return IntersectionDto.builder()
                .id(intersection.getId())
                .names(nameDtoList)
                .paths(pathDtoList)
                .build();
    }

    public NameDto getNameDto(Name name) {
        return NameDto.builder()
                .id(name.getId())
                .firstName(name.getFirstName())
                .middleName(name.getMiddleName())
                .lastName(name.getLastName())
                .build();
    }

    public PathDto getPathDto(Path path) {
        return PathDto.builder()
                .id(path.getId())
                .path(path.getPath())
                .build();
    }

    public Result getResult(ResultDto resultDto) {
        Result result = new Result();
        List<Intersection> intersectionList = new ArrayList<>();
        resultDto.getIntersectionList().forEach(i -> intersectionList.add(getIntersection(i)));
        result.setId(resultDto.getId());
        result.setIntersectionList(intersectionList);
        result.setStates(resultDto.getStates());
        return result;
    }

    public Intersection getIntersection(IntersectionDto intersectionDto) {
        Intersection intersection = new Intersection();
        List<Name> nameList = new ArrayList<>();
        List<Path> pathList = new ArrayList<>();
        intersectionDto.getNames().forEach(name -> nameList.add(getName(name)));
        intersectionDto.getPaths().forEach(path -> pathList.add(getPath(path)));
        intersection.setId(intersectionDto.getId());
        intersection.setNames(nameList);
        intersection.setPaths(pathList);
        return intersection;
    }

    public Name getName(NameDto nameDto) {
        Name name = new Name();
        name.setId(nameDto.getId());
        name.setFirstName(nameDto.getFirstName());
        name.setMiddleName(nameDto.getMiddleName());
        name.setLastName(nameDto.getLastName());
        return name;
    }

    public Path getPath(PathDto pathDto) {
        Path path = new Path();
        path.setId(pathDto.getId());
        path.setPath(pathDto.getPath());
        return path;
    }
}
