package ru.nessing.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IntersectionDto {
    private Long id;
    private List<NameDto> names;
    private List<PathDto> paths;
}
