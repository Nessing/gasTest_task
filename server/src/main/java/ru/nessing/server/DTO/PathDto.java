package ru.nessing.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PathDto {
    private Long id;
    private String path;
}
