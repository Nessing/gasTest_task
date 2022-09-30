package ru.nessing.test_task.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.nessing.test_task.enums.States;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResultDto {
    private Long id;
    private List<IntersectionDto> intersectionList;
    private States states;
}
