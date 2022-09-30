package ru.nessing.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.nessing.server.enums.States;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResultDto {
    private Long id;
    private List<IntersectionDto> intersectionList;
    private States states;
}
